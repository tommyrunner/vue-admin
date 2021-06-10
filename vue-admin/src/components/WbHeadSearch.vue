<template>
  <div class="center">
    <el-row :gutter="10" class="search-row">
      <slot name="title" />

      <div v-if="$slots.bottom" @click="isOpen = !isOpen" :class="['pointLine', !isOpen ? 'pointLine-off' : 'pointLine-on']"></div>
    </el-row>
    <transition name="my">
      <div class="center-open" v-if="isOpen">
        <slot name="bottom" />
        <el-divider v-if="$slots.bottom"></el-divider>
      </div>
    </transition>
  </div>
</template>
<script>
export default {
  name: 'SearchUser',
  data() {
    return {
      isOpen: false
    }
  },
  methods: {
    showAnimation(isOpen) {
      this.isOpen = isOpen
    }
  }
}
</script>
<style scoped lang="scss">
@import '~@/styles/variables';
.center {
  margin-bottom: 20px;
  .search-row {
    width: 100%;
    cursor: pointer;
    margin-top: 10px;
    .btn {
      display: flex;
    }
  }
  position: relative;
}
.pointLine {
  background: $themeColor;
  width: 10px;
  height: 10px;
  position: absolute;
  right: 0;
  transition: 1s;
  border-radius: 100%;
}
.pointLine-off {
  top: 0;
  background: $warningColor;
}
.pointLine-on {
  top: 100%;
  background: $themeColor;
}
//入出场动画
.my-enter,
.my-leave-to {
  opacity: 0;
  transform: translateY(100%);
}
//动画中间过渡效果
.my-enter-active,
.my-leave-active {
  transition: all 0.5s ease;
}
</style>
