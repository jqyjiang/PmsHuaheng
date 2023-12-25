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
        <li>采购需求</li>
        <li>采购申请</li>
        <li>采购合同</li>
        <li>采购订单</li>
      </ul>
      </div>
    </div>
    <!-- 中 -->
    <div id="centre">
      <div id="left">

      </div>
      <div id="right">

      </div>
    </div>
    <!-- 下 -->
    <div id="below">

    </div>
  </body>
  </template>

  <script>


  export default {
    data(){
      return {

      }
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
</style>
