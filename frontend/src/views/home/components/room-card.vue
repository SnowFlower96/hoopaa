<template>
  <div class="room-info-carosel" :style="{backgroundImage: 'url('+backurl+')'}">
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
      ppSet: true
    };
  },
  created() {
     if(this.room.thumbUrl==null){
      this.backurl="https://img.animalplanet.co.kr/news/2019/06/28/700/50l8l41c2s798dtceu0m.jpg";
    }else{
      this.backurl="data:image/png;base64,"+this.room.thumbUrl;
    }

    // console.log(this.backurl);
  },
  updated() {
     if(this.room.thumbUrl==null){
      this.backurl="https://img.animalplanet.co.kr/news/2019/06/28/700/50l8l41c2s798dtceu0m.jpg";
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
      console.log(this.backurl);
    window.addEventListener("resize", this.handleResizeHome);
  }
};
</script>

<style>
.room-info-carosel:hover {
  -webkit-transform: scale(1.2);
  filter: brightness(100%);
}

.room-info-carosel {
  height: 100%;
  background-size:100% 100%;
  width: 100%;
  filter: brightness(80%);
  border-radius: 10px;
}
</style>
