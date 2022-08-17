<template>
<!-- 확인용 : 곧없애는 코드 -->
<div style="position:absolute;">
  <button @click="isSysStatus0">사회자모드일때</button>
  <button @click="isSysStatus1">시스템모드일때</button>
</div>
<!-- 확인용 : 곧없애는 코드 -->

  <div class="go-room-bg">
    <div class="go-room-outer">
    <div class="go-room-blank"></div>
      <div class="go-room-container">
        <div class="go-room-label">방 입장</div>
          <div class="go-select-wrap">
            <div class="go-select-inner">
              <div><img :class="{'go-select-activate':isSys === '0', 'go-select-deactivate':isSys === '1'}" src="https://user-images.githubusercontent.com/87743473/182767796-d6da026b-e704-4870-828e-acfaa287a18d.png" alt=""></div>
              <div><img :class="{'go-select-activate':isSys === '1', 'go-select-deactivate':isSys === '0'}" src="https://user-images.githubusercontent.com/87743473/182761383-205f241e-0fad-4d7e-bf74-544966eb2570.png" alt=""></div>
            </div>
          </div>
      <div class="go-select-status" v-if="isSys == '0'">👨‍⚖️사회자모드👩‍⚖️</div>
      <div class="go-select-status" v-if="isSys == '1'">🤖시스템모드🤖</div>

          <div>
            <div class="go-inner-wrap">
              <div class="go-label-container "><p>방 이름</p></div>
              <div class="go-input-container">여기에 방 제목 넣기</div>
            </div>

            <div class="go-inner-wrap">
              <div class="go-label-container "><p>주제</p></div>
              <div class="go-input-container">
                <div class="go-hash-combo">cate</div>
                <div class="go-hash-wrap-inner">
                  <div class="go-hash"><p>hash1</p></div>
                  <div class="go-hash"><p>hash2</p></div>
                  <div class="go-hash"><p>hash3</p></div>
                </div>
              </div>
            </div>
            
            <div class="go-inner-wrap">
                <div class="go-label-container"><p>토론인원</p></div>
              <div class="go-input-container">
                3명
              </div>
            </div>
          </div>
          <router-link style=text-decoration:none; to="/debateRoom"><div class="go-start-btn" @click="makeRoom"><span>토론방 입장</span></div></router-link>
          <router-link style=text-decoration:none; to="/"><div class="go-goto-home-sys"><span>홈으로 돌아가기</span><img :src="require(`@/assets/images/favi6.png`)" alt="home-favi"></div></router-link>
      </div>
    </div>
  </div>
    <div :class="{'go-room-outer-mod':isSys === '0', 'go-room-outer-sys':isSys === '1'}">
    </div>
</template>

<script>
export default {
  components : {
  },
  data () {
    return {
      isSys : '0'
    }
  },
  methods: {
    isSysStatus0() {
      this.isSys = '0'
    },
    isSysStatus1() {
      this.isSys = '1'
    },
  }
}

</script>
<style>
.go-start-btn {
  display: flex;
  justify-content: center;
  right: 0px;
  margin-top: 30px;
  color: white;
}
.go-start-btn > span:hover {
  background-color: rgba(255, 255, 255, 0.232);
  cursor: pointer;
}
.go-start-btn > span {
  padding: 10px;
  border-radius: 10px;
  outline: 1px white solid;
}
.go-room-label {
  font-size: 30px;
  margin: 20px;
}
.go-select-inner {
  display: flex;
  justify-content: center;
}
.go-select-deactivate {
  filter: brightness(40%);
  width: 180px;
  height: 180px;
  border-radius: 10px;
  
}
.go-select-activate {
  width: 180px;
  height: 180px;
  outline: 4px solid #FFDB65;
  border-radius: 10px;
  filter: brightness(100%);
}
.go-goto-home-sys {
    padding-top: 10px;
    display: flex;
    justify-content: end;
    color: white;
    height: 30px;
    margin-right: 20px;
    margin-bottom: 10px;
}
.go-goto-home-sys > img {
    height: 20px;
    width: 25px;
}
.goto-home-sys {
  display: flex;
  justify-content: end;
  margin-bottom: 20px;
}
.go-room-container {
  width: 500px;
  background-color: #0000009d;
  /* outline: 3px solid  #E1CAFF; */
  box-shadow: 3px 10px 10px 3px  rgba(0, 0, 0, 0.112);
  border-radius: 10px;
  overflow: hidden;
}
.go-inner-wrap {
  margin: 5px;
}
.go-select-status{
  overflow: hidden;
  animation: go-select-status 1s ease-in-out;
  font-size: 30px;
  margin-bottom: 20px;
}
@keyframes go-select-status{
  0% {
    opacity: 0;
    transform: translateY(10px);
  }
  100% {
    opacity: 1;
    transform: none;
  }
}
.go-room-outer-sys {
  height: 100vh;  
  /* background-image: url(https://user-images.githubusercontent.com/87743473/182767796-d6da026b-e704-4870-828e-acfaa287a18d.png); */
  background-image: linear-gradient( rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.3) ), url(https://user-images.githubusercontent.com/87743473/182761383-205f241e-0fad-4d7e-bf74-544966eb2570.png);
  animation: outer-bg 1s ease-in-out;
  background-size : cover;
}
.go-room-outer-mod {
  height: 100vh;  
  background-image: linear-gradient( rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.3) ), url(https://user-images.githubusercontent.com/87743473/182767796-d6da026b-e704-4870-828e-acfaa287a18d.png);
  /* background-image: linear-gradient( rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.3) ), url(https://user-images.githubusercontent.com/87743473/182761383-205f241e-0fad-4d7e-bf74-544966eb2570.png); */
  animation: outer-bg 1s ease-in-out;
  background-size : cover;
  
}
.go-room-blank {
  width: 100px;
  height: 90px;
}
.go-room-bg {
  display: flex;
  justify-content: center;
  color: white;
}
.go-room-outer {
  z-index: 5;
  position: absolute;
  text-align: center;
  height: 100vh;  
  width: 500px;
}
@keyframes outer-bg{
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}
.make-room-black-space {
  height: 100px;
}

.go-label-container > p {
  margin: 0px;
}

.go-select-inner > div > img {
  margin: 10px;
}
.go-inner-wrap, .go-hash-wrap-inner{
  display: flex;
  justify-content: center;
  margin: 7px;
  margin-left: 25px;
  margin-right: 25px;
}
.go-inner-wrap {
  border-radius: 5px;
  /* outline: solid white 3px; */
  overflow: hidden;
}

.go-hash-wrap-inner {
  margin: 0px;
  height: 100%;
}
.go-label-container {
  width: 170px;
  height: 30px;
  background-color: #667799;
  display: flex;
  align-items: center;
  justify-content: center;
}
.go-input-container {
  width: 280px;
  height: 30px;
  background-color: #667799a1;
  display: flex;
  align-items: center;
  justify-content: space-evenly;
}
.name-input {
  width: 100%;
  height: 100%;
}
.pw-input {
  width: 100%;
  height: 100%;
}
.go-hash {
  width: 73px;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
