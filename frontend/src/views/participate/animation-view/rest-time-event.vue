<template>
<div class="rest-sign-bg">
    <img :src="require(`@/assets/images/restTimeEventFront.png`)" style="position: absolute;width:900px; z-index: 25;" class="text-pop-up-top ">
    <h1 style="position: absolute; z-index: 13;" class="rsb-h1"><span class="rsb-span">rest</span>  <span class="rsb-span">time</span></h1>
    <h1 style="position: absolute; z-index: 13;" class="rsb-h1"><span class="rsb-span">rest</span>  <span class="rsb-span">time</span></h1>
    <img :src="require(`@/assets/images/restTimeEvent.png`)" style="width:900px; z-index: 3;" class="text-pop-up-top ">
</div>
</template>

<style>
@import url("https://fonts.googleapis.com/css?family=Luckiest+Guy&display=swap");


.rest-sign-bg {
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: "Luckiest Guy", cursive;
}

.rsb-h1 {
  margin: 0;
  font-size: 8em;
  padding: 0;
  color: whitesmoke;
  text-shadow: 0 0.1em 20px #4583ff, 0.05em -0.03em 0 #4583ff,
    0.05em 0.005em 0 #4583ff, 0em 0.08em 0 #4583ff,
    0.05em 0.08em 0 #4583ff, 0px -0.03em 0 #4583ff,
    -0.03em -0.03em 0 #4583ff, -0.03em 0.08em 0 #4583ff, -0.03em 0 0 #4583ff;
}
.rsb-span {
    transform: scale(0.9);
    display: inline-block;
    -webkit-text-stroke: 1px #44c933;
  }
.rsb-span:first-child {
animation: rop 1s cubic-bezier(0.175, 0.885, 0.32, 1.275) forwards infinite
	alternate;
}
.rsb-span:last-child {
animation: ropB 1s 0.2s cubic-bezier(0.175, 0.885, 0.32, 1.275) forwards
	infinite alternate;
}

@keyframes rop {
  0% {
    transform: scale(0.9);
  }
  50%,
  100% {
    transform: scale(1);
  }
}

@keyframes ropB {
  0% {
    transform: scale(0.9);
  }
  80%,
  100% {
    transform: scale(1) rotateZ(-3deg);
  }
}
</style>