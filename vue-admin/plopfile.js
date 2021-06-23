const formGenerator = require('./plop-templates/form/prompt') // 生成表单模块
const formLangGenerator = require('./plop-templates/formLang/prompt') // 生成表单模块-国际化
const springGenerator = require('./plop-templates/spring/prompt') // 后端4大模块

module.exports = function(plop) {
  plop.setGenerator('form', formGenerator)
  plop.setGenerator('formLang', formLangGenerator)
  plop.setGenerator('spring', springGenerator)
  // 如果模块多个，启动时，控制台可以选择
}
