<template>
  <!-- <div> -->
    <div>
      <div class="home-modal-wrap displayFlex" v-if="homeCheckInModal">
        <div class="home-check-in">
            <div style="display: flex; justify-content: end;" @click="offModal"><i class="fas fa-times"></i></div>
          <div class="home-check-in-inner displayFlex">
            <div style="text-align:center;">
              <div v-if="!isLogin">
                <div v-if="ppSet">
                  <div style="font-size:30px;">참여 설정</div>
                  <div class="displayFlex">
                    <router-link to="/login" style=text-decoration:none;><div class="home-modal-btn displayFlex">로그인</div></router-link>
                    <div class="home-modal-btn displayFlex" @click="changePpSet">비회원</div>
                  </div>
                </div>
                <div class="pp-set" v-if="!ppSet">
                  <div style="font-size:30px;">비회원 참여</div>
                  <div class="displayFlex pp-set-inner">
                    <div>닉네임 입력</div>
                    <input v-model="b_nnm" type="text">
                  </div>
                  <div class="displayFlex">
                    <div class="home-modal-btn displayFlex" @click="b_join">참여</div>
                  </div>
                </div>
              </div>
              <div v-else>
                <div v-if="ppSet">
                  <div style="font-size:30px;">참여 여부</div>
                  <div class="displayFlex">
                    <div class="home-modal-btn displayFlex" @click="roomEnter">참여</div>
                  </div>
                </div>
              </div>

            </div>
          </div>
        </div>
      </div>
      <div class="list-page-black-space"></div>
      <search @searchKeyword="searchKeyword"></search>

      <!--수정 부분-->
      <div class="left-main-wrap">
          <div class="left">
            <div class="cate-title">카테고리</div>
            <ul>
              <li v-for="(item, index) in menus" :key="index" @click="goCate(item.path)" :class="{'cldc-highlight': menusIndex == `${index}`, 'cate-li-div-container' : menusIndex != `${index}`}">
                <div>{{item.name}}</div>
              </li>
            </ul>
          </div> <!--left-->

            <div class="main-container">
              <div class="main-inner-container">
                <h1 v-if="allcheck!='true'" >실시간 인기 토론</h1>
                <div v-show="allcheck!='true'" class="carousel-wrapper">
                  <ul class="carousel-ul">
                    <li  v-for="(room, index) in roomList" :key="index">
                    <div v-if="index<8" class="carosel-room-card" >

                      <div class="room-info-carosel" :style="customCaroselStyle">
                        <!-- <p class="room-phase-tip">{{phase[room.phase]}}</p> -->
                        <room-card :room="roomList[roomList.length - index-1]" @click="gotoRoom(roomList[roomList.length - index-1].hostID)">
                       </room-card>
                      </div>
                    </div>
                    </li>
                  </ul>
                </div>


                <div>
                  <!-- 수정 부분 -->
                  <h1>{{menus[menusIndex].name}} 카테고리</h1> <!-- 수정 부분 -->
                  <div class="cate-items-wrap">
                    <div class="cate-item">
                      <div>
                        <div class="dropdown-sort-wrap">
                          <button class="dropdown-sort-btn" @click="dropdownSortBtnTF">정렬조건</button>
                          <div id="dropdown-sort" v-if="dropdownSortTF">
                            <div @click="sortRecent">최신순</div>
                            <div @click="sortCur">참여자순</div>
                            <div @click="sortOld">오래된순</div>
                            <div @click="sortRank">랭킹순</div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="cate-item">
                      <el-checkbox v-model="checkbox" @change="viewJoin"><span style="font-size : 10px">모집중만 보기</span></el-checkbox>
                    </div>
                  </div>
                </div>


                <!-- <div class="list">여기가 기본 all, 총 방 갯수 : {{roomList.length}}</div> -->
                <div class="main-all-wrapper">
                  <div class="main-all-container">
                    <ul class="main-all-ul">
                      <li  v-for="(room, index) in roomList" :key="index">
                      <div class="carosel-room-card" >
                        <div class="room-info-carosel" :style="customCaroselStyle">
                          <!-- <p class="room-phase-tip">{{phase[room.phase]}}</p> -->

                          <room-card :room="roomList[roomList.length - index-1]" @click="gotoRoom(roomList[roomList.length - index-1].hostID)">
                          </room-card>
                        </div>
                      </div>
                      </li>
                    </ul>
                  </div>
                </div>

              </div> <!-- main-inner-container -->
            </div> <!--main-container-->
      </div> <!--left-main-wrap-->
    </div>
  <!-- </div> -->

  <!--수정 부분-->
</template>

<style>
.pp-set-inner > input {
  margin: 10px;
}
.pp-set-inner {
  margin-top: 10px;
}
.home-modal-btn {
  margin: 20px;
  padding: 10px;
  outline: 1px solid #d4b4ff;
  width:60px;
  border-radius: 10px;
  box-shadow: 1px 1px 1px 1px #9747ff9f;
  background-color: #9747ffa8;
  color: white;
}
.home-modal-btn:hover {
  cursor: pointer;
  background-color: #9747FF;
}
.home-modal-btn:active {
  cursor: pointer;
  box-shadow: 0px 0px 0px 0px #9747ff9f;
}
.fa-times:hover {
  cursor: pointer;
}
.home-check-in-inner {
  width: 100%;
  height: 90%;
}
.home-modal-wrap {
  z-index: 5;
  position: absolute;
  width: 100%;
  height: 100%;
}
.home-check-in{
  padding: 10px;
  width: 350px;
  height: 200px;
  background-color: whitesmoke;
  outline: 1px rgb(153, 153, 153) solid;
  border-radius: 10px;
  box-shadow: 3px 10px 10px 3px  rgba(0, 0, 0, 0.589);
}
.list-page-black-space {
  height: 61px;
}
.left-main-wrap {
  display: flex;
}
/* .room-phase-tip {
  width: 45px;
  text-align: end;
  background-color: rgb(167, 234, 255);
  font-size: 15px;
  padding: 3px;
  z-index: 3;
  border-radius: 3px;
  margin-left: var(--room-phase-tip-margin-left);
} */

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
.cate-title {
  display: flex;
  justify-content: center;
  align-items: center;
  color: #4b669b;
  font-weight: bolder;
  height:75px;
  margin-top: 20px;
  font-size:35px;
}
.left ul li#logo {font-family: 'Cafe24', cursive; font-size:30px; height: 130px;}
.left ul li#logo div {line-height: 0.8}
.cate-li-div-container:hover {
  color: #88a4dbb9;
  cursor: pointer;
}
.cldc-highlight {
  font-weight: bolder;
  color: #5c86da;
  text-shadow: #8a9dc291 1px 0 5px;
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

/* 수정함 */
h1{
  text-align: left;
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
.room-info-carosel {

  height: var(--carosel-item-height);
  background-size:100% 100%;
  width: var(--carosel-item-width);
  /* filter: brightness(80%); */
  border-radius: 10px;
}
.room-info-carosel:hover {
  /* background-image: linear-gradient( rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.3)), url('https://img.animalplanet.co.kr/news/2019/06/28/700/50l8l41c2s798dtceu0m.jpg'); */
  cursor: pointer;
  /* filter: brightness(80%);
  transition: filter .3s; */
}
.room-info-carosel:hover #title-carosel {
  cursor: pointer;
  opacity: 100%;
  color: white;
}
#title-carosel {
  opacity: 0%;
  z-index: 3;
  font-size: 60px;
  text-align: end;
}

/* 메인 뷰 - carousel */


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

/* room-list */
.room-ul {
  display: flex;
  overflow: visible;
  list-style: none;
}
.card-container-ul {
  flex-direction: row;
  list-style: none;
}
.room-card {
  margin: 10px;
  outline: solid rgba(180, 180, 180, 0.505) 1px;
  border-radius: 10px;
}
.room-info {
  width: 285px;
  height: 160px;
  display: block;
  background-image: url('../../assets/images/room.jpg');
  background-size:100% 100%;
  height: var(--carosel-item-height);
  width: var(--carosel-item-width);
}
.room-info:hover {
  cursor: pointer;
  background-image: linear-gradient( rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.3)), url('../../assets/images/room.jpg');
}
.room-info:hover #title-room {
  cursor: pointer;
  opacity: 100%;
  color: white;
}
#title-room {
  opacity: 0%;
  z-index: 3;
  font-size: 60px;
  text-align: end;
}
.card-container-ul, .room-ul {
  padding: 0px;
}
/* 수정함 */
.main-all-container{
  display : flex;
  flex-direction: row;
  flex-wrap: wrap;
   width: 100%;
}
.main-all-ul{
  width:100%;
  flex-wrap: wrap;
  flex-direction: row;
  list-style:none;
  padding: 0px;
}
.main-all-ul > li {
  margin: 10px;
}
/* room-list */

</style>
<script >
import Search from '@/views/common/search'
import Conference from './components/conference'
import RoomCard from './components/room-card'
import 'vue3-carousel/dist/carousel.css';
import { mapState , mapMutations} from "vuex";


export default {
  name: 'Home',

  components: {
    Conference,
     RoomCard,
    Search,
  },
  data () {
    return {
      menus : '',
      menusIndex: 0, // < ===== 이거추가
      checkbox : 'false',
      data : '',
      c_index : 0,
      clickCaroselNext: null,
      caroselWidth: '',
      caroselHeight: '',
      caoselWrapperOverTF: false,
      dropdownSortTF: false,
      homeCheckInModal : false,
      ppSet: true,
      b_nnm : '',
      roomId : '',
      allcheck:true,
      pwd:'',
      keyword:'',
      // roomList : [
      //   {phase: 0, title: 'yes01', subtitle: 'GMO식품과연안전한가?'},
      //   {phase: 1, title: 'yes01', subtitle: '가나다라마바사아자차카타퍼ㅏ하'},
      //   {phase: 1, title: '느엥', subtitle: '1대1하실분 들어오셈'},
      //   {phase: 1, title: '느엥', subtitle: '배고프다'},
      //   {phase: 1, title: '느엥', subtitle: '느dfgdfgdfgdfgdfg엥'},
      //   {phase: 1, title: '느엥', subtitle: '느엥'},
      //   ],
      viewCaroselLenghth : null
    }
  },
  computed : {
    ...mapState(["roomList","isLogin"]),
    customCaroselStyle() {
      return {
        "--carosel-item-width": this.caroselWidth,
        "--carosel-item-height": this.caroselHeight,
        // "--room-phase-tip-margin-left": `${this.caroselWidth}-200px`
      }
    }
  },
  watch: {
    '$route' (to, from) {
    }
  },
  mounted() {
    if (this.roomList.length < 4 || this.roomList.length === 4) {
      this.viewCaroselLenghth = 0
    }
    else {
      this.viewCaroselLenghth = this.roomList.length - 4
    }
    this.clickCaroselNext = setInterval(this.next, 4000)
    const value = document.body.clientWidth*0.8*0.25
    this.caroselWidth = `${value-20}px` // margin buffer 10px 고려한 계산
    this.caroselHeight = `${(value-20)*0.62}px`
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
    changePpSet() {
      this.ppSet = false
    },
    offModal() {
      this.homeCheckInModal = false
      this.ppSet = true
    },
    gotoRoom(hostID) {
      this.roomId = hostID;
      this.homeCheckInModal = true;
    },
    goCate(index)  {
      if(index!='/list/all'){
        this.allcheck ='true'
        this.menusIndex = index.charAt(index.length - 1)  // < ===== 이거추가
      }else{
        this.allcheck='false'
        this.menusIndex = 0  // < ===== 이거추가
      }
      this.checkbox = false;
      this.$store.dispatch("getRoomInfoCate", index);
    },
    dropdownSortBtnTF() {
      this.dropdownSortTF = !this.dropdownSortTF
    },
    caoselWrapperOver() {
      this.caoselWrapperOverTF = true
    },
    caoselWrapperOut() {
      this.caoselWrapperOverTF = false
    },
    handleResizeHome() {
      const value = document.body.clientWidth*0.8*0.25
      this.caroselWidth = `${value-20}px` // margin buffer 10px 고려한 계산
      this.caroselHeight = `${(value-20)*0.62}px`
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
      const carousel = document.querySelector('.carousel-ul');
      if (this.c_index === 0) return;
      this.c_index -= 1;
      const value = document.body.clientWidth*0.8*0.25
      carousel.style.transform = `translate3d(-${(value) * this.c_index}px, 0, 0)`;
    },
    next() {
        const carousel = document.querySelector('.carousel-ul');
        if (this.c_index === this.viewCaroselLenghth) {
          carousel.style.transform = `translate3d(0, 0, 0)`;
          this.c_index = 0
        } else {
          this.c_index += 1;
          const value = document.body.clientWidth*0.8*0.25
          carousel.style.transform = `translate3d(-${(value) * this.c_index}px, 0, 0)`;
        }
    },
    async b_join () {
      let b_data = {
        nnm : this.b_nnm,
        data : {
          pwd : '',
          sessionId : this.roomId,
        }
      }
      await this.$store.commit("SET_POSITION",'audience');

      await this.$store.dispatch("bJoin", b_data);
      //await this.$store.dispatch("enterRoom", data)
    },

    //방 입장
    roomEnter() {
      let data = {
        pwd : this.pwd,
        sessionId : this.roomId,
      }
      console.log(data)
      this.$store.dispatch("enterRoom", data)
   },

    //키워드 검색
    searchKeyword(keyword){
      if(keyword ==''){
        this.$store.dispatch("getRoomInfo");
        this.allcheck ='false'
      }else{
        this.$store.dispatch("getRoomInfokeyword", keyword);
        this.checkbox = false;
        this.allcheck='true';
      }
    }
}
}
</script>
