<template>
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
      location: null
    }
  },
  methods: {
    zeroLocation() {
      if (this.location == 'main-page') {
        const scrollPosition = window.scrollY || document.documentElement.scrollTop;
        const percentageOfPageScroll = scrollPosition/document.body.scrollHeight *100
        // console.log('돌아가는중',this.visible, this.location)
        if (percentageOfPageScroll < 15) {
          this.visibleMainHeader = false
        } else {
          this.visibleMainHeader = true
        }
      }

    }
  },
  watch: {
    '$route' (to, from) {
      console.log(to.name)
      if (to.name === 'main-page' || to.name === 'login' || to.name === 'sign-up' || to.name === 'checkPwd' ) {
        this.location = to.name
        this.visibleMainHeader = false
        document.addEventListener('scroll', this.zeroLocation)
        } 
      else if (to.name === 'debateRoom' || to.name === 'detailSession') {
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
  }
}
</script>

