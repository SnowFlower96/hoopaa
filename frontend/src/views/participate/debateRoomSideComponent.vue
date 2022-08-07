<template>
    <div class="debate-room-component-content-side">
        <!-- <div>여기에 패널 화면 넣으면 됨</div> -->
        <div class="blank-space-video" :style="customViewStyle"></div>
        <div class="panel-video" :style="customViewStyle"><div class="panel-video-inner"></div></div>
        <div class="panel-video" :style="customViewStyle"><div class="panel-video-inner"></div></div>
    </div>
</template>

<script>
export default {
    computed : {
        customViewStyle() {
            return {
                "--side-video-width" : this.sideVideoWidth,
                "--side-video-height" : this.sideVideoHeight,
                "--side-video-height-inner" : this.sideVideoHeightInner,
                "--blank-space-video" : this.blankSpaceVideo,
            }
        }
    },
    data() {
        return {
            sideVideoWidth: '',
            sideVideoHeight: '',
            sideVideoHeightInner: '',
            blankSpaceVideo: ''
        }
    },
    mounted() {
        const wValue = document.body.clientWidth*0.75*0.3-20

        const hBlankValue = document.body.clientHeight*0.8*0.2
        const hValue = document.body.clientHeight*0.8*0.8*0.5

        this.sideVideoWidth = `${wValue}px`
        this.sideVideoHeight = `${wValue*0.6}px`
        this.sideVideoHeightInner = `${hValue}px`
        this.blankSpaceVideo = `${hBlankValue}px`

        window.addEventListener('resize', this.handleResizeHome);
    },
    methods: {
        handleResizeHome() {
            const wValue = document.body.clientWidth*0.75*0.3-20

            const hBlankValue = document.body.clientHeight*0.8*0.2
            const hValue = document.body.clientHeight*0.8*0.8*0.5

            this.sideVideoWidth = `${wValue}px`
            this.sideVideoHeight = `${wValue*0.6}px`
            this.sideVideoHeightInner = `${hValue}px`
            this.blankSpaceVideo = `${hBlankValue}px`
        },
    }
}
</script>

<style>

.debate-room-component-content-side {
    color: brown;
}
.blank-space-video {
    height: var(--blank-space-video);
    /* background-color: blue; */
}
.panel-video {
    width: var(--side-video-width);
    height: var(--side-video-height-inner);
    /* background-color: rgb(100, 232, 255); */
    display: flex;
    align-items: center;
}
.panel-video-inner {
    width: var(--side-video-width);
    height: var(--side-video-height);
    background-color: white;
    border-radius: 10px;
}
</style>

