<template>
    <div class="debate-room-footer-container">
        <div @click="callModalFile" class="file-btn footer-btn"><i class="fas fa-desktop"></i></div>
        <div @click="callModalMessage" class="message-btn footer-btn"><i class="fas fa-envelope"></i></div>
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
    outline: white solid 1px;
    color: white;
}
.file-btn:hover {
    background-color: white;
}
.message-btn {
    outline: white solid 1px;
    color: white;
}
.message-btn:hover {
    background-color: white;
}
</style>
