<template>
    <div class="debate-room-component-content-side-a">
        <!-- <div>여기에 패널 화면 넣으면 됨</div> -->
        <div class="blank-space-video-a" :style="customViewStyle"></div>
        <div v-if="position == 'agree'" class="panel-video-a" :style="customViewStyle">
          <div class="panel-video-a-inner-a">
            <user-video  class="moderatorVideo" :stream-manager="agree"/>
          </div>
        </div>
        <div v-for="item in agreesub" :key="item.stream.connection.connectionId" class="panel-video-a" :style="customViewStyle">
          <div class="panel-video-a-inner-a">
            <user-video class="moderatorVideo" :stream-manager="item"/>
          </div>
        </div>

    </div>
</template>

<script>
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import UserVideo from '@/views/openvidu/UserVideo.vue';
import { mapState} from 'vuex';
axios.defaults.headers.post['Content-Type'] = 'application/json';

const OPENVIDU_SERVER_URL = process.env.OPENVIDU_SERVER_URL;
const OPENVIDU_SERVER_SECRET = process.env.OPENVIDU_SERVER_SECRET;

export default {
    name : 'debateRoomSideComponentAgree',
    componets : {
      UserVideo
    },
    computed : {
       ...mapState(["user","position"]),
        customViewStyle() {
            return {
                "--side-video-width" : this.sideVideoWidth,
                "--side-video-height" : this.sideVideoHeight,
                "--side-video-height-inner" : this.sideVideoHeightInner,
                "--blank-space-video-a" : this.blankSpaceVideo,
            }
        }
    },
    data() {
        return {
            sideVideoWidth: '',
            sideVideoHeight: '',
            sideVideoHeightInner: '',
            blankSpaceVideo: ''
        }
    },
    props : ['agree','agreesub'],
    created () {
      console.log("side")
    },
    mounted() {
        const wValue = document.body.clientWidth*0.75*0.3-20

        const hBlankValue = document.body.clientHeight*0.8*0.2
        const hValue = document.body.clientHeight*0.8*0.8*0.5

        this.sideVideoWidth = `${wValue}px`
        this.sideVideoHeight = `${wValue*0.6}px`
        this.sideVideoHeightInner = `${hValue}px`
        this.blankSpaceVideo = `${hBlankValue}px`

        window.addEventListener('resize', this.handleResizeHome);
    },

    methods: {
        handleResizeHome() {
            const wValue = document.body.clientWidth*0.75*0.3-20

            const hBlankValue = document.body.clientHeight*0.8*0.2
            const hValue = document.body.clientHeight*0.8*0.8*0.5

            this.sideVideoWidth = `${wValue}px`
            this.sideVideoHeight = `${wValue*0.6}px`
            this.sideVideoHeightInner = `${hValue}px`
            this.blankSpaceVideo = `${hBlankValue}px`
        },

    }
}
</script>

<style>
video {
  height: var(--center-video-height);
  width: var(--center-video-width);
}
.debate-room-component-content-side-a {
    color: brown;
}
.blank-space-video-a {
    height: var(--blank-space-video-a);
    /* background-color: blue; */
}
.panel-video-a {
    width: var(--side-video-width);
    height: var(--side-video-height-inner);
    /* background-color: rgb(100, 232, 255); */
    display: flex;
    align-items: center;
}
.panel-video-a-inner-a {
    width: var(--side-video-width);
    height: var(--side-video-height);
    background-color: white;
    border-radius: 10px;
}
.moderatorVideo {
   height: 300px;
   width: 300px;
}
</style>

