<template>
    <div class="end-debate-background">
        <div>
            <div style="font-size:100px;">토론 종료 !!!</div>
            <div class="end-btn-wrap">
            <div @click="resultBtn" class="end-btn">결과 보러가기 ⬇ </div>
            </div>
            
        </div>
    </div>

    <div id="resultWrap" class="graphWrap">
        <p style="font-size: 100px; margin:0px;">결과</p>
        
        <div id="barChart" class="barChart">
            <div id="chartValue">{{initial}}</div>
        </div>
        <div id="barChart2" class="barChart2">
            <div id="chartValue2">{{initial}}</div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            initial: 0,
            // 받아온 데이터 넣을 예정
            agree: 20,
            disagree: 55,
        }
    },
    mounted() {
        
        // let tmpH;
        // setInterval(()=>{
        // tmpH = (Math.random()*150+50);
        // document.getElementById("barChart").style.height = tmpH + "px";
        // document.getElementById("chartValue").innerText = Math.round(tmpH);

        // document.getElementById("barChart2").style.height = tmpH + "px";
        // document.getElementById("chartValue2").innerText = Math.round(tmpH);
        // }, 4000 );
    },
    methods: {
        // showResult() {
        //     document.getElementById("barChart").style.height = this.agree*12 + "px";
        //     document.getElementById("chartValue").innerText = Math.round(this.agree);

        //     document.getElementById("barChart2").style.height = this.disagree*12 + "px";
        //     document.getElementById("chartValue2").innerText = Math.round(this.disagree);
            
        // },
        resultBtn() {
            let element = document.getElementById("resultWrap");
            element.scrollIntoView({behavior: "smooth", block: "end", inline: "nearest"});

            document.getElementById("barChart").style.height = this.agree*12 + "px";
            document.getElementById("chartValue").innerText = Math.round(this.agree);

            document.getElementById("barChart2").style.height = this.disagree*12 + "px";
            document.getElementById("chartValue2").innerText = Math.round(this.disagree);
        }
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
        bottom:-100vh;  
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


    .barChart2{
        position: absolute;
        left:80%; 
        bottom:-100vh; 
        width:30px;
        height:20px;
        perspective-origin: center bottom ;
        transform: translate(-50%) scaleX(2);
        background-color:#9999ff;
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
</style>