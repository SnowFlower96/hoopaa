<template>
  <!-- <div> -->
    <div>
      <div class="left">
        <ul>
          <li id="logo">
            <div>카테고리</div>
          </li>
          <li v-for="(item, index) in menus" :key="index">{{item.name}}</li>
        </ul>
      </div>
    <div class="main-container">
      <div>dd</div>
      <div>d</div>
      <div>d</div>
      <div>d</div>
      <div>d</div>
      <div>d</div>
      <search/>

      <hr/>



      <div class="list">
        

        <div>
          <span>카테고리</span>
          <button>정렬조건</button>
          <el-checkbox>모집중만 보기</el-checkbox>
        </div>


      </div>
    </div>
  <!-- </div> -->
  </div>
</template>

<style scoped>
.left {
  position: fixed;
  top: 0px;
  left: 0px;
  width: 250px;
  height: 900px;
  background: rgba(255, 98,124,0.6);
  padding: 40px 0;
  overflow: hidden;
  text-align: center;
}
.left ul {padding: 0 30px}
  .left ul li { font-size:25px;  height:75px; list-style: none;}
  .left ul li#logo {font-family: 'Cafe24', cursive; font-size:50px; height: 130px;}
  .left ul li#logo div {line-height: 0.8}

.main-container {
  display: flex;
  flex-direction: column;
  text-align: center;
}

</style>
<script>
import Search from '@/views/common/search'
import Conference from './components/conference'

import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { mapState } from "vuex";

export default {
  name: 'Home',

  components: {
    Conference,
    Search,
  },
  computed : {
    ...mapState(["roomList", "menus"])
  },
  created() {
    this.$store.dispatch("getRoomInfo");
  },
   methods: {

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
