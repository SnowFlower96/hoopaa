<template>
    <div id="mainpage-maincontainer">
        <div class="main-img-wrapp">
            <div class="fadein">
                <img :src="require(`@/assets/images/071414.png`)" alt="">
                <h2>토론에 최적화된 미팅 서비스</h2>
            </div>
            <div>
                <img :src="require(`@/assets/images/mainPage01.png`)" alt="">
                <h2>팀을 구성하세요</h2>
            </div>
            <div>
                <img :src="require(`@/assets/images/mainPage02.png`)" alt="">
                <h2>친구들과 재미있게 의견을 나눠보세요!</h2>
            </div>
            <transition name="fade">
                <div v-if="show" class="image">
                    <h1>안녕하세요</h1>
                </div>
            </transition>
            <div>
                <img :src="require(`@/assets/images/mainPage03.png`)" alt="">
                <h2>배틀에서 승리하세요!</h2>
            </div>
            <div>
                <img :src="require(`@/assets/images/mainPage04.png`)" alt="">
                <h2>재미있는 효과들과 함께 하세요</h2>
            </div>
            <div>
                <img :src="require(`@/assets/images/mainPage05.png`)" alt="">
                <h2>방청객이 되어서 배틀을 관전할 수도 있습니다.</h2>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data(){
      return{
        show: false,
        scrollPosition: null
      }
    },
    methods: {
      updateScroll(){
        this.scrollPosition = window.scrollY || document.getElementById('mainpage-maincontainer').scrollTop;
        console.log(this.scrollPosition)
        if(this.scrollPosition > 50){
          this.show = true;
        } else {
          this.show = false;
        }
      }
    },
    mounted(){
      window.addEventListener('scroll', this.updateScroll);
    }
}
</script>

<style>
/* 스크롤 로직 */
.fade-enter-from {
  opacity: 0;
  transform: translateY(150px);
}

.fade-enter-active {
  transition: all 1s ease;
}

.fade-enter-to {
  opacity: 1;
  transform: translateY(0px);
}
/* 스크롤 로직 */

.fadein{
  overflow: hidden;
  animation: fadein 1s ease-in-out;
}

@keyframes fadein{
  0% {
    opacity: 0;
    transform: translateY(50px);
  }
  100% {
    opacity: 1;
    transform: none;
  }
}

.main-img-wrapp > div > img {
    width: 80%;
}
.main-img-wrapp > div {
    margin: 20%;
}

</style>


