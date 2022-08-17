<template>
    <div class="modal-inner-padding-setting">
        <div  class="rest-time-wrap">
            <div class="rest-time-inner">
                <p style="font-size: 30px;">쉬는시간 부여</p>
            </div>
            <div class="rest-time-inner">
                <div class="radio-rest">1분<input type="radio" value="1" v-model="timeRest"/></div>
                <div class="radio-rest">3분<input type="radio" value="3" v-model="timeRest"/></div>
                <div class="radio-rest">5분<input type="radio" value="5" v-model="timeRest"/></div>
                <div class="radio-rest">10분<input type="radio" value="10" v-model="timeRest"/></div>
            </div>
            <div class="rest-time-inner">
                <div class="rs-btn" @click="EmitRest">{{timeRest}}분 부여</div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            timeRest: 0
        }
    },
    methods: {
        EmitRest() {
            this.$emit('emit-rest', parseInt(this.timeRest)*60)
        }
    }

}
</script>


<style>
.radio-rest {
    margin: 10px;
    margin-top: 20px;
}
.rs-btn {
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
.rs-btn:hover {
    outline: 1px solid white;
    cursor: pointer;
    color: white;
}
.rest-time-inner {
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>