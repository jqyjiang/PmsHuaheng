<template>
    <div id="head">
        <ul>
            <li>
                <h4>待收货订单</h4>
                <h2>{{ treatNum }}</h2>
            </li>
            <li>
                <h4>待收货金额(元)</h4>
                <h2>{{ treatMoney!=null&&!isNaN(treatMoney)
                ?parseFloat(treatMoney).toFixed(2) :'0.00'}}</h2>
            </li>
            <li>
                <h4>完成订单</h4>
                <h2>{{ stopNum }}</h2>
            </li>
            <li>
                <h4>完成订单金额</h4>
                <h2>{{ completeMoney!=null&&!isNaN(completeMoney)
                ?parseFloat(completeMoney).toFixed(2) :'0.00' }}</h2>
            </li>
        </ul>

        <!-- 订单来源系统 -->
        <div id="myEcharts" ref="pieChart" style="width:50%;height:400px;border:1px solid gold;float: left;"></div>
        <!-- 月度订单数据统计 -->
        <div id="lifeCycle" ref="barChart" style="width: 50%; height: 400px ;float:left;border:1px solid gold;"></div>
        <!-- 月度订单金额统计 -->
        <div ref="chart" style="width: 100%; height: 400px ;float:left;border:1px solid gold;"></div>
    </div>
</template>
<script>
import * as echarts from 'echarts';
import { listOrderSource ,listMonthOrder,listMonthOrderMoney,treatNum,treatMoney,stopNum,completeMoney} from "@/api/chart/order";

export default {
  data() {
    return {
      orderSourceList: [],//订单来源系统后台数据
      monthOrderList:[],
      monthOrderMoneyList:[],
      treatNum:"",
      treatMoney:"",
      stopNum:"",
      completeMoney:"",
    };
  },
  filters: {
    formatNumber(value) {
      // 将数值转换为带小数点的形式
      return value.toFixed(2);
    }
  },
  mounted() {
    this.fetchData();
    this.fetchData1();
    this.getOrderMonthMoneyList();
    this.data1();
    this.data2();
    this.data3();
    this.data4();
  },
  methods: {
    //订单来源系统
    fetchData() {
        listOrderSource().then(response => {
          this.orderSourceList = response.rows;
          this.renderPieChart();
        })
        .catch(error => {
          console.error('Error fetching order source data: ', error);
        });
    },
    renderPieChart() {
      const pieChart = echarts.init(this.$refs.pieChart);

      const option = {
        title: {
          text: '订单来源系统数量',
          left: 'center',
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)',
        },
        legend: {
          orient: 'vertical',
          left: 'left',
          data: this.orderSourceList.map(item => item.orderSource),
        },
        series: [
          {
            name: '订单来源系统',
            type: 'pie',
            radius: ['50%', '70%'],
            avoidLabelOverlap: false,
            label: {
              show: false,
              position: 'center',
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '30',
                fontWeight: 'bold',
              },
            },
            labelLine: {
              show: false,
            },
            data: this.orderSourceList.map(item => ({
              value: item.oCount,
              name: item.orderSource,
            })),
          },
        ],
      };

      pieChart.setOption(option);
    },
    //月度订单
    fetchData1() {
        listMonthOrder().then(response => {
          this.monthOrderList = response.rows;
          // console.info(this.monthOrderList)
          this.renderBarChart();
        })
        .catch(error => {
          console.error('Error fetching order month data: ', error);
        });
    },
    renderBarChart() {
      const barChart = echarts.init(this.$refs.barChart);

      const option = {
        title: {
          text: '订单月度订单数量',
          left: 'center',
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow',
          },
        },
        legend: {
          data: ['订单数量'],
          left: 'left',
        },
        xAxis: {
          type: 'category',
          data: this.monthOrderList.map(item => item.month),
          axisLabel: {
            rotate: 45,
          },
        },
        yAxis: {
          type: 'value',
        },
        series: [
          {
            name: '订单数量',
            type: 'bar',
            data: this.monthOrderList.map(item => item.mCount),
          },
        ],
      };

      barChart.setOption(option);
    },
    //月度订单金额
    getOrderMonthMoneyList() {
      // 假设使用axios库请求后台接口
      listMonthOrderMoney().then(res => {
        this.orderMonthMoneyList = res.rows || [];
        this.renderChart(); // 数据获取成功后渲染图表
      }).catch(err => {
        console.error(err);
      });
    },
    renderChart() {
      // 处理后台数据
      const months = this.orderMonthMoneyList.map(item => item.month);
      const amounts = this.orderMonthMoneyList.map(item => item.aMoney);

      // 初始化echarts实例
      const chartDom = this.$refs.chart;
      const myChart = echarts.init(chartDom);

      // 配置折线图选项
      const option = {
        title: {
          text: '订单月度订单金额',
          left: 'center',
        },
        xAxis: {
          type: 'category',
          data: months
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          data: amounts,
          type: 'line'
        }]
      };

      // 使用刚指定的配置项和数据显示图表
      myChart.setOption(option);
    },
    data1(){
      treatNum().then(response => {
          this.treatNum = response;
          // console.log(response.data);
        })
    },
    data2(){
      treatMoney().then(response => {
          this.treatMoney = response;
          // console.log(this.treatMoney);
        })
    },
    data3(){
      stopNum().then(response => {
          this.stopNum = response;
          // console.log(this.stopNum);
        })
    },
    data4(){
      completeMoney().then(response => {
          this.completeMoney = response;
          // console.log(this.completeMoney);
        })
    }

}
}
</script>

<style>
#head li{
flex: 0 0 auto;
width: 25%;
margin-right: 8px;
border: 1px solid #EEF0F2;
list-style-type: none;
height: 130px;
background-color: white;
}
#head li h4{
margin-left: 10px;
font-weight: bold;
}
#head ul{
    display: flex;
    list-style-type: none;
    padding: 0;
    margin: 10px 10px;
}
#head{
    width: 100%;
    /* height: 100%; */
    background-color: #EEF0F2;
}
#head li h2{
margin-left: 10px;
}
</style>
