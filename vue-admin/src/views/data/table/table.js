import { getAxiosInstance, setDomain, postTableDeleteTable, postTableGetTableAll, postTableSaveTable, postTableSyncTable } from 'tommy-vueadmin-sdk'
//修改请求头
import { setApi } from '@/api/index'
//getAxiosInstance:获取path,setDomain:设置请求头的方法
setApi(getAxiosInstance, setDomain, '')

// 菜单-获取全部
const PostTableGetTableAll = (params) => {
  return postTableGetTableAll(params)
}
// 菜单-删除
const PostTableDeleteTable = (params) => {
  return postTableDeleteTable(params)
}
// 菜单-保存/更新
const PostTableSaveTable = (params) => {
  return postTableSaveTable(params)
}
// 菜单-同步
const PostTableSyncTable = (params) => {
  return postTableSyncTable(params)
}
export default {
  PostTableGetTableAll,
  PostTableDeleteTable,
  PostTableSaveTable,
  PostTableSyncTable
}
