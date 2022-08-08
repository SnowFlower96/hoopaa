<template>
    <div>
        <div class="chatting-all-view">
            <div class="chat-close-btn" @click="EmitChatClose"><i class="fas fa-times"></i></div>
            <h1>전체 채팅창</h1>
            <div class="chatting-all-view-inner">
                <p>안뇽</p>
                <p>안뇽</p>
                <p>안뇽</p>
                <p>안뇽</p>
                <p>안뇽</p>
                <p>안뇽</p>
                <p>안뇽</p>
                <p>안뇽</p>
                <p>안뇽</p>
                <p>안뇽</p>
                <p>안뇽</p>
                <p>안뇽</p>
                <p>안뇽</p>
                <p>안뇽</p>
                <p>안뇽</p>
                <p>안뇽</p>
                <p>안뇽</p>
                <p>안뇽</p>
                <p>안뇽</p>
                <p>안뇽</p>
                <p>안뇽</p>
                <p>안뇽</p>
                <p>안뇽</p>
                <p>안뇽</p>
            </div>
        </div>
        <div class="chatting-all-write">
            <input type="text" placeholder="여기에 메세지를 입력하세요">
        </div>
    </div>
</template>

<script>
export default {
    methods: {
        EmitChatClose() {
            this.$emit('close-chat')
        }
    }
}
</script>

<style>
.chat-close-btn {
    display: flex;
    justify-content: end;
}
.chatting-all-view {
    padding: 5%;
    
}
.chatting-all-view-inner {
    height:55vh;
    overflow: auto;
}
.chatting-all-write {
    padding: 5%;
    height:10vh;
    overflow: auto;
}
.chatting-all-view-inner::-webkit-scrollbar{width: 8px;}
.chatting-all-view-inner::-webkit-scrollbar-thumb {background-color: #2c5b94;}

.chatting-all-write > input {
    width: 41vh;
    height:9vh;
}
</style>
