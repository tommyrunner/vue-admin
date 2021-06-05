/* eslint-disable */
import axios from 'axios'
import qs from 'qs'
let domain = ''
let axiosInstance = axios.create()
export const getDomain = () => {
  return domain
}
export const setDomain = ($domain) => {
  domain = $domain
}
export const getAxiosInstance = () => {
  return axiosInstance
}
export const setAxiosInstance = ($axiosInstance) => {
  axiosInstance = $axiosInstance
}
export const request = (method, url, body, queryParameters, formData, config) => {
  // method = method.toLowerCase()
  // let keys = Object.keys(queryParameters)
  // let queryUrl = url
  // if ( keys.length > 0 ) {
  //     queryUrl = url + '?' + qs.stringify(queryParameters)
  // }
  // if(body && Object.keys(body).length > 0){
  //     return axiosInstance[method](queryUrl,body,config)
  // } else if (method === 'get' || method === 'delete' || method === 'head' || method === 'option') {
  //     return axiosInstance[method](queryUrl,config)
  // } else {
  //     return axiosInstance[method](queryUrl,formData,config)
  // }
  method = method.toLowerCase()
  if (!config) {
    config = {}
  }
  config.url = url
  config.params = queryParameters
  config.method = method
  const formDataExist = formData.entries().next().value
  const bodyExist = body && Object.keys(body).length > 0
  if (formDataExist) {
    config.data = formData
  } else if (bodyExist) {
    config.data = body
  }
  return axiosInstance.request(config)
}
/*==========================================================
 *                    文档描述
 ==========================================================*/
const isArray = (val) => !!val && Array.isArray(val)
/**
 * loginCode
 * request: getUserCode
 * url: getUserCodeURL
 * method: getUserCode_TYPE
 * raw_url: getUserCode_RAW_URL
 */
export const getUserCode = function(parameters = {}) {
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  const config = parameters.$config
  let path = '/user/code'
  let body = {}
  let queryParameters = {}
  let formData = new FormData()
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    })
  }
  return request('get', domain + path, body, queryParameters, formData, config)
}
export const getUserCode_RAW_URL = function() {
  return '/user/code'
}
export const getUserCode_TYPE = function() {
  return 'get'
}
export const getUserCodeURL = function(parameters = {}) {
  let queryParameters = {}
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  let path = '/user/code'
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    })
  }
  let keys = Object.keys(queryParameters)
  return domain + path + (keys.length > 0 ? '?' + keys.map((key) => key + '=' + encodeURIComponent(queryParameters[key])).join('&') : '')
}
/**
 * userLogin
 * request: postUserLogin
 * url: postUserLoginURL
 * method: postUserLogin_TYPE
 * raw_url: postUserLogin_RAW_URL
 * @param code - 登录验证码
 * @param pwd - 用户密码
 * @param user - 用户
 * @param userMap - 接收对象
 */
export const postUserLogin = function(parameters = {}) {
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  const config = parameters.$config
  let path = '/user/login'
  let body = {}
  let queryParameters = {}
  let formData = new FormData()
  if (parameters['code'] === undefined) {
    return Promise.reject(new Error('Missing parameter : code'))
  }
  if (parameters['pwd'] === undefined) {
    return Promise.reject(new Error('Missing parameter : pwd'))
  }
  if (parameters['user'] === undefined) {
    return Promise.reject(new Error('Missing parameter : user'))
  }
  if (parameters.$body) {
    body = parameters.$body
  } else {
    body = parameters
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    })
  }
  return request('post', domain + path, body, queryParameters, formData, config)
}
export const postUserLogin_RAW_URL = function() {
  return '/user/login'
}
export const postUserLogin_TYPE = function() {
  return 'post'
}
export const postUserLoginURL = function(parameters = {}) {
  let queryParameters = {}
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  let path = '/user/login'
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    })
  }
  let keys = Object.keys(queryParameters)
  return domain + path + (keys.length > 0 ? '?' + keys.map((key) => key + '=' + encodeURIComponent(queryParameters[key])).join('&') : '')
}
