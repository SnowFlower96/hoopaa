<template>
    <div>
        <div class="participate-black-space"></div>
        <h1>참여 코드를 받았나요?</h1>
            <input v-model="sessionId" type="text" placeholder="sessionId 입력">
            <input v-model="pwd" type="password" placeholder="방 비밀번호 입력">
            <button @click="roomEnter">submit</button>

    </div>
</template>

<script>
export default {
  data () {
    return {
      sessionId : '',
      pwd : '',
    }
  },
  methods : {
    roomEnter() {
      let data = {
        pwd : this.pwd,
        sessionId : this.sessionId,
      }
      console.log(data)
      this.$store.dispatch("enterRoom", data)
    }
  }
}
</script>

<style>
.participate-black-space {
    height: 100px;
}
</style>
