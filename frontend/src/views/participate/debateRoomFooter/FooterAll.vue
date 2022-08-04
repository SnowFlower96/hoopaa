<template>
    <div class="debate-room-footer-container">
        <div class="heart-btn">😍</div>
    </div>
</template>

<script>
export default {
    name: 'debateRoomFooter',
    methods: {
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
</style>
