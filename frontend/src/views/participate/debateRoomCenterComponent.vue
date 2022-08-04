<template>
    <div class="debate-center-wrap" >
        <div class="debate-moderator" :style="customViewStyle"><div class="debate-moderator-inner" :style="customViewStyle"><user-video class="moderatorVideo" :stream-manager="publisher"/></div></div>
        <div class="debate-guague" :style="customViewStyle"><div class="debate-guague-inner" :style="customViewStyle">게이지바</div></div>
        <div class="debate-content" :style="customViewStyle"><div class="debate-content-inner" :style="customViewStyle">자료화면</div></div>
    </div>
</template>

<script>
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import UserVideo from '@/views/openvidu/UserVideo.vue';
import { mapState} from 'vuex';
axios.defaults.headers.post['Content-Type'] = 'application/json';

const OPENVIDU_SERVER_URL = "https://3.38.181.187:8443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

export default {
  components : {
    UserVideo,
  },
    computed : {
        customViewStyle() {
            return {
                "--center-video-height" : this.centerVideoHeight,
                "--center-video-width" : this.centerVideoWidth,

                "--db-mod" : this.dbModerator,
                "--db-gg" : this.dbGuague,
                "--db-ct" : this.dbContent,

                "--db-gg-in-w" : this.dbGuagueInnerWidth,
                "--db-ct-in-w" : this.dbContentInnerWidth,
                "--db-gg-in-h" : this.dbGuagueInnerHeight,
                "--db-ct-in-h" : this.dbContentInnerHeight,
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
            centerVideoHeight : '',
            centerVideoWidth : '',

            dbModerator: '',
            dbGuague: '',
            dbContent:'',

            dbGuagueInnerWidth: '',
            dbContentInnerWidth: '',
            dbGuagueInnerHeight: '',
            dbContentInnerHeight: '',
        }
    },
    created () {
        this.token = this.$store.state.tempToken;

      	// --- Get an OpenVidu object ---
			this.OV = new OpenVidu();

			// --- Init a session ---
			this.session = this.OV.initSession();

			// --- Specify the actions when events take place in the session ---

			// On every new Stream received...
			this.session.on('streamCreated', ({ stream }) => {
				const subscriber = this.session.subscribe(stream);
        subscriber.stream.connection.dataObject=JSON.parse(subscriber.stream.connection.data)
        subscriber.stream.connection.role=this.role
				this.subscribers.push(subscriber);
			});

			// On every Stream destroyed...
			this.session.on('streamDestroyed', ({ stream }) => {
				const index = this.subscribers.indexOf(stream.streamManager, 0);
				if (index >= 0) {
					this.subscribers.splice(index, 1);
				}
			});

			// On every asynchronous exception...
			this.session.on('exception', ({ exception }) => {
				console.warn(exception);
			});

			// --- Connect to the session with a valid user token ---

			// 'getToken' method is simulating what your server-side should do.
			// 'token' parameter should be retrieved and returned by your own backend
      console.log("before connect", this.token);
				this.session.connect(this.token, { clientData: this.$store.state.userStat.em })
					.then(() => {
            console.log("connecting...");
						// --- Get your own camera stream with the desired properties ---

						let publisher = this.OV.initPublisher(undefined, {
							audioSource: undefined, // The source of audio. If undefined default microphone
							videoSource: undefined, // The source of video. If undefined default webcam
							publishAudio: true,  	// Whether you want to start publishing with your audio unmuted or not
							publishVideo: true,  	// Whether you want to start publishing with your video enabled or not
							resolution: '640x480',  // The resolution of your video
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
    },
    mounted() {

        const wValue = document.body.clientWidth*0.75*0.3-20  // 사회자 비디오
        const wValueNotVid = document.body.clientWidth*0.75*0.4 // 게이지 + 컨텐츠
        this.centerVideoWidth = `${wValue}px`
        this.centerVideoHeight = `${wValue*0.6}px`

        const hValue = document.body.clientHeight*0.8 - (wValue*0.6)

        this.dbModerator = `${wValue*0.6}px`
        this.dbGuague = `${hValue*0.15}px`
        this.dbContent = `${hValue*0.85}px`

        this.dbGuagueInnerWidth = `${wValueNotVid-30}px`
        this.dbContentInnerWidth = `${wValueNotVid-50}px`
        this.dbGuagueInnerHeight = `${hValue*0.15-30}px`
        this.dbContentInnerHeight = `${hValue*0.85-50}px`
        window.addEventListener('resize', this.handleResizeHome);
    },
    methods: {
        handleResizeHome() {
            const wValue = document.body.clientWidth*0.75*0.3-20  // 사회자 비디오
            const wValueNotVid = document.body.clientWidth*0.75*0.4 // 게이지 + 컨텐츠
            this.centerVideoWidth = `${wValue}px`
            this.centerVideoHeight = `${wValue*0.6}px`

            const hValue = document.body.clientHeight*0.8 - (wValue*0.6)

            this.dbModerator = `${wValue*0.6}px`
            this.dbGuague = `${hValue*0.15}px`
            this.dbContent = `${hValue*0.85}px`

            this.dbGuagueInnerWidth = `${wValueNotVid-30}px`
            this.dbContentInnerWidth = `${wValueNotVid-50}px`
            this.dbGuagueInnerHeight = `${hValue*0.15-30}px`
            this.dbContentInnerHeight = `${hValue*0.85-50}px`
        },
    }
}
</script>

<style>
.debate-moderator {
    height: var(--db-mod);
    display: flex;
    justify-content: center;
    align-items: center;
    /* outline: aqua solid 3px; */
}
.debate-moderator-inner{
    background-color: white;
    height: var(--center-video-height);
    width: var(--center-video-width);
    border-radius: 10px;
}
.moderatorVideo {
   height: var(--center-video-height);
  width: var(--center-video-width);
}


.debate-guague {
    height: var(--db-gg);
    /* background-color: rgb(255, 255, 24); */
    display: flex;
    justify-content: center;
    align-items: flex-end;
}
.debate-guague-inner {
    height: var(--db-gg-in-h);
    width: var(--db-gg-in-w);
    background-color: rgb(23, 139, 32);
}


.debate-content {
    height: var(--db-ct);
    /* background-color: rgb(23, 139, 32); */
    display: flex;
    justify-content: center;
    align-items: center;
}
.debate-content-inner {
    height: var(--db-ct-in-h);
    width: var(--db-ct-in-w);
    background-color: rgb(255, 255, 24);
}
</style>

