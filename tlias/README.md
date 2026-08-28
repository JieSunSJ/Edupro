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
| Spring AOP | 2.6.13 | 操作日志自动记录 |
| Maven | 3.x | 项目构建与依赖管理 |

## 项目结构（Maven 多模块）

```
tlias/
├── tlias-common/          # 公共模块
│   ├── entity/            # 实体类 (User, Student, Clazz, OperateLog 等)
│   ├── utils/             # 工具类 (JwtUtils, CurrentHolder)
│   ├── vo/                # 视图对象 (Result, PageResult 等)
│   └── annotation/        # 自定义注解 (@LogOperation)
├── tlias-mapper/          # 数据持久层
│   ├── mapper/            # Mapper 接口
│   └── resources/mapper/  # MyBatis XML 映射文件
├── tlias-service/         # 业务逻辑层
│   ├── service/           # 服务接口
│   └── service/impl/      # 服务实现
└── tlias-web/             # Web 表现层
    ├── controller/        # REST 控制器
    ├── config/            # 配置类 (OSS, WebConfig)
    ├── filter/            # 过滤器 (TokenFilter)
    ├── interceptor/       # 拦截器 (TokenInterceptor, OperateLogAspect)
    ├── exception/         # 全局异常处理
    └── resources/         # 配置文件 + schema.sql
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
| POST | `/login` | 管理员/教师登录 |

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

### 二、用户管理 `/user`

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/user/profile` | 获取当前用户信息 |
| PUT | `/user/profile` | 更新用户信息 |
| PUT | `/user/image` | 更新用户头像 |
| PUT | `/user/password` | 修改密码 |

---

### 三、班级管理 `/clazzes`

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/clazzes` | 分页查询班级（含班主任姓名） |
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
| GET | `/students/list` | 分页查询学生（含班级名+班主任） |
| GET | `/students/{id}` | 根据 ID 查询学生 |
| POST | `/students/add` | 添加学生 |
| PUT | `/students/update` | 更新学生 |
| DELETE | `/students/delete/{id}` | 删除学生 |

**GET /students/list**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| name | String | 否 | 姓名 |
| clazzId | Integer | 否 | 班级 ID |
| page | Integer | 是 | 页码 |
| pageSize | Integer | 是 | 每页条数 |

---

### 五、操作日志 `/logs`

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/logs` | 分页查询操作日志 |

日志由 AOP 切面自动记录，拦截所有 Controller 中的 `add*`、`update*`、`delete*` 方法。

---

### 六、文件上传

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/upload` | 上传文件到阿里云 OSS |

请求格式：`multipart/form-data`，字段名 `file`

---

## 数据库表结构

### user 表（管理员/教师）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | INT | 主键 |
| username | VARCHAR | 登录用户名 |
| password | VARCHAR | 登录密码 |
| name | VARCHAR | 姓名 |
| role | INT | 角色：1-管理员, 2-教师 |
| phone | VARCHAR | 手机号 |
| gender | INT | 性别：1-男, 2-女 |
| image | VARCHAR | 头像 URL |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |

### student 表（学生）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | INT | 主键 |
| name | VARCHAR | 姓名 |
| no | VARCHAR | 学号 |
| gender | INT | 性别：1-男, 2-女 |
| phone | VARCHAR | 手机号 |
| address | VARCHAR | 地址 |
| clazz_id | INT | 所属班级 ID |
| password | VARCHAR | 登录密码 |
| image | VARCHAR | 头像 URL |
| violation_count | INT | 违纪次数 |
| violation_score | INT | 违纪扣分 |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |

### clazz 表（班级）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | INT | 主键 |
| name | VARCHAR | 班级名称 |
| room | VARCHAR | 教室 |
| begin_date | DATE | 开课日期 |
| end_date | DATE | 结课日期 |
| subject | INT | 学科码 |
| master_id | INT | 班主任 ID（关联 user 表） |
| status | INT | 状态：0-未开课, 1-已开课, 2-已完结 |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |

### operate_log 表（操作日志）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | INT | 主键 |
| operate_user | VARCHAR | 操作人 |
| operate_time | DATETIME | 操作时间 |
| operate_type | VARCHAR | 操作类型（新增/修改/删除） |
| operate_detail | VARCHAR | 操作详情 |
| params | TEXT | 方法参数 |
| cost_time | BIGINT | 耗时(ms) |

---

## 角色说明

| 角色 | role 值 | 权限 |
|------|---------|------|
| 管理员 | 1 | 全部功能 |
| 教师 | 2 | 受限功能 |
| 学生 | student | 学生端功能 |
