<template>
  <div class="mypage-info-container">
      <div class="mypage-info-blank-space"></div>
      <div class= 'mypage-info'>
        <h1>회원정보 변경</h1>
        <form>
          <div class="mypage-info-item">
            <input :placeholder="userEmail" disabled>
            </div>
          <div class="mypage-info-item">
            <input type="password" placeholder="password" v-model="infoPwd1">
            <p>비밀번호를 입력하세요</p>
          </div>
          <div class="mypage-info-item"> 
            <input type="password" placeholder="confirm password" v-model="infoPwd2">
            <p :class="{'info-item-p-false': pwTheme === false, 'info-item-p-true': pwTheme === true }">{{displayPwStatus}}</p>
          </div>
          <div class="mypage-info-item">
            <input placeholder="change nickname" v-model="infoNickName">
            <p >닉네임을 입력하세요</p>
          </div>
          <div class="info-item-btn"><button>수정</button></div>
        </form>
    <!-- <div>pwd1 : {{ infoPwd1 }}</div>
    <div>pwd2 : {{ infoPwd2 }}</div>
    <div>nickname : {{ infoNickName }}</div> -->
      </div>
    <div><button>회원탈퇴</button></div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userEmail: 'OOOO@naver.com',
      infoPwd1: null,
      infoPwd2: null,
      infoNickName: null,
      displayPwStatus: '비밀번호를 한번 더 입력하세요',
      pwTheme: false
    }
  },
  watch: {
    infoPwd2: function(infoPwd2) {
      if (this.infoPwd1 === infoPwd2) {
        this.displayPwStatus = '비밀번호를 동일하게 작성하셨습니다'
        this.pwTheme = false
      } else {
        this.displayPwStatus = '비밀번호가 서로 다릅니다.'
        this.pwTheme = true
      }
    },
    infoPwd1: function(infoPwd1) {
      if (this.infoPwd2 === infoPwd1) {
        this.displayPwStatus = '비밀번호를 동일하게 작성하셨습니다'
        this.pwTheme = false
      } else {
        this.displayPwStatus = '비밀번호가 서로 다릅니다.'
        this.pwTheme = true
      }
    }
  }
}
</script>

<style>
.mypage-info {
  height: 70vh;
  text-align: center;
}
.mypage-info > h1 {
  margin-top: 40px;
  margin-bottom: 50px;
}
.mypage-info-item > p {
  font-size: 10px;
  margin-right: 200px;
}
.mypage-info-item > input {
  padding: 8px;
  border-radius: 5px;
  border: none;
  background-color: #E0E4EB;
  width: 350px;
  margin: 5px;
}
.mypage-info-item > input:focus {
  outline: solid 4px #ABB2BF;
}
.mypage-info-blank-space {
  height: 100px;
}
.info-item-p-false {
  font-size: 10px;
  margin-right: 200px;
}
.info-item-p-true {
  font-size: 10px;
  margin-right: 200px;
  color: red;
  font-weight: bold;
}
.info-item-btn > button {
  padding: 8px;
  background-color: #E0C8FF;
  border-radius: 5px;
  border: none;
  width: 100px;
}
.info-item-btn > button:hover {
  outline: solid 3px #CBA3FF;
  cursor: pointer;
}
</style>
