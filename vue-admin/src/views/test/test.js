import { getAxiosInstance, setDomain, postTestDeleteTest, postTestGetTestAll, postTestSaveTest, postTestSyncTest } from 'tommy-vueadmin-sdk'
//修改请求头
import { setApi } from '@/api/index'
//getAxiosInstance:获取path,setDomain:设置请求头的方法
setApi(getAxiosInstance, setDomain, '')

// 菜单-获取全部
const PostTestGetTestAll = (params) => {
  return postTestGetTestAll(params)
}
// 菜单-删除
const PostTestDeleteTest = (params) => {
  return postTestDeleteTest(params)
}
// 菜单-保存/更新
const PostTestSaveTest = (params) => {
  return postTestSaveTest(params)
}
// 菜单-同步
const PostTestSyncTest = (params) => {
  return postTestSyncTest(params)
}
export default {
  PostTestGetTestAll,
  PostTestDeleteTest,
  PostTestSaveTest,
  PostTestSyncTest
}
