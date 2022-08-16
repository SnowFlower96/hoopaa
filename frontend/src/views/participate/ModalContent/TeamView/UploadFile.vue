<template>
    <div class="upload-file-wrap">
        <div  class="ufw-inner">
            <div>
                <div class="ufw-inner">
                    <p style="font-size: 30px;">화면 공유</p>
                </div>
                <div class="ufw-inner">
                    <div class="ufw-btn" @click="EmitScreen">공유하기</div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
   
    methods: {

        EmitScreen() {
            this.$emit('publishScreenShare');
        }

    }
}
</script>

<style>
.upload-file-wrap {
    color: white;
}
.ufw-btn {
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
.ufw-btn:hover {
    outline: 1px solid white;
    cursor: pointer;
    color: white;
}
.ufw-inner {
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>
