<template>
    <div :style="customStyle" class="ctb-background" @click="chatCheck">
      <div class="chat-close-btn" @click="EmitChatClose"><i class="fas fa-times"></i></div>
      <div  class="title">
        <h1 class="title"> Team 채팅창</h1>
      </div>
      <div class="new-message-alarm">
          <span>{{isNewMessage}}</span>
      </div>

        <!--채팅 내역-->
      <div class="chatting-all-view" ref="messages"><!--  :heigth="chatHeight"> -->
        <div
          class="chat-format"
          v-for="(message, i) of messagesTeam"
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
import { mapState} from 'vuex';
export default {
    props:{
    messagesTeam:[]
  }
  ,
  data(){
      return {
        message: "",
        chatHeight: "80vh",
        isNewMessage:'',
        borderColor: ''
      }
    },

  watch:{
    messagesTeam: {
      deep : true,

      handler(){
        let len = this.messagesTeam.length;
        let unm = '';
        if(this.user.nnm==null){
          console.log("닉넴 파악 불가");
          unm = this.user
        }else{
          unm = this.user.nnm
          console.log("닉넴 파악 가능");
          console.log(unm);
          console.log(this.messagesTeam[len-1].writer)
        }
        if(this.messagesTeam[len-1].writer!=unm){
          console.log("다른 사람이 보냄");
          this.borderColor="rgb(70, 70, 74)"
          this.isNewMessage="*new"
        }

        this.$nextTick(()=>{
            let messages = this.$refs.messages;
            messages.scrollTo({ top: messages.scrollHeight, behavior: 'smooth' });
          })

      }
    }
  },
  computed:{
    ...mapState(["user"]),
    messagesTeam(){
      return this.messagesTeam
    },
    customStyle(){
      return{
      '--border-color' : this.borderColor
      }
    }
  }
  ,
    methods: {
        EmitChatClose() {
            this.$emit('close-chat')
        },
        clickSendMessages(){
          this.$emit('chat-team', this.message)
          this.message=''
        },
        chatCheck(){
          this.borderColor=''
          this.isNewMessage=''
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
  background-color: var(--border-color);
  width:100%;
  height: 100%;
  margin:0px;
  display: flex;
  flex-direction: column;
  color: white;
}
.title{
  text-align: center;
   display: inline;
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
  margin-top: 10%;
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
.new-message-alarm{
  text-align:right;
  height:2%;
}
</style>
