// axios 객체를 만들거야
import axios from 'axios'

function createApi() {
  return axios.create({
    // 설정하는 정보가 여러개라서 객체로 지정
    baseURL: process.env.VUE_APP_API_SERVER,
    headers: {
      // json 형태로 날릴거야
      // "Content-Type": "application/json",
      "Authorization" : `Bearer ${sessionStorage.getItem("accessToken")}`
    },
  });
}

export { createApi }
