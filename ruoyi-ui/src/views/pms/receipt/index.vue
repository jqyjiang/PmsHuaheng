<template>
  <div class="app-container">
    <div class="word-group">
      <div class="word" @click="toggleContent('待收货')">待收货</div>
      <div class="word" @click="toggleContent('收货单')">收货单</div>
      <!-- 添加更多的词 -->
    </div>
    <div class="content" v-show="activeWord === '待收货'">
      <!-- 待收货对应的内容 -->
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="98px">
        <el-form-item label="发货单号" prop="deliveryNoteNo">
          <el-input v-model="queryParams.deliveryNoteNo" placeholder="请输入发货单号" clearable
            @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item label="订单处理状态" prop="orderHandle">
          <el-select v-model="queryParams.orderHandle" placeholder="请选择订单处理状态" clearable>
            <el-option v-for="dict in dict.type.order_handle" :key="dict.value" :label="dict.label" :value="dict.value" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
            v-hasPermi="['pms:receipt:add']">新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
            v-hasPermi="['pms:receipt:edit']">收货</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
            v-hasPermi="['pms:receipt:remove']">删除</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
            v-hasPermi="['pms:receipt:export']">导出</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table v-loading="loading" :data="orderDetailList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="发货单号" align="center" prop="deliveryNoteNo" />
        <el-table-column label="供应商" align="center" prop="supplier" />
        <el-table-column label="公司" align="center" prop="company" />
        <el-table-column label="物料名称" align="center" prop="materialName" />
        <el-table-column label="发货数量" align="center" prop="deliveryQuantity" />
        <el-table-column label="已发货金额" align="center" prop="deliveredAmount" />
        <el-table-column label="发货日期" align="center" prop="deliveryDate" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.deliveryDate, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="收货人" align="center" prop="receiver" />
        <el-table-column label="订单处理状态" align="center" prop="orderHandle">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.order_handle" :value="scope.row.orderHandle" />
          </template>
        </el-table-column>
        <!-- <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                    <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                        v-hasPermi="['pms:receipt:edit']">修改</el-button>
                    <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                        v-hasPermi="['pms:receipt:remove']">删除</el-button>
                </template>
            </el-table-column> -->
      </el-table>

      <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
        @pagination="getList" />
    </div>

    <div class="content" v-show="activeWord === '收货单'">
      <!-- 收货单对应的内容 -->
      <el-form :model="receiptQueryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
        label-width="108px">
        <el-form-item label="收货单号" prop="deliveryNoteNo">
          <el-input v-model="receiptQueryParams.receiptNoteNo" placeholder="请输入收货单号" clearable
            @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item label="发货单号" prop="deliveryNoteNo">
          <el-input v-model="receiptQueryParams.deliveryNoteNo" placeholder="请输入发货单号" clearable
            @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item label="订单处理状态" prop="orderHandle">
          <el-select v-model="receiptQueryParams.orderHandle" placeholder="请选择订单处理状态" clearable>
            <el-option v-for="dict in dict.type.order_handle" :key="dict.value" :label="dict.label" :value="dict.value" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="loading" :data="orderReceiptDetailList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="收货单号" align="center" prop="receiptNoteNo" />
        <el-table-column label="发货单号" align="center" prop="deliveryNoteNo" />
        <el-table-column label="公司" align="center" prop="company" />
        <el-table-column label="供应商" align="center" prop="supplier" />
        <el-table-column label="物料信息" align="center" prop="materialName" />
        <el-table-column label="收货总数量" align="center" prop="receiveQuantity" />
        <el-table-column label="收货总金额" align="center" prop="recepitAmountTax" />
        <el-table-column label="收货日期" align="center" prop="receivedDate" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.receivedDate, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="收货人" align="center" prop="receiver" />
        <el-table-column label="订单处理状态" align="center" prop="orderHandle">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.order_handle" :value="scope.row.orderHandle" />
          </template>
        </el-table-column>
        <!-- <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                    <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                        v-hasPermi="['pms:receipt:edit']">修改</el-button>
                    <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                        v-hasPermi="['pms:receipt:remove']">删除</el-button>
                </template>
            </el-table-column> -->
      </el-table>

      <pagination v-show="receiptTotal > 0" :total="receiptTotal" :page.sync="receiptQueryParams.pageNum"
        :limit.sync="receiptQueryParams.pageSize" @pagination="getReceiptList" />

    </div>
    <!-- 添加或修改订单收货管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="收货单号" prop="receiptCode">
          <el-input v-model="form.receiptCode" placeholder="请输入收货单号" />
        </el-form-item>
        <el-form-item label="收货人" prop="receiptName">
          <el-input v-model="form.consignee" placeholder="请输入收货人" />
        </el-form-item>
        <el-form-item label="收货总数量" prop="receiveQuantity">
          <el-input v-model="form.receiveQuantity" placeholder="请输入收货总数量" />
        </el-form-item>
        <el-form-item label="收货含税金额" prop="recepitAmountTax">
          <el-input v-model="form.recepitAmountTax" placeholder="请输入收货含税金额" />
        </el-form-item>
        <el-form-item label="收货备注" prop="receiptRemarks">
          <el-input v-model="form.receiptRemarks" placeholder="请输入收货备注" />
        </el-form-item>
        <el-form-item label="附件" prop="annex">
          <file-upload v-model="form.annex" />
        </el-form-item>
        <el-form-item label="质检人" prop="inspector">
          <el-input v-model="form.inspector" placeholder="请输入质检人" />
        </el-form-item>
        <el-form-item label="供应商名称" prop="supplier">
          <el-input v-model="form.supplier" placeholder="请输入供应商名称" />
        </el-form-item>
        <el-form-item label="联系人" prop="receiptConcat">
          <el-input v-model="form.receiptConcat" placeholder="请输入联系人" />
        </el-form-item>
        <el-form-item label="联系电话" prop="receiptPhone">
          <el-input v-model="form.receiptPhone" placeholder="请输入联系电话" />
        </el-form-item>
        <!-- <el-form-item label="收货状态" prop="receiptState">
          <el-select v-model="form.receiptState" placeholder="请选择收货状态">
            <el-option v-for="dict in dict.type.delivery_state" :key="dict.value" :label="dict.label"
              :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item> -->
        <el-divider content-position="center">收货明细信息信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddReceiptDetails">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteReceiptDetails">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="receiptDetailsList" :row-class-name="rowReceiptDetailsIndex"
          @selection-change="handleReceiptDetailsSelectionChange" ref="receiptDetails">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50" />
          <!-- <el-table-column label="序号" prop="serialNumber" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.serialNumber" placeholder="请输入序号" />
            </template>
          </el-table-column> -->
          <el-table-column label="订单号" prop="orderCode" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.orderCode" placeholder="请输入订单号" />
            </template>
          </el-table-column>
          <el-table-column label="物料编号" prop="orCode" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.orCode" placeholder="请输入物料编号" />
            </template>
          </el-table-column>
          <el-table-column label="物料名称" prop="orName" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.orName" placeholder="请输入物料名称" />
            </template>
          </el-table-column>
          <el-table-column label="物料分类" prop="materialCategory" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.materialCategory" placeholder="请输入物料分类" />
            </template>
          </el-table-column>
          <el-table-column label="物料规格" prop="materialSpecification" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.materialSpecification" placeholder="请输入物料规格" />
            </template>
          </el-table-column>
          <el-table-column label="物料单位" prop="materialUnit" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.materialUnit" placeholder="请输入物料单位" />
            </template>
          </el-table-column>
          <el-table-column label="需求数量" prop="requireNumber" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.requireNumber" placeholder="请输入需求数量" />
            </template>
          </el-table-column>
          <el-table-column label="需求日期" prop="requireTime" width="240">
            <template slot-scope="scope">
              <el-date-picker clearable v-model="scope.row.requireTime" type="date" value-format="yyyy-MM-dd"
                placeholder="请选择需求日期" />
            </template>
          </el-table-column>
          <el-table-column label="发货数量" prop="deliveryQuantity" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.deliveryQuantity" placeholder="请输入发货数量" />
            </template>
          </el-table-column>
          <el-table-column label="拒收数量" prop="rejectedQuantity" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.rejectedQuantity" placeholder="请输入拒收数量" />
            </template>
          </el-table-column>
          <el-table-column label="拒收原因" prop="rejectionReason" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.rejectionReason" placeholder="请输入拒收原因" />
            </template>
          </el-table-column>
          <el-table-column label="收货数量" prop="receivedQuantity" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.receivedQuantity" placeholder="请输入收货数量" />
            </template>
          </el-table-column>
          <el-table-column label="不含税单价" prop="noTaxPrice" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.noTaxPrice" placeholder="请输入不含税单价" />
            </template>
          </el-table-column>
          <el-table-column label="税率" prop="taxCode" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.taxCode" placeholder="请输入税率" />
            </template>
          </el-table-column>
          <el-table-column label="税率值" prop="tax" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.tax" placeholder="请输入税率值" />
            </template>
          </el-table-column>
          <el-table-column label="含税单价" prop="taxPrice" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.taxPrice" placeholder="请输入含税单价" />
            </template>
          </el-table-column>
          <el-table-column label="收货人" prop="receiver" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.consignee" placeholder="请输入收货人" />
            </template>
          </el-table-column>
          <el-table-column label="收货电话" prop="recipientPhone" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.receivingPhone" placeholder="请输入收货电话" />
            </template>
          </el-table-column>
          <!-- <el-table-column label="库房" prop="warehouse" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.warehouse" placeholder="请输入库房" />
            </template>
          </el-table-column> -->
          <el-table-column label="供货能力免检" prop="exemptFromSupplyCapacityCheck" width="150">
            <span v-text="isEdit1"></span>
          </el-table-column>
          <el-table-column label="物料免检" prop="exemptFromMaterialInspection" width="150">
            <span v-text="isEdit2"></span>
          </el-table-column>
          <el-table-column label="备注" prop="remarks" width="150">
            <template scope="scope">
              <span>{{ scope.row.remarks }}</span>
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
import { listReceipt, getReceipt, delReceipt, addReceipt, updateReceipt, getDeliveryInfo } from "@/api/pms/receipt";
import { listOrderDetail, getOrderDetail, delOrderDetail, addOrderDetail, updateOrderDetail, listOrderDelivery, listOrderReceipt } from "@/api/pms/orderDetail";

export default {
  name: "Receipt",
  dicts: ['delivery_state', 'om_state', 'order_quanlity', 'order_handle'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedOrderExecutionDetails: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 订单收货管理表格数据
      receiptList: [],
      // mingxi表格数据
      //orderExecutionDetailsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      activeWord: '待收货', // 当前选中的词
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderHandle: null,
        deliveryNoteNo: null,
      },
      //收货单查询参数
      receiptQueryParams: {
        pageNum: 1,
        pageSize: 10,
        deliveryNoteNo: null,
        receiptNoteNo: null,
        orderHandle: null,
      },
      //收货单列表
      orderReceiptDetailList: [],
      isEdit1: '',
      isEdit2: '',
      // mingxi表格数据
      orderDetailList: [],
      // 收货明细信息表格数据
      receiptDetailsList: [],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      receiptTotal:0
    };
  },
  created() {
    this.getList();
    this.getReceiptList();
  },
  methods: {
    //查询收货单列表
    getReceiptList() {
      this.loading = true;
      listOrderReceipt(this.receiptQueryParams).then(response=>{
        this.orderReceiptDetailList = response.rows;
        this.receiptTotal = response.total;
        this.loading=false;
      })
    },
    toggleContent(word) {

      this.activeWord = this.activeWord === word ? null : word;
    },
    /** 查询订单收货管理列表 */
    getList() {
      this.loading = true;
      listOrderDelivery(this.queryParams).then(response => {
        this.orderDetailList = response.rows;
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
        receiptId: null,
        receiptNoteNo: null,
        receiver: null,
        receiveQuantity: null,
        recepitAmountTax: null,
        receiptRemarks: null,
        annex: null,
        inspector: null,
        supplier: null,
        receiptConcat: null,
        receiptPhone: null,
        receiptState: null
      };
      this.receiptDetailsList = [];
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
      console.log("这是选中的数据:" + selection);
      console.log(selection)
      this.receiptList = selection
      this.ids = selection.map(item => item.receiptId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加订单收货管理";
    },
    // getDeliveryInfo(row) {
    //   //获取收货基本信息
    //   this.orderExecutionDetailsList = row
    // },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.title = "新建收货单";
      this.open = true;
      this.form.receiptConcat = this.receiptList[0].orderManager.supplierDetails.sdPcn
      this.form.receiptPhone = this.receiptList[0].orderManager.supplierDetails.sdPcp
      this.form.supplier = this.receiptList[0].supplier
      this.form.inspector = this.$store.state.user.name
      this.form.receiptName = this.$store.state.user.name
      this.form.receiveQuantity = this.receiptList[0].deliveryQuantity
      getDeliveryInfo(this.receiptList[0].orderCode, this.receiptList[0].deliveryNoteNo).then(response => {
        this.receiptDetailsList.push(response.data);
        this.receiptDetailsList[0].receivedQuantity = this.form.receiveQuantity
        this.form.consignee=this.receiptDetailsList[0].consignee
        // this.receiptDetailsList[0].consignee=this.form.consignee
        if (this.receiptDetailsList[0].exemptFromSupplyCapacityCheck === 1) {
          this.isEdit1 = '否'
        } else {
          this.isEdit1 = '是'
        }
        if (this.receiptDetailsList[0].exemptFromMaterialInspection === 1) {
          this.isEdit2 = '否'
        } else {
          this.isEdit2 = '是'
        }
        if (response.data.tax === 0) {
          //税率为0的计算含税总金额
          this.form.recepitAmountTax = (parseFloat(response.data.noTaxPrice) * parseFloat(this.receiptDetailsList[0].receivedQuantity)).toFixed(2)
        } else {
          this.form.recepitAmountTax = (parseFloat(response.data.taxPrice) * parseFloat(this.receiptDetailsList[0].receivedQuantity)).toFixed(2)
        }
      })
      // const receiptId = row.receiptId || this.ids
      // getReceipt(receiptId).then(response => {
      //   this.form = response.data;
      //   this.orderExecutionDetailsList = response.data.orderExecutionDetailsList;
      //   this.open = true;
      //   this.title = "修改订单收货管理";
      // });
    },
    /** 提交按钮 */
    submitForm() {
      //提交按钮首先会对order_receipt表进行插入数据  然后更改订单执行表的状态 订单执行状态 质检状态  订单处理状态 收货数量和收货总金额 收货时间 
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.receiptDetails = this.receiptDetailsList;
          if (this.form.receiptId != null) {
            updateReceipt(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addReceipt(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getReceiptList();
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const receiptIds = row.receiptId || this.ids;
      this.$modal.confirm('是否确认删除订单收货管理编号为"' + receiptIds + '"的数据项？').then(function () {
        return delReceipt(receiptIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 收货明细信息序号 */
    rowReceiptDetailsIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 收货明细信息添加按钮操作 */
    handleAddReceiptDetails() {
      let obj = {};
      obj.serialNumber = "";
      obj.orderCode = "";
      obj.orCode = "";
      obj.orName = "";
      obj.materialCategory = "";
      obj.materialSpecification = "";
      obj.materialUnit = "";
      obj.requireNumber = "";
      obj.requireTime = "";
      obj.deliveryQuantity = "";
      obj.rejectedQuantity = "";
      obj.rejectionReason = "";
      obj.receivedQuantity = "";
      obj.noTaxPrice = "";
      obj.taxCode = "";
      obj.tax = "";
      obj.taxPrice = "";
      obj.receiver = "";
      obj.recipientPhone = "";
      obj.warehouse = "";
      obj.exemptFromSupplyCapacityCheck = "";
      obj.exemptFromMaterialInspection = "";
      obj.note = "";
      this.receiptDetailsList.push(obj);
    },
    /** 收货明细信息删除按钮操作 */
    handleDeleteReceiptDetails() {
      if (this.checkedReceiptDetails.length == 0) {
        this.$modal.msgError("请先选择要删除的收货明细信息数据");
      } else {
        const receiptDetailsList = this.receiptDetailsList;
        const checkedReceiptDetails = this.checkedReceiptDetails;
        this.receiptDetailsList = receiptDetailsList.filter(function (item) {
          return checkedReceiptDetails.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleReceiptDetailsSelectionChange(selection) {
      this.checkedReceiptDetails = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('pms/receipt/export', {
        ...this.queryParams
      }, `receipt_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style scoped>
.word-group {
  display: flex;
  margin-bottom: 35px;
}

.word-group .word {
  margin-right: 10px;
  cursor: pointer;
}
</style>