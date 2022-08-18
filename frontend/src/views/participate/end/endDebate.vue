<template>
    <div class="end-debate-background">
        <div>
            <div style="font-size:100px;">토론 종료 !!!</div>
            <div class="end-btn-wrap">
            <div @click="resultBtn" class="end-btn">결과 보러가기 ⬇ </div>
            </div>

        </div>
    </div>

    <div class="mvp-view displayFlex" v-if="mvpView">
        <div class="mvp-box">
            <p style="font-size: 40px; color:white;" class="displayFlex">👑 이번 토론의 MVP 👑</p>
            <div class="displayFlex">
                <div class="mvp-menber-box displayFlex">
                    <p style="color:white; font-size: 40px; margin:15px;">{{king}}</p>
                </div>
            </div>
            <div class="displayFlex">
                <router-link to="/"><div class="mvp-btn">홈으로</div></router-link>
            </div>
        </div>
    </div>
    <div id="resultWrap" class="graphWrap">
        <p class="result-title">토론 결과</p>

        <div id="barChart" class="barChart">
            <div id="chartValue">{{initial}}</div>
        </div>
        <div class="barChart-t1">찬성</div>
        <div id="barChart2" class="barChart2">
            <div id="chartValue2">{{initial}}</div>
        </div>
        <div class="barChart-t2">반대</div>
    </div>
</template>

<script>

export default {
    data() {
        return {
            initial: 0,
            // 받아온 데이터 넣을 예정
            agree: '',
            disagree: '',
            king : '',
            mvpView: false
        }
    },
    mounted() {
        const endSound = new Audio("https://drive.google.com/uc?export=download&id=1CwL1BIeUH7ymCbHOFtO9J2BMHCw3Vsk0");
        endSound.play();
    },
    methods: {
        async resultBtn() {
            const clap = new Audio("https://drive.google.com/uc?export=download&id=13AIGF6JIkbdiO5WYS2YhB-g4Y-5Ub6iE");
            clap.play();
          let query = window.location.search;
         await this.$store.dispatch("getRoomResult", query.split('?')[1]).then((res)=> {
            console.log(res)
            let result = JSON.parse(res.data.json);
            this.agree = result.agree.split("표")[0];
            this.disagree = result.disagree.split("표")[0];
            this.king = result.king
         })
            let element = document.getElementById("resultWrap");
            element.scrollIntoView({behavior: "smooth", block: "end", inline: "nearest"});
            const total = this.agree + this.disagree
            document.getElementById("barChart").style.height = (this.agree/total)*570 + "px";
            document.getElementById("chartValue").innerText = Math.round(this.agree);

            document.getElementById("barChart2").style.height = (this.disagree/total)*570 + "px";
            document.getElementById("chartValue2").innerText = Math.round(this.disagree);
            setTimeout(() => {
                this.mvpView = true
            }, 5000)
        }
    },
    created () {

    }
}
</script>

<style>
    #resultWrap {
        background-color: aquamarine;
        background-size: 100% 100%;
        background-image: url('../../../assets/images/resultBackground.png')
    }
    .result-btn {
        margin: 10px;
        width: 70px;
        height: 40px;
        display: flex;
        justify-content: center;
        align-items: center;
        background-color: rgb(255, 210, 210);
        border-radius: 10px;
        outline: 1px solid rgba(187, 106, 106, 0.753);
        color: rgb(0, 0, 0);
    }
    .result-btn:hover {
        outline: 1px solid rgb(167, 41, 41);
        cursor: pointer;
        color: rgb(0, 0, 0);
    }
    .graphWrap{
        height: 100vh;
    }
    .barChart{
        position: absolute;
        left:65%;
        bottom:-90vh;
        width:30px;
        height:20px;
        perspective-origin: center bottom ;
        transform: translate(-50%) scaleX(2);
        background-color:#9999ff;
        border-radius:15px;
        transition:height 3s;
    }
    .barChart::before{
        position: absolute;
        content:"";
        width:30px; height:30px; border-radius: 100%;
        background-color:rgba(255,255,255,0.25);
    }
    .barChart::after{
        position: absolute;
        content:"";
        width:30px; height:30px; bottom:0;
        border-radius: 100%;
        background-color:rgba(0,0,0,0.15);
    }
    #chartValue{
        position: absolute; left:50%;
        transform: scaleX(0.5) translateX(-100%) rotate(-0.03deg) translateY(-15px); text-align:center;
        font-weight:bold; font-size:24px; color:#000000;
        filter:drop-shadow(0px 5px 3px rgba(0,0,0,0.25));
    }
    .barChart-t1{
        transform: translate(-29%, 0%);
        width: 100px;
        position: absolute;
        left: 65%;
        bottom:-95vh;
        font-size: 30px;
    }
    .barChart-t2{
        transform: translate(-27%, 0%);
        width: 100px;
        position: absolute;
        left:  80%;
        bottom:-95vh;
        font-size: 30px;
    }

    .result-title {
        position: absolute;
        font-size: 100px;
        margin:0px;
        top: 125%;
        left: 13%;
    }
    .barChart2{
        position: absolute;
        left:80%;
        bottom:-90vh;
        width:30px;
        height:20px;
        perspective-origin: center bottom ;
        transform: translate(-50%) scaleX(2);
        background-color:#40b854;
        border-radius:15px;
        transition:height 3s;
    }
    .barChart2::before{
        position: absolute;
        content:"";
        width:30px; height:30px; border-radius: 100%;
        background-color:rgba(255,255,255,0.25);
    }
    .barChart2::after{
        position: absolute;
        content:"";
        width:30px; height:30px; bottom:0;
        border-radius: 100%;
        background-color:rgba(0,0,0,0.15);
    }
    #chartValue2{
        position: absolute; left:50%;
        transform: scaleX(0.5) translateX(-100%) rotate(-0.03deg) translateY(-15px); text-align:center;
        font-weight:bold; font-size:24px; color:#000000;
        filter:drop-shadow(0px 5px 3px rgba(0,0,0,0.25));
    }

.mvp-view {
    width: 100%;
    height: 100vh;
    background-color: rgba(0, 0, 0, 0.452);
    position: absolute;
    z-index: 3;
}

.end-debate-background {
    height: 100vh;
    background-color: black;
    color:whitesmoke;
    display: flex;
    justify-content: center;
    align-items: center;
}
.end-btn {
    font-size:30px;
    margin: 50px;
    width: 300px;
    height: 90px;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: rgb(0, 0, 0);
    border-radius: 10px;
    outline: 1px solid rgba(168, 168, 168, 0.753);
    color: rgb(182, 182, 182);
}
.end-btn-wrap {
    display: flex;
    justify-content: center;
    align-items: center;
}
.end-btn:hover {
    outline: 1px solid white;
    cursor: pointer;
    color: white;
    background-color: rgba(43, 43, 43, 0.781);
}
.mvp-box{
    width: 600px;
    height: 470px;
    background: #000000a1;
    border: 1px solid #8a8a8aaf;
    border-radius: 20px;
}
.mvp-btn {
    margin-top: 20px;
    width: 150px;
    height: 70px;
    border-radius: 10px;
    color: rgb(161, 161, 161);
    border: rgb(161, 161, 161) 1px solid;
    font-size: 30px;
    display: flex;
    justify-content: center;
    align-items: center;
}
.mvp-btn:hover {
    border: rgb(255, 255, 255) 1px solid;
    color: rgb(255, 255, 255);
    cursor: pointer;
}
.mvp-menber-box {
    width: 400px;
    height: 200px;
    border-radius: 20px;
    border: 1px solid #8a8a8aaf;
}
</style>
