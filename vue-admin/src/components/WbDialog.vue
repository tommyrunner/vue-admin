<template>
  <el-dialog :title="title" :visible.sync="isShow" @close="cancel">
    <div class="center">
      <slot v-on="center" />
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button @click="cancel()">取 消</el-button>
      <el-button type="primary" @click="sublimt()">提 交</el-button>
    </div>
  </el-dialog>
</template>
<script>
export default {
  data() {
    return {
      isShow: false,
      title: ''
    }
  },
  methods: {
    showDialog(title) {
      this.title = title
      this.isShow = true
    },
    closeDialog() {
      this.isShow = false
    },
    sublimt() {
      this.$emit('sublimt')
    },
    cancel() {
      this.closeDialog()
      this.$emit('cancel')
    }
  }
}
</script>
<style scoped lang="scss">
.center {
  max-height: 400px;
  overflow: auto;
}
</style>
