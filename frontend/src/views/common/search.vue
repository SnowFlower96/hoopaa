<template>
  <div class="search-out-container">
    <div class="search-head">
      <span class="search-item">검색조건</span>
      <div class="search-item search-item-wrap">
        <input placeholder="검색어를 입력하세요">
        <button>검/</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name : 'search',
}
</script>

<style>
.search-head {
  height: 5vh;
  text-align: center;
  /* background-color: bisque; */
  display: flex;
  justify-content: center;
}
.search-out-container {
  border-bottom: solid 1px rgba(212, 212, 212, 0.505);
  padding: 20px;
  height: 50px;
}


.search-item-wrap {
  height: 30px;
  border-radius: 20px;
  width: 40%;
  max-width: 500px;
  border: solid 2px rgba(197, 196, 196, 0.505);
}
.search-item-wrap:hover {
  border: solid 2px rgba(141, 141, 141, 0.505);
}
.search-item-wrap > input {
  width: 85%;
  border: none;
  border-radius: 20px;
}
.search-item-wrap > input:focus {
  outline: none;
}
.search-item-wrap > button {
  border-radius: 20px;
  border: none;
  padding: 6px;
  background-color: rgba(197, 196, 196, 0.505);
}
.search-item-wrap > button:hover {
  background-color: rgba(163, 162, 162, 0.505);
}
</style>
