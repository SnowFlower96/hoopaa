<template>

    <div class="room-info" :style="{backgroundImage: 'url('+backurl+')'}">
     <p class="room-phase-tip"  :style="{'background-color' : backcolor}" >{{phase[room.phase]}}</p>
     <!-- <p class="room-desc">
      <span class="room-phase">{{phase[room.phase]}}</span><span>     </span><span class="room-cate">{{this.menus[room.cate].name}}</span></p> -->

      <div class="room-title">
        <p class="para-hashtag">
          <span v-if="room.hash1!=null" class="hash1">#{{room.hash1}}</span>
          <span v-if="room.hash2!=null" class="hash2">#{{room.hash2}}</span>
          <span v-if="room.hash3!=null" class="hash3">#{{room.hash3}}</span>
        </p>
        <p class="para-title">{{room.title}}</p>
      </div>
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
      recuruitStyle:'',
      backcolor:'',
    };
  },
  created() {
       if(this.room.phase==0){
         this.backcolor = "rgb(167, 234, 255)"

    }else if(this.room.phase==1){

          this.backcolor = "rgb(109, 229, 45)"


    }else if(this.room.phase==2){
        this.backcolor = "rgb(109, 229, 45)"
    }else{
        this.backcolor = "rgb(109, 229, 45)"
    }

     if(this.room.thumbUrl==null){
      this.backurl="https://user-images.githubusercontent.com/87743473/184837786-3b9f1e12-9837-4f8f-be4c-ac12ceceddec.png";
    }else{
      this.backurl="data:image/png;base64,"+this.room.thumbUrl;
    }
    const menuData = require('@/views/main/menu.json')
    this.menus = menuData;

  },
  updated() {
      if(this.room.phase==0){
         this.backcolor = "rgb(167, 234, 255)"

    }else if(this.room.phase==1){

          this.backcolor = "rgb(109, 229, 45)"


    }else if(this.room.phase==2){
        this.backcolor = "rgb(247, 168, 59)"
    }else{
        this.backcolor = "rgb(235, 22, 22)"
    }
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
  -webkit-transform: scale(1.1);
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
  margin-top:0;
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
.room-phase-tip {
  width: 45px;
  text-align: end;
  font-size: 15px;
  padding: 3px;
  z-index: 3;
  border-radius: 3px;
  margin-bottom: 0;
  /* margin-left: var(--room-phase-tip-margin-left); */
}
.para-title{
  margin-top:0;
}
.para-hashtag{
  margin-bottom:0;
  color:rgb(27, 25, 29);
  font-size: smaller;
}
.hash1{
  background-color: rgb(167, 234, 255);
  border-radius: 4px;
  margin-right: 2%;
}

.hash2{
  background-color: rgb(212, 114, 235);
  border-radius: 4px;
  margin-right: 2%;
}
.hash3{
  background-color:rgb(234, 242, 129);
  border-radius: 4px;
}

</style>
