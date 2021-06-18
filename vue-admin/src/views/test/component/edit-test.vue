<template>
  <wb-dialog ref="dialog" @cancel="cancel" @sublimt="sublimt" v-loading="loading">
    <el-form ref="form" :rules="rules" :model="form" label-width="80px">
      <el-form-item label="值" prop="value">
        <el-input v-model="form.value" placeholder="值"></el-input>
      </el-form-item>
      <el-form-item label="备注" prop="note">
        <el-input v-model="form.note" placeholder="备注"></el-input>
      </el-form-item>
    </el-form>
  </wb-dialog>
</template>
<script>
import { $Loading } from '@/utils'

export default {
  name: 'EditTest',
  data() {
    return {
      form: this.initForm(),
      loading: true,
      isEdit: false,
      rules: {
        value: [{ required: true, message: '请输入值', trigger: 'blur' }],
        note: [{ required: true, message: '请输入备注', trigger: 'blur' }],
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
        value: '',
        note: '',
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
