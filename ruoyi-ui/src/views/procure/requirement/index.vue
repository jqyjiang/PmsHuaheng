<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="需求编号" prop="requirementCode">
        <el-input
          v-model="queryParams.requirementCode"
          placeholder="请输入需求编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="需求创建人" prop="requirementCreator">
        <el-input
          v-model="queryParams.requirementCreator"
          placeholder="请输入需求创建人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="需求部门" prop="demandDepartment">
        <el-input
          v-model="queryParams.demandDepartment"
          placeholder="请输入需求部门"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- icon="el-icon-plus" -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          size="mini"
          @click="handleAdd"
          v-hasPermi="['procure:requirement:add']"
        >需求申请
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
          v-hasPermi="['procure:requirement:remove']"
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
          v-hasPermi="['procure:requirement:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="requirementList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="需求编号" align="center" prop="requirementCode"/>
      <el-table-column label="需求创建人" align="center" prop="requirementCreator"/>
      <el-table-column label="需求标题" align="center" prop="requirementTitle"/>
      <el-table-column label="创建部门" align="center" prop="createDepartment"/>
      <el-table-column label="创建日期" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="需求人" align="center" prop="demander"/>
      <el-table-column label="需求部门" align="center" prop="demandDepartment"/>
      <el-table-column label="需求类型" align="center" prop="requirementType">
        <template slot-scope="scope">
          <el-tag :type="getTagType(scope.row.requirementType)">
            {{ getRequirementTypeName(scope.row.requirementType) }}
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

    <!-- 添加或修改采购需求申请对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1050px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="需求创建人" prop="requirementCreator">
          <el-input v-model="form.requirementCreator" placeholder="请输入需求创建人" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="需求标题" prop="requirementTitle"
                      style="float: right;margin-left: 540px;margin-top: -59px">
          <el-input v-model="form.requirementTitle" readonly placeholder="请输入需求标题" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="需求部门" prop="demandDepartment">
          <el-input v-model="form.demandDepartment" placeholder="请输入需求部门" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="需求人" prop="demander" style="float: right;margin-left: 540px;margin-top: -59px">
          <el-input v-model="form.demander" placeholder="请输入需求人" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="公司" prop="companiesId">
          <el-input v-model="companiesName" readonly placeholder="请输入公司" style="width: 300px"/>
          <i class="el-icon-search" id="serachOne2" @click="showCompanies"></i>
          <el-dialog :visible.sync="dialogCompanies" title="公司" :modal="false">
            <el-form :model="queryParams" ref="cqueryForm" size="small" :inline="true" v-show="showSearch"
                     label-width="68px">
              <el-form-item label="企业名称" prop="currencyCode">
                <el-input v-model="comqueryParams.companiesName" placeholder="请输入企业名称" clearable
                          @keyup.enter.native="handleQuery3"/>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery3">搜索</el-button>
              </el-form-item>
            </el-form>
            <el-table v-loading="loading" :data="companiesList" @row-click="handleSelectionChangeCompanies">
              <el-table-column type="selection" width="55" align="center"/>
              <el-table-column label="企业名称" align="center" prop="companiesName"/>
              <el-table-column label="公司编码" align="center" prop="companiesCode"/>
            </el-table>
            <pagination v-show="ctotal > 0" :total="ctotal" :page.sync="comqueryParams.pageNum"
                        :limit.sync="comqueryParams.pageSize" @pagination="getListCompanies"/>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogCompanies = false">取消</el-button>
            </div>
          </el-dialog>
        </el-form-item>
        <el-form-item label="需求类型" prop="requirementType" style="float: right;margin-left: 540px;margin-top: -59px">
          <el-select v-model="form.requirementType" placeholder="请输入需求类型" style="width: 300px">
            <el-option v-for="dict in dict.type.requirement_table" :key="dict.value" :label="dict.label"
                       :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="预估总金额" prop="totalAmount">
          <el-input v-model="form.totalAmount" placeholder="请输入预估总金额" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="收货联系人" prop="contactPerson" style="float: right;margin-left: 540px;margin-top: -59px">
          <el-input v-model="form.contactPerson" placeholder="请输入收货联系人" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="创建部门" prop="createDepartment">
          <el-input v-model="form.createDepartment" placeholder="请输入创建部门" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="费用部门" prop="expenseDepartment"
                      style="float: right;margin-left: 540px;margin-top: -59px">
          <el-input v-model="form.expenseDepartment" placeholder="请输入费用部门" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="主要用途及说明" prop="description">
          <el-input v-model="form.description" placeholder="请输入主要用途及说明" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="地址" prop="address" style="float: right;margin-left: 540px;margin-top: -59px">
          <el-input v-model="form.address" placeholder="请输入地址" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="联系方式" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入联系方式" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="附件" prop="annex">
          <file-upload v-model="form.annex"/>
        </el-form-item>
        <el-form-item label="相关项目" prop="annex" style="float: right;margin-left: 540px;margin-top: -164px">
          <el-input v-model="form.relatedProjects" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="币种" prop="currencyId" style="float: right;margin-left: 540px;margin-top: -59px">
          <el-input v-model="currencyName" readonly placeholder="请输入币种" style="width: 300px"/>
          <i class="el-icon-search" id="serachOne" @click="showDiagCurrency"></i>
          <el-dialog :visible.sync="dialogCurrency" title="币种定义" :modal="false">
            <el-form :model="cqueryParams" ref="cqueryForm" size="small" :inline="true" v-show="showSearch"
                     label-width="68px">
              <el-form-item label="币种编码" prop="currencyCode">
                <el-input v-model="cqueryParams.currencyCode" placeholder="请输入币种编码" clearable
                          @keyup.enter.native="handleQuery2"/>
              </el-form-item>
              <el-form-item label="币种名称" prop="currencyName">
                <el-input v-model="cqueryParams.currencyName" placeholder="请输入币种名称" clearable
                          @keyup.enter.native="handleQuery2"/>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery2">搜索</el-button>
              </el-form-item>
            </el-form>
            <el-table v-loading="loading" :data="currencyList" @row-click="handleSelectionChangeCurrency">
              <el-table-column type="selection" width="55" align="center"/>
              <!-- <el-table-column label="币种ID" align="center" prop="currencyId" /> -->
              <el-table-column label="币种编码" align="center" prop="currencyCode"/>
              <el-table-column label="币种名称" align="center" prop="currencyName"/>
              <el-table-column label="国家/地区" align="center" prop="countryRegion"/>
              <el-table-column label="财务精度" align="center" prop="financialAccuracy"/>
              <el-table-column label="精度" align="center" prop="accuracy"/>
              <el-table-column label="货币符号" align="center" prop="currencySymbol"/>
            </el-table>
            <pagination v-show="ctotal > 0" :total="ctotal" :page.sync="cqueryParams.pageNum"
                        :limit.sync="cqueryParams.pageSize" @pagination="getListCurrency"/>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogCurrency = false">取消</el-button>
            </div>
          </el-dialog>
        </el-form-item>

        <!--   物料信息    -->
        <el-divider content-position="center">物料明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddMaterial">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteMaterial">删除</el-button>
          </el-col>
        </el-row>
        <el-table @row-click="noDuplication" :data="InfomaterialList" :row-class-name="rowMaterialIndex"
                  @selection-change="handleMaterialSelectionChange" ref="material">
          <el-table-column type="selection" width="50" align="center"/>
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="物料编码" prop="materialCode" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.materialCode" placeholder="请输入物料编码"/>
              <i class="el-icon-search" id="serachOne1" @click="showMaterial()"></i>
              <el-dialog :visible.sync="dialogMaterial" title="物料管理-浏览框" :modal="false">
                <!-- 这里是物料管理的内容 -->
                <el-table v-loading="loading" :data="materialList" @row-click="handleRowClickMaterial">
                  <el-table-column type="selection" width="55" align="center"/>
                  <el-table-column label="物料编码" align="center" prop="materialCode"/>
                  <el-table-column label="物料名称" align="center" prop="materialName"/>
                  <el-table-column label="物料品类" align="center" prop="categoryName"/>
                </el-table>
                <pagination v-show="mtotal > 0" :total="mtotal" :page.sync="ququeryParams.pageNum"
                            :limit.sync="ququeryParams.pageSize" @pagination="getList3"/>
                <div slot="footer" class="dialog-footer">
                  <el-button @click="dialogMaterial = false">取消</el-button>
                </div>
              </el-dialog>
            </template>
          </el-table-column>
          <el-table-column label="物料名称" prop="materialName" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.materialName" placeholder="请输入物料名称"/>
            </template>
          </el-table-column>

          <el-table-column label="物料品类" prop="categoryCode" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.categoryCode" placeholder="请输入品类代码"/>
              <i class="el-icon-search" id="serachOne1" @click="showMaterial1()"></i>
              <el-dialog :visible.sync="dialogMaterial1" title="品类对象-浏览框" :modal="false">
                <!-- 这里是品类的内容 -->
                <el-table :data="categoryList" v-loading="loading" @row-click="handleRowClickMaterial1">
                  <el-table-column type="selection" width="55" align="center"/>
                  <el-table-column label="品类名称" align="center" prop="categoryName"/>
                  <el-table-column label="品类代码" align="center" prop="categoryCode"/>
                  <el-table-column label="上级品类" align="center" prop="superiorCategory"/>
                </el-table>
                <pagination v-show="catotal > 0" :total="catotal" :page.sync="caqueryParams.pageNum"
                            :limit.sync="caqueryParams.pageSize" @pagination="getListCategory"/>
                <div slot="footer" class="dialog-footer">
                  <el-button @click="dialogMaterial1 = false">取消</el-button>
                </div>
              </el-dialog>
            </template>
          </el-table-column>

          <el-table-column label="物料品类名称" prop="materialCategory" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.materialCategory" placeholder="请输入品类名称"/>
            </template>
          </el-table-column>
          <el-table-column label="物料规格" prop="materialSpecification" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.materialSpecification" placeholder="请输入规格"/>
            </template>
          </el-table-column>
          <el-table-column label="物料型号" prop="materialModel" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.materialModel" placeholder="请输入型号"/>
            </template>
          </el-table-column>
          <el-table-column label="品牌" prop="materialBrand" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.materialBrand" placeholder="请输入品牌"/>
            </template>
          </el-table-column>
          <el-table-column label="物料单位" prop="materialUnit" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.materialUnit" placeholder="请输入基本计算单位"/>
            </template>
          </el-table-column>
          <el-table-column label="需求数量" prop="mustNumber" width="150">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.mustNumber" controls-position="right"
                               @change="changeInput" :min="0"  readonly></el-input-number>
            </template>
          </el-table-column>
          <el-table-column label="需求日期" prop="mustDate" width="150">
            <template slot-scope="scope">
              <el-date-picker clearable
                              v-model="scope.row.mustDate"
                              type="date"
                              value-format="yyyy-MM-dd hh:mm:ss"
                              placeholder="请选择需求日期">
              </el-date-picker>
            </template>
          </el-table-column>
          <el-table-column label="币种" prop="currencyId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.currencyName" readonly placeholder="请输入币种"/>
              <i class="el-icon-search" id="serachOne1" @click="showDiagCurrency2()"></i>
              <el-dialog :visible.sync="dialogCurrency1" title="币种定义" :modal="false">
                <el-table v-loading="loading" :data="currencyList" @row-click="handleSelectionChangeCurrency2">
                  <el-table-column type="selection" width="55" align="center"/>
                  <el-table-column label="币种编码" align="center" prop="currencyCode"/>
                  <el-table-column label="币种名称" align="center" prop="currencyName"/>
                  <el-table-column label="国家/地区" align="center" prop="countryRegion"/>
                  <el-table-column label="财务精度" align="center" prop="financialAccuracy"/>
                  <el-table-column label="精度" align="center" prop="accuracy"/>
                  <el-table-column label="货币符号" align="center" prop="currencySymbol"/>
                </el-table>
                <pagination v-show="ctotal > 0" :total="ctotal" :page.sync="cqueryParams.pageNum"
                            :limit.sync="cqueryParams.pageSize" @pagination="getListCurrencyB"/>
                <div slot="footer" class="dialog-footer">
                  <el-button @click="dialogCurrency1 = false">取消</el-button>
                </div>
              </el-dialog>
            </template>

          </el-table-column>
          <el-table-column label="参考价格" prop="referencePrice" width="150">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.referencePrice" controls-position="right"
                               @change="changeInput" :min="0" readonly></el-input-number>
            </template>
          </el-table-column>
          <el-table-column label="预算单价(不含税)" prop="unitPrice" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.unitPrice" placeholder=""/>
            </template>
          </el-table-column>
          <el-table-column label="税率" prop="tax" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.tax" placeholder="请输入税率"/>
              <i class="el-icon-search" id="serachOne1" @click="showMaterial2()"></i>
              <el-dialog :visible.sync="dialogMaterial2" title="税率-浏览框" :modal="false">
                <!-- 这里是税率的内容 -->
                <el-table :data="rateList" v-loading="loading" @row-click="handleRowClickMaterial2">
                  <el-table-column type="selection" width="55" align="center"/>
                  <!-- <el-table-column label="税种序号" align="center" prop="taxTypeId" /> -->
                  <el-table-column label="税种代码" align="center" prop="taxCode"/>
                  <el-table-column label="描述" align="center" prop="describes"/>
                  <el-table-column label="税率" align="center" prop="taxRate"/>
                </el-table>
                <pagination v-show="rtotal > 0" :total="rtotal" :page.sync="rqueryParams.pageNum"
                            :limit.sync="rqueryParams.pageSize" @pagination="getListRate"/>
                <div slot="footer" class="dialog-footer">
                  <el-button @click="dialogMaterial2 = false">取消</el-button>
                </div>
              </el-dialog>
            </template>
          </el-table-column>
          <el-table-column label="税率值" prop="rateValue" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.rateValue" placeholder="请输入税率值"/>
            </template>
          </el-table-column>
          <el-table-column label="行预算金额" prop="budgetAmount" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.budgetAmount" placeholder=""/>
            </template>
          </el-table-column>
          <el-table-column label="备注" prop="remarks" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.remarks"/>
            </template>
          </el-table-column>
          <el-table-column label="附件" prop="annex" width="150">
            <file-upload v-model="form.annex"/>
<!--            <template slot-scope="scope">-->
<!--              <el-input v-model="scope.row.annex"/>-->
<!--            </template>-->
          </el-table-column>
          <el-table-column label="采购员" prop="purchaser" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.purchaser" placeholder="请输入采购员"/>
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
import {
  listRequirement,
  getRequirement,
  delRequirement,
  addRequirement,
  updateRequirement,
  listInformation,
  listCurrency,
  listCompanies,
  listMaterial,
  listRate
} from "@/api/procure/requirement";
import {listCategory} from "@/api/pms/manager";

export default {
  name: "Requirement",
  dicts: ['requirement_table'],
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
      // 采购需求申请表格数据
      requirementList: [],
      // 物料表格数据
      InfomaterialList: [],
      //物料基本信息总条数
      mtotal: 0,
      // 物料基本信息
      materialList: [],
      //物料基本信息查询参数
      mqueryParams: {
        pageNum: 1,
        pageSize: 10,
        materialCode: null,
        materialName: null,
      },
      //添加物料基本信息
      materialInfo: [],
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
      // 币种名称定义
      currencyName: '',
      // 公司名称定义
      companiesName: '',
      // 公司表表格数据
      companiesList: [],
      // 查询参数
      comqueryParams: {
        pageNum: 1,
        pageSize: 10,
        companiesName: null,
      },
      // 查询参数
      ququeryParams: {
        pageNum: 1,
        pageSize: 10,
        companiesName: null,
      },
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        requirementCode: null,
        requirementCreator: null,
        requirementTitle: null,
        createDepartment: null,
        createTime: null,
        demander: null,
        demandDepartment: null,
        requirementType: null,
        companiesId: null,
        totalAmount: null,
        contactPerson: null,
        expenseDepartment: null,
        description: null,
        address: null,
        phone: null,
        annex: null,
        currencyId: null,
        relatedProjects: null,
        materialId: null,
        requiredMaterials: null,
        deliveryTime: null,
        status: null,
        mi_id: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        requirementType: [
          {required: true, message: "需求类型不能为空", trigger: "blur"}
        ],
        companiesId: [
          {required: true, message: "公司不能为空", trigger: "blur"}
        ],
        mustNumber: [
          {required: true, message: "需求数量不能为空", trigger: "blur"}
        ],
        referencePrice: [
          {required: true, message: "参考价格不能为空", trigger: "blur"}
        ],
        purchaser: [
          {required: true, message: "采购员不能为空", trigger: "blur"}
        ]
      },
      dialogMaterial: false, //用于标记物料信息表是否可见
      dialogCurrency: false, //用于币种信息是否可见（需求申请）
      dialogCompanies: false, //用于公司信息是否可见
      dialogCurrency1: false, //用于币种信息是否可见（需求申请（物料））
      dialogMaterial1: false, //用于标记物料品类是否可见
      dialogMaterial2: false, //用于标记税率
      // 获取需求物料明细表格下标
      materialIndex: 0,
      //品类对象总数
      catotal: 0,
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
      // 税率表格数据
      rateList: [],
      // 税率数量
      rtotal:0,
      // 查询参数
      rqueryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      currencyName2:null,
    }
  },
  created() {
    this.getList();
    this.getList3();
  },
  computed: {
    getRequirementTypeName() {
      return function (requirementType) {
        switch (requirementType) {
          case 1:
            return '生产性采购';
          case 2:
            return '项目性采购';
          case 3:
            return '服务型采购';
          case 4:
            return '一般用品采购';
        }
      }
    },
    isSelfPickupSelected() {
      return this.form.isSelfPickup === 1; // 根据选择的值判断是否自提被选中
    }
  },
  methods: {
    /** 查询物料维护列表 */
    getList3() {
      this.loading = true;
      listMaterial(this.ququeryParams).then(response => {
        this.materialList = response.rows;
        this.mtotal = response.total;
        this.loading = false;
      });
    },
    /** 查询采购需求申请列表 */
    getList() {
      this.loading = true;
      listRequirement(this.queryParams).then(response => {
        this.requirementList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getTagType(requirementType) {
      if (requirementType == 1) {
        return 'success';
      } else if (requirementType == 2) {
        return 'info';
      } else if (requirementType == 3) {
        return 'warning';
      } else {
        return 'danger';
      }
    },
    /** 查询采购需求池列表 */
    getList2() {
      this.loading = true;
      listInformation(this.mqueryParams).then(response => {
        this.informationList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
    /** 查询币种定义列表 */
    getListCurrencyB() {
      this.loading = true;
      listCurrency(this.cqueryParams).then(response => {
        // 过滤出状态为1的元素并重新赋值给currencyList
        this.currencyList = response.rows;
        this.ctotal = response.total;
        this.loading = false;
      });
    },
    /** 查询公司表列表 */
    getListCompanies() {
      this.loading = true;
      listCompanies(this.queryParams).then(response => {
        this.companiesList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    showMaterial() {
      this.dialogMaterial = true;
      this.getList2();
    },
    // 币种
    showDiagCurrency() {
      this.dialogCurrency = true;
      this.getListCurrency();
    },
    // 币种(物料)
    showDiagCurrency2() {
      this.dialogCurrency1 = true;
      this.getListCurrency();
    },
    // 公司
    showCompanies() {
      this.dialogCompanies = true;
      this.getListCompanies();
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        requirementId: null,
        requirementCode: null,
        requirementCreator: null,
        requirementTitle: null,
        createDepartment: null,
        createTime: null,
        demander: null,
        demandDepartment: null,
        requirementType: null,
        companiesId: null,
        totalAmount: null,
        contactPerson: null,
        expenseDepartment: null,
        description: null,
        address: null,
        phone: null,
        annex: null,
        currencyId: null,
        currencyName: null,
        relatedProjects: null,
        materialId: null,
        requiredMaterials: null,
        deliveryTime: null,
        status: null,
        mi_id: null
      };
      this.InfomaterialList = [];
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
    /** 公司搜索按钮操作 */
    handleQuery3() {
      this.comqueryParams.pageNum = 1;
      this.getListCompanies();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 多选框选中数据 */
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.requirementId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
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
    // 选中币种(物料)
    handleSelectionChangeCurrency2(row) {
      console.log("this.materialIndex:"+this.materialIndex)
      console.log("row:"+row)
      this.InfomaterialList[this.materialIndex].currencyId =  parseInt(row.currencyId);
      this.InfomaterialList[this.materialIndex].currencyName = row.currencyName;
      // this.currencyName2=row.currencyName;
      this.dialogCurrency1 = false; // 关闭对话框
    },
    // 选中公司
    handleSelectionChangeCompanies(row) {
      this.form.companiesId = row.companiesId;
      for (let i = 0; i < this.companiesList.length; i++) {
        const innerElement = this.companiesList[i];
        if (innerElement.companiesId === row.companiesId) {
          this.companiesName = innerElement.companiesName
        }
      }
      this.dialogCompanies = false; // 关闭对话框
    },
    // 物料框架+数据
    handleRowClickMaterial(row) {
      // 修改第一条数据的属性值
      this.InfomaterialList[this.materialIndex].materialCode = row.materialCode;// 物料编码
      this.InfomaterialList[this.materialIndex].materialName = row.materialName;// 物料名称
      this.InfomaterialList[this.materialIndex].materialSpecification = row.specifications; // 物料规格
      this.InfomaterialList[this.materialIndex].materialModel = row.model; // 物料型号
      this.InfomaterialList[this.materialIndex].materialUnit = row.metering_unit; // 物料单位
      this.InfomaterialList[this.materialIndex].materialBrand = row.brand; // 品牌
      this.dialogMaterial = false; // 关闭对话框
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.form.totalAmount = 0.00;
      this.form.demandDepartment = "产品技术部";
      this.form.expenseDepartment = "产品技术部";
      this.open = true;
      this.title = "添加采购需求申请";
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.materialInformations = this.InfomaterialList;
          if (this.form.requirementId != null) {
            updateRequirement(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRequirement(this.form).then(response => {
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
      const requirementIds = row.requirementId || this.ids;
      this.$modal.confirm('是否确认删除采购需求申请编号为"' + requirementIds + '"的数据项？').then(function () {
        return delRequirement(requirementIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 物料序号 */
    rowMaterialIndex({row, rowIndex}) {
      row.index = rowIndex + 1;
    },
    /** 物料添加按钮操作 */
    handleAddMaterial() {
      let obj = {};
      obj.materialCode = "";
      obj.materialName = "";
      obj.purchaser = "";
      obj.specifications = "";
      obj.model = "";
      obj.brand = "";
      obj.categoriesTaxes = "";
      obj.image = "";
      obj.budgetAmount = 0.00;
      obj.mustDate = new Date();
      this.InfomaterialList.push(obj);
    },
    /** 物料删除按钮操作 */
    handleDeleteMaterial() {
      if (this.checkedMaterial.length == 0) {
        this.$modal.msgError("请先选择要删除的物料数据");
      } else {
        const InfomaterialList = this.InfomaterialList;
        const checkedMaterial = this.checkedMaterial;
        this.InfomaterialList = InfomaterialList.filter(function (item) {
          return checkedMaterial.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleMaterialSelectionChange(selection) {
      this.checkedMaterial = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('procure/information/export', {
        ...this.queryParams
      }, `information_${new Date().getTime()}.xlsx`)
    },
    //获取点击行的行索引
    noDuplication(row, event, column) {
      this.materialIndex = this.InfomaterialList.indexOf(row);
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
    // 品类展示
    showMaterial1() {
      this.dialogMaterial1 = true;
      this.getListCategory();
    },
    handleRowClickMaterial1(row) {
      this.InfomaterialList[this.materialIndex].categoryCode = row.categoryName;
      this.InfomaterialList[this.materialIndex].materialCategory = row.categoryName;
      this.dialogMaterial1 = false; // 关闭对话框
    },
    // 税率
    showMaterial2() {
      this.dialogMaterial2 = true;
      this.getListRate();
    },
    handleRowClickMaterial2(row) {
      this.InfomaterialList[this.materialIndex].tax = row.taxCode;
      this.InfomaterialList[this.materialIndex].rateValue = row.taxRate;
      this.dialogMaterial2 = false; // 关闭对话框
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
    //计算小计并累加到合同金额 输入框改变值是触发事件
    changeInput(){
      let total=0
      for (let i = 0; i < this.InfomaterialList.length; i++) {
        var materials=this.InfomaterialList[i]
        // 数量
        let number=materials.mustNumber === ''||materials.mustNumber+""=== 'undefined' ?0:materials.mustNumber
        // 单价
        let price=materials.referencePrice === '' || materials.referencePrice=== null|| materials.referencePrice+""=== 'undefined'?0:materials.referencePrice
        let num=Number(number)*Number(price)
        this.InfomaterialList[i].budgetAmount=num
        total=total+Number(num)
      }
      this.form.totalAmount=total // 预估总金额
    },
  },
}
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
  top: 57.5px;
}

#serachOne2 {
  position: absolute;;
  right: 613px;
  top: 10px;
}
#serachOne3 {
  position: absolute;
  right: 15px;
  top: 21.5px;
}
</style>
