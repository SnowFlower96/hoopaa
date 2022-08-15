<template>
  <div class="mypage-bg">
    <div></div>
    <!-- 두개의 칸으로 나뉘어짐 : 왼쪽) 회원정보 / 오른쪽) 전적 -->
    <div class="info-stat-wrap">

        <!-- 회원정보 부분 -->
          <div class="my-page-info-space" :style="customBoxSize">
            <div class="my-page-inner-wrap justifyEnd">
              <div>
                <div class="my-page-info-space-inner" :style="customBoxSize">
                  <div style="height: 100px;"></div>
                  <div class="mpis-inner-inner">
                    <div>
                      <!-- <p style="font-size:30px;">{{userStat.nnm}}님의</p> -->
                      <p style="font-size:30px;">토롱이님의</p>
                      <p style="font-size:30px;">마이페이지</p>
                      <!-- <router-link to="/checkPwd?loc=info"><el-button>회원정보 수정</el-button></router-link> -->
                      <router-link to="/myPage/info" style="text-decoration:none;"><div class="my-page-btn">회원정보 수정</div></router-link>
                      <!-- <router-link to="/checkPwd"><el-button>회원정보 수정</el-button></router-link> -->
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        <!-- 회원정보 부분 -->

        <!-- 전적 부분 -->
        <div class="displayFlex my-page-stat-background">
          <div>
            <div style="padding-left: 50px;"> 
              <h1>토론 기록 상세 보기</h1>
            </div>
            <div class="my-page-stat-space" :style="customBoxSize">
              <!-- ***** 이부분 주석 풀면 됨 ***** -->
                <!-- <div>
                  <ul>
                    <li v-for="(item, index) in userHistory" :key="index" style="list-style:none;">
                        <div class="my-stat-ele-wrap">
                          <div @click="toggleOn(index)" :class="{'my-stat-ele-closed' : !show[index], 'my-stat-ele-opened' : show[index]}">
                            <div class="my-stat-ele-title">
                              <div style="color:white; font-size:20px; width: 350px;">{{item.start_time}}</div>
                              <div class="title">{{item.title}}</div>
                            </div>
                            <i style="font-size:50px;" class="fas fa-angle-down" v-if="!show[index]"></i>
                            <i style="font-size:50px;" class="fas fa-angle-up" v-if="show[index]"></i>
                          </div>
                          <div v-if="show[index]" class="my-stat-ele-content">
                            <div style="font-size:25px; color: black;">{{item.title}}</div>
                            <p>{{item._win ? "승리" : "패배"}}</p>
                            <p>결과 : 총 {{item.max_num}}명중 찬성 {{item.agree}}명 반대 {{item.disagree}}명 무효 {{item.invalid}}명</p>
                          </div>
                        </div>
                    </li>
                  </ul>
                </div> -->
              <!-- ***** 이부분 주석 풀면 됨 ***** -->

              <!-- 이부분은 예시임 : 주석처리하면 됨-->
                  <div>
                    <ul>
                      <li style="list-style:none;">
                          <div class="my-stat-ele-wrap">
                            <div :class="{'my-stat-ele-closed' : !show[0], 'my-stat-ele-opened' : show[0]}" @click="toggleOn(0)">
                              <div class="my-stat-ele-title">
                                <div style="color:white; font-size:20px; width: 200px;">2022.07.14 13:21</div>
                                <div class="title"> 주민등록번호제도는 유지되어야 하는가 </div>
                              </div>
                              <i style="font-size:50px;" class="fas fa-angle-down" v-if="!show[0]"></i>
                              <i style="font-size:50px;" class="fas fa-angle-up" v-if="show[0]"></i>
                            </div>
                            <div v-if="show[0]" class="my-stat-ele-content">
                              <p style="font-size:25px; color: black;">주민등록번호제도는 유지되어야 하는가</p>
                              <p>승리 패배</p>
                              <p>40병중 30명 찬성 10명 반대</p>
                              <p>내 포지션 찬성</p>
                            </div>
                          </div>
                      </li>
                      <li style="list-style:none;">
                          <div class="my-stat-ele-wrap">
                            <div :class="{'my-stat-ele-closed' : !show[1], 'my-stat-ele-opened' : show[1]}" @click="toggleOn(1)">
                              <div class="my-stat-ele-title">
                                <div style="color:white; font-size:20px; width: 200px;">2022.07.14 13:21</div>
                                <div class="title">GMO 식품 안전한가</div>
                              </div>
                              <i style="font-size:50px;" class="fas fa-angle-down" v-if="!show[1]"></i>
                              <i style="font-size:50px;" class="fas fa-angle-up" v-if="show[1]"></i>
                            </div>
                            <div v-if="show[1]" class="my-stat-ele-content">
                              <p style="font-size:25px; color: black;">GMO 식품 안전한가</p>
                              <p>승리 패배</p>
                              <p>40병중 30명 찬성 10명 반대</p>
                              <p>내 포지션 찬성</p>
                            </div>
                          </div>
                      </li>
                      <li style="list-style:none;">
                          <div class="my-stat-ele-wrap">
                            <div :class="{'my-stat-ele-closed' : !show[2], 'my-stat-ele-opened' : show[2]}" @click="toggleOn(2)">
                              <div class="my-stat-ele-title">
                                <div style="color:white; font-size:20px; width: 200px;">2022.07.14 13:21</div>
                                <div class="title"> 사회 주민등록번호제도는 유지되어야 하는록번호제도는 유지 jf;alksdj;falksjd;fklj </div>
                              </div>
                              <i style="font-size:50px;" class="fas fa-angle-down" v-if="!show[2]"></i>
                              <i style="font-size:50px;" class="fas fa-angle-up" v-if="show[2]"></i>
                            </div>
                            <div v-if="show[2]" class="my-stat-ele-content">
                              <p style="font-size:25px; color: black;">사회 주민등록번호제도는 유지되어야 하는록번호제도는 유지 jf;alksdj;falksjd;fklj </p>
                              <p>승리 패배</p>
                              <p>40병중 30명 찬성 10명 반대</p>
                              <p>내 포지션 찬성</p>
                            </div>
                          </div>
                      </li>
                    </ul>
                  </div>
              <!-- 이부분은 예시임 : 주석처리하면 됨-->
            </div>
          </div>
        </div>
        <!-- 전적 부분 -->

    </div>
    <!-- 두개의 칸으로 나뉘어짐 : 왼쪽) 회원정보 / 오른쪽) 전적 -->
  </div>
</template>

<script>
import { mapState } from 'vuex';

export default {
 data () {
  return {
    show : [],
    infoSpace : '',
    statSpace : '',
    infoSpaceInner: '',
  }
 },
created () {
  this.$store.dispatch("getUserHistory")
  this.$store.dispatch("getUserStat")
},
 computed : {
    ...mapState(["userHistory", "userStat"]),
    customBoxSize() {
      return {
        "--info-space": this.infoSpace,
        "--stat-space": this.statSpace,
        "--info-space-inner" : this.infoSpaceInner
      }
  }
  },
 methods : {
  toggleOn(index) {
    this.show[index] = !this.show[index];
  },
  handleResizeHome() {
    const wValue = document.body.clientWidth
    this.infoSpace = `${wValue*0.4}px`
    this.statSpace = `${wValue*0.6}px`
    this.infoSpaceInner = `${wValue*0.4*0.7}px`
  }
 },
 mounted() {
  for (let i = 0; i < this.userHistory.length; i++) {
    this.show.push(false);
  }

  // 화면 크기 설정 부분 🔽
  const wValue = document.body.clientWidth
  this.infoSpace = `${wValue*0.4}px`
  this.statSpace = `${wValue*0.6}px`
  this.infoSpaceInner = `${wValue*0.4*0.7}px`

  window.addEventListener('resize', this.handleResizeHome);

 }

}

</script>

<style>

.mypage-bg {
  /* background-color: yellow; */
  height: 93vh;
}
.answer {
    display: none;
    padding-bottom: 30px;
  }


.my-page-info-space {
  /* background-color: pink; */
  height: calc(94vh - 8px);
  width: var(--info-space);
  box-shadow: 6px -1px 5px -1px #e0e0e09e;
}
.justifyEnd {
  display: flex;
  justify-content: flex-end;
}

.mpis-inner-inner {
  display: flex;
  justify-content: center;
}
.my-page-info-space-inner {
  width: var(--info-space-inner);
  background-color: whitesmoke;
  border-width: 0px 0px 0px 1px ;
  border-style: solid;
  border-color:rgba(219, 219, 219, 0.63);
  height: calc(94vh - 8px);
}
.my-page-btn {
  width: 130px;
  height: 50px;
  border-radius: 10px;
  outline: solid 1px #9747FF;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #9747FF;
}
.my-page-btn:hover {
  cursor: pointer;
  background-color: #9747FF;
  color: white;
}

.my-stat-ele-wrap {
  outline: #c69bff9d solid 2px;
  width: 80%;
  border-radius: 10px;
  margin-top: 10px;
  margin-bottom: 10px;
  box-shadow: 2px 2px 2px 2px rgb(187, 187, 187);
}
.my-stat-ele-closed{
  width: 100%;
  height: 50px;
  background: linear-gradient(to bottom right, #67bb8e70, #c89eff7c);
  /* background-color: #9747ff9f; */
  border-radius: 10px;
  color: rgb(0, 0, 0);
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  box-shadow: 1px 1px 1px 1px rgb(187, 187, 187);
}
.my-stat-ele-closed:hover {
 background: linear-gradient(to bottom right, #2fad6a, #b276ff);
 cursor: pointer;
}
.my-stat-ele-closed:hover {
  cursor: pointer;
}
.my-stat-ele-opened:hover {
  cursor: pointer;
}
.my-stat-ele-opened{
  width: 100%;
  height: 50px;
  background: linear-gradient(to bottom right, #2fad6a, #b276ff);
  /* background-color: #9747ff9f; */
  /* border-radius: 30px; */
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  color: white;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  
}

.my-stat-ele-title{
  width: 80%;  
  display: flex;
}
.my-stat-ele-title > .title {
  width: 60%; 
  white-space:nowrap;
  overflow:hidden;
  text-overflow:ellipsis;
}
.my-stat-ele-content > p {
  color: rgb(116, 116, 116);
}
.my-stat-ele-content{
  width: calc(100% - 20px);
  height: 100%;
  background-color: whitesmoke;
  /* background-color: #9747ff9f; */
  /* border-radius: 30px; */
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
  color: black;
  padding: 10px;
}
.info-stat-wrap {
  display: flex;
}

.my-page-stat-space {
  /* background-color: rgb(255, 63, 95); */
  height: 60vh;
  width: var(--stat-space);
  overflow: auto;
  /* box-shadow: inset 0px 0px 10px 0px rgb(17, 17, 17); */
  padding-left: 30px;
  padding-right: 30px;
  /* background-color:pink; */

}
.my-page-stat-space::-webkit-scrollbar{width: 10px;}
.my-page-stat-space::-webkit-scrollbar-thumb {
    background-color: rgba(65, 115, 114, 0.664);
    border-radius: 5px;
}
.tes {
  width: 100px;
  height: 100px;
  background-color: bisque;
  
}
</style>
