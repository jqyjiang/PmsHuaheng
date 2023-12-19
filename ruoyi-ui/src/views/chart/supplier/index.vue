<template>
    <div class="echartsMapAllDemo">
        <!-- style的宽高一定要写，不写也不会展示echarts图 -->
        <!-- 地图 -->
        <div id="myEcharts" ref="myEcharts" style="width:60%;height:800px;border:1px solid gold;float: left;"></div>
        <!-- 生命周期 -->
        <div id="lifeCycle" ref="barChart" style="width: 40%; height: 400px ;float:left;border:1px solid gold;"></div>
        <!-- 供应商分类 -->
        <div id="chartPie" ref="pieChart" style="width: 40%; height: 400px;float:left;border:1px solid gold;">
          <!-- <canvas ref="chartCanvas"></canvas> -->
        </div>
    </div>
    
  </template>
  
  <script>
  import chinaJson from '../../../assets/json/china.json'
  import * as echarts from 'echarts';

  import { listDetailsClass,listDetailsLife } from "@/api/chart/supplier";

  
  export default {
    name: 'FilmChart2',
   data() {
     return {
      detailsClassList: [],
      detailsLifeList: [], 
     };
   },
  mounted() {
    this.chinaEcharts();
    this.lifeChart();
    this.supplierChart();
  },
  methods: {
    
      //区域地图
    chinaEcharts() {
      //1.注册一个地图
      this.$echarts.registerMap("china", chinaJson);

      //2.初始化echarts
      let myChart = this.$echarts.init(this.$refs.myEcharts);
    
      // 3.准备数据
      var data = [
        { name: "北京", value: 177 },
        { name: "天津", value: 42 },
        { name: "河北", value: 102 },
        { name: "山西", value: 81 },
        { name: "内蒙古", value: 47 },
        { name: "辽宁", value: 67 },
        { name: "吉林", value: 82 },
        { name: "黑龙江", value: 66 },
        { name: "上海", value: 24 },
        { name: "江苏", value: 92 },
        { name: "浙江", value: 114 },
        { name: "安徽", value: 109 },
        { name: "福建", value: 116 },
        { name: "江西", value: 91 },
        { name: "山东", value: 119 },
        { name: "河南", value: 137 },
        { name: "湖北", value: 116 },
        { name: "湖南", value: 114 },
        { name: "重庆", value: 91 },
        { name: "四川", value: 125 },
        { name: "贵州", value: 62 },
        { name: "云南", value: 83 },
        { name: "西藏", value: 9 },
        { name: "陕西", value: 80 },
        { name: "甘肃", value: 56 },
        { name: "青海", value: 10 },
        { name: "宁夏", value: 18 },
        { name: "新疆", value: 67 },
        { name: "广东", value: 123 },
        { name: "广西", value: 59 },
        { name: "海南", value: 14 }
      ];
      var geoCoordMap = {
          上海:  [121.472644, 31.231706],
          云南:  [102.712251, 25.040609],
          内蒙古:  [111.670801, 40.818311],
          北京:  [116.405285, 39.904989],
          台湾:  [121.509062, 25.044332],
          吉林:  [125.3245, 43.886841],
          四川:  [104.065735, 30.659462],
          天津:  [117.190182, 39.125596],
          宁夏:  [106.278179, 38.46637],
          安徽:  [117.283042, 31.86119],
          山东:  [117.000923, 36.675807],
          山西:  [112.549248, 37.857014],
          广东:  [113.280637, 23.125178],
          广西:  [108.320004, 22.82402],
          新疆:  [87.617733, 43.792818],
          江苏:  [118.767413, 32.041544],
          江西:  [115.892151, 28.676493],
          河北:  [114.502461, 38.045474],
          河南:  [113.665412, 34.757975],
          浙江:  [120.153576, 30.287459],
          海南:  [110.33119, 20.031971],
          湖北:  [114.298572, 30.584355],
          湖南:  [112.982279, 28.19409],
          澳门:  [113.54909, 22.198951],
          甘肃:  [103.823557, 36.058039],
          福建:  [119.306239, 26.075302],
          西藏:  [91.132212, 29.660361],
          贵州:  [106.713478, 26.578343],
          辽宁:  [123.429096, 41.796767],
          重庆:  [106.504962, 29.533155],
          陕西:  [108.948024, 34.263161],
          青海:  [101.778916, 36.623178],
          香港:  [114.173355, 22.320048],
          黑龙江:  [126.642464, 45.756967],

      };
      //4.把数据变成[{{ name: "北京", value: [121.472644, 31.231706,177] },...}]格式
      var convertData = function(data) {
        var res = [];
        for (var i = 0; i < data.length; i++) {
          var geoCoord = geoCoordMap[data[i].name];
          if (geoCoord) {
            res.push({
              name: data[i].name,
              value: geoCoord.concat(data[i].value)
            });
          }
        }
        console.log(res)
        return res;
      };
     //5.echarts配置
      let option = {
        backgroundColor: "transparent",
        title: {
          text: "全国主要城市空气质量",
          subtext: "data from PM25.in",
          sublink: "http://www.pm25.in",
          left: "center",
          textStyle: {
            color: "#fff"
          }
        },
        tooltip: {
          trigger: "item",
          formatter: "{b}<br/>{c} "
        },
        //1)加上这个就可以实现多种颜色的功能
        visualMap: {
          min: 0,
          max: 100,
          text: ['High', 'Low'],
          calculable: true,
          seriesIndex: [0],//这个对应的是series的数组下标
          inRange: {
            color: ["#00467F", "#A5CC82"]
          }
        },
        //series就是地图上的数据（去掉就没有数据了）
        title: {
        text: '供应商区域分布',
        textStyle: {
          fontSize: 16,
          fontWeight: 'bold'
        },
        left: '10px',
        top: '10px'
      },
        series: [
          {
            type: "map",
            map: 'china',
            geoIndex: 0,
            aspectScale: 0.75, //长宽比
            showLegendSymbol: false, // 存在legend时显示
            label: {
              normal: {
                show: true,
                textStyle:{
                    fontSize: "8px" 
                }
              },
              emphasis: {
                show: false,
                textStyle: {
                  color: "#fff"
                }
              }
            },
            roam: true,
            animation: false,
            data: data
          },
        ]
      };
      myChart.setOption(option);
      window.addEventListener("resize", function() {
        myChart.resize();
      });
    },
    //生命周期
    lifeChart() {
      listDetailsLife().then(response => {
          this.detailsLifeList = response.rows;
          this.renderBarChart();
        })
        .catch(error => {
          console.error('Error fetching DetailsLifeList: ', error);
        });
    },
    //生命周期数据
    renderBarChart() {
      const barChart = echarts.init(this.$refs.barChart);

      const option = {
        title: {
          text: '供应商生命周期统计',
          // left: 'center',
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow',
          },
        },
        legend: {
          data: ['数量'],
          left: 'right',
        },
        xAxis: {
          type: 'category',
          data: this.detailsLifeList.map(item => item.cycle),
        },
        yAxis: {
          type: 'value',
        },
        series: [
          {
            name: '数量',
            type: 'bar',
            data: this.detailsLifeList.map(item => item.lifeNum),
          },
        ],
        };
        barChart.setOption(option);
    },

    //供应商分类
    supplierChart(){
      listDetailsClass()
      // axios.get('http://localhost:9555/supplierpms/details/listClass') // 发送异步请求获取数据
      .then(response => {
          this.detailsClassList = response.rows; // 从后台获取的数据
          // console.info("你好"+DetailsClassList);
          // 渲染图表
          this.renderChart();
        })
        .catch(error => {
          console.error(error);
        });
    },
    //供应商类型数据
    renderChart() {
      // 使用 chartData 进行图表的配置和渲染
      const pieChart = echarts.init(this.$refs.pieChart);

      // 根据 chartData 进行图表的配置
      const option = {
        title: {
          text: '供应商分类',
          left: 10,
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)',
        },
        legend: {
          orient: 'horizontal',
          bottom:0,
          // x: 'left',
          data: this.detailsClassList.map(item => item.classDescribe), // 获取数据中的名称作为图例
          
        },
        series: [
          {
            name: '供应商类型',
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
            data: this.detailsClassList.map(item=>({
              name:item.classDescribe,
              value:item.num,
            })), // 使用从后台获取的数据渲染图表
          },
        ],
      };
      
      // 渲染图表
      pieChart.setOption(option);
    },
  }
}

</script>


<style>

</style>
