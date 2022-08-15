<template>
  <div class="room-info-carosel" :style="customCaroselStyle">
    <img class="img-thumbnail" :src="getPic(room.thumbUrl)" />

    <h1>{{ room.subtitle }}</h1>
    <!-- <p class="room-phase-tip">{{phase[room.phase]}}</p>
      <p id="title-carosel">{{room.subtitle}}</p> -->

    <!-- <img :src ="require(this.backurl)"> -->
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
      backurl: null,
      // litt: '../../../assets/thumbnail/14dcc40e-285b-48a2-a104-1c2c7e90fa24_20220814211735.jpg',
      ppSet: true
    };
  },
  created() {
    // this.backurl=require("../../../assets/thumbnail/"+room.thumbUrl)
    console.log(this.room.thumbUrl);
    // this.backurl ="../../../assets/thumbnail/"+ this.room.thumbUrl;
    console.log(this.backurl);
    // console.log(this.backurl);
  },
  computed: {
    customCaroselStyle() {
      return {
        "--carosel-item-width": this.caroselWidth,
        "--carosel-item-height": this.caroselHeight,
        "--carosel-item-back": this.backurl
        // "--room-phase-tip-margin-left": `${this.caroselWidth}-200px`
      };
    }
  },
  watch: {
    $route(to, from) {
      console.log(to);
    }
  },
  methods: {
    // getPic(name) {
    //   return require("../../../assets/thumbnail/" + name);
    // },
    // geturl(name) {
    //   return `url(../../../assets/thumbnail/` + name + `)`;
    // }
  },
  mounted() {
    console.log(this.room.thumbUrl);
    // var str = "../../assets/thumbnail/" + this.room.thumbUrl;
    // this.backurl=require("../../assets/thumbnail/"+this.room.thumbUrl)
    const value = document.body.clientWidth * 0.8 * 0.25;
    this.caroselWidth = `${value - 20}px`; // margin buffer 10px 고려한 계산
    (this.caroselHeight = `${(value - 20) * 0.62}px`),
      console.log(this.backurl);
    window.addEventListener("resize", this.handleResizeHome);
  }
};
</script>

<style>
.img-thumbnail {
  /* width:100%; */
  height: 100%;
  object-fit: cover;
}

.room-info-carosel:hover {
  -webkit-transform: scale(1.4);
}
</style>
