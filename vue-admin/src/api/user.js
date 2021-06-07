import { getAxiosInstance, setDomain, postUserLogin, getUserCode, getUserGetUserInfo } from 'tommy-vueadmin-sdk'

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
// 用户登录
const GetUserInfo = (params) => {
  return getUserGetUserInfo(params)
}
export default {
  UserLogin,
  GetUserCode,
  GetUserInfo
}
