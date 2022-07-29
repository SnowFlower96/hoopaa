<template>
  <!-- <div> -->
    <div>
    <div class="list-page-black-space"></div>
    <search></search>
    <!-- <hr/> -->
    <div class="left-main-wrap">

          <div class="left">
            <ul>
              <li id="logo">
                <div>카테고리</div>
              </li>
              <li v-for="(item, index) in menus" :key="index" @click="goCate(item.path)">{{item.name}}</li>
            </ul>
          </div>

        <div class="main-container">
          <hr/>
          <div class="list">
            <div>지금 이 토론이 제일 핫해요</div>
            <div class="carousel-wrapper">
              <ul class="carousel">
                <li v-for="(room, index) in roomList" :key="index">
                <span>{{phase[room.phase]}}</span>
                <el-card class="room-card"><img :src="require(`@/assets/images/room.jpg`)" alt="" class="room-info"/>
                <span>{{room.title}} <br></span>
                <span>{{room.subtitle}}</span>
                </el-card>
                </li>
              </ul>
            </div>
                <button class="prev" type="button" @click="prev">prev</button>
                <button class="next" type="button" @click="next">next</button>

            <div>
              <span>카테고리</span>
              <div class="flex flex-wrap items-center">
                <el-dropdown>
                  <el-button type="primary">
                    정렬조건<el-icon class="el-icon--right"><arrow-down /></el-icon>
                  </el-button>
                  <template #dropdown>
                    <el-dropdown-menu>
                      <el-dropdown-item @click="sortRecent">최신순</el-dropdown-item>
                      <el-dropdown-item @click="sortCur">참여자순</el-dropdown-item>
                      <el-dropdown-item @click="sortOld">오래된순</el-dropdown-item>
                      <el-dropdown-item @click="sortRank">랭킹순</el-dropdown-item>
                    </el-dropdown-menu>
                  </template>
                </el-dropdown>
              </div>
              <el-checkbox v-model="checkbox" @change="viewJoin">모집중만 보기</el-checkbox>
            </div>
            <div class="list">여기가 기본 all, 총 방 갯수 : {{roomList.length}}</div>
                <!-- 프정현 코드 -->
                <!-- <ul class="room-ul">
                  <li v-for="(room, index) in roomList" :key="index">
                      <div class="room-card">
                        <span>{{phase[room.phase]}} {{room.id}}</span>
                        <img :src="require(`@/assets/images/room.jpg`)" alt="" class="room-info"/>
                        <span>{{room.title}} <br></span>
                        <span>{{room.subtitle}}</span>
                      </div>
                  </li>
                </ul> -->
                <!-- 프정현 코드 -->

                <!-- 새로운 로직 -->
                <ul class="room-ul">
                  <li v-for="(indexOut) in (roomList.length/4-1)" :key="indexOut"><ul class="card-container-ul">
                    <li v-for="(indexIn) in 4" :key="indexIn">
                      <div class="room-card">
                        <span>{{phase[roomList[(4 * (indexOut-1)) + indexIn-1].phase]}} {{roomList[(4 * (indexOut-1)) + indexIn-1].id}}</span>
                        <img :src="require(`@/assets/images/room.jpg`)" alt="" class="room-info"/>
                        <p>{{roomList[(4 * (indexOut-1)) + indexIn-1].id}} : <span>{{roomList[(4 * (indexOut-1)) + indexIn-1].subtitle}}</span></p>
                      </div>
                    </li>
                  </ul></li>
                </ul>
                <!-- 새코드 -->

          </div>
        </div>
      </div>
    </div>
  <!-- </div> -->
</template>

<style>
/*  */
.carousel-wrapper {
  width: 100%;
  height: 300px;
  overflow-x: hidden;
  overflow-y: hidden;
  background-color: aqua;
}

.carousel {
  display: flex;
  flex-direction: row;
  /* overflow: auto; */
  list-style: none;
}

.carousel-wrapper > .carousel {
  display: flex;
  transform: translate3d(0, 0, 0);
  transition: transform 0.2s;
}

.carousel-wrapper > .carousel > img {
  width: 500px;
  height: 300px;
}

.list-page-black-space {
  height: 100px;
}
ul {
  display: flex;
  flex-direction: column;
}
.el-main {
  width: 100%;
}
.left-main-wrap {
  display: flex;
}
.left {
  /* position: fixed; */
  /* top: 0px;
  left: 0px; */
  width: 250px;
  height: 900px;
  /* background: rgba(255, 98,124,0.6); */
  /* padding: 40px 0; */
  /* overflow: hidden; */
  text-align: center;
  border-right: solid 1px black;
  padding: 30px;
}
.card-container-ul {
  flex-direction: row;
  list-style: none;
}
.left ul {padding: 0 30px}
  .left ul li { font-size:25px;  height:75px; list-style: none;}
  .left ul li#logo {font-family: 'Cafe24', cursive; font-size:50px; height: 130px;}
  .left ul li#logo div {line-height: 0.8}

.main-container {
  display: flex;
  flex-direction: row;
  text-align: center;
  padding: 10px;
  max-width: 100%;
  flex-wrap: wrap;
}
.room-hot-wrap {
  display: flex;
}
.room-card {
  max-width: 100%;
  height: 280px;
  background-color: beige;
}
.room-info {
  width: 300px;
  height: 200px;
}
.room-ul {
  display: flex;
  /* flex-direction: row; */
  overflow: visible;
  list-style: none;
  width: 100%
  00px;
  background-color: rgb(197, 197, 255);
}
.list {
  max-width: inherit;
}
.example-showcase .el-dropdown + .el-dropdown {
  margin-left: 15px;
}
.example-showcase .el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}
</style>
<script >
import Search from '@/views/common/search'
import Conference from './components/conference'
import 'vue3-carousel/dist/carousel.css';
import { mapState , mapMutations} from "vuex";

export default {
  name: 'Home',

  components: {
    Conference,
    Search,
  },
  data () {
    return {
      phase : {0:"모집중", 1:"진행중", 3:"투표중", 4:"종료"},
      menus : '',
      checkbox : 'false',
      data : '',
      c_index : 0
    }
  },
  computed : {
    ...mapState(["roomList"])
  },
  mount() {

  },
  created() {
    this.$store.dispatch("getRoomInfo");
    const menuData = require('@/views/main/menu.json')
    this.menus = menuData;
  },
   methods: {
    ...mapMutations(["GET_ROOM_LIST"]),
    goCate(index)  {
      this.checkbox = false;
      this.$store.dispatch("getRoomInfoCate", index);
    },
    sortRecent () {
      const orderedDate = this.$store.state.roomList.sort((a, b) => new Date(b.start_time) - new Date(a.start_time))
      this.GET_ROOM_LIST(JSON.stringify(orderedDate));
      this.data = orderedDate;
      this.$router.replace;
    },
    sortCur () {
      const sortdata = this.$store.state.roomList.sort((a,b) => b.cur_num - a.cur_num);
      this.GET_ROOM_LIST(JSON.stringify(sortdata));
      this.data= sortdata;
      this.$router.replace;
    },
    sortOld () {
      const orderedDate = this.$store.state.roomList.sort((a, b) => new Date(a.start_time) - new Date(b.start_time))
      this.GET_ROOM_LIST(JSON.stringify(orderedDate));
      this.data = orderedDate;
      this.$router.replace;
    },
    sortRank () {

    },
    viewJoin () {
      const sortdata = this.$store.state.roomList;
      const data = [];
      if (this.checkbox) {
        this.data = sortdata;
      for (let i=0 ; i < sortdata.length; i++) {
          if (sortdata[i].phase == 0 ) {
            data.push(JSON.parse(JSON.stringify(sortdata[i])));
          }
        }

    this.GET_ROOM_LIST(JSON.stringify(data));
    this.$router.replace;
    } else {
      this.data = JSON.stringify(this.data)
      this.GET_ROOM_LIST(this.data);
      this.$router.replace;
    }
    },

    prev() {
      const carousel = document.querySelector('.carousel');
      if (this.c_index === 0) return;
      this.c_index -= 1;

      carousel.style.transform = `translate3d(-${500 * this.c_index}px, 0, 0)`;
    },
    next() {
        const carousel = document.querySelector('.carousel');
        if (this.c_index === 2) return;
        this.c_index += 1;
        carousel.style.transform = `translate3d(-${500 * this.c_index}px, 0, 0)`;
    }
  }
  // setup () {
  //   const router = useRouter()

  //   const state = reactive({
  //     count: 5
  //   })

  //   const load = function () {
  //     state.count += 4
  //   }

  //   const clickConference = function (id) {
  //     router.push({
  //       name: 'conference-detail',
  //       params: {
  //         conferenceId: id
  //       }
  //     })
  //   }

  //   return { state, load, clickConference }
  // },

}
</script>
