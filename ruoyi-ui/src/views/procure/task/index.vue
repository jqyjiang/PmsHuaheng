<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="任务单号" prop="taskCode">
        <el-input
          v-model="queryParams.taskCode"
          placeholder="请输入任务单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          size="mini"
          :disabled="single"
          @click="handleTransfer"
          v-hasPermi="['procure:task:edit']"
        >受理
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          size="mini"
          :disabled="single"
          @click="handleUpdatePurchaser"
        >转办
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['procure:task:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['procure:task:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="taskList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="任务单号" align="cerequireListnter" prop="taskCode"/>
      <el-table-column label="采购策略" align="center" prop="procurementStrategy"/>
      <el-table-column label="任务总金额" align="center" prop="taskTotal"/>
      <el-table-column label="公司" align="center" prop="companies.companiesName"/>
      <el-table-column label="采购员" align="center" prop="purchaser"/>
      <el-table-column label="物料名称" align="center" prop="descriptionName"/>
      <el-table-column label="受理策略" align="center" prop="acceptanceStrategy"/>
      <el-table-column label="任务总数量" align="center" prop="taskNumber"/>
      <el-table-column label="已受理数量" align="center" prop="acceptedQuantity"/>
      <el-table-column label="占用任务数量" align="center" prop="taskOccupied"/>
      <el-table-column label="待受理数量" align="center" prop="taskAccepted"/>
      <el-table-column label="状态" align="center" prop="taskStatus">
        <template slot-scope="scope">
          <el-tag :type="getTagType(scope.row.taskStatus)">
            {{ getTaskStatusName(scope.row.taskStatus) }}
          </el-tag>
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

    <!-- 受理对话框 -->
    <el-dialog :title="title" :visible.sync="openTransfer" width="1100px" id="dialog_box1" append-to-body>
      <el-divider content-position="center">基本信息</el-divider>
      <el-form ref="form" :model="form" :rules="rules" label-width="130px">
        <el-form-item label="订单号" prop="applicant">
          <el-input v-model="form.orderCode" placeholder="自动生成" disabled style="width: 300px"/>
        </el-form-item>
        <el-form-item label="订单来源" prop="orderSource" style="float: right;margin-left: 540px;margin-top: -59px">
          <el-input v-model="form.orderSource" placeholder="" style="width: 300px;"/>
        </el-form-item>
        <el-form-item label="订单类型" prop="orderType">
          <el-select v-model="form.orderType" placeholder="请选择订单类型" disabled>
            <el-option v-for="dict in dict.type.order_type" :key="dict.value" :label="dict.label"
                       :value="parseInt(dict.value)" ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="公司" prop="companiesId" style="float: right;margin-left: 540px;margin-top: -59px">
          <el-input v-model="form.companiesId" placeholder="请输入公司" style="width: 300px" readonly/>
        </el-form-item>
        <el-form-item label="采购员：" prop="purchaser">
          <el-select v-model="queryParams.purchaser" placeholder="请选择采购员" clearable  style="width: 300px;">
            <el-option
              v-for="dict in dict.type.buyer"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="含税总金额(元)" prop="taxTotal"  style="float: right;margin-left: 540px;margin-top: -59px">
          <el-input :value="isCalculationNeeded ? calculateTotalAmount : (taxTotal ? taxTotal.toFixed(2) : '')" disabled
                    placeholder="请输入含税总金额" style="width: 300px;"/>
        </el-form-item>
        <el-form-item label="币种" prop="currencyId" >
          <el-input v-model="currencyName" readonly  placeholder="请输入币种" style="width: 300px"/>
          <i class="el-icon-search" id="serachOne4" @click="showDiagCurrency"></i>
          <el-dialog :visible.sync="dialogCurrency" title="币种定义" :modal="false">
            <el-table v-loading="loading" :data="currencyList" @row-click="handleSelectionChangeCurrency">
              <el-table-column type="selection" width="55" align="center"/>
              <el-table-column label="币种编码" align="center" prop="currencyCode"/>
              <el-table-column label="币种名称" align="center" prop="currencyName"/>
              <el-table-column label="国家/地区" align="center" prop="countryRegion"/>
              <el-table-column label="财务精度" align="center" prop="financialAccuracy"/>
              <el-table-column label="精度" align="center" prop="accuracy"/>
              <el-table-column label="货币符号" align="center" prop="currencySymbol"/>
            </el-table>
            <pagination v-show="currencytotal > 0" :total="currencytotal" :page.sync="cqueryParams.pageNum"
                        :limit.sync="cqueryParams.pageSize" @pagination="getListCurrency"/>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogCurrency = false">取消</el-button>
            </div>
          </el-dialog>
        </el-form-item>
        <el-form-item label="附件" prop="annex"  style="float: right;margin-left: 540px;margin-top: -59px">
          <file-upload v-model="form.annex"/>
        </el-form-item>
        <el-form-item label="是否自提" prop="isSelfPickup">
          <el-select v-model="form.isSelfPickup" placeholder="请选择是否自提" style="width: 300px;">
            <el-option v-for="dict in dict.type.self_pickup" :key="dict.value" :label="dict.label"
                       :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="自提车牌号" prop="licensePlateNumber"  style="float: right;margin-left: 540px;margin-top: -59px">
          <el-input v-model="form.licensePlateNumber" placeholder="请输入自提车牌号" :disabled="!isSelfPickupSelected" style="width: 300px;"/>
        </el-form-item>
        <el-form-item label="自提司机联系方式" prop="concatInfomation">
          <el-input v-model="form.concatInfomation" placeholder="请输入自提司机联系方式" :disabled="!isSelfPickupSelected" style="width: 300px;"/>
        </el-form-item>
        <el-form-item label="关联合同编号" prop="contractCode"  style="float: right;margin-left: 540px;margin-top: -59px">
          <el-input v-model="form.contractCode" placeholder="请输入关联合同编号" disabled style="width: 300px;"/>
        </el-form-item>
        <el-form-item label="关联合同名称" prop="contractName">
          <el-input v-model="form.contractName" placeholder="请输入关联合同名称" style="width: 300px;"/>
        </el-form-item>
        <el-form-item label="订单说明" prop="orderDescription"  style="float: right;margin-left: 540px;margin-top: -59px">
          <el-input v-model="form.orderDescription" placeholder="请输入订单说明" style="width: 300px;"/>
        </el-form-item>
        <el-form-item label="需求总数量" prop="totalDemand">
          <el-input :value="isCalculationNeeded ? totalDemands : (number ? number.toFixed(2) : '')" disabled
                    placeholder="请输入需求总数量" style="width: 300px;"/>
        </el-form-item>
        <el-divider content-position="center">供货方</el-divider>
        <el-form-item label="供应商名称" prop="supplier">
          <el-input v-model="supplyName" placeholder="请输入供应商名称" style="width: 300px;"/>
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
        <el-form-item label="联系人" prop="contacts"  style="float: right;margin-left: 540px;margin-top: -59px">
          <el-input v-model="form.contacts" placeholder="请输入联系人" style="width: 300px;"/>
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入联系电话" style="width: 300px;"/>
        </el-form-item>
        <el-form-item label="供应商发票方式" prop="invoiceMethod"  style="float: right;margin-left: 540px;margin-top: -59px">
          <el-select v-model="form.invoiceMethod" placeholder="请选择供应商发票方式" style="width: 300px;">
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
                  @selection-change="handleOrderMaterialSelectionChange" ref="orderMaterial" @row-click="clickRowMaterial">
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
              <el-input v-model="scope.row.newPrice" placeholder="请输入最新价格" @change="changeInput"/>
            </template>
          </el-table-column>
          <el-table-column label="不含税单价" prop="noTaxPrice" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.noTaxPrice" placeholder="请输入不含税单价" />
            </template>
          </el-table-column>
          <el-table-column label="税率代码" prop="taxCode" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.taxCode" readonly placeholder="请输入税率代码" />
              <i class="el-icon-search" id="serachOne1" @click="showMaterial2()"></i>
              <el-dialog :visible.sync="dialogMaterial2" title="税率-浏览框" :modal="false">
                <!-- 这里是税种的内容 -->
                <el-table :data="rateList" v-loading="loading" @row-click="handleRowClickMaterial2">
                  <el-table-column type="selection" width="55" align="center" />
                  <el-table-column label="税种代码" align="center" prop="taxCode" />
                  <el-table-column label="描述" align="center" prop="describes" />
                  <el-table-column label="税率" align="center" prop="taxRate" :formatter="formatTaxRate" />
                  <el-table-column label="是否启用" align="center" prop="enable"></el-table-column>
                </el-table>
                <pagination v-show="rtotal > 0" :total="rtotal" :page.sync="rqueryParams.pageNum"
                            :limit.sync="rqueryParams.pageSize" @pagination="getListRate" />
                <div slot="footer" class="dialog-footer">
                  <el-button @click="dialogMaterial2 = false">取消</el-button>
                </div>
              </el-dialog>
            </template>
          </el-table-column>
          <el-table-column label="税率" readonly prop="tax" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.tax" placeholder="请输入税率"  @change="changeInput"/>
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
        <el-button type="primary" @click="submitFormOrder">确 定</el-button>
        <el-button @click="cancelTransfer">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 转办对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" id="dialog_box" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="负责采购人：" prop="purchaser">
          <el-select v-model="queryParams.purchaser" placeholder="请选择采购员" clearable >
            <el-option
              v-for="dict in dict.type.buyer"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormPurchaser">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import {listTask, getTask, delTask, addTask, updateTask, updateTaskPurchaser,addOrderManger,editOrAdd} from "@/api/procure/task";
import {addRequirement, listCurrency, updateRequirement} from "@/api/procure/requirement";
import {addManager, listMaterial, listRate, listSupplier} from "@/api/pms/manager";

export default {
  name: "Task",
  dicts: ['acceptance_strategy','buyer','self_pickup','invoice_method', 'order_type'],
  data() {
    return {
      invoiceOrderMaterialList: [],//显示新建送货单的物料基本信息
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
      // 我的需求任务表格数据
      taskList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层转办)
      open: false,
      status:0,
      occupied:0,
      totalss:0,
      accepted:0,
      // 受理
      openTransfer:false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        taskCode: null,
        procurementStrategy: null,
        taskTotal: null,
        requirementId: null,
        companiesId: null,
        purchaser: null,
        materialId: null,
        currencyId: null,
        acceptanceStrategy: null,
        taskNumber: null,
        acceptedQuantity: null,
        taskOccupied: null,
        taskAccepted: null,
        mergeOrders: null,
        taskStatus: null
      },
      // 币种表格数据
      currencyList: [],
      // 币种定义总条数
      currencytotal: 0,
      // 币种名称定义
      currencyName: '',
      cqueryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      //控制是否启用计算属性计算需求总数量和含税金额
      isCalculationNeeded: true,
      //需求总数修改
      number: 0,
      //含税总金额
      taxTotal: 0,
      //供应商名称
      supplyName: '',
      // 供应商列表表格数据
      detailsList: [],
      //供应商总条数
      stotal: 0,
      //供应商查询参数
      squeryParams: {
        pageNum: 1,
        pageSize: 10,
        sdCode: null,
        sbiName: null,
        phone: '',
        people: '',
      },
      // 订单物料明细表格数据
      orderMaterialList: [],
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
      //物料基本信息查询参数
      mqueryParams: {
        pageNum: 1,
        pageSize: 10,
        materialCode: null,
        materialName: null,
      },
      // 物料基本信息
      materialList: [],
      // 税率表格数据
      rateList: [],
      // 查询参数
      rqueryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      //选中税率数组
      rateId: [],
      rtotal: 0,
      // 表单参数
      form: {},
      forms: {
        batchRequireTime: '',
        batchRequireNumber: '',
        batchConsignee: '',
        batchReceivingAddress: '',
        batchReceivingPhone: ''
      },
      // 表单校验
      rules: {},
      dialogCurrency: false, //用于币种信息是否可见（需求申请）
      dialogVisible: false, // 用于标记供应商弹窗是否可见
      dialogVisible1: false, // 用于标记供应商弹窗是否可见
      dialogMaterial: false, //用于标记物料信息表是否可见
      dialogMaterial1: false,
      dialogMaterial2: false,
      // 批量新增对话框的可见性
      batchAddDialogVisible: false,
      // 批量新增数据列表
      batchAddDataList: [],
      requireList:[],
    };
  },
  created() {
    this.getList();
  },
  computed: {
    getTaskStatusName() {
      return function (taskStatus) {
        switch (taskStatus) {
          case 1:
            return '待受理';
          case 2:
            return '受理完成';
          case 3:
            return '关闭';
          case 4:
            return '不展示';
        }
      }
    },
    //计算总需求量
    totalDemands() {
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
    //计算税后总金额
    calculateTotalAmount() {
      let sum = 0;
      for (let i = 0; i < this.orderMaterialList.length; i++) {
        const item = this.orderMaterialList[i];
        if (item.tax !== '' && item.tax !== 0) {
          const basePrice = parseFloat(item.noTaxPrice);
          const taxRate = parseFloat(item.tax);
          if (taxRate !== 0) { // 如果税率不为0，则继续计算
            const taxIncludedPrice = basePrice * (1 + taxRate*0.01);
            const requireNumber = parseFloat(item.requireNumber);
            const afterTaxAmount = taxIncludedPrice * requireNumber;
            sum += afterTaxAmount;
          } else {//这是税率为0的情况  用不含税单价乘以需求数量即可
            const requireNumber = parseFloat(item.requireNumber);
            const basePrice = parseFloat(item.noTaxPrice);
            const afterTaxAmount = basePrice * requireNumber;
            sum += afterTaxAmount;
          }
        }
      }
      const totalAfterTax = isNaN(sum) ? 0.00 : sum.toFixed(2);
      this.form.taxTotal = totalAfterTax; // 将总税后金额赋值给 this.form.taxTotal
      return totalAfterTax;
    },
    isSelfPickupSelected() {
      return this.form.isSelfPickup === 1; // 根据选择的值判断是否自提被选中
    }
  },
    methods: {
      /** 状态 */
      getTagType(taskStatus) {
        if (taskStatus == 1) {
          return 'success';
        } else if (taskStatus == 2) {
          return 'info';
        } else if (taskStatus == 3) {
          return 'warning';
        } else {
          return 'danger';
        }
      },
      /** 查询我的需求任务列表 */
      getList() {
        this.loading = true;
        console.log(this.queryParams);
        listTask(this.queryParams).then(response => {
          this.taskList = response.rows;
          console.log(this.taskList)
          this.total = response.total;
          this.loading = false;
        });
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 受理
      cancelTransfer() {
        this.openTransfer = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          taskId: null,
          taskCode: null,
          procurementStrategy: null,
          taskTotal: null,
          requirementId: null,
          companiesId: null,
          purchaser: null,
          materialId: null,
          currencyName: null,
          acceptanceStrategy: null,
          taskNumber: null,
          acceptedQuantity: null,
          taskOccupied: null,
          taskAccepted: null,
          mergeOrders: null,
          taskStatus: null
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
        console.log(selection)
        this.ids = selection.map(item => item.taskId)
        this.single = selection.length !== 1
        this.multiple = !selection.length
        this.status = selection.map(item => item.taskStatus)
        this.accepted = selection.map(item => item.taskAccepted)
        this.occupied = selection.map(item => item.taskOccupied)
        this.numbers = selection.map(item => item.taskNumber)
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "受理";
      },
      /** 受理按钮操作 */
      handleTransfer(row) {
        const zt=this.accepted
        console.log(zt)
        if (zt === 0.00 || zt === null || zt === ''){
          this.$notify({
            title: '警告',
            message: '已受理，请重新选择',
            type: 'warning'
          });
        }else {
          this.reset();
          const taskId = row.taskId || this.ids
          console.log(taskId)
          getTask(taskId).then(response => {
            this.form = response.data;
            console.log(response.data)
            this.orderMaterialList=[]
            for (let i=0;i<this.materialList.length;i++){
              const innerElement = this.materialList[i];
              if (innerElement.orderCode === response.data.purchasingList) {
                this.orderMaterialList.push(innerElement)
              }
            }
            this.openTransfer = true;
            this.title = "受理";
            this.form.orderSource='采购需求';
            this.form.orderType=2;
            this.form.companiesId=response.data.companies.companiesName;
            this.form.purchaser=response.data.purchaser;
            this.form.company=response.data.companiesId;
          });
        }

      },
      /** 转办按钮操作 */
      handleUpdatePurchaser(row) {
        this.reset();
        const taskId = row.taskId || this.ids
        getTask(taskId).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "需求分配";
        });
      },
      /** 受理提交按钮 */
      submitFormOrder() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            // 任务总数量    占用任务数量=需求数量   待受理数量=总数量-已受理数量   已受理数量=待受理数量-需求数量
            const taskId = this.ids
            const taskAccepted=this.accepted // 待受理数量
            const number=this.form.totalDemand  // 需求数量
            console.log("1:"+number)
            const occupied= this.occupied // 占用任务数量
            const total= this.numbers // 总数量
            //console.log(total-taskAccepted)
            // 已受理数量
            const acceptedQuantity= Number(taskAccepted)-Number(number)
            console.log("2:"+acceptedQuantity)
            const allNumber=Number(total)-Number(acceptedQuantity)
            console.log("3:"+allNumber)
              editOrAdd(number,acceptedQuantity,allNumber,taskId);
              addOrderManger(this.form).then(response => {
                this.$modal.msgSuccess("生成采购订单成功");
                this.queryParams.purchaser = null;
                this.getList();
                this.openTransfer = false;
              });
            }
        });
      },
      /** 转办按钮 */
      submitFormPurchaser() {
        const taskIds = this.ids;
        console.log("taskIds:" + taskIds)
        const selectedValue = this.queryParams.purchaser;
        const purchaser = this.dict.type.buyer.find(dict => dict.value === selectedValue)?.label;
        console.log("purchaser:" + purchaser)
        this.$modal.confirm('是否确认转办编号为"' + taskIds + '"的数据项？').then(function () {
          return updateTaskPurchaser(purchaser, taskIds);
        }).then(() => {
          this.queryParams.purchaser = null;
          this.getList();
          this.$modal.msgSuccess("转办成功");
          this.open = false;
        }).catch(() => {
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const taskIds = row.taskId || this.ids;
        this.$modal.confirm('是否确认删除我的需求任务编号为"' + taskIds + '"的数据项？').then(function () {
          return delTask(taskIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {
        });
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('procure/task/export', {
          ...this.queryParams
        }, `task_${new Date().getTime()}.xlsx`)
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
      // 币种
      showDiagCurrency() {
        this.dialogCurrency = true;
        this.getListCurrency();
      },
      // 选中币种
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
      // 展示供应商
      showDiagSupplie1() {
        this.dialogVisible1 = true;
        this.getList1();
      },
      // 选中供应商
      handleRowClick1(row) {
        this.form.supplier = row.sdId; // 将供应商名称填充到输入框中
        for (let i = 0; i < this.detailsList.length; i++) {
          const innerElement = this.detailsList[i];
          if (innerElement.sdId === row.sdId) {
            this.supplyName = innerElement.sbiName
            this.form.phone = innerElement.sdPcp
            this.form.contacts = innerElement.sdPcn
          }
        }
        this.dialogVisible1 = false; // 关闭对话框
      },
      /** 订单物料明细添加按钮操作 */
      handleAddOrderMaterial() {
        let obj = {};
        obj.lineNumber = "",
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
        if (this.checkedOrderMaterials.length == 0) {
          console.log('123')
          this.$modal.msgError("请先选择要删除的订单物料明细数据");
        } else {
          const orderMaterialList = this.orderMaterialList;
          const checkedOrderMaterial = this.checkedOrderMaterials;
          this.orderMaterialList = orderMaterialList.filter(function (item) {
            return checkedOrderMaterial.indexOf(item.index) == -1
          });
        }
      },
      // 显示批量新增对话框
      showBatchAddDialog() {
        this.batchAddDialogVisible = true;
      },
      /** 订单物料明细序号 */
      rowOrderMaterialIndex({row, rowIndex}) {
        row.index = rowIndex + 1;
        row.lineNumber = row.index
      },
      /** 物料明细复选框选中数据 */
      handleOrderMaterialSelectionChange(selection) {
        let checkedOrderMaterial = selection.map(item => item)
        this.checkedOrderMaterials = checkedOrderMaterial
      },
      clickRowMaterial(row, event, column) {
        this.materialIndex = this.orderMaterialList.indexOf(row); // 获取点击的行索引
      },
      showMaterial() {
        this.dialogMaterial = true;
        this.getList2();
      },
      showMaterial1() {
        this.dialogMaterial1 = true;
        this.getListCategory();
      },
      showMaterial2() {
        this.dialogMaterial2 = true;
        this.getListRate();
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
      handleRowClickMaterial1(row) {
        // 修改第某一条数据的属性值
        this.orderMaterialList[this.materialIndex].categoryCode = row.categoryName;
        this.orderMaterialList[this.materialIndex].materialCategory = row.categoryName;
        this.dialogMaterial1 = false; // 关闭对话框
      },
      handleRowClickMaterial2(row) {
        // 修改某一条数据的属性值
        this.orderMaterialList[this.materialIndex].taxCode = row.taxCode;
        this.orderMaterialList[this.materialIndex].tax = row.taxRate;
        this.dialogMaterial2 = false; // 关闭对话框
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
      formatTaxRate(row, column) {
        const value = row[column.property];
        if (value === null || value === undefined) {
          return ''; // 处理空值情况
        }
        return parseFloat(value).toFixed(2); // 使用 toFixed 方法保留两位小数
      },
      /** 查询税率对象列表 */
      getListRate() {
        this.loading = true;
        listRate(this.rqueryParams).then(response => {
          this.rateList = response.rows;
          this.rtotal = response.total;
          this.loading = false;
        })
      },
      // 取消批量新增
      cancelBatchAdd() {
        this.batchAddDataList = []; // 清空批量新增数据列表
        this.batchAddDialogVisible = false;
      },
      // 执行批量新增
      doBatchAdd() {
        if (this.checkedOrderMaterials.length === 0) {
          this.$message.warning('请至少选择一条记录');
          return;
        }
      },
      /**
       * 为物料编码等赋值
       * @param {*} row
       */
      handleRowClickMaterial(row) {
        // 修改某一条数据的属性值
        this.orderMaterialList[this.materialIndex].orCode = row.materialCode;
        this.orderMaterialList[this.materialIndex].orName = row.materialName;
        this.orderMaterialList[this.materialIndex].categoryCode = row.categoryName;
        this.orderMaterialList[this.materialIndex].materialCategory = row.categoryName;
        this.orderMaterialList[this.materialIndex].materialSpecification = row.specifications;
        this.orderMaterialList[this.materialIndex].materialModel = row.model;
        this.orderMaterialList[this.materialIndex].materialUnit = row.metering_unit;
        this.dialogMaterial = false; // 关闭对话框
      },
      //计算小计并累加到合同金额 输入框改变值是触发事件
      changeInput(){
        let total=0;
        for (let i = 0; i < this.orderMaterialList.length; i++) {
          var materials=this.orderMaterialList[i]
          let taxValue=materials.tax
          // 数量
          let number=materials.requireNumber === ''||materials.requireNumber+""=== 'undefined' ?0:materials.requireNumber
          // 单价
          let price=materials.newPrice === '' || materials.newPrice=== null|| materials.newPrice+""=== 'undefined'?0:materials.newPrice
          console.log(taxValue)
          let num=Number(number)*Number(price)
          let rateValue=Number(price)*Number(1+taxValue)
          // 行含税金额
          let colHaveTax=Number(num)*Number(1+taxValue);
          this.orderMaterialList[i].noTaxPrice=price // 不含税单价
          this.orderMaterialList[i].taxPrice=rateValue // 含税单价
          this.orderMaterialList[i].lineTaxAmount=colHaveTax // 含税单价
          total=total+Number(num)
        }
        this.form.totalAmount=total // 预估总金额
      },
    }
};
</script>
<style>
#dialog_box{
  margin-top: 300px;
}
#dialog_box1{
  margin-top: 50px;
}
#serachOne1 {
  position: absolute;
  right: 15px;
  top: 21.5px;
}

#serachOne4 {
  position: absolute;
  right: 656px;
  top: 10px;
}
#serachOne {
  position: absolute;
  right: 656px;
  top: 10px;
}
</style>
