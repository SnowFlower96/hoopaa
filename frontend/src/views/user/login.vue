<template>
  <div class="login-container-outer">
    <div class="login-container">
      <div class="login-item"><router-link class="el-menu-item" to="/"><img class="login-favi-img" :src="require(`@/assets/images/favi6.png`)" alt="home-favi"></router-link></div>
      <div class="login-item">Login to Hoopa</div>
      <div class="login-item"><input type="text" placeholder="e-mail 입력" v-model="em"></div>
      <div class="login-item"><input type="password" placeholder="비밀번호 입력" v-model="pwd" @keyup.enter="login"></div>
      <div class="login-item"><label class="login-item-input"><input type="checkbox" v-model="checked"> 로그인 유지</label></div>
      <div class="login-item"><button class="login-button" @click="login" >로그인</button></div>
      <div class="login-item"><p class="login-to-signup">회원이 아니신가요? <router-link style=text-decoration:none; to="/signUp">회원가입</router-link></p></div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      em : '',
      pwd : '',
      loginNew : '',
      checked : false,
    }
  },
  created () {
    let query = window.location.search;
    let param = new URLSearchParams(query);
    let loginNew = param.get('em');
    this.loginNew = loginNew;
    console.log(this.loginNew)
     var data = {
        em : this.loginNew
      }
    if(this.loginNew) {
      this.$store.dispatch("emailAuth",data)
    }
  },

  methods : {
    login () {
      var data = {
        em : this.em,
        pwd : this.pwd
      }
      console.log(data);
      this.$store.dispatch("login",data);
    }
  }
}
</script>

<style>
.login-item-input {
  font-size: 15px;
  margin-right: 150px;
}
.login-container-outer {
  height: 90vh;
}
.login-container {
  text-align: center;
}
.login-item > input {
  padding: 8px;
  border-radius: 5px;
  width: 250px;
  border: none;
  outline: solid 1px #ABB2BF;
}
.login-item > input:focus {
  outline: solid 4px #E0E4EB;
  border: solid 1px black;
}
.login-favi-img {
  margin-top: 100px;
  width: 70px;
}
.logo-img {
  width: 30%;
  margin-bottom: 3%;
}
.login-item {
  margin: 10px;
}
.login-button {
  padding: 8px;
  width: 250px;
  border-radius: 5px;
  border: none;
  background-color: #E0C8FF;
  margin: 20px;
}
.login-button:hover {
  outline: solid 4px #CBA3FF;
  width: 250px;
}
.login-to-signup {
  font-size: 13px;
  font-weight: bold;
}
</style>
