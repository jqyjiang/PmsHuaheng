<template>
  <div class="app-container">
    <div style="background-color: #f9f9f9; border: 1px solid #eaeaea; padding: 10px;">
      <div style="display: flex; align-items: center; justify-content: space-between; ">
        <div>
          <!-- 左边的图片和文字 -->
          <img :src="headerImages" alt="采购订单管理"
            style="width: 40px; height: 40px;margin-right: 10px; vertical-align: middle;">
          <span style="font-size: 18px; line-height: 40px; vertical-align: middle;">采购订单管理</span>
        </div>

        <div>
          <!-- 右边的三个按钮 -->
          <el-button type="primary" size="small" @click="handleConvertToOrder">需求转订单</el-button>
          <el-button type="primary" size="small" @click="handleContractToOrder">合同转订单</el-button>
          <el-button type="primary" size="small" @click="handleCloseOrder">关闭订单</el-button>
        </div>
      </div>
    </div>
    <div style="background-color: #f9f9f9; border: 1px solid #eaeaea; padding: 10px;">
        <router-link to="/pms/order">整单</router-link>
     <router-link to="/pms/info">订单明细</router-link>
     <router-view></router-view>
    </div>
     
  </div>
</template>

<script>
export default {
data(){
    return{
        headerImages: require('../../../assets/images/order_main_header1.png')
    }
}
}
</script>

<style>

</style>