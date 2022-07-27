<template>
<div>
  <div class="mypage-info-black-space"></div>
  <div class= 'mypage-info'>
    <form>
      <div><span>이메일 </span>{{userEmail}}</div>
      <div><sapn>비밀번호</sapn><input type="password" placeholder="[비밀번호}" v-model="infoPwd1"></div>
      <div><span>비밀번호 확인</span><input type="password" placeholder="{비밀번호}" v-model="infoPwd2"></div>
      <div><span>닉네임</span><input placeholder="{닉네임}" v-model="infoNickName"></div>
      <button>수정</button>
      
    </form>
      
  </div>
    <div><button>회원탈퇴</button></div>
    <div>pwd1 : {{ infoPwd1 }}</div>
    <div>pwd2 : {{ infoPwd2 }}</div>
    <div>nickname : {{ infoNickName }}</div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userEmail: 'OOOO@naver.com',
      infoPwd1: null,
      infoPwd2: null,
      infoNickName: null
    }
  }
}
</script>

<style>
  .mypage-info {
    display: flex;
    justify-content: space-around;
  }
  .mypage-info-black-space {
      height: 100px;
  }
</style>
