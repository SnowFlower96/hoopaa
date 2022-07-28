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
            <div>여기는 핫 한 영상</div>
            <!-- <el-carousel :interval="4000" type="card" height="200px">
    <el-carousel-item v-for="item in 6" :key="item">
      <h3 text="2xl" justify="center">{{ item }}</h3>
    </el-carousel-item>
            </el-carousel> -->
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
              <el-checkbox>모집중만 보기</el-checkbox>
            </div>
            <div class="list">여기가 기본 all</div>
            <ul class="room-ul">
              <li v-for="(room, index) in roomList" :key="index">
              <span>{{phase[room.phase]}}</span>
              <el-card class="room-card"><img :src="require(`@/assets/images/room.jpg`)" alt="" class="room-info"/>
              <span>{{room.title}} <br></span>
              <span>{{room.subtitle}}</span>
              </el-card>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  <!-- </div> -->
</template>

<style scoped>

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
.el-carousel__item h3 {
  color: #475669;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
  text-align: center;
}
.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}

.room-card {
  max-width: 400px;
  height: 400px;
}
.room-ul {
  display: flex;
  flex-direction: row;
  overflow: auto;
  list-style: none;
}
.list {
  max-width: inherit;
}
.room-info {
  width: 300px;
  height: 200px;
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
import { Carousel, Slide, Pagination, Navigation } from 'vue3-carousel';
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { removeComments } from '@babel/types';
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
    }
  },
  computed : {
    ...mapState(["roomList"])
  },
  created() {
    this.$store.dispatch("getRoomInfo");
    const menuData = require('@/views/main/menu.json')
    this.menus = menuData;
  },
   methods: {
    ...mapMutations(["GET_ROOM_LIST"]),
    goCate(index)  {
      console.log(index)
      this.$store.dispatch("getRoomInfoCate", index);
    },
    sortRecent () {
      const orderedDate = this.$store.state.roomList.sort((a, b) => new Date(b.start_time) - new Date(a.start_time))
      this.GET_ROOM_LIST(orderedDate);
      this.$router.replace;
    },
    sortCur () {
      const sortdata = this.$store.state.roomList.sort((a,b) => b.cur_num - a.cur_num);
      this.GET_ROOM_LIST(sortdata);
      this.$router.replace;
    },
    sortOld () {
      const orderedDate = this.$store.state.roomList.sort((a, b) => new Date(a.start_time) - new Date(b.start_time))
      this.GET_ROOM_LIST(orderedDate);
      this.$router.replace;
    },
    sortRank () {

    }
  },
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
