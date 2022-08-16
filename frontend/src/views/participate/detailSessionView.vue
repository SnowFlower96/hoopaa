<template>
    <div class="detail-box-background" :style="customDetailSessionSize">
        <div class="detail-box-outer">
            <div class="detail-box-file">
                <div v-for="(sub, index) in subscribersScreen" :key="index" >
					        <user-video :stream-manager="sub.data" ></user-video>
				        </div>
            </div>
            <div class="detail-box-video">
                <debate-room-video class="debate-room-side-vido" v-for="(item, index) in subscribers" :key="index" :stream="item.data"></debate-room-video>
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
import debateRoomSideComponent from './debateRoomSideComponent'
import chattingTeam from './ChattingComponents/chatting-team.vue'
// 토론방 위에 보여지는 효과 관련

import debateRoomVideo from './debateRoomVideo'


// 메뉴 및 모달뷰
import callToModerator from './ModalContent/TeamView/callToModerator'// 팀에서 사회자한테 메세지보내는 모달
import UploadFile from './ModalContent/TeamView/UploadFile'// 화면공유 모달
import UserOut from './ModalContent/ModeratorView/UserOut'// 사용자 강퇴 모달
import MessageFromTeam from './ModalContent/ModeratorView/MessageFromTeam'//사회자가 보는 메세지 (팀에서 보낸)모달
import LetTeamSpeak from './ModalContent/ModeratorView/LetTeamSpeak'//발언권 부여 모달
import RestTime from './ModalContent/ModeratorView/RestTime'// 쉬는시간 부여 모달

// 비디오 관련 및 내부로직
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import UserVideo from '@/views/openvidu/UserVideo.vue';
import { mapState} from 'vuex';
axios.defaults.headers.post['Content-Type'] = 'application/json';
import moment from 'moment';
import router from '../../common/lib/vue-router'

const OPENVIDU_SERVER_URL = "https://hoopaa.site:8443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";
export default {
    components: {
        FooterTeam,
        debateRoomSideComponent,
        chattingTeam,


      // 토론방 관련
        debateRoomVideo,

      //  채팅
        chattingTeam,

      // 메뉴 및 모달뷰
        MessageFromTeam,
        UploadFile,
      // 영상
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
        },
        ...mapState(["user", "position", "tempToken"]),
        customCaroselStyle() {
            return {

              // 토론방 센터 (.videobox-center)
                "--debate-box-center-width": this.dCenterW,
                "--debate-box-center-height": this.dCenterH,
                "--dct-height" : this.dtcHeight,

              // 표준 비디오 사이즈
              "--center-video-height" : this.centerVideoHeight,
              "--center-video-width" : this.centerVideoWidth,

              // 토론방 양쪽 (.videobox-side)
                "--debate-box-side-width": this.dSideW,
                "--debate-box-side-height": this.dSideH,
                "--vsi-blank" : this.vsiBlank,

              // 하단바 (.debate-room-footer-class)
                "--footer-width": this.footerWidth,

              // 채팅창 (.chatting-box)
                "--chatt-box": this.chattBox,

              // 메인화면 wrap (.debate-background)
                "--db-bg" : this.wValue075,

              // 토론방 추가기능 모달창
                "--call-to-md-view" : this.callToMDView,
                "--call-to-md-blnk" : this.callToMDBlnk,
                "--call-to-md-ct" : this.callToMDCt,
                "--call-to-md-in-width" : this.callToMDInW,
                "--call-to-md-in-height" : this.callToMDInH,
                "--call-to-md-in-height-c" : this.callToMDINHC,

                "--mod-menus-loc" : this.modMenusLoc,
                "--vote-modal-width" : this.voteModalWidth,

                "--all-heart-left" : this.allHeartLeft,

                "--share-view-height": this.shareViewH,
                "--share-view-width": this.shareViewW,


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
            // 비디오 관련 및 내부로직
            OV: undefined,
            session: undefined,
            host: undefined,
            subscribers : [],
            publisher : undefined,

          // 화면 공유
            OVScreen : undefined,
			      sessionScreen: undefined,
			      publisherScreen: undefined,
			      subscribersScreen:[],
            screensharing: false,
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
    async created() {
      await this.joinSession();
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
         // 세션 연결
      async joinSession() {
      const token = this.tempToken;

      // --- Get an OpenVidu object ---
      this.OV = new OpenVidu();

      // --- Init a session ---
      this.session = this.OV.initSession();
      // --- Specify the actions when events take place in the session ---

      // On every new Stream received...
      this.session.on("streamCreated", ({ stream }) => {
        const subscriber = this.session.subscribe(stream);
        if (subscriber.stream.typeOfVideo == 'CAMERA') {
          let connectionData = JSON.parse(subscriber.stream.connection.data);
          var clientData = connectionData.clientData.split("/");
          console.log(clientData);
          let sub = {
              id : clientData[0],
              stream : 'subscriber',
              data : subscriber
          };
          this.subscribers.push(sub);
        }

      });

      // On every Stream destroyed...
      // TODO
      this.session.on("streamDestroyed", ({stream}) => {

        for (var i = 0; i < this.subscribers.length; i++) {
          if (this.subscribers[i].data == stream.streamManager) {
          this.subscribers.splice(i, 1);
        }
        }
        console.log(this.subscribersScreen.length)
        for (var i = 0; i < this.subscribersScreen.length; i++) {
          if (this.subscriberScreen[i].data == stream.streamManager) {
            this.subscriberScreen.splice(i, 1);
          }
        }

      });
      // on session destroyed...
      this.session.on("sessionDestroyed", () => {
        router.push('/')
      });


      // On every asynchronous exception...
      this.session.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      // Hearing Signal


      await this.session
        .connect(token, { clientData: this.user.id + "/" + this.position })
        .then(() => {
          let publisher = this.OV.initPublisher(undefined, {
            audioSource: undefined, // The source of audio. If undefined default microphone
            videoSource: undefined, // The source of video. If undefined default webcam
            publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
            publishVideo: true, // Whether you want to start publishing with your video enabled or not
            resolution: "680x480", // The resolution of your video
            frameRate: 30, // The frame rate of your video
            insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
            mirror: false // Whether to mirror your local video or not
          });
          this.publisher = publisher;
          let sub = {
            id : this.user.id,
            stream : 'publisher',
            data : this.publisher
        };


            this.subscribers.push(sub);

          console.log("Connected!!!");
          console.log(this.session.connection)
          this.session.publish(publisher);
        })
        .catch(error => {
          console.log(
            "There was an error connecting to the session:",
            error.code,
            error.message
          );
        });

      if (this.user.id == this.session.sessionId) {
        console.log("you are host");
      } else {
        console.log("you are pannel");
        console.log(this.agree);
      }
      this.joinScreen();
      },

      async joinScreen(){
			this.OVScreen = new OpenVidu();
			this.sessionScreen = this.OVScreen.initSession();

			this.sessionScreen.on('streamCreated', ({ stream }) => {
					const subscriberScreen = this.sessionScreen.subscribe(stream);
          if (subscriberScreen.stream.typeOfVideo == 'SCREEN') {
             let sub = {
            id : this.user.id,
            stream : 'publisher',
            data : subscriberScreen
        };
            this.subscribersScreen.push(sub);
          }
					console.log(this.subscribersScreen.length + "!!!!!!!!!!!!!!!!")
			});

			await this.getToken(this.session.sessionId).then(tokenScreen => {
				this.sessionScreen.connect(tokenScreen, { clientData: this.user.id })
				.then(() => {
					console.log("Session screen connected");
				})
				.catch(error => {
					console.log('There was an error connecting to the session for screen share:', error.code, error.message);
				});
			});
		},
    leaveSession () {
			// --- Leave the session by calling 'disconnect' method over the Session object ---
			if (this.session) this.session.disconnect();
			if (this.sessionScreen) this.sessionScreen.disconnect();

			this.session = undefined;
			this.sessionScreen = undefined;
			this.publisher = undefined;
			this.publisherScreen = undefined;
      this.host = undefined;
      this.subscribers = [];
			this.subscribersScreen = [];
			this.OV= undefined;
			this.OVScreen = undefined;

			if(this.screensharing)
				this.screensharing=false;

			window.removeEventListener('beforeunload', this.leaveSession);
      router.push('/')
		},
     getToken (mySessionId) {
			return this.createSession(mySessionId).then(sessionId => this.createToken(sessionId));
		},

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-session
		createSession (sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`, JSON.stringify({
						customSessionId: sessionId,
					}), {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.id))
					.catch(error => {
						if (error.response.status === 409) {
							resolve(sessionId);
						} else {
							console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`);
							if (window.confirm(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`)) {
								location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
							}
							reject(error.response);
						}
					});
			});
		},

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-connection
		createToken (sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`, {}, {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.token))
					.catch(error => reject(error.response));
			});
		},


		publishScreenShare(){
      console.log("들어오지");
			let publisherScreen = this.OVScreen.initPublisher("container-screens", {videoSource: "screen"});
      console.log("여기오냐?")
			publisherScreen.once('accessAllowed', () => {
		this.screensharing = true;
		// It is very important to define what to do when the stream ends.
		publisherScreen.stream.getMediaStream().getVideoTracks()[0].addEventListener('ended', () => {
			console.log('User pressed the "Stop sharing" button');
			this.sessionScreen.unpublish(publisherScreen);
			this.screensharing = false;
		});
    this.publisherScreen = publisherScreen;
     let sub = {
            id : this.user.id,
            stream : 'subscriber',
            data : publisherScreen
        };
    this.subscribersScreen.push(sub);
		this.sessionScreen.publish(publisherScreen);
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
