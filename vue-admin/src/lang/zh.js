// 载入element的语言包
import zhLocale from 'element-ui/lib/locale/lang/zh-CN'
export default {
  ...zhLocale,
  loginPage: {
    title: '模板登录界面',
    user: '用户',
    pwd: '密码',
    code: '验证码',
    login: '登录',
    codeToast: '刷新图片!'
  },
  layout: {
    home: '主页',
    loginOut: '登出',
    tagesClose: '关闭',
    tagesCloseAll: '关闭所有',
    tagesCloseOthers: '关闭其他',
    loading: '加载中...'
  },
  langs: {
    zh: '中文',
    en: '英文'
  },
  route: {
    Dashboard: '控制板',
    Data: '数据',
    DataTable: '表格',
    DataTest: '测试',
    ManagementSystem: '系统',
    Management: '管理',
    ManagementUser: '用户',
    ManagementRoles: '菜单'
  },
  form: {
    new: '新键',
    delete: '删除',
    number: '序号',
    edit: '查看',
    update: '修改',
    operation: '操作',
    nullDataToast: '暂无数据',
    cancel: '取消',
    submit: '提交'
  }
}
