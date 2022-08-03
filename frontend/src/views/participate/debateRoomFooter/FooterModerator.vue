<template>
    <div class="debate-room-footer-container">
        <span>여기는 사회버튼</span>
        <button @click="callModalMenu">도구모음</button>
        <button @click="callModalOut">강퇴버튼</button>
    </div>
</template>

<script>
export default {
    name: 'debateRoomFooter',
    methods: {
        callModalMenu() {this.$emit('call-modal', 'menu')},
        callModalOut() {this.$emit('call-modal', 'out')},
    }
}
</script>


<style>
.debate-room-footer-container {
    color: white;
}
</style>
