<template>
    <div id="head">
        <ul>
            <li>
                <h4>待收货订单</h4>
                <h2></h2>
            </li>
            <li>
                <h4>待收货金额(元)</h4>
                <h2></h2>
            </li>
            <li>
                <h4>完成订单</h4>
                <h2></h2>
            </li>
            <li>
                <h4>完成订单金额</h4>
                <h2></h2>
            </li>
        </ul>

        
        <!-- 订单来源系统 -->
        <div id="myEcharts" ref="pieChart" style="width:50%;height:400px;border:1px solid gold;float: left;"></div>
        <!-- 月度订单来源统计 -->
        <div id="lifeCycle" ref="barChart" style="width: 50%; height: 400px ;float:left;border:1px solid gold;"></div>
    </div>
</template>
<script>
import * as echarts from 'echarts';
import { listOrderSource ,listMonthOrder} from "@/api/chart/order";

export default {
  data() {
    return {
      orderSourceList: [],//订单来源系统后台数据
      monthOrderList:[],
    };
  },
  mounted() {
    this.fetchData();
    this.fetchData1();
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
          console.info(this.monthOrderList)
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
</style>