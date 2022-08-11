<template>
    <div class="cab-background">
        <div class="chatting-all-view">
            <div class="chat-close-btn" @click="EmitChatClose"><i class="fas fa-times"></i></div>
            <h1>전체 채팅창</h1>
            <div
              class="mt-2 text-left message"
              v-for="(message, i) of messagesAll"
              :key="i"
            >
              <div>
                {{message.writer}}
              </div>
              <div>
                {{ message.content}} {{message.time}}
              </div>
            </div>
        </div>
        <div class="chatting-all-write">
            <input type="text" v-model="message" placeholder="여기에 메세지를 입력하세요">
        </div>
        <div>
          <button class="send-btn" @click="clickSendMessages">
          전송
          </button>
        </div>
    </div>
</template>

<script>
export default {
    props:{
    messagesAll:[]
  }
  ,
  data(){
      return {
        message: "",
        chatHeight: "33vh"
      }
    },

  watch:{

  },
  computed:{
    messagesAll(){
      return this.messagesAll
    }
  }
  ,
    methods: {
        EmitChatClose() {
            this.$emit('close-chat')
        },
        clickSendMessages(){
          console.log("메세지 보내기");
          this.$emit('chat-all', this.message)
          this.message=''
        }

    }
}
</script>


<style>
.cab-background {
    color: white;
}
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
    height:200px;
}
.chatting-all-view-inner::-webkit-scrollbar{width: 8px;}
.chatting-all-view-inner::-webkit-scrollbar-thumb {
    background-color: rgb(156, 156, 156);
    border-radius: 5px;
}
.chatting-all-write::-webkit-scrollbar{width: 0px;}

.chat-close-btn > i {
    color:  rgb(156, 156, 156);
}
.chat-close-btn:hover {
    cursor: pointer;
}
.chatting-all-ta {
    border-radius: 5px;
    background-color:  rgb(54, 54, 54);
    width: 95%;
    height: 70px;
    margin: 0px;
}
</style>
