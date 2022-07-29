<template>
  <div class="search-head">
    <span>검색조건</span><input placeholder="검색어를 입력하세요">
    <button>검색</button>
  </div>
</template>

<script>
export default {
  name : 'search',
}
</script>

<style>
.search-head {
  height: 45px;
  text-align: center;
  /* background-color: bisque; */
  height: 10vh;
  border-bottom: solid 1px black;
}
</style>
