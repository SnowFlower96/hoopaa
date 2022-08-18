<template id="debate-room-body">
<!-- 토론진행중 애니메이션 구성 -->
<div  class="animation-role-background" v-if="animationBG" :style="customCaroselStyle">
    <start-letter id="arb" v-if="startEvent"></start-letter>
    <heart-hund :props-heart="propsHeart" id="arb" v-if="heartHund"></heart-hund>
</div>
<!-- 토론진행중 애니메이션 구성 -->
<!-- 쉬는시간 모달 -->
<div class="vote-modal-container displayFlex" v-if="restModal" :style="customCaroselStyle">
    <div>
      <div class="rest-timer displayFlex">
          <p id="restTimerDemo">0분0초</p>
      </div>
      <div class="rest-animation">
          <rest-time-event v-if="restEvent"></rest-time-event>
      </div>
    </div>
</div>
<!-- 쉬는시간 모달 -->

<!-- 0818 임시버튼 또 추가 -->
<!-- <div style="position: absolute; top: 10%;"> <button @click="userPenalty">유저패널티 모달창</button></div> -->
<!-- 0818 임시버튼 또 추가 -->

<!-- 사회자 시작버튼 -->
<div class="mod-start-btn-bg">
    <div v-if="session.sessionId == user.id && modStart == true" @click="animationSignal('startEvent')">토론 시작하기</div>
</div>
<!-- 사회자 시작버튼 -->


<!-- 뷰바꾸는 임시버튼 -->
<div v-if="imgTF" class="startImg">
<!-- <img v-if="imgTF" class="startImg" :src="require(`@/assets/images/start.png`)" alt=""> -->
    <!-- <div id="demo">넨</div> -->
</div>
<!-- 뷰바꾸는 임시버튼 -->


    <!-- 투표 받는 창 -->
    <div class="vote-modal-container displayFlex" v-if="voteViewTF" :style="customCaroselStyle">
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
                                <router-link style="text-decoration: none;" to="/endDebate"><div class="sub-vote-btn">결과화면으로</div></router-link>
                            </div>
                        </div>
                    </div>
                    <div v-if="voteAll" class="all-view">
                        <!-- 방청객  => 1. 투표하기 뷰 -->
                            <div class="all-view-wrap" v-if="allVoteView">
                                <p>당신의 최종 의견을 투표하세요</p>
                                <p style="color: tomato;">제한시간안에 투표를 꼭 해주시기 바랍니다</p>
                                <div class="vote-btn-wrap">
                                    <div :class="{'vote-btn-selected': voteStatus === 1, 'vote-btn': voteStatus === 0}" @click="voteFunction(1)">찬성</div>
                                    <div :class="{'vote-btn-selected': voteStatus === 0, 'vote-btn': voteStatus === 1}" @click="voteFunction(0)">반대</div>
                                </div>
                                <p>시간안에 제출하지 않으면 무효표 처리됩니다.</p>
                                <div class="displayFlex">
                                    <div class="sub-vote-btn" @click="submitVote('vote')">제출</div>
                                </div>
                            </div>
                        <!-- 방청객  => 투표하기 뷰 -->

                        <!-- 방청객 => 2. MVP 선택 뷰 -->
                            <div class="all-view-wrap" v-if="selMVPView">
                                <div style="font-size: 20px;">이번 토론의 MVP를 선택해주세요</div>
                                <div class="mvp-list displayFlex" style="flex-wrap: wrap;">
                                    <div class="vote-btn" v-for="(item , index) in pannelList" :key="index"  @click="voteFunction(item)">{{item}}</div>
                                </div>
                                <div class="displayFlex">
                                    <div class="sub-vote-btn" @click="submitVote('mvp')">제출</div>
                                </div>
                            </div>
                        <!-- 방청객 => MVP 선택 뷰 -->

                        <!-- 방청객 => 3. 집계 대기 뷰 -->
                            <div class="all-view-wrap" v-if="waitVoteView">
                                <p>잠시 대기해주세요</p>
                                <p>다른 방청객들이 대기중입니다 ..</p>
                            </div>
                        <!-- 방청객 => 집계 대기 뷰 -->
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
                <call-to-moderator v-if="message" @sendMessage="toModerator"></call-to-moderator>

                <user-out v-if="out" :list="penaltyList" @sendPenalty="sendPenalty"></user-out>

                <message-from-team v-if="messageFrom" :from="from" :message="toModeratorMessage" @close-modal="offCallModal"></message-from-team>

                <upload-file v-if="file" @publishScreenShare="publishScreenShare"></upload-file>

                <let-team-speak
                v-if="menu"
                @emit-time="EmitTime"
                @sendAudioMute="audioMute"
                ></let-team-speak>

                <rest-time
                v-if="rest"
                @emit-rest="EmitRest"
                @sendSebuSession="sendSession"
                ></rest-time>

                <div v-if="penaltyView" class="penalty-view displayFlex">
                  <div>
                    <div style="font-size:50px; color:white; text-align:center;">🚨경고🚨</div>
                    <div style="font-size:20px; color:white; text-align:center;">올바른 태도로 토론에 참여해주세요</div>
                    <div class="penalty-btn-wrap displayFlex">
                      <div @click="offpenaltyView" class="penalty-btn displayFlex">확인</div>
                    </div>
                  </div>
                </div>

                <div v-if="viewCodeMd" class="penalty-view displayFlex">
                  <div>
                    <div style="color:white; font-size:20px; margin-bottom: 70px;">입장코드 : {{roomCode}}</div>
                    <div @click="offpenaltyView" class="penalty-btn displayFlex">확인</div>
                  </div>
                </div>
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
                                        <!-- <debate-room-video v-for="(item, index) in agree" :key="index" :stream="item.data" class="debate-room-side-vido"></debate-room-video> -->
                                        <debate-room-video class="debate-room-side-vido" v-for="(item, index) in agree" :key="index" :stream="item.data"></debate-room-video>
                                    </div>
                                </div>
                              <!-- 토론방 왼쪽 -->

                              <!-- 토론방 센터 -->
                                <div class="videobox-center" :style="customCaroselStyle">

                                  <div class="moderator-video">
                                    <div class="moderator-video-inner" :style="customCaroselStyle">
                                      <!-- 사회자 비디오 하나 들어갈 곳 -->
                                      <debate-room-video :stream="host"></debate-room-video>
                                    </div>
                                  </div>

                                  <div class="debateroom-center-timer" :style="customCaroselStyle"> <!-- 이부분 나중에 위로 가져오기-->
                                      <debate-room-center-component
                                      :time-list="timeList"
                                      ref="debateRoomSideComponent"
                                      :moderator="moderator"
                                      :all="all"
                                      :team="team"
                                      :timer-min="timerMin"
                                      ></debate-room-center-component>
                                  </div>

                                  <div class="share-view-wrap" :style="customCaroselStyle">
                                    <div class="share-view">
                                        <!-- 화면공유 여기에 넣으면 됨 -->
                                        <!-- <user-video :stream-manager="publisherScreen"></user-video> -->
				                                <div v-for="(sub, index) in subscribersScreen" :key="index" >
					                                <user-video :stream-manager="sub.data" ></user-video>
				                                </div>
                                    </div>
                                  </div>

                                </div>
                              <!-- 토론방 센터 -->

                              <!-- 토론방 오른쪽 -->
                                <div class="videobox-side" :style="customCaroselStyle">
                                    <div class="vsi-blank"></div>
                                    <div class="vsi-wrap">
                                        <!-- 여기에 for문으로 비디오 넣어보면 됨 -->
                                        <!-- <debate-room-video v-for="(item, index) in disagree" :key="index" :stream="item.data" class="debate-room-side-vido"></debate-room-video> -->
                                        <debate-room-video class="debate-room-side-vido" v-for="(item, index) in disagree" :key="index" :stream="item.data"></debate-room-video>
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
                        <chatting-all v-if="chattingAllView" :messagesAll="messagesAll" @close-chat="changeChatView" @chat-all="sendAllMessage"></chatting-all>
                        <chatting-team v-if="chattingTeamView && position=='agree'" :messagesTeam="messagesAgree" @close-chat="changeChatView" @chat-team="sendTeamMessage"></chatting-team>
                        <chatting-team v-if="chattingTeamView && position!='agree'" :messagesTeam="messagesDisagree" @close-chat="changeChatView" @chat-team="sendTeamMessage"></chatting-team>
                    </div>
                <!-- 채팅창 -->


            </div>
        <!-- 메인화면 + 채팅 -->


        <!-- 사회자 footer에서 나오는 메뉴 -->
            <div class="moderator-menus" v-if="modMenu && moderator" :style="customCaroselStyle">
                <p @click="viewCode">입장코드 확인</p>
                <p @click="EmitcallModal('menu')">패널 발언권 부여</p>
                <p @click="EmitcallModal('rest')">쉬는시간 부여</p>
                <p @click="voteVisible">투표 보내기</p>
            </div>
        <!-- 사회자 footer에서 나오는 메뉴 -->


            <div class="leave-session displayFlex" @click="leaveSessionButton">토론 나가기</div>
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
</template>


<script>
// 토론방 위에 보여지는 효과 관련
import startLetter from './animation-view/start-letter.vue'
import heartHund from './animation-view/heartHund.vue'
import restTimeEvent from './animation-view/rest-time-event.vue'


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
import moment from 'moment';
import router from '../../common/lib/vue-router'


const OPENVIDU_SERVER_URL = "https://hoopaa.site";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";


export default {
    name: 'debateRoom',
    components: {
      // 토론방 위에 보여지는 효과 관련
        startLetter,
        heartHund,
        restTimeEvent,

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
      // 영상
        UserVideo,
    },
    async created() {
      await this.joinSession();
      if (this.session.sessionId == this.user.id) {
        this.moderatorView();
      } else if (this.position == 'agree' || this.position == 'disagree') {
        this.teamView();
      } else {
        this.allView();
      }
  },
    computed : {
      ...mapState(["user", "position", "tempToken", "roomCode"]),
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
            animationBG: false,
            startEvent: false,
            heartTen: false,
            heartfift: false,
            heartHund: false,
            restEvent: false,
            allHeartLeft: '',
            countingHeart :0,
            propsHeart: 1,
            modStart: true,

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
            voteStatus: 1,
            voteViewTF: false,
            voteTeam: false,
            voteAll: false,
            voteMod: false,
            timerTime:null,
            timerTeam:null,
            timeList:[], // 타이머 = 0: 시간(초), 1: 찬반 (찬1, 반0)
            timerMin: 0,
            penaltyView: false,
            viewCodeMd:false,

            // 토론끝나고 방청잭 투표뷰 3개
            allVoteView: true,
            selMVPView: false,
            waitVoteView: false,

          // 비디오 관련 및 내부로직
            OV: undefined,
            session: undefined,
            host: undefined,
            agree: [],
            disagree: [],
            publisher : undefined,

          // 화면 공유
            OVScreen : undefined,
			      sessionScreen: undefined,
			      publisherScreen: undefined,
			      subscribersScreen:[],
            screensharing: false,

          //채팅
            messagesAll:[],
            messagesAgree:[],
            messagesDisagree:[],

          // 투표결과
            voteData : {
              sessionID : '',
              kingUserID : '',
              vote : '',
            },
            pannelList : [],
            penaltyList : [],
          // 룸id
            roomId : '',
          // 팀 > 사회자 메시지
            toModeratorMessage : '',
            from : '',
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
      viewCode() {
        this.callToMdModal = true
        this.viewCodeMd = true
        this.penaltyView = false
        this.menu = false
        this.out = false
        this.message = false
        this.file = false
        this.rest = false
        this.messageFrom = false
      },
      offviewCode() {
        this.callToMdModal = false
        this.viewCodeMd = false
      },
      offpenaltyView() {
        this.callToMdModal = false
        this.penaltyView = false
      },
      userPenalty() {
        this.callToMdModal = true
        this.penaltyView = true
        this.viewCodeMd = false
        this.menu = false
        this.out = false
        this.message = false
        this.file = false
        this.rest = false
        this.messageFrom = false
      },
        submitVote(option) {
            if(option === 'vote') {
                if(this.voteStatus == 1) {
                  this.voteData.vote = 'agree'
                } else if (this.voteStatus == 0) {
                  this.voteData.vote = 'disagree'
                }
                this.allVoteView = false
                this.selMVPView = true
                this.waitVoteView =  false
            }
            else if (option === 'mvp') {
                let kingId = '';
                for (var i in this.agree) {
                  if (this.agree[i].nnm == this.voteStatus) kingId = this.agree[i].id
                }
                 for (var i in this.disagree) {
                  if (this.disagree[i].nnm == this.voteStatus) kingId = this.disagree[i].id
                }
                this.voteData.kingUserID = kingId;
                this.voteData.sessionID = this.session.sessionId;
                this.$store.dispatch('voteFinal', this.voteData);
                this.allVoteView = false
                this.selMVPView = false
                this.waitVoteView = true
            }
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

          let sub = {
              id : clientData[0],
              stream : 'subscriber',
              data : subscriber,
              nnm : clientData[2]
          };
          if (clientData[0] == this.session.sessionId) {

            this.host = subscriber;
          } else if (clientData[1] == "agree") {

            this.agree.push(sub);
          } else if (clientData[1] == "disagree") {

            this.disagree.push(sub);
          }
        }

      });

      // On every Stream destroyed...
      // TODO
      this.session.on("streamDestroyed", ({stream}) => {

        for (var i = 0; i < this.agree.length; i++) {
          if (this.agree[i].data == stream.streamManager) {
          this.agree.splice(i, 1);
        }
        }

        for (var i = 0; i < this.disagree.length; i++) {
          if (this.disagree[i].data == stream.streamManager) {
          this.disagree.splice(i, 1);
        }
        }

      });

      // On every asynchronous exception...
      this.session.on("exception", ({ exception }) => {

      });

      // Hearing Signal
      // 스크린 signal
      this.session.on('signal:Unpublish-Screen', (event) => {
        this.subscribersScreen.splice(0, 1);
      })

      // 세부세션 signal
     this.session.on('signal:Go-SebuSession', (event) => {
      if (this.position == 'agree' || this.position == 'disagree') {
        var connectionId = event.data.split('%')[0]
        var token = event.data.split('%')[1]
        var time = event.data.split('%')[2]

        var connection = this.session.connection
        var sessionId = this.session.sessionId
        if (connection.connectionId == connectionId) {
          this.$store.commit("CREATE_TEMP_SUB_TOKEN", token);
          this.leaveSession()
          if (this.position == 'agree') {
            this.$router.push('/detailSessionView?' + sessionId + '_' + 'agree' + '?time=' + time)
          } else if (this.position == 'disagree') {
            this.$router.push('/detailSessionView?' + sessionId + '_' + 'disagree' + '?time=' + time)
          }
        }
      }
    });

      // 발언권 signal
      this.session.on('signal:Set-Audio', (event) => {
        if (this.session.sessionId != this.user.id) {
        if (event.data == 'agree') {
          if (this.position == 'agree') {
            this.publisher.publishAudio(true);
          } else this.publisher.publishAudio(false);
        } else if (event.data == 'disagree') {
          if (this.position == 'disagree') {
            this.publisher.publishAudio(true);
          } else this.publisher.publishAudio(false);
        }
        }
    });
    // 투표시작 signal
      this.session.on('signal:Start-Vote', (event) => {
        if (this.voteTeam || this.voteAll ) {
          this.voteView();
          this.pannelList = event.data.split('&')[0].split(',');
          this.roomId = event.data.split('&')[1];
          console.log(this.roomId + '222222222')
        }
      })
    // 팀 > 사회자 메시지 signal
      this.session.on('signal:Team-To-Moderator', (event) => {
        if (this.session.sessionId == this.user.id) {
          this.toModeratorMessage = event.data.split('/')[0]
          this.from = event.data.split('/')[1]
          this.messageFromTeam();
        }
      })
    // 애니메이션 시그널

      this.session.on('signal:Animation', (event) => {
        if (event.data == 'startEvent') {
          this.animation(event.data)
        } else if (event.data == 'heartHund') {
          if (this.position) {
            this.animation(event.data)
          }
        }
      })

    // 휴식 애니메이션
      this.session.on('signal:Rest-Time', (event) => {
        if (this.position == 'audience') {
          this.EmitRest(event.data)
        }
      })
    // 주의 signal
      this.session.on('signal:Send-Penalty', (event) => {
        if (this.user.id == event.data) {
          this.userPenalty();
        }
      })

    // end signal
      this.session.on('signal:The-End', (event) => {
        if (this.session.sessionId == this.user.id) {
          this.$store.dispatch("closeRoom")
        }
        this.$router.push('/endDebate?' + this.roomId)
      })

      this.session.on("signal:chat-all",(event)=>{

      let eventData = JSON.parse(event.data);
      let data = new Object()
      let time = new Date()
      data.writer = eventData.writer
      data.message = eventData.content
      data.time = moment(time).format('HH:mm')

      this.messagesAll.push(data)

    } )

    this.session.on("signal:chat-agree",(event)=>{
      let eventData = JSON.parse(event.data);
      let data = new Object()
      let time = new Date()
      data.writer = eventData.writer
      data.message = eventData.content
      data.time = moment(time).format('HH:mm')
      this.messagesAgree.push(data)

    } )

    this.session.on("signal:chat-disagree",(event)=>{
     let eventData = JSON.parse(event.data);
      let data = new Object()
      let time = new Date()
      data.writer = eventData.writer
      data.message = eventData.content
      data.time = moment(time).format('HH:mm')
      this.messagesDisagree.push(data)

    } )

      await this.session
        .connect(token, { clientData: this.user.id + "/" + this.position + "/" + this.user.nnm })
        .then(() => {
          let publisher = undefined
          if (this.session.sessionId == this.user.id) {
              publisher = this.OV.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not
              resolution: "680x480", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: false // Whether to mirror your local video or not
            });
          } else {
              publisher = this.OV.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: false, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not
              resolution: "680x480", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: false // Whether to mirror your local video or not
            });
          }
          this.publisher = publisher;
          let sub = {
            id : this.user.id,
            stream : 'publisher',
            data : publisher,
            nnm : this.user.nnm
        };
          if (this.user.id == this.session.sessionId) {
            this.host = publisher;

          } else if (this.position == "agree") {

            this.agree.push(sub);

          } else if (this.position == "disagree") {
            this.disagree.push(sub);

          }

          this.session.publish(publisher);
        })
        .catch(error => {
          console.log(
            "There was an error connecting to the session:",
            error.code,
            error.message
          );
        });

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

			});

      this.sessionScreen.on("streamDestroyed", ({stream}) => {

        for (var i = 0; i < this.subscribersScreen.length; i++) {
          if (this.subscribersScreen[i].data == stream.streamManager) {
            this.subscribersScreen.splice(i, 1);

          }
        }

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
      this.agree = undefined;
      this.disagree = undefined;
			this.subscribersScreen = [];
			this.OV= undefined;
			this.OVScreen = undefined;

			if(this.screensharing)
				this.screensharing=false;

			window.removeEventListener('beforeunload', this.leaveSession);

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

			let publisherScreen = this.OVScreen.initPublisher("container-screens", {videoSource: "screen"});

			publisherScreen.once('accessAllowed', () => {
		this.screensharing = true;
		// It is very important to define what to do when the stream ends.
		publisherScreen.stream.getMediaStream().getVideoTracks()[0].addEventListener('ended', () => {
			console.log('User pressed the "Stop sharing" button');
			this.sessionScreen.unpublish(publisherScreen);
      this.unpublishScreen()
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

        handleResizeHome() {  // 화면 움직일때 조정 다시함
            if (this.chattTF === true) {    // 채팅창 열려있을때
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
            }
        },
        animation(option) {
            this.animationBG = !this.animationBG
            if (option === 'startEvent') {
                const startSound = new Audio("https://drive.google.com/uc?export=download&id=1SCuIo3Ds1tU3RBaO-JljNA-liMFASoLB");
                startSound.play();
                // this.startEvent = true
                this.startEvent = !this.startEvent
                this.heartTen = false
                this.heartfift = false
                this.heartHund = false
                this.restEvent = false
                if (this.session.sessionId == this.user.id) {
                  this.$store.dispatch("roomStart", this.session.sessionId)
                }
                this.modStart = false
                setTimeout(() => {
                  this.startEvent = false
                  this.animationBG = false
                }, 5000)
            }
            else if (option === 'heartHund') {
                this.startEvent = false
                this.heartTen = false
                this.heartfift = false
                this.heartHund = !this.heartHund
                this.restEvent = false
            }
        },
        messageFromTeam() {
            this.callToMdModal = !this.callToMdModal
            this.messageFrom = !this.messageFrom
            this.viewCodeMd = false
            this.menu = false
            this.out = false
            this.message = false
            this.file = false
            this.rest = false
        },
        EmitRest(timeRest) {
            if (this.session.sessionId == this.user.id) {
               this.session.signal({
                data : timeRest,
                to : [],
                type : 'Rest-Time'
              })
            }
            const restSound = new Audio("https://drive.google.com/uc?export=download&id=1R8_KNwIEBS_LpkCjazOjPZrk4jz4F2cM");
            restSound.play();

            this.animationBG = true
            this.restEvent = true
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

            // if (time < 0) {
            //     clearInterval(z);
            // }
        }, 1000);
        setTimeout(() => {
          this.closeSession(this.session.sessionId)
          this.rest = false
          this.animationBG = false
          this.restEvent = false
          this.restModal = false
          clearInterval(z);
          restSound.pause();
        }, (timeRest*1000) + 2000)


        },
        EmitTime(Array) {
            this.timerTime = Array[0]*60
            this.timerTeam = Array[1]
            this.timeList = [this.timerTime, this.timerTeam]

            this.callToMdModal = false
            this.timerMin = Array[0]
        },
        voteFunction(status) {
            this.voteStatus = status
        },
        async voteVisible() {
            this.voteViewTF = !this.voteViewTF
            this.startVote();
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
            setTimeout(this.theEnd,63000)

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
            this.countingHeart += 1
            if(this.countingHeart % 50 === 0) {
                    this.propsHeart = this.countingHeart /50
                    this.animationBG = true
                    this.heartHund = true
                const x = setTimeout(() => {
                    this.animationBG = false
                    this.heartHund = false
                }, 2000)
            }
        },
        voteView() {
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
        },
        risingHeart() {
            const stripe = document.getElementById('heart-div')
            setTimeout(() => {
                stripe.classList.remove('animate');
            }, 500);
            stripe.classList.add('animate');
            this.countingHeart += 1
            if(this.countingHeart % 50 === 0) {
                    this.propsHeart = this.countingHeart /50
                    this.animationBG = true
                    this.heartHund = true
                const x = setTimeout(() => {
                    this.animationBG = false
                    this.heartHund = false
                }, 2000)
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
            }
        },

        //채팅 추가
        sendAllMessage(message){
          if(this.user.nnm==null){
              var messageData = {
              writer : this.user,
              content: message
            }
          }else{
            var messageData = {
            writer : this.user.nnm,
            content: message
            }
          }

          this.session.signal({
            type: "chat-all",
            data:JSON.stringify(messageData),
            to:[]
          })
          // this.messagesAll.push(message)
        },
        sendTeamMessage(message){
          var messageData = {
            writer : this.user.nnm,
            content: message
          }
          if(this.position ==="agree"){
            this.session.signal({
              type : "chat-agree",
              data : JSON.stringify(messageData),
              to : []
            })
          }else{
            this.session.signal({
              type : "chat-disagree",
              data : JSON.stringify(messageData),
              to : []
            })
          }

        },

        offCallModal() {
            this.callToMdModal = false
            this.messageFrom = false
        },
        // Emit 함수를 하나로 하고 그 안에서 분기처리하기
        EmitcallModal(option) {

            this.callToMdModal = !this.callToMdModal

            if (option == 'menu') {
                this.menu = true
                this.out = false
                this.message = false
                this.file = false
                this.rest = false
                this.messageFrom = false
                this.viewCodeMd = false
            }
            else if (option == 'out') {
                this.menu = false
                this.out = true
                this.message = false
                this.file = false
                this.rest = false
                this.messageFrom = false
                this.viewCodeMd = false
                this.makeList();
            }
            else if (option == 'message') {
                this.menu = false
                this.out = false
                this.message = true
                this.file = false
                this.rest = false
                this.messageFrom = false
                this.viewCodeMd = false
            }
            else if (option == 'file') {
                this.menu = false
                this.out = false
                this.message = false
                this.file = true
                this.rest = false
                this.messageFrom = false
                this.viewCodeMd = false
            }
            else if (option == 'rest') {
                this.animationBG = !this.animationBG
                this.menu = false
                this.out = false
                this.message = false
                this.file = false
                this.rest = true
                this.messageFrom = false
                this.startEvent = false
                this.heartTen = false
                this.heartfift = false
                this.viewCodeMd = false
                this.heartHund = false
                this.restEvent = !this.restEvent
            }
        },

        // 세부세션 보내기 시그널
        async sendSession(time) {
          let agreeArr = [];
          let disagreeArr = [];
          let index = "/room/session/" + this.session.sessionId;
          await this.$store.dispatch("makeSessionRoom", index).then((response) => {
          let data = JSON.parse(response.data.json)
          console.log(data)
          for (var key in data) {

              this.sendSessionFunc(data[key].connectionID, data[key].token, time)
          }
        })

    },
    // 세부세션 보내기 시그널 함수
     sendSessionFunc(connectionId, token, time) {
       this.session.signal({
          data: connectionId + '%' + token + '%' + time ,
          to: [],
          type: 'Go-SebuSession'
        });
     },

    // 찬성 반대 connectId 얻기
    async getAgreePosition () {
      let result = [];
      await this.$store.dispatch("getConnectionAgree",this.session.sessionId).then((response) => {
        let data = JSON.parse(response.data.json)
        for (var key in data) {
          result.push(data[key])
        }
      })

        return result;
    },
    async getDisagreePosition() {
        let result = [];
      await this.$store.dispatch("getConnectionDisagree",this.session.sessionId).then((response) => {
        let data = JSON.parse(response.data.json)
        for (var key in data) {
          result.push(data[key])
        }
      })

        return result.data;
    },
    // 신고 리스트 만들기
    makeList() {
      this.$store.dispatch("getPanels").then((res) => {
        var data = JSON.parse(res.data.json)
        console.log(data)
        for (var key in data) {
        this.penaltyList.push({id: key, nnm:data[key]})
      }
      })
      console.log(this.penaltyList)
    },
    // 음소거 컨트롤 시그널
    async audioMute(status) {
      if (status == 1) {
        this.sendAudioSignal('agree')
      } else if (status == 0) {
        this.sendAudioSignal('disagree')
      }
    },
    sendAudioSignal(order) {
      this.session.signal({
        data: order,
        to: [],
        type: 'Set-Audio'
      });
    },

    // 투표시작 시그널 보내기
    startVote() {
      for (var i in this.agree) {
        this.pannelList.push(this.agree[i].nnm)
      }
      for (var i in this.disagree) {
        this.pannelList.push(this.disagree[i].nnm)
      }

      this.$store.dispatch("voteStart", this.session.sessionId).then((res) =>{
        this.roomId = res.data.response
        this.session.signal({
          data : this.pannelList.toString() + '&' + res.data.response,
          to : [],
          type : 'Start-Vote'
        })
      })
    },

    // 최종화면으로 보내기
    theEnd() {
      this.session.signal({
        data : 'the end',
        to : [],
        type : 'The-End'
      })

    },

    // 세부세션 닫기
    closeSession(sessionId) {
      this.$store.dispatch("closeSession", sessionId );
    },
    // 팀 > 사회자 메시지
    toModerator(message) {
      this.session.signal({
        data : message + '/' + this.position,
        to : [],
        type : 'Team-To-Moderator'
      })
      this.offCallModal();
    },

    // 애니메이션 시그널
    animationSignal(option) {
      this.session.signal({
        data : option,
        to : [],
        type : 'Animation'
      })
    },
    // 주의 보내기
    sendPenalty(id) {
      this.session.signal({
        data : id,
        to : [],
        type : 'Send-Penalty'
      })
      this.out = false
    },

    // screen 삭제
    unpublishScreen() {
      this.session.signal({
        data : 'unpublish',
        to : [],
        type : 'Unpublish-Screen'
      })
    },

    // 나가기 버튼
    leaveSessionButton() {
      this.leaveSession();
      this.$router.push('/')
    }
    }
  }

</script>

<style>
.leave-session {
  right: 0;
  position: absolute;
  color: rgb(132, 132, 132);
  font-size: 15px;
  width: 100px;
  height: 50px;
  border-radius: 10px;
}
.leave-session:hover {
  color: white;
  cursor: pointer;
}
.mvp-list {
    width: 300px;
    height: 200px;
    margin: 10px;
    border-radius: 10px;
    outline: 1px solid rgba(168, 168, 168, 0.753);
    /* background-color: beige; */
    overflow: auto;
}
.mvp-list::-webkit-scrollbar{width: 4px;}
.mvp-list::-webkit-scrollbar-thumb {
  background-color: rgba(39, 39, 39, 0.712);
    border-radius: 5px;
}
.displayFlex {
    display: flex;
    justify-content: center;
    align-items: center;
}
.animation-role-background {
    position: absolute;
    height: 90vh;
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
    height: 25%;
    /* background: blue; */
    text-align: center;
    display: flex;
    justify-content: center;
    align-items: center;
}
.mod-start-btn-bg {
  position: absolute;
  top: 10%;
  left: 50%;
}
.mod-start-btn-bg > div {
  width: 130px;
  height: 50px;
  border-radius: 10px;
  font-size: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  outline: rgb(141, 141, 141) 1px solid;
  color: rgb(141, 141, 141);
}
.mod-start-btn-bg > div:hover {
  outline: white 1px solid;
  color: white;
  cursor: pointer;
}
.sub-vote-btn {
    font-size: 25px;
    margin: 10px;
    width: 170px;
    height: 50px;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: rgb(0, 0, 0);
    border-radius: 10px;
    outline: 1px solid rgba(168, 168, 168, 0.753);
    color: rgb(182, 182, 182);
}
.sub-vote-btn:hover {
    outline: 1px solid white;
    cursor: pointer;
    color: white;
}
.vote-btn {
    margin: 10px;
    width: 100px;
    height: 40px;
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
.vote-btn-selected {
    margin: 10px;
    width: 100px;
    height: 40px;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: rgba(120, 120, 120, 0.521);
    border-radius: 10px;
    outline: 3px solid rgba(255, 255, 255, 0.753);
    color: rgb(255, 255, 255);
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
    height: 65%;
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
    background-color: rgba(54, 54, 54, 0.699);
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
.penalty-view {
  height: 90%;

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
.penalty-btn-wrap {
  margin-top: 25px;
}
.penalty-btn {
  width: 100px;
  height: 50px;
  border-radius: 10px;
  outline: 1px rgb(145, 145, 145) solid;
  color:rgb(145, 145, 145);
  font-size: 30px;
}
.penalty-btn:hover {
  outline: 1px white solid;
  color:white;
  cursor: pointer;
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
    height: calc(100% - var(--vsi-blank));
    width: 100%;
    /* background-color: rgb(61, 255, 94); */
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
  background-color: rgba(102, 102, 102, 0.853);
    border-radius: 5px;
}
.debate-room-side-vido {
  margin-top:10px;
  margin-bottom: 10px;
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
  /* background-color: aquamarine; */
}

.debateroom-center-timer {
  display: flex;
  justify-content: center;
  align-items: flex-end;
  /* background-color: rgb(119, 0, 255); */
  height: calc(var(--dct-height) + 10px);
}
.share-view-wrap {
    /* background-color: aquamarine; */
    height: calc(var(--share-view-height) + 20px);
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
.rest-timer > p{
  position: absolute;
  left: 43%;
  top: 5%;
  color: white;
  font-size: 30px;
}
</style>
