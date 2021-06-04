<template>
  <div class="login-container">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">
      <div class="title-container">
        <h3 class="title">{{ $t('loginPage.title') }}</h3>
      </div>

      <el-form-item prop="user">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input ref="user" v-model="loginForm.user" :placeholder="$t('loginPage.user')" name="user" type="text" tabindex="1" auto-complete="on" />
      </el-form-item>

      <el-form-item prop="pwd">
        <span class="svg-container">
          <svg-icon icon-class="pwd" />
        </span>
        <el-input
          :key="passwordType"
          ref="password"
          v-model="loginForm.pwd"
          :type="passwordType"
          :placeholder="$t('loginPage.pwd')"
          name="pwd"
          tabindex="2"
          auto-complete="on"
          @keyup.enter.native="handleLogin"
        />
        <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>
      <el-row :gutter="10">
        <el-col :span="12">
          <el-form-item prop="code" class="svg-container">
            <span>
              <svg-icon icon-class="code" />
            </span>
            <el-input
              ref="code"
              v-model="loginForm.code"
              :placeholder="$t('loginPage.code')"
              name="code"
              type="text"
              tabindex="1"
              auto-complete="on"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <div class="code-center">
            <img :src="codeImg" alt="" @click="getCodeImg" />
            <span @click="getCodeImg">{{ $t('loginPage.codeToast') }}</span>
          </div>
        </el-col>
      </el-row>

      <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:30px;" @click.native.prevent="handleLogin">{{
        $t('loginPage.login')
      }}</el-button>

      <div class="tips">
        <span style="margin-right:20px;">username: admin</span>
        <span> password: any</span>
      </div>
    </el-form>
  </div>
</template>

<script>
import userApi from '@/api/user.js'

export default {
  name: 'Login',
  data() {
    const validatePassword = (rule, value, callback) => {
      if (value.length < 6) {
        callback(new Error('The password can not be less than 6 digits'))
      } else {
        callback()
      }
    }
    return {
      loginForm: {
        user: '1223758238@qq.com',
        pwd: '123456',
        code: '123'
      },
      loginRules: {
        user: [{ required: true, trigger: 'blur' }],
        pwd: [{ required: true, trigger: 'blur', validator: validatePassword }],
        code: [{ required: true, trigger: 'blur', min: 1, max: 5 }]
      },
      codeImg: '',
      loading: false,
      passwordType: 'password',
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  mounted() {
    this.getCodeImg()
  },
  methods: {
    arrayBufferToBase64(buffer) {
      var binary = ''
      var bytes = new Uint8Array(buffer)
      var len = bytes.byteLength
      for (var i = 0; i < len; i++) {
        binary += String.fromCharCode(bytes[i])
      }
      return window.btoa(binary)
    },
    //获取验证图片
    getCodeImg(params = {}) {
      params.$config = { responseType: 'arraybuffer' } // 最为关键
      params.time = new Date().getTime()
      userApi.GetUserCode(params).then((res) => {
        this.codeImg = 'data:image/jpeg;base64,' + this.arrayBufferToBase64(res.data)
      })
    },
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    handleLogin() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.loading = true
          userApi
            .UserLogin(this.loginForm)
            .then((res) => {
              console.log(res)
              this.loading = false
            })
            .catch((e) => {
              console.log(e)
              this.loading = false
            })
        }
      })
    }
  }
}
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg: #283443;
$light_gray: #fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}
.code-center {
  color: white;
  display: flex;
  align-items: center;
  img {
    cursor: pointer;
    height: 45px;
    width: 120px;
    margin-right: 10px;
  }
  span:hover {
    cursor: pointer;
    text-decoration: underline;
  }
}

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;

.login-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding-left: 10px;
    color: $dark_gray;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}
</style>
