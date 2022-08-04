<template>
    <div>
        <div class="make-room-black-space"></div>
        <h1>
            방 만들기
        </h1>
        <div id="main-container" class="container">
		<div id="join">
			<div id="join-dialog" class="jumbotron vertical-center">


          			<p>
						<label>host</label>
						<input v-model="host" class="form-control" type="text" required>
					</p>
          <div><input v-model="roomName" type="text" placeholder="방 이름"/></div>
          <div><input type="checkbox"><input v-model="roomPwd" type="password" placeholder="비밀번호"></div>
          <div><input v-model="roomTitle" type="text" placeholder="주제"></div>
          <div class="dropdown-sort-wrap">
                          <button class="dropdown-sort-btn" @click="dropdownSortBtnTF">카테고리</button>
                          <div id="dropdown-sort" v-if="dropdownSortTF">
                            <div v-for="(item, index) in menus" :key="index" @click="choiceCate(index)">{{item.name}}</div>
                          </div>
                        </div>
          <div><input v-model="hashTag1" type="text" placeholder="해쉬태그1"></div>
          <div><input v-model="hashTag2" type="text" placeholder="해쉬태그2"></div>
          <div><input v-model="hashTag3" type="text" placeholder="해쉬태그3"></div>
          <div>사회자없이 진행 <input v-model="isSys" type="checkbox" true-value="1" false-value="0"></div>
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
    }
  },
   computed : {
    ...mapState(["userStat"])
  },
  created() {
    const menuData = require('@/views/main/menu.json')
    this.menus = menuData;
    this.host = this.$store.state.userStat.em;
    this.myUserName = this.$store.state.userStat.nnm;
  },
  methods : {
     dropdownSortBtnTF() {
      this.dropdownSortTF = !this.dropdownSortTF
    },
    choiceCate(index) {
      this.roomCate = index;
      console.log(this.roomCate);
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

    }
  }
}

</script>
<style>
.make-room-black-space {
    height: 100px;
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
