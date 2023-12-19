<template>
  <body id="body">
    <div id="head">
      <div id="ab">
      <img src="./../../assets/images/ab.jpg" 
       style="width:80px ;height: 80px;margin: 0px 10px;float: left;">
       <div style="padding-left: 20px;">
       <h4>采购部</h4>
       <span id="the">The purchasing department</span>
      </div>
      </div>
      <ul>
        <li></li>
      </ul>
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
  }
  #body{
    background-color: #E8EDF4;
  }
  #head{
    background-color: white;
    margin:10px;
  }
</style>