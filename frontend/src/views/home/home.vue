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

            <!--  -->
            <!-- <div>
              <div class="carousel-wrapper">
                <div class="carousel">
                  <div class="room-hot-wrap">
                    <img src="https://images.unsplash.com/photo-1583434987437-1b9dcbe44c9e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"/>
                    <img src="data:image/jpeg;base64,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"/>
                    <img src="data:image/jpeg;base64,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"/>
                    <img src="data:image/png;base64,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"/>
                  </div>
                </div>
              </div> -->

            <!-- </div> -->
            <!--  -->

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
            <div class="list">여기가 기본 all</div>
            <div class="carousel-wrapper">
              <ul class="room-ul carousel">
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
          </div>
        </div>
      </div>
    </div>
  <!-- </div> -->
</template>

<style>
/*  */
.carousel-wrapper {
  width: 1200px;
  height: 300px;
  overflow-x: hidden;
  overflow-y: hidden;
  background-color: aqua;
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
/*  */


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
.room-hot-wrap {
  display: flex;
}
.room-card {
  max-width: 400px;
  height: 400px;
}
.room-ul {
  display: flex;
  flex-direction: row;
  /* overflow: auto; */
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
      index : 0
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
      this.checkbox = false;
      this.$store.dispatch("getRoomInfoCate", index);
    },
    sortRecent () {
      const orderedDate = this.$store.state.roomList.sort((a, b) => new Date(b.start_time) - new Date(a.start_time))
      this.GET_ROOM_LIST(JSON.stringify(orderedDate));
      this.data = orderedDate;

    },
    sortCur () {
      const sortdata = this.$store.state.roomList.sort((a,b) => b.cur_num - a.cur_num);
      this.GET_ROOM_LIST(JSON.stringify(sortdata));
      this.data= sortdata;

    },
    sortOld () {
      const orderedDate = this.$store.state.roomList.sort((a, b) => new Date(a.start_time) - new Date(b.start_time))
      this.GET_ROOM_LIST(JSON.stringify(orderedDate));
      this.data = orderedDate;

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

    } else {
      this.data = JSON.stringify(this.data)
      this.GET_ROOM_LIST(this.data);
     
    }
    },

    prev() {
      const carousel = document.querySelector('.carousel');
      if (this.index === 0) return;
      this.index -= 1;

      carousel.style.transform = `translate3d(-${500 * this.index}px, 0, 0)`;
    },
    next() {
        const carousel = document.querySelector('.carousel');
        if (this.index === 2) return;
        this.index += 1;
        carousel.style.transform = `translate3d(-${500 * this.index}px, 0, 0)`;
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
