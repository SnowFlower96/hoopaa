<template>
    <div class="debate-room-footer-container">
        <span>여기는 팀버튼</span>
        <button @click="callModalFile">자료올리는버튼 => v-if로 view 조절하기</button>
        <button @click="callModalMessage">사회자에게 메세지보내기</button>
    </div>
</template>

<script>
export default {
    name: 'debateRoomFooter',
    methods: {
        callModalFile() {this.$emit('call-modal', 'file')},
        callModalMessage() {this.$emit('call-modal', 'message')},
    }
}
</script>


<style>
.debate-room-footer-container {
    color: white;
}
</style>
