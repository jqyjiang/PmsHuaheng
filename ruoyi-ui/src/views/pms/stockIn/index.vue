<template>
  <div class="app-container">
    <div class="container">
      <div class="item" @click="showContent(0)">待入库</div>
      <div class="item" @click="showContent(1)">入库单</div>
    </div>
    <div id="contentContainer">
      <!--
        待入库
      -->
      <div class="content" v-show="activeTab === 0">
        <el-form :model="stockInQueryParams" ref="stockInQueryParams" size="small" :inline="true" v-show="showSearch"
          label-width="68px">
          <el-form-item label="发货单号" prop="deliveryNoteNo">
            <el-input v-model="stockInQueryParams.deliveryNoteNo" placeholder="请输入发货单号" clearable
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="订单处理状态" prop="orderHandle">
            <el-select v-model="stockInQueryParams.orderHandle" placeholder="请选择订单处理状态" clearable>
              <el-option v-for="dict in dict.type.order_handle" :key="dict.value" :label="dict.label"
                :value="dict.value" />
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
              v-hasPermi="['pms:orderDelivery:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
              v-hasPermi="['pms:orderDelivery:edit']">入库</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
              v-hasPermi="['pms:orderDelivery:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
              v-hasPermi="['pms:orderDelivery:export']">导出</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getStockInList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="stockInList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="收货单号" align="center" prop="receiptNoteNo" />
          <el-table-column label="供应商" align="center" prop="supplier" />
          <el-table-column label="公司" align="center" prop="company" />
          <el-table-column label="物料名称" align="center" prop="materialName" />
          <el-table-column label="收货数量" align="center" prop="receiveQuantity" />
          <el-table-column label="收货日期" align="center" prop="receivedDate" width="180">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.deliveryDate, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="收货人" align="center" prop="receiver" />
          <el-table-column label="质检状态" align="center" prop="orderQuanlity">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.order_quanlity" :value="scope.row.orderQuanlity" />
            </template>
          </el-table-column>
          <!-- <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                    <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                        v-hasPermi="['pms:orderDetail:edit']">修改</el-button>
                    <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                        v-hasPermi="['pms:orderDetail:remove']">删除</el-button>
                </template>
            </el-table-column> -->
        </el-table>

        <pagination v-show="stockTotal > 0" :total="stockTotal" :page.sync="stockInQueryParams.pageNum"
          :limit.sync="stockInQueryParams.pageSize" @pagination="getStockInList" />
      </div>
      <!--
        入库单
      -->
      <div class="content" v-show="activeTab === 1">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="入库单号" prop="stockInNoteNo">
            <el-input v-model="queryParams.stockInNoteNo" placeholder="请输入入库单号" clearable
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
              v-hasPermi="['pms:stockIn:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
              v-hasPermi="['pms:stockIn:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
              v-hasPermi="['pms:stockIn:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
              v-hasPermi="['pms:stockIn:export']">导出</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="stockInList1" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="入库单号" align="center" prop="stockInNoteNo" />
          <el-table-column label="入库人" align="center" prop="stockInPerson" />
          <el-table-column label="入库总数量" align="center" prop="stockInQuantity" />
          <el-table-column label="入库总金额" align="center" prop="stockInAmount" />
          <el-table-column label="入库税额" align="center" prop="taxAmount" />
          <el-table-column label="退货总数量" align="center" prop="returnQuantity" />
          <el-table-column label="退货含税金额" align="center" prop="returnTaxIncludedAmount" />
          <el-table-column label="退货不含税金额" align="center" prop="returnTaxExcludedAmount" />
          <el-table-column label="入库日期" align="center" prop="inboundDate" width="180">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.inboundDate, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <!-- <el-table-column label="创建时间" align="center" prop="createdTime" width="180">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createdTime, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="更新时间" align="center" prop="updatedAt" width="180">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.updatedAt, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column> -->
          <!-- <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                v-hasPermi="['pms:stockIn:edit']">修改</el-button>
              <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                v-hasPermi="['pms:stockIn:remove']">删除</el-button>
            </template>
          </el-table-column> -->
        </el-table>

        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
          @pagination="getList" />
      </div>
    </div>


    <!-- 添加或修改订单入库管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="入库单号" prop="stockInNoteNo">
          <el-input v-model="form.stockInNoteNo" placeholder="请输入入库单号" />
        </el-form-item>
        <el-form-item label="入库日期" prop="inboundDate">
          <el-date-picker clearable v-model="form.inboundDate" type="date" value-format="yyyy-MM-dd"
            placeholder="请选择入库日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="入库人" prop="stockInPerson">
          <el-input v-model="form.stockInPerson" placeholder="请输入入库人" />
        </el-form-item>
        <el-form-item label="入库总数量" prop="stockInQuantity">
          <el-input v-model="form.stockInQuantity" placeholder="请输入入库总数量" />
        </el-form-item>
        <el-form-item label="入库含税总金额" prop="stockInAmount">
          <el-input v-model="form.stockInAmount" placeholder="请输入入库总金额" />
        </el-form-item>
        <el-form-item label="入库金额" prop="stockInNoTaxAmount">
          <el-input v-model="form.stockInNoTaxAmount" placeholder="请输入入库总金额" />
        </el-form-item>
        <el-form-item label="入库税额" prop="taxAmount">
          <el-input v-model="form.taxAmount" placeholder="请输入入库税额" />
        </el-form-item>
        <el-form-item label="入库备注" prop="remarks">
          <el-input v-model="form.remarks" placeholder="请输入入库备注" />
        </el-form-item>
        <el-form-item label="附件" prop="annex">
          <file-upload v-model="form.annex" placeholder="请输入附件" />
        </el-form-item>
        <el-form-item label="退货总数量" prop="returnQuantity">
          <el-input v-model="form.returnQuantity" placeholder="请输入退货总数量" />
        </el-form-item>
        <el-form-item label="退货含税金额" prop="returnTaxIncludedAmount">
          <el-input v-model="form.returnTaxIncludedAmount" placeholder="请输入退货含税金额" />
        </el-form-item>
        <el-form-item label="退货不含税金额" prop="returnTaxExcludedAmount">
          <el-input v-model="form.returnTaxExcludedAmount" placeholder="请输入退货不含税金额" />
        </el-form-item>
        <el-divider content-position="center">入库单物料信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddInboundMaterial">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteInboundMaterial">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="inboundMaterialList" :row-class-name="rowInboundMaterialIndex"
          @selection-change="handleInboundMaterialSelectionChange" ref="inboundMaterial">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50" />
          <el-table-column label="订单号" prop="orderCode" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.orderCode" placeholder="请输入订单号" />
            </template>
          </el-table-column>
          <el-table-column label="行号" prop="lineNumber" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.lineNumber" placeholder="请输入行号" />
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
          <el-table-column label="物料类别" prop="materialCategory" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.materialCategory" placeholder="请输入物料类别" />
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
          <el-table-column label="需求数量" prop="requiredQuantity" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.requiredQuantity" placeholder="请输入需求数量" />
            </template>
          </el-table-column>
          <el-table-column label="需求日期" prop="deliveryDate" width="240">
            <template slot-scope="scope">
              <el-date-picker clearable v-model="scope.row.deliveryDate" type="date" value-format="yyyy-MM-dd"
                placeholder="请选择需求日期" />
            </template>
          </el-table-column>
          <el-table-column label="收货数量" prop="receivedQuantity" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.receivedQuantity" placeholder="请输入收货数量" />
            </template>
          </el-table-column>
          <el-table-column label="质检处理措施" prop="handlingMeasuresName" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.handlingMeasuresName" placeholder="请输入质检处理措施" />
            </template>
          </el-table-column>
          <el-table-column label="质检合格数量" prop="inspectQualifiedNumber" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.inspectQualifiedNumber" placeholder="请输入质检合格数量" />
            </template>
          </el-table-column>
          <el-table-column label="入库数量" prop="stockInQuantity" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.stockInQuantity" placeholder="请输入入库数量" />
            </template>
          </el-table-column>
          <el-table-column label="退换货数量" prop="returnExchangeQuantity" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.returnExchangeQuantity" placeholder="请输入退换货数量" />
            </template>
          </el-table-column>
          <el-table-column label="退换货原因" prop="returnExchangeReason" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.returnExchangeReason" placeholder="请输入退换货原因" />
            </template>
          </el-table-column>
          <el-table-column label="收货人" prop="consignee" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.consignee" placeholder="请输入收货人" />
            </template>
          </el-table-column>
          <el-table-column label="收货人电话号码" prop="receivingPhone" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.receivingPhone" placeholder="请输入收货人电话号码" />
            </template>
          </el-table-column>
          <el-table-column label="库房" prop="inventory" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.inventory" placeholder="请输入库房" />
            </template>
          </el-table-column>
          <el-table-column label="备注" prop="remarks" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.remarks" placeholder="请输入备注" />
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
import { listStockIn, getStockIn, delStockIn, addStockIn, updateStockIn, getInboundMaterial } from "@/api/pms/stockIn";
import { stockInList } from "@/api/pms/orderDetail"
import { findByOrderCodeMaterial } from "@/api/pms/materials"
export default {
  name: "StockIn",
  dicts: ['decection_result_name', 'quality_status', 'inspect_type', 'handling_measures_name', 'inventory', 'inspect_order_type', 'order_handle', 'order_quanlity'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedInboundMaterial: [],
      //显示待入库列表
      stockInList: [],
      activeTab: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 订单入库管理表格数据
      stockInList1: [],
      // 入库单物料表格数据
      inboundMaterialList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        stockInNoteNo: null,
      },
      //待入库查询参数
      stockInQueryParams: {
        pageNum: 1,
        pageSize: 10
      },
      //总数
      stockTotal: 0,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      selectedItems: [],
      resposeList: [],
      noTaxPrice: 0//不含税金额
    };
  },
  created() {
    this.getList();
    this.getStockInList();
  },
  methods: {
    //查询待入库列表
    getStockInList() {
      this.loading = true;
      stockInList(this.stockInQueryParams).then(response => {
        this.stockInList = response.rows;
        this.stockTotal = response.total;
        this.loading = false;
      })
    },
    showContent(index) {
      this.activeTab = index;
      if (index === 0) {
        this.getStockInList();
      } else {
        this.getList();
      }
    },
    /** 查询订单入库管理列表 */
    getList() {
      this.loading = true;
      listStockIn(this.queryParams).then(response => {
        this.stockInList1 = response.rows;
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
        id: null,
        stockInNoteNo: null,
        inboundDate: null,
        stockInPerson: null,
        stockInQuantity: null,
        stockInNoTaxAmount: null,
        stockInAmount: null,
        taxAmount: null,
        remarks: null,
        annex: null,
        returnQuantity: null,
        returnTaxIncludedAmount: null,
        returnTaxExcludedAmount: null,
        createdTime: null,
        updatedAt: null
      };
      this.inboundMaterialList = [];
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
      this.selectedItems = selection;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加订单入库管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const selectedItem = this.selectedItems[0];
      console.log(selectedItem);
      console.log(selectedItem);
      if (selectedItem.orderQuanlity === 2) {
        //可以入库
        getInboundMaterial(selectedItem.orderCode,selectedItem.materialName).then(response => {
          this.inboundMaterialList = response.data;
          findByOrderCodeMaterial(selectedItem.materialName, selectedItem.orderCode)
            .then(response => {
              // 检查响应数据是否为数组
              if (Array.isArray(response.data)) {
                this.responseList = response.data;
                this.responseList.forEach(element => {
                  if (element.orderCode === this.inboundMaterialList[0].orderCode && element.orName === this.inboundMaterialList[0].orName) {
                    this.noTaxPrice = element.noTaxPrice;
                    this.taxPrice = element.taxPrice;
                    if (element.tax === 0) {
                      this.form.stockInAmount = element.noTaxPrice * this.inboundMaterialList[0].stockInQuantity;
                    } else {
                      this.form.stockInAmount = element.taxPrice * this.inboundMaterialList[0].stockInQuantity;
                    }
                    this.form.stockInQuantity = this.inboundMaterialList[0].stockInQuantity;
                    this.form.stockInNoTaxAmount = this.noTaxPrice * this.inboundMaterialList[0].stockInQuantity;
                    this.form.taxAmount = this.form.stockInAmount - this.form.stockInNoTaxAmount;
                    this.form.stockInPerson = this.$store.state.user.name;
                    this.form.inboundDate = new Date();
                  }
                });
              } else {
                // 处理响应数据不是数组的情况
                console.error('Response data is not an array');
                if (response.data.orderCode === this.inboundMaterialList[0].orderCode && response.data.orName === this.inboundMaterialList[0].orName) {
                    this.noTaxPrice = response.data.noTaxPrice;
                    this.taxPrice = response.data.taxPrice;
                    if (response.data.tax === 0) {
                      this.form.stockInAmount = response.data.noTaxPrice * this.inboundMaterialList[0].stockInQuantity;
                    } else {
                      this.form.stockInAmount = response.data.taxPrice * this.inboundMaterialList[0].stockInQuantity;
                    }
                    this.form.stockInQuantity = this.inboundMaterialList[0].stockInQuantity;
                    this.form.stockInNoTaxAmount = this.noTaxPrice * this.inboundMaterialList[0].stockInQuantity;
                    this.form.taxAmount = this.form.stockInAmount - this.form.stockInNoTaxAmount;
                    this.form.stockInPerson = this.$store.state.user.name;
                    this.form.inboundDate = new Date();
                  }
              }
              if (this.inboundMaterialList[0].handlingMeasuresName === 1) {
                this.form.returnQuantity = this.inboundMaterialList[0].returnExchangeQuantity
                this.form.returnTaxIncludedAmount = taxPrice * this.form.receivedQuantity
                this.form.returnTaxExcludedAmount = noTaxPrice * this.form.receivedQuantity
              } else {
                this.form.returnQuantity = 0.00
                this.form.returnTaxIncludedAmount = 0.00
                this.form.returnTaxExcludedAmount = 0.00
              }
              this.open = true;
              this.title = "订单入库";
            })
            .catch(error => {
              // 处理异常情况
              console.error('Error fetching data:', error);
            });
        })


      } else {
        this.$notify({
          title: '警告',
          message: '请先通过质检',
          type: 'warning'
        });
      }
      // const id = row.id || this.ids
      // getStockIn(id).then(response => {
      //   this.form = response.data;
      //   this.inboundMaterialList = response.data.inboundMaterialList;

      // });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.inboundMaterialList = this.inboundMaterialList;
          if (this.form.id != null) {
            updateStockIn(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStockIn(this.form).then(response => {
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
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除订单入库管理编号为"' + ids + '"的数据项？').then(function () {
        return delStockIn(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 入库单物料序号 */
    rowInboundMaterialIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 入库单物料添加按钮操作 */
    handleAddInboundMaterial() {
      let obj = {};
      obj.orderCode = "";
      obj.lineNumber = "";
      obj.orCode = "";
      obj.orName = "";
      obj.materialCategory = "";
      obj.materialSpecification = "";
      obj.materialUnit = "";
      obj.requiredQuantity = "";
      obj.deliveryDate = "";
      obj.receivedQuantity = "";
      obj.handlingMeasuresName = "";
      obj.inspectQualifiedNumber = "";
      obj.stockInQuantity = "";
      obj.returnExchangeQuantity = "";
      obj.returnExchangeReason = "";
      obj.consignee = "";
      obj.receivingPhone = "";
      obj.inventory = "";
      obj.remarks = "";
      this.inboundMaterialList.push(obj);
    },
    /** 入库单物料删除按钮操作 */
    handleDeleteInboundMaterial() {
      if (this.checkedInboundMaterial.length == 0) {
        this.$modal.msgError("请先选择要删除的入库单物料数据");
      } else {
        const inboundMaterialList = this.inboundMaterialList;
        const checkedInboundMaterial = this.checkedInboundMaterial;
        this.inboundMaterialList = inboundMaterialList.filter(function (item) {
          return checkedInboundMaterial.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleInboundMaterialSelectionChange(selection) {
      this.checkedInboundMaterial = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('pms/stockIn/export', {
        ...this.queryParams
      }, `stockIn_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style scoped>
.container {
  display: flex;
  flex-direction: row;

}

.item {
  margin-right: 10px;
  cursor: pointer;
}
</style>