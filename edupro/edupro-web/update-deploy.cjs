const fs = require('fs');

// ============================================
// 1. edupro.sql - Docker MySQL 初始化脚本
// ============================================
const eduproSql = `-- ========================================
-- EduPro 教务管理系统 - Docker 部署数据库初始化脚本
-- 使用方法: 由 docker-compose.yml 自动挂载执行
-- ========================================

-- ----------------------------------------
-- 1. 用户表 (管理员 + 教师)
-- ----------------------------------------
CREATE TABLE IF NOT EXISTS user (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(50)  NOT NULL UNIQUE COMMENT '登录用户名',
    password    VARCHAR(100) NOT NULL DEFAULT '123456' COMMENT '登录密码',
    role        TINYINT      NOT NULL DEFAULT 2 COMMENT '角色: 1=管理员, 2=教师',
    name        VARCHAR(50)  NOT NULL COMMENT '姓名',
    gender      TINYINT      COMMENT '性别: 1=男, 2=女',
    phone       VARCHAR(20)  COMMENT '手机号',
    image       VARCHAR(255) COMMENT '头像URL',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表(管理员/教师)';

-- ----------------------------------------
-- 2. 班级表
-- ----------------------------------------
CREATE TABLE IF NOT EXISTS clazz (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50) NOT NULL,
    room        VARCHAR(50),
    begin_date  DATE,
    end_date    DATE,
    master_id   INT COMMENT '班主任ID, 关联 user 表 id',
    subject     TINYINT COMMENT '学科: 1=Java,2=前端,3=Python,4=大数据,5=AI',
    status      TINYINT DEFAULT 0 COMMENT '0:未开课, 1:已开课, 2:已完结',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------------------
-- 3. 学生表
-- ----------------------------------------
CREATE TABLE IF NOT EXISTS student (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(50) NOT NULL COMMENT '姓名',
    no              VARCHAR(50) NOT NULL UNIQUE COMMENT '学号',
    gender          TINYINT COMMENT '1:男, 2:女',
    phone           VARCHAR(20),
    address         VARCHAR(255),
    clazz_id        INT,
    password        VARCHAR(64) DEFAULT '123456' COMMENT '登录密码',
    image           VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
    unenroll_count  INT DEFAULT 0 COMMENT '每学期退课次数',
    violation_count SMALLINT DEFAULT 0 COMMENT '违纪次数',
    violation_score SMALLINT DEFAULT 0 COMMENT '违纪扣分',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生表';

-- ----------------------------------------
-- 4. 课程表
-- ----------------------------------------
CREATE TABLE IF NOT EXISTS course (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(100) NOT NULL COMMENT '课程名称',
    teacher_name VARCHAR(50) COMMENT '授课教师',
    room         VARCHAR(100) COMMENT '上课地点',
    capacity     INT DEFAULT 30 COMMENT '课程最大容量',
    subject      TINYINT COMMENT '学科: 1=Java,2=前端,3=Python,4=UI,5=大数据,6=其他',
    description  VARCHAR(500) COMMENT '课程描述',
    status       TINYINT DEFAULT 0 COMMENT '0=未开课,1=已开课,2=已完结',
    begin_date   DATE COMMENT '开课日期',
    end_date     DATE COMMENT '结课日期',
    create_time  DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程表';

-- ----------------------------------------
-- 5. 选课记录表
-- ----------------------------------------
CREATE TABLE IF NOT EXISTS student_course (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    student_id  INT NOT NULL,
    course_id   INT NOT NULL,
    semester    VARCHAR(20) COMMENT '学期',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_student_course_semester (student_id, course_id, semester)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='选课记录表';

-- ----------------------------------------
-- 6. 课表表
-- ----------------------------------------
CREATE TABLE IF NOT EXISTS schedule (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    course_id   INT NOT NULL,
    day_of_week TINYINT NOT NULL COMMENT '1=周一~7=周日',
    start_time  TIME NOT NULL,
    end_time    TIME NOT NULL,
    week_start  INT DEFAULT 1,
    week_end    INT DEFAULT 20,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课表表';

-- ----------------------------------------
-- 7. 考试表
-- ----------------------------------------
CREATE TABLE IF NOT EXISTS exam (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    course_id   INT NOT NULL COMMENT '课程ID',
    exam_name   VARCHAR(100) NOT NULL COMMENT '考试名称',
    exam_date   DATE NOT NULL COMMENT '考试日期',
    start_time  TIME NOT NULL COMMENT '开始时间',
    end_time    TIME NOT NULL COMMENT '结束时间',
    room        VARCHAR(100) COMMENT '考试地点',
    exam_type   TINYINT DEFAULT 1 COMMENT '1=笔试,2=机试,3=综合',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='考试表';

-- ----------------------------------------
-- 8. 系统配置表
-- ----------------------------------------
CREATE TABLE IF NOT EXISTS system_config (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    config_key   VARCHAR(100) NOT NULL UNIQUE,
    config_value VARCHAR(500),
    description  VARCHAR(255),
    update_time  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置表';

-- ----------------------------------------
-- 9. 操作日志表
-- ----------------------------------------
CREATE TABLE IF NOT EXISTS operate_log (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    operate_user_id INT COMMENT '操作人ID',
    operate_user    VARCHAR(50) COMMENT '操作人姓名',
    operate_time    DATETIME COMMENT '操作时间',
    class_name      VARCHAR(100) COMMENT '类名',
    method_name     VARCHAR(100) COMMENT '方法名',
    method_params   TEXT COMMENT '方法参数',
    return_value    TEXT COMMENT '返回值',
    cost_time       BIGINT COMMENT '耗时(ms)',
    action          VARCHAR(100) COMMENT '操作类型',
    detail          VARCHAR(500) COMMENT '操作详情'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';


-- ========================================
-- 初始数据
-- ========================================

INSERT IGNORE INTO user (id, username, password, role, name, gender, phone, image, create_time, update_time) VALUES
(1, 'admin',     '123456', 1, '系统管理员', 1, '13800000001', NULL, NOW(), NOW()),
(2, 'chaijin',   '123456', 2, '柴进',       1, '13309090007', NULL, NOW(), NOW()),
(3, 'likui',     '123456', 2, '李逵',       1, '13309090008', NULL, NOW(), NOW()),
(4, 'wusong',    '123456', 2, '武松',       1, '13309090009', NULL, NOW(), NOW()),
(5, 'linchong',  '123456', 2, '林冲',       1, '13309090010', NULL, NOW(), NOW()),
(6, 'huyanzhuo', '123456', 2, '呼延灼',     1, '13309090011', NULL, NOW(), NOW()),
(7, 'yangzhi',   '123456', 2, '杨志',       1, '13309090013', NULL, NOW(), NOW()),
(8, 'sunerniang','123456', 2, '孙二娘',     2, '13309090015', NULL, NOW(), NOW()),
(9, 'luzhishen', '123456', 2, '鲁智深',     1, '13309090016', NULL, NOW(), NOW());

INSERT IGNORE INTO clazz (id, name, room, begin_date, end_date, master_id, subject, status, create_time, update_time) VALUES
(1, 'JavaEE就业165期', '108', '2026-08-01', '2026-12-31', 2, 1, 1, NOW(), NOW()),
(2, '前端就业90期',    '210', '2026-09-01', '2027-03-01', 3, 2, 1, NOW(), NOW()),
(3, '大数据就业58期',  '209', '2026-08-01', '2027-02-15', 5, 3, 1, NOW(), NOW()),
(4, 'Python就业30期',  '310', '2026-01-20', '2026-07-20', 4, 4, 1, NOW(), NOW()),
(5, '测试就业20期',    '313', '2026-02-10', '2026-07-10', 7, 5, 1, NOW(), NOW());

INSERT IGNORE INTO student (id, name, no, gender, phone, address, clazz_id, password, image, violation_count, violation_score, create_time, update_time) VALUES
(1, '萧远山', '2022000001', 1, '18800003211', '北京市昌平区建材城西路1号', 1, '123456', NULL, 0, 0, NOW(), NOW()),
(2, '阿朱',   '2022000002', 2, '18800160002', '北京市昌平区建材城西路2号', 1, '123456', NULL, 0, 0, NOW(), NOW()),
(3, '阿紫',   '2022000003', 2, '18800000034', '北京市昌平区建材城西路3号', 2, '123456', NULL, 0, 0, NOW(), NOW()),
(4, '游坦之', '2022000004', 1, '18800000067', '北京市昌平区建材城西路4号', 2, '123456', NULL, 0, 0, NOW(), NOW()),
(5, '康敏',   '2022000005', 2, '18800000077', '北京市昌平区建材城西路5号', 2, '123456', NULL, 0, 0, NOW(), NOW()),
(6, '王语嫣', '2022000006', 2, '18800007601', '北京市昌平区建材城西路6号', 3, '123456', NULL, 0, 0, NOW(), NOW()),
(7, '张伟',   '2025000001', 1, '13600001001', '北京市朝阳区',           3, '123456', NULL, 0, 0, NOW(), NOW()),
(8, '王芳',   '2025000002', 2, '13600001002', '上海市浦东新区',         4, '123456', NULL, 0, 0, NOW(), NOW()),
(9, '李娜',   '2025000003', 2, '13600001003', '广州市天河区',           5, '123456', NULL, 0, 0, NOW(), NOW()),
(10,'刘洋',   '2025000004', 1, '13600001004', '深圳市南山区',           1, '123456', NULL, 0, 0, NOW(), NOW());

INSERT IGNORE INTO course (id, name, teacher_name, room, capacity, subject, description, status, begin_date, end_date) VALUES
(1, 'Java基础',         '林冲',   '教学楼301', 30, 1, '系统学习Java编程语言基础', 1, '2026-09-01', '2027-01-15'),
(2, 'Spring框架实战',   '呼延灼', '实验楼201', 25, 1, 'Spring Boot框架整合开发',  1, '2026-09-01', '2027-01-15'),
(3, 'Vue3前端开发',      '孙二娘', '教学楼401', 28, 2, 'Vue3前端开发',             1, '2026-09-01', '2027-01-15'),
(4, 'Python数据分析',   '杨志',   '实验楼301', 20, 3, 'Python数据分析实战',       0, '2026-09-15', '2027-01-15'),
(5, 'MySQL数据库',       '柴进',   '教学楼303', 35, 1, 'MySQL数据库原理与实战',    1, '2026-09-01', '2027-01-15'),
(6, '大数据技术',        '鲁智深', '实验楼401', 22, 5, 'Hadoop、Spark核心技术',    0, '2026-09-15', '2027-01-15'),
(7, 'Linux操作系统',     '武松',   '实验楼101', 30, 6, 'Linux系统管理',            1, '2026-09-01', '2027-01-15'),
(8, 'UI设计基础',        '李逵',   '教学楼502', 25, 4, 'Figma工具使用、设计规范',  1, '2026-09-01', '2027-01-15');

INSERT IGNORE INTO schedule (course_id, day_of_week, start_time, end_time, week_start, week_end) VALUES
(1, 1, '08:30', '10:00', 1, 16),
(1, 3, '10:15', '11:45', 1, 16),
(2, 1, '14:00', '15:30', 1, 16),
(2, 5, '08:30', '10:00', 1, 16),
(3, 2, '08:30', '10:00', 1, 16),
(3, 4, '14:00', '15:30', 1, 16),
(5, 1, '10:15', '11:45', 1, 16),
(5, 3, '14:00', '15:30', 1, 16),
(7, 3, '08:30', '10:00', 1, 16),
(7, 5, '10:15', '11:45', 1, 16);

INSERT IGNORE INTO exam (course_id, exam_name, exam_date, start_time, end_time, room, exam_type) VALUES
(1, 'Java基础期中考试',   '2026-11-01', '09:00', '11:00', '教学楼301', 1),
(1, 'Java基础期末考试',   '2027-01-10', '09:00', '11:30', '教学楼301', 1),
(2, 'Spring框架期末项目', '2027-01-12', '14:00', '16:00', '实验楼201', 3),
(3, 'Vue3前端期末考试',   '2027-01-08', '09:00', '11:00', '教学楼401', 2),
(5, 'MySQL期末考试',      '2027-01-06', '14:00', '16:00', '教学楼303', 1);

INSERT IGNORE INTO student_course (student_id, course_id, semester) VALUES
(1, 1, '2026-2027-1'), (1, 2, '2026-2027-1'), (1, 5, '2026-2027-1'),
(2, 1, '2026-2027-1'), (2, 3, '2026-2027-1'),
(3, 2, '2026-2027-1'), (3, 5, '2026-2027-1'),
(4, 1, '2026-2027-1'), (4, 7, '2026-2027-1'),
(5, 3, '2026-2027-1'),
(6, 4, '2026-2027-1'),
(7, 1, '2026-2027-1'), (7, 5, '2026-2027-1'),
(8, 3, '2026-2027-1'),
(9, 6, '2026-2027-1'),
(10, 1, '2026-2027-1'), (10, 2, '2026-2027-1');

INSERT IGNORE INTO system_config (config_key, config_value, description) VALUES
('enroll_start_time', '2026-08-28 00:00:00', '选课开始时间'),
('enroll_end_time',   '2026-09-15 23:59:59', '选课截止时间');
`;

fs.writeFileSync('E:/学习/system-edupro/edupro/edupro.sql', eduproSql, 'utf8');
console.log('edupro.sql updated');

// ============================================
// 2. Dockerfile
// ============================================
const dockerfile = `FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY edupro-web-0.0.1-SNAPSHOT.jar app.jar
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
`;

fs.writeFileSync('E:/学习/system-edupro/edupro/Dockerfile', dockerfile, 'utf8');
console.log('Dockerfile updated');

// ============================================
// 3. docker-compose.yml
// ============================================
const dockerCompose = `version: "3.8"

services:
  mysql:
    image: mysql:8.0
    container_name: edupro-mysql
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: 123456
      MYSQL_DATABASE: test
      TZ: Asia/Shanghai
    ports:
      - "3306:3306"
    volumes:
      - mysql-data:/var/lib/mysql
      - ./edupro.sql:/docker-entrypoint-initdb.d/init.sql
    command:
      --character-set-server=utf8mb4
      --collation-server=utf8mb4_unicode_ci
      --default-time-zone=+08:00
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost", "-p123456"]
      interval: 5s
      timeout: 5s
      retries: 10

  app:
    build:
      context: .
      dockerfile: Dockerfile
    container_name: edupro-app
    restart: always
    depends_on:
      mysql:
        condition: service_healthy
    environment:
      TZ: Asia/Shanghai
      SPRING_PROFILES_ACTIVE: prod
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/test?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: "123456"
      OSS_ACCESS_KEY_ID: ${OSS_ACCESS_KEY_ID}
      OSS_ACCESS_KEY_SECRET: ${OSS_ACCESS_KEY_SECRET}

    ports:
      - "8080:8080"

  nginx:
    image: nginx:1.25-alpine
    container_name: edupro-nginx
    restart: always
    ports:
      - "80:80"
    volumes:
      - ./dist:/usr/share/nginx/html
      - ./nginx.conf:/etc/nginx/conf.d/default.conf
    depends_on:
      - app

volumes:
  mysql-data:
`;

fs.writeFileSync('E:/学习/system-edupro/edupro/docker-compose.yml', dockerCompose, 'utf8');
console.log('docker-compose.yml updated');

// ============================================
// 4. nginx.conf
// ============================================
const nginxConf = `server {
    listen       80;
    server_name  localhost;

    client_max_body_size 10m;

    gzip on;
    gzip_types text/plain text/css application/json application/javascript text/xml application/xml text/javascript image/svg+xml;
    gzip_min_length 1024;

    location /api/ {
        proxy_pass http://app:8080/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }

    location /upload {
        proxy_pass http://app:8080/upload;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        client_max_body_size 10m;
    }

    location / {
        root   /usr/share/nginx/html;
        index  index.html;
        try_files $uri $uri/ /index.html;
    }
}
`;

fs.writeFileSync('E:/学习/system-edupro/edupro/nginx.conf', nginxConf, 'utf8');
console.log('nginx.conf updated');

console.log('\nAll EduPro deployment files updated successfully!');