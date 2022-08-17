<template>
        <div class="participate-black-space"></div>
    <div class="participate-background">
        <div>
            <div class="participate-with-code-container">
                <div class="pp-items-wrap">
                    <p style="font-size: 30px;">토론방 입장</p>
                    <p>참여코드를 받았나요?</p>
                    <div class="pp-code-item">
                        <input type="text" v-model="sessionId" placeholder="참여 코드를 입력하세요">
                    </div>
                    <div class="pp-code-item">
                        <input type="text" v-model="pwd" placeholder="방 비밀번호를 입력하세요">
                    </div>
                    <div class="pp-code-item">
                        <button class="pp-code-item-btn" @click="roomEnter">입장</button>
                    </div>
                </div>
            </div>
            <router-link style=text-decoration:none; to="/"><div class="pp-goto-home"><span>홈으로 돌아가기</span><img :src="require(`@/assets/images/favi6.png`)" alt="home-favi"></div></router-link>
        </div>
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
    
      this.$store.dispatch("enterRoom", data)
    }
  }
}
</script>

<style>
.pp-goto-home {
    padding-top: 10px;
    display: flex;
    justify-content: end;
    color: rgba(0, 0, 0, 0.656);
    height: 30px;
}
.pp-goto-home > span {
    margin-top: 20px;
}
.pp-goto-home > img {
    height: 20px;
    width: 25px;
    margin-left: 10px;
    margin-top: 18px;
}
.participate-with-code-container {
    width: 500px;
    height: 350px;
    background-color: #e8d6ff2f;
    /* outline: 3px solid  #E1CAFF; */
    box-shadow: 3px 10px 10px 3px  rgba(0, 0, 0, 0.112);
    border-radius: 10px;
    display: flex;
    justify-content: center;
    align-items: center;
    overflow: hidden;
}
.pp-items-wrap {
    width: 400px;
    text-align: center;
}
.pp-items-wrap > p {
    margin-top: 20px;
    margin-bottom: 20px;
}
.participate-black-space {
    width:100vh;
    height: 150px;
}
.participate-background {
    display: flex;
    justify-content: center;
    height: calc(90vh - 150px);
}
.pp-code-item > input {
    padding: 8px;
    border-radius: 5px;
    width:384px;
    border: none;
    outline: solid 1px #ABB2BF;
    margin-bottom: 10px;

}

.pp-code-item > input:focus {
    outline: solid 4px #E0E4EB;
    border: solid 1px black
}

.pp-code-item-btn:hover {
  cursor: pointer;
  box-shadow: 0px 2px 2px 0px rgba(0, 0, 0, 0.297);

}
.pp-code-item-btn {
  background-color: #e8d6ff;
  border-radius: 5px;
  border: none;
  width: 80px;
  height: 40px;
  box-shadow: 3px 10px 10px 3px  rgba(0, 0, 0, 0.086);
  outline: 1px solid #d8bbff;
  margin-top: 20px;
  margin-bottom: 20px;
}
.pp-code-item {
}
</style>
