import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
//引入多语言支持
import i18n from '@/lang/i18n.js'
import lang from '@/lang/lang.js'
window.lang = lang
import '@/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'

import '@/icons' // icon
import '@/permission' // permission control

Vue.use(ElementUI)

// // 判断Wb开头的组件加载 /Wb.*\.vue$/
//返回的是以后函数接受req，传入keys[x]会返回一个module模块
//module === 使用import导入的模块
const requireComponent = require.context('./components', false, /Wb.*\.vue$/)
// //全部注册
requireComponent.keys().forEach((fileName) => {
  //格式化名字，从./xxxx.vue获取为xxxx
  const fileNames = fileName.split('./')
  const componentName = fileNames[1].substring(0, fileNames[1].lastIndexOf('.'))
  //获取组件
  let componentConfig = requireComponent(fileName).default
  //注册组件
  Vue.component(componentName, componentConfig)
})

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  i18n, //挂载i18n
  render: (h) => h(App)
})
