<template>
    <div class="modal-inner-padding-setting">
        <div class="let-team-speak">

            <div class="let-team-speak-inner">
                <p>발언권 부여하기</p>
            </div>

            <div class="let-team-speak-inner">
                <div class="ts-btn" @click="speakFunction(1)">찬성팀</div>
                <div class="ts-btn" @click="speakFunction(0)">반대팀</div>
            </div>

            <div class="let-team-speak-inner">
                <div class="radio-speak">1분<input type="radio" value="1" v-model="timeSpeak"/></div>
                <div class="radio-speak">3분<input type="radio" value="3" v-model="timeSpeak"/></div>
                <div class="radio-speak">5분<input type="radio" value="5" v-model="timeSpeak"/></div>
                <div class="radio-speak">10분<input type="radio" value="10" v-model="timeSpeak"/></div>
            </div>
                <!-- {{timeSpeak}} -->
            <div class="let-team-speak-inner">
                <div class="ts-submit-btn" @click="EmitTime">
                    <p v-if="speakStatus">찬성</p>
                    <p v-if="!speakStatus">반대</p>
                    팀 에게 
                    <p> {{timeSpeak}}</p> 분 부여
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            timeSpeak: 0,
            speakStatus: 0
        }
    },
    methods: {
        speakFunction(status) {
            this.speakStatus = status
        },
        EmitTime() {
            this.$emit('emit-time', [parseInt(this.timeSpeak), this.speakStatus])
        }
        
    }
}
</script>


<style>
.radio-speak {
    margin: 10px;
    margin-top: 20px;
}
.modal-inner-padding-setting {
    width: 100%;
    height: 90%;
    color: white;
}
.let-team-speak{
    width: 100%;
    height: 100%;
    /* display: flex;
    justify-content: center;
    align-items: center; */
}
.ts-submit-btn {
    margin: 10px;
    width: 200px;
    height: 40px;
    border-radius: 10px;
    background-color: yellow;
    color: black;
    display: flex;
    justify-content: center;
    align-items: center;
}
.ts-btn {
    margin: 10px;
    width: 70px;
    height: 40px;
    border-radius: 10px;
    background-color: yellow;
    color: black;
    display: flex;
    justify-content: center;
    align-items: center;
}
.let-team-speak-inner {
    display: flex;
    justify-content: center;
    align-items: center;
}

</style>