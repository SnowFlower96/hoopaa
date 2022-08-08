<template>
<div v-if="imgTF" class="startImg">
    <!-- <animation-view></animation-view> -->
    <!-- 뷰바꾸는 임시버튼 -->
    <button @click="moderatorView">사회자뷰</button>
    <button @click="allView">방청객뷰</button>
    <button @click="teamView">패널뷰</button>
    <router-link to="/detailSession"><button>세부세션 가기</button></router-link>
    <button @click="voteView">투표모달창 끄기</button>
    <button @click="positionAgree">찬성</button>
    <!-- 뷰바꾸는 임시버튼 -->
</div>


<div class="vote-modal-container" v-if="voteViewTF" :style="customCaroselStyle">
    <div>
        투표받을 모달창
    </div>
</div>
<div class="live-heart-container">
    <div id="heart-div"></div>
    <div>하트 누른 갯수{{countingHeart}}</div>
</div>
<!-- <img v-if="imgTF" class="startImg" :src="require(`@/assets/images/start.png`)" alt=""> -->
<!-- 사회자에게 메세지 보내기 -->
<div v-if="callToMdModal" class="call-to-moderator-container" :style="customCaroselStyle">
    <div class="call-to-moderator-blank"></div>
    <div class="call-to-moderator">
        <div class="call-to-moderator-inner" :style="customCaroselStyle"></div>
        <div class="call-to-moderator-inner call-to-moderator-center" :style="customCaroselStyle">
            <button @click="offCallModal">끄기</button>
            <call-to-moderator v-if="message"></call-to-moderator>
            <user-out v-if="out"></user-out>
            <message-from-team v-if="messageFrom"></message-from-team>
            <upload-file v-if="file"></upload-file>
            <let-team-speak v-if="menu"></let-team-speak>
            <rest-time v-if="false"></rest-time>
            <let-vote v-if="false"></let-vote>
        </div>
        <div class="call-to-moderator-inner" :style="customCaroselStyle"></div>
    </div>
    <div class="call-to-moderator-blank"></div>
</div>
<!-- 사회자에게 메세지 보내기 -->
    <div class="debate-backcolor">
        <div class="video-chatt-wrap">
            <div class="debate-background" :style="customCaroselStyle">
                <div class="debate-room-wrap">
                    <!-- <detail-session :chattOpen="chattTF"></detail-session> -->
                    <div class="videobox-side" :style="customCaroselStyle">
                        <debate-room-side-component ></debate-room-side-component>
                    </div>

                    <div class="videobox-center" :style="customCaroselStyle">
                        <debate-room-center-component ref="debateRoomSideComponent"></debate-room-center-component>
                    </div>

                    <div class="videobox-side" :style="customCaroselStyle">
                        <debate-room-side-component v-bind:position="position" ref="debateRoomSideComponent"></debate-room-side-component>
                    </div>
                </div>
            </div>
            <div v-if="chattTF" class="chatting-box" :style="customCaroselStyle">
                <chatting-all v-if="chattingAllView" @close-chat="changeChatView"></chatting-all>
                <chatting-team v-if="chattingTeamView" @close-chat="changeChatView"></chatting-team>
            </div>
        </div>
        <div class="moderator-menus" v-if="true" :style="customCaroselStyle">
            <p>찬성측 발언권 부여</p>
            <p>반대측 발언권 부여</p>
            <p>쉬는시간 부여</p>
            <router-link to="/"><p>투표 보내기</p></router-link>
        </div>
        <div class="debate-room-footer-class">
            <footer-team
            v-if="footerTeam"
            @call-modal="EmitcallModal"></footer-team>
            <footer-moderator v-if="footerModerator" @call-modal="EmitcallModal"></footer-moderator>
            <footer-all
            v-if="footerAll"
            @rising-heart="risingHeart"
            ></footer-all>
            <div class="chatt-btn" @click="changeChatView"><i class="fas fa-comment-alt"></i></div>
        </div>
    </div>
    <button @click="leaveSession">닫기닫기닫기</button>
</template>


<script>
import debateRoomSideComponent from './debateRoomSideComponent'
import debateRoomCenterComponent from './debateRoomCenterComponent'
// import animationView from './animation-view.vue'
import detailSession from './detailSession'

import chattingAll from './ChattingComponents/chatting-all'
import chattingTeam from './ChattingComponents/chatting-team'

import FooterTeam from './debateRoomFooter/FooterTeam'
import FooterModerator from './debateRoomFooter/FooterModerator'
import FooterAll from './debateRoomFooter/FooterAll'

import callToModerator from './ModalContent/TeamView/callToModerator'
import UploadFile from './ModalContent/TeamView/UploadFile'

import UserOut from './ModalContent/ModeratorView/UserOut'
import MessageFromTeam from './ModalContent/ModeratorView/MessageFromTeam'
import LetTeamSpeak from './ModalContent/ModeratorView/LetTeamSpeak'
import RestTime from './ModalContent/ModeratorView/RestTime'
import LetVote from './ModalContent/ModeratorView/LetVote'
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import UserVideo from '@/views/openvidu/UserVideo.vue';
import { mapState} from 'vuex';
axios.defaults.headers.post['Content-Type'] = 'application/json';


const OPENVIDU_SERVER_URL = process.env.OPENVIDU_SERVER_URL;
const OPENVIDU_SERVER_SECRET = process.env.OPENVIDU_SERVER_SECRET;

export default {
    name: 'debateRoom',
    components: {
        debateRoomSideComponent,
        debateRoomCenterComponent,
        // animationView,
        chattingAll,
        chattingTeam,
        FooterTeam,
        FooterModerator,
        FooterAll,
        callToModerator,
        UserOut,
        MessageFromTeam,
        UploadFile,
        LetTeamSpeak,
        RestTime,
        LetVote,
        detailSession
    },
    async created() {
    var token = this.$store.state.tempToken;

    // --- Get an OpenVidu object ---
    this.room.OV = new OpenVidu();

    // --- Init a session ---
    this.room.session = this.room.OV.initSession();
    // --- Specify the actions when events take place in the session ---

    // On every new Stream received...
    this.room.session.on("streamCreated", ({ stream }) => {
      const subscriber = this.room.session.subscribe(stream);
      subscriber.stream.connection.dataObject = JSON.parse(
        subscriber.stream.connection.data
      );
      var clientData = subscriber.stream.connection.dataObject.clientData;
      console.log(clientData.split("/"));
      if (clientData[0] == this.room.session.sessionId)
        this.room.host = subscriber;
      else this.room.agrees.push(subscriber);
    });

    // On every Stream destroyed...
    // TODO
    this.room.session.on("streamDestroyed", ({ stream }) => {
      const index = this.subscribers.indexOf(stream.streamManager, 0);
      if (index >= 0) {
        this.subscribers.splice(index, 1);
      }
    });

    // On every asynchronous exception...
    this.room.session.on("exception", ({ exception }) => {
      console.warn(exception);
    });

    await this.room.session
      .connect(token, { clientData: this.user.em })
      .then(() => {
        console.log("Connected!!!");
      })
      .catch(error => {
        console.log(
          "There was an error connecting to the session:",
          error.code,
          error.message
        );
      });

    if (this.user.em == this.room.session.sessionId) {
      console.log("you are host");
      let publisher = this.room.OV.initPublisher(undefined, {
        audioSource: undefined, // The source of audio. If undefined default microphone
        videoSource: undefined, // The source of video. If undefined default webcam
        publishAudio: false, // Whether you want to start publishing with your audio unmuted or not
        publishVideo: true, // Whether you want to start publishing with your video enabled or not
        resolution: "680x480", // The resolution of your video
        frameRate: 30, // The frame rate of your video
        insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
        mirror: true, // Whether to mirror your local video or not
      });

      this.room.publisher = publisher;
      this.room.host = publisher;

      // --- Publish your stream ---
      this.room.session.publish(publisher);
    }
  },
    computed : {
      ...mapState(["user", "room"]),
        customCaroselStyle() {
            return {
                "--debate-box-center-width": this.debateCenterBoxWidth,    // videobox-center
                "--debate-box-center-height": this.debateCenterBoxHeight,  // videobox-center

                "--debate-box-side-width": this.debateSideBoxWidth,        // videobox-side
                "--debate-box-side-height": this.debateSideBoxHeight,      // videobox-side

                "--footer-width": this.footerWidth,    // debate-room-footer-class

                "--video-box": this.videoBox,
                "--chatt-box": this.chattBox,      // chatting-box

                "--db-bg" : this.debateBackground, // debate background

                "--call-to-md-view" : this.callToMDView,
                "--call-to-md-blnk" : this.callToMDBlnk,
                "--call-to-md-ct" : this.callToMDCt,
                "--call-to-md-in-width" : this.callToMDInW,
                "--call-to-md-in-height" : this.callToMDInH,
                "--mod-menus-loc" : this.modMenusLoc,
                "--vote-modal-width" : this.voteModalWidth
            }
        }
    },
    data() {
        return {
            position : '',

            debateCenterBoxWidth: '',
            debateCenterBoxHeight: '',

            debateSideBoxWidth: '',
            debateSideBoxHeight: '',

            videoBox: '',
            chattBox: '',

            debateBackground: '',

            chattTF: true,
            imgTF:true,

            chattingAllView: false,
            chattingTeamView: false,

            footerTeam: false,
            footerModerator: false,
            footerAll: false,

            callToMDView: '',
            callToMDBlnk: '',
            callToMDCt: '',
            callToMDInW: '',
            callToMDInH: '',

            callToMdModal: false,

            message: false,
            file: false,
            out: false,
            menu: false,
            options: [this.menu, this.out, this.message, this.file],
            countingHeart :0,
            modMenusLoc: '',
            voteViewTF: true,
            voteModalWidth: ''
        }
    },
    mounted() {
        const wVideoValue = document.body.clientWidth
        const debateBackground = wVideoValue*0.75
        const hValue = document.body.clientHeight


        this.debateBackground = `${debateBackground}px`
        this.chattBox =  `${wVideoValue*0.25}px`
        this.footerWidth = `${wVideoValue}px`


        this.debateCenterBoxWidth = `${debateBackground*0.4-10}px`
        this.debateSideBoxWidth = `${debateBackground*0.3-10}px`

        this.debateCenterBoxHeight = `${hValue*0.8}px`
        this.debateSideBoxHeight = `${hValue*0.8}px`

        this.callToMDView = `${debateBackground}px`
        this.callToMDBlnk = `${debateBackground*0.3}px`
        this.callToMDCt = `${debateBackground*0.4}px`
        this.callToMDInW = `${debateBackground*0.4}px`
        this.callToMDInH = `${hValue*0.31}px`

        this.modMenusLoc = `${wVideoValue*0.36}px`
        this.voteModalWidth = `${debateBackground}px`
        window.addEventListener('resize', this.handleResizeHome);
    },
    methods: {
        voteView() {
            this.voteViewTF = !this.voteViewTF
        },
        risingHeart() {
            const stripe = document.getElementById('heart-div')
            setTimeout(() => {
                stripe.classList.remove('animate');
            }, 400);
            stripe.classList.add('animate');
            this.countingHeart += 1
            if(this.countingHeart === 5) {
                console.log('5개')
            }
        },
        handleResizeHome() {
            if (this.chattTF === true) {    // 채팅창 열려있을때
                const wVideoValue = document.body.clientWidth
                const debateBackground = wVideoValue*0.75
                const hValue = document.body.clientHeight


                this.debateBackground = `${debateBackground}px`
                this.chattBox =  `${wVideoValue*0.25}px`
                this.footerWidth = `${wVideoValue}px`


                this.debateCenterBoxWidth = `${debateBackground*0.4-10}px`
                this.debateSideBoxWidth = `${debateBackground*0.3-10}px`

                this.debateCenterBoxHeight = `${hValue*0.8}px`
                this.debateSideBoxHeight = `${hValue*0.8}px`

                this.callToMDView = `${debateBackground}px`
                this.callToMDBlnk = `${debateBackground*0.3}px`
                this.callToMDCt = `${debateBackground*0.4}px`
                this.callToMDInW = `${debateBackground*0.4}px`
                this.callToMDInH = `${hValue*0.31}px`
                this.modMenusLoc = `${wVideoValue*0.36}px`
                this.voteModalWidth = `${debateBackground}px`
            }
            else {     // 채팅창 닫혀있을때
                const wVideoValue = document.body.clientWidth
                const debateBackground = wVideoValue
                const hValue = document.body.clientHeight


                this.debateBackground = `${debateBackground}px`
                this.chattBox =  `${wVideoValue*0.25}px`
                this.footerWidth = `${wVideoValue}px`


                this.debateCenterBoxWidth = `${debateBackground*0.4-10}px`
                this.debateSideBoxWidth = `${debateBackground*0.3-10}px`

                this.debateCenterBoxHeight = `${hValue*0.8}px`
                this.debateSideBoxHeight = `${hValue*0.8}px`

                this.callToMDView = `${debateBackground}px`
                this.callToMDBlnk = `${debateBackground*0.3}px`
                this.callToMDCt = `${debateBackground*0.4}px`
                this.callToMDInW = `${debateBackground*0.4}px`
                this.callToMDInH = `${hValue*0.31}px`
                this.modMenusLoc = `${wVideoValue*0.36}px`
                this.voteModalWidth = `${debateBackground}px`
            }
        },
        moderatorView() {
            this.chattingAllView = true
            this.chattingTeamView = false

            this.footerModerator = true
            this.footerTeam = false
            this.footerAll = false
        },
        allView() {
            this.chattingAllView = true
            this.chattingTeamView = false

            this.footerAll = true
            this.footerModerator = false
            this.footerTeam = false
        },
        teamView() {
            this.chattingAllView = false
            this.chattingTeamView = true

            this.footerTeam = true
            this.footerModerator = false
            this.footerAll = false
        },
        changeChatView() {
            this.chattTF = !this.chattTF
            if (this.chattTF === true) {    // 채팅창 열려있을때
                const wVideoValue = document.body.clientWidth
                const debateBackground = wVideoValue*0.75
                const hValue = document.body.clientHeight


                this.debateBackground = `${debateBackground}px`
                this.chattBox =  `${wVideoValue*0.25}px`
                this.footerWidth = `${wVideoValue}px`


                this.debateCenterBoxWidth = `${debateBackground*0.4-10}px`
                this.debateSideBoxWidth = `${debateBackground*0.3-10}px`

                this.debateCenterBoxHeight = `${hValue*0.8}px`
                this.debateSideBoxHeight = `${hValue*0.8}px`

                this.callToMDView = `${debateBackground}px`
                this.callToMDBlnk = `${debateBackground*0.3}px`
                this.callToMDCt = `${debateBackground*0.4}px`
                this.callToMDInW = `${debateBackground*0.4}px`
                this.callToMDInH = `${hValue*0.31}px`
                this.voteModalWidth = `${debateBackground}px`
            }
            else {     // 채팅창 닫혀있을때
                const wVideoValue = document.body.clientWidth
                const debateBackground = wVideoValue
                const hValue = document.body.clientHeight


                this.debateBackground = `${debateBackground}px`
                this.chattBox =  `${wVideoValue*0.25}px`
                this.footerWidth = `${wVideoValue}px`


                this.debateCenterBoxWidth = `${debateBackground*0.4-10}px`
                this.debateSideBoxWidth = `${debateBackground*0.3-10}px`

                this.debateCenterBoxHeight = `${hValue*0.8}px`
                this.debateSideBoxHeight = `${hValue*0.8}px`

                this.callToMDView = `${debateBackground}px`
                this.callToMDBlnk = `${debateBackground*0.3}px`
                this.callToMDCt = `${debateBackground*0.4}px`
                this.callToMDInW = `${debateBackground*0.4}px`
                this.callToMDInH = `${hValue*0.31}px`
                this.voteModalWidth = `${debateBackground}px`
            }
        },
        offCallModal() {
            this.callToMdModal = false
        },
        // Emit 함수를 하나로 하고 그 안에서 분기처리하기
        EmitcallModal(option) {
            // const labels = ['menu', 'out', 'message', 'file'];

            // for (let idx=0; idx < this.options.length; idx++) {
            //      console.log(labels[idx], option, this.options[idx])
            //     if (labels[idx] == option) {
            //         this.options[idx] = true
            //         console.log('여기를 왔는데 ?', labels[idx], this.options)
            //     } else {
            //         this.options[idx] = false
            //     }
            // }
            this.callToMdModal = !this.callToMdModal

            if (option == 'menu') {
                this.menu = true
                this.out = false
                this.message = false
                this.file = false
            }
            else if (option == 'out') {
                this.menu = false
                this.out = true
                this.message = false
                this.file = false
            }
            else if (option == 'message') {
                this.menu = false
                this.out = false
                this.message = true
                this.file = false
            }
            else if (option == 'file') {
                this.menu = false
                this.out = false
                this.message = false
                this.file = true
            }
        },
        leaveSession() {
          this.$refs.debateRoomSideComponent.leaveSession();
        },
      // 찬성 참여
      positionAgree() {
        this.position = 'agree',
        console.log(this.$refs);
        this.$refs.debateRoomSideComponent.joinPannel()
      },
      positionDisagree() {

      },
    }
}
</script>

<style>
.vote-modal-container {
    position: absolute;
    height: 93vh;
    width: var(--vote-modal-width);
    background-color: rgba(255, 255, 255, 0.295);
}
.moderator-menus {
    position: absolute;
    width:200px;
    height: auto;
    background-color: aliceblue;
    bottom: 7vh;
    left: var(--mod-menus-loc);
}
.live-heart-container {
    top: 500px;
    left: 100px;
    position: absolute;
}

#heart-div {
  position: absolute;
  bottom: -60px;
  width: 50px;
  height: 50px;
  background: url("https://s3.us-east-2.amazonaws.com/upload-icon/uploads/icons/png/15721583221557740359-512.png") no-repeat;
  background-size: cover;
  left: 10px;
}
#heart-div.animate {
  animation: bubble 1s linear;
}
/* .smile-div {
    position: absolute;
    bottom: -60px;
    width: 50px;
    height: 50px;
    background-size: cover;
    background: url("https://user-images.githubusercontent.com/87743473/183299200-727383c3-6ae0-4631-bf17-169720b9f480.png") no-repeat;
    left: 10px;
    animation: bubble 1s linear infinite;
} */

@keyframes bubble {
    0% {
        bottom: 0px;
        opacity: 1
    }
    70% {
        opacity: 0
    }
    100% {
        bottom: 500px;
        opacity: 0
    }
}


.chatt-btn {
    width: 5vh;
    height: 5vh;
    outline: solid 1px aqua;
    border-radius: 50%;
    margin-left: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
    color: aqua;
}
.chatt-btn:hover {
    cursor: pointer;
    background-color: aqua;
    opacity: 50%;
    color: black;
}
/* 사회자에게 메세지 보내기 스타일 라인 */
.call-to-moderator-container {
    position: absolute;
    height: 93vh;
    width: var(--call-to-md-view);
    /* background-color: yellow; */
    /* opacity: 50%; */
    display: flex;
    justify-content: start;
}
.call-to-moderator-blank {
    width: var(--call-to-md-blnk);
}
.call-to-moderator {
    width: var(--call-to-md-ct);
}
.call-to-moderator-inner {
    width: var(--call-to-md-in-width);
    height: var(--call-to-md-in-height);
    /* outline: solid 3px orange; */
}
.call-to-moderator-center {
    background-color: whitesmoke;
    border-radius: 10px;
    outline: black 3px solid;
}
/* 사회자에게 메세지 보내기 스타일 라인 */

.startImg {
    position: absolute;
    top: 20%;
    color: aliceblue;
    background-color: rgb(93, 93, 53);
    z-index: 3;
}
.debate-backcolor {
    background-color: black;
    height: 100vh;
    /* text-align: center; */
}
.video-chatt-wrap {
    display: flex;
}
.debate-background {
    height: 93vh;
    display: flex;
    align-items: center;
    justify-content: center;
    /* background-color: rgba(96, 255, 112, 0.766); */
    width: var(--db-bg);
}
.debate-room-footer-class {
    width: var(--footer-width);
    height: 7vh;
    /* outline: 10px #669977 solid; */
    display: flex;
    justify-content: center;
    align-items: center;
}
.chatting-box {
    background-color: whitesmoke;
    width: var(--chatt-box);
    height: 93vh;
    border-radius: 10px;
}
.debate-room-wrap {
    display: flex;
    /* background-color: rgba(146, 227, 153, 0.581); */
}
.videobox-side {
    display: flex;
    justify-content: center;
    height: var(--debate-box-side-height);
    width: var(--debate-box-side-width);
    color: black;
    /* background-color: rgb(61, 255, 94);
    outline: 10px #667799 solid; */
}
.videobox-center {
  height: var(--debate-box-center-height);
  width: var(--debate-box-center-width);
  color: black;
  /* background-color: rgba(121, 193, 255, 0.621); */
  /* outline: 10px #667799 solid; */
}


</style>
