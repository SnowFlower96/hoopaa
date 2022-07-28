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
              <li v-for="(item, index) in menus" :key="index">{{item.name}}</li>
            </ul>
          </div>

        <div class="main-container">
          <hr/>
          <div class="list">
            <div>여기는 핫 한 영상</div>
            <carousel :items-to-show="1.5">
    <slide v-for="slide in 10" :key="slide">
      {{ roomList.json }}
    </slide>

    <template #addons>
      <navigation />
      <pagination />
    </template>
  </carousel>
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
            <div>여기가 기본 all</div>

          </div>
        </div>
      </div>
    </div>
  <!-- </div> -->
</template>

<style>

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
  flex-direction: column;
  text-align: center;
  padding: 10px;
}

</style>
<script>
import Search from '@/views/common/search'
import Conference from './components/conference'
import 'vue3-carousel/dist/carousel.css';
import { Carousel, Slide, Pagination, Navigation } from 'vue3-carousel';
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { assertDeclareExportDeclaration, removeComments } from '@babel/types';
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
    }
  },
  computed : {
    ...mapState(["roomList", "menus"])
  },
  created() {
    this.$store.dispatch("getRoomInfo");
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
