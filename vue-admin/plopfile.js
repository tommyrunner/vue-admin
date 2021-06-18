const formGenerator = require('./plop-templates/form/prompt') // 指定一个模块目录（根据自己目录）
const springGenerator = require('./plop-templates/spring/prompt') // 指定一个模块目录（根据自己目录）

module.exports = function(plop) {
  plop.setGenerator('form', formGenerator) // 引入模块，可以多个
  plop.setGenerator('spring', springGenerator) // 引入模块，可以多个
  // 如果模块多个，启动时，控制台可以选择
}
