<template>
    <div>
        <div class="participating-page-black-space"></div>
        <h1>
            토론 입장
        </h1>
        <button v-if="isPannel" @click="positionAgree">찬성</button>
        <button v-if="isPannel" @click="positionDisagree">반대</button>
        <router-link to="/debateRoom"><button>입장</button></router-link>
        <router-link to="/"><button>취소</button></router-link>

    </div>
</template>
<script>
import router from '../../common/lib/vue-router'
import { mapState} from 'vuex';
export default {
  data () {
    return {
      isPannel : false,
      sessionId : '',
    }
  },
  methods : {
    positionAgree () {
      this.$store.commit("SET_POSITION",'agree')
      let index = '/room/enter/select/' + this.sessionId + '?pos=agree'
      console.log(index)
      this.$store.dispatch("setPosition", index)
    },
    positionDisagree () {
      this.$store.commit("SET_POSITION",'disagree')
      let index = '/room/enter/select/' + this.sessionId + '?pos=disagree'
      this.$store.dispatch("setPosition", index)
    }
  },
  created () {
    var id = this.tempToken.split("?")[1].split("&")[0].split("=")[1]
    if (id != this.user.id) this.isPannel = true;
    this.sessionId = id;
  },
  computed : {
    ...mapState(["user", "tempToken"]),
  },
}

</script>
<style>
.participating-page-black-space {
    height: 100px;
}
</style>
