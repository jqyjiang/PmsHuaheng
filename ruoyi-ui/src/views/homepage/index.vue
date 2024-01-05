<template>
  <body id="body">
    <!-- 头 -->
    <div id="head">
      <div id="ab">
      <img src="./../../assets/images/ab.jpg"
       style="width:80px ;height: 80px;margin: 0px 10px;float: left;">
       <div style="padding-left: 20px;">
       <h4>采购部</h4>
       <span id="the">The purchasing department</span>
      </div>
      </div>
      <div id="cd">
        <ul>
        <li><router-link to="/procure/pool"><img src="./../../assets/images/a1.jpg"
       style="width:40px ;height: 40px;margin: 0px 5px;float: left;"></router-link></li>
        <li><router-link to="/procure/requirement"><img src="./../../assets/images/a2.jpg"
       style="width:40px ;height: 40px;margin: 0px 5px;float: left;"></router-link></li>
       <li><router-link to="/supplierpms/details"><img src="./../../assets/images/a3.jpg"
       style="width:40px ;height: 40px;margin: 0px 5px;float: left;"></router-link></li>
        <li><router-link to="/pms/manager"><img src="./../../assets/images/a4.jpg"
       style="width:40px ;height: 40px;margin: 0px 5px;float: left;"></router-link></li>
      </ul>
      <ul>
        <li>采购需求</li>
        <li>采购申请</li>
        <li>采购供应商</li>
        <li>采购订单</li>
      </ul>
      </div>
    </div>
    <div id="below">
     <h4 style="margin-left: 20px;padding-top: 10px;color: #4965b7;">项目看板</h4>
     <ul>
      <li><div class="he1">
        <div class="shu">1</div>
        <span class="h6">采购需求申请</span>
        <div style="background-color: white;height: 1px;width: 100%;float: left;margin:5px 0px;"></div>
        <div class="h5"><span>申请总数:</span><br><div style="margin-top: 18px;">{{ applyNum }}</div></div>
        <div class="h5"><span>未执行:</span><br><div style="margin-top: 18px;">5</div></div>
      </div></li>
      <li><div class="he2">
        <div class="shu">2</div>
        <span class="h6">订单确认</span>
        <div style="background-color: white;height: 1px;width: 100%;float: left;margin: 5px 0px;"></div>
        <div class="h5"><span>订单总数:</span><br><div style="margin-top: 18px;">{{ orderTotal }}</div></div>
        <div class="h5"><span>订单总金额:</span><br><div style="margin-top: 18px;">{{ orderTotalAmount!=null&&!isNaN(orderTotalAmount)
                ?parseFloat(orderTotalAmount).toFixed(2) :'0.00' }}</div></div>
      </div></li>
      <li><div class="he3">
        <div class="shu">3</div>
        <span class="h6">合同生效</span>
        <div style="background-color: white;height: 1px;width: 100%;float: left;margin: 5px 0px;"></div>
        <div class="h5"><span>合同总数:</span><br><div style="margin-top: 18px;">{{ mentNum }}</div></div>
        <div class="h5"><span>未激活:</span><br><div style="margin-top: 18px;">{{ mentNumNo }}</div></div>
      </div></li>
      <li><div class="he4">
        <div class="shu">4</div>
        <span class="h6">交货及时率</span>
        <div style="background-color: white;height: 1px;width: 100%;float: left;margin: 5px 0px;"></div>
        <div class="h5"><span>待收货数:</span><br><div style="margin-top: 18px;">{{ treatNum }}</div></div>
        <div class="h5"><span>收货总金额:</span><br><div style="margin-top: 18px;">{{ treatMoney!=null&&!isNaN(treatMoney)
                ?parseFloat(treatMoney).toFixed(2) :'0.00' }}</div></div>
      </div></li>
      <li><div class="he5">
        <div class="shu">5</div>
        <span class="h6">质检</span>
        <div style="background-color: white;height: 1px;width: 100%;float: left;margin: 5px 0px;"></div>
        <div class="h5"><span>处理总数:</span><br><div style="margin-top: 18px;">{{ inspectTotal }}</div></div>
        <div class="h5"><span>未执行:</span><br><div style="margin-top: 18px;">{{ inspectTotalNo }}</div></div>
      </div></li>
      <li><div class="he6">
        <div class="shu">6</div>
        <span class="h6">对账结算</span>
        <div style="background-color: white;height: 1px;width: 100%;float: left;margin: 5px 0px;"></div>
        <div class="h5"><span>申请总数:</span><br><div style="margin-top: 18px;">{{ reconciliationNum }}</div></div>
        <div class="h5"><span>已拒绝:</span><br><div style="margin-top: 18px;">{{ reconciliationRefuseNum }}</div></div>
      </div></li>
    </ul>
    </div>
    <!-- 中 -->
    <div id="centre">
      <div id="centre-right">
      <div id="left">
        <h4 style="margin-left: 20px;color: #4965b7;">采购订单</h4>
        <el-table :data="managerList" >
        <!-- <el-table-column type="selection" width="55" align="center" /> -->
        <el-table-column label="订单号" align="center" prop="orderCode" width="150px">
          <template slot-scope="scope">
            <span @click="redirectToOtherPage(scope.row.orderId)">{{ scope.row.orderCode }}</span>
          </template>
        </el-table-column>
        <el-table-column label="供应商" align="center" prop="supplierDetails.sbiName" />
        <el-table-column label="含税总金额" align="center" prop="taxTotal">
          <template slot-scope="scope">
            <span>
              {{ scope.row.taxTotal !== null && !isNaN(scope.row.taxTotal)
                ? parseFloat(scope.row.taxTotal).toFixed(2)
                : '0.00' }}
            </span>
          </template>
        </el-table-column>
        <el-table-column label="审批状态" align="center" prop="orderState">
          <template slot-scope="scope">
            <span :class="getStatusClass(scope.row.orderState)">
              <dict-tag :options="dict.type.order_state" :value="scope.row.orderState" />
            </span>
          </template>
        </el-table-column>
      </el-table>
      </div>

      <div id="right">
      <h4 style="margin-left: 20px;color: #4965b7;">供应商信息</h4>
      <el-table v-loading="loading" :data="supplierList" >
      <el-table-column label="公司编码" align="center" prop="sdCode" />
      <el-table-column label="企业名称" align="center" prop="sbiName" />
      <el-table-column label="企业类型" align="center" prop="sdClass" >
        <template slot-scope="scope">
          <span v-for="item in classificationList">
            <template v-if="scope.row.sdClass===item.classCode">
              {{item.classDescribe}}
            </template>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="服务范围" align="center" prop="sbiServe">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.service_scope" :value="scope.row.sbiServe ? scope.row.sbiServe.split(',') : []" />
        </template>
      </el-table-column>
    </el-table>
      </div>
    </div>
    </div>
    <!-- 下 -->

  </body>
  </template>

  <script>
import { listManager,listDetails,listClassification,applyNum,reconciliationRefuseNum,reconciliationNum,orderTotal,orderTotalAmount,
mentNum,mentNumNo,inspectTotalNo,inspectTotal} from "@/api/chart/homepage";
import { treatNum,treatMoney} from "@/api/chart/order";

import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

  export default {
    name: "Supplier",
    dicts: ['service_scope','order_state'],
    routes: [
    {
      path: '/procure/pool',
      name: 'ProcurePool',
      component: () => import('@/views/procure/pool/index.vue')
    },
    {
      path: '/procure/requirement',
      name: 'ProcureRequirement',
      component: () => import('@/views/procure/requirement/index.vue')
    },
    {
      path: '/supplierpms/details',
      name: 'Supplierpms',
      component: () => import('@/views/supplierpms/details/index.vue')
    },
    {
      path: '/pms/manager',
      name: 'PMSManager',
      component: () => import('@/views/pms/manager/index.vue')
    }

  ],
    data(){
      return {
        applyNum:"",
        reconciliationRefuseNum:"",
        reconciliationNum:"",
        orderTotalAmount:"",
        orderTotal:"",
        mentNum:"",
        mentNumNo:"",
        treatNum:"",
        treatMoney:"",
        inspectTotalNo:"",
        inspectTotal:"",
        // 供应商详细表格数据
      supplierList: [],
      classificationList:[],
      // 查询参数
      rwqueryParams: {
        pageNum: 1,
        pageSize: 10,
        taskCode: null,
      },
      rwtotal: 0,
      // 遮罩层
      loading: true,
      taskList: [],
      //用于展示执行状态的个数
      runNumber: [],
      // 选中数组
      ids: [],
      //供应商名称
      supplyName: '',
      //控制是否启用计算属性计算需求总数量和含税金额
      isCalculationNeeded: true,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      //订单物料明细对比数据
      listOrderMaterial: [],
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      //选中税率数组
      rateId: [],
      rtotal: 0,

      // 查询参数
      ormqueryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      materialqueryParams: {
        pageNum: 1,
        pageSize: 10,
        orName: null,
        supply: null,
        orderSource: null,
        orderCode: null
      },

      // 采购订单管理表格数据
      managerList: [],
      // 供应商列表表格数据
      detailsList: [],
      // 订单物料明细表格数据
      orderMaterialList: [],
      // 物料基本信息
      materialList: [],
      //订单物料基本信息表明细
      materiaslList: [],
      //供应商总条数
      stotal: 0,
      //物料基本信息总条数
      mtotal: 0,
      //品类对象列表
      categoryList: [],
      // 品类对象查询参数
      caqueryParams: {
        pageNum: 1,
        pageSize: 10,
        categoryCode: null,
        categoryName: null,
        superiorCategory: null,
      },
      //品类对象总数
      catotal: 0,
      //供应商查询参数
      squeryParams: {
        pageNum: 1,
        pageSize: 10,
        sdCode: null,
        sbiName: null,
        phone: '',
        people: '',
      },
      //物料基本信息查询参数
      mqueryParams: {
        pageNum: 1,
        pageSize: 10,
        materialCode: null,
        materialName: null,
      },
      // 税率表格数据
      rateList: [],
      // 查询参数
      rqueryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      //添加物料基本信息
      materialInfo: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 币种定义名称
      currencyName: '',
      //执行状态列表
      typeRunList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 9,
        orderCode: null,
        orderSource: null,
        orderState: null,
        supplier: null,
        //根据执行状态Id查询
        orId: null,
      },

      forms: {
        batchRequireTime: '',
        batchRequireNumber: '',
        batchConsignee: '',
        batchReceivingAddress: '',
        batchReceivingPhone: ''
      },
      // 表单参数
      form: {
      },
      // 表单校验
      rules: {
      },
      dialogVisible: false, // 用于标记供应商弹窗是否可见
      dialogVisible1: false, // 用于标记供应商弹窗是否可见
      dialogMaterial: false, //用于标记物料信息表是否可见
      dialogCurrency: false, //用于标记物料信息表是否可见
      dialogMaterial1: false,
      dialogMaterial2: false,
      // 批量新增对话框的可见性
      batchAddDialogVisible: false,
      // 批量新增数据列表
      batchAddDataList: [],
      checkedOrderMaterials: null,
      //需求总数修改
      number: 0,
      //含税总金额
      taxTotal: 0,
      //执行状态查询
      formData: {},
      selectedRoute: null,
      ormtotal: 0,
      drawer: false,
      direction: 'rtl',
      checkedOrderMaterials: [],
      //关闭订单的集合或对象
      orderCancel: [],
      //获取到订单明细表格下标
      materialIndex: 0,
      //获取新建送货单的订单信息
      invoiceList: [],
      //新建送货单参数
      invoiceForm: {

      },

      materialIndex1: 0,

    };
    },
    created(){
      this.getList();
      this.getList1();
      this.getList2();
      this.data1();
      this.data2();
      this.data3();
      this.data4();
      this.data5();
      this.data6();
      this.data7();
      this.data8();
      this.data9();
      this.data10();
      this.data11();
    },
    methods:{
       /** 查询供应商详细列表 */
    getList1() {
      this.loading = true;
      listDetails(this.queryParams).then((response) => {
        this.supplierList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询供应商分类定义列表 */
    getList2() {
      this.loading = true;
      listClassification(this.queryParams).then(response => {
        this.classificationList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    data1(){
      applyNum().then(response=>{
        this.applyNum=response;
      })
    },
    data2(){
      reconciliationRefuseNum().then(response=>{
        this.reconciliationRefuseNum=response;
      })
    },
    data3(){
      reconciliationNum().then(response=>{
        this.reconciliationNum=response;
      })
    },
    data4(){
      orderTotal().then(response=>{
        this.orderTotal=response;
      })
    },
    data5(){
      orderTotalAmount().then(response=>{
        this.orderTotalAmount=response;
      })
    },
    data6(){
      mentNum().then(response=>{
        this.mentNum=response;
      })
    },
    data7(){
      mentNumNo().then(response=>{
        this.mentNumNo=response;
      })
    },
    data8(){
      treatNum().then(response=>{
        this.treatNum=response;
      })
    },
    data9(){
      treatMoney().then(response=>{
        this.treatMoney=response;
      })
    },
    data10(){
      inspectTotal().then(response=>{
        this.inspectTotal=response;
      })
    },
    data11(){
      inspectTotalNo().then(response=>{
        this.inspectTotalNo=response;
      })
    },

       //控制订单审批状态的颜色
    getStatusClass(orderState) {
      if (orderState === 1) {
        return 'status-pending'; // 新建样式类
      } else if (orderState === 2) {
        return 'status-canceled'; // 审批中样式类
      } else if (orderState === 3) {
        return 'status-approved'; // 审批通过样式类
      } else if (orderState === 4) {
        return 'status-rejected'; // 审批拒绝样式类
      }
    },
    /** 查询采购订单管理列表 */
    getList() {
      this.loading = true;
      listManager(this.queryParams).then(response => {
        this.managerList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    }
  };
  </script>
<style>
  #the{
    font-size: 12px;
    color: #888888;
  }
  #ab{
    width: 25%;
    display: inline-block;
    border-right: 1px solid #eee;
    position: relative;
    vertical-align: middle;
    float: left;
  }
  #body{
    background-color: #E8EDF4;
  }
  #head{
    background-color: white;
    margin:10px;
    /* border: 1px solid black; */
    height: 90px;
  }
  #cd li{
    list-style: none;
    float: left;
    width: 25%;
    font-size: 12px;
    /* border: 1px solid black; */
  }
  #cd{
    width: 75%;
    float: left;
    /* border: 1px solid black; */
  }
  #centre{
    margin-top:10px;
    width: 100%;
    height: 500px;
    /* border: 1px solid black; */
    /* background-color: white; */
  }
  #left{
    width: 49%;
    height: 500px;
    float: left;
    /* border: 1px solid black; */
    background-color: white;
    margin-left: 0.5%;
    margin-right: 1%;
  }
  #right{
    width: 49%;
    height: 500px;
    float: left;
    /* border: 1px solid black; */
    background-color: white;
    margin-right: 0.5%;
  }
  #below{
    width: 99%;
    margin-left:0.5%;
    margin-right: 0.5%;
    margin-top: 10px;
    height: 200px;
    background-color: white;
  }
  #below li{
    list-style: none;
    width: 16%;
    float: left;
  }
  .h6{
    float: left;
    color:white;
    font-weight: bold;
    font-size: 13px;
    margin-left: 10px;
  }
  .h5{
    float: left;
    color:white;
    font-size: 12px;
    margin-left: 13px;
    width: 40%;
    height: 55px;
    /* border: 1px solid black; */
  }
  .he1{
    height: 100px;
    width: 95%;
    background-color: #27817a;
    padding-top: 10px;
    border-radius: 10px;
  }
  .he2{
    height: 100px;
    width: 95%;
    background-color: #ea9060;
    padding-top: 10px;
    border-radius: 10px;
  }
  .he3{
    height: 100px;
    width: 95%;
    background-color: #039f94;
    padding-top: 10px;
    border-radius: 10px;
  }
  .he4{
    height: 100px;
    width: 95%;
    background-color: #ffa83a;
    padding-top: 10px;
    border-radius: 10px;
  }
  .he5{
    height: 100px;
    width: 95%;
    background-color: #a09da5;
    padding-top: 10px;
    border-radius: 10px;
  }
  .he6{
    height: 100px;
    width: 95%;
    background-color: #ff9050;
    padding-top: 10px;
    border-radius: 10px;
  }
  .status-pending {
  display: inline-block;
  width: 85px;
  border: 1px solid #ff9800;
  border-radius: 5px;
  color: #ff9800;
}

.status-canceled {
  display: inline-block;
  width: 85px;
  border: 1px solid #9e9e9e;
  border-radius: 5px;
  color: #9e9e9e;
}

.status-approved {
  display: inline-block;
  width: 85px;
  border: 1px solid #4caf50;
  border-radius: 5px;
  color: #4caf50;
}

.status-rejected {
  border: 1px solid #f44336;
  display: inline-block;
  width: 85px;
  border-radius: 5px;
  color: #f44336;
}
.shu{
  border-radius: 50%;
  background-color: white;
  width: 20px;
  height: 20px;
  text-align: center;
  font-weight: bold;
  margin-left: 10px;
  float: left;
}
</style>
