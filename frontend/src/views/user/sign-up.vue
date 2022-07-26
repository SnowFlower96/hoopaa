<template>
  <div class="signup-container-outer">
      <div class="signup-container">
        <div class="signup-item"><router-link to="/"><img class="signup-favi-img" :src="require(`@/assets/images/favi6.png`)" alt="home-favi"></router-link></div>
        <div class="signup-item">Sign up with Hoopa</div>

        <div :class="{'signup-item-false': emailTheme === false, 'signup-item-true': emailTheme === true }">
          <input type="text" placeholder="e-mail 입력" v-model="em"  @blur="checkEmail" >
          <p v-if="availableEmail && availableEmailForm">이메일을 입력하세요</p>
          <p v-if="!availableEmail" class="alert-message">이미 사용중인 이메일입니다.</p>
	        <p v-if="!availableEmailForm" class="alert-message">이메일 형식이 다릅니다.</p>
        </div>
        <div class="signup-item-false">
          <input type="password" placeholder="비밀번호 입력" v-model="pwd1">
          <p>비밀번호를 입력하세요</p>
        </div>
        <div :class="{'signup-item-false': pwTheme === false, 'signup-item-true': pwTheme === true }">
          <input type="password" placeholder="비밀번호 확인" v-model="pwd2" @blur="checkPwd">
          <p :class="{'signup-item-p-false': pwTheme === false, 'signup-item-p-true': pwTheme === true }">{{displayPwStatus}}</p>
        </div>
        <div :class="{'signup-item-false': nickNameTheme === false, 'signup-item-true': nickNameTheme === true }">
          <input type="text" placeholder="닉네임 입력" v-model="nnm" @blur="checkNickName">
          <p>닉네임을 입력하세요</p>
          <p class="alert-message" v-if="!availableNickName">이미 사용중인 닉네임 입니다.</p>
        </div>
        <div class="signup-item"><button class="signup-button" type="submit" @click="submitForm">회원가입</button></div>
        <div class="signup-item"><p class="signup-to-login">회원이신가요? <router-link style=text-decoration:none; to="/login">로그임</router-link></p></div>
    </div>
  </div>
</template>

<script>
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
      displayPwStatus: '비밀번호를 한번 더 입력하세요',
      emailTheme: false,
      pwTheme: false,
      nickNameTheme: false,
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
        this.emailTheme = true
      } else {
        this.availableEmailForm = true;
        this.emailTheme = false
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

    checkNickName(nnm) {
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
        this.pwTheme = false
      } else {
        this.displayPwStatus = '비밀번호가 서로 다릅니다.'
        this.pwTheme = true
      }
    },
    pwd1: function(pwd1) {
      if (this.pwd2 === pwd1) {
        this.displayPwStatus = '비밀번호를 동일하게 작성하셨습니다'
        this.pwTheme = false
      } else {
        this.displayPwStatus = '비밀번호가 서로 다릅니다.'
        this.pwTheme = true
      }
    },
    nnm: function(nnm) {
      checkNickName(nnm)
    }
  }
}
</script>

<style>
.alert-message {
  color: red;
  font-weight: bold;
}
.signup-container-outer {
  height: 90vh;
}
.signup-container {
  text-align: center;
}
.signup-item-false > input {
  padding: 8px;
  border-radius: 5px;
  width: 250px;
  border: none;
  background-color: #E0E4EB;
  width: 350px;
}
.signup-item-p-false {
  font-size: 10px;
  margin-right: 200px;
}
.signup-item-p-true {
  font-size: 10px;
  margin-right: 200px;
  color: red;
  font-weight: bold;
}
.signup-item-false > p {
  font-size: 10px;
  margin-right: 200px;
}
.signup-item-false > input:focus {
  outline: solid 4px #ABB2BF;
}

.signup-item-true > input {
  padding: 8px;
  border-radius: 5px;
  width: 250px;
  border: none;
  background-color: #FCDAD3;
  width: 350px;
}
.signup-item-true > p {
  font-size: 10px;
  margin-right: 200px;
}
.signup-item-true > input:focus {
  outline: solid 4px #FCDAD3;
}
.signup-favi-img {
  margin-top: 50px;
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
  border-radius: 5px;
  border: none;
  background-color: #E0C8FF;
  margin: 20px;
}
.signup-button:hover {
  outline: solid 4px #CBA3FF;
  width: 250px;
}
.signup-to-login {
  font-size: 13px;
  font-weight: bold;
}
</style>
