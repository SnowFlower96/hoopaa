<template>
    <div class="ctb-background">
        <div class="chatting-team-view">
            <div class="chat-close-btn" @click="EmitChatClose"><i class="fas fa-times"></i></div>
            <h1>팀 채팅창</h1>
            <div class="chatting-team-view-inner">
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
        <div class="chatting-team-write">
            <textarea placeholder="여기에 메세지를 입력하세요" class="chatting-team-ta" cols="30" rows="10"></textarea>
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
.ctb-background {
    color: white;
}
.chat-close-btn {
    display: flex;
    justify-content: end;
}
.chatting-team-view {
    padding: 5%;
    
}
.chatting-team-view-inner {
    height:55vh;
    overflow: auto;
}
.chatting-team-write {
    padding: 5%;
    height:200px;
}
.chatting-team-view-inner::-webkit-scrollbar{width: 8px;}
.chatting-team-view-inner::-webkit-scrollbar-thumb {
    background-color: rgb(156, 156, 156);
    border-radius: 5px;
}
.chatting-team-write::-webkit-scrollbar{width: 0px;}

.chat-close-btn > i {
    color:  rgb(156, 156, 156);
}
.chat-close-btn:hover {
    cursor: pointer;
}

.chatting-team-ta {
    border-radius: 5px;
    background-color:  rgb(54, 54, 54);
    width: 95%;
    height: 70px;
    margin: 0px;
}
</style>
