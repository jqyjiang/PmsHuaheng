<template>
    <div class="echartsMapAllDemo">
        <!-- style的宽高一定要写，不写也不会展示echarts图 -->
        <!-- 月度需求 -->
        <div id="myEcharts" ref="barChart" style="width:60%;height:800px;border:1px solid gold;float: left;"></div>
        <!-- 需求类型 -->
        <div id="lifeCycle" ref="pieChart" style="width: 40%; height: 400px ;float:left;border:1px solid gold;"></div>
        <!-- 需求状态 -->
        <div id="chartPie" ref="barChart1" style="width: 40%; height: 400px;float:left;border:1px solid gold;"></div>
    </div>
</template>

<script>
import * as echarts from 'echarts';
import { listMonthDemand ,listDemandType,listDemandStatus} from '@/api/chart/demand';

export default {
  data() {
    return {
      monthDemandList: [], // 从后台获取的数据月度需求
      demandTypeList:[],
      demandStatusList:[],
    };
  },
  mounted() {
    this.fetchMonthDemandList();
    this.demandTypeChart();
    this.fetchDemandStatusList();
  },
  methods:{
    //月度需求
    fetchMonthDemandList() {
        listMonthDemand().then(response => {
          this.monthDemandList = response.rows;
          this.renderBarChart();
        })
        .catch(error => {
          console.error('Error fetching MonthDemandList: ', error);
        });
    },
    renderBarChart() {
      const barChart = echarts.init(this.$refs.barChart);

      const option = {
        title: {
          text: '月度需求数量统计',
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow',
          },
        },
        xAxis: {
          type: 'value',
          name: '数量',
          position: 'top',
        },
        yAxis: {
          type: 'category',
          data: this.monthDemandList.map(item => item.month),
        },
        series: [
          {
            name: '数量',
            type: 'bar',
            data: this.monthDemandList.map(item => item.demandNum),
          },
        ],
      };

      barChart.setOption(option);
    },

    //需求分类
    demandTypeChart(){
        listDemandType().then(response => {
          this.demandTypeList = response.rows;
          this.renderPieChart();
        //   console.info(this.demandTypeList)
        })
        .catch(error => {
          console.error('Error fetching DemandTypeList: ', error);
        });
    },
    renderPieChart() {
      const pieChart = echarts.init(this.$refs.pieChart);

      const option = {
        title: {
          text: '需求类型数量统计',
          left: 'center',
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)',
        },
        legend: {
          orient: 'vertical',
          left: 'left',
          data: this.demandTypeList.map(item => item.requirementTypeName),
        },
        series: [
          {
            name: '数量',
            type: 'pie',
            radius: '55%',
            center: ['50%', '60%'],
            data: this.demandTypeList.map(item => ({
              value: item.demandTypeNum,
              name: item.requirementTypeName,
            })),
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)',
              },
            },
          },
        ],
      };

      pieChart.setOption(option);
    },

    //需求状态
    fetchDemandStatusList() {
        listDemandStatus().then(response => {
          this.demandStatusList = response.rows;
          this.renderBarChart1();
        })
        .catch(error => {
          console.error('Error fetching DemandStatusList: ', error);
        });
    },
    renderBarChart1() {
      const barChart = echarts.init(this.$refs.barChart1);

      const option = {
        title: {
          text: '需求状态数量统计',
          left: 'center',
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow',
          },
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true,
        },
        xAxis: {
          type: 'category',
          data: this.demandStatusList.map(item => item.demandStatus),
        },
        yAxis: {
          type: 'value',
        },
        series: [
          {
            name: '数量',
            type: 'bar',
            data: this.demandStatusList.map(item => item.count),
            itemStyle: {
              color: '#1890FF',
            },
          },
        ],
      };

      barChart.setOption(option);
    },
  }
}

</script>
