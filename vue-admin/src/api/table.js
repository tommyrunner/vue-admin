import { getAxiosInstance, setDomain, getTableGetTableAll, postTableDeleteTable, postTableSaveTable } from 'tommy-vueadmin-sdk'
//修改请求头
import { setApi } from './index'
//getAxiosInstance:获取path,setDomain:设置请求头的方法
setApi(getAxiosInstance, setDomain, '')

// 表格-获取全部
const GetTableGetTableAll = (params) => {
  return getTableGetTableAll(params)
}
// 表格-删除
const PostTableDeleteTable = (params) => {
  return postTableDeleteTable(params)
}
// 表格-保存/更新
const PostTableSaveTable = (params) => {
  return postTableSaveTable(params)
}
export default {
  GetTableGetTableAll,
  PostTableDeleteTable,
  PostTableSaveTable
}
