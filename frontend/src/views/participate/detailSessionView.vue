<template>
    <div class="detail-box-background" :style="customDetailSessionSize">
        <div class="detail-box-outer">
            <div class="detail-box-file">
                화면공유부분 => 컴포넌트를 분리할까 생각중
            </div>
            <div class="detail-box-video">
                <debate-room-side-component></debate-room-side-component>
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

export default {
    components: {
        FooterTeam,
        debateRoomSideComponent,
        chattingTeam
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
            videoBox : ''
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
        }
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
