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
│   │   ├── emp.js           # 员工管理 API
│   │   ├── student.js       # 学生管理 API
│   │   ├── clazz.js         # 班级管理 API
│   │   ├── dept.js          # 部门管理 API
│   │   ├── log.js           # 操作日志 API
│   │   ├── report.js        # 数据统计 API
│   │   └── upload.js        # 文件上传 API
│   ├── router/
│   │   └── index.js         # 路由配置（History 模式）
│   ├── views/
│   │   ├── login/           # 登录页
│   │   │   └── LoginView.vue
│   │   ├── layout/          # 布局组件
│   │   │   └── LayoutView.vue
│   │   ├── emp/             # 员工管理
│   │   │   └── EmpView.vue
│   │   ├── student/         # 学生管理
│   │   │   └── StudentView.vue
│   │   ├── clazz/           # 班级管理
│   │   │   └── ClazzView.vue
│   │   ├── report/          # 数据统计
│   │   │   └── ReportView.vue
│   │   └── log/             # 操作日志
│   │       └── LogView.vue
│   ├── App.vue              # 根组件
│   └── main.js              # 入口文件
├── vite.config.js           # Vite 配置（代理转发）
├── package.json
└── index.html
```

## 功能模块

| 模块 | 功能 | 说明 |
|------|------|------|
| 登录 | 用户登录、角色选择 | 支持管理员/普通用户角色 |
| 员工管理 | 增删改查、多条件搜索、工作经历管理、头像上传 | 姓名、性别、职位、部门、薪资范围搜索 |
| 班级管理 | 增删改查、状态管理 | 未开课/已开课/已完结 |
| 学生管理 | 增删改查、分页 | 按姓名、学历、班级筛选 |
| 数据统计 | ECharts 可视化图表 | 员工职位、性别分布；学生学历、班级人数 |
| 操作日志 | 日志查看与删除 | 记录关键操作 |
| 个人信息 | 查看/编辑个人资料、修改密码 | 右上角头像下拉菜单 |

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

| 路径 | 组件 | 说明 |
|------|------|------|
| /login | LoginView | 登录页 |
| /emp | EmpView | 员工管理 |
| /student | StudentView | 学生管理 |
| /clazz | ClazzView | 班级管理 |
| /report | ReportView | 数据统计 |
| /log | LogView | 操作日志 |

使用 Vue Router History 模式，URL 不包含 # 符号。

## 登录凭据

| 角色 | 用户名 | 密码 |
|------|--------|------|
| 管理员 | admin | 123456 |
| 普通用户 | zhangsan | 123456 |

## 认证流程

1. 登录成功后，后端返回 JWT Token
2. Axios 拦截器将 Token 存入 localStorage 并自动注入到请求 Header
3. 响应拦截器检测 401 状态码，自动跳转登录页
