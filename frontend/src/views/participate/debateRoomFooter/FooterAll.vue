<template>
    <div class="debate-room-footer-container">
        <div class="heart-btn" @click="risingHeart"><i class="fas fa-heart"></i></div>
        <div class="clap-btn" @click="clapAnime"><i class="fas fa-sign-language"></i></div>
    </div>
</template>

<script>
export default {
    name: 'debateRoomFooter',
    methods: {
        risingHeart() {this.$emit('rising-heart')},
        clapAnime() {this.$emit('clap-anime')},
    }
}
</script>


<style>
.debate-room-footer-container {
    color: white;
}
.heart-btn {
    width: 5vh;
    height: 5vh;
    outline: tomato solid 1px;
    border-radius: 50%;
    margin-left: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
    color: tomato;
}
.heart-btn:hover {
    cursor: pointer;
    background-color: tomato;
    opacity: 50%;
    color: black;
}

.clap-btn {
    width: 5vh;
    height: 5vh;
    outline: yellow solid 1px;
    border-radius: 50%;
    margin-left: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
    color: yellowtomato;
}
.clap-btn:hover {
    cursor: pointer;
    background-color: yellow;
    opacity: 50%;
    color: black;
}
</style>
