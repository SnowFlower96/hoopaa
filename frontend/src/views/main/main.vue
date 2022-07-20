<template>
  <el-container class="main-wrapper">
    <main-header class="main-header-class"/>
      <el-main>
        <router-view></router-view>
      </el-main>
    <main-footer :height="`110px`"/>
  </el-container>
</template>
<style>
  @import "https://unpkg.com/element-plus/lib/theme-chalk/index.css";
  @import './main.css';
  @import '../../common/css/common.css';
  @import '../../common/css/element-plus.css';
  /* .main-header-class {
  position: fixed;
} */
  .el-main::-webkit-scrollbar{width: 0px;} 
</style>
<script>
import MainHeader from './components/main-header'
import MainSidebar from './components/main-sidebar'
import MainFooter from './components/main-footer'

export default {
  name: 'Main',
  components: {
    MainHeader,
    MainSidebar,
    MainFooter
  },
  data () {
    return {
      loginDialogOpen: false
    }
  },
  methods: {
    onOpenLoginDialog () {
      this.loginDialogOpen = true
    },
    onCloseLoginDialog () {
      this.loginDialogOpen = false
    }
  }
}
</script>

