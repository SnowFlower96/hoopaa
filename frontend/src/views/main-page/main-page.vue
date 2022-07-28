<template>
    <div id="mainpage-maincontainer">
        <div class="main-img-wrapp">
          <div class="main-img-wrapp-child">
            <div class="main-img-wrap-child-buffer"></div>
            <img class="fadein" :src="require(`@/assets/images/071414.png`)" alt="">
            <p>토론을 위한 화상 미팅 서비스</p>
            <div class="main-img-wrapp-button" v-if="resizeWidthVisible">
              <router-link class="el-menu-item" to="/makeRoomPage"><img :src="require(`@/assets/images/main-page-button01.png`)" alt=""></router-link>
              <router-link class="el-menu-item" to="/participatingPage"><img :src="require(`@/assets/images/main-page-button02.png`)" alt=""></router-link>
              <router-link class="el-menu-item" to="/"><img :src="require(`@/assets/images/main-page-button03.png`)" alt=""></router-link>
            </div>
            <div class="main-first-img"  v-if="!resizeWidthVisible">
              <p>여기에 나중에 영상이나 gif 넣기</p>
              <img :src="require(`@/assets/images/0727_mokup_01.png`)" alt="">
            </div>
          </div>

          <div class="main-img-wrapp-child main-introduce" style="background-color: #fff8e0; color: #ffdb65"><img v-if="show1" class="fadein" :src="require(`@/assets/images/0727_mokup_01.png`)" alt=""><div><p>팀을 구성하세요</p></div></div>
          <div class="main-img-wrapp-child main-introduce" style="background-color: #debcb5; color: #f24822"><div><p>친구들과 재밌게 의견을 나눠보세요</p></div><img v-if="show2" class="fadein" :src="require(`@/assets/images/0727_mokup_04.png`)" alt=""></div>
          <div class="main-img-wrapp-child main-introduce" style="background-color: #e1b5d4; color: #ff24bd"><img v-if="show3" class="fadein" :src="require(`@/assets/images/0727_mokup_03.png`)" alt=""><div><p>배틀에서 승리하세요</p></div></div>
          <div class="main-img-wrapp-child main-introduce" style="background-color: #e0c8ff; color: #9747ff"><div><p>재미있는 효과들과 함께 하세요</p></div><img v-if="show4" class="fadein" :src="require(`@/assets/images/mainPage04.png`)" alt=""></div>
          <div class="main-img-wrapp-child main-introduce" style="background-color: #b3d1c1; color: #1bb061"><img v-if="show5" class="fadein" :src="require(`@/assets/images/mainPage05.png`)" alt=""><div><p>방청객이 되어서 배틀을 관전할 수도 있습니다</p></div></div>
        </div>
    </div>
</template>

<script>

export default {
    data(){
      return{
        show1: false,
        show2: false,
        show3: false,
        show4: false,
        show5: false,
        resizeWidthVisible: true
      }
    },
    methods: {
      updateScroll(){
        const scrollPosition = window.scrollY || document.documentElement.scrollTop;
        const percentageOfPageScroll = scrollPosition/document.body.scrollHeight *100
        if (15 < percentageOfPageScroll ) {this.show1 = true} 
        else {this.show1 = false}
        if (33 < percentageOfPageScroll ) {this.show2 = true}
        else {this.show2 = false}
        if (47 < percentageOfPageScroll) {this.show3 = true}
        else {this.show3 = false}
        if (64 < percentageOfPageScroll ) {this.show4 = true}
        else {this.show4 = false}
        if (81 < percentageOfPageScroll) {this.show5 = true}
        else {}
      },
      handleResize() {
          if (window.innerWidth < 1200) {
            this.resizeWidthVisible = false
          } else {
            this.resizeWidthVisible = true
          }
      }
    },
    mounted(){
      document.addEventListener('scroll', this.updateScroll);
      window.addEventListener('resize', this.handleResize);
    }
}
</script>

<style>
.main-first-img > img {
  width: 50%;
}

.main-first-img > p {
  font-size: 5px;
}

.fadein{
  overflow: hidden;
  animation: fadein 1s ease-in-out;
}

@keyframes fadein{
  0% {
    opacity: 0;
    transform: translateY(150px);
  }
  100% {
    opacity: 1;
    transform: none;
  }
}
.main-img-wrapp-item > img {
  justify-content: center;
    width: 70%;
}
.main-img-wrapp-item > img {
  margin: 10%;
}
.main-img-wrapp-button {
  display: flex;
  justify-content: center;
}
.main-img-wrapp-button > img {
  width: 20%;
}
.mainpage-maincontainer {
  display: flex;
  
}
.main-img-wrapp {
  display: felx;
}
.main-img-wrapp-child {
  left: 0;
  width: 100%;
  height: 100vh;
  padding: auto;
  text-align: center;
}
.main-img-wrap-child-buffer {
  left: 0;
  width: 100%;
  height: 20vh;
  padding: auto;
  text-align: center;
}
.main-img-wrapp-child > img {
  width: 40%;
}
.main-introduce {
  display: flex;
  align-items: center;
  justify-content: space-evenly;
}



/* introduce 사진 + 설명 => 반응형 */
.main-img-wrapp-child > div > p {
  font-size: 40px;
}
.main-introduce > div {
  width: 30%;
}

.main-img-wrapp-child > p {
  font-size: 30px;
}

@media (max-width: 800px) {
  .main-img-wrapp-button {
    width: 50%;
  }
  .main-img-wrapp-child > div > p {
  font-size: 25px;
  }
  .main-introduce > div {
  width: 30%;
  }
  .main-img-wrapp-child > p {
  font-size: 20px;
  }
}
/* introduce 사진 + 설명 => 반응형 */

</style>


