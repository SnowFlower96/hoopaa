<template>
    <div class="debate-background">
        <div class="debate-room-outer"> 
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
            <div class="debate-room-footer-class">
                <debate-room-footer></debate-room-footer>
            </div>
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
            }
        }
    },
    data() {
        return {
            debateCenterBoxWidth: '',
            debateCenterBoxHeight: '',
            debateSideBoxWidth: '',
            debateSideBoxHeight: '',
        }
    },
    mounted() {
    const value = document.body.clientWidth
    this.debateCenterBoxWidth = `${value*0.4}px` // margin buffer 10px 고려한 계산
    this.debateSideBoxWidth = `${value*0.3}px` // margin buffer 10px 고려한 계산
    this.debateBoxWidth = `${value}px` // margin buffer 10px 고려한 계산
    // this.debateCenterBoxHeight = `${value}px`
    // this.debateSideBoxHeight = `100vh`
    window.addEventListener('resize', this.handleResizeHome);
    },
    methods: {
        handleResizeHome() {
        const value = document.body.clientWidth
        this.debateCenterBoxWidth = `${value*0.4}px` // margin buffer 10px 고려한 계산
        this.debateSideBoxWidth = `${value*0.3}px` // margin buffer 10px 고려한 계산
        this.debateBoxWidth = `${value}px` // margin buffer 10px 고려한 계산
        // this.debateCenterBoxHeight = `${value}px`
        // this.debateSideBoxHeight = `100vh`
        },
    }
}
</script>

<style>
.debate-background {
    background-color: black;
    height: 100vh;
}
.debate-room-outer {
    padding: 5%;
}
.debate-room-wrap {
    display: flex;
}
.videobox-side {
  height: auto;
  width: var(--debate-box-side-width);
  color: black;
  background-color: aliceblue;
  outline: 10px #667799 solid;
}
.videobox-center {
  height: auto;
  width: var(--debate-box-center-width);
  color: black;
  background-color: aliceblue;
  outline: 10px #667799 solid;
}
.debate-room-component-content {
    color: brown;
}
.debate-room-footer-class {
    width: var(--debate-box-width);
    height: auto;
    outline: 10px #669977 solid;
}
</style>
