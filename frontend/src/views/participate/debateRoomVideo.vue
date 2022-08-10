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
    console.log(this.timeList);
    const wValue = document.body.clientWidth * 0.75 * 0.3 - 20; // 사회자 비디오
    const wValueNotVid = document.body.clientWidth * 0.75 * 0.4; // 게이지 + 컨텐츠
    this.centerVideoWidth = `${wValue}px`;
    this.centerVideoHeight = `${wValue * 0.6}px`;

    const hValue = document.body.clientHeight * 0.8 - wValue * 0.6;

    this.dbModerator = `${wValue * 0.6}px`;
    this.dbGuague = `${hValue * 0.15}px`;
    this.dbContent = `${hValue * 0.85}px`;

    this.dbGuagueInnerWidth = `${wValueNotVid - 30}px`;
    this.dbContentInnerWidth = `${wValueNotVid - 50}px`;
    this.dbGuagueInnerHeight = `${hValue * 0.15 - 30}px`;
    this.dbContentInnerHeight = `${hValue * 0.85 - 50}px`;
    window.addEventListener("resize", this.handleResizeHome);
  },
  methods: {
    handleResizeHome() {
      const wValue = document.body.clientWidth * 0.75 * 0.3 - 20; // 사회자 비디오
      const wValueNotVid = document.body.clientWidth * 0.75 * 0.4; // 게이지 + 컨텐츠
      this.centerVideoWidth = `${wValue}px`;
      this.centerVideoHeight = `${wValue * 0.6}px`;

      const hValue = document.body.clientHeight * 0.8 - wValue * 0.6;

      this.dbModerator = `${wValue * 0.6}px`;
      this.dbGuague = `${hValue * 0.15}px`;
      this.dbContent = `${hValue * 0.85}px`;

      this.dbGuagueInnerWidth = `${wValueNotVid - 30}px`;
      this.dbContentInnerWidth = `${wValueNotVid - 50}px`;
      this.dbGuagueInnerHeight = `${hValue * 0.15 - 30}px`;
      this.dbContentInnerHeight = `${hValue * 0.85 - 50}px`;
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
  background-color: white;
  height: var(--center-video-height);
  width: var(--center-video-width);
  border-radius: 10px;
}
.moderatorVideo {
  height: 300px;
  width: 300px;
}
</style>
