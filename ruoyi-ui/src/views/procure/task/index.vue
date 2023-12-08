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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['procure:task:add']"
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
          v-hasPermi="['procure:task:edit']"
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
          v-hasPermi="['procure:task:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['procure:task:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="taskList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="任务单号" align="center" prop="taskCode" />
      <el-table-column label="采购策略" align="center" prop="procurementStrategy" />
      <el-table-column label="任务总金额" align="center" prop="taskTotal" />
      <el-table-column label="公司" align="center" prop="companiesId1" />
      <el-table-column label="采购员" align="center" prop="purchaser" />
      <el-table-column label="物料名称" align="center" prop="materialId2" />
      <el-table-column label="币种" align="center" prop="currencyId2" />
      <el-table-column label="受理策略" align="center" prop="acceptanceStrategy" />
      <el-table-column label="任务总数量" align="center" prop="taskNumber" />
      <el-table-column label="已受理数量" align="center" prop="acceptedQuantity" />
      <el-table-column label="占用任务数量" align="center" prop="taskOccupied" />
      <el-table-column label="待受理数量" align="center" prop="taskAccepted" />
      <el-table-column label="状态" align="center" prop="taskStatus" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改我的需求任务对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="任务单号" prop="taskCode">
          <el-input v-model="form.taskCode" placeholder="请输入任务单号" />
        </el-form-item>
        <el-form-item label="采购策略" prop="procurementStrategy">
          <el-input v-model="form.procurementStrategy" placeholder="请输入采购策略" />
        </el-form-item>
        <el-form-item label="任务总金额" prop="taskTotal">
          <el-input v-model="form.taskTotal" placeholder="请输入任务总金额" />
        </el-form-item>
        <el-form-item label="需求申请ID" prop="requirementId1">
          <el-input v-model="form.requirementId1" placeholder="请输入需求申请ID" />
        </el-form-item>
        <el-form-item label="公司ID" prop="companiesId1">
          <el-input v-model="form.companiesId1" placeholder="请输入公司ID" />
        </el-form-item>
        <el-form-item label="采购员" prop="purchaser">
          <el-input v-model="form.purchaser" placeholder="请输入采购员" />
        </el-form-item>
        <el-form-item label="物料ID" prop="materialId2">
          <el-input v-model="form.materialId2" placeholder="请输入物料ID" />
        </el-form-item>
        <el-form-item label="币种ID" prop="currencyId2">
          <el-input v-model="form.currencyId2" placeholder="请输入币种ID" />
        </el-form-item>
        <el-form-item label="受理策略" prop="acceptanceStrategy">
          <el-input v-model="form.acceptanceStrategy" placeholder="请输入受理策略" />
        </el-form-item>
        <el-form-item label="任务总数量" prop="taskNumber">
          <el-input v-model="form.taskNumber" placeholder="请输入任务总数量" />
        </el-form-item>
        <el-form-item label="已受理数量" prop="acceptedQuantity">
          <el-input v-model="form.acceptedQuantity" placeholder="请输入已受理数量" />
        </el-form-item>
        <el-form-item label="占用任务数量" prop="taskOccupied">
          <el-input v-model="form.taskOccupied" placeholder="请输入占用任务数量" />
        </el-form-item>
        <el-form-item label="待受理数量" prop="taskAccepted">
          <el-input v-model="form.taskAccepted" placeholder="请输入待受理数量" />
        </el-form-item>
        <el-form-item label="是否自动合单" prop="mergeOrders">
          <el-input v-model="form.mergeOrders" placeholder="请输入是否自动合单" />
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
import { listTask, getTask, delTask, addTask, updateTask } from "@/api/procure/task";

export default {
  name: "Task",
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
      // 我的需求任务表格数据
      taskList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        taskCode: null,
        procurementStrategy: null,
        taskTotal: null,
        requirementId1: null,
        companiesId1: null,
        purchaser: null,
        materialId2: null,
        currencyId2: null,
        acceptanceStrategy: null,
        taskNumber: null,
        acceptedQuantity: null,
        taskOccupied: null,
        taskAccepted: null,
        mergeOrders: null,
        taskStatus: null
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
    /** 查询我的需求任务列表 */
    getList() {
      this.loading = true;
      listTask(this.queryParams).then(response => {
        this.taskList = response.rows;
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
        taskId: null,
        taskCode: null,
        procurementStrategy: null,
        taskTotal: null,
        requirementId1: null,
        companiesId1: null,
        purchaser: null,
        materialId2: null,
        currencyId2: null,
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
      this.ids = selection.map(item => item.taskId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加我的需求任务";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const taskId = row.taskId || this.ids
      getTask(taskId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改我的需求任务";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.taskId != null) {
            updateTask(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTask(this.form).then(response => {
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
      const taskIds = row.taskId || this.ids;
      this.$modal.confirm('是否确认删除我的需求任务编号为"' + taskIds + '"的数据项？').then(function() {
        return delTask(taskIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('procure/task/export', {
        ...this.queryParams
      }, `task_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
