<template>
  <wb-dialog ref="dialog" @cancel="cancel" @sublimt="sublimt" v-loading="loading">
    <el-form ref="form" :rules="rules" :model="form" label-width="80px">
      <transition-group name="my">
        <el-form-item label="账号" prop="user" :key="'user'">
          <el-input v-model="form.user" :disabled="!isEdit" placeholder="请输入邮箱"></el-input>
        </el-form-item>
        <el-form-item label="名称" prop="name" :key="'name'">
          <el-input v-model="form.name" placeholder="请输入名称"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="pwd" v-if="isEdit && form.user" :key="'pwd'">
          <el-input v-model="form.pwd" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label="验证码" prop="code" v-if="isEdit && form.user" :key="'code'">
          <el-input class="input-code" v-model="form.code" placeholder="请输入验证码"></el-input>
          <el-button @click="getSaveCode" :disabled="saveCodeToast.indexOf('秒') != -1">{{ saveCodeToast }}</el-button>
        </el-form-item>
        <el-form-item label="备注" prop="note" :key="'note'">
          <el-input v-model="form.note" placeholder="请输入备注"></el-input>
        </el-form-item>
        <el-form-item label="权限" :key="'tree'">
          <el-tree
            @check="treeCheckChange"
            ref="tree"
            :default-checked-keys="defSelectRoels"
            :data="appRoles"
            show-checkbox
            node-key="name"
            :props="defaultProps"
          >
          </el-tree>
        </el-form-item>
      </transition-group>
    </el-form>
  </wb-dialog>
</template>
<script>
import userApi from '@/api/user'
import { rolesRoutes } from '@/router'
import { isEmail, $Loading } from '@/utils'

export default {
  name: 'EditUser',
  data() {
    return {
      form: this.initForm(),
      loading: true,
      userRoles: [],
      appRoles: [],
      appInterval: {}, //计时器
      saveCodeToast: '获取验证码',
      defSelectRoels: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      isEdit: false,
      rules: {
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
        user: [
          { required: true, message: '请填写账户', trigger: 'blur' },
          { validator: this.validateEmail, message: '邮箱格式', trigger: 'blur' }
        ],
        pwd: [
          { required: true, message: '请填写密码', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请填写验证码', trigger: 'blur' },
          { min: 4, max: 4, message: '长度为 4 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    //获取注册验证码
    getSaveCode() {
      //发送验证码
      const loading = $Loading()
      userApi
        .GetUserSendEmail({ email: this.form.user })
        .then((res) => {
          const { data } = res
          loading.close()
          if (data.code == 200) {
            this.$message.success(data.msg)
            let time = 60
            clearInterval(this.appInterval)
            this.saveCodeToast = time-- + '秒'
            this.appInterval = setInterval(() => {
              this.saveCodeToast = time-- + '秒'
              if (time === 0) {
                this.saveCodeToast = '获取验证码'
                clearInterval(this.appInterval)
              }
            }, 1000)
          }
        })
        .catch((e) => {
          loading.close()
          this.$message.error(String(e))
        })
    },
    //校验邮箱rules
    validateEmail(rule, value, callback) {
      if (!isEmail(value)) {
        callback(new Error('邮箱格式错误'))
      } else {
        callback()
      }
    },
    //选中权限
    treeCheckChange(data) {
      this.defSelectRoels = this.$refs.tree.getCheckedNodes().map((item) => item.name)
    },
    // 显示
    showEdit(userItem, userRoles) {
      this.isEdit = !userItem
      if (userItem) {
        this.$refs.dialog.showDialog('编辑用户')
        //回显数据
        this.form = Object.assign({}, userItem)
        this.$nextTick(() => {
          //初始化
          //获取app路由
          this.appRoles = rolesRoutes.filter((item) => item.children && item.children.length > 0)
          this.$refs.tree.setCheckedKeys([])
          //显示权限
          this.defSelectRoels = userRoles
        })
      } else {
        this.$refs.dialog.showDialog('添加用户')
        //清空
        this.form = this.initForm()
        //初始化
        this.saveCodeToast = '获取验证码'
        this.appRoles = []
        clearInterval(this.appInterval)
      }
    },
    // 初始化
    initForm() {
      return {
        name: '',
        user: '',
        pwd: '',
        code: '',
        note: ''
      }
    },
    closeDialog() {
      this.$refs.dialog.closeDialog()
    },
    //取消关闭
    cancel() {
      this.from = this.initForm()
      //重置错误
      this.$refs.form.resetFields()
    },
    //提交
    sublimt() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.$emit('Submit', { form: this.form, rolesAll: this.defSelectRoels })
        } else {
          return false
        }
      })
      //   //
    },
    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    }
  }
}
</script>
<style scoped lang="scss">
.input-code {
  width: 50%;
  margin-right: 10px;
}
//入出场动画
.my-enter,
.my-leave-to {
  opacity: 0;
  transform: translateX(-10%);
}
//动画中间过渡效果
.my-enter-active,
.my-leave-active {
  transition: all 0.5s ease;
}
</style>
