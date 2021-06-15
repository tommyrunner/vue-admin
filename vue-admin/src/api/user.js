import {
  getAxiosInstance,
  setDomain,
  postUserLogin,
  getUserCode,
  getUserGetUserInfo,
  getUserLoginOut,
  postUserGetUserAll,
  postUserSaveUser,
  postUserDeleteUser,
  getRolesGetRolesByUserIdAll,
  getUserSendEmail,
  postRolesSaveRolesByUserId,
  getUserResetUserPwd
} from 'tommy-vueadmin-sdk'
//修改请求头
import { setApi } from './index'
//getAxiosInstance:获取path,setDomain:设置请求头的方法
setApi(getAxiosInstance, setDomain, '')

// 获取登录验证图片
const GetUserCode = (params) => {
  return getUserCode(params)
}
// 发送验证码
const GetUserSendEmail = (params) => {
  return getUserSendEmail(params)
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
const PostUserGetUserAll = (params) => {
  return postUserGetUserAll(params)
}
// 根据用户id获取权限
const GetRolesByUserIdAll = (params) => {
  return getRolesGetRolesByUserIdAll(params)
}
// 根据用户id批量添加权限
const PostRolesSaveRolesByUserId = (params) => {
  return postRolesSaveRolesByUserId(params)
}
// 重置密码
const GetUserResetUserPwd = (params) => {
  return getUserResetUserPwd(params)
}
export default {
  UserLogin,
  GetUserSendEmail,
  GetUserCode,
  GetUserInfo,
  GetUserLoginOut,
  PostUserGetUserAll,
  PostUserSaveUser,
  PostUserDeleteUser,
  GetRolesByUserIdAll,
  PostRolesSaveRolesByUserId,
  GetUserResetUserPwd
}
