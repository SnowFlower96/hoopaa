<template>
  <div>
    <div class="mypage-black-space"></div>
    <h1>이거 나오기는 하고 있어 ?</h1>
      <div class="check">
        <input placeholder="비밀번호 입력" v-model="checkPwd" />
        <!-- <button @click="clickPwd">확인</button> -->
        <button @click="testPwd">확인</button> <!-- 곧 지울거 -->
        
      </div>
      <button>뒤로 가기</button>
  </div>
</template>

<script>
import {ref} from 'vue'
import tempKey from './tempKey';
import { useRouter } from 'vue-router'
export default {
  name: 'checkPwd',
    setup(_, { root }) {
      const router = useRouter()
        const checkPwd = ref('');
        const clickPwd = () => {
            if (checkPwd.value === tempKey) {
                router.push({
                    path: '/myPage/info',
                    query: {
                        [tempKey] : true,
                    },
                })
            }
        };
  //  - - - - - - 곧 지울거 - - - - - -  //
        const testPwd = () => {
            if (checkPwd.value === '1234') {
              console.log('맞아')
              router.push({
                          path: '/',
                      })}};
  //  - - - - - - 곧 지울거 - - - - - -  //
        return {
            checkPwd,
            clickPwd,
            testPwd
        };
    },
  methods: {
  }
  }
</script>

<style>
.mypage-black-space {
    height: 100px;
}
</style>
