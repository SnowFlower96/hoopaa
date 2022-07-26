<template>
  <div class="login-container-outer">
    <div class="login-container">
      <router-link class="el-menu-item" to="/"><div class="login-item"><img class="favi-img" :src="require(`@/assets/images/favi6.png`)" alt="home-favi"></div></router-link>
      <div class="login-item">Login to Hoopa</div>
      <div class="login-item"><input type="text" placeholder="e-mail 입력" v-model="em"></div>
      <div class="login-item"><input type="password" placeholder="비밀번호 입력" v-model="pwd"></div>
      <div class="login-item"><el-checkbox-button>로그인 유지</el-checkbox-button></div>
      <div class="login-item"><button class="login-button" @click="login">로그인</button></div>
      <div class="login-item">회원이 아니신가요? <router-link to="/signUp">회원가입</router-link></div>
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
    }
  },
  created () {
    let query = window.location.search;
    let param = new URLSearchParams(query);
    let loginNew = param.get('em');
    this.loginNew = loginNew;
    console.log(this.loginNew)
    // if(!this.loginNew) {
    //   this.$store.dispatch("emailAuth",loginNew)
    // }
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
}
.favi-img {
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
}
.login-button:hover {
  background-color: beige;
  padding: 8px;
  width: 250px;
}
</style>
