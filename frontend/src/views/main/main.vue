<template>
  <div class="size-block" v-if="sizeBlock">
    <div class="size-block-inner displayFlex">
      <div class="sbi-inner displayFlex">
        <div>
          <div style="font-size:30px;" class="displayFlex">화면 크기를 키워주세요 😥</div>
          <div class="displayFlex">가로 100px, 세로 600px 이상의 화면 크기에서만 지원합니다</div>
        </div>
      </div>
    </div>
  </div>
  <el-container class="main-wrapper">
    <main-header v-if="visibleMainHeader" class="main-header-class" />
      <router-view></router-view>
    <main-footer v-if="visibleMainFooter" class="main-footer-class"/>
  </el-container>
</template>
<style scoped>
  @import "https://unpkg.com/element-plus/lib/theme-chalk/index.css";
  @import './main.css';
  @import '../../common/css/common.css';
  @import '../../common/css/element-plus.css';
  .main-header-class {
  position: fixed;
  color: white;
  background-color: white;
  z-index: 10;
}
  .el-main::-webkit-scrollbar{width: 0px;}
  .main-footer-class {
    background-color: white;
  }
.size-block {
  position: fixed;
  padding: 10px;
  padding-left: 20px;
  z-index: 250;
  height: 100vh;
  width: 100%;
}
.size-block-inner{
  background: repeating-linear-gradient(45deg, rgba(85, 156, 156, 0.76), rgba(85, 156, 156, 0.76) 10px, rgba(79, 136, 93, 0.26) 0, rgba(79, 136, 93, 0.26) 20px);
  /* background-color: rgba(81, 163, 163, 0.712); */
  border: 10px solid rgb(94, 153, 153);
  border-radius: 10px;
  height: calc(100% - 60px);
  width: calc(100% - 60px);
  box-shadow: 2px 2px 2px 2px rgba(0, 0, 0, 0.274);
}
.sbi-inner {
  width: 550px;
  height: 100px;
  background-color: rgba(0, 0, 0, 0.603);
  border-radius: 10px;
  color: white;
}
</style>
<script>
import MainHeader from './components/main-header'
import MainFooter from './components/main-footer'

export default {
  name: 'Main',
  components: {
    MainHeader,
    MainFooter
  },
  data () {
    return {
      tired: null,
      visibleMainHeader: true,
      visibleMainFooter: true,
      location: null,
      sizeBlock: false,
    }
  },
  methods: {
    zeroLocation() {
      if (this.location == 'main-page') {
        const scrollPosition = window.scrollY || document.documentElement.scrollTop;
        const percentageOfPageScroll = scrollPosition/document.body.scrollHeight *100
        if (percentageOfPageScroll < 15) {
          this.visibleMainHeader = false
        } else {
          this.visibleMainHeader = true
        }
      }

    },
    handleResizeHome () {
      const wValue = document.body.clientWidth
      const hValue = document.body.clientHeight
      if (wValue < 1000 || hValue <600) {
        this.sizeBlock = true
      }
      else {
        this.sizeBlock = false
      }
    }
  },
  watch: {
    '$route' (to, from) {
      if (to.name === 'main-page' || to.name === 'login' || to.name === 'sign-up' || to.name === 'checkPwd' || to.name === 'Participate') {
        this.location = to.name
        this.visibleMainHeader = false
        document.addEventListener('scroll', this.zeroLocation)
        }
      else if (to.name === 'debateRoom'|| to.name === 'make-room' || to.name === 'homeToParticipate' || to.name === 'detailSessionView' || to.name === 'endDebate' || to.name === 'resultPage') {
        this.visibleMainHeader = false
        this.visibleMainFooter = false
      }
      else {
        this.location = to.name
        this.visibleMainHeader = true
        this.visibleMainFooter = true
      }
    }
  },
  mounted() {
    const wValue = document.body.clientWidth
    const hValue = document.body.clientHeight
    if (wValue < 1000 || hValue <600) {
        this.sizeBlock = true
    }
    else {
      this.sizeBlock = false
    }
    window.addEventListener('resize', this.handleResizeHome);
  }
}
</script>

