<template>
  <div class="center">
    <wb-head-search ref="wbHeadSearch">
      <template v-slot:title>
        <el-row :gutter="10" class="search-row">
          <el-col :span="3">
            <el-input v-model="form.user" size="mini" placeholder="账号"></el-input>
          </el-col>
          <el-col :span="3">
            <el-input v-model="form.name" size="mini" placeholder="名称"></el-input>
          </el-col>
          <el-col :span="3">
            <el-input v-model="form.note" size="mini" placeholder="备注"></el-input>
          </el-col>
          <el-col :span="3" :offset="12">
            <div class="btn">
              <el-button icon="el-icon-search" circle></el-button>
              <el-button type="warning" icon="el-icon-refresh" circle></el-button>
              <el-button type="primary" @click="showOpen" :icon="isOpen ? 'el-icon-caret-top' : 'el-icon-caret-bottom'" circle></el-button>
            </div>
          </el-col>
        </el-row>
      </template>
    </wb-head-search>
  </div>
</template>
<script>
export default {
  name: 'SearchUser',
  data() {
    return {
      form: {},
      isOpen: false
    }
  },
  methods: {
    showOpen() {
      this.$nextTick(() => {
        this.isOpen = !this.isOpen
        this.$refs.wbHeadSearch.showAnimation(this.isOpen)
      })
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
  background: $themeColor;
}
.pointLine-on {
  top: 100%;
  background: white;
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
