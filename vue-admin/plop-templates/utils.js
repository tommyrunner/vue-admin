// 简单的验证下，输入的值
// 返回string：代表有错误信息，会显示再控制台
// 热会true：代表通过
exports.notEmpty = (name) => {
  return (v) => {
    if (!v || v.trim === '') {
      return `${name}为必填项`
    } else {
      return true
    }
  }
}
