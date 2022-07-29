import { createApi, createTokenApi } from "@/api";
import Vuex from "vuex";
import router from "@/common/lib/vue-router";
import createPersistedState from "vuex-persistedstate";

//Vue.use(Vuex);

const api = createApi();
const tapi = createTokenApi();

export default new Vuex.Store({
  plugins: [createPersistedState()],
  state: {
    isLogin: false,
    userHistory : '',
    userStat : '',
    userInfo : '',
    roomList : [],
    headerVisible: true
  },

  mutations : {
    // 방 리스트 불러오기
    GET_ROOM_LIST(state, data) {

      state.roomList = JSON.parse(data);
    },
    // 로그인 토큰, 상태
    USER_LOGIN(state, token) {
      state.isLogin = true;
      sessionStorage.setItem("accessToken", token);
      api.defaults.headers["accessToken"] = token;
    },
    // 로그아웃
    USER_LOGOUT(state) {
      state.isLogin = false;
      sessionStorage.removeItem("accessToken");
      api.defaults.headers["accessToken"] = "";
      alert("로그아웃 됐습니다.");
    },
    USER_HISTORY(state, data) {
      state.userHistory = JSON.parse(data);
    },
    USER_STAT(state, data) {
      state.userStat = JSON.parse(data);
    },
    USER_INFO(state, data) {
      state.userInfo = JSON.parse(data);
    },
  },

  actions : {

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

     // User 정보
     getUserHistory({commit}) {
      tapi({
        url : `/users/history`,
        method : "GET"
      }).then((res) => {
        console.log(res.data.json)
        commit("USER_HISTORY",res.data.json);
      })
     },
     // User Stat
     getUserStat({commit}) {
      tapi({
        url : `/users/stat`,
        method : "GET"
      }).then((res) => {
        commit("USER_STAT",res.data.json);
      })
     },
     // User Info
     getUserInfo({commit}) {
      tapi({
        url : `/users/info`,
        method : "GET"
      }).then((res) => {
        commit("USER_INFO",res.data);
      })
     },

     // User Info 수정
      putUserStat({commit},data) {
      tapi({
        url : `/users/stat`,
        method : "PUT",
        data : data,
      }).then((res) => {
        commit("USER_INFO",res.data);
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
    },

    //ALL 리스트 받아오기
    getRoomInfo({commit}) {
      api({
        url: `/list/all`,
        method: "GET",
      }).then((res) => {
        commit("GET_ROOM_LIST", res.data.json);
      })
    },

    //Cate별 리스트 받아오기
    getRoomInfoCate({commit},index) {
      api({
        url: index,
        method : "GET",
      }).then((res) => {
        commit("GET_ROOM_LIST", res.data.json);
        router.push("/list?"+index);
      })
    },


  }
})