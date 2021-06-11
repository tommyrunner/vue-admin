import {
  getAxiosInstance,
  setDomain,
  postUserLogin,
  getUserCode,
  getUserGetUserInfo,
  getUserLoginOut,
  getUserGetUserAll,
  postUserSaveUser,
  postUserDeleteUser,
  getRolesGetRolesByUserIdAll
} from 'tommy-vueadmin-sdk'
//修改请求头
import { setApi } from './index'
//getAxiosInstance:获取path,setDomain:设置请求头的方法
setApi(getAxiosInstance, setDomain, '')

// 获取登录验证图片
const GetUserCode = (params) => {
  return getUserCode(params)
}
// 用户登录
const UserLogin = (params) => {
  return postUserLogin(params)
}
// 获取用户详情
const GetUserInfo = (params) => {
  return getUserGetUserInfo(params)
}

// 获取用户详情
const GetUserLoginOut = (params) => {
  return getUserLoginOut(params)
}
// 保存/修改用户
const PostUserSaveUser = (params) => {
  return postUserSaveUser(params)
}
// 批量删除用户
const PostUserDeleteUser = (params) => {
  return postUserDeleteUser(params)
}
// 获取所有用户
const GetUserGetUserAll = (params) => {
  return getUserGetUserAll(params)
}
// 根据用户id获取权限
const GetRolesByUserIdAll = (params) => {
  return getRolesGetRolesByUserIdAll(params)
}
export default {
  UserLogin,
  GetUserCode,
  GetUserInfo,
  GetUserLoginOut,
  GetUserGetUserAll,
  PostUserSaveUser,
  PostUserDeleteUser,
  GetRolesByUserIdAll
}
