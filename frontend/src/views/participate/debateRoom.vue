<template id="debate-room-body">
<!-- 토론진행중 애니메이션 구성 -->
<div  class="animation-role-background" v-if="false" :style="customCaroselStyle">
    <start-letter id="arb" v-if="startEvent"></start-letter>
</div>
<!-- 토론진행중 애니메이션 구성 -->



<!-- 곧 없어질 버튼 -->
<div class="animation-control-btns">
    <button @click="animation('startEvent')">시작 이벤트</button>
    <button @click="animation('heartTen')" v-if="heartTen">하트갯수 10개 됐을때</button>
    <button @click="animation('heartfift')" v-if="heartfift">하트갯수 50개 됐을때</button>
    <button @click="animation('heartHund')" v-if="heartHund">하트갯수 100개 됐을때</button>
    <button @click="animation('restEvent')" v-if="restEvent">쉬는시간일때</button>
</div>
<!-- 곧 없어질 버튼 -->


<!-- 뷰바꾸는 임시버튼 -->
<div v-if="imgTF" class="startImg">
<!-- <img v-if="imgTF" class="startImg" :src="require(`@/assets/images/start.png`)" alt=""> -->
    <button @click="moderatorView">사회자뷰</button>
    <button @click="allView">방청객뷰</button>
    <button @click="teamView">패널뷰</button>
    <router-link to="/detailSessionView"><button>세부세션 가기</button></router-link>
    <button @click="positionAgree">찬성</button>
    <button @click="messageFromTeam">팀에서 사회자한테 주는 메세지</button>
    <!-- <div id="demo">넨</div> -->
</div>
<!-- 뷰바꾸는 임시버튼 -->

    

    <!-- 쉬는시간 모달 -->
    <div class="vote-modal-container" v-if="restModal" :style="customCaroselStyle">
        <div class="rest-timer">
            <p id="restTimerDemo">0분0초</p>
        </div>
        <div class="rest-animation">
            여기 애니메이션 들어갈곳
        </div>
    </div>
    <!-- 쉬는시간 모달 -->


    <!-- 투표 받는 창 -->
    <div class="vote-modal-container" v-if="voteViewTF" :style="customCaroselStyle">
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
                                <router-link style="text-decoration: none;" to="/endDebate"><div class="vote-btn">결과화면으로</div></router-link>
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
    <div class="live-heart-container" :style="customCaroselStyle">
        <div id="heart-div"></div>
        <div id="heart-div"></div>
        <div id="clap-div"></div>
        <div id="clap-div"></div>
        <!-- <div>하트 누른 갯수{{countingHeart}}</div> -->
    </div>
    <!-- 방청객 반응 -->


    <!-- 토론방 추가기능 모달창 -->
    <div v-if="callToMdModal" class="call-to-moderator-container" :style="customCaroselStyle">
        <div class="call-to-moderator-blank"></div>
        <div class="call-to-moderator">
            <div class="call-to-moderator-inner" :style="customCaroselStyle"></div>
            <div class="call-to-moderator-inner-c call-to-moderator-center" :style="customCaroselStyle">
                <div class="modal-icon" @click="offCallModal"><i class="fas fa-times"></i></div>
                <call-to-moderator v-if="message"></call-to-moderator>

                <user-out v-if="out"></user-out>

                <message-from-team v-if="messageFrom"></message-from-team>

                <upload-file v-if="file"></upload-file>

                <let-team-speak 
                v-if="menu"
                @emit-time="EmitTime"
                ></let-team-speak>

                <rest-time 
                v-if="rest"
                @emit-rest="EmitRest"
                ></rest-time>
            </div>
            <div class="call-to-moderator-inner" :style="customCaroselStyle"></div>
        </div>
        <div class="call-to-moderator-blank"></div>
    </div>
    <!-- 토론방 추가기능 모달창 -->


    <!-- 토론방 메인화면 -->
    <div class="debate-backcolor">

        <!-- 메인화면 + 채팅 -->
            <div class="video-chatt-wrap">

                <!-- 메인화면 (비디오 + 화면공유 컴포넌트 모인곳) -->
                    <div class="debate-background" :style="customCaroselStyle">
                        <div class="debate-room-wrap">
                            <!-- <detail-session :chattOpen="chattTF"></detail-session> -->
<!-------------------------------------------- 여기가 토론방 비디오 들어가는 부분 ------------------------------------------->
<!-------------------------------------------- 여기가 토론방 비디오 들어가는 부분 ------------------------------------------->
                              <!-- 토론방 왼쪽 -->
                                <div class="videobox-side" :style="customCaroselStyle">
                                    <div class="vsi-blank"></div>
                                    <div class="vsi-wrap">
                                        <!-- <div class="videobox-side-inner" :style="customCaroselStyle"></div> -->
                                        <!-- 여기에 for문으로 비디오 넣어보면 됨 -->
                                        <debate-room-video></debate-room-video>
                                    </div>
                                </div>
                              <!-- 토론방 왼쪽 -->
                              
                              <!-- 토론방 센터 -->
                                <div class="videobox-center" :style="customCaroselStyle">
                                  
                                  <div class="moderator-video">
                                    <div class="moderator-video-inner" :style="customCaroselStyle">
                                      <!-- 사회자 비디오 하나 들어갈 곳 -->
                                      <debate-room-video></debate-room-video>
                                    </div>
                                  </div>

                                  <div class="debateroom-center-timer" :style="customCaroselStyle"> <!-- 이부분 나중에 위로 가져오기-->
                                      <debate-room-center-component 
                                      :time-list="timeList" 
                                      ref="debateRoomSideComponent"
                                      :moderator="moderator"
                                      :all="all"
                                      :team="team"
                                      ></debate-room-center-component>
                                  </div>

                                  <div class="share-view-wrap" :style="customCaroselStyle">
                                    <div class="share-view">
                                        <!-- 화면공유 여기에 넣으면 됨 -->
                                    </div>
                                  </div>

                                </div>
                              <!-- 토론방 센터 -->

                              <!-- 토론방 오른쪽 -->
                                <div class="videobox-side" :style="customCaroselStyle">
                                    <div class="vsi-blank"></div>
                                    <div class="vsi-wrap">
                                        <!-- 여기에 for문으로 비디오 넣어보면 됨 -->
                                        <debate-room-video></debate-room-video>
                                    </div>
                                </div>
                              <!-- 토론방 오른쪽 -->
<!-------------------------------------------- 여기가 토론방 비디오 들어가는 부분 ------------------------------------------->
<!-------------------------------------------- 여기가 토론방 비디오 들어가는 부분 ------------------------------------------->
                        </div>
                    </div>
                <!-- 메인화면 (비디오 + 화면공유 컴포넌트 모인곳) -->


                <!-- 채팅창 -->
                    <div v-if="chattTF" class="chatting-box" :style="customCaroselStyle">
                        <chatting-all v-if="chattingAllView" @close-chat="changeChatView"></chatting-all>
                        <chatting-team v-if="chattingTeamView" @close-chat="changeChatView"></chatting-team>
                    </div>
                <!-- 채팅창 -->


            </div>
        <!-- 메인화면 + 채팅 -->


        <!-- 사회자 footer에서 나오는 메뉴 -->
            <div class="moderator-menus" v-if="modMenu && moderator" :style="customCaroselStyle">
                <p @click="EmitcallModal('menu')">패널 발언권 부여</p>
                <p @click="EmitcallModal('rest')">쉬는시간 부여</p>
                <p @click="voteVisible">투표 보내기</p>
            </div>
        <!-- 사회자 footer에서 나오는 메뉴 -->


        <!-- footer -->
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
        <!-- footer -->

    </div>
    <!-- 토론방 메인화면 -->
    <button @click="leaveSession">닫기닫기닫기</button>
</template>


<script>
// 토론방 위에 보여지는 효과 관련
import startLetter from './animation-view/start-letter.vue'


// 토론방 관련
import debateRoomSideComponent from './debateRoomSideComponent'
import debateRoomSideComponentAgree from './debateRoomSideComponentAgree'  // @@ 없앨거
import debateRoomCenterComponent from './debateRoomCenterComponent'        // @@ 없앨거
import detailSessionView from './detailSessionView'
import debateRoomVideo from './debateRoomVideo'


//  채팅
import chattingAll from './ChattingComponents/chatting-all'
import chattingTeam from './ChattingComponents/chatting-team'


// 하단바
import FooterTeam from './debateRoomFooter/FooterTeam'
import FooterModerator from './debateRoomFooter/FooterModerator'
import FooterAll from './debateRoomFooter/FooterAll'


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


const OPENVIDU_SERVER_URL = process.env.OPENVIDU_SERVER_URL;
const OPENVIDU_SERVER_SECRET = process.env.OPENVIDU_SERVER_SECRET;

export default {
    name: 'debateRoom',
    components: {
      // 토론방 위에 보여지는 효과 관련
        startLetter,

      // 토론방 관련
        debateRoomSideComponent,
        debateRoomCenterComponent,
        detailSessionView,
        debateRoomSideComponentAgree,
        debateRoomVideo,

      //  채팅
        chattingAll,
        chattingTeam,

      // 하단바
        FooterTeam,
        FooterModerator,
        FooterAll,

      // 메뉴 및 모달뷰
        callToModerator,
        UserOut,
        MessageFromTeam,
        UploadFile,
        LetTeamSpeak,
        RestTime,

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
        // 반응형 고려 화면 픽셀 데이터
            dCenterW: '',
            dCenterH: '',
            dSideW: '',
            dSideH: '',
            videoBox: '',
            chattBox: '',
            wValue075: '',

            callToMDView: '',
            callToMDBlnk: '',
            callToMDCt: '',
            callToMDInW: '',
            callToMDInH: '',
            callToMDINHC: '',
            modMenusLoc: '',

            // 센터 - 화면공유부분
            shareViewH: '',
            shareViewW: '',

            // 센터 - 타이머 부분
            dtcHeight: '',

            // 양쪽 비디오 blank
            vsiBlank: '',

          // 표준 비디오 사이즈
          centerVideoHeight : '',
          centerVideoWidth : '',


          // 사용자에 따른 boolean
            moderator: false,
            team: false,
            all: false,

          // 토론방 위에 보여지는 효과 관련
            startEvent: false,
            heartTen: false,
            heartfift: false,
            heartHund: false,
            restEvent: false,
            allHeartLeft: '',
            countingHeart :0,

          //  채팅
            chattTF: true,
            chattingAllView: false,
            chattingTeamView: false,

          // 하단바
            footerTeam: false,
            footerModerator: false,
            footerAll: false,

          // 메뉴 및 모달뷰
            restModal: false,
            imgTF:true,
            callToMdModal: false,
            message: false,
            file: false,
            out: false,
            menu: false,
            rest: false,
            options: [this.menu, this.out, this.message, this.file],
            modMenu: false,
            messageFrom: false,
            voteModalWidth: '',
            voteTime: 60,
            voteStatus: null,
            voteViewTF: false,
            voteTeam: false,
            voteAll: false,
            voteMod: false,
            timerTime:null,
            timerTeam:null,
            timeList:[], // 타이머 = 0: 시간(초), 1: 찬반 (찬1, 반0)

          // 비디오 관련 및 내부로직
            position : '',
        }
    },
    mounted() {
        // 화면 기본 사이즈 받아옴 => 채팅창 있는 화면 로드됨
        const wValue = document.body.clientWidth
        const wValue075 = document.body.clientWidth*0.75
        const hValue = document.body.clientHeight
        
        this.wValue075 = `${wValue075}px` // 메인화면 wrap (.debate-background)
        this.chattBox =  `${wValue*0.25}px` // 채팅창 (.chatting-box)
        this.footerWidth = `${wValue}px` // 하단바 (.debate-room-footer-class)

        //표준 비디오 사이즈
        const videoSize = document.body.clientWidth*0.75*0.3-20
        this.centerVideoWidth = `${videoSize}px`
        this.centerVideoHeight = `${videoSize*0.6}px`

        // 토론방 센터 (.videobox-center)
        this.dCenterW = `${wValue075*0.4-10}px`
        this.dCenterH = `${hValue*0.8}px`
        
        const debateTimer = (hValue - (videoSize*0.6))
        this.dtcHeight = `${debateTimer*0.1}px`

        // 토론방 화면공유 (.share-view)
        this.shareViewH = `${debateTimer*0.6-20}px`
        this.shareViewW = `${wValue075*0.4-40}px`

        // 토론방 양쪽 (.videobox-side)
        this.dSideW = `${wValue075*0.3-10}px`
        this.dSideH = `${hValue*0.8}px`
        this.vsiBlank = `${hValue*0.8*0.2}px`

        // 토론방 추가기능 모달창
        this.callToMDView = `${wValue075}px`
        this.callToMDBlnk = `${wValue075*0.3}px`
        this.callToMDCt = `${wValue075*0.4}px`
        this.callToMDInW = `${wValue075*0.4}px`
        this.callToMDInH = `${hValue*0.25}px`
        this.callToMDINHC = `${hValue*0.4}px`

        this.modMenusLoc = `${wValue*0.36}px`
        this.voteModalWidth = `${wValue075}px`

        this.allHeartLeft= `${document.body.clientWidth*0.5}px`

        window.addEventListener('resize', this.handleResizeHome);
    },
    methods: {
        handleResizeHome() {  // 화면 움직일때 조정 다시함
            if (this.chattTF === true) {    // 채팅창 열려있을때
                // 화면 기본 사이즈 받아옴
                const wValue = document.body.clientWidth
                const wValue075 = document.body.clientWidth*0.75
                const hValue = document.body.clientHeight
                
                this.wValue075 = `${wValue075}px` // 메인화면 wrap (.debate-background)
                this.chattBox =  `${wValue*0.25}px` // 채팅창 (.chatting-box)
                this.footerWidth = `${wValue}px` // 하단바 (.debate-room-footer-class)

                //표준 비디오 사이즈
                const videoSize = document.body.clientWidth*0.75*0.3-20
                this.centerVideoWidth = `${videoSize}px`
                this.centerVideoHeight = `${videoSize*0.6}px`

                // 토론방 센터 (.videobox-center)
                this.dCenterW = `${wValue075*0.4-10}px`
                this.dCenterH = `${hValue*0.8}px`
                
                const debateTimer = (hValue - (videoSize*0.6))
                this.dtcHeight = `${debateTimer*0.1}px`

                // 토론방 화면공유 (.share-view)
                this.shareViewH = `${debateTimer*0.6-20}px`
                this.shareViewW = `${wValue075*0.4-40}px`

                // 토론방 양쪽 (.videobox-side)
                this.dSideW = `${wValue075*0.3-10}px`
                this.dSideH = `${hValue*0.8}px`

                // 토론방 추가기능 모달창
                this.callToMDView = `${wValue075}px`
                this.callToMDBlnk = `${wValue075*0.3}px`
                this.callToMDCt = `${wValue075*0.4}px`
                this.callToMDInW = `${wValue075*0.4}px`
                this.callToMDInH = `${hValue*0.25}px`
                this.callToMDINHC = `${hValue*0.4}px`

                this.modMenusLoc = `${wValue*0.36}px`
                this.voteModalWidth = `${wValue075}px`

                this.allHeartLeft= `${document.body.clientWidth*0.5}px`
            }
            else {     // 채팅창 닫혀있을때
                const wValue = document.body.clientWidth
                const wValue075 = wValue  // 닫혀있으면 전체사이즈로 지정
                const hValue = document.body.clientHeight

                this.wValue075 = `${wValue075}px` // 메인화면 wrap (.debate-background)
                this.chattBox =  `${wValue*0.25}px` // 채팅창 (.chatting-box)
                this.footerWidth = `${wValue}px` // 하단바 (.debate-room-footer-class)

                //표준 비디오 사이즈
                const videoSize = document.body.clientWidth*0.75*0.3-20
                this.centerVideoWidth = `${videoSize}px`
                this.centerVideoHeight = `${videoSize*0.6}px`

                // 토론방 센터 (.videobox-center)
                this.dCenterW = `${wValue075*0.4-10}px`
                this.dCenterH = `${hValue*0.8}px`
                
                const debateTimer = (hValue - (videoSize*0.6))
                this.dtcHeight = `${debateTimer*0.1}px`

                // 토론방 화면공유 (.share-view)
                this.shareViewH = `${debateTimer*0.6-20}px`
                this.shareViewW = `${wValue075*0.4-40}px`

                // 토론방 양쪽 (.videobox-side)
                this.dSideW = `${wValue075*0.3-10}px`
                this.dSideH = `${hValue*0.8}px`

                // 토론방 추가기능 모달창
                this.callToMDView = `${wValue075}px`
                this.callToMDBlnk = `${wValue075*0.3}px`
                this.callToMDCt = `${wValue075*0.4}px`
                this.callToMDInW = `${wValue075*0.4}px`
                this.callToMDInH = `${hValue*0.25}px`
                this.callToMDINHC = `${hValue*0.4}px`

                this.modMenusLoc = `${wValue*0.36}px`
                this.voteModalWidth = `${wValue075}px`

                this.allHeartLeft= `${document.body.clientWidth*0.5}px`
            }
        },
        animation(option) {
            if (option === 'startEvent') { 
                // this.startEvent = true
                this.startEvent = !this.startEvent
                this.heartTen = false
                this.heartfift = false
                this.heartHund = false
                this.restEvent = false
                }
            else if (option === 'heartTen') {}
            else if (option === 'heartfift') {}
            else if (option === 'heartHund') {}
            else if (option === 'restEvent') {}
        },
        messageFromTeam() {
            this.callToMdModal = !this.callToMdModal
            this.messageFrom = !this.messageFrom
            this.menu = false
            this.out = false
            this.message = false
            this.file = false
            this.rest = false
        },
        EmitRest(timeRest) {
            this.restModal = true
            this.callToMdModal = false

            let time = timeRest;
            let min = "";
            let sec = "";
            let z = setInterval(function() {
            min = parseInt(time/60);
            sec = time%60;

            document.getElementById("restTimerDemo").innerHTML = min + "분" + sec + "초";
            time--;

            if (time < 0) {
                clearInterval(z);
                this.restModal = false
            }
            }, 1000);

        },
        EmitTime(Array) {
            this.timerTime = Array[0]*60
            this.timerTeam = Array[1]
            this.timeList = [this.timerTime, this.timerTeam]
            console.log(this.timeList)
            this.callToMdModal = false
        },
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
            }, 500);
            stripe.classList.add('animate');
        },
        voteView() {
            this.voteViewTF = !this.voteViewTF
        },
        risingHeart() {
            const stripe = document.getElementById('heart-div')
            setTimeout(() => {
                stripe.classList.remove('animate');
            }, 500);
            stripe.classList.add('animate');
            this.countingHeart += 1
            if(this.countingHeart === 5) {
                console.log('5개')
            }
        },
        
        moderatorView() {
            this.moderator = true,
            this.all = false,
            this.team = false,

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
            this.all = true,
            this.moderator = false,
            this.team = false,

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
            this.team = true,
            this.all = false,
            this.moderator = false,

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
                // 화면 기본 사이즈 받아옴
                const wValue = document.body.clientWidth
                const wValue075 = document.body.clientWidth*0.75
                const hValue = document.body.clientHeight
                
                this.wValue075 = `${wValue075}px` // 메인화면 wrap (.debate-background)
                this.chattBox =  `${wValue*0.25}px` // 채팅창 (.chatting-box)
                this.footerWidth = `${wValue}px` // 하단바 (.debate-room-footer-class)

                //표준 비디오 사이즈
                const videoSize = document.body.clientWidth*0.75*0.3-20
                this.centerVideoWidth = `${videoSize}px`
                this.centerVideoHeight = `${videoSize*0.6}px`

                // 토론방 센터 (.videobox-center)
                this.dCenterW = `${wValue075*0.4-10}px`
                this.dCenterH = `${hValue*0.8}px`
                
                const debateTimer = (hValue - (videoSize*0.6))
                this.dtcHeight = `${debateTimer*0.1}px`

                // 토론방 화면공유 (.share-view)
                // this.shareViewH = `${debateTimer*0.6-20}px`
                // this.shareViewW = `${wValue075*0.4-40}px`

                // 토론방 양쪽 (.videobox-side)
                this.dSideW = `${wValue075*0.3-10}px`
                this.dSideH = `${hValue*0.8}px`

                // 토론방 추가기능 모달창
                this.callToMDView = `${wValue075}px`
                this.callToMDBlnk = `${wValue075*0.3}px`
                this.callToMDCt = `${wValue075*0.4}px`
                this.callToMDInW = `${wValue075*0.4}px`
                this.callToMDInH = `${hValue*0.25}px`
                this.callToMDINHC = `${hValue*0.4}px`

                this.modMenusLoc = `${wValue*0.36}px`
                this.voteModalWidth = `${wValue075}px`

                this.allHeartLeft= `${document.body.clientWidth*0.5}px`
            }
            else {     // 채팅창 닫혀있을때
                const wValue = document.body.clientWidth
                const wValue075 = wValue  // 닫혀있으면 전체사이즈로 지정
                const hValue = document.body.clientHeight

                this.wValue075 = `${wValue075}px` // 메인화면 wrap (.debate-background)
                this.chattBox =  `${wValue*0.25}px` // 채팅창 (.chatting-box)
                this.footerWidth = `${wValue}px` // 하단바 (.debate-room-footer-class)

                //표준 비디오 사이즈
                const videoSize = document.body.clientWidth*0.75*0.3-20
                this.centerVideoWidth = `${videoSize}px`
                this.centerVideoHeight = `${videoSize*0.6}px`

                // 토론방 센터 (.videobox-center)
                this.dCenterW = `${wValue075*0.4-10}px`
                this.dCenterH = `${hValue*0.8}px`
                
                const debateTimer = (hValue - (videoSize*0.6))
                this.dtcHeight = `${debateTimer*0.1}px`

                // 토론방 화면공유 (.share-view)
                // this.shareViewH = `${debateTimer*0.6-20}px`
                // this.shareViewW = `${wValue075*0.4-40}px`

                // 토론방 양쪽 (.videobox-side)
                this.dSideW = `${wValue075*0.3-10}px`
                this.dSideH = `${hValue*0.8}px`

                // 토론방 추가기능 모달창
                this.callToMDView = `${wValue075}px`
                this.callToMDBlnk = `${wValue075*0.3}px`
                this.callToMDCt = `${wValue075*0.4}px`
                this.callToMDInW = `${wValue075*0.4}px`
                this.callToMDInH = `${hValue*0.25}px`
                this.callToMDINHC = `${hValue*0.4}px`

                this.modMenusLoc = `${wValue*0.36}px`
                this.voteModalWidth = `${wValue075}px`

                this.allHeartLeft= `${document.body.clientWidth*0.5}px`
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
                this.rest = false
                this.messageFrom = false
            }
            else if (option == 'out') {
                this.menu = false
                this.out = true
                this.message = false
                this.file = false
                this.rest = false
                this.messageFrom = false
            }
            else if (option == 'message') {
                this.menu = false
                this.out = false
                this.message = true
                this.file = false
                this.rest = false
                this.messageFrom = false
            }
            else if (option == 'file') {
                this.menu = false
                this.out = false
                this.message = false
                this.file = true
                this.rest = false
                this.messageFrom = false
            }
            else if (option == 'rest') {
                this.menu = false
                this.out = false
                this.message = false
                this.file = false
                this.rest = true
                this.messageFrom = false
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

.animation-control-btns {
    position: absolute;
    top: 50px;
    background-color: rgba(202, 88, 88, 0.534);
    height: 50px;
}
.animation-role-background {
    position: absolute;
    height: 100vh;
    width: var(--footer-width);
    display: flex;
    justify-content: center;
    align-items: center;
}
.modal-icon {
    display: flex;
    justify-content: end;
    color:white;
}
.modal-icon > i {
    position: relative;
    top: 20px;
    right: 20px;
    cursor: pointer;
}
#debate-room-body {
    overflow: hidden;
}
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
    width: 150px;
    height: 50px;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: rgb(0, 0, 0);
    border-radius: 10px;
    outline: 1px solid rgba(168, 168, 168, 0.753);
    color: rgb(182, 182, 182);
}
.vote-btn:hover {
    outline: 1px solid white;
    cursor: pointer;
    color: white;
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
    top: 100vh;
    left: var(--all-heart-left);
    position: absolute;
}

#heart-div {
  position: absolute;
  bottom: 0px;
  width: 0px;
  height: 0px;
  background: url("https://s3.us-east-2.amazonaws.com/upload-icon/uploads/icons/png/15721583221557740359-512.png") no-repeat;
  background-size: cover;
  left: 10px;
}
#clap-div {
  position: absolute;
  bottom: 0px;
  width: 0px;
  height: 0px;
    background: url("https://user-images.githubusercontent.com/87743473/183299200-727383c3-6ae0-4631-bf17-169720b9f480.png") no-repeat;
    background-size: cover;
  left: 10px;
}
#clap-div.animate {
    animation: bubble 1s linear;
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
        width: 10px;
        height: 10px;
        bottom: 0px;
        opacity: 1
    }
    90% {
        opacity: 0
    }
    100% {
        bottom: 1000px;
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
.call-to-moderator-inner-c {
    width: var(--call-to-md-in-width);
    height: var(--call-to-md-in-height-c);
    /* outline: solid 3px orange; */
}
.call-to-moderator-center {
    border-radius: 10px;
    background: rgba(0, 0, 0, 0.925);
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
    background-color: rgb(32, 32, 32);
    width: var(--chatt-box);
    height: 93vh;
    border-radius: 10px;
}
.debate-room-wrap {
    display: flex;
    /* background-color: rgba(146, 227, 153, 0.581); */
}
.videobox-side {
    height: var(--debate-box-side-height);
    width: var(--debate-box-side-width);
    overflow: hidden;
}
.vsi-blank {
    height: var(--vsi-blank);
}
.vsi-wrap {
    height: 100%;
    width: 100%;
    background-color: rgb(61, 255, 94);
    overflow: auto;
}
.videobox-side-inner {
    height: var(--center-video-height);
    width: var(--center-video-width);
    background-color: bisque;
    outline: 3px solid aqua;
}
.vsi-wrap::-webkit-scrollbar{width: 4px;}
.vsi-wrap::-webkit-scrollbar-thumb {
  background-color: rgba(39, 39, 39, 0.712);
    border-radius: 5px;
}
.videobox-center {
  height: var(--debate-box-center-height);
  width: var(--debate-box-center-width);
  color: black;
  /* background-color: rgba(121, 193, 255, 0.621); */
}
.moderator-video {
  display: flex;
  justify-content: center;
  align-items: center;
  height: var(--center-video-height);
}
.moderator-video-inner {
  height: var(--center-video-height);
  width: var(--center-video-width);
  background-color: bisque;
}

.debateroom-center-timer {
  /* background-color: rgb(119, 0, 255); */
  height: var(--dct-height);
}
.share-view-wrap {
    height: calc(var(--share-view-height) + 40px);
    display: flex;
    justify-content: center;
    align-items: center;
}
.share-view {
  height: var(--share-view-height);
  width: var(--share-view-width);
  background-color: rgb(56, 56, 56);
  border-radius: 10px;
}
</style>
