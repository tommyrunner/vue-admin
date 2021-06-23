import { getAxiosInstance, setDomain, postTestDeleteTest, postTestGetTestAll, postTestSaveTest } from 'tommy-vueadmin-sdk'
//修改请求头
import { setApi } from '@/api/index'
//getAxiosInstance:获取path,setDomain:设置请求头的方法
setApi(getAxiosInstance, setDomain, '')

// -获取全部
const PostTestGetTestAll = (params) => {
  return postTestGetTestAll(params)
}
// -删除
const PostTestDeleteTest = (params) => {
  return postTestDeleteTest(params)
}
// -保存/更新
const PostTestSaveTest = (params) => {
  return postTestSaveTest(params)
}
// -同步
const PostTestSyncTest = (params) => {
  return postTestSyncTest(params)
}
export default {
  PostTestGetTestAll,
  PostTestDeleteTest,
  PostTestSaveTest
}
