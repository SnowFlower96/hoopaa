<template>
<div v-if="imgTF" class="startImg">
<!-- <img v-if="imgTF" class="startImg" :src="require(`@/assets/images/start.png`)" alt=""> -->
    <!-- <animation-view></animation-view> -->
    <!-- 뷰바꾸는 임시버튼 -->
    <button @click="moderatorView">사회자뷰</button>
    <button @click="allView">방청객뷰</button>
    <button @click="teamView">패널뷰</button>
    <router-link to="/detailSessionView"><button>세부세션 가기</button></router-link>
    <button @click="positionAgree">찬성</button>
    <!-- <div id="demo">넨</div> -->
    <!-- 뷰바꾸는 임시버튼 -->
</div>


<!-- 투표 받는 창 -->
<div class="vote-modal-container" v-if="voteViewTF" :style="customCaroselStyle">``
    <div>
        <div class="vote-view">
            <div class="vote-view-inner">
                <div class="common-vote-view">
                    <div class="all-view-wrap">
                        <div style="font-size: 40px;">최종 투표</div>
                        <div class="timer">
                            <div id="demo"></div>
                        </div>
                    </div>
                </div>

                <div v-if="voteTeam" class="team-view">
                    <p>방청객이 투표중입니다 ...</p>
                </div>
                <div v-if="voteMod" class="mod-view">
                    <div>
                        <p>방청객이 투표중입니다 ...</p>
                        <p>타이머가 끝나면 종료버튼을 눌러 토론을 종료하세요</p>
                        <div class="vote-btn-wrap">
                            <router-link style="text-decoration: none;" to="/"><div class="vote-btn">결과화면으로</div></router-link>
                        </div>
                    </div>
                </div>
                <div v-if="voteAll" class="all-view">
                    <div class="all-view-wrap">
                        <p>당신의 최종 의견을 투표하세요</p> 
                        <p>제한시간안에 투표하지 않으면 무효 처리됩니다</p>
                        <p>타이머가 끝나면 자동으로 제출됩니다</p>
                        <div class="vote-btn-wrap">
                            <div class="vote-btn" @click="voteFunction(1)">찬성</div>
                            <div class="vote-btn" @click="voteFunction(0)">반대</div>
                        </div>
                        <p>
                            <span v-if="voteStatus">찬성</span> 
                            <span v-if="!voteStatus">반대</span> 
                            을(를) 선택하셨습니다</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 투표 받는 창 -->


<!-- 방청객 반응 -->
<div class="live-heart-container">
    <div id="heart-div"></div>
    <div id="heart-div"></div>
    <div id="clap-div"></div>
    <div id="clap-div"></div>
    <div>하트 누른 갯수{{countingHeart}}</div>
</div>
<!-- 방청객 반응 -->


<!-- 토론방 추가기능 모달창 -->
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
<!-- 토론방 추가기능 모달창 -->


<!-- 토론방 메인화면 -->
<div class="debate-backcolor">
    <div class="video-chatt-wrap">
        <div class="debate-background" :style="customCaroselStyle">
            <div class="debate-room-wrap">
                <!-- <detail-session :chattOpen="chattTF"></detail-session> -->
                <div class="videobox-side" :style="customCaroselStyle">
                    <debate-room-side-component ></debate-room-side-component>
                </div>

                <div class="videobox-center" :style="customCaroselStyle">
                    <debate-room-center-component :time-list="timeList" ref="debateRoomSideComponent"></debate-room-center-component>
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

    <div class="moderator-menus" v-if="modMenu" :style="customCaroselStyle">
        <p @click="EmitcallModal('menu')">패널 발언권 부여</p>
        <p>쉬는시간 부여</p>
        <p @click="voteVisible">투표 보내기</p>
    </div>

    <div class="debate-room-footer-class">
        <footer-team
        v-if="footerTeam"
        @call-modal="EmitcallModal"
        ></footer-team>

        <footer-moderator
        v-if="footerModerator"
        @call-modal="EmitcallModal"
        @mod-menu="openCloseModMenu"
        ></footer-moderator>

        <footer-all
        v-if="footerAll"
        @rising-heart="risingHeart"
        @clap-anime="clapAnime"
        ></footer-all>

        <div class="chatt-btn" @click="changeChatView"><i class="fas fa-comment-alt"></i></div>
    </div>
</div>
<!-- 토론방 메인화면 -->
<button @click="leaveSession">닫기닫기닫기</button>
</template>


<script>
import debateRoomSideComponent from './debateRoomSideComponent'
import debateRoomCenterComponent from './debateRoomCenterComponent'
// import animationView from './animation-view.vue'
import detailSessionView from './detailSessionView'

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
        detailSessionView
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
            voteViewTF: false,
            voteModalWidth: '',
            modMenu: false,

            voteTeam: false,
            voteAll: false,
            voteMod: false,

            voteTime: 60,
            voteStatus: null

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
        voteFunction(status) {
            this.voteStatus = status
        },
        voteVisible() {
            // this.voteViewTF = true
            this.voteViewTF = !this.voteViewTF

            let time = this.voteTime;
            let min = "";
            let sec = "";
            let x = setInterval(function() {
            min = parseInt(time/60);
            sec = time%60;

            document.getElementById("demo").innerHTML = min + "분" + sec + "초";
            time--;

            if (time < 0) {
                clearInterval(x);
                document.getElementById("demo").innerHTML = "투표가 종료되었습니다";
            }
            }, 1000);

        // 타이머 로직
        },
        openCloseModMenu() {
            this.modMenu = !this.modMenu
        },
        clapAnime() {
            const stripe = document.getElementById('clap-div')
            setTimeout(() => {
                stripe.classList.remove('animate');
            }, 250);
            stripe.classList.add('animate');
        },
        voteView() {
            this.voteViewTF = !this.voteViewTF
        },
        risingHeart() {
            const stripe = document.getElementById('heart-div')
            setTimeout(() => {
                stripe.classList.remove('animate');
            }, 250);
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

            this.voteTeam = false
            this.voteAll = false
            this.voteMod = true
        },
        allView() {
            this.chattingAllView = true
            this.chattingTeamView = false

            this.footerAll = true
            this.footerModerator = false
            this.footerTeam = false

            this.voteTeam = false
            this.voteAll = true
            this.voteMod = false
        },
        teamView() {
            this.chattingAllView = false
            this.chattingTeamView = true

            this.footerTeam = true
            this.footerModerator = false
            this.footerAll = false

            this.voteTeam = true
            this.voteAll = false
            this.voteMod = false
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
.vote-view {
    width: 500px;
    height: 500px;
    background: rgba(0, 0, 0, 0.856);
    border-radius: 10px;
    outline: rgb(122, 122, 122) 1px solid;
    box-shadow: 3px 10px 10px 3px  rgba(0, 0, 0, 0.589);
}
.vote-view-inner {
    width: 460px;
    height: 460px;
    padding: 20px;
    color: white;
}
.common-vote-view {
    width: 100%;
    height: 35%;
    /* background: blue; */
    text-align: center;
    display: flex;
    justify-content: center;
    align-items: center;
}
.vote-btn {
    margin: 10px;
    width: 100px;
    height: 50px;
    background: yellow;
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 10px;
    color: black;
}
#demo {
    color: white;
    font-size: 30px;
}
.timer {
    width: 350px;
    height: 40px;
    /* background: rgb(126, 126, 105); */
    margin: 10px;
}
.vote-btn-wrap {
    display: flex;
    justify-content: center;
    align-items: center;
}
.team-view, .all-view, .mod-view {
    margin-top: 20px;
    width: 100%;
    height: 60%;
    /* background: rgb(0, 255, 179); */
    text-align: center;
    display: flex;
    justify-content: center;
    align-items: center;
}
.vote-modal-container {
    position: absolute;
    height: 93vh;
    width: var(--vote-modal-width);
    background-color: rgba(255, 255, 255, 0.295);
    display: flex;
    justify-content: center;
    align-items: center;
}
.moderator-menus {
    color: white;
    position: absolute;
    width:170px;
    height: auto;
    background-color: rgb(0, 0, 0);
    bottom: 7vh;
    left: var(--mod-menus-loc);
    text-align: center;
    border-radius: 10px;
    outline: 0.5px solid white;
}
.moderator-menus > p {cursor: pointer;}
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
#clap-div {
  position: absolute;
  bottom: -60px;
  width: 50px;
  height: 50px;
    background: url("https://user-images.githubusercontent.com/87743473/183299200-727383c3-6ae0-4631-bf17-169720b9f480.png") no-repeat;
    background-size: cover;
  left: 10px;
}
#clap-div.animate {
    animation: bubble 0.7s linear;
}
#heart-div.animate {
  animation: bubble 0.7s linear;
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
        width: 200px;
        height: 200px;
        opacity: 0
    }
}


.chatt-btn {
    width: 5vh;
    height: 5vh;
    outline: solid 1px white;
    border-radius: 50%;
    margin-left: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
    color: white;
}
.chatt-btn:hover {
    cursor: pointer;
    background-color: white;
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
    border-radius: 10px;
    background: rgba(0, 0, 0, 0.856);
    border-radius: 10px;
    outline: rgb(122, 122, 122) 1px solid;
    box-shadow: 3px 10px 10px 3px  rgba(0, 0, 0, 0.589);
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
