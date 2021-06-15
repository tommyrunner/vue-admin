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
    config = {};
  }
  config.url = url;
  config.params = queryParameters;
  config.method = method;
  const formDataExist = formData.entries().next().value;
  const bodyExist = body && Object.keys(body).length > 0;
  if (formDataExist) {
    config.data = formData;
  } else if (bodyExist) {
    config.data = body;
  }
  return axiosInstance.request(config);
}
/*==========================================================
 *                    文档描述
 ==========================================================*/
const isArray = (val) => !!val && Array.isArray(val)
/**
 * getRolesByUserIdAll
 * request: getRolesGetRolesByUserIdAll
 * url: getRolesGetRolesByUserIdAllURL
 * method: getRolesGetRolesByUserIdAll_TYPE
 * raw_url: getRolesGetRolesByUserIdAll_RAW_URL
 * @param userId - userId
 */
export const getRolesGetRolesByUserIdAll = function(parameters = {}) {
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  const config = parameters.$config
  let path = '/roles/getRolesByUserIdAll'
  let body = {};
  let queryParameters = {};
  let formData = new FormData();
  if (parameters['userId'] !== undefined) {
    queryParameters['userId'] = parameters['userId']
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    });
  }
  return request('get', domain + path, body, queryParameters, formData, config)
}
export const getRolesGetRolesByUserIdAll_RAW_URL = function() {
  return '/roles/getRolesByUserIdAll'
}
export const getRolesGetRolesByUserIdAll_TYPE = function() {
  return 'get'
}
export const getRolesGetRolesByUserIdAllURL = function(parameters = {}) {
  let queryParameters = {}
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  let path = '/roles/getRolesByUserIdAll'
  if (parameters['userId'] !== undefined) {
    queryParameters['userId'] = parameters['userId']
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    })
  }
  let keys = Object.keys(queryParameters)
  return domain + path + (keys.length > 0 ? '?' + (keys.map(key => key + '=' + encodeURIComponent(queryParameters[key])).join('&')) : '')
}
/**
 * saveRolesByUserId
 * request: postRolesSaveRolesByUserId
 * url: postRolesSaveRolesByUserIdURL
 * method: postRolesSaveRolesByUserId_TYPE
 * raw_url: postRolesSaveRolesByUserId_RAW_URL
 * @param map - map
 * @param rolesAll - 权限列表Roles
 * @param userId - 用户唯一id
 */
export const postRolesSaveRolesByUserId = function(parameters = {}) {
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  const config = parameters.$config
  let path = '/roles/saveRolesByUserId'
  let body = {};
  let queryParameters = {};
  let formData = new FormData();
  if (parameters.$body) {
    body = parameters.$body;
  } else {
    body = parameters
  }
  if (parameters['rolesAll'] === undefined) {
    return Promise.reject(new Error('Missing parameter : rolesAll'))
  }
  if (parameters['userId'] === undefined) {
    return Promise.reject(new Error('Missing parameter : userId'))
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    });
  }
  return request('post', domain + path, body, queryParameters, formData, config)
}
export const postRolesSaveRolesByUserId_RAW_URL = function() {
  return '/roles/saveRolesByUserId'
}
export const postRolesSaveRolesByUserId_TYPE = function() {
  return 'post'
}
export const postRolesSaveRolesByUserIdURL = function(parameters = {}) {
  let queryParameters = {}
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  let path = '/roles/saveRolesByUserId'
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    })
  }
  let keys = Object.keys(queryParameters)
  return domain + path + (keys.length > 0 ? '?' + (keys.map(key => key + '=' + encodeURIComponent(queryParameters[key])).join('&')) : '')
}
/**
 * deleteTable
 * request: postTableDeleteTable
 * url: postTableDeleteTableURL
 * method: postTableDeleteTable_TYPE
 * raw_url: postTableDeleteTable_RAW_URL
 * @param tableEntity - tableEntity
 */
export const postTableDeleteTable = function(parameters = {}) {
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  const config = parameters.$config
  let path = '/table/deleteTable'
  let body = {};
  let queryParameters = {};
  let formData = new FormData();
  if (parameters.$body) {
    body = parameters.$body;
  } else {
    body = parameters
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    });
  }
  return request('post', domain + path, body, queryParameters, formData, config)
}
export const postTableDeleteTable_RAW_URL = function() {
  return '/table/deleteTable'
}
export const postTableDeleteTable_TYPE = function() {
  return 'post'
}
export const postTableDeleteTableURL = function(parameters = {}) {
  let queryParameters = {}
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  let path = '/table/deleteTable'
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    })
  }
  let keys = Object.keys(queryParameters)
  return domain + path + (keys.length > 0 ? '?' + (keys.map(key => key + '=' + encodeURIComponent(queryParameters[key])).join('&')) : '')
}
/**
 * getTableAll
 * request: getTableGetTableAll
 * url: getTableGetTableAllURL
 * method: getTableGetTableAll_TYPE
 * raw_url: getTableGetTableAll_RAW_URL
 */
export const getTableGetTableAll = function(parameters = {}) {
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  const config = parameters.$config
  let path = '/table/getTableAll'
  let body = {};
  let queryParameters = {};
  let formData = new FormData();
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    });
  }
  return request('get', domain + path, body, queryParameters, formData, config)
}
export const getTableGetTableAll_RAW_URL = function() {
  return '/table/getTableAll'
}
export const getTableGetTableAll_TYPE = function() {
  return 'get'
}
export const getTableGetTableAllURL = function(parameters = {}) {
  let queryParameters = {}
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  let path = '/table/getTableAll'
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    })
  }
  let keys = Object.keys(queryParameters)
  return domain + path + (keys.length > 0 ? '?' + (keys.map(key => key + '=' + encodeURIComponent(queryParameters[key])).join('&')) : '')
}
/**
 * saveTable
 * request: postTableSaveTable
 * url: postTableSaveTableURL
 * method: postTableSaveTable_TYPE
 * raw_url: postTableSaveTable_RAW_URL
 * @param tableEntity - tableEntity
 */
export const postTableSaveTable = function(parameters = {}) {
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  const config = parameters.$config
  let path = '/table/saveTable'
  let body = {};
  let queryParameters = {};
  let formData = new FormData();
  if (parameters.$body) {
    body = parameters.$body;
  } else {
    body = parameters
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    });
  }
  return request('post', domain + path, body, queryParameters, formData, config)
}
export const postTableSaveTable_RAW_URL = function() {
  return '/table/saveTable'
}
export const postTableSaveTable_TYPE = function() {
  return 'post'
}
export const postTableSaveTableURL = function(parameters = {}) {
  let queryParameters = {}
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  let path = '/table/saveTable'
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    })
  }
  let keys = Object.keys(queryParameters)
  return domain + path + (keys.length > 0 ? '?' + (keys.map(key => key + '=' + encodeURIComponent(queryParameters[key])).join('&')) : '')
}
/**
 * toError-返回错误
 * request: getToError
 * url: getToErrorURL
 * method: getToError_TYPE
 * raw_url: getToError_RAW_URL
 * @param code - code
 * @param msg - msg
 */
export const getToError = function(parameters = {}) {
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  const config = parameters.$config
  let path = '/toError'
  let body = {};
  let queryParameters = {};
  let formData = new FormData();
  if (parameters['code'] !== undefined) {
    queryParameters['code'] = parameters['code']
  }
  if (parameters['msg'] !== undefined) {
    queryParameters['msg'] = parameters['msg']
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    });
  }
  return request('get', domain + path, body, queryParameters, formData, config)
}
export const getToError_RAW_URL = function() {
  return '/toError'
}
export const getToError_TYPE = function() {
  return 'get'
}
export const getToErrorURL = function(parameters = {}) {
  let queryParameters = {}
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  let path = '/toError'
  if (parameters['code'] !== undefined) {
    queryParameters['code'] = parameters['code']
  }
  if (parameters['msg'] !== undefined) {
    queryParameters['msg'] = parameters['msg']
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    })
  }
  let keys = Object.keys(queryParameters)
  return domain + path + (keys.length > 0 ? '?' + (keys.map(key => key + '=' + encodeURIComponent(queryParameters[key])).join('&')) : '')
}
/**
 * toError-返回错误
 * request: headToError
 * url: headToErrorURL
 * method: headToError_TYPE
 * raw_url: headToError_RAW_URL
 * @param code - code
 * @param msg - msg
 */
export const headToError = function(parameters = {}) {
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  const config = parameters.$config
  let path = '/toError'
  let body = {};
  let queryParameters = {};
  let formData = new FormData();
  if (parameters['code'] !== undefined) {
    queryParameters['code'] = parameters['code']
  }
  if (parameters['msg'] !== undefined) {
    queryParameters['msg'] = parameters['msg']
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    });
  }
  return request('head', domain + path, body, queryParameters, formData, config)
}
export const headToError_RAW_URL = function() {
  return '/toError'
}
export const headToError_TYPE = function() {
  return 'head'
}
export const headToErrorURL = function(parameters = {}) {
  let queryParameters = {}
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  let path = '/toError'
  if (parameters['code'] !== undefined) {
    queryParameters['code'] = parameters['code']
  }
  if (parameters['msg'] !== undefined) {
    queryParameters['msg'] = parameters['msg']
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    })
  }
  let keys = Object.keys(queryParameters)
  return domain + path + (keys.length > 0 ? '?' + (keys.map(key => key + '=' + encodeURIComponent(queryParameters[key])).join('&')) : '')
}
/**
 * toError-返回错误
 * request: postToError
 * url: postToErrorURL
 * method: postToError_TYPE
 * raw_url: postToError_RAW_URL
 * @param code - code
 * @param msg - msg
 */
export const postToError = function(parameters = {}) {
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  const config = parameters.$config
  let path = '/toError'
  let body = {};
  let queryParameters = {};
  let formData = new FormData();
  if (parameters['code'] !== undefined) {
    queryParameters['code'] = parameters['code']
  }
  if (parameters['msg'] !== undefined) {
    queryParameters['msg'] = parameters['msg']
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    });
  }
  return request('post', domain + path, body, queryParameters, formData, config)
}
export const postToError_RAW_URL = function() {
  return '/toError'
}
export const postToError_TYPE = function() {
  return 'post'
}
export const postToErrorURL = function(parameters = {}) {
  let queryParameters = {}
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  let path = '/toError'
  if (parameters['code'] !== undefined) {
    queryParameters['code'] = parameters['code']
  }
  if (parameters['msg'] !== undefined) {
    queryParameters['msg'] = parameters['msg']
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    })
  }
  let keys = Object.keys(queryParameters)
  return domain + path + (keys.length > 0 ? '?' + (keys.map(key => key + '=' + encodeURIComponent(queryParameters[key])).join('&')) : '')
}
/**
 * toError-返回错误
 * request: putToError
 * url: putToErrorURL
 * method: putToError_TYPE
 * raw_url: putToError_RAW_URL
 * @param code - code
 * @param msg - msg
 */
export const putToError = function(parameters = {}) {
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  const config = parameters.$config
  let path = '/toError'
  let body = {};
  let queryParameters = {};
  let formData = new FormData();
  if (parameters['code'] !== undefined) {
    queryParameters['code'] = parameters['code']
  }
  if (parameters['msg'] !== undefined) {
    queryParameters['msg'] = parameters['msg']
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    });
  }
  return request('put', domain + path, body, queryParameters, formData, config)
}
export const putToError_RAW_URL = function() {
  return '/toError'
}
export const putToError_TYPE = function() {
  return 'put'
}
export const putToErrorURL = function(parameters = {}) {
  let queryParameters = {}
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  let path = '/toError'
  if (parameters['code'] !== undefined) {
    queryParameters['code'] = parameters['code']
  }
  if (parameters['msg'] !== undefined) {
    queryParameters['msg'] = parameters['msg']
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    })
  }
  let keys = Object.keys(queryParameters)
  return domain + path + (keys.length > 0 ? '?' + (keys.map(key => key + '=' + encodeURIComponent(queryParameters[key])).join('&')) : '')
}
/**
 * toError-返回错误
 * request: deleteToError
 * url: deleteToErrorURL
 * method: deleteToError_TYPE
 * raw_url: deleteToError_RAW_URL
 * @param code - code
 * @param msg - msg
 */
export const deleteToError = function(parameters = {}) {
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  const config = parameters.$config
  let path = '/toError'
  let body = {};
  let queryParameters = {};
  let formData = new FormData();
  if (parameters['code'] !== undefined) {
    queryParameters['code'] = parameters['code']
  }
  if (parameters['msg'] !== undefined) {
    queryParameters['msg'] = parameters['msg']
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    });
  }
  return request('delete', domain + path, body, queryParameters, formData, config)
}
export const deleteToError_RAW_URL = function() {
  return '/toError'
}
export const deleteToError_TYPE = function() {
  return 'delete'
}
export const deleteToErrorURL = function(parameters = {}) {
  let queryParameters = {}
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  let path = '/toError'
  if (parameters['code'] !== undefined) {
    queryParameters['code'] = parameters['code']
  }
  if (parameters['msg'] !== undefined) {
    queryParameters['msg'] = parameters['msg']
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    })
  }
  let keys = Object.keys(queryParameters)
  return domain + path + (keys.length > 0 ? '?' + (keys.map(key => key + '=' + encodeURIComponent(queryParameters[key])).join('&')) : '')
}
/**
 * toError-返回错误
 * request: optionsToError
 * url: optionsToErrorURL
 * method: optionsToError_TYPE
 * raw_url: optionsToError_RAW_URL
 * @param code - code
 * @param msg - msg
 */
export const optionsToError = function(parameters = {}) {
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  const config = parameters.$config
  let path = '/toError'
  let body = {};
  let queryParameters = {};
  let formData = new FormData();
  if (parameters['code'] !== undefined) {
    queryParameters['code'] = parameters['code']
  }
  if (parameters['msg'] !== undefined) {
    queryParameters['msg'] = parameters['msg']
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    });
  }
  return request('options', domain + path, body, queryParameters, formData, config)
}
export const optionsToError_RAW_URL = function() {
  return '/toError'
}
export const optionsToError_TYPE = function() {
  return 'options'
}
export const optionsToErrorURL = function(parameters = {}) {
  let queryParameters = {}
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  let path = '/toError'
  if (parameters['code'] !== undefined) {
    queryParameters['code'] = parameters['code']
  }
  if (parameters['msg'] !== undefined) {
    queryParameters['msg'] = parameters['msg']
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    })
  }
  let keys = Object.keys(queryParameters)
  return domain + path + (keys.length > 0 ? '?' + (keys.map(key => key + '=' + encodeURIComponent(queryParameters[key])).join('&')) : '')
}
/**
 * toError-返回错误
 * request: patchToError
 * url: patchToErrorURL
 * method: patchToError_TYPE
 * raw_url: patchToError_RAW_URL
 * @param code - code
 * @param msg - msg
 */
export const patchToError = function(parameters = {}) {
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  const config = parameters.$config
  let path = '/toError'
  let body = {};
  let queryParameters = {};
  let formData = new FormData();
  if (parameters['code'] !== undefined) {
    queryParameters['code'] = parameters['code']
  }
  if (parameters['msg'] !== undefined) {
    queryParameters['msg'] = parameters['msg']
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    });
  }
  return request('patch', domain + path, body, queryParameters, formData, config)
}
export const patchToError_RAW_URL = function() {
  return '/toError'
}
export const patchToError_TYPE = function() {
  return 'patch'
}
export const patchToErrorURL = function(parameters = {}) {
  let queryParameters = {}
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  let path = '/toError'
  if (parameters['code'] !== undefined) {
    queryParameters['code'] = parameters['code']
  }
  if (parameters['msg'] !== undefined) {
    queryParameters['msg'] = parameters['msg']
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    })
  }
  let keys = Object.keys(queryParameters)
  return domain + path + (keys.length > 0 ? '?' + (keys.map(key => key + '=' + encodeURIComponent(queryParameters[key])).join('&')) : '')
}
/**
 * loginCode
 * request: getUserCode
 * url: getUserCodeURL
 * method: getUserCode_TYPE
 * raw_url: getUserCode_RAW_URL
 * @param uuid - uuid
 */
export const getUserCode = function(parameters = {}) {
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  const config = parameters.$config
  let path = '/user/code'
  let body = {};
  let queryParameters = {};
  let formData = new FormData();
  if (parameters['uuid'] !== undefined) {
    queryParameters['uuid'] = parameters['uuid']
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    });
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
  if (parameters['uuid'] !== undefined) {
    queryParameters['uuid'] = parameters['uuid']
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    })
  }
  let keys = Object.keys(queryParameters)
  return domain + path + (keys.length > 0 ? '?' + (keys.map(key => key + '=' + encodeURIComponent(queryParameters[key])).join('&')) : '')
}
/**
 * deleteUser
 * request: postUserDeleteUser
 * url: postUserDeleteUserURL
 * method: postUserDeleteUser_TYPE
 * raw_url: postUserDeleteUser_RAW_URL
 * @param map - map
 * @param userIds - 用户唯一id
 */
export const postUserDeleteUser = function(parameters = {}) {
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  const config = parameters.$config
  let path = '/user/deleteUser'
  let body = {};
  let queryParameters = {};
  let formData = new FormData();
  if (parameters.$body) {
    body = parameters.$body;
  } else {
    body = parameters
  }
  if (parameters['userIds'] === undefined) {
    return Promise.reject(new Error('Missing parameter : userIds'))
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    });
  }
  return request('post', domain + path, body, queryParameters, formData, config)
}
export const postUserDeleteUser_RAW_URL = function() {
  return '/user/deleteUser'
}
export const postUserDeleteUser_TYPE = function() {
  return 'post'
}
export const postUserDeleteUserURL = function(parameters = {}) {
  let queryParameters = {}
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  let path = '/user/deleteUser'
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    })
  }
  let keys = Object.keys(queryParameters)
  return domain + path + (keys.length > 0 ? '?' + (keys.map(key => key + '=' + encodeURIComponent(queryParameters[key])).join('&')) : '')
}
/**
 * getUserAll
 * request: postUserGetUserAll
 * url: postUserGetUserAllURL
 * method: postUserGetUserAll_TYPE
 * raw_url: postUserGetUserAll_RAW_URL
 * @param map - map
 * @param name - 模糊搜索-name
 * @param page - 当前页
 * @param pageSize - 一页多少个
 * @param sort - 升降序
 * @param sortKey - 升降序的键
 * @param user - 模糊搜索-user
 */
export const postUserGetUserAll = function(parameters = {}) {
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  const config = parameters.$config
  let path = '/user/getUserAll'
  let body = {};
  let queryParameters = {};
  let formData = new FormData();
  if (parameters.$body) {
    body = parameters.$body;
  } else {
    body = parameters
  }
  if (parameters['page'] === undefined) {
    return Promise.reject(new Error('Missing parameter : page'))
  }
  if (parameters['pageSize'] === undefined) {
    return Promise.reject(new Error('Missing parameter : pageSize'))
  }
  if (parameters['sort'] === undefined) {
    return Promise.reject(new Error('Missing parameter : sort'))
  }
  if (parameters['sortKey'] === undefined) {
    return Promise.reject(new Error('Missing parameter : sortKey'))
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    });
  }
  return request('post', domain + path, body, queryParameters, formData, config)
}
export const postUserGetUserAll_RAW_URL = function() {
  return '/user/getUserAll'
}
export const postUserGetUserAll_TYPE = function() {
  return 'post'
}
export const postUserGetUserAllURL = function(parameters = {}) {
  let queryParameters = {}
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  let path = '/user/getUserAll'
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    })
  }
  let keys = Object.keys(queryParameters)
  return domain + path + (keys.length > 0 ? '?' + (keys.map(key => key + '=' + encodeURIComponent(queryParameters[key])).join('&')) : '')
}
/**
 * getUserInfo
 * request: getUserGetUserInfo
 * url: getUserGetUserInfoURL
 * method: getUserGetUserInfo_TYPE
 * raw_url: getUserGetUserInfo_RAW_URL
 * @param token - token
 */
export const getUserGetUserInfo = function(parameters = {}) {
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  const config = parameters.$config
  let path = '/user/getUserInfo'
  let body = {};
  let queryParameters = {};
  let formData = new FormData();
  if (parameters['token'] !== undefined) {
    queryParameters['token'] = parameters['token']
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    });
  }
  return request('get', domain + path, body, queryParameters, formData, config)
}
export const getUserGetUserInfo_RAW_URL = function() {
  return '/user/getUserInfo'
}
export const getUserGetUserInfo_TYPE = function() {
  return 'get'
}
export const getUserGetUserInfoURL = function(parameters = {}) {
  let queryParameters = {}
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  let path = '/user/getUserInfo'
  if (parameters['token'] !== undefined) {
    queryParameters['token'] = parameters['token']
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    })
  }
  let keys = Object.keys(queryParameters)
  return domain + path + (keys.length > 0 ? '?' + (keys.map(key => key + '=' + encodeURIComponent(queryParameters[key])).join('&')) : '')
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
 * @param uuid - 设备id
 */
export const postUserLogin = function(parameters = {}) {
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  const config = parameters.$config
  let path = '/user/login'
  let body = {};
  let queryParameters = {};
  let formData = new FormData();
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
    body = parameters.$body;
  } else {
    body = parameters
  }
  if (parameters['uuid'] === undefined) {
    return Promise.reject(new Error('Missing parameter : uuid'))
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    });
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
  return domain + path + (keys.length > 0 ? '?' + (keys.map(key => key + '=' + encodeURIComponent(queryParameters[key])).join('&')) : '')
}
/**
 * loginOut
 * request: getUserLoginOut
 * url: getUserLoginOutURL
 * method: getUserLoginOut_TYPE
 * raw_url: getUserLoginOut_RAW_URL
 * @param token - token
 */
export const getUserLoginOut = function(parameters = {}) {
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  const config = parameters.$config
  let path = '/user/loginOut'
  let body = {};
  let queryParameters = {};
  let formData = new FormData();
  if (parameters['token'] !== undefined) {
    queryParameters['token'] = parameters['token']
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    });
  }
  return request('get', domain + path, body, queryParameters, formData, config)
}
export const getUserLoginOut_RAW_URL = function() {
  return '/user/loginOut'
}
export const getUserLoginOut_TYPE = function() {
  return 'get'
}
export const getUserLoginOutURL = function(parameters = {}) {
  let queryParameters = {}
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  let path = '/user/loginOut'
  if (parameters['token'] !== undefined) {
    queryParameters['token'] = parameters['token']
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    })
  }
  let keys = Object.keys(queryParameters)
  return domain + path + (keys.length > 0 ? '?' + (keys.map(key => key + '=' + encodeURIComponent(queryParameters[key])).join('&')) : '')
}
/**
 * resetUserPwd
 * request: getUserResetUserPwd
 * url: getUserResetUserPwdURL
 * method: getUserResetUserPwd_TYPE
 * raw_url: getUserResetUserPwd_RAW_URL
 * @param userId - userId
 */
export const getUserResetUserPwd = function(parameters = {}) {
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  const config = parameters.$config
  let path = '/user/resetUserPwd'
  let body = {};
  let queryParameters = {};
  let formData = new FormData();
  if (parameters['userId'] !== undefined) {
    queryParameters['userId'] = parameters['userId']
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    });
  }
  return request('get', domain + path, body, queryParameters, formData, config)
}
export const getUserResetUserPwd_RAW_URL = function() {
  return '/user/resetUserPwd'
}
export const getUserResetUserPwd_TYPE = function() {
  return 'get'
}
export const getUserResetUserPwdURL = function(parameters = {}) {
  let queryParameters = {}
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  let path = '/user/resetUserPwd'
  if (parameters['userId'] !== undefined) {
    queryParameters['userId'] = parameters['userId']
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    })
  }
  let keys = Object.keys(queryParameters)
  return domain + path + (keys.length > 0 ? '?' + (keys.map(key => key + '=' + encodeURIComponent(queryParameters[key])).join('&')) : '')
}
/**
 * saveUser
 * request: postUserSaveUser
 * url: postUserSaveUserURL
 * method: postUserSaveUser_TYPE
 * raw_url: postUserSaveUser_RAW_URL
 * @param code - code
 * @param userEntity - userEntity
 */
export const postUserSaveUser = function(parameters = {}) {
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  const config = parameters.$config
  let path = '/user/saveUser'
  let body = {};
  let queryParameters = {};
  let formData = new FormData();
  if (parameters['code'] !== undefined) {
    queryParameters['code'] = parameters['code']
  }
  if (parameters.$body) {
    body = parameters.$body;
  } else {
    body = parameters
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    });
  }
  return request('post', domain + path, body, queryParameters, formData, config)
}
export const postUserSaveUser_RAW_URL = function() {
  return '/user/saveUser'
}
export const postUserSaveUser_TYPE = function() {
  return 'post'
}
export const postUserSaveUserURL = function(parameters = {}) {
  let queryParameters = {}
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  let path = '/user/saveUser'
  if (parameters['code'] !== undefined) {
    queryParameters['code'] = parameters['code']
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    })
  }
  let keys = Object.keys(queryParameters)
  return domain + path + (keys.length > 0 ? '?' + (keys.map(key => key + '=' + encodeURIComponent(queryParameters[key])).join('&')) : '')
}
/**
 * sendUserEmail
 * request: getUserSendEmail
 * url: getUserSendEmailURL
 * method: getUserSendEmail_TYPE
 * raw_url: getUserSendEmail_RAW_URL
 * @param email - email
 */
export const getUserSendEmail = function(parameters = {}) {
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  const config = parameters.$config
  let path = '/user/sendEmail'
  let body = {};
  let queryParameters = {};
  let formData = new FormData();
  if (parameters['email'] !== undefined) {
    queryParameters['email'] = parameters['email']
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    });
  }
  return request('get', domain + path, body, queryParameters, formData, config)
}
export const getUserSendEmail_RAW_URL = function() {
  return '/user/sendEmail'
}
export const getUserSendEmail_TYPE = function() {
  return 'get'
}
export const getUserSendEmailURL = function(parameters = {}) {
  let queryParameters = {}
  const domain = parameters.$domain ? parameters.$domain : getDomain()
  let path = '/user/sendEmail'
  if (parameters['email'] !== undefined) {
    queryParameters['email'] = parameters['email']
  }
  if (parameters.$queryParameters) {
    Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
      queryParameters[parameterName] = parameters.$queryParameters[parameterName]
    })
  }
  let keys = Object.keys(queryParameters)
  return domain + path + (keys.length > 0 ? '?' + (keys.map(key => key + '=' + encodeURIComponent(queryParameters[key])).join('&')) : '')
}