<template>
    <div class="debate-room-footer-container">
        <div class="footer-btn menu-btn" @click="callModalMenu"><i class="fas fa-bars"></i></div>
        <div class="footer-btn out-btn" @click="callModalOut"><i class="fas fa-user-alt-slash"></i></div>
    </div>
</template>

<script>
export default {
    name: 'debateRoomFooter',
    methods: {
        callModalMenu() {this.$emit('call-modal', 'menu')},
        callModalOut() {this.$emit('call-modal', 'out')},
    }
}
</script>


<style>
.debate-room-footer-container {
    color: white;
}
.footer-btn {
    width: 5vh;
    height: 5vh;
    border-radius: 50%;
    margin-left: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
    
}
.footer-btn:hover {
    cursor: pointer;
    opacity: 50%;
    color: black;
}
.menu-btn {
    outline: rgb(255, 255, 5) solid 1px;
    color: rgb(255, 255, 5);
}
.menu-btn:hover {
    background-color: rgb(255, 255, 5);
}
.out-btn {
    outline: rgb(255, 49, 87) solid 1px;
    color: rgb(255, 49, 87);
}
.out-btn:hover {
    background-color: rgb(255, 49, 87);
}
</style>
