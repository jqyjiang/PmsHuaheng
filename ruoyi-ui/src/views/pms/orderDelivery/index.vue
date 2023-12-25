<template>
    <div class="app-container">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
            <el-form-item label="发货单号" prop="deliveryNoteNo">
                <el-input v-model="queryParams.deliveryNoteNo" placeholder="请输入发货单号" clearable
                    @keyup.enter.native="handleQuery" />
            </el-form-item>
            <el-form-item label="订单处理状态" prop="orderHandle">
                <el-select v-model="queryParams.orderHandle" placeholder="请选择订单处理状态" clearable>
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
                    v-hasPermi="['pms:orderDelivery:edit']">收货</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
                    v-hasPermi="['pms:orderDelivery:remove']">删除</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
                    v-hasPermi="['pms:orderDelivery:export']">导出</el-button>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="orderDetailList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="发货单号" align="center" prop="deliveryNoteNo" />
            <el-table-column label="供应商" align="center" prop="supplier" />
            <el-table-column label="公司" align="center" prop="materialName" />
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
                        v-hasPermi="['pms:orderDetail:edit']">修改</el-button>
                    <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                        v-hasPermi="['pms:orderDetail:remove']">删除</el-button>
                </template>
            </el-table-column> -->
        </el-table>

        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
            @pagination="getList" />

        <!-- 添加或修改mingxi对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
            <el-form ref="form" :model="form" :rules="rules" label-width="80px">
                <el-form-item label="收货单号" prop="receiptNoteNo">
                    <el-input v-model="form.receiptNoteNo" placeholder="请输入收货单号" />
                </el-form-item>
                <!-- <el-form-item label="收货单行号" prop="receiptNoteLineNo">
                    <el-input v-model="form.receiptNoteLineNo" placeholder="请输入收货单行号" />
                </el-form-item> -->
                <!-- <el-form-item label="拒收数量" prop="rejectedQuantity">
                    <el-input v-model="form.rejectedQuantity" placeholder="请输入拒收数量" />
                </el-form-item> -->
                <el-form-item label="已收货数量" prop="receiveQuantity">
                    <el-input v-model="form.receiveQuantity" placeholder="请输入已收货数量" />
                </el-form-item>
                <el-form-item label="收货人" prop="receiver">
                    <el-input v-model="form.receiver" placeholder="请输入收货人" />
                </el-form-item>
                <el-form-item label="收货日期" prop="receivedDate">
                    <el-date-picker clearable v-model="form.receivedDate" type="date" value-format="yyyy-MM-dd"
                        placeholder="请选择收货日期">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="质检人" prop="totalDemand">
                    <el-input v-model="form.totalDemand" placeholder="请输入总需求量" />
                </el-form-item>
                <el-form-item label="发货数量" prop="deliveryQuantity">
                    <el-input v-model="form.deliveryQuantity" placeholder="请输入发货数量" />
                </el-form-item>
                <el-form-item label="已收货数量" prop="receivedQuantity">
                    <el-input v-model="form.receivedQuantity" placeholder="请输入已收货数量" />
                </el-form-item>
                <el-form-item label="库存数量" prop="inStockQuantity">
                    <el-input v-model="form.inStockQuantity" placeholder="请输入库存数量" />
                </el-form-item>
                <el-form-item label="税后总金额" prop="taxTotal">
                    <el-input v-model="form.taxTotal" placeholder="请输入税后总金额" />
                </el-form-item>
                <el-form-item label="已收款金额" prop="receivedAmount">
                    <el-input v-model="form.receivedAmount" placeholder="请输入已收款金额" />
                </el-form-item>
                <el-form-item label="待发货数量" prop="awaitingDeliveryQuantity">
                    <el-input v-model="form.awaitingDeliveryQuantity" placeholder="请输入待发货数量" />
                </el-form-item>
                <el-form-item label="待发货金额" prop="awaitingDeliveryAmount">
                    <el-input v-model="form.awaitingDeliveryAmount" placeholder="请输入待发货金额" />
                </el-form-item>
                <el-form-item label="订单行号" prop="orderLineNo">
                    <el-input v-model="form.orderLineNo" placeholder="请输入订单行号" />
                </el-form-item>
                <el-form-item label="物料编码" prop="materialCode">
                    <el-input v-model="form.materialCode" placeholder="请输入物料编码" />
                </el-form-item>
                <el-form-item label="物料名称" prop="materialName">
                    <el-input v-model="form.materialName" placeholder="请输入物料名称" />
                </el-form-item>
                <el-form-item label="物料单位" prop="materialUnit">
                    <el-input v-model="form.materialUnit" placeholder="请输入物料单位" />
                </el-form-item>
                <el-form-item label="需求数量" prop="requiredQuantity">
                    <el-input v-model="form.requiredQuantity" placeholder="请输入需求数量" />
                </el-form-item>
                <el-form-item label="需求日期" prop="requiredDate">
                    <el-date-picker clearable v-model="form.requiredDate" type="date" value-format="yyyy-MM-dd"
                        placeholder="请选择需求日期">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="发货单号" prop="deliveryNoteNo">
                    <el-input v-model="form.deliveryNoteNo" placeholder="请输入发货单号" />
                </el-form-item>
                <el-form-item label="发货单行号" prop="deliveryNoteLineNo">
                    <el-input v-model="form.deliveryNoteLineNo" placeholder="请输入发货单行号" />
                </el-form-item>
                <el-form-item label="已发货数量" prop="deliveredQuantity">
                    <el-input v-model="form.deliveredQuantity" placeholder="请输入已发货数量" />
                </el-form-item>
                <el-form-item label="已发货金额" prop="deliveredAmount">
                    <el-input v-model="form.deliveredAmount" placeholder="请输入已发货金额" />
                </el-form-item>
                <el-form-item label="供应商" prop="supplier">
                    <el-input v-model="form.supplier" placeholder="请输入供应商" />
                </el-form-item>
                <el-form-item label="交货日期" prop="deliveryDate">
                    <el-date-picker clearable v-model="form.deliveryDate" type="date" value-format="yyyy-MM-dd"
                        placeholder="请选择交货日期">
                    </el-date-picker>
                </el-form-item>
                
                <el-form-item label="入库单号" prop="stockInNoteNo">
                    <el-input v-model="form.stockInNoteNo" placeholder="请输入入库单号" />
                </el-form-item>
                <el-form-item label="入库单行号" prop="stockInNoteLineNo">
                    <el-input v-model="form.stockInNoteLineNo" placeholder="请输入入库单行号" />
                </el-form-item>
                <el-form-item label="退回数量" prop="returnedQuantity">
                    <el-input v-model="form.returnedQuantity" placeholder="请输入退回数量" />
                </el-form-item>
                <el-form-item label="入库金额" prop="stockInAmount">
                    <el-input v-model="form.stockInAmount" placeholder="请输入入库金额" />
                </el-form-item>
                <el-form-item label="入库数量" prop="stockInQuantity">
                    <el-input v-model="form.stockInQuantity" placeholder="请输入入库数量" />
                </el-form-item>
                <el-form-item label="入库人员" prop="stockInPerson">
                    <el-input v-model="form.stockInPerson" placeholder="请输入入库人员" />
                </el-form-item>
                <el-form-item label="入库日期" prop="stockInDate">
                    <el-date-picker clearable v-model="form.stockInDate" type="date" value-format="yyyy-MM-dd"
                        placeholder="请选择入库日期">
                    </el-date-picker>
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
import { listOrderDetail, getOrderDetail, delOrderDetail, addOrderDetail, updateOrderDetail, listOrderDelivery } from "@/api/pms/orderDetail";

export default {
    name: "OrderDetail",
    dicts: ['om_state', 'order_quanlity', 'order_handle'],
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
            // mingxi表格数据
            orderDetailList: [],
            // 弹出层标题
            title: "",
            // 是否显示弹出层
            open: false,
            // 查询参数
            queryParams: {
                pageNum: 1,
                pageSize: 10,
                orderHandle: null,
                deliveryNoteNo: null,
                supplier: null,
                receiver: null,
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
        /** 查询mingxi列表 */
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
                id: null,
                orderCode: null,
                orderHandle: null,
                orderQuanlity: null,
                orderStatus: null,
                totalDemand: null,
                deliveryQuantity: null,
                receivedQuantity: null,
                inStockQuantity: null,
                taxTotal: null,
                receivedAmount: null,
                awaitingDeliveryQuantity: null,
                awaitingDeliveryAmount: null,
                orderLineNo: null,
                materialCode: null,
                materialName: null,
                materialUnit: null,
                requiredQuantity: null,
                requiredDate: null,
                deliveryNoteNo: null,
                deliveryNoteLineNo: null,
                deliveredQuantity: null,
                deliveredAmount: null,
                supplier: null,
                deliveryDate: null,
                receiptNoteNo: null,
                receiptNoteLineNo: null,
                rejectedQuantity: null,
                receiveQuantity: null,
                receiver: null,
                receivedDate: null,
                stockInNoteNo: null,
                stockInNoteLineNo: null,
                returnedQuantity: null,
                stockInAmount: null,
                stockInQuantity: null,
                stockInPerson: null,
                stockInDate: null,
                createTime: null
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
            this.ids = selection.map(item => item.id)
            this.single = selection.length !== 1
            this.multiple = !selection.length
        },
        /** 新增按钮操作 */
        handleAdd() {
            this.reset();
            this.open = true;
            this.title = "添加mingxi";
        },
        /** 收货按钮操作 */
        handleUpdate(row) {
            this.reset();
            const id = row.id || this.ids
            getOrderDetail(id).then(response => {
                this.form = response.data;
                this.open = true;
                this.title = "修改mingxi";
            });
        },
        /** 提交按钮 */
        submitForm() {
            this.$refs["form"].validate(valid => {
                if (valid) {
                    if (this.form.id != null) {
                        updateOrderDetail(this.form).then(response => {
                            this.$modal.msgSuccess("修改成功");
                            this.open = false;
                            this.getList();
                        });
                    } else {
                        addOrderDetail(this.form).then(response => {
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
            this.$modal.confirm('是否确认删除mingxi编号为"' + ids + '"的数据项？').then(function () {
                return delOrderDetail(ids);
            }).then(() => {
                this.getList();
                this.$modal.msgSuccess("删除成功");
            }).catch(() => { });
        },
        /** 导出按钮操作 */
        handleExport() {
            this.download('pms/orderDetail/export', {
                ...this.queryParams
            }, `orderDetail_${new Date().getTime()}.xlsx`)
        }
    }
};
</script>
  