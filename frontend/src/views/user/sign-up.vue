<template>
  <div class="signup-container-outer">
      <div class="signup-container">
        <router-link class="el-menu-item" to="/"><div class="signup-item"><img class="favi-img" :src="require(`@/assets/images/favi6.png`)" alt="home-favi"></div></router-link>
        <div class="signup-item">Sign up with Hoopa</div>

        <div class="signup-item"><input type="text" placeholder="e-mail 입력" v-model="em"><el-button type="primary" @click="checkEmail">중복확인</el-button></div>
        <div class="signup-item"><input type="password" placeholder="비밀번호 입력" v-model="pwd"></div>
        <div class="signup-item"><input type="password" placeholder="비밀번호 확인" ></div>
        <div class="signup-item"><input type="text" placeholder="닉네임 입력" v-model="nnm" ><button  @click="checkNickName">중복확인</button></div>
        <div class="signup-item"><button class="signup-button" type="submit" @click="submitForm">회원가입</button></div>
        <div>회원이신가요? <router-link to="/login">로그인</router-link></div>

    </div>
  </div>
</template>

<script>
import router from '../../common/lib/vue-router';

export default {
  data: function() {
    return {
      em: '',
      nnm : '',
      pwd: ''
    }
  },
  methods : {
    checkEmail () {
      var email = {
        em : this.em
      }
      this.$store.dispatch("checkEmail",email);
    },
    checkNickName() {
      var nickname = {
        nnm : this.nnm
      }
      this.$store.dispatch("checkNickName",nickname)
    },
    submitForm: function () {
      var data = {
        em : this.em,
        nnm : this.nnm,
        pwd : this.pwd
      }
      console.log(this.email)
      this.$store.dispatch("signUp",data);
    }
  }
}
</script>

<style>
.signup-container-outer {
  height: 90vh;
}
.signup-container {
  text-align: center;
}
.signup-item > input {
  padding: 8px;
  border-radius: 5px;
  width: 250px;
}
.favi-img {
  width: 70px;
}
.signup-img {
  width: 30%;
  margin-bottom: 3%;
}
.signup-item {
  margin: 10px;
}
.signup-button {
  padding: 8px;
  width: 250px;
}
.signup-button:hover {
  background-color: beige;
  padding: 8px;
  width: 250px;
}
</style>
