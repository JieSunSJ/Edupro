-- ========================================
-- 学生端数据库初始化脚本
-- ========================================

-- 1. 修改 student 表，新增字段
ALTER TABLE `student`
ADD COLUMN `password` VARCHAR(64) DEFAULT '123456' COMMENT '登录密码',
ADD COLUMN `image` VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
ADD COLUMN `unenroll_count` INT DEFAULT 0 COMMENT '每学期退课次数';

-- 2. 课程表
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL COMMENT '课程名称',
  `teacher_name` VARCHAR(50) COMMENT '授课教师',
  `room` VARCHAR(100) COMMENT '上课地点',
  `capacity` INT DEFAULT 30 COMMENT '课程最大容量',
  `subject` TINYINT COMMENT '学科: 1=Java,2=前端,3=Python,4=UI,5=大数据,6=其他',
  `description` VARCHAR(500) COMMENT '课程描述',
  `status` TINYINT DEFAULT 0 COMMENT '0=未开课,1=已开课,2=已完结',
  `begin_date` DATE COMMENT '开课日期',
  `end_date` DATE COMMENT '结课日期',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 3. 选课记录表
DROP TABLE IF EXISTS `student_course`;
CREATE TABLE `student_course` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `student_id` INT NOT NULL,
  `course_id` INT NOT NULL,
  `semester` VARCHAR(20) COMMENT '学期',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`student_id`) REFERENCES `student`(`id`),
  FOREIGN KEY (`course_id`) REFERENCES `course`(`id`),
  UNIQUE KEY `uk_student_course_semester` (`student_id`, `course_id`, `semester`)
);

-- 4. 课表表
DROP TABLE IF EXISTS `schedule`;
CREATE TABLE `schedule` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `course_id` INT NOT NULL,
  `day_of_week` TINYINT NOT NULL COMMENT '1=周一~7=周日',
  `start_time` TIME NOT NULL,
  `end_time` TIME NOT NULL,
  `week_start` INT DEFAULT 1,
  `week_end` INT DEFAULT 20,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (`course_id`) REFERENCES `course`(`id`)
);

-- 5. 系统配置表
DROP TABLE IF EXISTS `system_config`;
CREATE TABLE `system_config` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `config_key` VARCHAR(100) NOT NULL,
  `config_value` VARCHAR(500),
  `description` VARCHAR(255),
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY `uk_config_key` (`config_key`)
);

-- ========================================
-- 测试数据
-- ========================================

INSERT INTO `system_config` (`config_key`, `config_value`, `description`) VALUES
('enroll_start_time', '2024-08-28 00:00:00', '选课开始时间'),
('enroll_end_time', '2024-09-15 23:59:59', '选课截止时间');

INSERT INTO `course` (`name`, `teacher_name`, `room`, `capacity`, `subject`, `description`, `status`, `begin_date`, `end_date`) VALUES
('Java基础', '张老师', '教学楼301', 30, 1, '系统学习Java编程语言基础，面向对象、集合框架、IO流、多线程', 1, '2024-09-01', '2025-01-15'),
('Spring框架实战', '王老师', '实验楼201', 25, 1, 'Spring Boot + Spring MVC + MyBatis框架整合开发', 1, '2024-09-01', '2025-01-15'),
('Vue3前端开发', '刘老师', '教学楼401', 28, 2, 'Vue3 + Element Plus前端开发，组件化、路由、状态管理', 1, '2024-09-01', '2025-01-15'),
('Python数据分析', '赵老师', '实验楼301', 20, 3, 'Python数据分析实战，NumPy、Pandas、Matplotlib', 0, '2024-09-15', '2025-01-15'),
('UI设计基础', '钱老师', '教学楼502', 25, 4, 'Figma工具使用、设计规范、交互设计', 1, '2024-09-01', '2025-01-15'),
('大数据技术', '孙老师', '实验楼401', 22, 5, 'Hadoop、Spark、Hive等核心技术', 0, '2024-09-15', '2025-01-15'),
('MySQL数据库', '李老师', '教学楼303', 35, 1, 'MySQL数据库原理与实战，SQL编写、索引优化、事务处理', 1, '2024-09-01', '2025-01-15'),
('Linux操作系统', '周老师', '实验楼101', 30, 6, 'Linux系统管理，常用命令、Shell脚本、服务部署', 1, '2024-09-01', '2025-01-15');

INSERT INTO `schedule` (`course_id`, `day_of_week`, `start_time`, `end_time`, `week_start`, `week_end`) VALUES
(1, 1, '08:30', '10:00', 1, 16),
(1, 3, '10:15', '11:45', 1, 16),
(2, 1, '14:00', '15:30', 1, 16),
(2, 5, '08:30', '10:00', 1, 16),
(3, 2, '08:30', '10:00', 1, 16),
(3, 4, '14:00', '15:30', 1, 16),
(5, 2, '14:00', '15:30', 1, 16),
(5, 4, '10:15', '11:45', 1, 16),
(7, 1, '10:15', '11:45', 1, 16),
(7, 3, '14:00', '15:30', 1, 16),
(8, 3, '08:30', '10:00', 1, 16),
(8, 5, '10:15', '11:45', 1, 16);

UPDATE `student` SET `password` = '123456' WHERE `password` IS NULL;

INSERT INTO `student_course` (`student_id`, `course_id`, `semester`) VALUES
(1, 1, '2024-2025-1'),
(1, 2, '2024-2025-1'),
(1, 3, '2024-2025-1'),
(2, 1, '2024-2025-1'),
(2, 3, '2024-2025-1'),
(3, 2, '2024-2025-1'),
(3, 5, '2024-2025-1'),
(3, 7, '2024-2025-1'),
(4, 1, '2024-2025-1'),
(4, 8, '2024-2025-1'),
(5, 3, '2024-2025-1'),
(5, 5, '2024-2025-1'),
(5, 7, '2024-2025-1');