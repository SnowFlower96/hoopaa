<template>
  <div class="mypage-chpwd-container-outer">
    <div class="mypage-blank-space"></div>
    <div class="mypage-chpwd-container">
      <h1>회원정보 변경</h1>
      <p>비밀번호를 한번 더 입력해주세요.</p>
        <div class="check-item">
          <input type="password" placeholder="비밀번호 입력" v-model="checkPwd" />
        </div>
        <div class="check-item">
          <!-- <button @click="clickPwd">확인</button> -->
          <button @click="testPwd">확인</button> <!-- 곧 지울거 -->
        </div>
    </div>
    </div>
</template>

<script>
export default {
  name: 'checkPwd',
   data () {
    return {
      checkPwd : '',
    }
   },
   methods : {
    testPwd () {
      var data = {
        pwd : this.checkPwd,
      }
      this.$store.dispatch("checkPwd", this.checkPwd)
    }
   }
  }
</script>

<style>
.mypage-blank-space {
    height: 100px;
}
.mypage-chpwd-container {
  text-align: center;
}
.mypage-chpwd-container-outer {
  height: 90vh;
}
.check-item > input {
  padding: 8px;
  border-radius: 5px;
  width: 250px;
  border: none;
  background-color: #E0E4EB;
  width: 350px;
}
.check-item > input:focus {
  outline: solid 4px #ABB2BF;
}
.check-item {
  margin: 20px;
}
.check-item > button {
  padding: 8px;
  background-color: #E0C8FF;
  border-radius: 5px;
  border: none;
  width: 100px;
}
.check-item > button:hover {
  outline: solid 3px #CBA3FF;
  cursor: pointer;
}
</style>
