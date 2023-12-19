<template>
    <div>
        <h1 style="text-align: center; background-color: #576980; color: #fff; height: 100px; line-height: 100px;">采购订单审批流程</h1>
        <!-- <p>提交的数据：{{ submittedData }}</p> -->
        <!-- 下一步操作按钮等... -->
        <!-- 添加或修改采购订单管理对话框 -->
        <!-- <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body> -->
        <el-form ref="form" :model="form" :rules="rules" label-width="100px">
            <el-form-item label="采购订单编号" prop="orderCode">
                <el-input v-model="form.orderCode" placeholder="请输入采购订单编号" disabled />
            </el-form-item>
            <el-form-item label="公司" prop="company">
                <el-input v-model="form.company" placeholder="请输入公司" />
            </el-form-item>
            <el-form-item label="订单类型" prop="orderType">
                <el-select v-model="form.orderType" placeholder="请选择订单类型">
                    <el-option v-for="dict in dict.type.order_type" :key="dict.value" :label="dict.label"
                        :value="parseInt(dict.value)"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="订单来源" prop="orderSource">
                <el-select v-model="form.orderSource" placeholder="请选择订单来源">
                    <el-option v-for="dict in dict.type.order_source" :key="dict.value" :label="dict.label"
                        :value="dict.value"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="采购组织" prop="purOrganization">
                <el-select v-model="form.purOrganization" placeholder="请选择采购组织">
                    <el-option v-for="dict in dict.type.procure" :key="dict.value" :label="dict.label"
                        :value="parseInt(dict.value)"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="附件" prop="annex">
                <file-upload v-model="form.annex" />
            </el-form-item>
            <el-form-item label="是否自提" prop="isSelfPickup">
                <el-select v-model="form.isSelfPickup" placeholder="请选择是否自提">
                    <el-option v-for="dict in dict.type.self_pickup" :key="dict.value" :label="dict.label"
                        :value="parseInt(dict.value)"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="自提车牌号" prop="licensePlateNumber">
                <el-input v-model="form.licensePlateNumber" 
                    />
            </el-form-item>
            <el-form-item label="自提司机联系方式" prop="concatInfomation">
                <el-input v-model="form.concatInfomation" 
                     />
            </el-form-item>
            <el-form-item label="关联合同编号" prop="contractCode">
                <el-input v-model="form.contractCode" placeholder="请输入关联合同编号" disabled />
            </el-form-item>
            <el-form-item label="关联合同名称" prop="contractName">
                <el-input v-model="form.contractName" placeholder="请输入关联合同名称" />
            </el-form-item>
            <el-form-item label="订单说明" prop="orderDescription">
                <el-input v-model="form.orderDescription" placeholder="请输入订单说明" />
            </el-form-item>
            <el-form-item label="需求总数量" prop="totalDemand">
                <el-input v-model="form.totalDemand"
                    />
            </el-form-item>
            <el-form-item label="含税总金额(元)" prop="taxTotal">
                <el-input v-model="form.taxTotal"
                   />
            </el-form-item>
            <el-form-item label="采购员" prop="purchaser">
                <el-input v-model="form.purchaser"  />
            </el-form-item>
            <el-form-item label="采购审批状态" prop="orderState">
                <el-select v-model="form.orderState" >
                    <el-option v-for="dict in dict.type.order_state" :key="dict.value" :label="dict.label"
                        :value="parseInt(dict.value)"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="币种" prop="currency.currencyName">
                <el-input v-model="form.currencyId"  />
            </el-form-item>
            <el-form-item label="供应商名称" prop="supplierDetails.sbiName">
                <el-input v-model="form.supplier" />
            </el-form-item>
            <el-form-item label="联系人" prop="contacts">
                <el-input v-model="form.contacts"  />
            </el-form-item>
            <el-form-item label="联系电话" prop="phone">
                <el-input v-model="form.phone"  />
            </el-form-item>
            <el-form-item label="供应商发票方式" prop="invoiceMethod">
                <el-select v-model="form.invoiceMethod" >
                    <el-option v-for="dict in dict.type.invoice_method" :key="dict.value" :label="dict.label"
                        :value="parseInt(dict.value)"></el-option>
                </el-select>
            </el-form-item>
            <el-table :data="orderMaterialList" 
                 ref="orderMaterial">
                <el-table-column type="selection" width="50" align="center" />
                <el-table-column label="序号" align="center" prop="index" width="50" />
                <el-table-column label="物料编码" prop="orCode" width="150">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.orCode" placeholder="" />
                    </template>
                </el-table-column>
                <el-table-column label="物料名称" prop="orName" width="150">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.orName"/>
                    </template>
                </el-table-column>
                <el-table-column label="品类编码" prop="categoryCode" width="150">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.categoryCode" />
                    </template>
                </el-table-column>
                <el-table-column label="物料品类" prop="materialCategory" width="150">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.materialCategory"/>
                    </template>
                </el-table-column>
                <el-table-column label="物料规格" prop="materialSpecification" width="150">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.materialSpecification" />
                    </template>
                </el-table-column>
                <el-table-column label="物料型号" prop="materialModel" width="150">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.materialModel"/>
                    </template>
                </el-table-column>
                <el-table-column label="物料单位" prop="materialUnit" width="150">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.materialUnit"/>
                    </template>
                </el-table-column>
                <el-table-column label="需求数量" prop="requireNumber" width="150">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.requireNumber"/>
                    </template>
                </el-table-column>
                <el-table-column label="需求日期" prop="requireTime" width="240">
                    <template slot-scope="scope">
                        <el-date-picker clearable v-model="scope.row.requireTime" type="date" value-format="yyyy-MM-dd"
                            />
                    </template>
                </el-table-column>
                <el-table-column label="历史最低价" prop="lowerPrice" width="150">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.lowerPrice" />
                    </template>
                </el-table-column>
                <el-table-column label="最新价格" prop="newPrice" width="150">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.newPrice"/>
                    </template>
                </el-table-column>
                <el-table-column label="不含税单价" prop="noTaxPrice" width="150">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.noTaxPrice"/>
                    </template>
                </el-table-column>
                <el-table-column label="税率代码" prop="taxCode" width="150">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.taxCode"/>
                    </template>
                </el-table-column>
                <el-table-column label="税率" prop="tax" width="150">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.tax"/>
                    </template>
                </el-table-column>
                <el-table-column label="含税单价" prop="taxPrice" width="150">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.taxPrice"  />
                    </template>
                </el-table-column>
                <el-table-column label="行含税金额" prop="lineTaxAmount" width="150">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.lineTaxAmount" />
                    </template>
                </el-table-column>
                <el-table-column label="收货人" prop="consignee" width="150">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.consignee"/>
                    </template>
                </el-table-column>
                <el-table-column label="收货电话" prop="receivingPhone" width="150">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.receivingPhone" />
                    </template>
                </el-table-column>
                <el-table-column label="收货地址" prop="receivingAddress" width="150">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.receivingAddress" />
                    </template>
                </el-table-column>
                <el-table-column label="备注" prop="remarks" width="150">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.remarks"  />
                    </template>
                </el-table-column>
            </el-table>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitForm">保 存</el-button>
            <!-- <el-button type="primary" @click="cancel">取 消</el-button> -->
            <!--审批通过-->
            <el-button type="primary" @click="approval">批 准</el-button>
            <!--审批拒绝-->
            <el-button type="primary" @click="cancel">退 回</el-button>
             <!--
                审批拒绝
             -->
            <el-button type="primary" @click="cancel">提交</el-button>
        </div>
    </div>
</template>

<script>
import {  getManager, updateManager,  updateManagerState } from "@/api/pms/manager";
import {addOrderDetails} from "@/api/pms/orderDetail";
export default {
    name: 'SubmitResult',
    dicts: ['self_pickup', 'order_state', 'order_type', 'order_source', 'procure', 'supplier_invoice', 'invoice_method', 'om_state'],
    data() {
        return {
            orderId: null,
            supplyName:null,
            currencyName:null,
            rules:{

            },
            form:{

            },
            orderMaterialList:[]
        };
    },
    created() {
        this.form = this.$route.params.formData;
        this.orderId = this.form.orderId
        this.getList();
    },
    methods: {
        getList() {
            getManager(this.orderId).then(response => {
                this.form = response.data;
                this.orderMaterialList = response.data.orderMaterialList
            });
        },
        /**
         * 退回即审批拒绝
         */
        cancel(){
            //审批拒绝
            this.form.orderState=4
           //退回到刚才的路径
           updateManagerState(this.form).then(response=>{
            
                this.$modal.msgSuccess("新增成功");
                //返回到指定的路由地址
            })
           this.$router.go(-1);
        },
        /**
         * 刷新本页面
         */
        submitForm(){

        },
        /**
         * 批准  将采购订单审批状态状态改为审批通过
         */
        approval(){
            //审批通过
            this.form.orderState=3
            updateManager(this.form).then(response=>{
                 this.$modal.msgSuccess("新增成功");
                //指定到订单执行明细的路由地址
                this.$router.push('/pms/manager'); // 替换为您要返回的具体路由地址
                //批准通过之后进行订单执行明细的操作
                //插入订单明细表的部分字段数据
                // addOrderDetails(this.form).then(response=>{
                   
                // })
            })
        }
    }
}
</script>

<style>
</style>