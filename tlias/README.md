# TliasWeb 教学管理后台

基于 Spring Boot 的 Web 教学管理系统后端项目，提供员工、班级、学生管理等接口，支持条件分页查询、文件上传到阿里云 OSS 等功能。

## 技术栈

- **JDK 17**
- **Spring Boot 2.6.13**（Spring MVC）
- **MyBatis 2.2.2**（MyBatis-Spring-Boot-Starter）
- **MySQL**（mysql-connector-j）
- **PageHelper 1.4.6**（分页插件）
- **阿里云 OSS 3.17.4**（文件上传）
- **Lombok**

## 功能模块

| 模块 | 说明 | 接口前缀 |
|------|------|----------|
| 员工管理 | 员工增删改查、批量删除、分页 | `/emp` |
| 班级管理 | 班级条件分页查询、增删改查 | `/clzzs` |
| 学生管理 | 学生条件分页查询、增删改 | `/students` |
| 报表统计 | 各职位人数、男女比例统计 | `/report` |
| 文件上传 | 上传文件到阿里云 OSS | `/upload` |

## 项目结构

```
src/main/java/cn/hytc/mysql
├── MysqlApplication.java        # 启动类
├── config/                      # 阿里云 OSS 配置
├── controller/                  # 控制层
├── service/                     # 业务层接口
│   └── impl/                    # 业务层实现
├── mapper/                      # MyBatis Mapper 接口
├── entity/                      # 实体类
├── vo/                          # 通用返回对象（Result、PageResult）
└── exception/                   # 全局异常处理

src/main/resources
├── application.yml              # 应用配置
└── cn/hytc/mysql/mapper/*.xml   # MyBatis SQL 映射文件
```

## 快速开始

### 1. 环境要求

- JDK 17
- Maven 3.6+
- MySQL 5.7+ / 8.0

### 2. 数据库配置

默认连接本机 MySQL，库名 `test`，账号密码见 `src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/test
    username: root
    password: 123456
```

按需修改账号密码，并确保 `test` 库中存在以下表：

`clazz`（班级）、`emp`（员工）、`emp_expr`（工作经历）、`emp_log`（操作日志）、`student`（学生）、`tb_course`、`tb_student`、`tb_student_course`、`tb_user`、`tb_user_card`。

### 3. 阿里云 OSS 配置（文件上传用）

在 `application.yml` 中配置：

```yaml
aliyun:
  oss:
    endpoint: https://oss-cn-beijing.aliyuncs.com
    bucketName: java-ai-muli
    region: cn-beijing
```

### 4. 启动

```bash
mvn spring-boot:run
```

或在 IDE 中直接运行 `MysqlApplication`，默认端口 `8080`。

## 主要接口

### 员工管理 `/emp`

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/emp/list` | 员工列表 |
| GET | `/emp/page` | 分页查询（page、pageSize） |
| POST | `/emp` | 新增员工 |
| DELETE | `/emp?ids=1,2` | 批量删除 |
| GET | `/emp/{id}` | 按 ID 查询 |
| PUT | `/emp` | 修改员工 |

### 班级管理 `/clzzs`

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/clzzs` | 条件分页查询（name、begin、end、page、pageSize） |
| GET | `/clzzs/all` | 查询所有班级 |
| GET | `/clzzs/all/page` | 查询所有班级（分页） |
| GET | `/clzzs/{id}` | 按 ID 查询 |
| POST | `/clzzs` | 新增班级 |
| PUT | `/clzzs` | 修改班级 |
| DELETE | `/clzzs/{id}` | 删除班级 |

### 学生管理 `/students`

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/students/list` | 条件分页查询（name、degree、clazzId、page、pageSize） |
| POST | `/students/add` | 新增学生 |
| PUT | `/students/update` | 修改学生 |
| DELETE | `/students/delete/{id}` | 删除学生 |

### 报表统计 `/report`

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/report/empJobData` | 各职位员工人数 |
| GET | `/report/empGenderData` | 男女员工数量 |

### 文件上传 `/upload`

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/upload` | 上传文件（表单参数 `file`），返回 OSS 访问地址 |

## 统一返回格式

所有接口返回统一结构 `Result`：

```json
{
  "code": 200,
  "msg": "success",
  "data": {}
}
```

分页数据使用 `PageResult` 包装，包含总条数与分页列表。
