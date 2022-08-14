<template>
  <div class="mypage-top">
    <div class="mypage-blank-space"></div>
    <div>
      {{userStat.nnm}}님의 마이페이지
    <router-link to="/checkPwd?loc=info"><el-button>회원정보 수정</el-button></router-link>
    <!-- <router-link to="/checkPwd"><el-button>회원정보 수정</el-button></router-link> -->
    </div>
    <el-card>
      <h1>토론 기록 상세 보기 durldi?</h1>
      <ul>
        <li v-for="(item, index) in userHistory" :key="index">
          <el-button @click="toggleOn"><span>2022.07.14 13:21 사회 주민등록번호제도는 유지되어야 하는가 </span><span id="que-1-toggle">더보기</span></el-button>
          <el-button @click="toggleOn(index)"><span>{{item.start_time}} {{item.title}} {{item._win ? "승리" : "패배"}}</span><span id="que-1-toggle"> 더보기</span></el-button>
          <div v-if="show[index]">내 포지션 : {{item.position}} <br> 결과 : 총 {{item.max_num}}명중 찬성 {{item.agree}}명 반대 {{item.disagree}}명 무효 {{item.invalid}}명</div>
        </li>
      </ul>
    </el-card>
    <dialog></dialog>
  </div>
</template>

<script>
import { mapState } from 'vuex';

export default {
 data () {
  return {
    show : [],

  }
 },
created () {
  this.$store.dispatch("getUserHistory")
  this.$store.dispatch("getUserStat")
},
 computed : {
    ...mapState(["userHistory", "userStat"])
  },
 methods : {
  toggleOn(index) {
    this.show[index] = !this.show[index];
  },
 },
 mounted() {
  for (let i = 0; i < this.userHistory.length; i++) {
    this.show.push(false);
  }
 }

}

</script>

<style scoped>

.answer {
    display: none;
    padding-bottom: 30px;
  }
.mypage-blank-space {
      height: 100px;
  }
</style>
