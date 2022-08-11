<template>
    <div class="detail-box-background" :style="customDetailSessionSize">
        <div class="detail-box-outer">
            <div class="detail-box-file">
                화면공유부분 => 컴포넌트를 분리할까 생각중
            </div>
            <div class="detail-box-video">
                <detail-session-side-component :publisher="publisher" :subscribers="subscribers"></detail-session-side-component>
            </div>
        </div>
        <div v-if="chattTF" class="chatting-box" :style="customDetailSessionSize">
            <chatting-team @click="changeChatView"></chatting-team>
        </div>
    </div>
    <div class="debate-room-footer-class-detail">
        <footer-team @call-modal="changeChatView"></footer-team>
        <div class="chatt-btn" @click="changeChatView"><i class="fas fa-comment-alt"></i></div>
    </div>

</template>
<script>
import FooterTeam from "./debateRoomFooter/FooterTeam"
import detailSessionSideComponent from './detailSessionSideComponent'
import chattingTeam from './ChattingComponents/chatting-team.vue'
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import UserVideo from '@/views/openvidu/UserVideo.vue';
import { mapState} from 'vuex';
axios.defaults.headers.post['Content-Type'] = 'application/json';

const OPENVIDU_SERVER_URL = process.env.OPENVIDU_SERVER_URL;
const OPENVIDU_SERVER_SECRET = process.env.OPENVIDU_SERVER_SECRET;
export default {
    components: {
        FooterTeam,
        detailSessionSideComponent,
        chattingTeam,
        UserVideo,
    },
    computed: {
        customDetailSessionSize() {
            return {
                '--detail-box-background' : this.detailBoxBackground,
                "--chatt-box": this.chattBox,
                "--view-share": this.viewShare,
                "--video-box": this.videoBox,
            }
        }
    },
    data() {
        return {
            detailBoxBackground: '',
            chattTF: true,
            chattBox : '',
            viewShare : '',
            videoBox : '',
            OV: undefined,
            session: undefined,
            publisher : undefined,
            subscribers : [],
        }
    },
    mounted() {
        const ViewWidth = document.body.clientWidth
        const ViewHeight = document.body.clientHeight
        const exceptChatting = ViewWidth*0.75

        this.detailBoxBackground = `${ViewWidth}px`
        this.chattBox =  `${ViewWidth*0.25}px`
        this.viewShare = `${exceptChatting*0.7}px`
        this.videoBox = `${exceptChatting*0.3}px`
        window.addEventListener('resize', this.handleResizeSession);
    },
    methods: {
        handleResizeSession() {
            if(this.chattTF === true) {
                const ViewWidth = document.body.clientWidth
                const ViewHeight = document.body.clientHeight
                const exceptChatting = ViewWidth*0.75

                this.detailBoxBackground = `${ViewWidth}px`
                this.chattBox =  `${ViewWidth*0.25}px`
                this.viewShare = `${exceptChatting*0.7}px`
                this.videoBox = `${exceptChatting*0.3}px`
            }
            else {
                const ViewWidth = document.body.clientWidth
                const ViewHeight = document.body.clientHeight
                const exceptChatting = ViewWidth

                this.detailBoxBackground = `${ViewWidth}px`
                this.chattBox =  `${ViewWidth*0.25}px`
                this.viewShare = `${exceptChatting*0.8}px`
                this.videoBox = `${exceptChatting*0.2}px`
            }
        },
        changeChatView() {
            this.chattTF = !this.chattTF
            if(this.chattTF === true) {
                const ViewWidth = document.body.clientWidth
                const ViewHeight = document.body.clientHeight
                const exceptChatting = ViewWidth*0.75

                this.detailBoxBackground = `${ViewWidth}px`
                this.chattBox =  `${ViewWidth*0.25}px`
                this.viewShare = `${exceptChatting*0.7}px`
                this.videoBox = `${exceptChatting*0.3}px`
            }
            else {
                const ViewWidth = document.body.clientWidth
                const ViewHeight = document.body.clientHeight
                const exceptChatting = ViewWidth

                this.detailBoxBackground = `${ViewWidth}px`
                this.chattBox =  `${ViewWidth*0.25}px`
                this.viewShare = `${exceptChatting*0.7}px`
                this.videoBox = `${exceptChatting*0.3}px`
            }

        },
        EmitcallModal(option) {
            this.chattTF = !this.chattTF
        },
        async joinSession () {
    const token = this.tempToken;

    // --- Get an OpenVidu object ---
    this.OV = new OpenVidu();

    // --- Init a session ---
    this.session = this.OV.initSession();
    // --- Specify the actions when events take place in the session ---

    // On every new Stream received...
    this.session.on("streamCreated", ({ stream }) => {
      const subscriber = this.session.subscribe(stream);
      this.subscribers.push(subscriber);
    });

    // On every Stream destroyed...
    // TODO
    this.session.on("streamDestroyed", ({ stream }) => {
      const index = this.subscribers.indexOf(stream.streamManager, 0);
      if (index >= 0) {
        this.subscribers.splice(index, 1);
      }
    });

    // On every asynchronous exception...
    this.session.on("exception", ({ exception }) => {
      console.warn(exception);
    });

    await this.session
      .connect(token, { clientData: this.user.id })
      .then(() => {
        let publisher = this.OV.initPublisher(undefined, {
        audioSource: undefined, // The source of audio. If undefined default microphone
        videoSource: undefined, // The source of video. If undefined default webcam
        publishAudio: false, // Whether you want to start publishing with your audio unmuted or not
        publishVideo: true, // Whether you want to start publishing with your video enabled or not
        resolution: "680x480", // The resolution of your video
        frameRate: 30, // The frame rate of your video
        insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
        mirror: true, // Whether to mirror your local video or not
      });
        this.publisher = publisher;
        console.log("Connected!!!");
        this.session.publish(publisher);
      })
      .catch(error => {
        console.log(
          "There was an error connecting to the session:",
          error.code,
          error.message
        );
      });
    },
    },

}
</script>


<style>
    .chatting-box {
        background-color: whitesmoke;
        width: var(--chatt-box);
        height: 93vh;
        border-radius: 10px;
    }
    .debate-room-footer-class-detail {
        width: var(--footer-width);
        height: 7vh;
        display: flex;
        justify-content: center;
        align-items: center;
        background-color: black;
    }
    .detail-box-background {
        background-color: black;
        /* background-color: blue; */
        height: 93vh;
        width: var(--detail-box-background);
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .detail-box-outer {
        display: flex;
    }
    .detail-box-file {
        /* outline: 2px solid yellow; */
        width: var(--view-share);
        color: aliceblue;
        height: 93vh;
    }
    .detail-box-video {
        /* outline: 2px solid yellow; */
        width: var(--video-box);
        color: aliceblue;
        height: 93vh;
    }
</style>
