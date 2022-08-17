<template>
    <div class="user-out-wrap">
        <div  class="uow-inner">
            <div>
                <div class="uow-label">
                    <p style="font-size: 30px;">유저 강퇴</p>
                    <p>선택한 유저를 방에서 내보내거나 음소거시킵니다.</p>
                </div>
                <div class="uow-inner">
                    <select name="" id="" v-model="id" style="width:100px; height: 30px; background-color: whitesmoke; border-radius:5px; margin:10px;" >
                        <option v-for="(item, index) in list" :key="index" :value="item.id" >{{item.nnm}}</option>
                    </select>
                </div>
                <div class="uow-inner">
                    <div class="uow-btn" @click="givePenalty" >주의주기</div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
export default {
  props : ['list'],
  data () {
    return {
      id : ''
    }
  },
  methods : {
    givePenalty() {
      this.$store.dispatch("givePenalty", this.id)
    }
  }
}
</script>
<style>
.user-out-wrap {
    color: white;
}
.uow-btn {
    margin: 10px;
    width: 130px;
    height: 50px;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: rgb(0, 0, 0);
    border-radius: 10px;
    outline: 1px solid rgba(168, 168, 168, 0.753);
    color: rgb(182, 182, 182);
}
.uow-btn:hover {
    outline: 1px solid white;
    cursor: pointer;
    color: white;
}
.uow-label {
    text-align: center;
}
.uow-inner {
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>
