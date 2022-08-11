<template>
  <div class="search-out-container">
    <div class="search-head">
      <!-- <span class="search-item">검색조건</span> -->
      <div class="search-item-wrap">
        <input placeholder="검색어를 입력하세요">
        <div class="search-icon"><i class="fas fa-search "></i></div>
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
.search-icon {
  display: flex;
  align-items: center;
}
.search-icon  > i {
  color: rgba(134, 134, 134, 0.671);
}
.search-icon  > i:hover {
  color: rgba(83, 83, 83, 0.733);
  cursor: pointer;
}
.search-item-wrap {
  /* height: 30px; */
  padding: 4px;
  border-radius: 20px;
  width: 40%;
  max-width: 500px;
  border: solid 2px rgba(197, 196, 196, 0.505);
  display: flex;
  justify-content: space-evenly;
  align-items: center;
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
  position: relative;
  left: 15px;
}
.search-item-wrap > button:hover {
  background-color: rgba(163, 162, 162, 0.505);
}
</style>
