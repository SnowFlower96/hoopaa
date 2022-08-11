<template>
    <div>
        <div class="participating-page-black-space"></div>
        <h1>
            토론 입장
        </h1>
        <router-link to="/debateRoom"><button>입장</button></router-link>
        <router-link to="/"><button>취소</button></router-link>

    </div>
</template>
<script>
import router from '../../common/lib/vue-router'

export default {

  methods : {
   
  }
}

</script>
<style>
.participating-page-black-space {
    height: 100px;
}
</style>
