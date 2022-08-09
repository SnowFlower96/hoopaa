<template>
    <div class="modal-inner-padding-setting">
        <div class="let-team-speak">
            <div class="let-team-speak-inner">
                <p>발언권 부여하기</p>
            </div>
            <div class="let-team-speak-inner">
                <div class="ts-btn">찬성팀</div>
                <div class="ts-btn">반대팀</div>
            </div>
            <div class="let-team-speak-inner">
                <div>1분<input type="radio" value="1" v-model="timeSpeak"/></div>
                <div>3분<input type="radio" value="3" v-model="timeSpeak"/></div>
                <div>5분<input type="radio" value="5" v-model="timeSpeak"/></div>
                <div>10분<input type="radio" value="10" v-model="timeSpeak"/></div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            timeSpeak: null
        }
    }
}
</script>


<style>
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
.ts-btn {
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