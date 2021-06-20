const getters = {
  sidebar: (state) => state.app.sidebar,
  device: (state) => state.app.device,
  token: (state) => state.user.token,
  userInfo: (state) => state.user.userInfo,
  userRoles: (state) => state.user.roles,
  //动态获取的路由
  permission_routes: (state) => state.permission.routes
}
export default getters
