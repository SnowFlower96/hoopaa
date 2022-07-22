<template>
  <el-row class="main-header" :gutter="10">

    <div class="hide-on-small">

        <router-link to="/mainPage">
          <div class="logo-wrapper"><div class="ic ic-logo"/></div>
        </router-link>

        <div class="el-menu">
          <router-link class="tool-wrapper-span-login" style=text-decoration:none; to="/">
            <span>모든 토론보기</span>
          </router-link>
          <router-link class="el-menu-item button-purple" style=text-decoration:none; to="/makeRoomPage">
            <span>미팅 시작하기</span>
          </router-link>
          <router-link class="el-menu-item button-green" style=text-decoration:none; to="/participatingPage">
            <span>미팅 참여하기</span>
          </router-link>
        </div>

        <div class="tool-wrapper">
            <router-link style=text-decoration:none; to="/login"><span class="tool-wrapper-span-login">Log in</span></router-link>
            <router-link style=text-decoration:none; to="/signUp"><span class="tool-wrapper-span-signup">Sign up</span></router-link>
        </div>

    </div>

    <div class="hide-on-big">
      <div class="logo-wrapper" @click="clickLogo"><div class="ic ic-logo"/></div> <!-- 로고 있는 부분 -->
    </div>

  </el-row>
</template>

<script>
import { reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'main-header',

  props: {
    height: {
      type: String,
      default: '70px'
    }
  },

  setup(props, { emit }) {
    const store = useStore()
    const router = useRouter()
    const state = reactive({
      searchValue: null,
      isCollapse: true,
    })
    const changeCollapse = () => {
      state.isCollapse = !state.isCollapse
    }

    return { state, changeCollapse }
  }
}
</script>
<style>

  /*Mobile, Tablet*/
  .menu-icon-wrapper {
    display: inline-block;
    vertical-align: top;
    position: relative;
    top: 14px;
  }

  .main-header .hide-on-big .logo-wrapper {
    display: inline-block;
    margin: 0 calc(50% - 51px)
  }
  .main-header .hide-on-big .logo-wrapper .ic.ic-logo {
    width: 70px;
    height: 50px;
    background-size: contain;
    background-repeat: no-repeat;
    background-image: url('../../../assets/images/favi6.png');
  }

  /*Desktop - Need to add Class if Need*/
  .main-header .hide-on-small {
    display: flex;
    padding: 5px 10px;
    justify-content: space-evenly;
  }
  .main-header .hide-on-small .logo-wrapper .ic.ic-logo {
    width: 70px;
    height: 50px;
    background-size: contain;
    background-repeat: no-repeat;
    background-image: url('../../../assets/images/favi6.png');
  }

  .el-menu {
    margin-right: 500px;
    margin-top: 15px;
  }
  .el-menu-show-all {
    border-radius: 5px;
    background-color: none;
    color: #1bb061;
    padding: 8px;
    cursor: pointer;
  }
  .el-menu-show-all:hover, .tool-wrapper-span-login:hover {
    font-weight: bold;
  }
  .el-menu-item {
    border-radius: 5px;
    background-color: none;
    padding: 8px;
    cursor: pointer;
    margin: 10px;
  }
  .el-menu-item:hover {
    border-radius: 5px;
    color: white;
    padding: 8px;
  }
  .button-purple {
    outline: solid 1px #9747ff;
    color: #9747ff;
  }
  .button-purple:hover {
    background-color: #9747ff ;
  }
  .button-green {
    outline: solid 1px #1bb061;
    color: #1bb061;
  }
  .button-green:hover {
    background-color: #1bb061 ;
  }

  .tool-wrapper {
    padding: 15px;
    justify-content: end;
  }
  .tool-wrapper-span-signup {
    outline: solid 1px #1bb061;
    border-radius: 5px;
    background-color: none;
    color: #1bb061;
    padding: 10px;
    cursor: pointer;
    margin: 5px;
    font-weight: bold;
  }
  .tool-wrapper-span-signup:hover {
    outline: solid 1px #1bb061;
    border-radius: 5px;
    background-color: #1bb061 ;
    color: white;
  }
  .tool-wrapper-span-login {
    border-radius: 5px;
    background-color: none;
    color: #1bb061;
    padding: 10px;
    cursor: pointer;
    margin: 5px;
  }



</style>
