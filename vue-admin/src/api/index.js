export function setApi(http, setBaseUrl, path) {
  setBaseUrl('/api' + path)
  // 拦截器，为后端每一个请求加上authorization
  http().interceptors.request.use(
    (config) => {
      //添加token
      // const token = getToken()
      // if (token) {
      //   config.headers['Authorization'] = `Bearer ${token}`
      // }
      return config
    },
    (error) => {
      return Promise.reject(error)
    }
  )
}
