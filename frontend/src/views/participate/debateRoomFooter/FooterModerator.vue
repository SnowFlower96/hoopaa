<template>
    <div class="debate-room-footer-container">
        <span>여기는 사회버튼</span>
        <button @click="callModal">도구모음</button>
        <button @click="callModal">강퇴버튼</button>
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
