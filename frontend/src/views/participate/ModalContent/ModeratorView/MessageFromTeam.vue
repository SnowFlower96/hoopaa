.<template>
    <div class="text-from-team">
        <div class="tft-inner">
            <div>
                <div class="tft-inner-label">
                    <p style="font-size: 25px; color:white;">찬성팀에서 온 메세지</p>
                </div>
                <div class="tft-inner-label message-box">
                    <div style="color:white;">발언ㅣㅅ간 10분으로 한번더 주세요dfgspdg;osdflkg;sdkljfg;sldfjg;slkdjf;lskdjf;glkdfjg;lkdf;lkd[fpioguspdofgjpsoidrhgposedjrpgiohjdlifgj;sodfjg;siodjfg;lsdjf;gkljs;dflgkj;dflkgjgsj;dlfkgjs;dlfkgj;k</div>
                </div>
                <div class="tft-inner-label">
                    <div class="tft-btn">확인</div>
                </div>
            </div>
        </div>
    </div>
</template>

<style>
.message-box::-webkit-scrollbar{width: 8px;}
.message-box::-webkit-scrollbar-thumb {
    background-color: rgb(156, 156, 156);
    border-radius: 5px;
}
.message-box{
    padding: 20px;
    width: 350px;
    height: 100px;
    outline: 1px solid rgba(168, 168, 168, 0.753);
    border-radius: 10px;
    overflow: auto;
    word-break:break-all;
}
.tft-btn {
    margin-top: 20px;
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
.tft-btn:hover {
    outline: 1px solid white;
    cursor: pointer;
    color: white;
}
.text-from-team {
    width: 100%;
    height: 100%;
}
.tft-inner {
    display: flex;
    justify-content: center;
    align-items: center;
    /* background-color: aqua; */
    width: 100%;
    height: 80%;
}
.tft-inner-label {
    display: flex;
    justify-content: center;
    align-items: center;
}
.tft-inner-input:focus {
    outline: 1px solid rgb(179, 178, 178);
}
</style>