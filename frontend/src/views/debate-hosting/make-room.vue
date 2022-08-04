<template>
    <div class="make-room-outer">
        <!-- <div class="make-room-black-space"></div> -->
        <h1>방 만들기</h1>
        <div class="make-room-main-container">
		      <div id="join">
			      <div id="join-dialog" class="jumbotron vertical-center">
                <div class="sys-mod-mode-sel-item">
                  <div class="fadein-make-room" v-if="isSys == '0'">사회자모드</div>
                  <div class="fadein-make-room" v-if="isSys == '1'">시스템모드</div>
                  <div class="sys-mod-mode-sel-outer">
                    <div><img :class="{'sys-mod-mode-sel-actived':isSys === '0', 'sys-mod-mode-sel':isSys === '1'}" @click="isSysStatus0" src="https://user-images.githubusercontent.com/87743473/182767796-d6da026b-e704-4870-828e-acfaa287a18d.png" alt=""></div>
                    <div><img :class="{'sys-mod-mode-sel-actived':isSys === '1', 'sys-mod-mode-sel':isSys === '0'}" @click="isSysStatus1" src="https://user-images.githubusercontent.com/87743473/182761383-205f241e-0fad-4d7e-bf74-544966eb2570.png" alt=""></div>
                  </div>
                </div>

                <div class="sys-mod-mode-sel-item"><input v-model="roomName" type="text" placeholder="방 이름"/></div>
                <div class="password-wrap">
                  <input type="checkbox"><div class="sys-mod-mode-sel-item"><input v-model="roomPwd" type="password" placeholder="비밀번호"></div>
                </div>

                <div class="obj-hashtag-wrap">
                  <select v-model="selectedMenu" class="menu-combo-box">
                    <option v-for="(item, index) in menus" :key="index" :value="item">{{item.name}}</option>
                  </select>

                  <div><input class="sys-mod-hash" v-model="hashTag1" type="text" placeholder="해쉬태그1"></div>
                  <div><input class="sys-mod-hash" v-model="hashTag2" type="text" placeholder="해쉬태그2"></div>
                  <div><input class="sys-mod-hash" v-model="hashTag3" type="text" placeholder="해쉬태그3"></div>
                </div>

                <div>토론인원
                  1<input type="radio" name="maxNum" value="1" @change="getNum($event)"/>
                  2<input type="radio" name="maxNum" value="2" @change="getNum($event)"/>
                  3<input type="radio" name="maxNum" value="3" @change="getNum($event)"/>
                  4<input type="radio" name="maxNum" value="4" @change="getNum($event)"/>
                  5<input type="radio" name="maxNum" value="5" @change="getNum($event)"/>
                </div>
                <router-link to="/"><button>뒤로가기</button></router-link> <button @click="makeRoom">토론방 생성</button>
            </div>
          </div>
        </div>
    </div>
</template>

<script>

import { mapState } from 'vuex';
import router from '../../common/lib/vue-router';


export default {
  components : {
  },
  data () {
    return {
      menuData : require('@/views/main/menu.json'),
      dropdownSortTF : false,
      host: '',
      mySessionId: 'SessionA',
      myUserName: '',
      roomName : '',
      roomPwd : '',
      meuns : '',
      roomTitle : '',
      roomCate : '',
      hashTag1 : '',
      hashTag2 : '',
      hashTag3 : '',
      maxNum : '',
      isSys : '0',
      selectedMenu: null
    }
  },
   computed : {
    ...mapState(["userStat"])
  },
  created() {
    this.menus = this.menuData;
    this.host = this.$store.state.userStat.em;
    this.myUserName = this.$store.state.userStat.nnm;
  },
  methods : {
     dropdownSortBtnTF() {
      this.dropdownSortTF = !this.dropdownSortTF
    },
    getNum(event) {
        this.maxNum = event.target.value;
        console.log(this.maxNum)
    },
    makeRoom() {
      var room = {
        cate : this.roomCate,
        hash_1 : this.hashTag1,
        hash_2 : this.hashTag2,
        hash_3 : this.hashTag3,
        host_em : this.host,
        is_sys : this.isSys,
        max_num : this.maxNum,
        pwd : this.roomPwd,
        subtitle : this.roomName,
        title : this.roomTitle
      }
      console.log(room)
      this.$store.dispatch("makeRoom",room).then(response => {
        let data = {
          sessionId : this.$store.state.userStat.em,
          pwd : this.pwd
        }
         this.$store.dispatch("enterRoom", data)

      })

    },
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
.password-wrap {
  display: flex;
  justify-content: center;
}
.sys-mod-mode-sel-item {
  margin: 10px;
}
.fadein-make-room{
  overflow: hidden;
  animation: fadein-make-room 1s ease-in-out;
  font-size: 30px;
}

@keyframes fadein-make-room{
  0% {
    opacity: 0;
    transform: translateY(10px);
  }
  100% {
    opacity: 1;
    transform: none;
  }
}
.sys-mod-hash {
  padding: 8px;
  border-radius: 5px;
  border: none;
  background-color: #E0E4EB;
  margin: 10px;
  width: 70px;
}
.sys-mod-hash:focus {
  outline: solid 2px #ABB2BF;
  background-color: #d7dae0;
}
.sys-mod-mode-sel-outer{
  display: flex;
  justify-content: center;
}
.sys-mod-mode-sel {
  width: 150px;
  height: 150px;
  box-shadow: 2px 2px 2px 2px rgb(178, 178, 178);
  outline: 2px solid rgb(163, 163, 255);
  border-style:outset;
  border-width: 8px;
  cursor: pointer;
  margin: 10px;
  border-radius: 10px;
}
.sys-mod-mode-sel:hover {
  box-shadow: 3px 3px 3px 3px rgb(178, 178, 178);
  outline: 3px solid rgba(163, 163, 255, 0.611);
  border-style:outset;
  cursor: pointer;
}
.sys-mod-mode-sel:active {
  border-style:inset;
  filter: brightness(80%);
  box-shadow: 0px 0px 0px 0px gray;
}
.sys-mod-mode-sel-actived {
  width: 150px;
  height: 150px;
  outline: 2px solid rgb(163, 163, 255);
  border-style:outset;
  cursor: pointer;
  margin: 10px;
  border-radius: 10px;
  border-style:inset;
  border-width: 8px;
  filter: brightness(60%);
  box-shadow: 0px 0px 0px 0px gray;
}
.sys-mod-mode-sel-item > input {
  padding: 8px;
  border-radius: 5px;
  border: none;
  background-color: #E0E4EB;
}
.sys-mod-mode-sel-item > input:focus {
  outline: solid 2px #ABB2BF;
  background-color: #d7dae0;
}

.menu-combo-box {
  height: 20px;
}
.make-room-outer {
  text-align: center;
}
.make-room-black-space {
    height: 100px;
}

.obj-hashtag-wrap {
  display: flex;
  justify-content: center;
}
.make-room-main-container {
  height: 70vh;
}

#dropdown-sort {
  position: absolute;
  background-color: white;
  border: solid 1px rgba(180, 180, 180, 0.505);
}
#dropdown-sort > div {
  padding: 10px;
}
#dropdown-sort > div:hover {
  cursor: pointer;
  color: #667799;
}
.dropdown-sort-btn {
  border: none;
  border-radius: 5px;
  background-color: #667799;
  color: white;
}
.dropdown-sort-btn:hover {
  cursor: pointer;
  outline: solid 2px #667799c1;
}
</style>
