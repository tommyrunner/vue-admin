<template>
  <wb-dialog ref="dialog" @cancel="cancel" @sublimt="sublimt" v-loading="loading">
    <el-form ref="form" :rules="rules" :model="form" label-width="80px">
      <el-form-item label="标题" prop="title">
        <el-input v-model="form.title" placeholder="标题"></el-input>
      </el-form-item>
      <el-form-item label="菜单" prop="roles">
        <el-input v-model="form.roles" placeholder="菜单"></el-input>
      </el-form-item>
      <el-form-item label="地址" prop="path">
        <el-input v-model="form.path" placeholder="地址"></el-input>
      </el-form-item>
      <el-form-item label="图标" prop="icon">
        <el-input v-model="form.icon" placeholder="图标"></el-input>
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
  name: 'EditRoles',
  data() {
    return {
      form: this.initForm(),
      loading: true,
      isEdit: false,
      rules: {
        title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
        roles: [{ required: true, message: '请输入菜单', trigger: 'blur' }],
        path: [{ required: true, message: '请输入路径', trigger: 'blur' }]
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
        title: '',
        roles: '',
        path: '',
        icon: '',
        note: ''
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
.input-code {
  width: 50%;
  margin-right: 10px;
}
</style>
