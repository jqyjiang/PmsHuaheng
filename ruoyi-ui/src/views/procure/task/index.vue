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
          @click="handleUpdate"
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
      <el-table-column label="任务单号" align="center" prop="taskCode"/>
      <el-table-column label="采购策略" align="center" prop="procurementStrategy"/>
      <el-table-column label="任务总金额" align="center" prop="taskTotal"/>
      <el-table-column label="公司ID" align="center" prop="companiesId">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.companies" :value="scope.row.companiesId"/>
        </template>
      </el-table-column>
      <el-table-column label="采购员" align="center" prop="purchaser"/>
      <el-table-column label="物料名称" align="center" prop="materialName"/>
      <el-table-column label="币种" align="center" prop="currencyId"/>
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
    <el-dialog :title="title" :visible.sync="open" width="500px" id="dialog_box" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
          <el-form-item label="*受理策略:" prop="acceptanceStrategy">
            <el-select v-model="form.acceptanceStrategy" placeholder="请选择受理策略">
              <el-option
                v-for="dict in dict.type.acceptance_strategy"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
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
import {listTask, getTask, delTask, addTask, updateTask, updateTaskPurchaser} from "@/api/procure/task";
import {addRequirement, updateRequirement} from "@/api/procure/requirement";

export default {
  name: "Task",
  dicts: ['companies','acceptance_strategy','buyer'],
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
      rules: {}
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
  },
    methods: {
      /** 状态 */
      getTagType(taskStatus){
        if(taskStatus==1){
          return 'success';
        }else if(taskStatus==2){
          return 'info';
        }else if(taskStatus==3){
          return 'warning';
        }else{
          return 'danger';
        }
      },
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
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "受理";
      },
      /** 受理按钮操作 */
      handleUpdate(row) {
        this.reset();
        const taskId = row.taskId || this.ids
        getTask(taskId).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "受理";
        });
      },
      /** 转办受理按钮操作 */
      handleUpdatePurchaser(row) {
        this.reset();
        const taskId = row.taskId || this.ids
        getTask(taskId).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "需求分配";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            this.form.materialInformations=this.InfomaterialList;
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
      /** 转办按钮 */
      submitFormPurchaser() {
        const taskIds=this.ids;
        console.log("taskIds:"+taskIds)
        const selectedValue = this.queryParams.purchaser;
        const purchaser = this.dict.type.buyer.find(dict => dict.value === selectedValue)?.label;
        console.log("purchaser:"+purchaser)
          this.$modal.confirm('是否确认转办编号为"' + taskIds + '"的数据项？').then(function() {
            return updateTaskPurchaser(purchaser,taskIds);
          }).then(() => {
            this.queryParams.purchaser=null;
            this.getList();

            this.$modal.msgSuccess("转办成功");
            this.open = false;
          }).catch(() => {});
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
      }
    }
};
</script>
<style>
#dialog_box{
  margin-top: 300px;
}
</style>
