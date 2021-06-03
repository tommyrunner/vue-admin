<template>
  <div>
    <el-radio-group v-model="radio" @change="setLang">
      <el-radio label="zh">中文</el-radio>
      <el-radio label="en">英文</el-radio>
    </el-radio-group>
  </div>
</template>
<script>
export default {
  data() {
    return {
      radio: 'zh'
    }
  },
  created() {
    this.radio = sessionStorage.getItem('lang')
  },
  methods: {
    setLang() {
      this.$t.locale = this.radio
      sessionStorage.setItem('lang', this.radio)
      location.reload()
    }
  }
}
</script>
<style lang="scss" scoped></style>
