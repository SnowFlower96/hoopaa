<template>

    <div class="room-info" :style="{backgroundImage: 'url('+backurl+')'}">
     <p class="room-phase-tip">{{phase[room.phase]}}</p>
     <!-- <p class="room-desc">
      <span class="room-phase">{{phase[room.phase]}}</span><span>     </span><span class="room-cate">{{this.menus[room.cate].name}}</span></p> -->
    <p class="room-title"> {{room.subtitle}}</P>
    </div>

</template>
<script>
export default {
  props: {
    room: []
  },
  data() {
    return {
      phase: { 0: "모집중", 1: "진행중", 3: "투표중", 4: "종료" },
      c_index: 0,
      clickCaroselNext: null,
      caroselWidth: "",
      caroselHeight: "",
      caoselWrapperOverTF: false,
      dropdownSortTF: false,
      homeCheckInModal: false,
      backurl: '',
      ppSet: true,
      menus : '',
    };
  },
  created() {
     if(this.room.thumbUrl==null){
      this.backurl="https://user-images.githubusercontent.com/87743473/184837786-3b9f1e12-9837-4f8f-be4c-ac12ceceddec.png";
    }else{
      this.backurl="data:image/png;base64,"+this.room.thumbUrl;
    }
    const menuData = require('@/views/main/menu.json')
    this.menus = menuData;

  },
  updated() {
     if(this.room.thumbUrl==null){
      this.backurl="https://user-images.githubusercontent.com/87743473/184837786-3b9f1e12-9837-4f8f-be4c-ac12ceceddec.png";
    }else{
      this.backurl="data:image/png;base64,"+this.room.thumbUrl;
    }
  },
  computed: {
    customCaroselStyle() {
      return {
        "--carosel-item-width": this.caroselWidth,
        "--carosel-item-height": this.caroselHeight
      };
    }
  },
  watch: {
    $route(to, from) {
      console.log(to);
    }
  },
  mounted() {


    const value = document.body.clientWidth * 0.8 * 0.25;
    this.caroselWidth = `${value - 20}px`; // margin buffer 10px 고려한 계산
    (this.caroselHeight = `${(value - 20) * 0.62}px`),
    window.addEventListener("resize", this.handleResizeHome);
  }
};
</script>

<style>
.room-info:hover {
  -webkit-transform: scale(1.2);
  filter: brightness(100%);
}
.room-desc{
  text-align: end;
}
.room-phase{
  background-color: rgb(167, 234, 255);
}
.room-cate{
  background-color : rgb(223, 230, 26);
}
.room-title{
  text-align: left;
}
.room-info {
  height: 100%;
  background-size:100% 100%;
  width: 100%;
  filter: brightness(80%);
  border-radius: 10px;
  z-index: 1;
}
.room-info::after {
  content :'';
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  background: linear-gradient(rgba(255, 255, 255,0), rgba(255, 255, 255,0), rgba(0, 0, 0, 0.856));
  z-index: -2;
  border-radius: 10px;
}
.room-title{
  position: relative;
  left: 25px;
  color: white;
  position : fixed;
  bottom:0;
  text-align: left;
  font-size: 20px;
  border-radius: 5px;
  white-space:nowrap;
  overflow:hidden;
  text-overflow:ellipsis;
  width: calc(100% - 40px);
}
</style>
