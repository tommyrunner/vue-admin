<template>
  <wb-dialog ref="dialog" @cancel="cancel" @sublimt="sublimt" v-loading="loading">
    <el-form ref="form" :rules="rules" :model="form" label-width="80px">
      <el-form-item label="值1" prop="key1">
        <el-input v-model="form.key1" placeholder="值1"></el-input>
      </el-form-item>
      <el-form-item label="值2" prop="key2">
        <el-input v-model="form.key2" placeholder="值2"></el-input>
      </el-form-item>
    </el-form>
  </wb-dialog>
</template>
<script>
import { $Loading } from '@/utils'

export default {
  name: 'EditTable',
  data() {
    return {
      form: this.initForm(),
      loading: true,
      isEdit: false,
      rules: {
        key1: [{ required: true, message: '请输入值1', trigger: 'blur' }],
        key2: [{ required: true, message: '请输入值2', trigger: 'blur' }],
      }
    }
  },
  methods: {
    // 显示
    showEdit(item) {
      this.isEdit = !item
      if (item) {
        this.$refs.dialog.showDialog('编辑菜单')
        //回显数据
        this.form = Object.assign({}, item)
      } else {
        this.$refs.dialog.showDialog('添加菜单')
        //清空
        this.form = this.initForm()
      }
    },
    // 初始化
    initForm() {
      return {
        key1: '',
        key2: '',
      }
    },
    closeDialog() {
      this.$refs.dialog.closeDialog()
    },
    //取消关闭
    cancel() {
      this.from = this.initForm()
      //重置错误警告
      this.$refs.form.resetFields()
    },
    //提交
    sublimt() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.$emit('Submit', { form: this.form })
        } else {
          return false
        }
      })
    }
  }
}
</script>
<style scoped lang="scss">
</style>
