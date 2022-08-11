<template>
    <div class="graphWrap">
        결과가 궁금해?
        <button @click="showResult">확인?</button>
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
        showResult() {
            document.getElementById("barChart").style.height = this.agree*12 + "px";
            document.getElementById("chartValue").innerText = Math.round(this.agree);

            document.getElementById("barChart2").style.height = this.disagree*12 + "px";
            document.getElementById("chartValue2").innerText = Math.round(this.disagree);
        }
    }
}
</script>

<style>
    /* .graphWrap{
        display: flex;
    }
    .barChart{
        position: absolute;
        left:65%; 
        bottom:10px; 
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
        bottom:10px; 
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
    } */
</style>
