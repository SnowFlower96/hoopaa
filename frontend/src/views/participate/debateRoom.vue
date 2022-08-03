<template>
<!-- <img v-if="imgTF" class="startImg" :src="require(`@/assets/images/start.png`)" alt=""> -->
<div v-if="imgTF" class="startImg">
    <animation-view></animation-view>
</div>
    <div class="debate-backcolor">
        <div class="video-chatt-wrap">
            <div class="debate-background" :style="customCaroselStyle">
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
            <div v-if="chattTF" class="chatting-box" :style="customCaroselStyle">
                <chatting-all></chatting-all>
            </div>
        </div>
        <div class="debate-room-footer-class">
            <button @click="">자료올리는버튼 => v-if로 view 조절하기</button>
            <button @click="footerButton">채팅방 버튼</button>
        </div>
    </div>
</template>


<script>
import debateRoomSideComponent from './debateRoomSideComponent'
import debateRoomCenterComponent from './debateRoomCenterComponent'
import animationView from './animation-view.vue'
import chattingAll from './ChattingComponents/chatting-all'

export default {
    name: 'debateRoom',
    components: {
        debateRoomSideComponent,
        debateRoomCenterComponent,
        animationView,
        chattingAll
    },
    computed : {
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

            debateBackground: '',

            chattTF: true,
            imgTF:true,
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
        
        window.addEventListener('resize', this.handleResizeHome);

    },
    methods: {
        handleResizeHome() {
            if (this.chattTF === true) {    // 채팅창 열려있을때
                const wVideoValue = document.body.clientWidth
                const debateBackground = wVideoValue*0.75

                const hValue = document.body.clientHeight


                this.debateBackground = `${debateBackground}px`
                this.chattBox = `${wVideoValue*0.25}px`
                this.footerWidth = `${wVideoValue}px`
                

                this.debateCenterBoxWidth = `${debateBackground*0.4-10}px`
                this.debateSideBoxWidth = `${debateBackground*0.3-10}px`
                
                this.debateCenterBoxHeight = `${hValue*0.8}px`
                this.debateSideBoxHeight = `${hValue*0.8}px`
            }
            else {     // 채팅창 닫혀있을때
                const wVideoValue = document.body.clientWidth
                const debateBackground = wVideoValue

                const hValue = document.body.clientHeight


                this.debateBackground = `${debateBackground}px`
                this.chattBox = `${wVideoValue*0.25}px`
                this.footerWidth = `${wVideoValue}px`
                

                this.debateCenterBoxWidth = `${debateBackground*0.4-10}px`
                this.debateSideBoxWidth = `${debateBackground*0.3-10}px`
                
                this.debateCenterBoxHeight = `${hValue*0.8}px`
                this.debateSideBoxHeight = `${hValue*0.8}px`
            }
        },
        footerButton() {
            this.chattTF = !this.chattTF
            if (this.chattTF === true) {    // 채팅창 열려있을때
                const wVideoValue = document.body.clientWidth
                const debateBackground = wVideoValue*0.75

                const hValue = document.body.clientHeight


                this.debateBackground = `${debateBackground}px`
                this.chattBox = `${wVideoValue*0.25}px`
                this.footerWidth = `${wVideoValue}px`
                

                this.debateCenterBoxWidth = `${debateBackground*0.4-10}px`
                this.debateSideBoxWidth = `${debateBackground*0.3-10}px`
                
                this.debateCenterBoxHeight = `${hValue*0.8}px`
                this.debateSideBoxHeight = `${hValue*0.8}px`
            }
            else {     // 채팅창 닫혀있을때
                const wVideoValue = document.body.clientWidth
                const debateBackground = wVideoValue

                const hValue = document.body.clientHeight


                this.debateBackground = `${debateBackground}px`
                this.chattBox = `${wVideoValue*0.25}px`
                this.footerWidth = `${wVideoValue}px`
                

                this.debateCenterBoxWidth = `${debateBackground*0.4-10}px`
                this.debateSideBoxWidth = `${debateBackground*0.3-10}px`
                
                this.debateCenterBoxHeight = `${hValue*0.8}px`
                this.debateSideBoxHeight = `${hValue*0.8}px`
            }
        }

    }
}
</script>

<style>
.startImg {
    position: absolute;
    top: 20%;
    color: aliceblue;
    background-color: rgb(93, 93, 53);
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
    /* background-color: rgb(61, 255, 94); */
    /* outline: 10px #667799 solid; */
}
.videobox-center {
  height: var(--debate-box-center-height);
  width: var(--debate-box-center-width);
  color: black;
  /* background-color: rgba(121, 193, 255, 0.621); */
  /* outline: 10px #667799 solid; */
}


</style>
