<template>
  <el-container class="main-wrapper">
    <main-header v-if="visible" class="main-header-class" />
    <div>
      <router-view></router-view>
    </div>
    <main-footer class="main-footer-class"/>
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
      visible: true,
      location: null
    }
  },
  methods: {
    zeroLocation() {
      if (this.location == 'main-page') {
        const scrollPosition = window.scrollY || document.documentElement.scrollTop;
        const percentageOfPageScroll = scrollPosition/document.body.scrollHeight *100
        console.log('돌아가는중',this.visible, this.location)
        if (percentageOfPageScroll < 15) {
          this.visible = false
        } else {
          this.visible = true
        }
      }

    }
  },
  watch: {
    '$route' (to, from) {
      if (to.name === 'main-page' || to.name === 'Login' || to.name === 'sign-up' ) {
        this.location = to.name
        this.visible = false
        document.addEventListener('scroll', this.zeroLocation)
        }
      else {
        this.location = to.name
        this.visible = true
      }
    }
  },
  mounted() {
  }
}
</script>

