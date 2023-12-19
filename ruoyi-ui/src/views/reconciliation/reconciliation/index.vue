<template>
    <div class="app-container">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="公司" prop="company">
          <el-input
            style="width: 120px;"
            v-model="queryParams.company"
            placeholder="请输入公司"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="供应商" prop="supplier">
          <el-input
          style="width: 125px;"
            v-model="queryParams.supplier"
            placeholder="请输入供应商"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="收货编号" prop="receiveCode">
          <el-input
          style="width: 135px;"
            v-model="queryParams.receiveCode"
            placeholder="请输入收货编号"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="订单号" prop="orderCode">
          <el-input
          style="width: 125px;"
            v-model="queryParams.orderCode"
            placeholder="请输入订单号"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="收货人" prop="consignee">
          <el-input
          style="width: 125px;"
            v-model="queryParams.consignee"
            placeholder="请输入收货人"
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
        <!-- <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['mast:reconciliation:add']"
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
            v-hasPermi="['mast:reconciliation:edit']"
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
            v-hasPermi="['mast:reconciliation:remove']"
          >删除</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['mast:reconciliation:export']"
          >导出</el-button>
        </el-col> -->
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
  
      <el-table v-loading="loading" :data="reconciliationList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <!-- <el-table-column label="对账单id" align="center" prop="statementAccountId" /> -->
        <el-table-column label="收货单号" align="center" prop="receiveCode" />

        <el-table-column label="收货单号" align="center" prop="receiveCode" >
        <template slot-scope="scope">
          <span v-for="item in orderDetailList" :key="index">
            <template v-if="scope.row.id===item.statement_account_id">
              {{item.receiptNoteNo}}
            </template>
          </span>
        </template>
      </el-table-column>

        <el-table-column label="订单号" align="center" prop="orderCode" />
        <el-table-column label="公司" align="center" prop="company" />
        <el-table-column label="供应商名称" align="center" prop="supplier" />
        <el-table-column label="物料名称" align="center" prop="materialName" />
        <el-table-column label="待对金额" align="center" prop="actualTax" />
        <el-table-column label="收货日期" align="center" prop="receivingDate" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.receivingDate, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <!-- <el-table-column label="对账人" align="center" prop="reconciler" /> -->
        <el-table-column label="收货人" align="center" prop="consignee" />
        <el-table-column label="状态" align="center" prop="status" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['mast:reconciliation:edit']"
            >生成对账单</el-button>
            <!-- <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['mast:reconciliation:remove']"
            >删除</el-button> -->
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
  
      <!-- 添加或修改对账单对话框 -->
      <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="公司" prop="company">
            <el-input v-model="form.company" placeholder="请输入公司" />
          </el-form-item>
          <el-form-item label="供应商名称" prop="supplier">
            <el-input v-model="form.supplier" placeholder="请输入供应商名称" />
          </el-form-item>
          <el-form-item label="对账总数量" prop="totalAccount">
            <el-input v-model="form.totalAccount" placeholder="请输入对账总数量" />
          </el-form-item>
          <el-form-item label="实对含税金额" prop="actualTax">
            <el-input v-model="form.actualTax" placeholder="请输入实对含税金额" />
          </el-form-item>
          <el-form-item label="实对不含税金额" prop="notActualTax">
            <el-input v-model="form.notActualTax" placeholder="请输入实对不含税金额" />
          </el-form-item>
          <el-form-item label="实对税额" prop="actualTaxAmount">
            <el-input v-model="form.actualTaxAmount" placeholder="请输入实对税额" />
          </el-form-item>
          <el-form-item label="调整金额" prop="adjustmentAmount">
            <el-input v-model="form.adjustmentAmount" placeholder="请输入调整金额" />
          </el-form-item>
          <el-form-item label="收货日期" prop="receivingDate">
            <el-date-picker clearable
              v-model="form.receivingDate"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="请选择收货日期">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="对账人" prop="reconciler">
            <el-input v-model="form.reconciler" placeholder="请输入对账人" />
          </el-form-item>
          <el-form-item label="币种" prop="currencyId">
            <el-input v-model="form.currencyId" placeholder="请输入币种" />
          </el-form-item>
          <el-form-item label="采购对账单备注" prop="notes">
            <el-input v-model="form.notes" placeholder="请输入采购对账单备注" />
          </el-form-item>
          <el-form-item label="附件" prop="annex">
            <el-input v-model="form.annex" placeholder="请输入附件" />
          </el-form-item>
          <el-form-item label="对账明细id" prop="statementId">
            <el-input v-model="form.statementId" placeholder="请输入对账明细id" />
          </el-form-item>
          <el-form-item label="收货编号" prop="receiveCode">
            <el-input v-model="form.receiveCode" placeholder="请输入收货编号" />
          </el-form-item>
          <el-form-item label="采购订单编号" prop="orderCode">
            <el-input v-model="form.orderCode" placeholder="请输入采购订单编号" />
          </el-form-item>
          <el-form-item label="对账单号" prop="statementAccountCode">
            <el-input v-model="form.statementAccountCode" placeholder="请输入对账单号" />
          </el-form-item>
          <el-form-item label="对账类型id" prop="statementAccountStateId">
            <el-input v-model="form.statementAccountStateId" placeholder="请输入对账类型id" />
          </el-form-item>
          <el-form-item label="物料名称" prop="materialName">
            <el-input v-model="form.materialName" placeholder="请输入物料名称" />
          </el-form-item>
          <el-form-item label="收货人" prop="consignee">
            <el-input v-model="form.consignee" placeholder="请输入收货人" />
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
  import { listReconciliation, getReconciliation, delReconciliation, addReconciliation, updateReconciliation ,listOrderDetail} from "@/api/reconciliation/reconciliation";
  
  export default {
    name: "Reconciliation",
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
        // 对账单表格数据
        reconciliationList: [],
        orderDetailList:[],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          company: null,
          supplier: null,
          receiveCode: null,
          orderCode: null,
          consignee: null
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
      this.getList1();
    },
    methods: {
      /** 查询对账单列表 */
      getList() {
        this.loading = true;
        listReconciliation(this.queryParams).then(response => {
          this.reconciliationList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      /** 查询mingxi列表 */
      getList1() {
      listOrderDetail(this.queryParams).then(response => {
        this.orderDetailList = response.rows;
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
          statementAccountId: null,
          company: null,
          supplier: null,
          totalAccount: null,
          actualTax: null,
          notActualTax: null,
          actualTaxAmount: null,
          adjustmentAmount: null,
          generateReconciliationDate: null,
          reconciler: null,
          currencyId: null,
          notes: null,
          annex: null,
          statementId: null,
          receiveCode: null,
          orderCode: null,
          statementAccountCode: null,
          status: null,
          statementAccountStateId: null,
          materialName: null,
          consignee: null,
          receivingDate:null
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
        this.ids = selection.map(item => item.statementAccountId)
        this.single = selection.length!==1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加对账单";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const statementAccountId = row.statementAccountId || this.ids
        getReconciliation(statementAccountId).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改对账单";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.statementAccountId != null) {
              updateReconciliation(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addReconciliation(this.form).then(response => {
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
        const statementAccountIds = row.statementAccountId || this.ids;
        this.$modal.confirm('是否确认删除对账单编号为"' + statementAccountIds + '"的数据项？').then(function() {
          return delReconciliation(statementAccountIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('mast/reconciliation/export', {
          ...this.queryParams
        }, `reconciliation_${new Date().getTime()}.xlsx`)
      }
    }
  };
  </script>
  