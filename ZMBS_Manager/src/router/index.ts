import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '@/views/LoginView.vue'
import Layout from '@/layout/Layout.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: LoginView,
      meta: { title: '登录', requiresAuth: false }
    },
    {
      path: '/',
      component: Layout,
      redirect: '/dashboard',
      children: [
        {
          path: 'dashboard',
          name: 'Dashboard',
          component: () => import('@/views/DashboardView.vue'),
          meta: { title: '仪表盘', requiresAuth: true }
        }
      ]
    },
    {
      path: '/user',
      component: Layout,
      redirect: '/user/list',
      meta: { title: '用户管理', requiresAuth: true },
      children: [
        {
          path: 'list',
          name: 'UserList',
          component: () => import('@/views/user/UserListView.vue'),
          meta: { title: '用户列表', requiresAuth: true }
        },
        {
          path: 'add',
          name: 'UserAdd',
          component: () => import('@/views/user/UserAddView.vue'),
          meta: { title: '添加用户', requiresAuth: true }
        }
      ]
    },
    {
      path: '/order',
      component: Layout,
      redirect: '/order/list',
      meta: { title: '订单管理', requiresAuth: true },
      children: [
        {
          path: 'list',
          name: 'OrderList',
          component: () => import('@/views/order/OrderListView.vue'),
          meta: { title: '订单列表', requiresAuth: true }
        },
        {
          path: 'statistics',
          name: 'OrderStatistics',
          component: () => import('@/views/order/OrderStatisticsView.vue'),
          meta: { title: '订单统计', requiresAuth: true }
        }
      ]
    },
    {
      path: '/settings',
      component: Layout,
      redirect: '/settings/profile',
      meta: { title: '系统设置', requiresAuth: true },
      children: [
        {
          path: 'profile',
          name: 'Profile',
          component: () => import('@/views/settings/ProfileView.vue'),
          meta: { title: '个人信息', requiresAuth: true }
        },
        {
          path: 'security',
          name: 'Security',
          component: () => import('@/views/settings/SecurityView.vue'),
          meta: { title: '安全设置', requiresAuth: true }
        }
      ]
    }
  ]
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  console.log('当前路由:', to.path, '是否需要认证:', to.meta.requiresAuth, '是否有token:', !!token, 'token值:', token)
  
  // 如果访问登录页且已有token，直接跳转到首页
  if (to.path === '/login' && token) {
    console.log('已登录状态，自动跳转到首页')
    next('/')
    return
  }
  
  // 需要认证但没有token的路由，跳转到登录页
  if (to.meta.requiresAuth && !token) {
    console.log('未登录，重定向到登录页')
    next('/login')
  } else {
    console.log('允许访问路由:', to.path)
    next()
  }
})

export default router
