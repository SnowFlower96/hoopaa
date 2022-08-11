<template>
    <div class="text-to-moderator">
        <div class="ttm-inner">
            <div>
                <div class="ttm-inner-label">
                    <p style="font-size: 25px; color:white;">사회자에게 메세지 보내기</p>
                </div>
                <div class="ttm-inner-label">
                    <textarea class="ttm-inner-input" placeholder="여기에 사회자에게 보내는 메세지를 입력하세요" cols="30" rows="10"></textarea>
                </div>
                <div class="ttm-inner-label">
                    <div class="ttm-btn" @click="EmitRest">제출</div>
                </div>
            </div>
        </div>
    </div>
</template>

<style>
.ttm-btn {
    margin-top: 30px;
    width: 70px;
    height: 40px;
    border-radius: 10px;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: rgb(0, 0, 0);
    border-radius: 10px;
    outline: 1px solid rgba(168, 168, 168, 0.753);
    color: rgb(182, 182, 182);
}
.ttm-btn:hover {
    outline: 1px solid white;
    cursor: pointer;
    color: white;
}
.text-to-moderator {
    width: 100%;
    height: 100%;
}
.ttm-inner {
    display: flex;
    justify-content: center;
    align-items: center;
    /* background-color: aqua; */
    width: 100%;
    height: 80%;
}
.ttm-inner-label {
    display: flex;
    justify-content: center;
    align-items: center;
}
.ttm-inner-input {
    padding: 5px;
    width: 350px;
    height: 80px;
    border-radius: 5px;
    border: none;
    outline: 1px solid rgb(116, 116, 116);
    background-color: rgba(39, 39, 39, 0.644);
    color: white;
}
.ttm-inner-input:focus {
    outline: 1px solid rgb(179, 178, 178);
}
</style>