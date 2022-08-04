<template>
    <div class="debate-room-footer-container">
        <div @click="callModalFile" class="file-btn footer-btn">F</div>
        <div @click="callModalMessage" class="message-btn footer-btn">M</div>
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
    display: flex;
}
.footer-btn {
    width: 5vh;
    height: 5vh;
    border-radius: 50%;
    margin-left: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
    
}
.footer-btn:hover {
    cursor: pointer;
    opacity: 50%;
    color: black;
}
.file-btn {
    outline: tomato solid 1px;
    color: tomato;
}
.file-btn:hover {
    background-color: tomato;
}
.message-btn {
    outline: rgb(80, 255, 71) solid 1px;
    color: rgb(80, 255, 71);
}
.message-btn:hover {
    background-color: rgb(80, 255, 71);
}
</style>
