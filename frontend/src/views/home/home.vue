<template>
  <!-- <div> -->
    <div>
      <div class="list-page-black-space"></div>
      <search></search>
      <div class="left-main-wrap">
        

          <div class="left">
            <ul>
              <div id="logo">카테고리</div>
              <li v-for="(item, index) in menus" :key="index" @click="goCate(item.path)">
                <div class="cate-li-div-container">{{item.name}}</div>
              </li>
            </ul>
          </div> <!--left-->

            <div class="main-container">
              <div class="main-inner-container">
                <h1>실시간 인기 토론</h1>
                <!-- <button v-if="caoselWrapperOverTF" class="prev" type="button" @click="prev">prev</button>
                <button v-if="caoselWrapperOverTF" class="next" type="button" @click="next">next</button> -->
                <!-- <div class="carousel-wrapper" @mouseover="caoselWrapperOver" @mouseout="caoselWrapperOut"> -->
                <div class="carousel-wrapper">
                  <ul class="carousel-ul">
                    <li  v-for="(room, index) in roomList" :key="index">
                    <div class="carosel-room-card">
                      <img class="room-info-carosel" :style="customCaroselStyle" src="https://img.animalplanet.co.kr/news/2019/06/28/700/50l8l41c2s798dtceu0m.jpg"/>
                      <div class="carosel-tips-wrap">
                        <span class="room-title-tip">{{room.title}}</span>
                        <span class="room-phase-tip">{{phase[room.phase]}}</span>
                      </div>
                    </div>
                    </li>
                  </ul>
                </div>


                <div>
                  <h1>전체 카테고리</h1>
                  <div class="cate-items-wrap">
                    <div class="cate-item">
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
                    <div class="cate-item">
                      <el-checkbox v-model="checkbox" @change="viewJoin">모집중만 보기</el-checkbox>
                    </div>
                  </div>
                </div>


                <!-- <div class="list">여기가 기본 all, 총 방 갯수 : {{roomList.length}}</div> -->
                <ul class="room-ul">
                  <li v-for="(indexOut) in (roomList.length/4-1)" :key="indexOut">
                    <ul class="card-container-ul">
                      <li v-for="(indexIn) in 4" :key="indexIn">
                      <div class="room-card">
                        <img class="room-info" :src="require(`@/assets/images/room.jpg`)"/>
                        <div class="carosel-tips-wrap">
                          <span class="room-title-tip">{{roomList[(4 * (indexOut-1)) + indexIn-1].subtitle}}</span>
                          <span class="room-phase-tip">{{phase[roomList[(4 * (indexOut-1)) + indexIn-1].phase]}}</span>
                        </div>
                      </div>
                      </li>
                    </ul>
                  </li>
                </ul>
              </div> <!-- main-inner-container -->
            </div> <!--main-container-->
      </div> <!--left-main-wrap-->
    </div>
  <!-- </div> -->
</template>

<style>

.list-page-black-space {
  height: 61px;
}
.left-main-wrap {
  display: flex;
}
.room-phase-tip {
  background-color: rgba(54, 167, 54, 0.322);
  font-size: 15px;
  padding: 3px;
  z-index: 3;
  border-radius: 3px;
  /* margin-left: var(--room-phase-tip-margin-left); */
}

/* 카테고리 style */
ul {
  display: flex;
  flex-direction: column;
}
.left {
  width: 15%;
  text-align: center;
  border-right: solid 1px rgba(212, 212, 212, 0.505);
}
.left ul {padding: 0px}
.left ul li { font-size:25px;  height:75px; list-style: none;}
.left ul li#logo {font-family: 'Cafe24', cursive; font-size:30px; height: 130px;}
.left ul li#logo div {line-height: 0.8}
.cate-li-div-container:hover {
  background-color: beige;
  cursor: pointer;
}
/* 카테고리 style */

.cate-items-wrap {
  display: flex;
  justify-content: start;
}
.cate-item {
  margin:5px;
}
.main-container {
  display: flex;
  flex-direction: row;
  text-align: center;
  padding: 10px;
  max-width: 100%;
  flex-wrap: wrap;
  width: 80%;
}

.main-inner-container {
  max-width: inherit;
}

/* 메인 뷰 - carousel */
.carosel-tips-wrap {
  display: flex;
  justify-content: space-evenly;
  padding: 10px;
}
.carousel-wrapper > button:hover {
  cursor: pointer;
}
.carousel-wrapper {
  width: 100%;
  overflow-x: hidden;
  overflow-y: hidden;
  /* background-color: aqua; */
}
/* .carousel-wrapper:hover {
  background-color: aqua;
} */
.carousel-ul {
  flex-direction: row;
  list-style: none;
  transition: transform 0.2s;
  padding: 0px;
}
.carousel-ul > li {
  margin: 10px;
}
.room-title-tip {
  background-color: white;
  display: flex;
  justify-content: center;

}
.carosel-room-card {
  /* background-color: beige; */
  width: 100%;
  outline: solid rgba(180, 180, 180, 0.505) 1px;
  border-radius: 10px;
}
.room-info-carosel {
  height: auto;
  width: var(--carosel-item-width);
}
/* 메인 뷰 - carousel */


.el-dropdown {
  margin-left: 15px;
}

/* room-list */
.room-ul {
  display: flex;
  overflow: visible;
  list-style: none;
  /* background-color: rgb(197, 197, 255); */
}
.card-container-ul {
  flex-direction: row;
  list-style: none;
}
.room-card {
  /* background-color: beige; */
  margin: 10px;
  outline: solid rgba(180, 180, 180, 0.505) 1px;
  border-radius: 10px;
}
.room-info {
  max-width: 100%;
  height: auto;
  display: block;
}

.card-container-ul, .room-ul {
  padding: 0px;
}
/* room-list */

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
      c_index : 0,
      clickCaroselNext: null,
      caroselWidth: '',
      caoselWrapperOverTF: false
    }
  },
  computed : {
    ...mapState(["roomList"]),
    customCaroselStyle() {
      return {
        "--carosel-item-width": this.caroselWidth,
        // "--room-phase-tip-margin-left": `${this.caroselWidth}-200px`
      }
    }
  },
  watch: {
    '$route' (to, from) {
      console.log(to)
    }
  },
  mounted() {
    this.clickCaroselNext = setInterval(this.next, 5000)
    const value = document.body.scrollWidth*0.8*0.25
    this.caroselWidth = `${value-20}px` // margin buffer 10px 고려한 계산
    window.addEventListener('resize', this.handleResizeHome);    
  },
  beforeRouteLeave() {
    clearInterval(this.clickCaroselNext)
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
    caoselWrapperOver() {
      this.caoselWrapperOverTF = true
    },
    caoselWrapperOut() {
      this.caoselWrapperOverTF = false
    },
    handleResizeHome() {
      const value = document.body.scrollWidth*0.8*0.25
      this.caroselWidth = `${value-20}px` // margin buffer 10px 고려한 계산
      carouselRoomCard.style.width = `${value}px`;
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
      const carousel = document.querySelector('.carousel-ul');
      if (this.c_index === 0) return;
      this.c_index -= 1;
      const value = document.body.scrollWidth*0.8*0.25
      carousel.style.transform = `translate3d(-${(value) * this.c_index}px, 0, 0)`;
    },
    next() {
        const carousel = document.querySelector('.carousel-ul');
        if (this.c_index === 10) {
          carousel.style.transform = `translate3d(0, 0, 0)`;
          this.c_index = 0
        } else {
          this.c_index += 1;
          const value = document.body.scrollWidth*0.8*0.25
          carousel.style.transform = `translate3d(-${(value) * this.c_index}px, 0, 0)`;
        }
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
