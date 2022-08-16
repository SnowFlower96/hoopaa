<template>
    <div class="ppbs-outer displayFlex">
        <div class="participating-page-blank-space"></div>
        <div class="ppbs-inner displayFlex">
          <div style="height: 100%;">

            <div class="displayFlex" style="font-size: 40px; color:white; height: 35%;">토론 입장</div>

            <div style="height: 60%;">
              <div v-if="!isPannel">방청객으로 입장합니다.</div>

              <div v-if="isPannel" class="displayFlex" >
                <div :class="{'ppbs-btn' : !isAgree, 'ppbs-btn-selected' : isAgree}" style="margin: 10px; margin-top: 0px;" @click="positionAgree">찬성</div>
                <div :class="{'ppbs-btn' : !isDisAgree, 'ppbs-btn-selected' : isDisAgree}" style="margin: 10px; margin-top: 0px;" @click="positionDisagree">반대</div>
              </div>

              <div class="displayFlex" style="margin-top: 30px;">
                <router-link style="text-decoration:none; margin: 10px; color: black;" to="/debateRoom"><div class="ppbs-sbm-btn displayFlex">입장하기</div></router-link>
              </div>
                <router-link style="text-decoration:none; color:white;" to="/"><div class="displayEnd ppbs-sbm-end">홈으로</div></router-link>
            </div>

          </div>
        </div>
    </div>
</template>
<script>
import { mapState } from 'vuex';
export default {
  data () {
    return {
      isPannel : true,
      sessionId : '',
      isAgree: false,
      isDisAgree: false,
    }
  },
  methods : {
    positionAgree () {
      this.$store.commit("SET_POSITION",'agree')
      let index = '/room/enter/select/' + this.sessionId + '?pos=agree'
      console.log(index)
      this.$store.dispatch("setPosition", index)
      this.isAgree = true
      this.isDisAgree = false
    },
    positionDisagree () {
      this.$store.commit("SET_POSITION",'disagree')
      let index = '/room/enter/select/' + this.sessionId + '?pos=disagree'
      this.$store.dispatch("setPosition", index)
      this.isDisAgree = true
      this.isAgree = false
    }
  },
  created () {
    var id = this.tempToken.split("?")[1].split("&")[0].split("=")[1]
    if (this.position == 'audience') {
      this.isPannel = false;
    }
    if (id != this.user.id) this.isPannel = true;
    this.sessionId = id;
  },
  computed : {
    ...mapState(["user", "tempToken", "position"]),
  },
}

</script>
<style>
.displayEnd {
  display: flex;
  justify-content: end;
}
.ppbs-outer {
  height: 93vh;
  width: 100%;
  background-image: linear-gradient( rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.3) ), url(../../assets/images/makeroombg.jpg);
  background-size : cover;
}
.participating-page-blank-space {
    height: 100px;
}
.ppbs-inner {
  width: 500px;
  height: 400px;
  background-color: rgba(0, 0, 0, 0.658);
  border-radius: 20px;
  box-shadow: 0px 0px 20px 3px rgba(0, 0, 0, 0.568);
}
.ppbs-btn {
  color: #6024ad;
  width: 150px;
  height: 90px;
  background-color: #d8bbff;
  outline: #d8bbff 3px solid;
  border-radius: 10px;
  font-size: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  filter: brightness(70%);
}
.ppbs-btn-selected {
  width: 150px;
  height: 90px;
  color: white;
  background-color: #8f4ae9;
  outline: #d8bbff 3px solid;
  border-radius: 10px;
  outline: solid 1px black;
  font-size: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  outline: 3px white solid;
  cursor: pointer;
}
.ppbs-btn:hover {
  background-color: #b189e6;
  cursor: pointer;
  outline: #d8bbff 3px solid;
  box-shadow: 2px 2px 2px 2px rgb(189, 189, 189);
}
.ppbs-btn:active {
  outline:none;
  box-shadow: 0px 0px 0px 0px black;
}
.ppbs-sbm-btn {
  width: 200px;
  height: 50px;
  border-radius: 10px;
  outline: solid 1px rgb(184, 184, 184);
  font-size: 30px;
  color: rgb(194, 194, 194);
}
.ppbs-sbm-btn:hover {
  cursor: pointer;
  color: white;
}
.ppbs-sbm-end {
  position: relative;
  top: 20px;
  left: 50px;
}
</style>

