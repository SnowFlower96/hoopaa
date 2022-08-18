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
          <div class="info-item-btn"><button @click="changeInfo">수정</button></div>
        </form>
        <div class="unsubscribe-btn">
          <div>회원탈퇴</div>
        </div>
      </div>
  </div>
</template>

<script>
import { mapState } from 'vuex';

export default {
  data() {
    return {
      userEmail: '',
      infoPwd1: null,
      infoPwd2: null,
      infoNickName: null,
      displayPwStatus: '비밀번호를 한번 더 입력하세요',
      pwTheme: false
    }
  },
  computed : {
    ...mapState(["user"]),
  },
  created () {
    this.userEmail = this.user.em;
    this.infoNickName = this.user.nnm;
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
  },
  methods : {
    changeInfo () {
      let data = {
        em : this.userEmail,
        nnm : this.infoNickName,
        pwd : this.infoPwd1,
      }
      this.$store.dispatch("changeInfo", data);
      alert("회원정보가 변경되었습니다.")
    }
  }
}
</script>

<style>
.unsubscribe-btn {
  position: relative;
  left: 150px;
  font-size: 15px;
  color: rgb(168, 90, 90);
  cursor: pointer;
}
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
