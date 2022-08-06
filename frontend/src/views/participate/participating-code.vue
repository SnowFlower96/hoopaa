<template>
        <div class="participate-black-space"></div>
    <div class="participate-background">
        <div>
            <div class="participate-with-code-container">
                <div class="pp-items-wrap">
                    <p style="font-size: 30px;">토론방 입장</p>
                    <p>참여코드를 받았나요?</p>
                    <div class="pp-code-item">
                        <input type="text" placeholder="참여 코드를 입력하세요">
                    </div>
                    <div class="pp-code-item">
                        <router-link to="/debateRoom"><button class="pp-code-item-btn">입장</button></router-link>
                    </div>
                </div>
            </div>
            <router-link style=text-decoration:none; to="/"><div class="pp-goto-home"><span>홈으로 돌아가기</span><img :src="require(`@/assets/images/favi6.png`)" alt="home-favi"></div></router-link>
        </div>
    </div>
</template>

<script>

</script>
    
<style>
.pp-goto-home {
    padding-top: 10px;
    display: flex;
    justify-content: end;
    color: rgba(0, 0, 0, 0.656);
    height: 30px;
}
.pp-goto-home > span {
    margin-top: 2px;
}
.pp-goto-home > img {
    height: 20px;
    width: 25px;
    margin-left: 10px;
}
.participate-with-code-container {
    width: 500px;
    height: 300px;
    background: linear-gradient(to bottom right, white, #E1CAFF);
    box-shadow: 3px 10px 10px 3px  rgba(0, 0, 0, 0.112);
    border-radius: 10px;
    display: flex;
    justify-content: center;
    align-items: center;
    overflow: hidden;
}
.pp-items-wrap {
    width: 400px;
}
.pp-items-wrap > p {
    margin-top: 10px;
    margin-bottom: 10px;
    /* text-shadow: 3px 3px 3px rgba(0, 0, 0, 0.297); */
}
.participate-black-space {
    width:100vh;
    height: 150px;
}
.participate-background {
    display: flex;
    justify-content: center;
    height: calc(90vh - 150px);
}
.pp-code-item > input {
    width: 394px;
    padding: 0px;
    height: 40px;
    border: #E0C8FF 1px solid;
    border-radius: 5px;
    background-color: #e0c8ff25;
    padding: none;
    outline: none;
    margin-top: 10px;
    margin-bottom: 10px;
    padding-left: 5px;
}
.pp-code-item > input:hover {
    outline: none;
    box-shadow: inset 1px 2px 2px 1px rgba(0, 0, 0, 0.297);
}
.pp-code-item > input:focus {
    outline: none;
    box-shadow: inset 1px 2px 2px 1px rgba(0, 0, 0, 0.297);
}


.fadein-pp-code{
  overflow: hidden;
  
}

@keyframes fadein-pp-code{
  0% {
    box-shadow: 3px 10px 10px 3px  rgba(0, 0, 0, 0.086);
  }
  100% {
    box-shadow: 0px 2px 2px 0px rgba(0, 0, 0, 0.297);
    /* filter: brightness(30%); */
  }
}
.pp-code-item-btn:hover {
  cursor: pointer;
  box-shadow: 0px 2px 2px 0px rgba(0, 0, 0, 0.297);
  animation: fadein-pp-code 0.07s ease-in-out;
  /* background-color: #d9c0fa; */
}
.pp-code-item-btn {
  background-color: #e8d6ff;
  border-radius: 5px;
  border: none;
  width: 400px;
  height: 40px;
  box-shadow: 3px 10px 10px 3px  rgba(0, 0, 0, 0.086);
  outline: 1px solid #d8bbff;
  margin-top: 10px;
  margin-bottom: 10px;
}
.pp-code-item {
}
</style>