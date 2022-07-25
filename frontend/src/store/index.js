import { createApi } from "@/api";
import Vue from 'vue';
import Vuex from "vuex";
import router from "@/common/lib/vue-router";
import createPersistedState from "vuex-persistedstate";

//Vue.use(Vuex);

const api = createApi();
const menuData = require('@/views/main/menu.json')

export default new Vuex.Store({
  plugins: [createPersistedState()],
  state: {
    isLogin: false,
    roomList : [],
    menus: menuData,
    headerVisible: true
  },

  mutations : {
    GET_ROOM_LIST(state, data) {
      state.roomList = data;
    },
    USER_LOGIN(state, token) {
      state.isLogin = true;
      sessionStorage.setItem("access-token", token);
      api.defaults.headers["access-token"] = token;
    }
  },

  actions : {
    //ALL 리스트 받아오기
    getRoomInfo({commit}) {
      api({
        url: `/api/v1/list/all?now=0`,
        method: "GET",
      }).then((res) => {
        commit("GET_ROOM_LIST", res.data);
      })
    },
    // 이메일 중복검사
    checkEmail({commit},email) {
      api({
        url : `/api/v1/users/check/email`,
        method: "POST",
        data : email
      }).then((res) => {
        if(res.data.response == 0 ) {
          alert("사용 가능한 이메일 입니다.")
        } else {
          alert("중복된 이메일 입니다.")
        }
      })

    },
    // 닉네임 중복검사
    checkNickName({commit},nickname) {
      api({
        url : `/api/v1/users/check/nickname`,
        method: "POST",
        data : nickname
      }).then((res) => {
        if(res.data.response == 0) {
          alert("사용 가능한 닉네임 입니다.")
        } else {
          alert("중복된 닉네임 입니다.")
        }
      })

    },
    // 회원가입
    signUp({commit},data) {
              api({
                url : `/api/v1/users`,
                method: "POST",
                data : data
              }).then((res) => {
                console.log(res);
                if(res.status == 400) {
                  alert("")
                }
              })
    },

    login({commit},data) {
      api({
        url : `/api/v1/login`,
        method : "POST",
        data : data
      }).then((res) => {
        if (data["access-token"] === undefined) {
          alert("비밀번호가 잘못되었습니다.")
          router.push({ name: "login" });
        } else {
          commit("USER_LOGIN", data["access-token"]);
        }
      })
    }
  }
})
