# TliasWeb - 教务管理系统（后端）

## 技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| JDK | 17 | Java 运行环境 |
| Spring Boot | 2.6.13 | 基础框架 |
| MyBatis | 2.2.2 | ORM 持久层框架 |
| MySQL | 8.0 | 关系型数据库 |
| PageHelper | 1.4.6 | MyBatis 分页插件 |
| JWT (jjwt) | 0.9.1 | 身份认证令牌 |
| Lombok | 1.18.42 | 简化 Java 代码 |
| 阿里云 OSS | 3.17.4 | 文件存储服务 |
| Maven | 3.x | 项目构建与依赖管理 |

## 项目结构（Maven 多模块）

```
tlias/
├── tlias-common/          # 公共模块
│   ├── entity/            # 实体类 (Emp, Student, Clazz, Dept, LoginInfo 等)
│   ├── utils/             # 工具类 (JwtUtils, CurrentHolder)
│   ├── vo/                # 视图对象 (Result, PageResult, JobOption 等)
│   └── annotation/        # 自定义注解 (@LogOperation)
├── tlias-mapper/          # 数据持久层
│   ├── mapper/            # Mapper 接口
│   └── resources/mapper/  # MyBatis XML 映射文件
├── tlias-service/         # 业务逻辑层
│   ├── service/           # 服务接口
│   ├── service/impl/      # 服务实现
│   ├── aspect/            # AOP 切面 (日志记录)
│   └── listener/          # 事件监听器
└── tlias-web/             # Web 表现层
    ├── controller/        # REST 控制器
    ├── config/            # 配置类 (OSS, WebConfig)
    ├── filter/            # 过滤器 (TokenFilter)
    ├── interceptor/       # 拦截器 (TokenInterceptor)
    ├── exception/         # 全局异常处理
    └── resources/         # 配置文件
```

## 快速启动

### 1. 环境要求

- JDK 17+
- MySQL 8.0+
- Maven 3.x

### 2. 数据库配置

修改 `tlias-web/src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/test
    username: root
    password: 你的密码
```

### 3. 阿里云 OSS 配置（可选）

文件上传功能需要配置阿里云 OSS，在 `application.yml` 中修改：

```yaml
aliyun:
  oss:
    endpoint: https://oss-cn-beijing.aliyuncs.com
    bucketName: 你的Bucket名称
    region: cn-beijing
```

并设置环境变量 `OSS_ACCESS_KEY_ID` 和 `OSS_ACCESS_KEY_SECRET`。

### 4. 启动项目

```bash
cd tlias/tlias-web
mvn spring-boot:run
```

服务默认运行在 `http://localhost:8080`

---

## API 接口文档

### 统一响应格式

```json
{
  "code": 200,
  "msg": "success",
  "data": {}
}
```

### 认证方式

登录后获取 JWT Token，后续请求在 Header 中携带：

```
token: <JWT Token>
```

---

### 一、登录认证

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/login` | 用户登录 |

**POST /login**

请求体：
```json
{
  "username": "admin",
  "password": "123456"
}
```

响应：
```json
{
  "code": 200,
  "msg": "success",
  "data": {
    "id": 1,
    "username": "admin",
    "name": "管理员",
    "role": 1,
    "token": "eyJhbGciOi...",
    "image": "https://..."
  }
}
```

---

### 二、员工管理 `/emp`

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/emp/list` | 查询所有员工（支持多条件筛选） |
| GET | `/emp/page` | 分页查询员工 |
| GET | `/emp/{id}` | 根据 ID 查询员工 |
| POST | `/emp` | 添加员工 |
| PUT | `/emp` | 更新员工 |
| DELETE | `/emp?ids=1,2,3` | 批量删除员工 |
| PUT | `/emp/password` | 修改密码 |

**GET /emp/list** | **GET /emp/page**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| name | String | 否 | 姓名（模糊查询） |
| gender | Integer | 否 | 性别：1-男, 2-女 |
| job | Integer | 否 | 职位：1-班主任, 2-讲师, 3-学工主管, 4-教研主管 |
| deptId | Integer | 否 | 部门 ID |
| salaryMin | BigDecimal | 否 | 最低薪资 |
| salaryMax | BigDecimal | 否 | 最高薪资 |
| page | Integer | 否 | 页码（默认 1） |
| pageSize | Integer | 否 | 每页条数（默认 10） |

**POST /emp** | **PUT /emp**

```json
{
  "name": "张三",
  "username": "zhangsan",
  "gender": 1,
  "job": 2,
  "deptId": 1,
  "salary": 8000,
  "image": "https://...",
  "exprList": [
    { "company": "某公司", "job": "工程师", "begin": "2020-01-01", "end": "2022-12-31" }
  ]
}
```

**PUT /emp/password**

```json
{
  "id": 1,
  "oldPassword": "123456",
  "newPassword": "654321"
}
```

---

### 三、班级管理 `/clazzes`

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/clazzes` | 分页查询班级 |
| GET | `/clazzes/{id}` | 根据 ID 查询班级 |
| GET | `/clazzes/all` | 查询所有班级 |
| GET | `/clazzes/all/page` | 分页查询所有班级 |
| POST | `/clazzes` | 添加班级 |
| PUT | `/clazzes` | 更新班级 |
| DELETE | `/clazzes/{id}` | 删除班级 |

**GET /clazzes**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| name | String | 否 | 班级名称 |
| begin | LocalDate | 否 | 开课时间（起始） |
| end | LocalDate | 否 | 结课时间（截止） |
| page | Integer | 否 | 页码（默认 1） |
| pageSize | Integer | 否 | 每页条数（默认 5） |

**POST /clazzes** | **PUT /clazzes**

```json
{
  "name": "Java就业180期",
  "room": "301教室",
  "beginDate": "2026-09-01",
  "endDate": "2027-03-01",
  "subject": 1,
  "masterId": 1,
  "status": 0
}
```

> **status 状态码**：0-未开课, 1-已开课, 2-已完结
>
> **subject 学科码**：1-Java, 2-前端, 3-Python, 4-大数据, 5-AI

---

### 四、学生管理 `/students`

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/students/list` | 分页查询学生 |
| GET | `/students/{id}` | 根据 ID 查询学生 |
| POST | `/students/add` | 添加学生 |
| PUT | `/students/update` | 更新学生 |
| DELETE | `/students/delete/{id}` | 删除学生 |

**GET /students/list**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| name | String | 否 | 姓名 |
| degree | String | 否 | 学历 |
| clazzId | Integer | 否 | 班级 ID |
| page | Integer | 是 | 页码 |
| pageSize | Integer | 是 | 每页条数 |

---

### 五、数据统计 `/report`

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/report/empJobData` | 各职位员工人数统计 |
| GET | `/report/empGenderData` | 员工性别分布统计 |
| GET | `/report/studentGenderData` | 学生性别分布统计 |
| GET | `/report/studentCollegeData` | 学生学历分布统计 |
| GET | `/report/studentCountData` | 各班级学生人数统计 |

---

### 六、操作日志 `/logs`

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/logs/list` | 查询所有日志 |
| GET | `/logs/page` | 分页查询日志 |
| DELETE | `/logs/{id}` | 删除日志 |

---

### 七、部门管理 `/dept`

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/dept/list` | 查询所有部门 |

---

### 八、文件上传

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/upload` | 上传文件到阿里云 OSS |

请求格式：`multipart/form-data`，字段名 `file`

---

## 角色说明

| 角色 | role 值 | 权限 |
|------|---------|------|
| 管理员 | 1 | 全部功能 |
| 普通用户 | 2 | 受限功能 |