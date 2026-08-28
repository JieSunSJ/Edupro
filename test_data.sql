-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.14-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `clazz`
--

DROP TABLE IF EXISTS `clazz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clazz` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID,主键',
  `name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '班级名称',
  `room` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '班级教室',
  `begin_date` date NOT NULL COMMENT '开课时间',
  `end_date` date NOT NULL COMMENT '结课时间',
  `master_id` int(10) unsigned DEFAULT NULL COMMENT '班主任ID, 关联员工表ID',
  `subject` tinyint(3) unsigned NOT NULL COMMENT '学科, 1:java, 2:前端, 3:大数据, 4:Python, 5:Go, 6: 嵌入式',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '班级状态: 0-未开课, 1-已开课, 2-已完结',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='班级表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clazz`
--

LOCK TABLES `clazz` WRITE;
/*!40000 ALTER TABLE `clazz` DISABLE KEYS */;
INSERT INTO `clazz` (`id`, `name`, `room`, `begin_date`, `end_date`, `master_id`, `subject`, `create_time`, `update_time`, `status`) VALUES (2,'前端就业90期','210','2026-09-01','2027-03-01',3,2,'2024-06-01 17:45:12','2024-06-01 17:45:12',1),(3,'JavaEE就业165期','108','2026-08-01','2026-12-31',6,1,'2024-06-01 17:45:40','2024-06-01 17:45:40',1),(4,'JavaEE就业166期','105','2026-05-01','2026-08-30',20,1,'2024-06-01 17:46:10','2024-06-01 17:46:10',0),(5,'大数据就业58期','209','2024-08-01','2024-02-15',7,3,'2024-06-01 17:51:21','2024-06-01 17:51:21',1),(6,'JavaEE就业167期','325','2024-11-20','2024-05-10',36,1,'2024-11-15 11:35:46','2024-12-13 14:31:24',2),(7,'JavaEE就业168期','301','2025-01-10','2025-07-10',10,1,'2025-01-01 09:00:00','2025-01-01 09:00:00',2),(8,'JavaEE就业169期','302','2025-02-15','2025-08-15',6,1,'2025-02-01 09:00:00','2025-02-01 09:00:00',2),(9,'JavaEE就业170期','303','2025-03-20','2025-09-20',20,1,'2025-03-01 09:00:00','2025-03-01 09:00:00',2),(10,'前端就业91期','304','2025-01-05','2025-06-05',3,2,'2025-01-01 09:00:00','2025-01-01 09:00:00',2),(11,'镜若汐','1','2026-07-18','1970-02-07',30,90,'2025-02-15 09:00:00','2025-02-15 09:00:00',2),(12,'前端就业93期','306','2025-05-10','2025-10-10',36,2,'2025-04-20 09:00:00','2025-04-20 09:00:00',2),(13,'大数据就业59期','307','2025-02-01','2025-08-01',7,3,'2025-01-15 09:00:00','2025-01-15 09:00:00',2),(14,'大数据就业60期','308','2025-04-15','2025-10-15',7,3,'2025-04-01 09:00:00','2025-04-01 09:00:00',2),(15,'大数据就业61期','309','2025-06-01','2025-12-01',10,3,'2025-05-15 09:00:00','2025-05-15 09:00:00',2),(16,'Python就业30期','310','2025-01-20','2025-07-20',20,4,'2025-01-01 09:00:00','2025-01-01 09:00:00',2),(17,'Python就业31期','311','2025-03-10','2025-09-10',6,4,'2025-02-20 09:00:00','2025-02-20 09:00:00',2),(18,'Python就业32期','312','2025-05-05','2025-11-05',36,4,'2025-04-15 09:00:00','2025-04-15 09:00:00',2),(19,'测试就业20期','313','2025-02-10','2025-07-10',10,5,'2025-01-20 09:00:00','2025-01-20 09:00:00',2),(20,'测试就业21期','314','2025-04-01','2025-09-01',3,5,'2025-03-15 09:00:00','2025-03-15 09:00:00',2),(21,'测试就业22期','315','2025-06-15','2025-11-15',7,5,'2025-06-01 09:00:00','2025-06-01 09:00:00',2),(22,'JavaEE就业171期','316','2025-04-01','2025-10-01',10,1,'2025-03-15 09:00:00','2025-03-15 09:00:00',2),(23,'JavaEE就业172期','317','2025-05-15','2025-11-15',6,1,'2025-05-01 09:00:00','2025-05-01 09:00:00',2),(24,'JavaEE就业173期','318','2025-06-20','2025-12-20',20,1,'2025-06-01 09:00:00','2025-06-01 09:00:00',2),(25,'前端就业94期','319','2025-06-01','2025-11-01',3,2,'2025-05-20 09:00:00','2025-05-20 09:00:00',2),(26,'前端就业95期','320','2025-07-10','2025-12-10',36,2,'2025-06-20 09:00:00','2025-06-20 09:00:00',2),(27,'大数据就业62期','321','2025-07-01','2026-01-01',7,3,'2025-06-15 09:00:00','2025-06-15 09:00:00',2),(28,'大数据就业63期','322','2025-08-15','2026-02-15',10,3,'2025-08-01 09:00:00','2025-08-01 09:00:00',2),(29,'Python就业33期','323','2025-07-05','2026-01-05',20,4,'2025-06-20 09:00:00','2025-06-20 09:00:00',2),(30,'Python就业34期','324','2025-08-20','2026-02-20',6,4,'2025-08-01 09:00:00','2025-08-01 09:00:00',2),(31,'敬芳','100','1987-04-09','2018-08-18',95,86,NULL,NULL,2),(32,'融辉','ut minim culpa','1982-10-22','2004-03-11',27,15,NULL,NULL,2),(34,'齐沐阳','aliquip','1991-08-10','1997-08-30',99,82,NULL,NULL,2),(36,'管沐阳','elit esse incididunt','2024-03-26','1984-01-16',84,68,NULL,NULL,2),(38,'门沐阳','veni','2014-07-12','1982-07-21',44,32,NULL,NULL,2);
/*!40000 ALTER TABLE `clazz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dept`
--

DROP TABLE IF EXISTS `dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dept` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID, 主键',
  `name` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '部门名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='部门表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dept`
--

LOCK TABLES `dept` WRITE;
/*!40000 ALTER TABLE `dept` DISABLE KEYS */;
INSERT INTO `dept` (`id`, `name`, `create_time`, `update_time`) VALUES (1,'学工部','2026-08-15 18:25:29','2026-08-15 18:25:29'),(2,'教研部','2026-08-15 18:25:29','2026-08-15 18:25:29'),(3,'咨询部','2026-08-15 18:25:29','2026-08-15 18:25:29'),(4,'就业部','2026-08-15 18:25:29','2026-08-15 18:25:29'),(5,'人事部','2026-08-15 18:25:29','2026-08-15 18:25:29');
/*!40000 ALTER TABLE `dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emp`
--

DROP TABLE IF EXISTS `emp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `emp` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID,主键',
  `username` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '123456' COMMENT '密码',
  `role` tinyint(1) NOT NULL DEFAULT 2 COMMENT '角色:1-管理员,2-普通用户',
  `name` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '姓名',
  `gender` tinyint(3) unsigned NOT NULL COMMENT '性别, 1:男, 2:女',
  `phone` char(11) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号',
  `job` tinyint(3) unsigned DEFAULT NULL COMMENT '职位, 1 班主任, 2 讲师 , 3 学工主管, 4 教研主管, 5 咨询师',
  `salary` int(10) unsigned DEFAULT NULL COMMENT '薪资',
  `image` varchar(300) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像',
  `entry_date` date DEFAULT NULL COMMENT '入职日期',
  `dept_id` int(10) unsigned DEFAULT NULL COMMENT '部门ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='员工表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emp`
--

LOCK TABLES `emp` WRITE;
/*!40000 ALTER TABLE `emp` DISABLE KEYS */;
INSERT INTO `emp` (`id`, `username`, `password`, `role`, `name`, `gender`, `phone`, `job`, `salary`, `image`, `entry_date`, `dept_id`, `create_time`, `update_time`) VALUES (6,'admin','123456',1,'系统管理员',1,'85986244088',3,20400,'https://java-ai-muli.oss-cn-beijing.aliyuncs.com/2026/08/d4b5540a-54ae-4f75-92de-855ec657b4df.png','2020-01-01',1,'2023-10-20 16:35:33','2026-08-25 18:55:27'),(7,'chaijin','123456',2,'柴进',1,'13309090007',1,10,'https://java-ai-muli.oss-cn-beijing.aliyuncs.com/2026/08/59db1b5a-a645-4cf5-9d93-86c5b2c99555.png','2005-08-01',1,'2023-10-20 16:35:33','2026-08-25 17:13:25'),(8,'likui','123456',2,'李逵',1,'13309090008',1,4800,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2014-11-09',1,'2023-10-20 16:35:33','2023-10-20 16:35:49'),(9,'wusong','123456',2,'武松',1,'13309090009',1,4900,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2011-03-11',1,'2023-10-20 16:35:33','2023-10-20 16:35:51'),(10,'linchong','123456',2,'林冲',1,'13309090010',1,5000,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2013-09-05',1,'2023-10-20 16:35:33','2023-10-20 16:35:53'),(11,'huyanzhuo','123456',2,'呼延灼',1,'13309090011',2,9700,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2007-02-01',2,'2023-10-20 16:35:33','2023-10-20 16:35:55'),(12,'xiaoliguang','123456',2,'小李广',1,'13309090012',2,10000,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2008-08-18',2,'2023-10-20 16:35:33','2023-10-20 16:35:57'),(13,'yangzhi','123456',2,'杨志',1,'13309090013',1,5300,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2012-11-01',1,'2023-10-20 16:35:33','2023-10-20 16:35:59'),(15,'sunerniang','123456',2,'孙二娘',2,'13309090015',2,10900,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2011-05-01',2,'2023-10-20 16:35:33','2023-10-20 16:36:03'),(16,'luzhishen','123456',2,'鲁智深',1,'13309090016',2,9600,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2010-01-01',2,'2023-10-20 16:35:33','2023-10-20 16:36:05'),(17,'liying','12345678',2,'李应',1,'13309090017',1,5800,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2015-03-21',1,'2023-10-20 16:35:33','2023-10-20 16:36:07'),(18,'shiqian','123456',2,'时迁',1,'13309090018',2,10200,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2015-01-01',2,'2023-10-20 16:35:33','2023-10-20 16:36:09'),(19,'gudasao','123456',2,'顾大嫂',2,'13309090019',2,10500,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2008-01-01',2,'2023-10-20 16:35:33','2023-10-20 16:36:11'),(20,'ruanxiaoer','123456',2,'阮小二',1,'13309090020',2,10800,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2018-01-01',2,'2023-10-20 16:35:33','2023-10-20 16:36:13'),(21,'ruanxiaowu','123456',2,'阮小五',1,'13309090021',5,5200,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2015-01-01',3,'2023-10-20 16:35:33','2023-10-20 16:36:15'),(22,'ruanxiaoqi','123456',2,'阮小七',1,'13309090022',5,5500,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2016-01-01',3,'2023-10-20 16:35:33','2023-10-20 16:36:17'),(23,'ruanji','123456',2,'阮籍',1,'13309090023',5,5800,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2012-01-01',3,'2023-10-20 16:35:33','2023-10-20 16:36:19'),(24,'tongwei','123456',2,'童威',1,'13309090024',5,5000,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2006-01-01',3,'2023-10-20 16:35:33','2023-10-20 16:36:21'),(25,'tongmeng','123456',2,'童猛',1,'13309090025',5,4800,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2002-01-01',3,'2023-10-20 16:35:33','2023-10-20 16:36:23'),(26,'yanshun','123456',2,'燕顺',1,'13309090026',5,5400,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2011-01-01',3,'2023-10-20 16:35:33','2023-11-08 22:12:46'),(27,'lijun','123456',2,'李俊',1,'13309090027',2,6600,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2004-01-01',2,'2023-10-20 16:35:33','2023-11-16 17:56:59'),(28,'lizhong','123456',2,'李忠',1,'13309090028',5,5000,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2007-01-01',3,'2023-10-20 16:35:33','2023-11-17 16:34:22'),(30,'liyun','123456',2,'李云',1,'13309090030',2,7000,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2020-03-01',2,'2023-10-20 16:35:33','2023-10-20 16:36:31'),(36,'linghuchong','123456',2,'令狐冲',1,'18809091212',2,6800,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2023-10-19',2,'2023-10-20 20:44:54','2023-11-09 09:41:04'),(41,'zhangsan','123456',2,'张三',1,'13800138000',2,15000,'https://example.com/avatar/zhangsan.jpg','2024-06-01',1,'2026-08-17 11:15:52','2026-08-17 11:15:52'),(50,'111','123456',2,'张三',1,'12345678901',1,8000,'1.jpg','2020-01-01',1,'2026-08-18 09:50:04','2026-08-18 09:50:04'),(56,'wangfang','123456',2,'王芳',2,'13800001001',1,5500,'','2019-03-15',1,'2026-08-25 16:27:39','2026-08-25 16:27:39'),(57,'zhaoli','123456',2,'赵莉',2,'13800001002',1,5200,'','2020-06-20',1,'2026-08-25 16:27:39','2026-08-25 16:27:39'),(58,'chenwei','123456',2,'陈伟',1,'13800001003',3,12000,'','2018-01-10',1,'2026-08-25 16:27:39','2026-08-25 16:27:39'),(59,'liumei','123456',2,'刘梅',2,'13800001004',4,13000,'','2017-05-18',2,'2026-08-25 16:27:39','2026-08-25 16:27:39'),(60,'zhouqiang','123456',2,'周强',1,'13800001005',3,11500,'','2019-09-01',1,'2026-08-25 16:27:39','2026-08-25 16:27:39'),(61,'wuxia','123456',2,'吴霞',2,'13800001006',4,14000,'','2016-03-22',2,'2026-08-25 16:27:39','2026-08-25 16:27:39'),(62,'sunlei','123456',2,'孙磊',1,'13800001007',5,6000,'','2021-07-08',3,'2026-08-25 16:27:39','2026-08-25 16:27:39'),(63,'huanglan','123456',2,'黄兰',2,'13800001008',5,5800,'','2022-02-14',3,'2026-08-25 16:27:39','2026-08-25 16:27:39'),(64,'zhaojun','123456',2,'赵军',1,'13800001009',5,6200,'','2020-11-30',3,'2026-08-25 16:27:39','2026-08-25 16:27:39'),(65,'qianli','123456',2,'钱丽',2,'13800001010',1,5000,'','2023-04-12',1,'2026-08-25 16:27:39','2026-08-25 16:27:39'),(66,'fengtao','123456',2,'冯涛',1,'13800001011',2,9500,'','2019-08-25',2,'2026-08-25 16:27:39','2026-08-25 16:27:39'),(67,'xujing','123456',2,'徐静',2,'13800001012',2,11000,'','2018-11-03',2,'2026-08-25 16:27:39','2026-08-25 16:27:39'),(68,'heping','123456',2,'何平',1,'13800001013',1,4800,'','2024-01-15',1,'2026-08-25 16:27:39','2026-08-25 16:27:39'),(69,'caixia','123456',2,'蔡霞',2,'13800001014',2,10500,'','2020-07-20',2,'2026-08-25 16:27:39','2026-08-25 16:27:39'),(70,'denghao','123456',2,'邓浩',1,'13800001015',5,6500,'','2021-09-28',3,'2026-08-25 16:27:39','2026-08-25 16:27:39'),(71,'panmin','123456',2,'潘敏',2,'13800001016',3,12500,'','2017-12-05',1,'2026-08-25 16:27:39','2026-08-25 16:27:39'),(72,'jiewei','123456',2,'杰伟',1,'13800001017',4,13500,'','2016-06-18',2,'2026-08-25 16:27:39','2026-08-25 16:27:39'),(73,'tangyue','123456',2,'唐月',2,'13800001018',1,5600,'','2023-08-10',1,'2026-08-25 16:27:39','2026-08-25 16:27:39'),(74,'luoyong','123456',2,'罗勇',1,'13800001019',2,10000,'','2019-04-22',2,'2026-08-25 16:27:39','2026-08-25 16:27:39'),(75,'xiejie','123456',2,'谢洁',2,'13800001020',5,5900,'','2022-05-17',3,'2026-08-25 16:27:39','2026-08-25 16:27:39'),(76,'haojie','123456',2,'郝杰',1,'13800001021',1,5300,'','2022-06-01',4,'2026-08-25 16:28:08','2026-08-25 16:28:08'),(77,'songna','123456',2,'宋娜',2,'13800001022',2,9800,'','2021-03-15',4,'2026-08-25 16:28:08','2026-08-25 16:28:08'),(78,'majian','123456',2,'马健',1,'13800001023',3,11800,'','2018-09-20',4,'2026-08-25 16:28:08','2026-08-25 16:28:08'),(79,'dwd','123456',2,'孙杰',1,'13056011030',1,199,'','2026-08-13',1,'2026-08-25 16:43:50','2026-08-25 16:44:19');
/*!40000 ALTER TABLE `emp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emp_expr`
--

DROP TABLE IF EXISTS `emp_expr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `emp_expr` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID, 主键',
  `emp_id` int(10) unsigned DEFAULT NULL COMMENT '员工ID',
  `begin` date DEFAULT NULL COMMENT '开始时间',
  `end` date DEFAULT NULL COMMENT '结束时间',
  `company` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公司名称',
  `job` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '职位',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='工作经历';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emp_expr`
--

LOCK TABLES `emp_expr` WRITE;
/*!40000 ALTER TABLE `emp_expr` DISABLE KEYS */;
INSERT INTO `emp_expr` (`id`, `emp_id`, `begin`, `end`, `company`, `job`) VALUES (2,41,'2020-07-01','2024-05-31','腾讯科技','高级Java开发工程师'),(3,41,'2026-08-18','2026-08-19','阿里巴巴','Java开发工程师'),(4,41,'2018-07-01','2020-06-30','阿里巴巴','Java开发工程师'),(5,41,'2018-07-01','2020-06-30','阿里巴巴','Java开发工程师'),(6,41,'2018-07-01','2020-06-30','阿里巴巴','Java开发工程师'),(7,41,'2018-07-01','2020-06-30','阿里巴巴','Java开发工程师'),(19,6,'2016-12-14','2020-06-24','dwdaw','wda');
/*!40000 ALTER TABLE `emp_expr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emp_log`
--

DROP TABLE IF EXISTS `emp_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `emp_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID, 主键',
  `operate_time` datetime DEFAULT NULL COMMENT '操作时间',
  `info` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '日志信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='员工日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emp_log`
--

LOCK TABLES `emp_log` WRITE;
/*!40000 ALTER TABLE `emp_log` DISABLE KEYS */;
INSERT INTO `emp_log` (`id`, `operate_time`, `info`) VALUES (5,'2026-08-18 10:31:33','Emp(id=null, username=111, password=null, name=张三, gender=1, phone=12345678901, job=1, salary=8000, image=1.jpg, entryDate=2020-01-01, deptId=1, createTime=2026-08-18T10:31:33.597590, updateTime=2026-08-18T10:31:33.597590, deptName=null, exprList=null)'),(6,'2026-08-25 16:15:06','[员工管理] 修改员工 | 方法：updateById，参数：Emp(id=7, username=chaijin, password=123456, role=null, name=柴进, gender=1, phone=13309090007, job=1, salary=4700, image=https://java-ai-muli.oss-cn-beijing.aliyuncs.com/2026/08/3ac30a8e-6104-48f0-91ef-7c1aa90f95d6.png, entryDate=2005-08-01, deptId=1, createTime=2023-10-20T16:35:33, updateTime=2023-10-20T16:35:47, deptName=null, exprList=[])，结果：成功 | 耗时：20ms'),(7,'2026-08-25 16:43:50','[员工管理] 新增员工 | 方法：save，参数：Emp(id=null, username=dwd, password=123456, role=null, name=孙杰, gender=1, phone=13056011030, job=1, salary=199, image=, entryDate=2026-08-13, deptId=1, createTime=null, updateTime=null, deptName=null, exprList=null)，结果：成功 | 耗时：15ms'),(8,'2026-08-25 16:44:20','[员工管理] 修改员工 | 方法：updateById，参数：Emp(id=79, username=dwd, password=123456, role=null, name=孙杰, gender=1, phone=13056011030, job=1, salary=199, image=, entryDate=2026-08-13, deptId=1, createTime=2026-08-25T16:43:50, updateTime=2026-08-25T16:43:50, deptName=null, exprList=[])，结果：成功 | 耗时：3ms'),(9,'2026-08-25 16:50:23','[员工管理] 修改员工 | 方法：updateById，参数：Emp(id=6, username=admin, password=123456, role=null, name=系统管理员, gender=1, phone=85986244088, job=3, salary=20000, image=https://loremflickr.com/400/400?lock=3878870684244681, entryDate=2020-01-01, deptId=5, createTime=2023-10-20T16:35:33, updateTime=2026-08-19T09:21:10, deptName=null, exprList=[EmpExpr(id=8, empId=6, begin=2016-12-21, end=2020-06-30, company=某公司, job=讲师)])，结果：成功 | 耗时：16ms'),(10,'2026-08-25 16:55:19','[员工管理] 修改员工 | 方法：updateById，参数：Emp(id=6, username=admin, password=123456, role=null, name=系统管理员, gender=1, phone=85986244088, job=3, salary=20000, image=https://loremflickr.com/400/400?lock=3878870684244681, entryDate=2020-01-01, deptId=5, createTime=2023-10-20T16:35:33, updateTime=2026-08-25T16:50:23, deptName=null, exprList=[EmpExpr(id=9, empId=6, begin=2016-12-21, end=2020-06-30, company=某公司, job=讲师)])，结果：成功 | 耗时：17ms'),(11,'2026-08-25 16:58:29','[员工管理] 修改员工 | 方法：updateById，参数：Emp(id=6, username=admin, password=123456, role=null, name=系统管理员, gender=1, phone=85986244088, job=3, salary=20000, image=https://loremflickr.com/400/400?lock=3878870684244681, entryDate=2020-01-01, deptId=5, createTime=2023-10-20T16:35:33, updateTime=2026-08-25T16:55:19, deptName=null, exprList=[EmpExpr(id=10, empId=6, begin=2016-12-21, end=2020-06-30, company=某公司, job=讲师)])，结果：成功 | 耗时：20ms'),(12,'2026-08-25 16:58:44','[员工管理] 修改员工 | 方法：updateById，参数：Emp(id=6, username=admin, password=123456, role=null, name=系统管理员, gender=1, phone=85986244088, job=3, salary=20000.01, image=https://loremflickr.com/400/400?lock=3878870684244681, entryDate=2020-01-01, deptId=1, createTime=2023-10-20T16:35:33, updateTime=2026-08-25T16:58:29, deptName=null, exprList=[EmpExpr(id=11, empId=6, begin=2016-12-21, end=2020-06-30, company=某公司, job=讲师)])，结果：成功 | 耗时：3ms'),(13,'2026-08-25 16:59:00','[员工管理] 修改员工 | 方法：updateById，参数：Emp(id=7, username=chaijin, password=123456, role=null, name=柴进, gender=1, phone=13309090007, job=1, salary=10.11, image=https://java-ai-muli.oss-cn-beijing.aliyuncs.com/2026/08/3ac30a8e-6104-48f0-91ef-7c1aa90f95d6.png, entryDate=2005-08-01, deptId=1, createTime=2023-10-20T16:35:33, updateTime=2026-08-25T16:15:06, deptName=null, exprList=[])，结果：成功 | 耗时：2ms'),(14,'2026-08-25 17:01:49','[员工管理] 修改员工 | 方法：updateById，参数：Emp(id=7, username=chaijin, password=123456, role=null, name=柴进, gender=1, phone=13309090007, job=1, salary=10, image=https://java-ai-muli.oss-cn-beijing.aliyuncs.com/2026/08/3ac30a8e-6104-48f0-91ef-7c1aa90f95d6.png, entryDate=2005-08-01, deptId=1, createTime=2023-10-20T16:35:33, updateTime=2026-08-25T16:59, deptName=null, exprList=[])，结果：成功 | 耗时：14ms'),(15,'2026-08-25 17:13:12','[员工管理] 修改员工 | 方法：updateById，参数：Emp(id=6, username=admin, password=123456, role=null, name=系统管理员, gender=1, phone=85986244088, job=3, salary=20000, image=https://java-ai-muli.oss-cn-beijing.aliyuncs.com/2026/08/558311e5-fcf4-464c-80bf-6b203a1e0672.png, entryDate=2020-01-01, deptId=1, createTime=2023-10-20T16:35:33, updateTime=2026-08-25T16:58:44, deptName=null, exprList=[EmpExpr(id=12, empId=6, begin=2016-12-21, end=2020-06-30, company=某公司, job=讲师)])，结果：成功 | 耗时：23ms'),(16,'2026-08-25 17:13:25','[员工管理] 修改员工 | 方法：updateById，参数：Emp(id=7, username=chaijin, password=123456, role=null, name=柴进, gender=1, phone=13309090007, job=1, salary=10, image=https://java-ai-muli.oss-cn-beijing.aliyuncs.com/2026/08/59db1b5a-a645-4cf5-9d93-86c5b2c99555.png, entryDate=2005-08-01, deptId=1, createTime=2023-10-20T16:35:33, updateTime=2026-08-25T17:01:49, deptName=null, exprList=[])，结果：成功 | 耗时：4ms'),(17,'2026-08-25 17:13:50','[员工管理] 修改员工 | 方法：updateById，参数：Emp(id=6, username=admin, password=123456, role=null, name=系统管理员, gender=1, phone=85986244088, job=3, salary=20000, image=https://java-ai-muli.oss-cn-beijing.aliyuncs.com/2026/08/7d2c0097-0a3f-49e2-a24d-7975266682b4.png, entryDate=2020-01-01, deptId=1, createTime=2023-10-20T16:35:33, updateTime=2026-08-25T17:13:12, deptName=null, exprList=[EmpExpr(id=13, empId=6, begin=2016-12-21, end=2020-06-30, company=某公司, job=讲师)])，结果：成功 | 耗时：5ms'),(18,'2026-08-25 17:13:58','[员工管理] 修改员工 | 方法：updateById，参数：Emp(id=6, username=admin, password=123456, role=null, name=系统管理员, gender=1, phone=85986244088, job=3, salary=20400, image=https://java-ai-muli.oss-cn-beijing.aliyuncs.com/2026/08/7d2c0097-0a3f-49e2-a24d-7975266682b4.png, entryDate=2020-01-01, deptId=1, createTime=2023-10-20T16:35:33, updateTime=2026-08-25T17:13:50, deptName=null, exprList=[EmpExpr(id=14, empId=6, begin=2016-12-21, end=2020-06-30, company=某公司, job=讲师)])，结果：成功 | 耗时：3ms'),(19,'2026-08-25 17:17:47','[员工管理] 批量删除员工 | 方法：deleteByIds，参数：[14]，结果：成功 | 耗时：4ms'),(20,'2026-08-25 17:20:29','[员工管理] 修改员工 | 方法：updateById，参数：Emp(id=6, username=admin, password=123456, role=null, name=系统管理员, gender=1, phone=85986244088, job=3, salary=20400.1, image=https://java-ai-muli.oss-cn-beijing.aliyuncs.com/2026/08/7d2c0097-0a3f-49e2-a24d-7975266682b4.png, entryDate=2020-01-01, deptId=1, createTime=2023-10-20T16:35:33, updateTime=2026-08-25T17:13:58, deptName=null, exprList=[EmpExpr(id=15, empId=6, begin=2016-12-21, end=2020-06-30, company=某公司, job=讲师)])，结果：成功 | 耗时：2ms'),(21,'2026-08-25 17:27:32','[员工管理] 修改员工 | 方法：updateById，参数：Emp(id=6, username=admin, password=123456, role=null, name=系统管理员, gender=1, phone=85986244088, job=3, salary=20400, image=https://java-ai-muli.oss-cn-beijing.aliyuncs.com/2026/08/65843abf-c07a-4763-9362-406b2f77dc34.png, entryDate=2020-01-01, deptId=1, createTime=2023-10-20T16:35:33, updateTime=2026-08-25T17:20:29, deptName=null, exprList=[EmpExpr(id=16, empId=6, begin=2016-12-14, end=2020-06-24, company=dwdaw, job=wda)])，结果：成功 | 耗时：15ms'),(22,'2026-08-25 18:23:57','[员工管理] 修改员工 | 方法：updateById，参数：Emp(id=6, username=admin, password=123456, role=null, name=系统管理员, gender=1, phone=85986244088, job=3, salary=20400, image=https://java-ai-muli.oss-cn-beijing.aliyuncs.com/2026/08/cc7891ae-ddb2-48c3-8b0f-a0a219295f36.png, entryDate=2020-01-01, deptId=1, createTime=2023-10-20T16:35:33, updateTime=2026-08-25T17:27:32, deptName=null, exprList=[EmpExpr(id=17, empId=6, begin=2016-12-14, end=2020-06-24, company=dwdaw, job=wda)])，结果：成功 | 耗时：15ms'),(23,'2026-08-25 18:55:27','[员工管理] 修改员工 | 方法：updateById，参数：Emp(id=6, username=admin, password=123456, role=null, name=系统管理员, gender=1, phone=85986244088, job=3, salary=20400, image=https://java-ai-muli.oss-cn-beijing.aliyuncs.com/2026/08/d4b5540a-54ae-4f75-92de-855ec657b4df.png, entryDate=2020-01-01, deptId=1, createTime=2023-10-20T16:35:33, updateTime=2026-08-25T18:23:57, deptName=null, exprList=[EmpExpr(id=18, empId=6, begin=2016-12-14, end=2020-06-24, company=dwdaw, job=wda)])，结果：成功 | 耗时：3ms');
/*!40000 ALTER TABLE `emp_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operate_log`
--

DROP TABLE IF EXISTS `operate_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `operate_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `operate_emp_id` int(10) unsigned DEFAULT NULL COMMENT '操作人ID',
  `operate_time` datetime DEFAULT NULL COMMENT '操作时间',
  `class_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作的类名',
  `method_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作的方法名',
  `method_params` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '方法参数',
  `return_value` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '返回值, 存储json格式',
  `cost_time` int(11) DEFAULT NULL COMMENT '方法执行耗时, 单位:ms',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='操作日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operate_log`
--

LOCK TABLES `operate_log` WRITE;
/*!40000 ALTER TABLE `operate_log` DISABLE KEYS */;
INSERT INTO `operate_log` (`id`, `operate_emp_id`, `operate_time`, `class_name`, `method_name`, `method_params`, `return_value`, `cost_time`) VALUES (1,1,'2026-08-24 15:06:45','cn.hytc.mysql.controller.ClazzController','add','[Clazz(id=30, name=融辉, room=ut minim culpa, beginDate=1982-10-22, endDate=2004-03-11, masterId=27, subject=15, createTime=1987-07-09T12:39:19, updateTime=1972-08-04T10:22:24, masterName=位奕辰, status=sed amet)]','Result(code=200, msg=success, data=添加成功)',194),(2,7,'2026-08-24 16:17:38','cn.hytc.mysql.controller.ClazzController','add','[Clazz(id=77, name=门沐阳, room=veni, beginDate=2014-07-12, endDate=1982-07-21, masterId=44, subject=32, createTime=2025-09-14T06:41:06, updateTime=2016-10-01T14:13:27, masterName=任乙萍, status=Duis)]','Result(code=200, msg=success, data=添加成功)',5);
/*!40000 ALTER TABLE `operate_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID,主键',
  `name` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '姓名',
  `no` char(10) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '学号',
  `gender` tinyint(3) unsigned NOT NULL COMMENT '性别, 1: 男, 2: 女',
  `phone` varchar(11) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号',
  `id_card` char(18) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '身份证号',
  `is_college` tinyint(3) unsigned NOT NULL COMMENT '是否来自于院校, 1:是, 0:否',
  `address` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系地址',
  `degree` tinyint(3) unsigned DEFAULT NULL COMMENT '最高学历, 1:初中, 2:高中, 3:大专, 4:本科, 5:硕士, 6:博士',
  `graduation_date` date DEFAULT NULL COMMENT '毕业时间',
  `clazz_id` int(10) unsigned NOT NULL COMMENT '班级ID, 关联班级表ID',
  `violation_count` tinyint(3) unsigned NOT NULL DEFAULT 0 COMMENT '违纪次数',
  `violation_score` tinyint(3) unsigned NOT NULL DEFAULT 0 COMMENT '违纪扣分',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `no` (`no`),
  UNIQUE KEY `phone` (`phone`),
  UNIQUE KEY `id_card` (`id_card`),
  CONSTRAINT `chk_student_gender` CHECK (`gender` in (1,2))
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学员表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` (`id`, `name`, `no`, `gender`, `phone`, `id_card`, `is_college`, `address`, `degree`, `graduation_date`, `clazz_id`, `violation_count`, `violation_score`, `create_time`, `update_time`) VALUES (2,'邱雨桐','aliqua',2,'13222345678','120100195604098500',0,'厚栋527号',21,'2019-07-16',5,182,35,'2024-11-14 21:22:19','2024-11-14 21:22:19'),(4,'萧远山','2022000004',1,'18800003211','110120000300200004',1,'北京市昌平区建材城西路4号',3,'2024-07-01',1,0,0,'2024-11-14 21:22:19','2024-11-14 21:22:19'),(5,'阿朱','2022000005',2,'18800160002','110120000300200005',1,'北京市昌平区建材城西路5号',4,'2020-07-01',1,0,0,'2024-11-14 21:22:19','2024-11-14 21:22:19'),(6,'阿紫','2022000006',2,'18800000034','110120000300200006',1,'北京市昌平区建材城西路6号',4,'2021-07-01',2,0,0,'2024-11-14 21:22:19','2024-11-14 21:22:19'),(7,'游坦之','2022000007',1,'18800000067','110120000300200007',1,'北京市昌平区建材城西路7号',4,'2022-07-01',2,0,0,'2024-11-14 21:22:19','2024-11-14 21:22:19'),(8,'康敏','2022000008',2,'18800000077','110120000300200008',1,'北京市昌平区建材城西路8号',5,'2024-07-01',2,0,0,'2024-11-14 21:22:19','2024-11-14 21:22:19'),(9,'徐长老','2022000009',1,'18800000341','110120000300200009',1,'北京市昌平区建材城西路9号',3,'2024-07-01',2,0,0,'2024-11-14 21:22:19','2024-11-14 21:22:19'),(10,'云中鹤','2022000010',1,'18800006571','110120000300200010',1,'北京市昌平区建材城西路10号',2,'2020-07-01',2,0,0,'2024-11-14 21:22:19','2024-11-14 21:22:19'),(11,'钟万仇','2022000011',1,'18800000391','110120000300200011',1,'北京市昌平区建材城西路11号',4,'2021-07-01',1,0,0,'2024-11-14 21:22:19','2024-11-15 16:21:24'),(12,'崔百泉','2022000012',1,'18800000781','110120000300200018',1,'北京市昌平区建材城西路12号',4,'2022-07-05',3,6,17,'2024-11-14 21:22:19','2024-12-13 14:33:58'),(13,'耶律洪基','2022000013',1,'18800008901','110120000300200013',1,'北京市昌平区建材城西路13号',4,'2024-07-01',2,0,0,'2024-11-14 21:22:19','2024-11-15 16:21:21'),(14,'天山童姥','2022000014',2,'18800009201','110120000300200014',1,'北京市昌平区建材城西路14号',4,'2024-07-01',1,0,0,'2024-11-14 21:22:19','2024-11-15 16:21:17'),(15,'刘竹庄','2022000015',1,'18800009401','110120000300200015',1,'北京市昌平区建材城西路15号',3,'2020-07-01',4,0,0,'2024-11-14 21:22:19','2024-11-14 21:22:19'),(16,'李春来','2022000016',1,'18800008501','110120000300200016',1,'北京市昌平区建材城西路16号',4,'2021-07-01',4,0,0,'2024-11-14 21:22:19','2024-11-14 21:22:19'),(17,'王语嫣','2022000017',2,'18800007601','110120000300200017',1,'北京市昌平区建材城西路17号',2,'2022-07-01',4,0,0,'2024-11-14 21:22:19','2024-11-14 21:22:19'),(18,'郑成功','2024001101',1,'13309092345','110110110110110110',0,'北京市昌平区回龙观街道88号',5,'2021-07-01',3,2,7,'2024-11-15 16:26:18','2024-11-15 16:40:10'),(19,'张三','2024001201',1,'13812345678','110101200001011234',1,'北京市海淀区中关村',4,'2024-07-01',3,0,0,'2026-08-20 15:38:07','2026-08-20 15:38:07'),(32,'张伟','2025000001',1,'13600001001','110101200001010001',1,'北京市朝阳区',4,'2023-06-30',3,0,0,NULL,NULL),(33,'王芳','2025000002',2,'13600001002','110101200001010002',1,'上海市浦东新区',3,'2022-06-30',4,0,0,NULL,NULL),(34,'李娜','2025000003',2,'13600001003','110101200001010003',1,'广州市天河区',4,'2023-06-30',6,0,0,NULL,NULL),(35,'刘洋','2025000004',1,'13600001004','110101200001010004',0,'深圳市南山区',2,NULL,7,1,3,NULL,NULL),(36,'陈静','2025000005',2,'13600001005','110101200001010005',1,'杭州市西湖区',5,'2022-07-01',8,0,0,NULL,NULL),(37,'杨帆','2025000006',1,'13600001006','110101200001010006',1,'南京市鼓楼区',3,'2021-06-30',9,0,0,NULL,NULL),(38,'赵磊','2025000007',1,'13600001007','110101200001010007',0,'成都市武侯区',4,NULL,10,0,0,NULL,NULL),(39,'黄敏','2025000008',2,'13600001008','110101200001010008',1,'武汉市洪山区',2,'2020-07-01',12,2,5,NULL,NULL),(40,'周杰','2025000009',1,'13600001009','110101200001010009',1,'西安市雁塔区',6,'2019-06-30',13,0,0,NULL,NULL),(41,'吴倩','2025000010',2,'13600001010','110101200001010010',1,'重庆市渝北区',3,'2023-06-30',14,0,0,NULL,NULL),(42,'徐强','2025000011',1,'13600001011','110101200001010011',0,'苏州市工业园区',4,NULL,15,0,0,NULL,NULL),(43,'孙丽','2025000012',2,'13600001012','110101200001010012',1,'长沙市岳麓区',1,'2021-06-30',16,0,0,NULL,NULL);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-08-27 22:32:50
