import { Loading } from 'element-ui'
import i18n from '@/lang/i18n'
/**
 * Created by PanJiaChen on 16/11/18.
 */

/**
 * 下载blob文件
 * @param {functions(){}} Api 函数-调用的api
 * @param {Object} params api需要传入的参数,默认以blob下载
 * @param {String} dowFileName 下载后的文件名默认dowFile
 * @param {String} dowFileSuf 下载后的文件名默认 xlsx
 * @returns
 */
export function dowBlobFile(Api = function() {}, params = {}, dowFileName = 'dowFile', dowFileSuf = 'xlsx') {
  // 默认以blob类型下载
  params = JSON.parse(JSON.stringify(params))
  params.$config = {
    responseType: 'blob'
  }
  return new Promise((resolve, reject) => {
    Api(params)
      .then((res) => {
        // 下载成功
        if (res.status === 200) {
          // 获取数据
          const content = res.data
          const blob = new Blob([content])
          // 如果文件不为空
          if (blob.size > 0) {
            // 使用a标签模拟点击下载
            const elink = document.createElement('a')
            // 给下载的文件名加上日期
            const date = new Date()
            const dateFrom = `${date.getFullYear()}-${date.getMonth() +
              1}-${date.getDate()} ${date.getHours()}-${date.getMinutes()}-${date.getSeconds()}`
            elink.download = `${dowFileName} ${dateFrom} .${dowFileSuf}`
            elink.style.display = 'none'
            elink.href = URL.createObjectURL(blob)
            document.body.appendChild(elink)
            elink.click()
            // 清空
            URL.revokeObjectURL(elink.href)
            document.body.removeChild(elink)
          } else {
            reject(new Error('下载文件失败-文件为空!'))
          }
        } else {
          reject(new Error('获取文件失败!'))
        }
        // 下载成功
        resolve()
      })
      .catch((e) => {
        reject(e)
      })
  })
}

/**
 * Parse the time to string
 * @param {(Object|string|number)} time
 * @param {string} cFormat
 * @returns {string | null}
 */
export function parseTime(time, cFormat) {
  if (arguments.length === 0 || !time) {
    return null
  }
  const format = cFormat || '{y}-{m}-{d} {h}:{i}:{s}'
  let date
  if (typeof time === 'object') {
    date = time
  } else {
    if (typeof time === 'string') {
      if (/^[0-9]+$/.test(time)) {
        // support "1548221490638"
        time = parseInt(time)
      } else {
        // support safari
        // https://stackoverflow.com/questions/4310953/invalid-date-in-safari
        time = time.replace(new RegExp(/-/gm), '/')
      }
    }

    if (typeof time === 'number' && time.toString().length === 10) {
      time = time * 1000
    }
    date = new Date(time)
  }
  const formatObj = {
    y: date.getFullYear(),
    m: date.getMonth() + 1,
    d: date.getDate(),
    h: date.getHours(),
    i: date.getMinutes(),
    s: date.getSeconds(),
    a: date.getDay()
  }
  const time_str = format.replace(/{([ymdhisa])+}/g, (result, key) => {
    const value = formatObj[key]
    // Note: getDay() returns 0 on Sunday
    if (key === 'a') {
      return ['日', '一', '二', '三', '四', '五', '六'][value]
    }
    return value.toString().padStart(2, '0')
  })
  return time_str
}

/**
 * @param {number} time
 * @param {string} option
 * @returns {string}
 */
export function formatTime(time, option) {
  if (('' + time).length === 10) {
    time = parseInt(time) * 1000
  } else {
    time = +time
  }
  const d = new Date(time)
  const now = Date.now()

  const diff = (now - d) / 1000

  if (diff < 30) {
    return '刚刚'
  } else if (diff < 3600) {
    // less 1 hour
    return Math.ceil(diff / 60) + '分钟前'
  } else if (diff < 3600 * 24) {
    return Math.ceil(diff / 3600) + '小时前'
  } else if (diff < 3600 * 24 * 2) {
    return '1天前'
  }
  if (option) {
    return parseTime(time, option)
  } else {
    return d.getMonth() + 1 + '月' + d.getDate() + '日' + d.getHours() + '时' + d.getMinutes() + '分'
  }
}

/**
 * @param {string} url
 * @returns {Object}
 */
export function param2Obj(url) {
  const search = decodeURIComponent(url.split('?')[1]).replace(/\+/g, ' ')
  if (!search) {
    return {}
  }
  const obj = {}
  const searchArr = search.split('&')
  searchArr.forEach((v) => {
    const index = v.indexOf('=')
    if (index !== -1) {
      const name = v.substring(0, index)
      const val = v.substring(index + 1, v.length)
      obj[name] = val
    }
  })
  return obj
}
export function generateUUID(withSeparator = true) {
  let d = new Date().getTime()
  if (window.performance && typeof window.performance.now === 'function') {
    d += performance.now()
  }
  const tpl = withSeparator ? 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx' : 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'
  return tpl.replace(/[xy]/g, function(c) {
    const r = (d + Math.random() * 16) % 16 | 0
    d = Math.floor(d / 16)
    return (c === 'x' ? r : (r & 0x3) | 0x8).toString(16)
  })
}

//定义全局加载动画
export function $Loading() {
  return Loading.service({
    lock: true,
    text: i18n.t('layout.loading'),
    spinner: 'el-icon-loading',
    background: 'rgba(0, 0, 0, 0.7)'
  })
}
//校验-判断邮箱
export function isEmail(s) {
  return /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(s)
}
