import { getToken } from '@/utils/auth'
import { Message } from 'element-ui'
export function setApi(http, setBaseUrl, path) {
  setBaseUrl('/api' + path)
  // 拦截器，为后端每一个请求加上authorization
  http().interceptors.request.use(
    (config) => {
      //添加token
      const token = getToken()
      if (token) {
        config.headers['Authorization'] = `Bearer ${token}`
      }
      return config
    },
    (error) => {
      return Promise.reject(error)
    }
  )
  // 拦截器 处理错误
  http().interceptors.response.use(
    (response) => {
      // 成功拦截
      const { data } = response
      if (data.code != undefined && data.code !== 200 && data.msg) {
        Message.error({ message: data.msg })
      }
      return response
    },
    // 错误拦截
    async ({ errorResponse }) => {
      console.log('-------接口错误:')
      return Promise.reject(errorResponse) // 返回接口返回的错误信息
    }
  )
}
