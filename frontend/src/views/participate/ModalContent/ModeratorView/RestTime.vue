<template>
    <div class="modal-inner-padding-setting">
        <div  class="rest-time-wrap">
            <div class="rest-time-inner">
                <p style="font-size: 30px;">쉬는시간 부여</p>
            </div>
            <div class="rest-time-inner">
                <div class="radio-speak">1분<input type="radio" value="1" v-model="timeSpeak"/></div>
                <div class="radio-speak">3분<input type="radio" value="3" v-model="timeSpeak"/></div>
                <div class="radio-speak">5분<input type="radio" value="5" v-model="timeSpeak"/></div>
                <div class="radio-speak">10분<input type="radio" value="10" v-model="timeSpeak"/></div>
            </div>
            <div class="rest-time-inner">
                <div class="rs-btn" @click="">쉬는시간 부여</div>
            </div>
        </div>
    </div>
</template>

<style>
.rs-btn {
    margin: 10px;
    width: 130px;
    height: 50px;
    border-radius: 10px;
    background-color: yellow;
    color: black;
    display: flex;
    justify-content: center;
    align-items: center;
}
.rest-time-inner {
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>