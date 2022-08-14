<template>
  <div class="main-footer">
    <div class="footer-contents">
      Copyright © <img :src="require(`@/assets/images/071414.png`)" alt=""> All Rights Reserved.
    </div>
  </div>
</template>

<style>
/* 반응형 - 모바일 화면 */
@media (max-width: 700px) {
  .footer-contents > img {
    width: 70px;
   
  }

  .footer-contents {
    line-height: 50px;
    text-align: center;
    font-size: small;
     background-color: white;
     z-index: 250 !important;
  }
}
/* 반응형 - 모바일 화면 */

/* 반응형 - 큰 화면 */
@media (min-width: 701px) {
  .footer-contents > img {
    width: 100px;
  }

  .footer-contents {
    line-height: 50px;
    text-align: center;
    background-color: white;
    z-index: 250 !important;
  }

}
/* 반응형 - 큰 화면 */
</style>
<script>
import { reactive } from 'vue'

export default {
  name: 'main-footer',

  setup() {
    const state = reactive({})

    return { state }
  }
  
}
</script>
<style>
</style>
