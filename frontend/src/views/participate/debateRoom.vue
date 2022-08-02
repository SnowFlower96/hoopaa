<template>
<img v-if="imgTF" class="startImg" :src="require(`@/assets/images/start.png`)" alt="">
    <div class="debate-backcolor">
        <div class="video-chatt-wrap">
            <div class="debate-background">
                <div class="debate-room-wrap">
                    <div class="videobox-side" :style="customCaroselStyle">
                        <debate-room-side-component></debate-room-side-component>
                    </div>

                    <div class="videobox-center" :style="customCaroselStyle">
                        <debate-room-center-component></debate-room-center-component>
                    </div>

                    <div class="videobox-side" :style="customCaroselStyle">
                        <debate-room-side-component></debate-room-side-component>
                    </div>
                </div>
            </div>
            <div class="chatting-box" :style="customCaroselStyle">
                <div>여기는 채팅창</div>
            </div>
        </div>
        <div class="debate-room-footer-class">
            <debate-room-footer></debate-room-footer>
        </div>
    </div>
</template>


<script>
import debateRoomSideComponent from './debateRoomSideComponent'
import debateRoomCenterComponent from './debateRoomCenterComponent'
import debateRoomFooter from './debateRoomFooter'

export default {
    name: 'debateRoom',
    components: {
        debateRoomSideComponent,
        debateRoomCenterComponent,
        debateRoomFooter
    },
    computed : {
        customCaroselStyle() {
            return {
                "--debate-box-center-width": this.debateCenterBoxWidth,
                "--debate-box-center-height": this.debateCenterBoxHeight,
                "--debate-box-side-width": this.debateSideBoxWidth,
                "--debate-box-side-height": this.debateSideBoxHeight,
                "--debate-box-width": this.debateBoxWidth,
                "--debate-box-height": this.debateBoxHeight,
                "--video-box": this.videoBox,
                "--chatt-box": this.chattBox,

            }
        }
    },
    data() {
        return {
            debateCenterBoxWidth: '',
            debateCenterBoxHeight: '',
            debateSideBoxWidth: '',
            debateSideBoxHeight: '',
            videoBox: '',
            chattBox: '',
            chattTF: true,
            imgTF:true,
        }
    },
    mounted() {
        const wVideoValue = document.body.clientWidth
        const wValue = wVideoValue*0.8
        this.chattBox =  `${wVideoValue*0.25}px`
        this.videoBox =  `${wVideoValue*0.7}px`
        const hValue = document.body.clientHeight
        this.debateCenterBoxWidth = `${wValue*0.4}px` // margin buffer 10px 고려한 계산
        this.debateSideBoxWidth = `${wValue*0.8*0.25}px` // margin buffer 10px 고려한 계산
        this.debateBoxWidth = `${wValue}px` // margin buffer 10px 고려한 계산

        

        this.debateCenterBoxHeight = `${hValue*0.7}px` // margin buffer 10px 고려한 계산
        this.debateSideBoxHeight = `${hValue*0.7}px` // margin buffer 10px 고려한 계산
        window.addEventListener('resize', this.handleResizeHome);


        
    },
    methods: {
        handleResizeHome() {
        const wVideoValue = document.body.clientWidth
        const wValue = wVideoValue*0.8
        this.chattBox = `${wVideoValue*0.25}px`
        const hValue = document.body.clientHeight
        this.debateCenterBoxWidth = `${wValue*0.35}px` // margin buffer 10px 고려한 계산
        this.debateSideBoxWidth = `${wValue*0.8*0.25}px` // margin buffer 10px 고려한 계산
        this.debateBoxWidth = `${wValue}px` // margin buffer 10px 고려한 계산
        
        this.debateCenterBoxHeight = `${hValue*0.7}px` // margin buffer 10px 고려한 계산
        this.debateSideBoxHeight = `${hValue*0.7}px` // margin buffer 10px 고려한 계산
        },
    }
}
</script>

<style>
.startImg {
    display: flex;
    position: absolute;
    top: 20%;
    justify-content: center;
}
.debate-backcolor {
    background-color: black;
    height: 100vh;
    text-align: center;
}
.video-chatt-wrap {
    display: flex;
    justify-content: space-evenly;
}
.debate-background {
    height: 95vh;
    display: flex;
    align-items: center;
}

.debate-room-wrap {
    display: flex;
    /* 여기 조절해야됨 */
    /* width: var(--video-box); */
    /* 여기 조절해야됨 */
}
.chatting-box {
    background-color: pink;
    /* 여기 조절해야됨 */
    width: var(--chatt-box);
    /* 여기 조절해야됨 */
    height: 95vh;
    /* position: fixed;
    top: 0;
    right: 0; */
}
.videobox-side {
  height: var(--debate-box-side-height);
  width: var(--debate-box-side-width);
  color: black;
  /* background-color: aliceblue; */
  outline: 10px #667799 solid;
}
.videobox-center {
  height: var(--debate-box-center-height);
  width: var(--debate-box-center-width);
  color: black;
  /* background-color: aliceblue; */
  outline: 10px #667799 solid;
}
.debate-room-component-content {
    color: brown;
}
.debate-room-footer-class {
    width: var(--debate-box-width);
    height: 5vh;
    outline: 10px #669977 solid;
}
</style>
