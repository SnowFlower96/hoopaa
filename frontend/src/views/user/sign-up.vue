<template>
  <div class="signup-container-outer">
      <div class="signup-container">
        <router-link class="el-menu-item" to="/"><div class="signup-item"><img class="favi-img" :src="require(`@/assets/images/favi6.png`)" alt="home-favi"></div></router-link>
        <div class="signup-item">Sign up with Hoopa</div>

        <div class="signup-item">
          <label for="email">이메일</label>
          <input type="text" placeholder="e-mail 입력" v-model="em"  @blur="checkEmail" >
          <span class="badge badge-danger mt-1" v-if="!availableEmail">이미 사용중인 이메일입니다.</span>
	        <span class="badge badge-danger mt-1" v-if="!availableEmailForm">이메일 형식이 다릅니다.</span>
        </div>
        <div class="signup-item">
          <label for="email">비밀번호</label>
          <input type="password" placeholder="비밀번호 입력" v-model="pwd1">
        </div>
        <div class="signup-item">
          <label for="email">비밀번호</label>
          <input type="password" placeholder="비밀번호 확인" v-model="pwd2" @blur="checkPwd">
          <div>{{displayPwStatus}}</div>
          <span class="badge badge-danger mt-1" v-if="!samePwd">비밀번호가 서로 다릅니다.</span>
        </div>
        <div class="signup-item">
          <label for="email">닉네임</label>
          <input type="text" placeholder="닉네임 입력" v-model="nnm" @blur="checkNickName">
          <span class="badge badge-danger mt-1" v-if="!availableNickName">이미 사용중인 닉네임 입니다.</span>
        </div>
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
      pwd1: '',
      pwd2: '',
      samePwd : true,
      availableEmail : true,
      availableEmailForm : true,
      availableNickName : true,
      displayPwStatus: '비밀번호를 입력하세요',
      
    }
  },
  methods : {
    validateEmail(em) {
      var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return re.test(em);
    },

    checkEmail () {
      this.availableEmail = true;
      if (!this.validateEmail(this.em)) {
        this.availableEmailForm = false;
      } else {
        this.availableEmailForm = true;
      }
      var email = {
        em : this.em
      }
     this.$store.dispatch("checkEmail",email).then(response => {
       if (response.data.response == 1) {
         this.availableEmail = false;
         this.availableEmailForm = true;
       } else {
         this.availableEmail = true;
       }
     });
    },

    checkPwd () {
      this.samePwd = true;
      console.log(this.pwd1, this.pwd2)
      if (this.pwd1 == this.pwd2) {
        this.samePwd = true;
      } else {
        this.samePwd = false;
      }
    },

    checkNickName() {
      var nickname = {
        nnm : this.nnm
      }
      this.$store.dispatch("checkNickName",nickname).then(response => {
       if (response.data.response == 1) {
         this.availableNickName = false;
       } else {
         this.availableNickName = true;
       }
     });
    },
    submitForm: function () {
      var data = {
        em : this.em,
        nnm : this.nnm,
        pwd : this.pwd1
      }
      console.log(this.email)
      if(this.samePwd && this.availableEmail && this.availableEmailForm && this.availableNickName) {
        this.$store.dispatch("signUp",data);
      } else {
        alert("메시지를 확인해 주세요")
      }
    }
  },
  watch: {
    pwd2: function(pwd2) {
      if (this.pwd1 === pwd2) {
        this.displayPwStatus = '비밀번호를 동일하게 작성하셨습니다'
      } else {
        this.displayPwStatus = '비밀번호가 다릅니다'
      }
    },
    pwd1: function(pwd1) {
      if (this.pwd2 === pwd1) {
        this.displayPwStatus = '비밀번호를 동일하게 작성하셨습니다'
      } else {
        this.displayPwStatus = '비밀번호가 다릅니다'
      }
    },
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
