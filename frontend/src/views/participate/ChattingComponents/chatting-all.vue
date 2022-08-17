<template>
    <div class="ctb-background">
      <div class="chat-close-btn" @click="EmitChatClose"><i class="fas fa-times"></i></div>
      <div class="title">
        <h1>전체 채팅창</h1>
      </div>
            <!--채팅 내역-->
            <div class="chatting-all-view"><!--  :heigth="chatHeight"> -->
              <div
                class="chat-format"
                v-for="(message, i) of messagesAll"
                :key="i"
              >


                <div class="sender">
                  <span>{{message.writer}}</span>
                </div>
                <div class="chat-content">
                  <div class="message">
                    <span>{{message.message}}</span>
                  </div>
                  <div class="time">
                    <span>{{message.time}}</span>
                  </div>
                </div>



            <!-- <div class="chatting-all-write">
            <textarea placeholder="여기에 메세지를 입력하세요"  v-model="message" class="chatting-team-ta" cols="30" rows="10"></textarea>
            </div> -->

        </div>
      </div>

      <div class="chatting-all-write">
        <div class="col-10 px-1 py-0">
          <textarea placeholder="여기에 메세지를 입력하세요"  v-model="message" @keyup.enter="clickSendMessages" class="chatting-all-ta" cols="30" rows="10"></textarea>
          <div class="chatting-send-btn">
            <button
              class="send-btn"
              @click="clickSendMessages"
            >
              <i class="fas fa-paper-plane"></i>
            </button>
          </div>
        </div>
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
        chatHeight: "80vh"
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
          this.$emit('chat-all', this.message)
          this.message=''
        }

    }
}
</script>


<style>

.chatting-all-write{
    bottom: 0;
    margin:2%;
    color : white;

}


.ctb-background {
  width:100%;
  height: 100%;
  margin:0px;
  display: flex;
  flex-direction: column;
  color: white;
}
.title{
  text-align: center;
}
.chatting-send-btn{
  float:right;
  margin:2%;
}
.chat-close-btn {
    display: flex;
    justify-content: end;
}
.chatting-all-view{
  position: relative;
  width: 100%;
  height: 60%;
  overflow: scroll;
  overflow-x: hidden;
}
.chatting-all-view-inner {
    height:55vh;
    overflow: auto;
}
.chat-format{
  margin: 7% 2%  7% 2%;
}
.chat-content{
    background-color: gold;
    border-radius:5px;
    padding:2%;
}

.chatting-all-view::-webkit-scrollbar{width: 5px;}
.chatting-all-view::-webkit-scrollbar-thumb {
    background-color: rgb(156, 156, 156);
    border-radius: 5px;
}
.chatting-all-write::-webkit-scrollbar{width: 0px;}

.chat-close-btn > i {
    color:  rgb(156, 156, 156);
}
.time{
  text-align: right;
  font-size: small;
}
.sender{
  font-size: small;
}
.chat-close-btn:hover {
    cursor: pointer;
}
.chatting-all-ta {
    border-radius: 5px;
    background-color:  rgb(54, 54, 54);
    color : white;
    width: 95%;
    height: 70px;
    margin: 0px;
}
</style>
