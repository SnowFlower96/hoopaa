<template>
<div class="start-sign-bg">
    <h1 class="ssb-h1"><span class="ssb-span">"Let's</span>  <span class="ssb-span">start"</span></h1>
</div>
</template>

<style>
@import url("https://fonts.googleapis.com/css?family=Luckiest+Guy&display=swap");


.start-sign-bg {
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: "Luckiest Guy", cursive;
}

.ssb-h1 {
  margin: 0;
  font-size: 8em;
  padding: 0;
  color: whitesmoke;
  text-shadow: 0 0.1em 20px #c9ae55, 0.05em -0.03em 0 #c9ae55,
    0.05em 0.005em 0 #c9ae55, 0em 0.08em 0 #c9ae55,
    0.05em 0.08em 0 #c9ae55, 0px -0.03em 0 #c9ae55,
    -0.03em -0.03em 0 #c9ae55, -0.03em 0.08em 0 #c9ae55, -0.03em 0 0 #c9ae55;
}
.ssb-span {
    transform: scale(0.9);
    display: inline-block;
  }
.ssb-span:first-child {
animation: bop 1s cubic-bezier(0.175, 0.885, 0.32, 1.275) forwards infinite
	alternate;
}
.ssb-span:last-child {
animation: bopB 1s 0.2s cubic-bezier(0.175, 0.885, 0.32, 1.275) forwards
	infinite alternate;
}

@keyframes bop {
  0% {
    transform: scale(0.9);
  }
  50%,
  100% {
    transform: scale(1);
  }
}

@keyframes bopB {
  0% {
    transform: scale(0.9);
  }
  80%,
  100% {
    transform: scale(1) rotateZ(-3deg);
  }
}
</style>