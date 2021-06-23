import Cookies from 'js-cookie'
import { constantRoutes } from '@/router'
import i18n from '@/lang/i18n'
import langEn from 'element-ui/lib/locale/lang/en'
import langZh from 'element-ui/lib/locale/lang/zh-CN'

const state = {
  sidebar: {
    opened: Cookies.get('sidebarStatus') ? !!+Cookies.get('sidebarStatus') : true,
    withoutAnimation: false
  },
  routes: [],
  device: 'desktop',
  langs: [
    { label: i18n.t('langs.zh'), value: 'zh', eleLang: langZh },
    { label: i18n.t('langs.en'), value: 'en', eleLang: langEn }
  ]
}

const mutations = {
  TOGGLE_SIDEBAR: (state) => {
    state.sidebar.opened = !state.sidebar.opened
    state.sidebar.withoutAnimation = false
    if (state.sidebar.opened) {
      Cookies.set('sidebarStatus', 1)
    } else {
      Cookies.set('sidebarStatus', 0)
    }
  },
  CLOSE_SIDEBAR: (state, withoutAnimation) => {
    Cookies.set('sidebarStatus', 0)
    state.sidebar.opened = false
    state.sidebar.withoutAnimation = withoutAnimation
  },
  TOGGLE_DEVICE: (state, device) => {
    state.device = device
  }
}

const actions = {
  GenerateRoutes({ commit }, data) {
    return new Promise((resolve) => {
      // const { roles } = data
      commit('SET_ROUTES', constantRoutes)
      resolve(constantRoutes)
    })
  },
  toggleSideBar({ commit }) {
    commit('TOGGLE_SIDEBAR')
  },
  closeSideBar({ commit }, { withoutAnimation }) {
    commit('CLOSE_SIDEBAR', withoutAnimation)
  },
  toggleDevice({ commit }, device) {
    commit('TOGGLE_DEVICE', device)
  },
  setLanguage({ commit }, language) {
    commit('SET_LANGUAGE', language)
  },
  setSize({ commit }, size) {
    commit('SET_SIZE', size)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
