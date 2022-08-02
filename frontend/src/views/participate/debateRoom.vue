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
    const wValue = document.body.clientWidth
    const hValue = document.body.clientHeight
    this.debateCenterBoxWidth = `${wValue*0.35}px` // margin buffer 10px 고려한 계산
    this.debateSideBoxWidth = `${wValue*0.25}px` // margin buffer 10px 고려한 계산
    this.debateBoxWidth = `${wValue}px` // margin buffer 10px 고려한 계산

    this.debateCenterBoxHeight = `${hValue*0.7}px` // margin buffer 10px 고려한 계산
    this.debateSideBoxHeight = `${hValue*0.7}px` // margin buffer 10px 고려한 계산
    window.addEventListener('resize', this.handleResizeHome);
    },
    methods: {
        handleResizeHome() {
        const wValue = document.body.clientWidth
        const hValue = document.body.clientHeight
        this.debateCenterBoxWidth = `${wValue*0.35}px` // margin buffer 10px 고려한 계산
        this.debateSideBoxWidth = `${wValue*0.25}px` // margin buffer 10px 고려한 계산
        this.debateBoxWidth = `${wValue}px` // margin buffer 10px 고려한 계산
        
        this.debateCenterBoxHeight = `${hValue*0.7}px` // margin buffer 10px 고려한 계산
        this.debateSideBoxHeight = `${hValue*0.7}px` // margin buffer 10px 고려한 계산
        },
    }
}
</script>

<style>
.debate-background {
    background-color: black;
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
}

.debate-room-wrap {
    display: flex;
}
.videobox-side {
  height: var(--debate-box-side-height);
  width: var(--debate-box-side-width);
  color: black;
  background-color: aliceblue;
  outline: 10px #667799 solid;
}
.videobox-center {
  height: var(--debate-box-center-height);
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
