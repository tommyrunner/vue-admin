<template>
  <el-button :type="type" :size="size" :round="round" @click="click" :upload="upload">
    <slot />
    <input ref="files" type="file" @change="changeFile" class="files" />
  </el-button>
</template>
<script>
export default {
  name: 'Wbbutton',
  props: {
    type: {
      type: String,
      default: ''
    },
    size: {
      type: String,
      default: 'mini'
    },
    round: {
      type: Boolean,
      default: false
    },
    upload: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      file: null
    }
  },

  mounted() {},

  methods: {
    click(e) {
      if (this.upload) {
        //上传文件
        this.$refs.files.click()
      } else {
        //普通按钮点击
        this.$emit('click', e)
      }
    },
    changeFile(e) {
      //选中文件
      this.file = this.$refs.files.files[0]
      if (this.file) {
        this.$emit('click', this.file)
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.files {
  display: none;
}
</style>
