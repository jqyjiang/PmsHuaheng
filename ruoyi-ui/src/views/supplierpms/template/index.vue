<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="评分模板编码" prop="scoringTemplateCode">
        <el-input v-model="queryParams.scoringTemplateCode" placeholder="请输入评分模板编码" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="评分模板名称" prop="scoringTemplateName">
        <el-input v-model="queryParams.scoringTemplateName" placeholder="请输入评分模板名称" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="模板状态" prop="templateState">
        <el-input v-model="queryParams.templateState" placeholder="请输入模板状态" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['supplierpms:template:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['supplierpms:template:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['supplierpms:template:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['supplierpms:template:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="templateList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="评分模板id" align="center" prop="scoringTemplateId" />
      <el-table-column label="评分模板编码" align="center" prop="scoringTemplateCode" />
      <el-table-column label="评分模板名称" align="center" prop="scoringTemplateName" />
      <!-- <el-table-column label="模板状态" align="center" prop="templateState" /> -->
      <el-table-column label="模板状态" align="center" prop="templateState">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.release" :value="scope.row.templateState"/>
        </template>
      </el-table-column>
      <el-table-column label="模板类型" align="center" prop="templateType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.template_type" :value="scope.row.templateType"/>
        </template>
      </el-table-column>
      <!-- <el-table-column label="模板类型" align="center" prop="templateType" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['supplierpms:template:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['supplierpms:template:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改考评模板对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="180px">
        <!-- <el-form-item label="评分模板编码" prop="scoringTemplateCode">
          <el-input v-model="form.scoringTemplateCode" placeholder="请输入评分模板编码" />
        </el-form-item> -->
        <el-form-item label="评分模板名称" prop="scoringTemplateName">
          <el-input v-model="form.scoringTemplateName" placeholder="请输入评分模板名称" />
        </el-form-item>
        <!-- <el-form-item label="模板状态" prop="templateState">
          <el-input v-model="form.templateState" placeholder="请输入模板状态" />
        </el-form-item> -->
        <el-form-item label="模板类型" prop="templateType">
          <el-select v-model="form.templateType" placeholder="请选择模板类型">
            <el-option
              v-for="dict in dict.type.template_type"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-divider content-position="center">评分明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddScoringDetails">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteScoringDetails">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="scoringDetailsList" :row-class-name="rowScoringDetailsIndex"
          @selection-change="handleScoringDetailsSelectionChange" ref="scoringDetails"
          @row-click="handleSupplierSupplierListe">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50" />

          <el-table-column label="指标编码" prop="indicatorCode" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.indicatorCode" placeholder="请输入指标编码" />
              <i class="el-icon-search" id="serachOne" @click="showMaterial()"></i>
              <el-dialog :visible.sync="dialogMaterial" title="指标管理-浏览框" :modal="false">
                <!-- 这里是指标管理的内容 -->
                <el-table v-loading="loading" :data="attendIndexList" @row-click="handleRowClickIndex">
                  <el-table-column type="selection" width="55" align="center" />
                  <el-table-column label="指标编码" align="center" prop="indicatorCode" />
                  <el-table-column label="指标名称" align="center" prop="indicatorName" />
                  <el-table-column label="评分标准" align="center" prop="scoringCriteria" />
                  <el-table-column label="分值从" align="center" prop="scoreFrom" />
                  <el-table-column label="分值至" align="center" prop="scoreTo" />
                  <el-table-column label="指标分值" align="center" prop="benchmarkScore" />
                  <el-table-column label="评分方式" align="center" prop="scoringMethod" />
                  <el-table-column label="指标类型" align="center" prop="indicatorType">
                    <template slot-scope="scope">
                      <dict-tag :options="dict.type.indicator_type" :value="scope.row.indicatorType" />
                    </template>
                  </el-table-column>
                </el-table>
                <pagination v-show="mtotal > 0" :total="mtotal" :page.sync="ququeryParams.pageNum"
                  :limit.sync="ququeryParams.pageSize" @pagination="getList" />
                <div slot="footer" class="dialog-footer">
                  <el-button @click="dialogMaterial = false">取消</el-button>
                </div>
              </el-dialog>
            </template>
          </el-table-column>
          <el-table-column label="指标名称" prop="indicatorDescription" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.indicatorDescription" placeholder="请输入指标名称" />
            </template>
          </el-table-column>
          <el-table-column label="评分标准" prop="scoringCriteria" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.scoringCriteria" placeholder="请输入评分标准" />
            </template>
          </el-table-column>
          <el-table-column label="权重(%)" prop="weight" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.weight" placeholder="请输入权重(%)" />
            </template>
          </el-table-column>
          <el-table-column label="评分方式" prop="scoringMethod" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.scoringMethod" placeholder="请输入评分方式" />
            </template>
          </el-table-column>
          <el-table-column label="指标类型" prop="indicatorType" width="150">
            <template slot-scope="scope">
              <el-select v-model="scope.row.indicatorType" placeholder="请选择指标类型">
                <el-option v-for="dict in dict.type.indicator_type" :key="dict.value" :label="dict.label"
                  :value="dict.value"></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="分值从" prop="scoreFrom" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.scoreFrom" placeholder="请输入分值从" />
            </template>
          </el-table-column>
          <el-table-column label="分值至" prop="scoreTo" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.scoreTo" placeholder="请输入分值至" />
            </template>
          </el-table-column>
          <el-table-column label="评分人" prop="rater" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.rater" placeholder="请输入评分人" />
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
import { listTemplate, getTemplate, delTemplate, addTemplate, updateTemplate } from "@/api/supplierpms/template";
import { listIndicators, getIndicators, delIndicators, addIndicators, updateIndicators } from "@/api/supplierpms/indicators";
export default {
  name: "Template",
  dicts: ['template_type', 'indicator_type', 'release'],
  data() {
    return {
      deliveryIndex: 0,
      dialogMaterial: false,
      attendIndexList: [],
      mtotal: 0,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedScoringDetails: [],
      indicatorsList: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 考评模板表格数据
      templateList: [],
      // 评分明细表格数据
      scoringDetailsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        scoringTemplateCode: null,
        scoringTemplateName: null,
        templateState: null,
        templateType: null
      },
      ququeryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      //userName:null,
    };
  },
  created() {
    this.getList();
    this.userName = this.$store.state.user.name;
  },
  methods: {
    /** 查询考评模板列表 */ /** 考评指标展示列表 */
    getList() {
      this.loading = true;
      listTemplate(this.queryParams).then(response => {
        this.templateList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
      listIndicators().then(response => {
        console.log(response.rows);
        this.attendIndexList = response.rows;
        // this.total = response.total;
        // this.loading = false;
      });
    },
    /**点击放大镜 */
    showMaterial() {
      this.dialogMaterial = true;
    },
    handleRowClickIndex(row) {
      this.scoringDetailsList[this.deliveryIndex].indicatorCode = row.indicatorCode;// 指标编码
      this.scoringDetailsList[this.deliveryIndex].indicatorDescription = row.indicatorName;// 指标名称
      this.scoringDetailsList[this.deliveryIndex].scoringCriteria = row.scoringCriteria;// 评分标准
      this.scoringDetailsList[this.deliveryIndex].scoreFrom = row.scoreFrom;// 分值从
      this.scoringDetailsList[this.deliveryIndex].scoreTo = row.scoreTo;// 分值至
      //this.attendIndexList[this.deliveryIndex].supplierName = row.benchmarkScore;// 指标分值
      this.scoringDetailsList[this.deliveryIndex].scoringMethod = row.scoringMethod;// 评分方式
      this.scoringDetailsList[this.deliveryIndex].indicatorType = row.indicatorType;// 指标类型
      this.dialogMaterial = false; // 关闭对话框
    },
    /** 指标添加 */
    handleSupplierSupplierListe(row) {
      this.deliveryIndex = this.scoringDetailsList.indexOf(row); // 获取点击的行索引
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        scoringTemplateId: null,
        scoringTemplateCode: null,
        scoringTemplateName: null,
        templateState: null,
        templateType: null
      };
      this.scoringDetailsList = [];
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
      this.ids = selection.map(item => item.scoringTemplateId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加考评模板";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const scoringTemplateId = row.scoringTemplateId || this.ids
      getTemplate(scoringTemplateId).then(response => {
        this.form = response.data;
        this.scoringDetailsList = response.data.scoringDetailsList;
        this.open = true;
        this.title = "修改考评模板";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.scoringDetailsList = this.scoringDetailsList;
          if (this.form.scoringTemplateId != null) {
            updateTemplate(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTemplate(this.form).then(response => {
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
      const scoringTemplateIds = row.scoringTemplateId || this.ids;
      this.$modal.confirm('是否确认删除考评模板编号为"' + scoringTemplateIds + '"的数据项？').then(function () {
        return delTemplate(scoringTemplateIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 评分明细序号 */
    rowScoringDetailsIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 评分明细添加按钮操作 */
    handleAddScoringDetails() {
      let obj = {};
      obj.supplierCode = "";
      obj.supplierName = "";
      obj.categoryCode = "";
      obj.categoryName = "";
      obj.indicatorDescription = "";
      obj.indicatorCode = "";
      obj.scoringCriteria = "";
      obj.weight = "";
      obj.scoringMethod = "";
      obj.rater = this.userName;
      obj.indicatorType = "";
      obj.ratingStatus = "";
      obj.score = "";
      obj.selectivity = "";
      obj.scoreFrom = "";
      obj.scoreTo = "";
      obj.feedback = "";
      obj.evaluationId = "";
      this.scoringDetailsList.push(obj);
    },
    /** 评分明细删除按钮操作 */
    handleDeleteScoringDetails() {
      if (this.checkedScoringDetails.length == 0) {
        this.$modal.msgError("请先选择要删除的评分明细数据");
      } else {
        const scoringDetailsList = this.scoringDetailsList;
        const checkedScoringDetails = this.checkedScoringDetails;
        this.scoringDetailsList = scoringDetailsList.filter(function (item) {
          return checkedScoringDetails.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleScoringDetailsSelectionChange(selection) {
      this.checkedScoringDetails = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('supplierpms/template/export', {
        ...this.queryParams
      }, `template_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style>
#serachOne {
  position: absolute;
  right: 15px;
  top: 21.5px;
}
</style>
