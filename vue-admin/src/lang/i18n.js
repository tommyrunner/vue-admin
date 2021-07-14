import Vue from 'vue'
import VueI18n from 'vue-i18n'
Vue.use(VueI18n)

//导入语言包
import Zh from './zh.js' // 中文语言包
import En from './en.js' // 英文语言包

const messages = {
  zh: Zh, // 中文语言包
  en: En // 英文语言包
}
function getLanguage() {
  let locale = sessionStorage.getItem('lang') || 'zh'
  return locale
}
export default new VueI18n({
  locale: getLanguage(), // set locale 默认显示中文
  fallbackLocale: 'zh', //如果语言包没有
  messages: messages // set locale messages
})
