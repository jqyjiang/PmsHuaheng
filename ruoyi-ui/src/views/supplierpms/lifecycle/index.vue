<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item label="生命周期" prop="cycle">
        <el-select v-model="queryParams.lifecycleId" placeholder="请选择生命周期" clearable>
          <el-option v-for="dict in lifecycleList" :key="dict.value" :label="dict.cycle" :value="dict.lifecycleId" />
        </el-select>
      </el-form-item>
      <el-form-item label="升降级状态" prop="slStatus">
        <el-select v-model="queryParams.slStatus" placeholder="请选择升降级状态" clearable>
          <el-option
            v-for="dict in dict.type.ud_status"
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
      <!-- <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['supplierpms:lifecycle:add']">新增</el-button>
      </el-col> -->
      <!-- <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-s-check" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['supplierpms:supplier:edit']">审核</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['supplierpms:lifecycle:export']">导出</el-button>
      </el-col>

      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="supplierList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="供应商编码" align="center" prop="sdCode" />
      <el-table-column label="供应商名称" align="center" prop="sbiName" />
      <el-table-column label="行业类型" align="center" prop="sdIndustry" />
      <el-table-column label="供应商分类" align="center" prop="classDescribe" />
      <!-- <el-table-column label="预升降级周期" align="center" prop="slAdvance" /> -->
      <el-table-column label="生命周期" align="center" prop="cycle" />
      <el-table-column label="升降级状态" align="center" prop="slStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ud_status" :value="scope.row.slStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-top" @click="handleUpdate(scope.row)"
            v-if="scope.row.slStatus === 1"
            v-hasPermi="['supplierpms:lifecycle:edit']">升级</el-button>
          <el-button size="mini" type="text" icon="el-icon-bottom" @click="handleDelete(scope.row)"
            v-if="scope.row.slStatus === 1"
            v-hasPermi="['supplierpms:lifecycle:remove']">降级</el-button>
          <el-button size="mini" type="text" icon="el-icon-s-check" @click="handleExamine(scope.row)"
          v-if="scope.row.slStatus === 2"
            v-hasPermi="['supplierpms:lifecycle:remove']">审核</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改生命周期维护对话框 -->
    <!-- <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="生命周期" prop="cycle">
          <el-input v-model="form.cycle" placeholder="请输入生命周期" />
        </el-form-item>
        <el-form-item label="加入黑名单" prop="blacklisted">
          <el-input v-model="form.blacklisted" placeholder="请输入加入黑名单" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog> -->
  </div>
</template>

<script>
import { listLifecycle, getLifecycle, delLifecycle, addLifecycle, updateLifecycle } from "@/api/supplierpms/lifecycle";
import {listDetails,getDetails,delDetails,addDetails,updateDetails,lifeStage,updateExamine} from "@/api/supplierpms/details";
export default {
  name: "Lifecycle",
  dicts:['ud_status'],
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
      // 生命周期维护表格数据
      lifecycleList: [],
      // 供应商详细表格数据
      supplierList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        cycle: null,
        blacklisted: null,
        lifecycleId: null,
        slStatus:null,
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
    this.getListOption();
  },
  methods: {
    /** 查询生命周期维护列表 */
    getList() {
      this.loading = true;
      lifeStage(this.queryParams).then((response) => {
        this.supplierList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getListOption(){
      this.loading = true;
      listLifecycle(this.queryParams).then((response) => {
        this.lifecycleList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    //取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        lifecycleId: null,
        cycle: null,
        blacklisted: null,
      };
      this.resetForm("form");
    },
    
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.lifecycleId=null;
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.lifecycleId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加生命周期维护";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const lifecycleId = row.lifecycleId || this.ids
      getLifecycle(lifecycleId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改生命周期维护";
      });
    },
    /**审核按钮 */
    handleExamine(row){
      console.log(row);
      updateExamine(row).then(response =>{
        this.$modal.msgSuccess("修改成功");
        this.open = false;
        this.getList();
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.lifecycleId != null) {
            updateLifecycle(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addLifecycle(this.form).then(response => {
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
      const lifecycleIds = row.lifecycleId || this.ids;
      this.$modal.confirm('是否确认删除生命周期维护编号为"' + lifecycleIds + '"的数据项？').then(function () {
        return delLifecycle(lifecycleIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('supplierpms/lifecycle/export', {
        ...this.queryParams
      }, `lifecycle_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
