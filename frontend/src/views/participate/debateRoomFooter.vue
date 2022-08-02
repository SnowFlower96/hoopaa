<template>
    <div>
        <button @click="footerButton">자료올리는버튼 => v-if로 view 조절하기</button>
        <button @click="footerButton">채팅방 버튼</button>
    </div>
</template>

<script>
export default {
    name: 'debateRoomFooter',
    methods: {
        footerButton() {
            console.log('sp')
        }
    }
}
</script>


<style>
.debate-room-footer-container {
    color: white;
}
</style>
