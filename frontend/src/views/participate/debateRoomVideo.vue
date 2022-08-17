<template>
  <div class="debate-moderator" :style="customViewStyle">
    <div class="debate-moderator-inner" :style="customViewStyle">
      <user-video class="moderatorVideo" :stream-manager="stream" />
    </div>
  </div>
</template>

<script>
import axios from "axios";
import UserVideo from "@/views/openvidu/UserVideo.vue";
axios.defaults.headers.post["Content-Type"] = "application/json";

export default {
  props: ["stream"],
  components: {
    UserVideo
  },
  computed: {
    customViewStyle() {
      return {
        "--center-video-height": this.centerVideoHeight,
        "--center-video-width": this.centerVideoWidth,

        "--db-mod": this.dbModerator,
        "--db-gg": this.dbGuague,
        "--db-ct": this.dbContent,

        "--db-gg-in-w": this.dbGuagueInnerWidth,
        "--db-ct-in-w": this.dbContentInnerWidth,
        "--db-gg-in-h": this.dbGuagueInnerHeight,
        "--db-ct-in-h": this.dbContentInnerHeight
      };
    }
  },
  data() {
    return {
      centerVideoHeight: "",
      centerVideoWidth: "",

      dbModerator: "",
      dbGuague: "",
      dbContent: "",

      dbGuagueInnerWidth: "",
      dbContentInnerWidth: "",
      dbGuagueInnerHeight: "",
      dbContentInnerHeight: "",
      resol_w: 300,
      resol_h: 200
    };
  },
  mounted() {
  
    const wValue = document.body.clientWidth * 0.75 * 0.3 - 20; // 사회자 비디오
    this.centerVideoWidth = `${wValue}px`;
    this.centerVideoHeight = `${wValue * 0.6}px`;


    this.dbModerator = `${wValue * 0.6}px`;

    window.addEventListener("resize", this.handleResizeHome);
  },
  methods: {
    handleResizeHome() {
      const wValue = document.body.clientWidth * 0.75 * 0.3 - 20; // 사회자 비디오
      this.centerVideoWidth = `${wValue}px`;
      this.centerVideoHeight = `${wValue * 0.6}px`;


      this.dbModerator = `${wValue * 0.6}px`;
    }
  }
};
</script>

<style>
video {
  height: var(--center-video-height);
  width: var(--center-video-width);
}
.debate-moderator {
  height: var(--db-mod);
  display: flex;
  justify-content: center;
  align-items: center;
  /* outline: aqua solid 3px; */
}
.debate-moderator-inner {
  background-color: rgb(56, 56, 56);
  height: var(--center-video-height);
  width: var(--center-video-width);
  border-radius: 10px;
}
.moderatorVideo {
  height: 300px;
  width: 300px;
}
</style>
