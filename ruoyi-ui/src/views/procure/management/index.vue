<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="合同编号：" prop="contractCode">
        <el-input
          v-model="queryParams.contractCode"
          placeholder="请输入合同编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="签订日期：" prop="signingDate">
        <el-date-picker clearable
                        v-model="queryParams.signingDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择签订日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="合同状态：" prop="contractstatusId">
        <el-select v-model="queryParams.contractstatusId" placeholder="请选择合同状态" clearable>
          <el-option
            v-for="dict in dict.type.contract_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="供应商：" prop="sdId">
        <el-input
          v-model="supplierListqueryParams.sbiName"
          placeholder=""
          clearable
          @keyup.enter.native="handleQuery"
        />
        <i class="el-icon-search" id="serachOne" @click="showDialogSupplierList()"></i>
        <el-dialog :visible.sync="dialogSupplierList" title="供应商-浏览框" >
          <!-- 这里是供应商的内容 -->
          <el-table v-loading="loading" :data="supplierList" @row-click="handleRowClickSupplierList">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="供应商编码" align="center" prop="sdCode" />
            <el-table-column label="供应商名称" align="center" prop="sbiName" />
          </el-table>

          <pagination v-show="supplierListTotal > 0" :total="supplierListTotal" :page.sync="supplierListqueryParams.pageNum"
                      :limit.sync="supplierListqueryParams.pageSize" @pagination="getListSupplierList" />
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogSupplierList = false">取消</el-button>
          </div>
        </el-dialog>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          size="mini"
          @click="handleAdd"
          v-hasPermi="['procure:management:add']"
        >发起采购合同</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['procure:management:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['procure:management:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['procure:management:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="managementList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="相关项目" align="center" prop="relatedProjects" />
      <el-table-column label="合同名称" align="center" prop="contractName" />
      <el-table-column label="合同编号" align="center" prop="contractCode" />
      <el-table-column label="供应商" align="center" prop="sdId" />
      <el-table-column label="负责人" align="center" prop="head" />
      <el-table-column label="签订日期" align="center" prop="signingDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.signingDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="合同总金额" align="center" prop="totalAmount" />
      <el-table-column label="付款比例(%)" align="center" prop="paymentProportion" />
      <el-table-column label="合同状态" align="center" prop="contractstatusId">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.contract_status" :value="scope.row.contractstatusId"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['procure:management:remove']"
          >查看详情</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改采购合同管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="申请人" prop="applicant">
          <el-input v-model="form.applicant" placeholder="请输入申请人" />
        </el-form-item>
        <el-form-item label="申请日期" prop="applicationDate">
          <el-date-picker clearable
                          v-model="form.applicationDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择申请日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="申请部门" prop="departmentId">
          <el-input v-model="form.departmentId" placeholder="请输入申请部门" />
        </el-form-item>
        <el-form-item label="申请公司" prop="companiesId">
          <el-input v-model="form.companiesId" placeholder="请输入申请公司" />
        </el-form-item>

        <el-form-item label="是否主子合同" prop="isMainContract">
          <el-radio-group v-model="form.isMainContract">
            <el-radio
              v-for="dict in dict.type.is_main_contract"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="预算相关合同" prop="budgetRelatedContracts">
          <el-input v-model="form.budgetRelatedContracts" placeholder="请输入预算相关合同" />
        </el-form-item>
        <el-form-item label="成本中心" prop="costCenter">
          <el-input v-model="form.costCenter" placeholder="请输入成本中心" />
        </el-form-item>
        <el-form-item label="项目总预算" prop="totalProjectBudget">
          <el-select v-model="form.totalProjectBudget" placeholder="请选择项目总预算">
            <el-option
              v-for="dict in dict.type.total_project_budget"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="项目可用预算" prop="projectAvailableBudget">
          <el-input v-model="form.projectAvailableBudget" placeholder="请输入项目可用预算" />
        </el-form-item>

        <el-form-item label="合同名称" prop="contractName">
          <el-input v-model="form.contractName" placeholder="请输入合同名称" />
        </el-form-item>
        <el-form-item label="项目相关合同" prop="projectRelatedContracts">
          <el-select v-model="form.projectRelatedContracts" placeholder="请选择项目相关合同">
            <el-option
              v-for="dict in dict.type.project_related_contracts"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="负责人" prop="head">
          <el-input v-model="form.head" placeholder="请输入负责人" />
        </el-form-item>
        <el-form-item label="合同总金额" prop="totalAmount">
          <el-input v-model="form.totalAmount" placeholder="请输入合同总金额" />
        </el-form-item>
        <el-form-item label="付款比例(%)" prop="paymentProportion">
          <el-input v-model="form.paymentProportion" placeholder="请输入付款比例(%)" />
        </el-form-item>
        <el-form-item label="合同状态" prop="contractstatusId">
          <el-select v-model="form.contractstatusId" placeholder="请选择合同状态">
            <el-option
              v-for="dict in dict.type.contract_status"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="供应商" prop="sdId">
          <el-input v-model="form.sdId" placeholder="请输入供应商" />
        </el-form-item>
        <el-form-item label="密集" prop="dense">
          <el-select v-model="form.dense" placeholder="请选择密集">
            <el-option
              v-for="dict in dict.type.dense"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="开始时间" prop="beginTime">
          <el-date-picker clearable
                          v-model="form.beginTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker clearable
                          v-model="form.endTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择结束时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="合同金额" prop="contractPrice">
          <el-input v-model="form.contractPrice" placeholder="请输入合同金额" />
        </el-form-item>
        <el-form-item label="大写" prop="capitalization">
          <el-input v-model="form.capitalization" placeholder="请输入大写" />
        </el-form-item>
        <el-form-item label="印花税金额" prop="stampDutyAmount">
          <el-input v-model="form.stampDutyAmount" placeholder="请输入印花税金额" />
        </el-form-item>
        <el-form-item label="合同情况说明" prop="contractDescription">
          <el-input v-model="form.contractDescription" placeholder="请输入合同情况说明" />
        </el-form-item>
        <el-form-item label="采购清单" prop="purchasingList">
          <el-input v-model="form.purchasingList" placeholder="" readonly/>
          <i class="el-icon-search" id="serachOne2" @click="showManager()"></i>
          <el-dialog :visible.sync="dialogManager" title="ERP采购清单" :modal="false">
            <el-table v-loading="loading" :data="managerList" @row-click="handleSelectionChangeManager">
              <el-table-column type="selection" width="55" align="center" />
              <el-table-column label="订单编号" align="center" prop="orderCode" />
              <el-table-column label="联系人" align="center" prop="contacts" />
              <el-table-column label="下单日期" align="center" prop="createTime" />
            </el-table>
            <pagination v-show="managerTotal > 0" :total="managerTotal" :page.sync="managerqueryParams.pageNum"
                        :limit.sync="managerqueryParams.pageSize" @pagination="getListManager" />
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogManager = false">取消</el-button>
            </div>
          </el-dialog>
        </el-form-item>
      </el-form>

      <el-divider content-position="center">合同标的物信息</el-divider>
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddSupplierList">添加</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteSupplierList">删除</el-button>
        </el-col>
      </el-row>
      <el-table :data="SupplierList" :row-class-name="rowSupplierListIndex" @selection-change="handleSupplierListSelectionChange" ref="supplier">
        <el-table-column type="selection" width="50" align="center" />
        <el-table-column label="序号" align="center" prop="index" width="50"/>
        <el-table-column label="产品服务名称" prop="productName" width="150">
          <template slot-scope="scope">
            <el-input v-model="scope.row.productName" placeholder="" />
            <i class="el-icon-search" id="serachOne1" @click="showProduct()"></i>
            <el-dialog :visible.sync="dialogProducts" title="产品/服务信息" :modal="false">
              <!-- 这里是合同标的物信息的内容 -->
              <el-table v-loading="loading" :data="productList" @row-click="handleRowClickProductList">
                <el-table-column type="selection" width="55" align="center" />
                <el-table-column label="产品/服务信息" align="center" prop="productName" />
                <el-table-column label="产品服务编号" align="center" prop="productCode" />
                <el-table-column label="规格型号及要求" align="center" prop="specifications" />
                <el-table-column label="单位" align="center" prop="unit" />
                <el-table-column label="含税单价" align="center" prop="price" />
                <el-table-column label="税点" align="center" prop="taxRate" />
              </el-table>
              <pagination v-show="productTotal > 0" :total="productTotal" :page.sync="productqueryParams.pageNum"
                          :limit.sync="productqueryParams.pageSize" @pagination="getListProducts" />
              <div slot="footer" class="dialog-footer">
                <el-button @click="dialogProducts = false">取消</el-button>
              </div>
            </el-dialog>
          </template>

        </el-table-column>
        <el-table-column label="框架合同行编号" prop="productCode" width="150">
          <template slot-scope="scope">
            <el-input v-model="scope.row.productCode" placeholder="" />
          </template>
        </el-table-column>
        <el-table-column label="规格型号" prop="specifications" width="150">
          <template slot-scope="scope">
            <el-input v-model="scope.row.specifications" placeholder="" />
          </template>
        </el-table-column>
        <el-table-column label="单位" prop="unit" width="150">
          <template slot-scope="scope">
            <el-input v-model="scope.row.unit" placeholder="" />
          </template>
        </el-table-column>
        <el-table-column label="含税单价" prop="price" width="150">
          <template slot-scope="scope">
            <el-input v-model="scope.row.price" placeholder="" />
          </template>
        </el-table-column>
        <el-table-column label="数量" prop="taxRate" width="150">
          <template slot-scope="scope">
            <el-input v-model="scope.row.number" placeholder="" />
          </template>
        </el-table-column>
        <el-table-column label="小计" prop="materialName" width="150">
          <template slot-scope="scope">
            <el-input v-model="scope.row.subtotal" placeholder="" />
          </template>
        </el-table-column>

      </el-table>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listManagement, getManagement, delManagement, addManagement, updateManagement, listSupplier } from "@/api/procure/management";
import {listProducts} from "@/api/pms/products";
import {listManager} from "@/api/pms/manager"

export default {
  name: "Management",
  dicts: ['is_main_contract', 'total_project_budget', 'dense', 'project_related_contracts', 'contract_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 采购合同管理表格数据
      managementList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 进度条展示
      paymentProportion: 0,
      // 供应商基本信息
      supplierList: [],
      // 供应商基本信息总条数
      supplierListTotal: 0,
      //供应商查询参数
      supplierListqueryParams: {
        pageNum: 1,
        pageSize: 10,
        sdCode: null,
        sbiName: null,
        phone: '',
        people: '',
      },
      // 合同标的物信息表格数据
      SupplierList:[],
      // 合同标的物查询参数
      productqueryParams: {
        pageNum: 1,
        pageSize: 10
      },
      // 合同标的物列表
      productList:[],
      // 合同标的物总数
      productTotal:0,
      // 采购清单列表
      managerList: [],
      // 采购清单总数
      managerTotal:0,
      managerqueryParams:{
        pageNum: 1,
        pageSize: 10
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        contractName: null,
        contractCode: null,
        purchasingCode: null,
        projectRelatedContracts: null,
        applicant: null,
        applicationDate: null,
        departmentId: null,
        companiesId: null,
        relatedProjects: null,
        isMainContract: null,
        relatedMainContract: null,
        budgetRelatedContracts: null,
        costCenter: null,
        head: null,
        signingDate: null,
        totalAmount: null,
        paymentProportion: null,
        contractstatusId: null,
        sdId: null,
        totalProjectBudget: null,
        projectAvailableBudget: null,
        contractType: null,
        dense: null,
        beginTime: null,
        endTime: null,
        contractPrice: null,
        capitalization: null,
        stampDutyAmount: null,
        contractDescription: null,
        purchasingList: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      dialogSupplierList: false, // 用于标记供应商列表是否展示
      dialogProducts: false, // 用于标记合同标的物信息列表是否展示
      dialogManager: false, // 用于采购清单列表是否展示

    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询采购合同管理列表 */
    getList() {
      this.loading = true;
      listManagement(this.queryParams).then(response => {
        this.managementList = response.rows;
        this.total = response.total;
        this.loading = false;
        console.log(this.managementList)
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        contractManagementId: null,
        contractName: null,
        contractCode: null,
        purchasingCode: null,
        projectRelatedContracts: null,
        applicant: null,
        applicationDate: null,
        departmentId: null,
        companiesId: null,
        relatedProjects: null,
        isMainContract: null,
        relatedMainContract: null,
        budgetRelatedContracts: null,
        costCenter: null,
        head: null,
        signingDate: null,
        totalAmount: null,
        paymentProportion: null,
        contractstatusId: null,
        sdId: null,
        totalProjectBudget: null,
        projectAvailableBudget: null,
        contractType: null,
        dense: null,
        beginTime: null,
        endTime: null,
        contractPrice: null,
        capitalization: null,
        stampDutyAmount: null,
        contractDescription: null,
        purchasingList: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.contractManagementId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加采购合同管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const contractManagementId = row.contractManagementId || this.ids
      getManagement(contractManagementId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改采购合同管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.contractManagementId != null) {
            updateManagement(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addManagement(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const contractManagementIds = row.contractManagementId || this.ids;
      this.$modal.confirm('是否确认删除采购合同管理编号为"' + contractManagementIds + '"的数据项？').then(function() {
        return delManagement(contractManagementIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('procure/management/export', {
        ...this.queryParams
      }, `management_${new Date().getTime()}.xlsx`)
    },
    /** 查询供应商 */
    showDialogSupplierList(){
      this.dialogSupplierList = true;
      this.getListSupplierList();
    },
    /** 查询供应商详细列表 */
    getListSupplierList() {
      this.loading = true;
      listSupplier(this.supplierListqueryParams).then(response => {
        this.supplierList = response.rows;
        this.stotal = response.total;
        this.loading = false;
      });
    },
    /** 供应商名称查询（填充） */
    handleRowClickSupplierList(row){
      this.supplierListqueryParams.sbiName = row.sbiName; // 将供应商名称填充到输入框中
      this.dialogSupplierList = false; // 关闭对话框
    },
    /** 合同标的物信息序号 */
    rowSupplierListIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 同标的物信息添加按钮操作 */
    handleAddSupplierList() {
      let obj = {};
      obj.productId = "";
      obj.productName = "";
      obj.productCode = "";
      obj.specifications = "";
      obj.unit = "";
      obj.price = "";
      obj.taxRate = "";
      obj.number = "";
      obj.subtotal = "";
      this.SupplierList.push(obj);
    },
    /** 查询产品信息列表 */
    getListProducts() {
      this.loading = true;
      listProducts(this.productqueryParams).then(response => {
        this.productList = response.rows;
        this.producttotal = response.total;
        this.loading = false;
      });
    },
    //控制产品信息的显示
    showProduct() {
      this.dialogProducts = true;
      this.getListProducts();
    },
    // 产品信息框架+数据
    handleRowClickProductList(row){
      const data = {
        productName: row.productName,
        productCode: row.productCode,
        specifications: row.specifications,
        unit: row.unit,
        price: row.price,
        taxRate: row.taxRate,
        number: row.number,
        subtotal: row.subtotal,
        // tax: row.tax,
      };
      let index = this.SupplierList.length;
      if (this.SupplierList.length === 1) {
        // 修改第一条数据的属性值
        this.SupplierList[0].productName = row.productName;// 物料编码
        this.SupplierList[0].productCode = row.productCode;// 物料名称
        this.SupplierList[0].specifications = row.specifications;// 品类
        this.SupplierList[0].unit = row.unit;// 物料品类
        this.SupplierList[0].price = row.price; // 物料规格
        this.SupplierList[0].taxRate = row.taxRate; // 物料型号
        this.SupplierList[0].number = row.number; // 物料单位
        this.SupplierList[0].subtotal=row.subtotal; // 品牌
      } else {
        this.SupplierList[index - 1].productName = row.productName;
        this.SupplierList[index - 1].productCode = row.productCode;
        this.SupplierList[index - 1].specifications = row.specifications;
        this.SupplierList[index - 1].unit = row.unit;
        this.SupplierList[index - 1].price = row.price;
        this.SupplierList[index - 1].taxRate = row.taxRate;
        this.SupplierList[index - 1].number = row.number;
        this.SupplierList[index - 1].subtotal=row.subtotal;
      }
      this.dialogProducts = false; // 关闭对话框
    },
    /** 产品信息复选框选中数据 */
    handleSupplierListSelectionChange(selection) {
      this.checkedSupplier = selection.map(item => item.index)
    },
    /** 产品信息删除按钮操作 */
    handleDeleteSupplierList() {
      if (this.checkedSupplier.length == 0) {
        this.$modal.msgError("请先选择要删除的产品数据");
      } else {
        const SupplierList = this.SupplierList;
        const checkedSupplier = this.checkedSupplier;
        this.SupplierList = SupplierList.filter(function(item) {
          return checkedSupplier.indexOf(item.index) == -1
        });
      }
    },
    /** 查询采购订单管理列表 */
    getListManager() {
      this.loading = true;
      listManager(this.managerqueryParams).then(response => {
        this.managerList = response.rows;
        this.managerTotal = response.total;
        this.loading = false;
      });
    },
    //控制采购订单信息的显示
    showManager() {
      this.dialogManager = true;
      this.getListManager();
    },
    // 选中采购清单
    handleSelectionChangeManager(row){
      this.form.orderId = row.orderId;
      for (let i = 0; i < this.managementList.length; i++) {
        const innerElement = this.managementList[i];
        if (innerElement.orderId === row.orderId) {
          this.orderCode = innerElement.orderCode
        }
      }
      this.dialogManager = false; // 关闭对话框
    }
  }
};
</script>
<style>
#serachOne {
  position: absolute;
  right: 10px;
  top: 10px;
}
#serachOne1 {
  position: absolute;
  right: 15px;
  top: 21.5px;
}
#serachOne2{
  position: absolute;
  right: 15px;
  top: 13px;
}
</style>
