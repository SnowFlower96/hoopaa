<template>
  <el-row class="main-header" :gutter="10">

    <div class="hide-on-small">

        <router-link to="/">
          <div class="logo-wrapper"><div class="ic ic-logo"/></div>
        </router-link>

        <div class="el-menu">
          <router-link class="tool-wrapper-span-login" style=text-decoration:none; to="/list">
            <span>모든 토론보기</span>
          </router-link>
          <router-link class="el-menu-item button-purple" style=text-decoration:none; to="/makeRoomPage">
            <span>미팅 시작하기</span>
          </router-link>
          <router-link class="el-menu-item button-green" style=text-decoration:none; to="/startWithCode">
            <span>미팅 참여하기</span>
          </router-link>
        </div>

        <div class="tool-wrapper">
          <router-link style=text-decoration:none; to="/test"><span>session</span></router-link>
            <router-link style=text-decoration:none; to="/login" v-if="!isLogin"><span class="tool-wrapper-span-login">Log in</span></router-link>
            <router-link style=text-decoration:none; to="" v-if="isLogin" @click="logout"><span class="tool-wrapper-span-login">Log out</span></router-link>
            <router-link style=text-decoration:none; to="/signUp" v-if="!isLogin"><span class="tool-wrapper-span-signup">Sign up</span></router-link>
            <router-link style=text-decoration:none; to="/myPage" v-if="isLogin"><span class="tool-wrapper-span-signup">My Page</span></router-link>
        </div>

    </div>

    <div class="hide-on-big icons-wrapper">
      <div class="logo-wrapper" @click="clickLogo">
        <div>
          <router-link to="/"><img class="header-logo-img" :src="require(`@/assets/images/favi6.png`)"></router-link>
        </div>
        <img @click="showDropDown" class="header-small-bar" :src="require(`@/assets/icons/bar.png`)">
      </div> <!-- 로고 있는 부분 -->
      <!-- bar는 더 좋은 아이콘을 찾아봐야됨 -->
      <div v-if="dropDownMenu" class="small-size-menu-wrap">
        <router-link style=text-decoration:none; to="/login"><p class="small-size-menu-wrap-p">login</p></router-link>
        <router-link style=text-decoration:none; to="/signUp"><p class="small-size-menu-wrap-p">sign up</p></router-link>
      </div>

    </div>

  </el-row>
</template>

<script>
import { reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { mapState , mapMutations} from "vuex";

export default {
  name: 'main-header',

  computed : {
    ...mapState(["isLogin"]),
  },
  data() {
    return {
      dropDownMenu: false
    }
  },
  props: {
    height: {
      type: String,
      default: '70px'
    }
  },

  methods : {
    ...mapMutations(["USER_LOGOUT"]),
   logout() {
      this.USER_LOGOUT();
      this.$router.push('/');
    },
    showDropDown() {
      this.dropDownMenu = !this.dropDownMenu
    },
    mypage() {
      this.$router.push('/myPage')
    }
  }
}
</script>
<style scoped>

  /*Mobile, Tablet*/
  .small-size-menu-wrap {
    color: black;
    text-align: center;
  }
  .small-size-menu-wrap-p {
    color: black;
  }

  .header-small-bar {
    width: 20px;
    height: 20px;
    align-items: center;
    padding: 10px;
  }
  .header-small-bar:hover {
    cursor: pointer;
  }
  .icons-wrapper{
    display: flex;
  }

  .main-header .hide-on-small .logo-wrapper .ic.ic-logo {
    width: 70px;
    height: 50px;
    background-size: contain;
    background-repeat: no-repeat;
    background-image: url('../../../assets/images/favi6.png');
  }

  .menu-icon-wrapper {
    display: inline-block;
    vertical-align: top;
    position: relative;
    top: 14px;
  }

  .logo-wrapper {
    display: flex;
    justify-content: end;
  }

  .header-logo-img {
    width: 50px;
    padding: 5px;
  }

  /*Desktop - Need to add Class if Need*/
  .main-header .hide-on-small {
    display: flex;
    padding: 5px 10px;
    justify-content: space-evenly;
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
