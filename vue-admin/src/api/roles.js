import { getAxiosInstance, setDomain, postRolesDeleteRoles, postRolesGetRolesAll, postRolesSaveRoles, postRolesSyncRoles } from 'tommy-vueadmin-sdk'
//修改请求头
import { setApi } from './index'
//getAxiosInstance:获取path,setDomain:设置请求头的方法
setApi(getAxiosInstance, setDomain, '')

// 菜单-获取全部
const PostRolesGetRolesAll = (params) => {
  return postRolesGetRolesAll(params)
}
// 菜单-删除
const PostRolesDeleteRoles = (params) => {
  return postRolesDeleteRoles(params)
}
// 菜单-保存/更新
const PostRolesSaveRoles = (params) => {
  return postRolesSaveRoles(params)
}
// 菜单-同步
const PostRolesSyncRoles = (params) => {
  return postRolesSyncRoles(params)
}
export default {
  PostRolesGetRolesAll,
  PostRolesDeleteRoles,
  PostRolesSaveRoles,
  PostRolesSyncRoles
}
