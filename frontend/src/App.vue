<template>
  <!-- main 으로 시작 -->
  <Main/>
</template>

<script>
import Main from './views/main/main.vue'

export default {
  name: 'App',

  components: {
    Main,
  },
  created () {
    this.$store.commit('RESET_STATE');
  },

  data: () => ({
    //
  }),
}
</script>
