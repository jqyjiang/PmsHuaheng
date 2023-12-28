//因为我本身的业务是在有新消息收到后再调用列表查询的接口，所以会引入框架的请求方法（无此需求的小伙伴不用理会）
import * as $http from '@/utils/request'
import { mapGetters } from 'vuex';
const utils = {
  namespaced: true,
  state: {     //声明变量，跟单页面data return声明一个意思
    message: [],
    readSysMsgList: [],
    readSysMsgListLength: "",
    wsUrl: '',
    ws_heart: '', // ws心跳定时器
    lockReconnect: false, //是否真正建立连接
    timeoutnum: null //断开 重连倒计时
  },
  getters: {},
  mutations: {
    //vuex突变，拿到维护的数据后交给对应的变量，供页面使用（可查看资料vuex用法）
    unReadSysMsg(state, options) {
      state.readSysMsgList = options
    },
    unReadSysMsglenght(state, options) {
      state.readSysMsgListLength = options
    },
  },
  actions: {
    // 获取连接信息（在登录接口调用getUserInfo方法建立连接）
    //getUserInfo有一个dispatch 参数，此参数目的为了调用actions中其他方法
    getUserInfo({ state, commit, dispatch },) {
    //先判断浏览器是否支持WebSocket
      if (typeof WebSocket === "undefined") {
        alert("您的浏览器不支持socket");
      } else {
      	//提前判断 WebSocket是否已经建立，避免重复连接问题
        if (this.socket) {
          this.socket.close()
        }
        //WebSocket连接时我的地址需要拼接用户的id所以再此进行获取，也就是在登录时为啥把getUserInfo放在登录成功的回调中，无此需要的小伙伴可省略
        let id = JSON.parse(localStorage.getItem('loginUserAllInfo')).user.id
        // 实例化socket （长连接为ws地址格式）这一步就是建立连接(自行放入url即可)
        this.socket = new WebSocket(`ws://xxx.xxx.xxx/${id}`);
        //new WebSocket有很多内置的方法，onopen 就是证明连接已经成功，可以在此进行心跳检测
        this.socket.onopen = () => {
          console.log('websocket已连接');
          //调用reset方法，reset方法是跟getUserInfo同级，在vuex中需要用dispatch进行调用（可参考actions里第二行注释解释，必须得有接收参数）。跟单页面中的this.reset（）同理
          dispatch("reset")
          //本项目业务，建立连接后先进行列表查询
          $http.get("/task/sysMessage/getUnReadSysMsg").then((res) => {
            console.log(res.data)
            //列表查询的结果拿commit存到vuex当中，然后再给了state中的变量，到时候页面就可以直接拿到了。比如先把unReadSysMsg存起来，给了readSysMsgList ，然后页面获取数据的时候就可以this.readSysMsgList 稍后会有页面展示的代码
            commit("unReadSysMsg", res.data);
            commit("unReadSysMsglenght", res.data.length);
          })
        };
        // 监听socket消息（主要内置方法，收到消息后会进行监听接收）
        this.socket.onmessage = (msg) => {
          // console.log('接收到新消息--------' + msg.data)
          //同样调用reset进行心跳检测重置
          dispatch("reset")
          
          //本项目业务，主要心跳检测，我会在start方法里每隔30秒主动进行ping推送，看连接是否还存在，如果接收到pong到pong就证明是因为我主动推送消息拿到的监听结果，就不需要处理。如果接收到非pong则证明是后端有新消息推送过来，重新进行列表查询，刷新数据
          if (msg.data == "pong") {
          } else {
          //直接返回数据的可避免此操作，直接拿到msg，commit存储好就可
            $http.get("/task/sysMessage/getUnReadSysMsg").then((res) => {
              commit("unReadSysMsg", res.data);
              commit("unReadSysMsglenght", res.data.length);
              console.log(res.data)
            })
          }
        };
        // 监听socket错误信息（websocket断开会进入此方法，需要进行重连）
        this.socket.onclose = function (e) {
          console.log('关闭了')
          //断开连接后调用reconnect进行重新连接
          dispatch("reconnect")
        };
        // WebSocket发生错误
        this.socket.onerror = function (e) {
         //断开连接后调用reconnect进行重新连接
          dispatch("reconnect")
          console.log("WebSocket发生错误");
        };
      }
    },
    //重新连接（断开跟错误后都需要进行重连操作）
    reconnect({ dispatch }) {
      var that = this;
      if (that.lockReconnect) {
        // 是否真正建立连接
        return;
      }
      that.lockReconnect = true;
      //没连接上会一直重连，设置延迟避免请求过多
      that.timeoutnum && clearTimeout(that.timeoutnum);
      // 如果到了这里断开重连的倒计时还有值的话就清除掉
      that.timeoutnum = setTimeout(function () {
        console.log('重启中')
        //然后新连接（dispatch照样进行方法getUserInfo的调用）
        dispatch('getUserInfo')
        that.lockReconnect = false;
      }, 60000);
    },
    //建立连接及有新消息接收后进行心跳重置
    reset({ dispatch }) {
      //重置心跳
      var that = this;
      //清除时间（清除心跳计时）
      clearInterval(that.ws_heart)
      //重启心跳
      dispatch("start")
    },
    //心跳检测
    start({ dispatch }) {
    //实时推送ping消息，查看连接是否断开
      this.ws_heart = setInterval(() => {
        let actions = "ping"
        this.socket.send(JSON.stringify(actions));
      }, 30000)
    },
  }
}
export default utils
