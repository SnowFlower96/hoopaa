<template>
    <div id="mainpage-maincontainer">
        <div class="main-img-wrapp">
          <div class="main-img-wrapp-child">
            <div class="main-img-wrap-child-buffer"></div>
            <img class="fadein" :src="require(`@/assets/images/071414.png`)" alt="">
            <p>토론을 위한 화상 미팅 서비스</p>
            <!-- background-image: url('../../../assets/images/favi6.png'); -->
            <div class="main-img-wrapp-button" v-if="resizeWidthVisible">
              <router-link class="el-menu-item" to="/makeRoomPage"><div class="img-container-mainbtn01"></div></router-link>
              <router-link class="el-menu-item" to="/startWithCode"><div class="img-container-mainbtn02"></div></router-link>
              <router-link class="el-menu-item" to="/list"><div class="img-container-mainbtn03"></div></router-link>
            </div>
            <div class="main-first-img"  v-if="!resizeWidthVisible">
              <!-- <p>여기에 나중에 영상이나 gif 넣기</p>
              <img :src="require(`@/assets/images/0727_mokup_01.png`)" alt=""> -->
            </div>
          </div>

          <div class="main-img-wrapp-child" style="background-color: #fff8e0; color: #eac035; font-weight:border;">
            <div style="height: 3%;"></div>
            <div style="font-size:70px; margin:4%;">서비스 소개</div>
            <iframe width="700" height="400" src="https://www.youtube.com/embed/h42tRhiiv48" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
          </div>
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
        resizeWidthVisible: true,

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
      },
    },
    mounted(){
      document.addEventListener('scroll', this.updateScroll);
      window.addEventListener('resize', this.handleResize);
    }
}
</script>

<style>
.img-container-mainbtn01 {
  width: 300px;
  /* background-color: aqua; */
  height: 170px;
  background-image: url('../../assets/images/mainPageBtn0101.png');
  background-size:100% 100%;
}
.img-container-mainbtn01:hover {
  background-image: url('../../assets/images/mainPageBtn0102.png');
  background-size:100% 100%;
}
.img-container-mainbtn02 {
  width: 300px;
  /* background-color: aqua; */
  height: 170px;
  background-image: url('../../assets/images/mainPageBtn0201.png');
  background-size:100% 100%;
}
.img-container-mainbtn02:hover {
  background-image: url('../../assets/images/mainPageBtn0202.png');
  background-size:100% 100%;
}
.img-container-mainbtn03 {
  width: 300px;
  /* background-color: aqua; */
  height: 170px;
  background-image: url('../../assets/images/mainPageBtn0301.png');
  background-size:100% 100%;
}
.img-container-mainbtn03:hover {
  background-image: url('../../assets/images/mainPageBtn0302.png');
  background-size:100% 100%;
}

.el-menu-item > img{
  width: 300px;
}
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


/* introduce 사진 + 설명 => 반응형 */
.main-img-wrapp-child > div > p {
  font-size: 40px;
}

.main-img-wrapp-child > p {
  font-size: 30px;
}

@media (max-width: 800px) {
  .main-img-wrapp-button {
    width: 50%;
    /* color: #eac035; */
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


