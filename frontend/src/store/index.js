import { createApi, createTokenApi } from "@/api";
import Vuex from "vuex";
import router from "@/common/lib/vue-router";
import createPersistedState from "vuex-persistedstate";
import { toRawType } from "@vue/shared";

//Vue.use(Vuex);

const api = createApi();

export default new Vuex.Store({
  plugins: [createPersistedState()],
  state: {
    isLogin: false,
    userHistory : '',
    userStat : '',
    roomList : [],
    headerVisible: true,
    tempToken : '',
    user : [],
    position : '',
    tempSubToken : '',
    roomCode : '',
  },

  mutations : {
    // State 초기화
    RESET_STATE(state) {

        state.isLogin = false;
        state.userHistory = '';
        state.userStat = '';
        state.roomList = [];
        state.headerVisible = true;
        state.tempToken = '';
        state.user = [];
        state.position = '';
        state.tempSubToken = '';
        state.roomCode = '';
    },
    // 방 리스트 불러오기
    GET_ROOM_LIST(state, data) {

      state.roomList = JSON.parse(data);
    },
    // 로그인 토큰, 상태
    USER_LOGIN(state, data) {
      state.isLogin = true;
      sessionStorage.setItem("accessToken", data.accessToken);
      api.defaults.headers["accessToken"] = data.accessToken;
      state.user = data.user;
    },
    // 비회원 로그인
    BUSER_LOGIN(state, data) {
      sessionStorage.setItem("accessToken", data.accessToken);
      api.defaults.headers["accessToken"] = data.accessToken;
      state.user = data.nnm;
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
    CREATE_TEMP_TOKEN(state, data) {
      state.tempToken = data;
    },
    CREATE_TEMP_SUB_TOKEN(state, data) {
      state.tempSubToken = data;
    },
    SET_POSITION(state, data) {
      state.position = data;
    },
    SET_ROOM_CODE(state, data) {
      state.roomCode = data;
    }
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
          commit("USER_LOGIN", res.data);
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
      api({
        headers : { Authorization : `Bearer ${sessionStorage.getItem("accessToken")}`},
        url : `/users/history`,
        method : "GET"
      }).then((res) => {
        commit("USER_HISTORY",res.data.json);
      })
     },
     // User Stat
     getUserStat({commit}) {
      api({
        headers : { Authorization : `Bearer ${sessionStorage.getItem("accessToken")}`},
        url : `/users/stat`,
        method : "GET"
      }).then((res) => {
        commit("USER_STAT",res.data.json);
      })
     },
     // User Info
     getUserInfo({commit}) {
      api({
        headers : { Authorization : `Bearer ${sessionStorage.getItem("accessToken")}`},
        url : `/users/info`,
        method : "GET"
      }).then((res) => {
        commit("USER_INFO",res.data.json);
      })
     },

     // User Info 수정
     changeInfo({commit}, data) {
      api({
        headers : { Authorization : `Bearer ${sessionStorage.getItem("accessToken")}`},
        url : `/users/info`,
        method : "PUT",
        data : data,
      }).then(() => {
        commit();
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

    // 유저 비밀번호 확인
    checkPwd({commit}, data) {
      api({
        headers : { Authorization : `Bearer ${sessionStorage.getItem("accessToken")}`},
        url : `/users/verify`,
        method : "POST",
        data : data,
      }).then((res) => {
        let query = window.location.search;
        let param = new URLSearchParams(query);
        let loc = param.get('loc');

        if (loc == 'info') {
          router.push('/myPage/info')
        } else {
          router.push('/myPage/reSign')
        }
        commit();
      }).catch(error => {
        reject(error)
        alert("비밀번호를 확인하세요")
        let query = window.location.search;
        let param = new URLSearchParams(query);
        let loc = param.get('loc');
        if (loc == 'info') {
          router.push('/checkPwd?loc=info')
        } else {
          router.push('/checkPwd?loc=reSign')
        }
    })
  },

  // 토론방 생성
  makeRoom({commit}, room) {
    return new Promise ((resolve, reject) => {
    api({
      headers : { Authorization : `Bearer ${sessionStorage.getItem("accessToken")}`},
      url : `/room`,
      method : "POST",
      data : room,
    }).then((res) =>{
      resolve(res);
      commit("SET_ROOM_CODE", res.data.response);
    }).catch((error) =>{
      reject(error);
    })
  })
  },

  enterRoom({commit}, data) {
    api({
      headers : { Authorization : `Bearer ${sessionStorage.getItem("accessToken")}`},
      url : `/room/enter`,
      method : "POST",
      data : data,
    }).then((res) => {
      commit("CREATE_TEMP_TOKEN",res.data.token);
      router.push("/participatingPage")
    })
  },
  makeSessionRoom({commit}, index) {
    return new Promise ((resolve, reject) => {
    api({
      headers : { Authorization : `Bearer ${sessionStorage.getItem("accessToken")}`},
      url : index,
      method : "POST",
    }).then((res) => {
      resolve(res);
      commit();
    }).catch((error) => {
      reject(error);
    })
  })
},
  getConnectionAgree({commit}, sessionId) {
    return new Promise ((resolve, reject) => {
    api({
      headers : { Authorization : `Bearer ${sessionStorage.getItem("accessToken")}`},
      url : '/room/connections/agree?sessionID=' + sessionId ,
      method : "GET",
    }).then((res) => {
      resolve(res);
      commit();
    }).catch((error) => {
      reject(error);
    })
  })
  },
  getConnectionDisagree({commit}, sessionId) {
    return new Promise ((resolve, reject) => {
    api({
      headers : { Authorization : `Bearer ${sessionStorage.getItem("accessToken")}`},
      url : '/room/connections/disagree?sessionID=' + sessionId ,
      method : "GET",
    }).then((res) => {
      resolve(res);
      commit();
    }).catch((error) => {
      reject(error);
    })
  })
  },
  setPosition({commit}, index) {
    api({
      headers : { Authorization : `Bearer ${sessionStorage.getItem("accessToken")}`},
      url : index,
      method : "POST",
    }).then((res) => {
      // commit();
    })
  },

  // 토론방 기능

  roomStart({commit}, data) {
    api({
      headers : { Authorization : `Bearer ${sessionStorage.getItem("accessToken")}`},
      url : `/room/start`,
      method : "PUT",
      data : data
    })
  },

  // 비회원 토큰 발급

  bJoin({commit}, data) {
    api({
      url : '/users/temp/' + data.nnm,
      method : "POST",
    }).then((res) => {
      commit("BUSER_LOGIN", res.data)
      this.dispatch("enterRoom", data.data);
    })
  },

  // 투표
  // 투표시작
  voteStart({commit},data) {
    return new Promise ((resolve, reject) => {
    api({
      headers : { Authorization : `Bearer ${sessionStorage.getItem("accessToken")}`},
      url : `/room/vote`,
      method : "PUT",
      data : data
    }).then((res) => {
      resolve(res);
    })
  })
  },
  // 투표 보내기
  voteFinal({commit}, data) {
    api({
      headers : { Authorization : `Bearer ${sessionStorage.getItem("accessToken")}`},
      url : '/room/vote/final?kingUserID=' + data.kingUserID + '&sessionID=' + data.sessionID + '&vote=' + data.vote,
      method : "POST",
    })
  },
  // 세부세션 닫기
  closeSession({commit}, data) {
    api({
      headers : { Authorization : `Bearer ${sessionStorage.getItem("accessToken")}`},
      url : '/room/session/' + data,
      method : "DELETE",
    })
  },
  // 룸 종료
  closeRoom({commit}) {
    api({
      headers : { Authorization : `Bearer ${sessionStorage.getItem("accessToken")}`},
      url : '/room',
      method : "DELETE",
    })
  },
  // 방 결과 불러오기
  getRoomResult({commit}, data) {
    return new Promise ((resolve, reject) => {
    api({
      headers : { Authorization : `Bearer ${sessionStorage.getItem("accessToken")}`},
      url : '/room/'+ data,
      method : "GET",
    }).then((res)=>{
      resolve(res);
    })
  })
  },

  // 패널티 주기
  givePenalty({commit}, data) {
    api({
      headers : { Authorization : `Bearer ${sessionStorage.getItem("accessToken")}`},
      url : '/room/'+ data,
      method : "PUT",
    })
  },
  // 패널 정보 가져오기
  getPanels({commit}) {
    return new Promise ((resolve, reject) => {
    api({
      headers : { Authorization : `Bearer ${sessionStorage.getItem("accessToken")}`},
      url : '/room/panels',
      method : "GET",
    }).then((res => {
      resolve(res)
    }))
  })
  },
}
})
