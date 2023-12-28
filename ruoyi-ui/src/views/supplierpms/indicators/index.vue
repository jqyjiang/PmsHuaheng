<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="指标编码" prop="indicatorCode">
        <el-input
          v-model="queryParams.indicatorCode"
          placeholder="请输入指标编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="指标名称" prop="indicatorName">
        <el-input
          v-model="queryParams.indicatorName"
          placeholder="请输入指标名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="指标类型" prop="indicatorType">
        <el-select v-model="queryParams.indicatorType" placeholder="请选择指标类型" clearable>
          <el-option
            v-for="dict in dict.type.indicator_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['supplierpms:indicators:add']"
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
          v-hasPermi="['supplierpms:indicators:edit']"
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
          v-hasPermi="['supplierpms:indicators:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['supplierpms:indicators:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="indicatorsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="指标编码" align="center" prop="indicatorCode" />
      
      <el-table-column label="指标名称" align="center" prop="indicatorName" />
      <el-table-column label="评分方式" align="center" prop="scoringMethod" />
      <el-table-column label="指标类型" align="center" prop="indicatorType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.indicator_type" :value="scope.row.indicatorType"/>
        </template>
      </el-table-column>
      <el-table-column label="评分标准" align="center" prop="scoringCriteria" />
      <el-table-column label="分值从" align="center" prop="scoreFrom" />
      <el-table-column label="分值至" align="center" prop="scoreTo" />
      <el-table-column label="基准分值" align="center" prop="benchmarkScore" />
      <el-table-column label="评分人信息" align="center" prop="rater" />
      <el-table-column label="配置" align="center" prop="configuration" />
      <el-table-column label="状态" align="center" prop="state" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['supplierpms:indicators:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['supplierpms:indicators:remove']"
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

    <!-- 添加或修改考评指标对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <!-- <el-form-item label="指标编码" prop="indicatorCode">
          <el-input v-model="form.indicatorCode" placeholder="请输入指标编码" />
        </el-form-item> -->
        <el-form-item label="指标名称" prop="indicatorName">
          <el-input v-model="form.indicatorName" placeholder="请输入指标名称" />
        </el-form-item>
        <el-form-item label="评分方式" prop="scoringMethod">
          <el-input v-model="form.scoringMethod" placeholder="请输入评分方式" />
        </el-form-item>
        <el-form-item label="指标类型" prop="indicatorType">
          <el-select v-model="form.indicatorType" placeholder="请选择指标类型">
            <el-option
              v-for="dict in dict.type.indicator_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="分值从" prop="scoreFrom">
          <el-input v-model="form.scoreFrom" placeholder="请输入分值从" />
        </el-form-item>
        <el-form-item label="分支至" prop="scoreTo">
          <el-input v-model="form.scoreTo" placeholder="请输入分支至" />
        </el-form-item>
        <el-form-item label="基准分值" prop="benchmarkScore">
          <el-input v-model="form.benchmarkScore" placeholder="请输入基准分值" />
        </el-form-item>
        <el-form-item label="评分人信息" prop="rater">
          <el-input v-model="form.rater" placeholder="请输入评分人信息" />
        </el-form-item>
        <el-form-item label="评分标准" prop="scoringCriteria">
          <el-input v-model="form.scoringCriteria" placeholder="请输入评分标准" />
        </el-form-item>
        <el-form-item label="配置" prop="configuration">
          <el-input v-model="form.configuration" placeholder="请输入配置" />
        </el-form-item>
        <!-- <el-form-item label="状态" prop="state">
          <el-input v-model="form.state" placeholder="请输入状态" />
        </el-form-item> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listIndicators, getIndicators, delIndicators, addIndicators, updateIndicators } from "@/api/supplierpms/indicators";

export default {
  name: "Indicators",
  dicts: ['indicator_type'],
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
      // 考评指标表格数据
      indicatorsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        indicatorCode: null,
        indicatorName: null,
        scoringMethod: null,
        indicatorType: null,
        scoreFrom: null,
        scoreTo: null,
        benchmarkScore: null,
        rater: null,
        scoringCriteria: null,
        configuration: null,
        state: null
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
    /** 查询考评指标列表 */
    getList() {
      this.loading = true;
      listIndicators(this.queryParams).then(response => {
        this.indicatorsList = response.rows;
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
        indicatorId: null,
        indicatorCode: null,
        indicatorName: null,
        scoringMethod: null,
        indicatorType: null,
        scoreFrom: null,
        scoreTo: null,
        benchmarkScore: null,
        rater: null,
        scoringCriteria: null,
        configuration: null,
        state: null
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
      this.ids = selection.map(item => item.indicatorId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加考评指标";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const indicatorId = row.indicatorId || this.ids
      getIndicators(indicatorId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改考评指标";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.indicatorId != null) {
            updateIndicators(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addIndicators(this.form).then(response => {
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
      const indicatorIds = row.indicatorId || this.ids;
      this.$modal.confirm('是否确认删除考评指标编号为"' + indicatorIds + '"的数据项？').then(function() {
        return delIndicators(indicatorIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('supplierpms/indicators/export', {
        ...this.queryParams
      }, `indicators_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
