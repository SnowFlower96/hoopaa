<template>
    <div class="end-debate-background">
        <div>
            <div style="font-size:100px;">토론 종료 !!!</div>
            <div class="end-btn-wrap">
                <router-link style="text-decoration:none;" to="/"><div class="end-btn">결과 보러가기 >>> </div></router-link>
            </div>
            
        </div>
    </div>
</template>

<style>
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