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
    }
  },
  methods : {
    positionAgree () {
      this.$store.state.position = 'agree'
    },
    positionDisagree () {
      this.$store.state.position = 'disagree'
    }
  },
  created () {
    var id = this.tempToken.split("?")[1].split("&")[0].split("=")[1]
    if (id != this.user.em) this.isPannel = true;
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
