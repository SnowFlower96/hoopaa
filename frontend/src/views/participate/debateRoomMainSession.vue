<template>
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
</template>

<script>
import debateRoomSideComponent from './debateRoomSideComponent'
import debateRoomCenterComponent from './debateRoomCenterComponent'

export default {
    components: {
        debateRoomSideComponent, 
        debateRoomCenterComponent
    },
    data() {return {
        debateBoxSideHeight: '',
        debateBoxSideWidth: '',
        debateBoxCenterHeight: '',
        debateBoxCenterWidth: '',
    }},
    mounted() {
        const ViewWidth = document.body.clientWidth
        const ViewHeight = document.body.clientHeight
        const debateBackground = ViewWidth*0.75

        this.debateCenterBoxWidth = `${debateBackground*0.4-10}px`
        this.debateSideBoxWidth = `${debateBackground*0.3-10}px`
        this.debateCenterBoxHeight = `${ViewHeight*0.8}px`
        this.debateSideBoxHeight = `${ViewHeight*0.8}px`
    },
    methods: {},
    computed: {customCaroselStyle() {
        return {
            '--debate-box-side-height' : this.debateBoxSideHeight,
            '--debate-box-side-width' : this.debateBoxSideWidth,
            '--debate-box-center-height' : this.debateBoxCenterHeight,
            '--debate-box-center-width' : this.debateBoxCenterWidth,
        }
    }}
}
</script>

<style>
.videobox-side {
    display: flex;
    justify-content: center;
    height: var(--debate-box-side-height);
    width: var(--debate-box-side-width);
    color: black;
    /* background-color: rgb(61, 255, 94);
    outline: 10px #667799 solid; */
}
.videobox-center {
  height: var(--debate-box-center-height);
  width: var(--debate-box-center-width);
  color: black;
  /* background-color: rgba(121, 193, 255, 0.621); */
  /* outline: 10px #667799 solid; */
}
</style>
