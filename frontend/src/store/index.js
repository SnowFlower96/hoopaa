import { createApi } from "@/api";
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
    user : {
      email : '',
    },
    roomList : [],
    menus: menuData,
    headerVisible: true
  },

  mutations : {
    // 방 리스트 불러오기
    GET_ROOM_LIST(state, data) {
      state.roomList = data;
    },
    // 로그인 토큰, 상태
    USER_LOGIN(state, token) {
      state.isLogin = true;
      sessionStorage.setItem("access-Token", token);
      api.defaults.headers["access-Token"] = token;
    },
    // 로그아웃
    USER_LOGOUT(state) {
      state.isLogin = false;
      sessionStorage.removeItem("access-token");
      api.defaults.headers["access-token"] = "";
      alert("로그아웃 됐습니다.");
    },
  },

  actions : {
    //ALL 리스트 받아오기
    getRoomInfo({commit}) {
      api({
        url: `/list/all`,
        method: "GET",
      }).then((res) => {
        commit("GET_ROOM_LIST", res.data);
      })
    },
    // 이메일 중복검사
    checkEmail({commit},email) {
      return new Promise ((resolve, reject) => {
        api({
          url : `/users/check/email`,
          method: "POST",
          data : email
        }).then((res) => {
          resolve(res);
          commit();
        }).catch(error => {
          reject(error)
        })
      })

    },
    // 닉네임 중복검사
    checkNickName({commit},nickname) {
      return new Promise ((resolve, reject) => {
        api({
          url : `/users/check/nickname`,
          method: "POST",
          data : nickname
        }).then((res) => {
          resolve(res);
          commit();
        }).catch(error => {
          reject(error)
        })
      })

    },
    // 회원가입
    signUp({commit},data) {
          api({
            url : `/users`,
            method: "POST",
            data : data
          }).then((res) => {
            console.log(res);
            router.push('/email?em='+data.em)
            commit();
          })

    },
    // 로그인
    login({commit},data) {
      return new Promise((reject) => {
      api({
        url : `/users/login`,
        method : "POST",
        data : data
      }).then((res) => {
          commit("USER_LOGIN", res.data.accessToken);
          router.push('/')
      }).catch(error => {
        reject(error)
        alert("이메일 , 비밀번호를 확인하세요")
        router.push('/login');
      })
    })
    },

    // DB 이메일 인증 auth 수정
    emailAuth({commit}, email) {
      api({
        url : `/users/certification/success`,
        method : "POST",
        data : email
      }).then(() => {
        commit();
      })
    },

    // 가입인증 이메일 발송
    sendEmail({commit}, email) {
      api({
        url : `/users/certification`,
        method : "POST",
        data : email
      }).then(() => {
        commit();
      })
    }
  }
})
