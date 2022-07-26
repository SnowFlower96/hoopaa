<template>
<div>
  <el-row
    class="main-sidebar"
    :gutter="10"
    :style="{ 'width': width }">
    <div class="hide-on-small">
     <ul>
      <li v-for="(item,index) in menus" :key="index">{{item.name}}</li>
     </ul>
    </div>
  </el-row>
  </div>
</template>
<style>
.main-sidebar {
  display: flex;
;
}
.main-sidebar .el-menu {
  margin-top: 0;
  padding-left: 0;

}
.main-sidebar .hide-on-small {
  height: 100%;
}
.main-sidebar .hide-on-small .el-menu {
  height: 100%;
}
.main-sidebar .el-menu .el-menu-item {
  cursor: pointer;
  border-right: none;
}
.main-sidebar .el-menu .el-menu-item .ic {
  margin-right: 5px;
}
</style>
<script>
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { mapState } from "vuex";

export default {
  name: 'main-header',

  props: {
    width: {
      type: String,
      default: '240px'
    }
  },
  data : {
  },
  computed : {
    ...mapState(["menus"])
  },

}
</script>
