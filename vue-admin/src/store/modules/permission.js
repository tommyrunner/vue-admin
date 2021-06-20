import { rolesRoutes, constantRoutes } from '@/router'

/**
 * Use meta.role to determine if the current user has permission
 * @param roles
 * @param route
 */
function hasPermission(roles, route) {
  // 如果是存有children直接通过-留住最外层路由,然后在layout里判断如果item一个都没就隐藏
  if (route.meta && route.name && route.name && !route.children) {
    return roles.some((role) => {
      return role.roles === route.name
    })
  } else {
    //在外层直接分开,不过必须有item
    return route.children.length > 0
  }
}

/**
 * Filter asynchronous routing tables by recursion
 * @param routes rolesRoutes
 * @param roles
 */
export function filterAsyncRoutes(routes, roles) {
  const res = []
  routes.forEach((route) => {
    const tmp = { ...route }
    if (tmp.children) {
      tmp.children = filterAsyncRoutes(tmp.children, roles)
    }
    if (hasPermission(roles, tmp)) {
      res.push(tmp)
    }
  })
  return res
}

const state = {
  routes: [],
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
  }
}

const actions = {
  generateRoutes({ commit }, roles) {
    return new Promise((resolve) => {
      let accessedRoutes
      accessedRoutes = filterAsyncRoutes(rolesRoutes, roles)
      commit('SET_ROUTES', accessedRoutes)
      resolve(accessedRoutes)
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
