/* Layout */
import Layout from '@/layout'
const dataRoute = {
  path: '/data',
  component: Layout,
  redirect: '/data/test',
  name: 'Data',
  meta: { title: 'Data', icon: 'data' },
  children: [
    {
      path: 'test',
      name: 'DataTest',
      component: () => import('@/views/data/test/index'),
      meta: { title: 'DataTest', icon: 'test' }
    },
    {
      path: 'table',
      name: 'DataTable',
      component: () => import('@/views/data/table/index'),
      meta: { title: 'DataTable', icon: 'table' }
    }
  ]
}
export default dataRoute
