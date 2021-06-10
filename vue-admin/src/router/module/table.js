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
      name: 'Table',
      component: () => import('@/views/data/table/index'),
      meta: { title: 'Table', icon: 'table' }
    }
  ]
}
export default tableRoute
