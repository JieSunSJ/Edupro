# TliasWeb - 教务管理系统（前端）

## 技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Vue 3 | 3.3.4 | 渐进式 JavaScript 框架 |
| Element Plus | 2.14.5 | 基于 Vue 3 的 UI 组件库 |
| Vue Router | 4.6.4 | 前端路由管理 |
| Axios | 1.19.0 | HTTP 请求库 |
| ECharts | 6.1.0 | 数据可视化图表库 |
| Vite | 4.4.5 | 前端构建工具 |
| @element-plus/icons-vue | 2.3.2 | Element Plus 图标库 |

## 项目结构

```
ai-vue/
├── public/                  # 静态资源
├── src/
│   ├── api/                 # 接口请求封装
│   │   ├── request.js       # Axios 实例（拦截器、Token 注入）
│   │   ├── user.js          # 用户管理 API
│   │   ├── student.js       # 学生管理 API
│   │   ├── clazz.js         # 班级管理 API
│   │   ├── log.js           # 操作日志 API
│   │   └── upload.js        # 文件上传 API
│   ├── router/
│   │   └── index.js         # 路由配置（History 模式）
│   ├── views/
│   │   ├── login/           # 登录页
│   │   │   └── LoginView.vue
│   │   ├── layout/          # 管理端布局
│   │   │   └── LayoutView.vue
│   │   ├── student/         # 学生管理
│   │   │   └── StudentView.vue
│   │   ├── clazz/           # 班级管理
│   │   │   └── ClazzView.vue
│   │   ├── report/          # 数据统计
│   │   │   └── ReportView.vue
│   │   ├── log/             # 操作日志
│   │   │   └── LogView.vue
│   │   └── student/         # 学生端页面
│   │       ├── StudentLayout.vue
│   │       ├── StudentDashboard.vue
│   │       ├── StudentClazz.vue
│   │       ├── StudentSchedule.vue
│   │       ├── StudentCourse.vue
│   │       ├── StudentExam.vue
│   │       ├── StudentViolation.vue
│   │       └── StudentProfile.vue
│   ├── App.vue              # 根组件
│   └── main.js              # 入口文件
├── vite.config.js           # Vite 配置（代理转发）
├── package.json
└── index.html
```

## 功能模块

### 管理端

| 模块 | 功能 | 说明 |
|------|------|------|
| 登录 | 用户登录、角色选择 | 支持管理员/教师角色 |
| 学生管理 | 增删改查、分页 | 按姓名、班级筛选，显示班级名+班主任 |
| 班级管理 | 增删改查、状态管理 | 未开课/已开课/已完结，显示班主任 |
| 数据统计 | ECharts 可视化图表 | 学生总数、班级总数、教师总数、班级人数/性别/状态/学科分布 |
| 操作日志 | 日志查看与分页 | AOP 自动记录新增/修改/删除操作 |
| 个人信息 | 查看/编辑个人资料、修改密码、头像上传 | 右上角头像下拉菜单 |

### 学生端

| 模块 | 功能 | 说明 |
|------|------|------|
| 登录 | 学号+密码登录 | 学生独立登录入口 |
| 首页 | 统计卡片+今日课表 | 本学期课程数、今日课程数、违纪次数 |
| 我的班级 | 查看班级信息 | 班级详情、班主任、同学列表 |
| 我的课表 | 按周查看课表 | 课程时间、教室、教师 |
| 选课 | 浏览/报名/退选 | 可选课程列表、选课状态 |
| 考试 | 考试安排+成绩查询 | 考试时间、地点、成绩 |
| 违纪 | 违纪记录查看 | 违纪次数、扣分汇总 |
| 个人中心 | 头像上传、信息编辑、修改密码 | 手机号、地址修改 |

## 快速启动

### 1. 安装依赖

```bash
cd ai-vue
npm install
```

### 2. 启动开发服务器

```bash
npm run dev
```

服务运行在 http://localhost:3000

### 3. 构建生产版本

```bash
npm run build
```

构建产物在 dist/ 目录。

## 代理配置

前端开发服务器将 /api 请求代理到后端 http://localhost:8080

## 路由说明

### 管理端路由

| 路径 | 组件 | 说明 |
|------|------|------|
| /login | LoginView | 登录页 |
| /student | StudentView | 学生管理 |
| /clazz | ClazzView | 班级管理 |
| /report | ReportView | 数据统计 |
| /log | LogView | 操作日志 |

### 学生端路由

| 路径 | 组件 | 说明 |
|------|------|------|
| /stu | StudentLayout | 学生端布局 |
| /stu/dashboard | StudentDashboard | 首页 |
| /stu/clazz | StudentClazz | 我的班级 |
| /stu/schedule | StudentSchedule | 我的课表 |
| /stu/course | StudentCourse | 选课 |
| /stu/exam | StudentExam | 考试 |
| /stu/violation | StudentViolation | 违纪 |
| /stu/profile | StudentProfile | 个人中心 |

使用 Vue Router History 模式，URL 不包含 # 符号。

## 登录凭据

| 角色 | 用户名 | 密码 |
|------|--------|------|
| 管理员 | admin | 123456 |
| 教师 | zhangsan | 123456 |
| 学生 | 学号 | 123456 |

## 认证流程

1. 登录成功后，后端返回 JWT Token
2. Axios 拦截器将 Token 存入 localStorage 并自动注入到请求 Header
3. 响应拦截器检测 401 状态码，自动跳转登录页
4. 路由守卫根据角色和路径控制页面访问权限
