<template>
    <div class="debate-room-component-content-side-a">
        <!-- <div>여기에 패널 화면 넣으면 됨</div> -->
        <div class="blank-space-video-a" :style="customViewStyle"></div>
        <div v-for="(item, index) in room.agree" :key="index" class="panel-video-a" :style="customViewStyle">
          <div class="panel-video-a-inner-a">
            <user-video class="moderatorVideo" :stream-manager="room.agrees[index]"/>
          </div>
        </div>

    </div>
</template>

<script>
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import UserVideo from '@/views/openvidu/UserVideo.vue';
import { mapState} from 'vuex';


const OPENVIDU_SERVER_URL = process.env.OPENVIDU_SERVER_URL;
const OPENVIDU_SERVER_SECRET = process.env.OPENVIDU_SERVER_SECRET;

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
                "--blank-space-video-a" : this.blankSpaceVideo,
            }
        }
    },
    data() {
        return {
            token : '',
            OV: undefined,
			      session: undefined,
			      mainStreamManager: undefined,
			      publisher: undefined,
			      subscribers: [],
            position : '',
            role : '',
            sideVideoWidth: '',
            sideVideoHeight: '',
            sideVideoHeightInner: '',
            blankSpaceVideo: ''
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
    created () {
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

      joinPannel () {
        if (this.position && this.user.em != this.session.sessionId) {
				this.session.connect(this.token, { clientData: this.$store.state.userStat.em + '/' + this.position })
					.then(() => {
            console.log("connecting...");
						// --- Get your own camera stream with the desired properties ---
            console.log(this.session.sessionId)

              let publisher = this.OV.initPublisher(undefined, {
                audioSource: undefined, // The source of audio. If undefined default microphone
                videoSource: undefined, // The source of video. If undefined default webcam
                publishAudio: true,  	// Whether you want to start publishing with your audio unmuted or not
                publishVideo: true,  	// Whether you want to start publishing with your video enabled or not
                resolution: '680x480',  // The resolution of your video
                frameRate: 30,			// The frame rate of your video
                insertMode: 'APPEND',	// How the video is inserted in the target element 'video-container'
                mirror: false       	// Whether to mirror your local video or not
              });

              this.mainStreamManager = publisher;
              this.publisher = publisher;

              // --- Publish your stream ---

              this.session.publish(this.publisher);

             console.log("connected");
					})
					.catch(error => {
						console.log('There was an error connecting to the session:', error.code, error.message);
					});

      console.log("!!!!"+this.role)
      window.addEventListener('beforeunload', this.leaveSession);
        }
    },
    }
}
</script>

<style>

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
</style>

