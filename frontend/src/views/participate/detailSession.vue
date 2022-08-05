<template>
    <div class="detail-box-background" :style="customDetailSessionSize">
        <div class="detail-box-outer">
            <div class="detail-box-file">
                여기가 자료화면 구성
                {{chattOpen}}
            </div>
            <div class="detail-box-video">
                여기가 비디오 들어오는 곳
            </div>
        </div>
    </div>
</template>
<script>

export default {
    props: {
        // 1. 채팅창 여닫이 여부 받아와야됨
        // 
        chattOpen: Boolean
    },
    computed: {
        customDetailSessionSize() {
            return {
                '--detail-box-background' : this.detailBoxBackground
            }
        }
    },
    data() {
        return {
        detailBoxBackground: ''
        }
    },
    mounted() {
        const ViewWidth = document.body.clientWidth
        if (this.chattOpen === true) {ViewWidth = ViewWidth*0.75}
        const ViewHeight = document.body.clientHeight
        this.detailBoxBackground = `${ViewWidth}px`
        window.addEventListener('resize', this.handleResizeSession);

    },
    methods: {
        handleResizeSession() {
            const ViewWidth = document.body.clientWidth
            const ViewHeight = document.body.clientHeight
            this.detailBoxBackground = `${ViewWidth}px`
            console.log(ViewWidth)
        }
    },


}
</script>


<style>
    .detail-box-background {
        background-color: blue;
        height: 93vh;
        width: var(--detail-box-background);
    }
    .detail-box-outer {
        display: flex;

    }
    .detail-box-file {
        outline: 2px solid yellow;
        color: aliceblue;
    }
    .detail-box-video {
        outline: 2px solid yellow;
        color: aliceblue;
    }
</style>