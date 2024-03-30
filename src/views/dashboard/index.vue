<template>
  <div class="dashboard-container">
    <div class="dashboard-text">
      視頻管理系统
    </div>
    <h1 style="margin-left: 16%; margin-top: 16%;">hello!<br>welcome to hahaha's video management system</h1>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name'
    ])
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
};
</style>
