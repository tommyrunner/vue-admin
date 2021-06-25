import {
  getAxiosInstance,
  setDomain,
  postTableDeleteTable,
  postTableGetTableAll,
  postTableSaveTable,
  postTableExportData,
  postTableImportData
} from 'tommy-vueadmin-sdk'
//修改请求头
import { setApi } from '@/api/index'
//getAxiosInstance:获取path,setDomain:设置请求头的方法
setApi(getAxiosInstance, setDomain, '')

// 表格-获取全部
const PostTableGetTableAll = (params) => {
  return postTableGetTableAll(params)
}
// 表格-删除
const PostTableDeleteTable = (params) => {
  return postTableDeleteTable(params)
}
// 表格-保存/更新
const PostTableSaveTable = (params) => {
  return postTableSaveTable(params)
}
// 表格-导出
const PostTableExportData = (params) => {
  return postTableExportData(params)
}
// 表格-导入
const PostTableImportData = (params) => {
  return postTableImportData(params)
}
export default {
  PostTableGetTableAll,
  PostTableDeleteTable,
  PostTableSaveTable,
  PostTableExportData,
  PostTableImportData
}
