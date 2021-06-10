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
      <el-form-item label="权限">
        <el-tree ref="tree" :default-checked-keys="defSelectRoels" :data="appRoles" show-checkbox node-key="name" :props="defaultProps"> </el-tree>
      </el-form-item>
    </el-form>
  </wb-dialog>
</template>
<script>
import userAip from '@/api/user'
import { rolesRoutes } from '@/router'
export default {
  name: 'EditUser',
  data() {
    return {
      form: this.initForm(),
      userRoles: [],
      appRoles: [],
      defSelectRoels: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      }
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

      this.$nextTick(() => {
        //初始化
        //获取app路由
        this.appRoles = rolesRoutes
        this.$refs.tree.setCheckedKeys([])
        //显示权限
        userAip
          .GetRolesByUserIdAll({ userId: userItem.id })
          .then((res) => {
            this.userRoles = res.data.data
            this.defSelectRoels = this.userRoles.map((item) => item.roles)
          })
          .catch((e) => {
            console.log(e)
            this.$message.error(String(e))
          })
      })
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
    },
    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    }
  }
}
</script>
<style scoped lang="scss"></style>
