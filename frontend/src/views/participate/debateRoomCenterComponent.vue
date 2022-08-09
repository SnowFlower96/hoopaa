<template>
    <div class="debate-center-wrap" >
        <div class="debate-moderator" :style="customViewStyle"><div class="debate-moderator-inner" :style="customViewStyle">
            <user-video class="moderatorVideo" :stream-manager="room.host"/>

        </div>
    </div>
    <div class="debate-guague" :style="customViewStyle">
        <div class="debate-guague-inner" :style="customViewStyle">
            <span style="font-size: 25px;" v-if="!timeList[1]">반대팀</span>
            <span style="font-size: 25px;" v-if="timeList[0]">찬성팀</span>
            <div id="speaktimer">0분0초</div>
            <button v-if="moderator" @click="startTimer">시작하기</button>
        </div>
    </div>
    <div class="debate-content" :style="customViewStyle"><div class="debate-content-inner" :style="customViewStyle"></div></div>
    </div>
</template>

<script>
import axios from 'axios';
import UserVideo from '@/views/openvidu/UserVideo.vue';
import { mapState} from 'vuex';
axios.defaults.headers.post['Content-Type'] = 'application/json';

const OPENVIDU_SERVER_URL = process.env.OPENVIDU_SERVER_URL;
const OPENVIDU_SERVER_SECRET = process.env.OPENVIDU_SERVER_SECRET;

export default {
    data() {
        return {
            timerMin: this.timeList[0]/60,
            timerSec: 0
        }
    },
    props: [
        'timeList',
        'moderator',
        'all',
        'team',
    ],
    components : {
        UserVideo,
    },
    computed : {
      ...mapState(["user","room"]),
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
            centerVideoHeight : '',
            centerVideoWidth : '',

            dbModerator: '',
            dbGuague: '',
            dbContent:'',

            dbGuagueInnerWidth: '',
            dbContentInnerWidth: '',
            dbGuagueInnerHeight: '',
            dbContentInnerHeight: '',
            resol_w: 300,
            resol_h: 200
        }
    },
    created () {

    },
    mounted() {
        console.log(this.timeList)
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
        startTimer() {
            let time = this.timeList[0];
            let min = this.timerMin;
            let sec = this.timerSec;
            let x = setInterval(function() {
            min = parseInt(time/60);
            sec = time%60;

            document.getElementById("speaktimer").innerHTML = min + "분" + sec + "초";
            time--;

            if (time < 0) {
                clearInterval(x);
                document.getElementById("speaktimer").innerHTML = "0분 0초";
            }
            }, 1000);

        },
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
         leaveSession() {
           console.log("leaveSession")
            // --- Leave the session by calling 'disconnect' method over the Session object ---
            if (this.session) this.session.disconnect();
            this.session = undefined;
            this.mainStreamManager = undefined;
            this.publisher = undefined;
            this.subscribers = [];
            this.OV = undefined;
            window.removeEventListener('beforeunload', this.leaveSession);
            this.$router.push("/list");
        },
    },
    watched: {
    }
}
</script>

<style>
#speaktimer {
    font-size: 45px;
}
.moderatorVideo > #local-video-undefined {
    height: var(--center-video-height);
    width: var(--center-video-width);
}
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
   height: 300px;
   width: 300px;
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
    /* background-color: rgb(23, 139, 32); */
    display: flex;
    justify-content: space-evenly;
    align-items: center;
    color: white;
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
    background-color: rgb(44, 44, 44);
    border-radius: 10px;
}
</style>

