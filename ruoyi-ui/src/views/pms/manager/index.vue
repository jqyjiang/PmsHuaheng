<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="108px">
      <el-form-item label="供应商名称:" prop="supplier">
        <el-input v-model="squeryParams.sbiName" placeholder="" clearable @keyup.enter.native="handleQuery"
          icon="el-icon-search" />
        <i class="el-icon-search" id="serachOne" @click="showDiagSupplie()"></i>
        <el-dialog :visible.sync="dialogVisible" title="供应商名称">
          <!-- 这里是供应商的内容 -->

          <el-table v-loading="loading" :data="detailsList" @row-click="handleRowClick">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="供应商详细编码" align="center" prop="sdCode" />
            <el-table-column label="供应商名称" align="center" prop="sbiName" />
          </el-table>

          <pagination v-show="stotal > 0" :total="stotal" :page.sync="squeryParams.pageNum"
            :limit.sync="squeryParams.pageSize" @pagination="getList1" />
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
          </div>
        </el-dialog>
      </el-form-item>
      <el-form-item label="采购订单编号:" prop="orderCode">
        <el-input v-model="queryParams.orderCode" placeholder="请输入采购订单编号" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="订单来源:" prop="orderSource">
        <el-select v-model="queryParams.orderSource" placeholder="请选择订单来源" clearable>
          <el-option v-for="dict in dict.type.order_source" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="采购审批状态:" prop="orderState">
        <el-select v-model="queryParams.orderState" placeholder="请选择采购审批状态" clearable>
          <el-option v-for="dict in dict.type.order_state" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['pms:manager:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['pms:manager:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['pms:manager:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['pms:manager:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="managerList" @selection-change="handleSelectionChangeCurrency">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="采购订单id" align="center" prop="orderId" /> -->
      <el-table-column label="采购订单编号" align="center" prop="orderCode" />
      <el-table-column label="公司" align="center" prop="companies.companiesName" />
      <el-table-column label="订单来源" align="center" prop="orderSource">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.order_source" :value="scope.row.orderSource" />
        </template>
      </el-table-column>
      <el-table-column label="关联合同名称" align="center" prop="contractName" />
      <el-table-column label="需求总数量" align="center" prop="totalDemand" />
      <el-table-column label="采购员" align="center" prop="purchaser" />
      <el-table-column label="采购审批状态" align="center" prop="orderState">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.order_state" :value="scope.row.orderState" />
        </template>
      </el-table-column>
      <el-table-column label="订单执行状态" align="center" prop="orderTypeRunning.ortName" />
      <el-table-column label="供应商名称" align="center" prop="supplierDetails.sbiName" />
      <el-table-column label="采购订单创建日期" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="物料信息" align="center" prop="materialId">
        <template slot-scope="scope">
          <span>{{ scope.row ? getFormattedMaterialName(scope.row) : '' }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['pms:manager:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['pms:manager:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改采购订单管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="采购订单编号" prop="orderCode">
          <el-input v-model="form.orderCode" placeholder="请输入采购订单编号" />
        </el-form-item>
        <el-form-item label="公司" prop="company">
          <el-input v-model="form.company" placeholder="请输入公司" />
        </el-form-item>
        <el-form-item label="订单类型" prop="orderType">
          <el-select v-model="form.orderType" placeholder="请选择订单类型">
            <el-option v-for="dict in dict.type.order_type" :key="dict.value" :label="dict.label"
              :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="订单来源" prop="orderSource">
          <el-select v-model="form.orderSource" placeholder="请选择订单来源">
            <el-option v-for="dict in dict.type.order_source" :key="dict.value" :label="dict.label"
              :value="dict.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="采购组织" prop="purOrganization">
          <el-select v-model="form.purOrganization" placeholder="请选择采购组织">
            <el-option v-for="dict in dict.type.procure" :key="dict.value" :label="dict.label"
              :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="附件" prop="annex">
          <file-upload v-model="form.annex" />
        </el-form-item>
        <el-form-item label="是否自提" prop="isSelfPickup">
          <el-select v-model="form.isSelfPickup" placeholder="请选择是否自提">
            <el-option v-for="dict in dict.type.self_pickup" :key="dict.value" :label="dict.label"
              :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="自提车牌号" prop="licensePlateNumber">
          <el-input v-model="form.licensePlateNumber" placeholder="请输入自提车牌号" :disabled="!isSelfPickupSelected" />
        </el-form-item>
        <el-form-item label="自提司机联系方式" prop="concatInfomation">
          <el-input v-model="form.concatInfomation" placeholder="请输入自提司机联系方式" :disabled="!isSelfPickupSelected" />
        </el-form-item>
        <el-form-item label="关联合同编号" prop="contractCode">
          <el-input v-model="form.contractCode" placeholder="请输入关联合同编号" />
        </el-form-item>
        <el-form-item label="关联合同名称" prop="contractName">
          <el-input v-model="form.contractName" placeholder="请输入关联合同名称" />
        </el-form-item>
        <el-form-item label="订单说明" prop="orderDescription">
          <el-input v-model="form.orderDescription" placeholder="请输入订单说明" />
        </el-form-item>
        <el-form-item label="需求总数量" prop="totalDemand">
          <el-input :value="totalDemand" disabled placeholder="请输入需求总数量" />
        </el-form-item>
        <el-form-item label="采购员" prop="purchaser">
          <el-input v-model="form.purchaser" placeholder="请输入采购员" />
        </el-form-item>
        <el-form-item label="采购审批状态" prop="orderState">
          <el-select v-model="form.orderState" placeholder="请选择采购审批状态">
            <el-option v-for="dict in dict.type.order_state" :key="dict.value" :label="dict.label"
              :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="币种" prop="currencyId">
          <el-input v-model="currencyName" placeholder="请输入币种" />
          <i class="el-icon-search" id="serachOne" @click="showDiagCurrency"></i>
          <el-dialog :visible.sync="dialogCurrency" title="币种定义" :modal="false">
            <el-form :model="queryParams" ref="cqueryForm" size="small" :inline="true" v-show="showSearch"
              label-width="68px">
              <el-form-item label="币种编码" prop="currencyCode">
                <el-input v-model="cqueryParams.currencyCode" placeholder="请输入币种编码" clearable
                  @keyup.enter.native="handleQuery2" />
              </el-form-item>
              <el-form-item label="币种名称" prop="currencyName">
                <el-input v-model="cqueryParams.currencyName" placeholder="请输入币种名称" clearable
                  @keyup.enter.native="handleQuery2" />
              </el-form-item>
              <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery2">搜索</el-button>
              </el-form-item>
            </el-form>
            <el-table v-loading="loading" :data="currencyList" @row-click="handleSelectionChangeCurrency">
              <el-table-column type="selection" width="55" align="center" />
              <!-- <el-table-column label="币种ID" align="center" prop="currencyId" /> -->
              <el-table-column label="币种编码" align="center" prop="currencyCode" />
              <el-table-column label="币种名称" align="center" prop="currencyName" />
              <el-table-column label="国家/地区" align="center" prop="countryRegion" />
              <el-table-column label="财务精度" align="center" prop="financialAccuracy" />
              <el-table-column label="精度" align="center" prop="accuracy" />
              <el-table-column label="货币符号" align="center" prop="currencySymbol" />
            </el-table>
            <pagination v-show="ctotal > 0" :total="ctotal" :page.sync="cqueryParams.pageNum"
              :limit.sync="cqueryParams.pageSize" @pagination="getListCurrency" />
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogCurrency = false">取消</el-button>
            </div>
          </el-dialog>
        </el-form-item>
        <el-form-item label="供应商名称" prop="supplier">
          <el-input v-model="supplyName" placeholder="请输入供应商名称" />
          <i class="el-icon-search" id="serachOne" @click="showDiagSupplie1()"></i>
          <el-dialog :visible.sync="dialogVisible1" title="供应商名称" :modal="false">
            <!-- 这里是供应商的内容 -->
            <el-table v-loading="loading" :data="detailsList" @row-click="handleRowClick1">
              <el-table-column type="selection" width="55" align="center" />
              <el-table-column label="供应商详细编码" align="center" prop="sdCode" />
              <el-table-column label="供应商名称" align="center" prop="sbiName" />
            </el-table>

            <pagination v-show="stotal > 0" :total="stotal" :page.sync="squeryParams.pageNum"
              :limit.sync="squeryParams.pageSize" @pagination="getList1" />
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogVisible1 = false">取消</el-button>
            </div>
          </el-dialog>
        </el-form-item>
        <el-form-item label="联系人" prop="contacts">
          <el-input v-model="form.contacts" placeholder="请输入联系人" />
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="供应商发票方式" prop="invoiceMethod">
          <el-select v-model="form.invoiceMethod" placeholder="请选择供应商发票方式">
            <el-option v-for="dict in dict.type.invoice_method" :key="dict.value" :label="dict.label"
              :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-divider content-position="center">订单物料明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddOrderMaterial">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteOrderMaterial">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="orderMaterialList" :row-class-name="rowOrderMaterialIndex"
          @selection-change="handleOrderMaterialSelectionChange" ref="orderMaterial">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50" />
          <el-table-column label="物料编码" prop="orCode" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.orCode" placeholder="" />
              <i class="el-icon-search" id="serachOne1" @click="showMaterial()"></i>
              <el-dialog :visible.sync="dialogMaterial" title="物料管理-浏览框" :modal="false">
                <!-- 这里是物料管理的内容 -->
                <el-table v-loading="loading" :data="materialList" @row-click="handleRowClickMaterial">
                  <el-table-column type="selection" width="55" align="center" />
                  <el-table-column label="物料编码" align="center" prop="materialCode" />
                  <el-table-column label="物料名称" align="center" prop="materialName" />
                </el-table>
                <pagination v-show="mtotal > 0" :total="mtotal" :page.sync="mqueryParams.pageNum"
                  :limit.sync="mqueryParams.pageSize" @pagination="getList2" />
                <div slot="footer" class="dialog-footer">
                  <el-button @click="dialogMaterial = false">取消</el-button>
                </div>
              </el-dialog>
            </template>
          </el-table-column>
          <el-table-column label="物料名称" prop="orName" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.orName" placeholder="请输入物料名称" />
            </template>
          </el-table-column>
          <el-table-column label="品类编码" prop="categoryCode" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.categoryCode" placeholder="请输入品类编码" />
              <i class="el-icon-search" id="serachOne1" @click="showMaterial1()"></i>
              <el-dialog :visible.sync="dialogMaterial1" title="品类对象-浏览框" :modal="false">
                <!-- 这里是品类的内容 -->
                <el-table :data="categoryList" v-loading="loading" @row-click="handleRowClickMaterial1">
                  <el-table-column type="selection" width="55" align="center" />
                  <el-table-column label="品类名称" align="center" prop="categoryName" />
                  <el-table-column label="品类代码" align="center" prop="categoryCode" />
                  <el-table-column label="上级品类" align="center" prop="superiorCategory" />
                </el-table>
                <pagination v-show="catotal > 0" :total="catotal" :page.sync="caqueryParams.pageNum"
                  :limit.sync="caqueryParams.pageSize" @pagination="getListCategory" />
                <div slot="footer" class="dialog-footer">
                  <el-button @click="dialogMaterial1 = false">取消</el-button>
                </div>
              </el-dialog>
            </template>
          </el-table-column>
          <el-table-column label="物料品类" prop="materialCategory" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.materialCategory" placeholder="请输入物料品类" />
            </template>
          </el-table-column>
          <el-table-column label="物料规格" prop="materialSpecification" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.materialSpecification" placeholder="请输入物料规格" />
            </template>
          </el-table-column>
          <el-table-column label="物料型号" prop="materialModel" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.materialModel" placeholder="请输入物料型号" />
            </template>
          </el-table-column>
          <el-table-column label="物料单位" prop="materialUnit" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.materialUnit" placeholder="请输入物料单位" />
            </template>
          </el-table-column>
          <el-table-column label="需求数量" prop="requireNumber" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.requireNumber" placeholder="请输入需求数量" />
            </template>
          </el-table-column>
          <el-table-column label="需求日期" prop="requireTime" width="240">
            <template slot-scope="scope">
              <el-date-picker clearable v-model="scope.row.requireTime" type="date" value-format="yyyy-MM-dd"
                placeholder="请选择需求日期" />
            </template>
          </el-table-column>
          <el-table-column label="历史最低价" prop="lowerPrice" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.lowerPrice" placeholder="请输入历史最低价" />
            </template>
          </el-table-column>
          <el-table-column label="最新价格" prop="newPrice" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.newPrice" placeholder="请输入最新价格" />
            </template>
          </el-table-column>
          <el-table-column label="不含税单价" prop="noTaxPrice" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.noTaxPrice" placeholder="请输入不含税单价" />
            </template>
          </el-table-column>
          <el-table-column label="税率代码" prop="taxCode" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.taxCode" placeholder="请输入税率代码" />
            </template>
          </el-table-column>
          <el-table-column label="税率" prop="tax" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.tax" placeholder="请输入税率" />
            </template>
          </el-table-column>
          <el-table-column label="含税单价" prop="taxPrice" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.taxPrice" placeholder="请输入含税单价" />
            </template>
          </el-table-column>
          <el-table-column label="行含税金额" prop="lineTaxAmount" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.lineTaxAmount" placeholder="请输入行含税金额" />
            </template>
          </el-table-column>
          <el-table-column label="收货人" prop="consignee" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.consignee" placeholder="请输入收货人" />
            </template>
          </el-table-column>
          <el-table-column label="收货电话" prop="receivingPhone" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.receivingPhone" placeholder="请输入收货电话" />
            </template>
          </el-table-column>
          <el-table-column label="收货地址" prop="receivingAddress" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.receivingAddress" placeholder="请输入收货地址" />
            </template>
          </el-table-column>
          <el-table-column label="备注" prop="remarks" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.remarks" placeholder="请输入备注" />
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listManager, getManager, delManager, addManager, updateManager, listSupplier, listMaterial, listOrderMaterial, listCurrency, listCategory } from "@/api/pms/manager";

export default {
  name: "Manager",
  dicts: ['self_pickup', 'order_state', 'order_type', 'order_source', 'procure', 'supplier_invoice', 'invoice_method'],
  data() {
    return {
      headerImages: require('../../../assets/images/order_main_header1.png'),
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      //供应商名称
      supplyName: '',
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      //订单物料明细对比数据
      listOrderMaterial: [],
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 币种表格数据
      currencyList: [],
      // 币种定义查询参数
      cqueryParams: {
        pageNum: 1,
        pageSize: 10,
        currencyCode: null,
        currencyName: null,
      },
      // 币种定义总条数
      ctotal: 0,
      // 采购订单管理表格数据
      managerList: [],
      // 供应商列表表格数据
      detailsList: [],
      // 订单物料明细表格数据
      orderMaterialList: [],
      // 物料基本信息
      materialList: [],
      //供应商总条数
      stotal: 0,
      //物料基本信息总条数
      mtotal: 0,
      //品类对象列表
      categoryList: [],
      // 品类对象查询参数
      caqueryParams: {
        pageNum: 1,
        pageSize: 10,
        categoryCode: null,
        categoryName: null,
        superiorCategory: null,
      },
      //品类对象总数
      catotal: 0,
      //供应商查询参数
      squeryParams: {
        pageNum: 1,
        pageSize: 10,
        sdCode: null,
        sbiName: null,
      },
      //物料基本信息查询参数
      mqueryParams: {
        pageNum: 1,
        pageSize: 10,
        materialCode: null,
        materialName: null,
      },
      //添加物料基本信息
      materialInfo: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 币种定义名称
      currencyName: '',
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderCode: null,
        orderSource: null,
        orderState: null,
        supplier: null,
      },
      // 表单参数
      form: {
      },
      // 表单校验
      rules: {
      },
      dialogVisible: false, // 用于标记供应商弹窗是否可见
      dialogVisible1: false, // 用于标记供应商弹窗是否可见
      dialogMaterial: false, //用于标记物料信息表是否可见
      dialogCurrency: false, //用于标记物料信息表是否可见
      dialogMaterial1: false
    };
  },
  created() {
    this.getList();
    this.getlistOrderMaterials();
  },
  computed: {
    totalDemand() {
      let sum = 0;
      this.orderMaterialList.forEach(item => {
        if (item.requireNumber !== '') {
          sum += parseFloat(item.requireNumber);
        }
      });
      const total = isNaN(sum) ? 0.00 : sum.toFixed(2);
      this.form.totalDemand = total; // 将计算得到的总价赋值给 form.totalDemand
      return total;
    },
    isSelfPickupSelected() {
      return this.form.isSelfPickup === 1; // 根据选择的值判断是否自提被选中
    }
  },
  methods: {
    /** 查询采购订单管理列表 */
    getList() {
      this.loading = true;
      listManager(this.queryParams).then(response => {
        this.managerList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询品类对象列表 */
    getListCategory() {
        this.loading = true;
        listCategory(this.caqueryParams).then(response => {
          this.categoryList = response.rows;
          this.catotal = response.total;
          this.loading = false;
        })
    },
    /** 查询币种定义列表 */
    getListCurrency() {
      this.loading = true;
      listCurrency(this.cqueryParams).then(response => {
        // 过滤出状态为1的元素并重新赋值给currencyList
        this.currencyList = response.rows;
        this.ctotal = response.total;
        this.loading = false;
      });
    },
    /**
     * 订单物料明细查询
     */
    getlistOrderMaterials() {
      this.loading = true;
      listOrderMaterial().then(response => {
        this.listOrderMaterial = response.rows;
        this.loading = false;
      });
    },
    /**
     * 查询orName物料名称
     * @param {*} orderMaterialList 
     */
    getFormattedMaterialName(row) {
      if (!row.orderCode || typeof row.orderCode !== 'string') {
        return '';
      }
      const names = [];
      for (let i = 0; i < this.listOrderMaterial.length; i++) {
        const innerElement = this.listOrderMaterial[i];
        if (innerElement.orderCode === row.orderCode) {
          names.push(innerElement.orName)
        }
      }
      return names.join(' ');
    },


    /**
     * 供应商列表
     */
    getList1() {
      this.loading = true;
      listSupplier(this.squeryParams).then(response => {
        this.detailsList = response.rows;
        this.stotal = response.total;
        this.loading = false;
      });
    },
    /**
     * 物料信息列表
     */
    getList2() {
      this.loading = true;
      listMaterial(this.mqueryParams).then(response => {
        this.materialList = response.rows;
        this.mtotal = response.total;
        this.loading = false;
      });
    },
    showDiagSupplie() {
      this.dialogVisible = true;
      this.getList1();
    },
    showDiagSupplie1() {
      this.dialogVisible1 = true;
      this.getList1();
    },
    showDiagCurrency() {
      this.dialogCurrency = true;
      this.getListCurrency();
    },
    showMaterial() {
      this.dialogMaterial = true;
      this.getList2();
    },
    showMaterial1() {
      this.dialogMaterial1 = true;
      this.getListCategory();
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        orderId: null,
        orderCode: null,
        company: null,
        orderType: null,
        orderSource: null,
        purOrganization: null,
        annex: null,
        isSelfPickup: null,
        licensePlateNumber: null,
        concatInfomation: null,
        contractCode: null,
        contractName: null,
        orderDescription: null,
        totalDemand: null,
        purchaser: null,
        orderState: null,
        orderStatus: null,
        currencyId: null,
        supplier: null,
        contacts: null,
        phone: null,
        invoiceMethod: null,
        createTime: null,
        materialId: null
      };
      this.orderMaterialList = [];
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 币种定义搜索按钮操作 */
    handleQuery2() {
      this.cqueryParams.pageNum = 1;
      this.getListCurrency();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 币种定义重置按钮操作 */
    resetQuery() {
      this.resetForm("cqueryForm");
      this.handleQuery2();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.orderId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    handleSelectionChangeCurrency(row) {
      this.form.currencyId = row.currencyId;
      for (let i = 0; i < this.currencyList.length; i++) {
        const innerElement = this.currencyList[i];
        if (innerElement.currencyId === row.currencyId) {
          this.currencyName = innerElement.currencyName
        }
      }
      this.dialogCurrency = false; // 关闭对话框
    },
    handleSelectionChangeCategory(row) {

    },
    handleRowClick1(row) {
      this.form.supplier = row.sdId; // 将供应商名称填充到输入框中
      for (let i = 0; i < this.detailsList.length; i++) {
        const innerElement = this.detailsList[i];
        if (innerElement.sdId === row.sdId) {
          this.supplyName = innerElement.sbiName
        }
      }
      this.dialogVisible1 = false; // 关闭对话框
      this.handleQuery();
    },
    //点击供应商列表列数据
    handleRowClick(row) {
      this.squeryParams.sbiName = row.sbiName; // 将供应商名称填充到输入框中
      this.dialogVisible = false; // 关闭对话框
      this.handleQuery();
    },
    handleRowClickMaterial(row) {
      const data = {
        orName: row.materialName,
        orCode: row.materialCode,
        materialModel: row.model,
        materialSpecification: row.specifications,
        materialUnit: row.calculationUnit,
        materialCategory: row.mCategory,
        // tax: row.tax,
      };
      let index = this.orderMaterialList.length;
      if (this.orderMaterialList.length === 1) {
        // 修改第一条数据的属性值
        this.orderMaterialList[0].orCode = row.materialCode;
        this.orderMaterialList[0].orName = row.materialName;
        this.orderMaterialList[0].categoryCode = row.categoryName;
        this.orderMaterialList[0].materialCategory = row.categoryName;
        this.orderMaterialList[0].materialSpecification = row.specifications;
        this.orderMaterialList[0].materialModel = row.model;
        this.orderMaterialList[0].materialUnit = row.calculationUnit;
      } else {
        this.orderMaterialList[index - 1].orCode = row.materialCode;
        this.orderMaterialList[index - 1].orName = row.materialName;
        this.orderMaterialList[index - 1].categoryCode = row.mCategory;
        this.orderMaterialList[index - 1].materialCategory = row.mCategory;
        this.orderMaterialList[index - 1].materialSpecification = row.specifications;
        this.orderMaterialList[index - 1].materialModel = row.model;
        this.orderMaterialList[index - 1].materialUnit = row.calculationUnit;
      }
      this.dialogMaterial = false; // 关闭对话框
    },
    handleRowClickMaterial1(row) {
      let index = this.orderMaterialList.length;
      if (this.orderMaterialList.length === 1) {
        // 修改第一条数据的属性值
        this.orderMaterialList[0].categoryCode = row.categoryName;
        this.orderMaterialList[0].materialCategory = row.categoryName;
      } else {
        this.orderMaterialList[0].categoryCode = row.categoryName;
        this.orderMaterialList[0].materialCategory = row.categoryName;
      }
      this.dialogMaterial1 = false; // 关闭对话框
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加采购订单管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      for (let i = 0; i < this.listOrderMaterial.length; i++) {
        const innerElement = this.listOrderMaterial[i];
        if (innerElement.orderCode === row.orderCode) {
          this.orderMaterialList[i] = this.listOrderMaterial[i];
        }
      }
      const orderId = row.orderId || this.ids
      getManager(orderId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改采购订单管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.orderMaterialList = this.orderMaterialList;
          if (this.form.orderId != null) {
            updateManager(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addManager(this.form).then(response => {
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
      const orderIds = row.orderId || this.ids;
      this.$modal.confirm('是否确认删除采购订单管理编号为"' + orderIds + '"的数据项？').then(function () {
        return delManager(orderIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 订单物料明细序号 */
    rowOrderMaterialIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 订单物料明细添加按钮操作 */
    handleAddOrderMaterial() {
      let obj = {};
      obj.orCode = "";
      obj.orName = "";
      obj.categoryCode = "";
      obj.materialCategory = "";
      obj.materialSpecification = "";
      obj.materialModel = "";
      obj.materialUnit = "";
      obj.requireNumber = "";
      obj.requireTime = "";
      obj.lowerPrice = "";
      obj.newPrice = "";
      obj.noTaxPrice = "";
      obj.taxCode = "";
      obj.tax = "";
      obj.taxPrice = "";
      obj.lineTaxAmount = "";
      obj.consignee = "";
      obj.receivingPhone = "";
      obj.receivingAddress = "";
      obj.remarks = "";
      this.orderMaterialList.push(obj);
    },
    /** 订单物料明细删除按钮操作 */
    handleDeleteOrderMaterial() {
      if (this.checkedOrderMaterial.length == 0) {
        this.$modal.msgError("请先选择要删除的订单物料明细数据");
      } else {
        const orderMaterialList = this.orderMaterialList;
        const checkedOrderMaterial = this.checkedOrderMaterial;
        this.orderMaterialList = orderMaterialList.filter(function (item) {
          return checkedOrderMaterial.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleOrderMaterialSelectionChange(selection) {
      this.checkedOrderMaterial = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('pms/manager/export', {
        ...this.queryParams
      }, `manager_${new Date().getTime()}.xlsx`)
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
</style>
