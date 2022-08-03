<template>
    <div class="debate-room-footer-container">
        <span>여기는 팀버튼</span>
        <button @click="callModal">자료올리는버튼 => v-if로 view 조절하기</button>
        <button @click="callModal">사회자에게 메세지보내기</button>
    </div>
</template>

<script>
export default {
    name: 'debateRoomFooter',
    methods: {
        callModal() {
            this.$emit('call-modal')
        }
    }
}
</script>


<style>
.debate-room-footer-container {
    color: white;
}
</style>
