/* Layout */
import Layout from '@/layout'
const managementRoute = {
  path: '/management',
  component: Layout,
  redirect: '/management/system',
  name: 'Management',
  meta: { title: 'Management', icon: 'management' },
  children: [
    {
      path: 'system',
      name: 'System',
      component: () => import('@/views/management/system/index'),
      meta: { title: 'System', icon: 'system' }
    },
    {
      path: 'user',
      name: 'User',
      component: () => import('@/views/management/user/index'),
      meta: { title: 'User', icon: 'user' }
    }
  ]
}

export default managementRoute
