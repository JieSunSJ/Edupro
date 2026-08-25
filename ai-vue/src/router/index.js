import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/login/LoginView.vue')
  },
  {
    path: '/',
    component: () => import('../views/layout/LayoutView.vue'),
    redirect: '/emp',
    children: [
      {
        path: 'emp',
        name: 'Emp',
        component: () => import('../views/emp/EmpView.vue'),
        meta: { title: '员工管理' }
      },
      {
        path: 'student',
        name: 'Student',
        component: () => import('../views/student/StudentView.vue'),
        meta: { title: '学生管理' }
      },
      {
        path: 'clazz',
        name: 'Clazz',
        component: () => import('../views/clazz/ClazzView.vue'),
        meta: { title: '班级管理' }
      },
      {
        path: 'report',
        name: 'Report',
        component: () => import('../views/report/ReportView.vue'),
        meta: { title: '数据统计' }
      },
      {
        path: 'log',
        name: 'Log',
        component: () => import('../views/log/LogView.vue'),
        meta: { title: '操作日志' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.path !== '/login' && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router