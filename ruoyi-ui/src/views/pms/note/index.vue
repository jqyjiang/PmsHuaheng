<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="108px">
      <el-form-item label="送货单号" prop="orderDeliveryCode">
        <el-input v-model="queryParams.orderDeliveryCode" placeholder="请输入送货单号" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="送货单状态" prop="deliveryNoteState">
        <el-select v-model="queryParams.deliveryNoteState" placeholder="请选择送货单状态" clearable>
          <el-option v-for="dict in dict.type.delivery_state" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="送货方式" prop="deliveryMethod">
        <el-input v-model="queryParams.deliveryMethod" placeholder="请输入送货方式" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="预计到货日期" prop="arrivalDate">
        <el-date-picker clearable v-model="queryParams.arrivalDate" type="date" value-format="yyyy-MM-dd"
          placeholder="请选择预计到货日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['pms:note:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['pms:note:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['pms:note:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['pms:note:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="noteList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="送货单号" align="center" prop="orderDeliveryCode" />
      <el-table-column label="送货单状态" align="center" prop="deliveryNoteState">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.delivery_state" :value="scope.row.deliveryNoteState" />
        </template>
      </el-table-column>
      <el-table-column label="预计到货日期" align="center" prop="arrivalDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.arrivalDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="供应商名称" align="center" prop="supplier" />
      <el-table-column label="供应商联系方式" align="center" prop="shipperPhone" />
      <el-table-column label="快递/物流单号" align="center" prop="trackingNumber" />
      <el-table-column label="承运人联系方式" align="center" prop="carrierPhone" />
      <el-table-column label="本次数量" align="center" prop="currentDeliveryQuantity" />
      <el-table-column label="剩余数量" align="center" prop="remainingDeliveryQuantity" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['pms:note:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['pms:note:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改订单送货管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="送货单号" prop="orderDeliveryCode">
          <el-input v-model="form.orderDeliveryCode" placeholder="请输入送货单号" />
        </el-form-item>
        <el-form-item label="关联订单" prop="orderConnectionId">
          <el-input v-model="form.orderConnectionId" placeholder="请输入关联订单" />
          <i class="el-icon-search" id="serachOne" @click="showDiagOrder()"></i>
          <el-dialog :visible.sync="dialogVisible7" title="采购订单" :modal="false">
            <!-- 这里是关联订单的内容 -->
            <el-table v-loading="loading" :data="contractOrderList" @row-click="handleRowClickOrder">
              <el-table-column type="selection" width="55" align="center" />
              <el-table-column label="订单号" align="center" prop="purchasingCode" />
              <el-table-column label="供应商名称" align="center" prop="supplierDetails.sbiName" />
            </el-table>
            <pagination v-show="ordertotal > 0" :total="ordertotal" :page.sync="orderqueryParams.pageNum"
              :limit.sync="orderqueryParams.pageSize" @pagination="getList7" />
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogVisible7 = false">取消</el-button>
            </div>
          </el-dialog>
        </el-form-item>
        <el-form-item label="送货单状态" prop="deliveryNoteState">
          <el-select v-model="form.deliveryNoteState" placeholder="请选择送货单状态">
            <el-option v-for="dict in dict.type.delivery_state" :key="dict.value" :label="dict.label"
              :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="收货地址" prop="deliveryAddress">
          <el-input v-model="form.deliveryAddress" placeholder="请输入收货地址" />
        </el-form-item>
        <el-form-item label="收货人" prop="consignee">
          <el-input v-model="form.consignee" placeholder="请输入收货人" />
        </el-form-item>
        <el-form-item label="收货公司名" prop="companyName">
          <el-input v-model="form.companyName" placeholder="请输入收货公司名" />
        </el-form-item>
        <el-form-item label="收货人联系方式" prop="consigneePhone">
          <el-input v-model="form.consigneePhone" placeholder="请输入收货人联系方式" />
        </el-form-item>
        <el-form-item label="发货人" prop="shipper">
          <el-input v-model="form.shipper" placeholder="请输入发货人" />
        </el-form-item>
        <el-form-item label="发货人联系方式" prop="shipperPhone">
          <el-input v-model="form.shipperPhone" placeholder="请输入发货人联系方式" />
        </el-form-item>
        <el-form-item label="供应商名称" prop="supplier">
          <el-input v-model="supplyName" placeholder="请输入供应商名称" />
          <i class="el-icon-search" id="serachOne" @click="showDiagSupplie()"></i>
          <el-dialog :visible.sync="dialogVisible" title="供应商名称" :modal="false">
            <!-- 这里是供应商的内容 -->
            <el-table v-loading="loading" :data="detailsList" @row-click="handleRowClick">
              <el-table-column type="selection" width="55" align="center" />
              <el-table-column label="供应商详细编码" align="center" prop="sdCode" />
              <el-table-column label="供应商名称" align="center" prop="sbiName" />
            </el-table>
            <pagination v-show="stotal > 0" :total="stotal" :page.sync="squeryParams.pageNum"
              :limit.sync="squeryParams.pageSize" @pagination="getList1" />
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogVisible = false">取消</el-button>
            </div>
          </el-dialog>
        </el-form-item>
        <el-form-item label="承运人" prop="carrier">
          <el-input v-model="form.carrier" placeholder="请输入承运人" />
        </el-form-item>
        <el-form-item label="承运人联系方式" prop="carrierPhone">
          <el-input v-model="form.carrierPhone" placeholder="请输入承运人联系方式" />
        </el-form-item>
        <el-form-item label="送货方式" prop="deliveryMethod">
          <el-input v-model="form.deliveryMethod" placeholder="请输入送货方式" />
        </el-form-item>
        <el-form-item label="快递/物流单号" prop="trackingNumber">
          <el-input v-model="form.trackingNumber" placeholder="请输入快递/物流单号" />
        </el-form-item>
        <el-form-item label="预计发货日期" prop="deliveryDate">
      <el-date-picker clearable v-model="form.deliveryDate" type="date" value-format="yyyy-MM-dd"
        placeholder="请选择预计发货日期">
      </el-date-picker>
    </el-form-item>
    <el-form-item label="预计到货日期" prop="arrivalDate">
      <el-date-picker clearable v-model="form.arrivalDate" type="date" value-format="yyyy-MM-dd"
        placeholder="请选择预计到货日期">
      </el-date-picker>
    </el-form-item>
        <el-form-item label="备注" prop="note">
          <el-input v-model="form.note" placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="附件" prop="annex">
          <file-upload v-model="form.annex"/>
        </el-form-item>
        <el-divider content-position="center">产品明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddDelivery">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteDelivery">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="deliveryList" :row-class-name="rowDeliveryIndex"
          @selection-change="handleDeliverySelectionChange" ref="delivery" @row-click="handleRowClickPro">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50" />
          <el-table-column label="产品信息" prop="productInfo" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.productInfo" placeholder="请输入产品信息" />
              <i class="el-icon-search" id="serachOne" @click="showDiagProduct()"></i>
              <el-dialog :visible.sync="dialogVisible8" title="产品信息" :modal="false">
                <!-- 这里是关联订单的内容 -->
                <el-table v-loading="loading" :data="productList" @row-click="handleRowClickProduct">
                  <el-table-column type="selection" width="55" align="center" />
                  <el-table-column label="产品名称" align="center" prop="productName" />
                  <el-table-column label="产品编码" align="center" prop="productCode" />
                  <el-table-column label="产品规格" align="center" prop="specifications" />
                  <el-table-column label="单位" align="center" prop="unit" />
                  <el-table-column label="价格" align="center" prop="price" />
                  <el-table-column label="税率" align="center" prop="taxRate" />
                </el-table>
                <pagination v-show="producttotal > 0" :total="producttotal" :page.sync="productqueryParams.pageNum"
                  :limit.sync="productqueryParams.pageSize" @pagination="getList8" />
                <div slot="footer" class="dialog-footer">
                  <el-button @click="dialogVisible8 = false">取消</el-button>
                </div>
              </el-dialog>
            </template>
          </el-table-column>
          <el-table-column label="产品规格" prop="productSpecifications" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.productSpecifications" placeholder="请输入产品规格" />
            </template>
          </el-table-column>
          <el-table-column label="需求数量" prop="requiredQuantity" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.requiredQuantity" placeholder="请输入需求数量" />
            </template>
          </el-table-column>
          <el-table-column label="单位" prop="unit" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.unit" placeholder="请输入单位" />
            </template>
          </el-table-column>
          <el-table-column label="剩余送货数量" prop="remainingDeliveryQuantity" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.remainingDeliveryQuantity" placeholder="请输入剩余送货数量" />
            </template>
          </el-table-column>
          <el-table-column label="本次送货数量" prop="currentDeliveryQuantity" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.currentDeliveryQuantity" placeholder="请输入本次送货数量" />
            </template>
          </el-table-column>
          <el-table-column label="注释" prop="remarks" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.remarks" placeholder="请输入注释" />
            </template>
          </el-table-column>
          <el-table-column label="来源单号" prop="sourceOrderNumber" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.sourceOrderNumber" placeholder="请输入来源单号" />
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
import { listProducts } from "@/api/pms/products";
import { listSupplier } from "@/api/pms/manager";
import {getDelivery} from "@/api/pms/delivery";
import { listNote, getNote, delNote, addNote, updateNote } from "@/api/pms/note";
import { listManagement } from "@/api/procure/management";

export default {
  name: "Note",
  dicts: ['delivery_state'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedDelivery: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      //供应商名称
      supplyName: '',
      // 订单送货管理表格数据
      noteList: [],
      // 产品明细表格数据
      deliveryList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderDeliveryCode: null,
        deliveryNoteState: null,
        deliveryMethod: null,
        arrivalDate: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      dialogVisible7: false,
      dialogVisible: false,
      ordertotal: 0,
      orderqueryParams: {
        pageNum: 1,
        pageSize: 10
      },
      productqueryParams: {
        pageNum: 1,
        pageSize: 10
      },
      //关联订单列表
      contractOrderList: [],
      productList: [],
      dialogVisible8: false,
      producttotal: 0,
      //供应商查询参数
      squeryParams: {
        pageNum: 1,
        pageSize: 10,
        sdCode: null,
        sbiName: null,
        phone: '',
        people: '',
      },
      //供应商总条数
      stotal: 0,
      //供应商列表
      detailsList: [],
      //获取deliveyList下标\
      deliveryIndex:0
    };
  },
  mounted() {
    this.setInitialDate();
  },
  created() {
    this.getList();
  },
  methods: {
    setInitialDate() {
      const today = new Date();
      const year = today.getFullYear();
      const month = `${today.getMonth() + 1}`.padStart(2, '0');
      const day = `${today.getDate()}`.padStart(2, '0');
      this.form.deliveryDate = `${year}-${month}-${day}`;
      // 如果需要预计到货日期也显示当前时间，可以类似地设置 this.form.arrivalDate
    },
    //点击供应商列表列数据
    handleRowClick(row) {
      this.form.supplier = row.sdId; // 将供应商名称填充到输入框中
      for (let i = 0; i < this.detailsList.length; i++) {
        const innerElement = this.detailsList[i];
        if (innerElement.sdId === row.sdId) {
          this.supplyName = innerElement.sbiName
        }
      }
      this.dialogVisible = false; // 关闭对话框

    },
    showDiagSupplie() {
      this.dialogVisible = true;
      this.getList1();
    },
    /**
     * 供应商列表
     */
    getList1() {
      this.loading = true;
      listSupplier(this.squeryParams).then(response => {
        this.detailsList = response.rows;
        this.stotal = response.total;
        this.loading = false;
      });
    },
    //点击deliveryList
    handleRowClickPro(row, event, column) {
    this.deliveryIndex = this.deliveryList.indexOf(row); // 获取点击的行索引
    // console.log(clickedRowIndex)
    // const clickedRowData = this.deliveryList[clickedRowIndex]; // 获取点击的行数据
    // console.log(clickedRowData)
    // 在这里处理你需要的逻辑
  },
    //点击每一行的产品信息显示到文本框里面
    handleRowClickProduct(row) {
        this.deliveryList[this.deliveryIndex].productInfo=row.productName
        this.deliveryList[this.deliveryIndex].productSpecifications=row.specifications
        this.deliveryList[this.deliveryIndex].unit=row.unit
        this.deliveryList[this.deliveryIndex].remainingDeliveryQuantity=50
        this.deliveryList[this.deliveryIndex].currentDeliveryQuantity=30
      this.dialogVisible8=false
    },
    //点击每一行显示到文本框里面
    handleRowClickOrder(row) {
      this.deliveryList = row.delivery
      this.form.orderConnectionId = row.purchasingCode
      this.dialogVisible7 = false;
    },
    //控制关联订单的显示
    showDiagOrder() {
      this.dialogVisible7 = true;
      this.getList7();
    },
    //控制产品信息的显示
    showDiagProduct() {
      this.dialogVisible8 = true;
      this.getList8();
    },
    /** 查询订单送货管理列表 */
    getList() {
      this.loading = true;
      listNote(this.queryParams).then(response => {
        this.noteList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询采购订单列表 */
    getList7() {
      this.loading = true;
      listManagement(this.orderqueryParams).then(response => {
        this.contractOrderList = response.rows;
        this.ordertotal = response.total;
        this.loading = false;
      });
    },
    /** 查询产品信息列表 */
    getList8() {
      this.loading = true;
      listProducts(this.productqueryParams).then(response => {
        this.productList = response.rows;
        this.producttotal = response.total;
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
        orderDeliveryNoteId: null,
        orderDeliveryCode: null,
        orderConnectionId: null,
        deliveryNoteState: null,
        deliveryAddress: null,
        consignee: null,
        companyName: null,
        consigneePhone: null,
        shipper: null,
        shipperPhone: null,
        supplier: null,
        carrier: null,
        carrierPhone: null,
        deliveryMethod: null,
        trackingNumber: null,
        deliveryDate: null,
        arrivalDate: null,
        note: null,
        annex: null,
        deliveryId: null
      };
      this.deliveryList = [];
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
      this.ids = selection.map(item => item.orderDeliveryNoteId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.form.shipper = '杨先生'
      this.form.consigneePhone = '18116372198'
      this.form.shipperPhone = '0642-167285'
      this.form.carrierPhone = '1886543210'
      this.form.companyName = '维森集团股份有限公司'
      this.form.carrier = '张经理'
      this.open = true;
      this.title = "添加订单送货管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const orderDeliveryNoteId = row.orderDeliveryNoteId || this.ids
      getNote(orderDeliveryNoteId).then(response => {
        this.form = response.data;
        //this.deliveryList = response.data.deliveryList;
        this.open = true;
        this.title = "修改订单送货管理";
      });
      getDelivery(orderDeliveryNoteId).then(response => {
        //this.form = response.data;
        this.deliveryList = response.data;
        //this.open = true;
        //this.title = "修改产品明细";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.deliveryList = this.deliveryList;
          if (this.form.orderDeliveryNoteId != null) {
            updateNote(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addNote(this.form).then(response => {
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
      const orderDeliveryNoteIds = row.orderDeliveryNoteId || this.ids;
      this.$modal.confirm('是否确认删除订单送货管理编号为"' + orderDeliveryNoteIds + '"的数据项？').then(function () {
        return delNote(orderDeliveryNoteIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 产品明细序号 */
    rowDeliveryIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 产品明细添加按钮操作 */
    handleAddDelivery() {
      let obj = {};
      obj.productInfo = "";
      obj.productSpecifications = "";
      obj.requiredQuantity = "";
      obj.unit = "";
      obj.remainingDeliveryQuantity = "";
      obj.currentDeliveryQuantity = "";
      obj.remarks = "";
      obj.sourceOrderNumber = "";
      this.deliveryList.push(obj);
    },
    /** 产品明细删除按钮操作 */
    handleDeleteDelivery() {
      if (this.checkedDelivery.length == 0) {
        this.$modal.msgError("请先选择要删除的产品明细数据");
      } else {
        const deliveryList = this.deliveryList;
        const checkedDelivery = this.checkedDelivery;
        this.deliveryList = deliveryList.filter(function (item) {
          return checkedDelivery.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleDeliverySelectionChange(selection) {
      this.checkedDelivery = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('pms/note/export', {
        ...this.queryParams
      }, `note_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style scoped>
#serachOne {
  position: absolute;
  right: 10px;
  top: 10px;
}

#serachOne1 {
  position: absolute;
  right: 15px;
  top: 21.5px;
}
</style>
