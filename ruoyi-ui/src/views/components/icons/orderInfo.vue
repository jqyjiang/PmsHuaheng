<template>
    <div>
        <h1 style="text-align: center; background-color: #576980; color: #fff; height: 100px; line-height: 100px;">采购订单执行明细
        </h1>
        <!--新建送货单-->
        <el-divider>基本信息</el-divider>
        <el-table>
          <el-table-column label="订单编号">
                <span>1111</span>
          </el-table-column>
          <el-table-column label="税后总金额">
                <span>122221</span>
          </el-table-column>
        </el-table>
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
            <el-form-item label="订单编号" prop="orderCode">
                <el-input v-model="form.orderCode" placeholder="请输入订单编号" />
            </el-form-item>
            <el-form-item label="订单状态" prop="orderStatus">
                <el-select v-model="form.orderStatus" placeholder="请选择订单状态">
                    <el-option v-for="dict in dict.type.om_state" :key="dict.value" :label="dict.label"
                        :value="dict.value"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="总需求量" prop="totalDemand">
                <el-input v-model="form.totalDemand" placeholder="请输入总需求量" />
            </el-form-item>
            <el-form-item label="交货数量" prop="deliveryQuantity">
                <el-input v-model="form.deliveryQuantity" placeholder="请输入交货数量" />
            </el-form-item>
            <el-form-item label="已收货数量" prop="receivedQuantity">
                <el-input v-model="form.receivedQuantity" placeholder="请输入已收货数量" />
            </el-form-item>
            <el-form-item label="库存数量" prop="inStockQuantity">
                <el-input v-model="form.inStockQuantity" placeholder="请输入库存数量" />
            </el-form-item>
            <el-form-item label="税额总计" prop="taxTotal">
                <el-input v-model="form.taxTotal" placeholder="请输入税额总计" />
            </el-form-item>
            <el-form-item label="已收款金额" prop="receivedAmount">
                <el-input v-model="form.receivedAmount" placeholder="请输入已收款金额" />
            </el-form-item>
            <el-form-item label="待交货数量" prop="awaitingDeliveryQuantity">
                <el-input v-model="form.awaitingDeliveryQuantity" placeholder="请输入待交货数量" />
            </el-form-item>
            <el-form-item label="待交货金额" prop="awaitingDeliveryAmount">
                <el-input v-model="form.awaitingDeliveryAmount" placeholder="请输入待交货金额" />
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
                <el-input v-model="form.deliveryNoteNo" placeholder="请输入交货单号" />
            </el-form-item>
            <el-form-item label="发货单行号" prop="deliveryNoteLineNo">
                <el-input v-model="form.deliveryNoteLineNo" placeholder="请输入交货单行号" />
            </el-form-item>
            <el-form-item label="已发货数量" prop="deliveredQuantity">
                <el-input v-model="form.deliveredQuantity" placeholder="请输入已交货数量" />
            </el-form-item>
            <el-form-item label="供应商" prop="supplier">
                <el-input v-model="form.supplier" placeholder="请输入供应商" />
            </el-form-item>
            <el-form-item label="发货日期" prop="deliveryDate">
                <el-date-picker clearable v-model="form.deliveryDate" type="date" value-format="yyyy-MM-dd"
                    placeholder="请选择交货日期">
                </el-date-picker>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitForm">确 定</el-button>
            <el-button @click="cancel">取 消</el-button>
        </div>
    </div>
</template>

<script>
import { getOrderManager, getManager } from "@/api/pms/manager";
import SubmitResult from './SubmitResult.vue';
export default {
    name: 'orderInfo',
    dicts: ['self_pickup', 'order_state', 'order_type', 'order_source', 'procure', 'supplier_invoice', 'invoice_method', 'om_state'],
    components: {
        SubmitResult
    },
    data() {
        return {
            orderId: null,
            orderManager: {

            },
            form: {

            },
            orderMaterialList: [],
            rules: {

            },
        }
    },
    created() {
        this.orderId = this.$route.params.orderCode
    },
    methods: {
        getList() {
            getManager(this.orderId).then(response => {
                this.form = response.data;
                this.orderMaterialList = response.data.orderMaterialList
            });
        },
    }
}
</script>

<style>
</style>