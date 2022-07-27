<template>
  <div>
    <h1>{{em}} 으로 가입인증 메일을 보내드렸습니다.</h1>
    <div>해당 메일을 확인하시고 인증을 완료해 주세요</div>

  </div>
</template>

<script>
export default {

  data () {
    return {
      email : '',
    }
  },
  created () {
    let query = window.location.search;
    let param = new URLSearchParams(query);
    let em = param.get('em');
    this.em = em;
  }
}
</script>

<style>

</style>
