<template>
    <div class="debate-room-component-content-side">
        <!-- <div>여기에 패널 화면 넣으면 됨</div> -->
        <div class="blank-space-video" :style="customViewStyle"></div>
        <div v-for="(item, index) in room.disagree" :key="index" class="panel-video" :style="customViewStyle">
          <div class="panel-video-inner">
            <user-video class="moderatorVideo" :stream-manager="room.disagrees[index]"/>
          </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import UserVideo from '@/views/openvidu/UserVideo.vue';
import { mapState} from 'vuex';


// const OPENVIDU_SERVER_URL = process.env.OPENVIDU_SERVER_URL;
// const OPENVIDU_SERVER_SECRET = process.env.OPENVIDU_SERVER_SECRET;

export default {
    name : 'debateRoomSideComponent',
    componets : {
      UserVideo
    },
    computed : {
       ...mapState(["user"]),
        customViewStyle() {
            return {
                "--side-video-width" : this.sideVideoWidth,
                "--side-video-height" : this.sideVideoHeight,
                "--side-video-height-inner" : this.sideVideoHeightInner,
                "--blank-space-video" : this.blankSpaceVideo,
            }
        }
    },
    data() {
        return {
            position : '',
            role : '',
            sideVideoWidth: '',
            sideVideoHeight: '',
            sideVideoHeightInner: '',
            blankSpaceVideo: '',


        }
    },
    props : ['room'],

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

    //   async joinPannel () {
    //    var data = {
    //     pwd : '',
    //     sessionId : this.room.session.sessionId
    //    }

    //   //await this.$store.dispatch("enterRoom",data)
    //    var token = this.$store.state.tempToken;

    // //await this.room.session.disconnect();

    // await this.room.session
    //   .connect(token, { clientData: this.user.em + '/' + this.position})
    //   .then(() => {
    //     console.log("Connected!!!");
    //   })
    //   .catch(error => {
    //     console.log(
    //       "There was an error connecting to the session:",
    //       error.code,
    //       error.message
    //     );
    //   });
    //   console.log("you are pannel");
    //   let publisher = this.room.OV.initPublisher(undefined, {
    //     audioSource: undefined, // The source of audio. If undefined default microphone
    //     videoSource: undefined, // The source of video. If undefined default webcam
    //     publishAudio: false, // Whether you want to start publishing with your audio unmuted or not
    //     publishVideo: true, // Whether you want to start publishing with your video enabled or not
    //     resolution: "680x480", // The resolution of your video
    //     frameRate: 30, // The frame rate of your video
    //     insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
    //     mirror: true, // Whether to mirror your local video or not
    //   });

    //   this.room.publisher = publisher;
    //   this.room.agrees = publisher;
    //   // --- Publish your stream ---
    //   this.room.session.publish(publisher);


    // },

}
}
</script>

<style>

.debate-room-component-content-side {
    color: brown;
}
.blank-space-video {
    height: var(--blank-space-video);
    /* background-color: blue; */
}
.panel-video {
    width: var(--side-video-width);
    height: var(--side-video-height-inner);
    /* background-color: rgb(100, 232, 255); */
    display: flex;
    align-items: center;
}
.panel-video-inner {
    width: var(--side-video-width);
    height: var(--side-video-height);
    background-color: white;
    border-radius: 10px;
}
</style>

