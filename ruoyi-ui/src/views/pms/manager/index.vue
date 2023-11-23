<template>
  <div class="app-container">
    <!-- <div class="header_box" style="font-size: 20px; height: 50px; line-height: 50px; ">
      
      <img :src="headerImages" style="width: 40px;height: 40px;vertical-align: middle;margin-right: 10px;"/>
    <span style="display: inline-block;">采购订单管理</span>

    </div> -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="108px">
      <el-form-item label="供应商名称:" prop="supplier">
        <el-input
          v-model="queryParams.supplier"
          placeholder=""
          clearable
          @keyup.enter.native="handleQuery"
          icon="el-icon-search"
        />
        <i class="el-icon-search" id="serachOne"></i>
      </el-form-item>
      <el-form-item label="采购订单编号:" prop="orderCode">
        <el-input
          v-model="queryParams.orderCode"
          placeholder="请输入采购订单编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单来源:" prop="orderSource">
        <el-select v-model="queryParams.orderSource" placeholder="请选择订单来源" clearable>
          <el-option
            v-for="dict in dict.type.order_source"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="采购审批状态:" prop="orderState">
        <el-select v-model="queryParams.orderState" placeholder="请选择采购审批状态" clearable>
          <el-option
            v-for="dict in dict.type.order_state"
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
          v-hasPermi="['pms:manager:add']"
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
          v-hasPermi="['pms:manager:edit']"
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
          v-hasPermi="['pms:manager:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['pms:manager:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="managerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="采购订单id" align="center" prop="orderId" /> -->
      <el-table-column label="采购订单编号" align="center" prop="orderCode" />
      <el-table-column label="公司" align="center" prop="company" />
      <el-table-column label="订单来源" align="center" prop="orderSource">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.order_source" :value="scope.row.orderSource"/>
        </template>
      </el-table-column>
      <el-table-column label="关联合同名称" align="center" prop="contractName" />
      <el-table-column label="需求总数量" align="center" prop="totalDemand" />
      <el-table-column label="采购员" align="center" prop="purchaser" />
      <el-table-column label="采购审批状态" align="center" prop="orderState">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.order_state" :value="scope.row.orderState"/>
        </template>
      </el-table-column>
      <el-table-column label="订单执行状态" align="center" prop="orderStatus" />
      <el-table-column label="供应商名称" align="center" prop="supplier" />
      <el-table-column label="采购订单创建日期" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="物料信息ID" align="center" prop="materialId" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['pms:manager:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['pms:manager:remove']"
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

    <!-- 添加或修改采购订单管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="采购订单编号" prop="orderCode">
          <el-input v-model="form.orderCode" placeholder="请输入采购订单编号" />
        </el-form-item>
        <el-form-item label="公司" prop="company">
          <el-input v-model="form.company" placeholder="请输入公司" />
        </el-form-item>
        <el-form-item label="订单类型" prop="orderType">
          <el-select v-model="form.orderType" placeholder="请选择订单类型">
            <el-option
              v-for="dict in dict.type.order_type"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="订单来源" prop="orderSource">
          <el-select v-model="form.orderSource" placeholder="请选择订单来源">
            <el-option
              v-for="dict in dict.type.order_source"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="采购组织" prop="purOrganization">
          <el-input v-model="form.purOrganization" placeholder="请输入采购组织" />
        </el-form-item>
        <el-form-item label="附件" prop="annex">
          <el-input v-model="form.annex" placeholder="请输入附件" />
        </el-form-item>
        <el-form-item label="是否自提" prop="isSelfPickup">
          <el-select v-model="form.isSelfPickup" placeholder="请选择是否自提">
            <el-option
              v-for="dict in dict.type.self_pickup"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="自提车牌号" prop="licensePlateNumber">
          <el-input v-model="form.licensePlateNumber" placeholder="请输入自提车牌号" />
        </el-form-item>
        <el-form-item label="自提司机联系方式" prop="concatInfomation">
          <el-input v-model="form.concatInfomation" placeholder="请输入自提司机联系方式" />
        </el-form-item>
        <el-form-item label="关联合同编号" prop="contractCode">
          <el-input v-model="form.contractCode" placeholder="请输入关联合同编号" />
        </el-form-item>
        <el-form-item label="关联合同名称" prop="contractName">
          <el-input v-model="form.contractName" placeholder="请输入关联合同名称" />
        </el-form-item>
        <el-form-item label="订单说明" prop="orderDescription">
          <el-input v-model="form.orderDescription" placeholder="请输入订单说明" />
        </el-form-item>
        <el-form-item label="需求总数量" prop="totalDemand">
          <el-input v-model="form.totalDemand" placeholder="请输入需求总数量" />
        </el-form-item>
        <el-form-item label="采购员" prop="purchaser">
          <el-input v-model="form.purchaser" placeholder="请输入采购员" />
        </el-form-item>
        <el-form-item label="采购审批状态" prop="orderState">
          <el-select v-model="form.orderState" placeholder="请选择采购审批状态">
            <el-option
              v-for="dict in dict.type.order_state"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="币种" prop="currencyId">
          <el-input v-model="form.currencyId" placeholder="请输入币种" />
        </el-form-item>
        <el-form-item label="供应商名称" prop="supplier">
          <el-input v-model="form.supplier" placeholder="请输入供应商名称" />
        </el-form-item>
        <el-form-item label="联系人" prop="contacts">
          <el-input v-model="form.contacts" placeholder="请输入联系人" />
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="供应商发票方式" prop="invoiceMethod">
          <el-select v-model="form.invoiceMethod" placeholder="请选择供应商发票方式">
            <el-option
              v-for="dict in dict.type.supplier_invoice"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="物料信息ID" prop="materialId">
          <el-input v-model="form.materialId" placeholder="请输入物料信息ID" />
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
import { listManager, getManager, delManager, addManager, updateManager } from "@/api/pms/manager";

export default {
  name: "Manager",
  dicts: ['self_pickup', 'order_state', 'order_type', 'order_source','supplier_invoice'],
  data() {
    return {
      headerImages:require('../../../assets/images/order_main_header1.png'),
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
      // 采购订单管理表格数据
      managerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderCode: null,
        orderSource: null,
        orderState: null,
        supplier: null,
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
    /** 查询采购订单管理列表 */
    getList() {
      this.loading = true;
      listManager(this.queryParams).then(response => {
        this.managerList = response.rows;
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
        orderId: null,
        orderCode: null,
        company: null,
        orderType: null,
        orderSource: null,
        purOrganization: null,
        annex: null,
        isSelfPickup: null,
        licensePlateNumber: null,
        concatInfomation: null,
        contractCode: null,
        contractName: null,
        orderDescription: null,
        totalDemand: null,
        purchaser: null,
        orderState: null,
        orderStatus: null,
        currencyId: null,
        supplier: null,
        contacts: null,
        phone: null,
        invoiceMethod: null,
        createTime: null,
        materialId: null
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
      this.ids = selection.map(item => item.orderId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加采购订单管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const orderId = row.orderId || this.ids
      getManager(orderId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改采购订单管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.orderId != null) {
            updateManager(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addManager(this.form).then(response => {
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
      const orderIds = row.orderId || this.ids;
      this.$modal.confirm('是否确认删除采购订单管理编号为"' + orderIds + '"的数据项？').then(function() {
        return delManager(orderIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('pms/manager/export', {
        ...this.queryParams
      }, `manager_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style>
#serachOne {
  position: absolute;
  right: 10px;
  top: 10px;
}
</style>