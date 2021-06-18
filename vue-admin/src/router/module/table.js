/* Layout */
import Layout from '@/layout'
const tableRoute = {
  path: '/data',
  component: Layout,
  redirect: '/example/table',
  name: 'Data',
  meta: { title: 'Data', icon: 'data' },
  children: [
    {
      path: 'table',
      name: 'DataTable',
      // component: () => import('@/views/data/table/index'),
      meta: { title: 'DataTable', icon: 'table' }
    },
    {
      path: 'test',
      name: 'DataTest',
      component: () => import('@/views/data/test/index'),
      meta: { title: 'DataTest', icon: 'test' }
    }
  ]
}
export default tableRoute
