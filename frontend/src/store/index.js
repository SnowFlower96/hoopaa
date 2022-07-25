import { createApi } from "@/api";
import Vue from 'vue';
import Vuex from "vuex";
import router from "@/common/lib/vue-router";
import createPersistedState from "vuex-persistedstate";

//Vue.use(Vuex);

const api = createApi();

export default new Vuex.Store({
  plugins: [createPersistedState()],
  state: {
    isLogin: false,
    roomList : [],
  },

  mutations : {
    GET_ROOM_LIST(state, data) {
      state.roomList = data;
    }
  },

  actions : {
    getRoomInfo({commit}) {
      api({
        url: `/api/v1/list/all?now=0`,
        method: "GET",
      }).then((res) => {
        commit("GET_ROOM_LIST", res.data);
      })
    },

  }
})
