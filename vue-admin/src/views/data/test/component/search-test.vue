<template>
  <div class="center">
    <wb-head-search ref="wbHeadSearch">
      <template v-slot:title>
        <el-row :gutter="10" class="search-row">
          <el-col :span="3">
            <el-input v-model.trim="form.value" size="mini" placeholder="值"></el-input>
          </el-col>
          <el-col :span="3">
            <el-input v-model.trim="form.note" size="mini" placeholder="备注"></el-input>
          </el-col>
          <el-col :span="3" :offset="6">
            <div class="btn">
              <el-button icon="el-icon-search" circle @click="search"></el-button>
              <el-button type="warning" icon="el-icon-refresh" circle @click="refresh"></el-button>
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
  name: 'SearchTest',
  data() {
    return {
      form: this.initForm(),
      isOpen: false,
      searchParams: this.initSearch()
    }
  },
  methods: {
    showOpen() {
      this.$nextTick(() => {
        this.isOpen = !this.isOpen
        this.$refs.wbHeadSearch.showAnimation(this.isOpen)
      })
    },
    refresh() {
      //初始化
      this.searchParams = this.initSearch()
      this.form = this.initForm()
      this.$emit('onRefresh', this.searchParams)
    },
    search() {
      this.searchParams.containing = this.form
      this.$emit('onSearch', this.searchParams)
    },
    initForm() {
      return {
        value: '',
        note: '',
      }
    },
    initSearch() {
      return {
        page: 1,
        pageSize: 2,
        sort: 'DESC',
        sortKey: 'id',
      }
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
