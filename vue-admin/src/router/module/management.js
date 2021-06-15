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
      name: 'ManagementSystem',
      component: () => import('@/views/management/system/index'),
      meta: { title: 'ManagementSystem', icon: 'system' }
    },
    {
      path: 'user',
      name: 'ManagementUser',
      component: () => import('@/views/management/user/index'),
      meta: { title: 'ManagementUser', icon: 'user' }
    },
    {
      path: 'roles',
      name: 'ManagementRoles',
      component: () => import('@/views/management/roles/index'),
      meta: { title: 'ManagementRoles', icon: 'roles' }
    }
  ]
}

export default managementRoute
