<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="采购订单号" prop="purchasingCode">
        <el-input
          v-model="queryParams.purchasingCode"
          placeholder="请输入采购订单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目相关合同" prop="projectRelatedContracts">
        <el-input
          v-model="queryParams.projectRelatedContracts"
          placeholder="请输入项目相关合同"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申请人" prop="applicant">
        <el-input
          v-model="queryParams.applicant"
          placeholder="请输入申请人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申请日期" prop="applicationDate">
        <el-date-picker clearable
          v-model="queryParams.applicationDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择申请日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="申请部门" prop="departmentId">
        <el-input
          v-model="queryParams.departmentId"
          placeholder="请输入申请部门"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申请公司" prop="companiesId">
        <el-input
          v-model="queryParams.companiesId"
          placeholder="请输入申请公司"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="相关项目" prop="relatedProjects">
        <el-input
          v-model="queryParams.relatedProjects"
          placeholder="请输入相关项目"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否主子合同" prop="isMainContract">
        <el-input
          v-model="queryParams.isMainContract"
          placeholder="请输入是否主子合同"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="关联主合同" prop="relatedMainContract">
        <el-input
          v-model="queryParams.relatedMainContract"
          placeholder="请输入关联主合同"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预算相关合同" prop="budgetRelatedContracts">
        <el-input
          v-model="queryParams.budgetRelatedContracts"
          placeholder="请输入预算相关合同"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="成本中心" prop="costCenter">
        <el-input
          v-model="queryParams.costCenter"
          placeholder="请输入成本中心"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="负责人" prop="head">
        <el-input
          v-model="queryParams.head"
          placeholder="请输入负责人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="签订日期" prop="signingDate">
        <el-date-picker clearable
          v-model="queryParams.signingDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择签订日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="合同总金额" prop="totalAmount">
        <el-input
          v-model="queryParams.totalAmount"
          placeholder="请输入合同总金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="付款比例(%)" prop="paymentProportion">
        <el-input
          v-model="queryParams.paymentProportion"
          placeholder="请输入付款比例(%)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="合同状态ID" prop="contractstatusId">
        <el-input
          v-model="queryParams.contractstatusId"
          placeholder="请输入合同状态ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="供应商" prop="sdId">
        <el-input
          v-model="queryParams.sdId"
          placeholder="请输入供应商"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目总预算" prop="totalProjectBudget">
        <el-input
          v-model="queryParams.totalProjectBudget"
          placeholder="请输入项目总预算"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目可用预算" prop="projectAvailableBudget">
        <el-input
          v-model="queryParams.projectAvailableBudget"
          placeholder="请输入项目可用预算"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="合同名称" prop="contractName">
        <el-input
          v-model="queryParams.contractName"
          placeholder="请输入合同名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="合同编号" prop="contractCode">
        <el-input
          v-model="queryParams.contractCode"
          placeholder="请输入合同编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="密集" prop="dense">
        <el-input
          v-model="queryParams.dense"
          placeholder="请输入密集"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开始时间" prop="beginTime">
        <el-date-picker clearable
          v-model="queryParams.beginTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择开始时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间" prop="endTime">
        <el-date-picker clearable
          v-model="queryParams.endTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择结束时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="合同金额" prop="contractPrice">
        <el-input
          v-model="queryParams.contractPrice"
          placeholder="请输入合同金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="大写" prop="capitalization">
        <el-input
          v-model="queryParams.capitalization"
          placeholder="请输入大写"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="印花税金额" prop="stampDutyAmount">
        <el-input
          v-model="queryParams.stampDutyAmount"
          placeholder="请输入印花税金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="合同情况说明" prop="contractDescription">
        <el-input
          v-model="queryParams.contractDescription"
          placeholder="请输入合同情况说明"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="采购方式" prop="procurementMethod">
        <el-input
          v-model="queryParams.procurementMethod"
          placeholder="请输入采购方式"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="相关框架协议" prop="frameworkAgreements">
        <el-input
          v-model="queryParams.frameworkAgreements"
          placeholder="请输入相关框架协议"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="采购清单" prop="purchasingList">
        <el-input
          v-model="queryParams.purchasingList"
          placeholder="请输入采购清单"
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['procure:management:add']"
        >新增</el-button>
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
      <el-table-column label="合同管理ID" align="center" prop="contractManagementId" />
      <el-table-column label="采购订单号" align="center" prop="purchasingCode" />
      <el-table-column label="项目相关合同" align="center" prop="projectRelatedContracts" />
      <el-table-column label="申请人" align="center" prop="applicant" />
      <el-table-column label="申请日期" align="center" prop="applicationDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.applicationDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申请部门" align="center" prop="departmentId" />
      <el-table-column label="申请公司" align="center" prop="companiesId" />
      <el-table-column label="相关项目" align="center" prop="relatedProjects" />
      <el-table-column label="是否主子合同" align="center" prop="isMainContract" />
      <el-table-column label="关联主合同" align="center" prop="relatedMainContract" />
      <el-table-column label="预算相关合同" align="center" prop="budgetRelatedContracts" />
      <el-table-column label="成本中心" align="center" prop="costCenter" />
      <el-table-column label="负责人" align="center" prop="head" />
      <el-table-column label="签订日期" align="center" prop="signingDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.signingDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="合同总金额" align="center" prop="totalAmount" />
      <el-table-column label="付款比例(%)" align="center" prop="paymentProportion" />
      <el-table-column label="合同状态ID" align="center" prop="contractstatusId" />
      <el-table-column label="供应商" align="center" prop="sdId" />
      <el-table-column label="项目总预算" align="center" prop="totalProjectBudget" />
      <el-table-column label="项目可用预算" align="center" prop="projectAvailableBudget" />
      <el-table-column label="合同名称" align="center" prop="contractName" />
      <el-table-column label="合同编号" align="center" prop="contractCode" />
      <el-table-column label="类型" align="center" prop="contractType" />
      <el-table-column label="密集" align="center" prop="dense" />
      <el-table-column label="开始时间" align="center" prop="beginTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.beginTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="endTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="合同金额" align="center" prop="contractPrice" />
      <el-table-column label="大写" align="center" prop="capitalization" />
      <el-table-column label="印花税金额" align="center" prop="stampDutyAmount" />
      <el-table-column label="合同情况说明" align="center" prop="contractDescription" />
      <el-table-column label="采购方式" align="center" prop="procurementMethod" />
      <el-table-column label="相关框架协议" align="center" prop="frameworkAgreements" />
      <el-table-column label="采购清单" align="center" prop="purchasingList" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['procure:management:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['procure:management:remove']"
          >删除</el-button>
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
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="采购订单号" prop="purchasingCode">
          <el-input v-model="form.purchasingCode" placeholder="请输入采购订单号" />
        </el-form-item>
        <el-form-item label="项目相关合同" prop="projectRelatedContracts">
          <el-input v-model="form.projectRelatedContracts" placeholder="请输入项目相关合同" />
        </el-form-item>
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
        <el-form-item label="相关项目" prop="relatedProjects">
          <el-input v-model="form.relatedProjects" placeholder="请输入相关项目" />
        </el-form-item>
        <el-form-item label="是否主子合同" prop="isMainContract">
          <el-input v-model="form.isMainContract" placeholder="请输入是否主子合同" />
        </el-form-item>
        <el-form-item label="关联主合同" prop="relatedMainContract">
          <el-input v-model="form.relatedMainContract" placeholder="请输入关联主合同" />
        </el-form-item>
        <el-form-item label="预算相关合同" prop="budgetRelatedContracts">
          <el-input v-model="form.budgetRelatedContracts" placeholder="请输入预算相关合同" />
        </el-form-item>
        <el-form-item label="成本中心" prop="costCenter">
          <el-input v-model="form.costCenter" placeholder="请输入成本中心" />
        </el-form-item>
        <el-form-item label="负责人" prop="head">
          <el-input v-model="form.head" placeholder="请输入负责人" />
        </el-form-item>
        <el-form-item label="签订日期" prop="signingDate">
          <el-date-picker clearable
            v-model="form.signingDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择签订日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="合同总金额" prop="totalAmount">
          <el-input v-model="form.totalAmount" placeholder="请输入合同总金额" />
        </el-form-item>
        <el-form-item label="付款比例(%)" prop="paymentProportion">
          <el-input v-model="form.paymentProportion" placeholder="请输入付款比例(%)" />
        </el-form-item>
        <el-form-item label="合同状态ID" prop="contractstatusId">
          <el-input v-model="form.contractstatusId" placeholder="请输入合同状态ID" />
        </el-form-item>
        <el-form-item label="供应商" prop="sdId">
          <el-input v-model="form.sdId" placeholder="请输入供应商" />
        </el-form-item>
        <el-form-item label="项目总预算" prop="totalProjectBudget">
          <el-input v-model="form.totalProjectBudget" placeholder="请输入项目总预算" />
        </el-form-item>
        <el-form-item label="项目可用预算" prop="projectAvailableBudget">
          <el-input v-model="form.projectAvailableBudget" placeholder="请输入项目可用预算" />
        </el-form-item>
        <el-form-item label="合同名称" prop="contractName">
          <el-input v-model="form.contractName" placeholder="请输入合同名称" />
        </el-form-item>
        <el-form-item label="合同编号" prop="contractCode">
          <el-input v-model="form.contractCode" placeholder="请输入合同编号" />
        </el-form-item>
        <el-form-item label="密集" prop="dense">
          <el-input v-model="form.dense" placeholder="请输入密集" />
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
        <el-form-item label="采购方式" prop="procurementMethod">
          <el-input v-model="form.procurementMethod" placeholder="请输入采购方式" />
        </el-form-item>
        <el-form-item label="相关框架协议" prop="frameworkAgreements">
          <el-input v-model="form.frameworkAgreements" placeholder="请输入相关框架协议" />
        </el-form-item>
        <el-form-item label="采购清单" prop="purchasingList">
          <el-input v-model="form.purchasingList" placeholder="请输入采购清单" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listManagement, getManagement, delManagement, addManagement, updateManagement } from "@/api/procure/management";

export default {
  name: "Management",
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
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
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
        contractName: null,
        contractCode: null,
        contractType: null,
        dense: null,
        beginTime: null,
        endTime: null,
        contractPrice: null,
        capitalization: null,
        stampDutyAmount: null,
        contractDescription: null,
        procurementMethod: null,
        frameworkAgreements: null,
        purchasingList: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
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
        contractName: null,
        contractCode: null,
        contractType: null,
        dense: null,
        beginTime: null,
        endTime: null,
        contractPrice: null,
        capitalization: null,
        stampDutyAmount: null,
        contractDescription: null,
        procurementMethod: null,
        frameworkAgreements: null,
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
    }
  }
};
</script>
