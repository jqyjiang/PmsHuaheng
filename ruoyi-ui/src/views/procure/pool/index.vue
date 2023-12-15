<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="物料编码" prop="materialCode">
        <el-input
          v-model="queryParams.materialCode"
          placeholder="请输入物料编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物料名称" prop="materialName">
        <el-input
          v-model="queryParams.materialName"
          placeholder="请输入物料名称"
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
          :disabled="pending"
          @click="handleUpdateAllocation"
        >分配</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          size="mini"
          :disabled="pending"
          @click="handleUpdate"
        >暂挂</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          size="mini"
          :disabled="pending"
          @click="handleUpdateCancel"
        >作废</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['procure:information:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['procure:information:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="informationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="相关项目" align="center" prop="relatedProjects" />
      <el-table-column label="需求物料号" align="center" prop="materialCode" />
      <el-table-column label="需求物料名称" align="center" prop="materialName" />
      <el-table-column label="需求物料数量" align="center" prop="mustNumber" />
      <el-table-column label="需求到货时间" align="center" prop="mustDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.mustDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="需求编号" align="center" prop="requirementCode" />
      <el-table-column label="负责采购人" align="center" prop="purchaser" />
      <el-table-column label="需求部门" align="center" prop="demandDepartment" />
      <el-table-column label="预算单价(不含税)" align="center" prop="unitPrice" />
      <el-table-column label="行金额" align="center" prop="budgetAmount" />
      <el-table-column label="需求说明" align="center" prop="description" />
      <el-table-column label="需求人" align="center" prop="demander" />
      <el-table-column label="状态" align="center" prop="status" >
        <template slot-scope="scope">
          <el-tag :type="getTagType(scope.row.status)">
            {{ getStstusName(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="任务Id" align="center" prop="taskId"/>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改采购需求池对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="960px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
            <el-form :model="buyerqueryParams" ref="cqueryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
              <el-form-item label="账号" prop="loginId">
                <el-input v-model="buyerqueryParams.loginId" placeholder="账号" clearable
                          @keyup.enter.native="handleQueryBuyer"/>
              </el-form-item>
              <el-form-item label="登录名" prop="loginName">
                <el-input v-model="buyerqueryParams.loginName" placeholder="登录名" clearable
                          @keyup.enter.native="handleQueryBuyer"/>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQueryBuyer">搜索</el-button>
              </el-form-item>
              <el-table v-loading="loading" :data="buyerList" @selection-change="handleSelectionChangeBuyer">
                <el-table-column type="selection" width="55" align="center" />
                <el-table-column label="账号" align="center" prop="loginId" />
                <el-table-column label="登录名" align="center" prop="loginName" />
                <el-table-column label="性别" align="center" prop="sex" />
              </el-table>
              <pagination
                v-show="buyertotal>0"
                :total="buyertotal"
                :page.sync="buyerqueryParams.pageNum"
                :limit.sync="buyerqueryParams.pageSize"
                @pagination="getListBuyer"
              />
            </el-form>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getInformation, delInformation, addInformation, updateInformation,listInformation, editStatus, editStatusCancel, listBuyer,editStatusAllocation, editStatusProcurementTask} from "@/api/procure/pool";

export default {
  name: "Pool",
  data() {
    return {
      selectedRows: [], // 初始化为空数组
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      status:"",
      taskIds:null,
      loginName:"",
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      pending: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 采购需求池表格数据
      informationList: [],
      // 采购员表格数据
      buyerList: [],
      // 采购员总条数
      buyertotal: 0,
      // 采购员查询参数
      buyerqueryParams: {
        pageNum: 1,
        pageSize: 10,
        securityLevel: null,
        loginId: null,
        loginName: null,
        sex: null
      },
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        materialCode: null,
        materialName: null,
        calculationUnit: null,
        purchaser: null,
        lUpdated: null,
        lUpdateTime: null,
        sourceSystem: null,
        enable: null,
        mCategory: null,
        specifications: null,
        model: null,
        brand: null,
        categoriesTaxes: null,
        image: null,
        gWeight: null,
        nWeight: null,
        weight: null,
        volume: null,
        vUnit: null,
        abcAttribute: null,
        avoidInspect: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      dialogBuyer: false //用于标记采购员列表是否可见
    };
  },
  created() {
    this.getList();
  },
  computed: {
    getStstusName(){
      return function(status){
        switch(status){
          case 1:
            return '待分配';
          case 2:
            return '已暂挂';
          case 3:
            return '已分配';
          case 4:
            return '已作废';
        }
      }
    },
  },
  methods: {
    /** 查询采购需求池列表 */
    getList() {
      this.loading = true;
      listInformation(this.queryParams).then(response => {
        this.informationList = response.rows;
        this.total = response.total;
        this.loading = false;
        // console.info(this.poolList)
      });
    },
    /** 查询采购员列表 */
    getListBuyer() {
      this.loading = true;
      listBuyer(this.buyerqueryParams).then(response => {
        this.buyerList = response.rows;
        this.buyertotal = response.total;
        this.loading = false;
      });
    },

    getTagType(status){
      if(status==1){
        return 'success';
      }else if(status==2){
        return 'info';
      }else if(status==3){
        return 'warning';
      }else{
        return 'danger';
      }
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        materialId: null,
        materialCode: null,
        materialName: null,
        calculationUnit: null,
        purchaser: null,
        lUpdated: null,
        lUpdateTime: null,
        sourceSystem: null,
        enable: null,
        mCategory: null,
        specifications: null,
        model: null,
        brand: null,
        categoriesTaxes: null,
        image: null,
        gWeight: null,
        nWeight: null,
        weight: null,
        volume: null,
        vUnit: null,
        abcAttribute: null,
        avoidInspect: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 采购员搜索按钮操作 */
    handleQueryBuyer() {
      this.buyerqueryParams.pageNum = 1;
      this.getListBuyer();
    },

    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.miId)
      this.status = selection.map(item => item.status)
      this.single = selection.length!==1
      this.multiple = !selection.length
      this.pending=!selection.length
    },
    handleRowClick(row) {
      this.selectedRows = [row]; // 将选中的行数据赋值给 selectedRows 数组
      console.info("选中的行数据：", row);
    },
    // 选中采购员
      handleSelectionChangeBuyer(row){
      this.form.purchaserId=row.purchaserId;
      for (let i=0;i<this.buyerList.length;i++){
        const innerElement=this.buyerList[i];
        if (innerElement.purchaserId===row[0].purchaserId){
          this.loginName=innerElement.loginName
        }
      }
      this.dialogBuyer=false; // 关闭对话框
    },
     /** 分配按钮操作 */
    handleUpdateAllocation() {
      this.reset();
      this.open = true;
      this.buyerqueryParams.pageNum = 1;
      this.getListBuyer();
      this.title = "查询采购员";
    },
    /** 暂挂按钮操作 */
    handleUpdate() {
      const miIds =  this.ids;
      const status =  this.status;
      if (parseInt(status)===1){
        this.$modal.confirm('是否暂挂编号为"' + miIds + '"的数据项？').then(function() {
          return editStatus(miIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("暂挂成功");
        }).catch(() => {});
      }else {
        this.$message.error('存在已暂挂、已分配或已作废的数据,请修改后重试！');
        return;
      }
    },
    /** 作废按钮操作 */
    handleUpdateCancel() {
      const miIds =  this.ids;
      const status =  this.status;
      if (parseInt(status)===1||parseInt(status)===2){
        this.$modal.confirm('是否作废编号为"' + miIds + '"的数据项？').then(function() {
          return editStatusCancel(miIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("作废成功");
        }).catch(() => {});
      }else {
        this.$message.error('存在已分配或已作废的数据,请修改后重试！');
        return;
      }
    },
    /** 提交按钮（分配） */
    submitForm() {
      const miIds=this.ids;
      const status=this.status;
      const purchaser=this.loginName;
      if (parseInt(status)===1||parseInt(status)===2){
        this.$modal.confirm('是否确认分配编号为"' + miIds + '"的数据项？').then(function() {
          return editStatusAllocation(purchaser,miIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("分配成功");
          return editStatusProcurementTask(purchaser,miIds);
        }).catch(() => {});
      }else {
        this.$message.error('存在已分配或已作废的数据,请修改后重试！');
        return;
      }
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const miIds = row.miId || this.ids;
      this.$modal.confirm('是否确认删除采购需求池编号为"' + miIds + '"的数据项？').then(function() {
        return delInformation(miIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('procure/information/export', {
        ...this.queryParams
      }, `information_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
