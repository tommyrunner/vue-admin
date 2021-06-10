<template>
  <wb-dialog ref="dialog" @cancel="cancel" @sublimt="sublimt">
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="账号">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="名称">
        <el-input v-model="form.user"></el-input>
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="form.note"></el-input>
      </el-form-item>
    </el-form>
  </wb-dialog>
</template>
<script>
export default {
  name: 'EditUser',
  data() {
    return {
      form: this.initForm()
    }
  },
  methods: {
    // 显示
    showEdit(userItem) {
      if (userItem) {
        this.$refs.dialog.showDialog('编辑用户')
        //回显数据
        this.form = Object.assign({}, userItem)
      } else {
        this.$refs.dialog.showDialog('添加用户')
        //清空
        this.form = this.initForm()
      }
    },
    // 初始化
    initForm() {
      return {
        name: '',
        user: '',
        note: ''
      }
    },
    //取消关闭
    cancel() {
      this.from = this.initForm()
    },
    //提交
    sublimt() {
      console.log('2')
    }
  }
}
</script>
<style scoped lang="scss"></style>
