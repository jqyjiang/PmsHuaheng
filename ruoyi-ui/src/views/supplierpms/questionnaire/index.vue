<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="调查编码" prop="questionnaireCode">
        <el-input v-model="queryParams.questionnaireCode" placeholder="请输入调查编码" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="调查状态" prop="questionnaireState">
        <el-select v-model="queryParams.questionnaireState" placeholder="请选择调查状态" clearable>
          <el-option v-for="dict in dict.type.investigation_status" :key="dict.value" :label="dict.label"
            :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="是否上市" prop="listed">
        <el-select v-model="queryParams.listed" placeholder="请选择是否上市" clearable>
          <el-option v-for="dict in dict.type.sys_yes_no" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="供应商名称" prop="supplierName">
        <el-input v-model="queryParams.supplierName" placeholder="请输入供应商名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['supplierpms:questionnaire:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['supplierpms:questionnaire:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['supplierpms:questionnaire:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['supplierpms:questionnaire:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="questionnaireList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="调查id" align="center" prop="questionnaireId" /> -->
      <el-table-column label="调查编码" align="center" prop="questionnaireCode" width="190">
        <template slot-scope="scope">
          <a class="hover-style" @click="accessControl(scope.row)">{{ scope.row.questionnaireCode }}</a>
        </template>
      </el-table-column>
      <el-table-column label="调查标题" align="center" prop="questionnaireTitle" />
      <el-table-column label="供应商名称" align="center" prop="supplierName" />
      <el-table-column label="公司" align="center" prop="company" />
      <el-table-column label="调查类型" align="center" prop="questionnaireType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.questionnaire_type" :value="scope.row.questionnaireType" />
        </template>
      </el-table-column>
      <el-table-column label="调查分类" align="center" prop="surveyClassification">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.survey_classification" :value="scope.row.surveyClassification" />
        </template>
      </el-table-column>

      <el-table-column label="调查状态" align="center" prop="questionnaireState">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.investigation_status" :value="scope.row.questionnaireState" />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-if="scope.row.questionnaireState === 2" size="mini" type="text" icon="el-icon-edit"
            @click="handleUpdate(scope.row)" v-hasPermi="['supplierpms:questionnaire:edit']">供方填写</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['supplierpms:questionnaire:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改调查表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="540px" append-to-body>
      <el-form :disabled="fillIn" ref="form" :model="form" :rules="rules" label-width="120px">
        <!-- <el-form-item label="调查编码" prop="questionnaireCode">
          <el-input v-model="form.questionnaireCode" placeholder="请输入调查编码" />
        </el-form-item> -->
        <el-form-item label="调查标题" prop="questionnaireTitle">
          <el-input v-model="form.questionnaireTitle" placeholder="请输入调查标题" />
        </el-form-item>
        <!-- <el-form-item label="调查状态" prop="questionnaireState">
          <el-select v-model="form.questionnaireState" placeholder="请选择调查状态">
            <el-option v-for="dict in dict.type.investigation_status" :key="dict.value" :label="dict.label"
              :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item> -->
        <el-form-item label="调查类型" prop="questionnaireType">
          <el-select v-model="form.questionnaireType" placeholder="请选择调查类型">
            <el-option v-for="dict in dict.type.questionnaire_type" :key="dict.value" :label="dict.label"
              :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="公司" prop="company">
          <el-input v-model="form.company" placeholder="请输入公司">
            <template v-slot:suffix>
              <el-popover placement="right" width="340" trigger="click">
                <el-table :data="companiesList" @row-click="handleRowClickfrom">
                  <el-table-column width="156" property="companiesId" label="公司id"></el-table-column>
                  <el-table-column width="156" property="companiesName" label="公司名称"></el-table-column>
                </el-table>
                <i class="el-icon-search" slot="reference"></i>
              </el-popover>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="调查分类" prop="surveyClassification">
          <el-select v-model="form.surveyClassification" placeholder="请选择调查分类">
            <el-option v-for="dict in dict.type.survey_classification" :key="dict.value" :label="dict.label"
              :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="供应商名称" prop="supplierName">
          <el-input v-model="form.supplierName" placeholder="请输入供应商名称">
            <template v-slot:suffix>
              <el-popover placement="right" width="340" trigger="click">
                <el-table :data="supplyCanList" @row-click="hanSupplierClickfrom">
                  <el-table-column width="156" property="sdCode" label="供应商编号"></el-table-column>
                  <el-table-column width="156" property="sbiName" label="供应商名称"></el-table-column>
                </el-table>
                <i class="el-icon-search" slot="reference"></i>
              </el-popover>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="供应商联系人" prop="supplierContact">
          <el-input v-model="form.supplierContact" placeholder="请输入供应商联系人" />
        </el-form-item>
        <el-form-item label="供应商联系方式" prop="supplierSupplierContactInformation">
          <el-input v-model="form.supplierSupplierContactInformation" placeholder="请输入供应商联系方式" />
        </el-form-item>
        <el-form-item label="供应商联系邮箱" prop="supplierContactEmail">
          <el-input v-model="form.supplierContactEmail" placeholder="请输入供应商联系邮箱" />
        </el-form-item>
        <el-form-item label="调查回复时间" prop="surveyResponseTime">
          <el-date-picker clearable v-model="form.surveyResponseTime" type="date" value-format="yyyy-MM-dd"
            placeholder="请选择调查回复时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="采方调查说明" prop="surveyDescription">
          <el-input v-model="form.surveyDescription" placeholder="请输入采方调查说明" />
        </el-form-item>
        <el-divider content-position="center" v-if="basic">企业基本信息信息</el-divider>
        <!-- <el-row :gutter="10" class="mb8" v-if="basic">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddInformation">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteInformation">删除</el-button>
          </el-col>
        </el-row> -->
        <el-form :disabled="fillIn" v-if="basic" ref="informationList" :model="informationList" :rules="rules"
          label-width="120px">
          <el-form-item label="董事长" prop="chairman">
            <el-input v-model="informationList.chairman" placeholder="请输入董事长" />
          </el-form-item>
          <el-form-item label="总经理" prop="president">
            <el-input v-model="informationList.president" placeholder="请输入总经理" />
          </el-form-item>
          <el-form-item label="是否上市" prop="listed">
            <el-select v-model="informationList.listed" placeholder="请选择是否上市">
              <el-option v-for="dict in dict.type.sys_yes_no" :key="dict.value" :label="dict.label"
                :value="dict.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="股票代码" prop="stockCode">
            <el-input v-model="informationList.stockCode" placeholder="请输入股票代码" />
          </el-form-item>
          <el-form-item label="股权结构附件" prop="attachment">
            <el-input v-model="informationList.attachment" placeholder="请输入股权结构附件" />
          </el-form-item>
          <el-form-item label="行业排名" prop="industryRanking">
            <el-input v-model="informationList.industryRanking" placeholder="请输入行业排名" />
          </el-form-item>
          <el-form-item label="是否愿意配合审计" prop="cooperateWith">
            <el-select v-model="informationList.cooperateWith" placeholder="请选择是否愿意配合审计">
              <el-option v-for="dict in dict.type.sys_yes_no" :key="dict.value" :label="dict.label"
                :value="dict.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="公司员工数量" prop="numberOfEmployees">
            <el-input v-model="informationList.numberOfEmployees" placeholder="请输入公司员工数量" />
          </el-form-item>
          <el-form-item label="管理人员数量" prop="administrativeNumber">
            <el-input v-model="informationList.administrativeNumber" placeholder="请输入管理人员数量" />
          </el-form-item>
          <el-form-item label="组织架构附件" prop="Structure">
            <el-input v-model="informationList.Structure" placeholder="请输入组织架构附件" />
          </el-form-item>
          <el-form-item label="主要竞争对手经营范围" prop="majorCompetitors">
            <el-input v-model="informationList.majorCompetitors" placeholder="请输入主要竞争对手经营范围" />
          </el-form-item>
          <el-form-item label="经营范围" prop="businessScope">
            <el-input v-model="informationList.businessScope" placeholder="请输入经营范围" />
          </el-form-item>
          <!-- <el-form-item label="调查表id" prop="questionnaireId">
            <el-input v-model="form.questionnaireId" placeholder="请输入调查表id" />
          </el-form-item> -->
        </el-form>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" v-if="form.questionnaireState == 2" @click="refuseForm">拒绝提交</el-button>
        <el-button type="primary" v-if="form.questionnaireState == 4" @click="InterviewReview">采方审核</el-button>
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="提示" :visible.sync="dialogVisible" width="30%">
      <!-- <span>这是一段信息</span> -->
      <el-button type="primary" @click="review(true)">审核通过</el-button>
        <el-button  @click="review(false)">审核不通过</el-button>
      <span slot="footer" class="dialog-footer">
        
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { listQuestionnaire, getQuestionnaire, delQuestionnaire, addQuestionnaire, updateQuestionnaire } from "@/api/supplierpms/questionnaire";
import { listSupply, getSupply, delSupply, addSupply, updateSupply, companies, listMaterial } from "@/api/supplierpms/supply";
import { canSupplier } from "@/api/supplierpms/details";
export default {
  name: "Questionnaire",
  dicts: ['questionnaire_type', 'survey_classification', 'investigation_status', 'sys_yes_no'],
  data() {
    return {
      dialogVisible: false,
      // 遮罩层
      loading: true,
      //公司数据
      companiesList: [],
      // 能新增的供应商数据
      supplyCanList: [],
      //是否展示企业基本信息
      basic: false,
      //能否填写
      fillIn: false,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedInformation: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 调查表表格数据
      questionnaireList: [],
      // 企业基本信息表格数据
      informationList: {},
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        questionnaireCode: null,
        questionnaireTitle: null,
        questionnaireState: null,
        questionnaireType: null,
        company: null,
        surveyClassification: null,
        supplierName: null,
        supplierContact: null,
        supplierSupplierContactInformation: null,
        supplierContactEmail: null,
        surveyResponseTime: null,
        surveyDescription: null,
        status: null,
        enterpriseId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getList();
    this.getCompanies();
  },
  methods: {
    /** 查询调查表列表 */
    getList() {
      this.loading = true;
      listQuestionnaire(this.queryParams).then(response => {
        this.questionnaireList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    review(determine) {
      this.dialogVisible = false;
      if (determine) {
        this.form.questionnaireState = 6
        console.log(this.form);
        updateQuestionnaire(this.form).then(response => {
          this.$modal.msgSuccess("提交成功");
          this.open = false;
          this.getList();
        });
      } else {
        this.form.questionnaireState = 7
        console.log(this.form);
        updateQuestionnaire(this.form).then(response => {
          this.$modal.msgSuccess("提交成功");
          this.open = false;
          this.getList();
        });
      }
    },
    /**查询公司和供应商数据 */
    getCompanies() {
      companies().then(response => {
        this.companiesList = response.rows;
      });
      canSupplier().then(response => {
        this.supplyCanList = response.rows;
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
        questionnaireId: null,
        questionnaireCode: null,
        questionnaireTitle: null,
        questionnaireState: null,
        questionnaireType: null,
        company: null,
        surveyClassification: null,
        supplierName: null,
        supplierContact: null,
        supplierSupplierContactInformation: null,
        supplierContactEmail: null,
        surveyResponseTime: null,
        surveyDescription: null,
        status: null,
        enterpriseId: null
      };
      this.informationList = {
        chairman: "",
        president: "",
        listed: "",
        stockCode: "",
        attachment: "",
        industryRanking: "",
        cooperateWith: "",
        numberOfEmployees: "",
        administrativeNumber: "",
        Structure: "",
        majorCompetitors: "",
        businessScope: "",
      };
      this.resetForm("form");
      this.resetForm("informationList");
    },
    //**新增供应商搜索按钮 */
    hanSupplierClickfrom(row) {
      this.form.supplierName = row.sbiName;
      this.form.supplierContact = row.sdPcn;
      this.form.supplierContactEmail = row.sdPce;
      this.form.supplierSupplierContactInformation = row.sdPcp;
    },
    /**新增公司搜索按钮 */
    handleRowClickfrom(row) {
      //console.log(row.companiesId);
      //this.form.companiesId = row.companiesId;
      this.form.company = row.companiesName;
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
    /**采方审核按钮 */
    InterviewReview() {
      this.dialogVisible = true
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.questionnaireId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.basic = false;
      this.fillIn = false;
      this.title = "添加调查表";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      //this.reset();
      this.basic = true;
      this.fillIn = false;
      //this.handleAddInformation();
      const questionnaireId = row.questionnaireId || this.ids
      getQuestionnaire(questionnaireId).then(response => {
        this.form = response.data;
        this.informationList = response.data.informationList;
        this.open = true;
        this.title = "调查表_企业信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.informationList = this.informationList;
          if (this.form.questionnaireId != null) {
            this.form.questionnaireState = 4
            updateQuestionnaire(this.form).then(response => {
              this.$modal.msgSuccess("提交成功");
              this.open = false;
              this.getList();
            });
          } else {
            addQuestionnaire(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    refuseForm(){
      this.$refs["form"].validate(valid => {
        if (valid) {
          //this.form.informationList = this.informationList;
          if (this.form.questionnaireId != null) {
            this.form.questionnaireState = 3
            updateQuestionnaire(this.form).then(response => {
              this.$modal.msgSuccess("提交成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const questionnaireIds = row.questionnaireId || this.ids;
      this.$modal.confirm('是否确认删除调查表编号为"' + questionnaireIds + '"的数据项？').then(function () {
        return delQuestionnaire(questionnaireIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 企业基本信息序号 */
    rowInformationIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 企业基本信息添加按钮操作 */
    handleAddInformation() {
      let obj = {};
      obj.chairman = "";
      obj.president = "";
      obj.listed = "";
      obj.stockCode = "";
      obj.attachment = "";
      obj.industryRanking = "";
      obj.cooperateWith = "";
      obj.numberOfEmployees = "";
      obj.administrativeNumber = "";
      obj.Structure = "";
      obj.majorCompetitors = "";
      obj.businessScope = "";
      //this.informationList.push(obj);
    },
    accessControl(row) {
      console.log(row);
      this.fillIn = true;
      this.basic = true;
      const questionnaireId = row.questionnaireId || this.ids
      getQuestionnaire(questionnaireId).then(response => {
        this.form = response.data;
        this.informationList = response.data.informationList;
        this.open = true;
        this.title = "调查表_企业信息";
      });
    },
    /** 复选框选中数据 */
    handleInformationSelectionChange(selection) {
      this.checkedInformation = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('supplierpms/questionnaire/export', {
        ...this.queryParams
      }, `questionnaire_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style>
.hover-style:hover {
  color: rgb(51, 83, 165);
}
</style>
