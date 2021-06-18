const { notEmpty } = require('../utils') // 自己定义的一个工具方法-后面会说
// 导入数据
const json = require('./config.json')

module.exports = {
  description: '自动按模板生成 spirng后端接口代码', // 描述这个generate的作用
  prompts: [
    {
      type: 'input', // 问题的类型
      name: 'fileName', // 问题对应得到答案的变量名，可以在acitons中使用该变量
      message: '请输入模块名:', // 在命令行中的问题
      validate: notEmpty('fileName') // 验证输入的值，notEmpty自定义的工具方法里验证
    }
    // 这里可以多个，代表多个问题，依次执行
    /**    {
      type: 'input', // 问题的类型
      name: 'pathName2', // 问题对应得到答案的变量名，可以在acitons中使用该变量
      message: '文件名称2' // 在命令行中的问题
    }**/
  ],
  // 执行的动作
  actions: (data) => {
    // 这里可以通过data获取输入的fileName
    const actions = [
      // 创建index.js文件
      {
        type: 'add', // 操作类型 添加文件
        path: `src/${data.fileName}/${data.fileName}Entity.java`, // 添加的文件的路径
        templateFile: 'plop-templates/spring/entity.hbs', // 模版文件的路径（***这里就是想要生成的模板）
        data: {
          entity: json.entity,
          module: json.module,
          Module: json.Module
        }
      },
      {
        type: 'add', // 操作类型 添加文件
        path: `src/${data.fileName}/${data.fileName}Dao.java`, // 添加的文件的路径
        templateFile: 'plop-templates/spring/dao.hbs', // 模版文件的路径（***这里就是想要生成的模板）
        data: {
          entity: json.entity,
          module: json.module,
          Module: json.Module
        }
      },
      {
        type: 'add', // 操作类型 添加文件
        path: `src/${data.fileName}/${data.fileName}Service.java`, // 添加的文件的路径
        templateFile: 'plop-templates/spring/service.hbs', // 模版文件的路径（***这里就是想要生成的模板）
        data: {
          entity: json.entity,
          module: json.module,
          Module: json.Module
        }
      },
      {
        type: 'add', // 操作类型 添加文件
        path: `src/${data.fileName}/${data.fileName}Controller.java`, // 添加的文件的路径
        templateFile: 'plop-templates/spring/controller.hbs', // 模版文件的路径（***这里就是想要生成的模板）
        data: {
          entity: json.entity,
          module: json.module,
          Module: json.Module
        }
      }
    ]
    return actions
  }
}
