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
    redirect: '/student',
    children: [
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
  },
  {
    path: '/stu',
    component: () => import('../views/student/StudentLayout.vue'),
    redirect: '/stu/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'StudentDashboard',
        component: () => import('../views/student/StudentDashboard.vue'),
        meta: { title: '学生首页' }
      },
      {
        path: 'course',
        name: 'StudentCourse',
        component: () => import('../views/student/StudentCourse.vue'),
        meta: { title: '选课中心' }
      },
      {
        path: 'course/:id',
        name: 'StudentCourseDetail',
        component: () => import('../views/student/StudentCourseDetail.vue'),
        meta: { title: '课程详情' }
      },
      {
        path: 'schedule',
        name: 'StudentSchedule',
        component: () => import('../views/student/StudentSchedule.vue'),
        meta: { title: '我的课表' }
      },
      {
        path: 'exam',
        name: 'StudentExam',
        component: () => import('../views/student/StudentExam.vue'),
        meta: { title: '考试信息' }
      },
      {
        path: 'violation',
        name: 'StudentViolation',
        component: () => import('../views/student/StudentViolation.vue'),
        meta: { title: '违纪记录' }
      },
      {
        path: 'profile',
        name: 'StudentProfile',
        component: () => import('../views/student/StudentProfile.vue'),
        meta: { title: '个人中心' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  if (to.path === '/login') {
    return next()
  }
  const token = localStorage.getItem('token')
  if (!token) {
    return next('/login')
  }
  try {
    const studentInfo = JSON.parse(localStorage.getItem('studentInfo') || 'null')
    const userInfo = JSON.parse(localStorage.getItem('userInfo') || 'null')
    if (studentInfo && (to.path === '/' || to.path.startsWith('/student') || to.path.startsWith('/clazz'))) {
      return next('/stu/dashboard')
    }
    if (userInfo && (to.path === '/stu' || to.path.startsWith('/stu/'))) {
      return next('/student')
    }
  } catch (e) {
    localStorage.clear()
    return next('/login')
  }
  next()
})

export default router
