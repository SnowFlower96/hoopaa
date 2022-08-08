<template>
  <div class="email-background">
    <div class="email-blank-space"></div>
    <div>
      <h1>가입을 축하합니다!</h1>
      <p>{{em}} 으로 인증 메일을 보내드렸습니다.</p>
      <p>해당 메일을 확인하시고 인증을 완료해 주세요</p>
    </div>
  </div>
</template>

<script>
export default {

  data () {
    return {
      em : '',
    }
  },
  created () {
    let query = window.location.search;
    let param = new URLSearchParams(query);
    let em = param.get('em');
    this.em = em;
    var data = {
      em : em
    }
    this.$store.dispatch("sendEmail",data);
    },
}
</script>

<style>
.email-background {
  height: 90vh;
  text-align: center;
}
.email-blank-space {
  width: 100vh;
  height: 300px;
}
</style>
