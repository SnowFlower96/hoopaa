<template>
    <div>
        <div class="debate-moderator"><div class="debate-moderator-inner" :style="customViewStyle"></div></div>
        <div class="debate-guague">게이지바</div>
        <div class="debate-content">자료화면</div>
    </div>
</template>

<script>
export default {
    computed : {
        customViewStyle() {
            return {
                "--center-video-height" : this.centerVideoHeight,
                "--center-video-width" : this.centerVideoWidth,
            }
        }
    },
    data() {
        return {
            centerVideoHeight : '',
            centerVideoWidth : '',
        }
    },
    mounted() {
        const wValue = document.body.clientWidth*0.75*0.3-20
        this.centerVideoWidth = `${wValue}px`
        this.centerVideoHeight = `${wValue*0.6}px`

        window.addEventListener('resize', this.handleResizeHome);
    },
    methods: {
        handleResizeHome() {
            const wValue = document.body.clientWidth*0.75*0.3-20
            this.centerVideoWidth = `${wValue}px`
            this.centerVideoHeight = `${wValue*0.6}px`
        },
    }
}
</script>

<style>
.debate-moderator {
    display: flex;
    justify-content: center;
    align-items: center;
    outline: aqua solid 3px;
}
.debate-moderator-inner{
    background-color: white;
    height: var(--center-video-height);
    width: var(--center-video-width);
}
.debate-guague {
    color: brown;
    height: 5vh;
    outline: aqua solid 3px;
}
.debate-content {
    color: brown;
    height: 40vh;
    outline: aqua solid 3px;
}
</style>

