-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: k6a301.p.ssafy.io    Database: darly
-- ------------------------------------------------------
-- Server version	5.7.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema darly
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `darly` ;

-- -----------------------------------------------------
-- Schema darly
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `darly` DEFAULT CHARACTER SET utf8mb4 ;
USE `darly` ;

--
-- Table structure for table `tb_address`
--

DROP TABLE IF EXISTS `tb_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_address` (
  `address_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'AI',
  `address_name` varchar(100) NOT NULL,
  PRIMARY KEY (`address_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_address`
--

LOCK TABLES `tb_address` WRITE;
/*!40000 ALTER TABLE `tb_address` DISABLE KEYS */;
INSERT INTO `tb_address` VALUES (1,'모든 지역'),(2,'서울특별시 중구'),(3,'서울특별시 용산구'),(4,'서울특별시 성동구'),(5,'서울특별시 광진구'),(6,'서울특별시 동대문구'),(7,'서울특별시 중랑구'),(8,'서울특별시 성북구'),(9,'서울특별시 강북구'),(10,'서울특별시 도봉구'),(11,'서울특별시 노원구'),(12,'서울특별시 은평구'),(13,'서울특별시 서대문구'),(14,'서울특별시 마포구'),(15,'서울특별시 양천구'),(16,'서울특별시 강서구'),(17,'서울특별시 구로구'),(18,'서울특별시 금천구'),(19,'서울특별시 영등포구'),(20,'서울특별시 동작구'),(21,'서울특별시 관악구'),(22,'서울특별시 강남구'),(23,'서울특별시 송파구'),(24,'서울특별시 강동구'),(25,'부산광역시 중구'),(26,'부산광역시 서구'),(27,'부산광역시 동구'),(28,'부산광역시 영도구'),(29,'부산광역시 부산진구'),(30,'부산광역시 동래구'),(31,'부산광역시 남구'),(32,'부산광역시 북구'),(33,'부산광역시 해운대구'),(34,'부산광역시 사하구'),(35,'부산광역시 금정구'),(36,'부산광역시 강서구'),(37,'부산광역시 연제구'),(38,'부산광역시 수영구'),(39,'부산광역시 사상구'),(40,'부산광역시 기장군'),(41,'대구광역시 중구'),(42,'대구광역시 동구'),(43,'대구광역시 서구'),(44,'대구광역시 남구'),(45,'대구광역시 북구'),(46,'대구광역시 수성구'),(47,'대구광역시 달서구'),(48,'대구광역시 달성군'),(49,'인천광역시 중구'),(50,'인천광역시 동구'),(51,'인천광역시 미추홀구'),(52,'인천광역시 연수구'),(53,'인천광역시 남동구'),(54,'인천광역시 부평구'),(55,'인천광역시 계양구'),(56,'인천광역시 서구'),(57,'인천광역시 강화군'),(58,'인천광역시 옹진군'),(59,'광주광역시 동구'),(60,'광주광역시 서구'),(61,'광주광역시 남구'),(62,'광주광역시 북구'),(63,'광주광역시 광산구'),(64,'대전광역시 동구'),(65,'대전광역시 중구'),(66,'대전광역시 서구'),(67,'대전광역시 유성구'),(68,'대전광역시 대덕구'),(69,'울산광역시 중구'),(70,'울산광역시 남구'),(71,'울산광역시 동구'),(72,'울산광역시 북구'),(73,'울산광역시 울주군'),(74,'세종특별자치시 세종시'),(75,'경기도 수원시'),(76,'경기도 장안구'),(77,'경기도 권선구'),(78,'경기도 팔달구'),(79,'경기도 영통구'),(80,'경기도 성남시'),(81,'경기도 수정구'),(82,'경기도 중원구'),(83,'경기도 분당구'),(84,'경기도 의정부시'),(85,'경기도 안양시'),(86,'경기도 만안구'),(87,'경기도 동안구'),(88,'경기도 부천시'),(89,'경기도 광명시'),(90,'경기도 평택시'),(91,'경기도 동두천시'),(92,'경기도 안산시'),(93,'경기도 상록구'),(94,'경기도 단원구'),(95,'경기도 고양시'),(96,'경기도 덕양구'),(97,'경기도 일산동구'),(98,'경기도 일산서구'),(99,'경기도 과천시'),(100,'경기도 구리시'),(101,'경기도 남양주시'),(102,'경기도 오산시'),(103,'경기도 시흥시'),(104,'경기도 군포시'),(105,'경기도 의왕시'),(106,'경기도 하남시'),(107,'경기도 용인시'),(108,'경기도 처인구'),(109,'경기도 기흥구'),(110,'경기도 수지구'),(111,'경기도 파주시'),(112,'경기도 이천시'),(113,'경기도 안성시'),(114,'경기도 김포시'),(115,'경기도 화성시'),(116,'경기도 광주시'),(117,'경기도 양주시'),(118,'경기도 포천시'),(119,'경기도 여주시'),(120,'경기도 연천군'),(121,'경기도 가평군'),(122,'경기도 양평군'),(123,'강원도 춘천시'),(124,'강원도 원주시'),(125,'강원도 강릉시'),(126,'강원도 동해시'),(127,'강원도 태백시'),(128,'강원도 속초시'),(129,'강원도 삼척시'),(130,'강원도 홍천군'),(131,'강원도 횡성군'),(132,'강원도 영월군'),(133,'강원도 평창군'),(134,'강원도 정선군'),(135,'강원도 철원군'),(136,'강원도 화천군'),(137,'강원도 양구군'),(138,'강원도 인제군'),(139,'강원도 고성군'),(140,'강원도 양양군'),(141,'충청북도 충주시'),(142,'충청북도 제천시'),(143,'충청북도 청주시'),(144,'충청북도 상당구'),(145,'충청북도 서원구'),(146,'충청북도 흥덕구'),(147,'충청북도 청원구'),(148,'충청북도 보은군'),(149,'충청북도 옥천군'),(150,'충청북도 영동군'),(151,'충청북도 진천군'),(152,'충청북도 괴산군'),(153,'충청북도 음성군'),(154,'충청북도 단양군'),(155,'충청북도 증평군'),(156,'충청남도 천안시'),(157,'충청남도 동남구'),(158,'충청남도 서북구'),(159,'충청남도 공주시'),(160,'충청남도 보령시'),(161,'충청남도 아산시'),(162,'충청남도 서산시'),(163,'충청남도 논산시'),(164,'충청남도 계룡시'),(165,'충청남도 당진시'),(166,'충청남도 금산군'),(167,'충청남도 부여군'),(168,'충청남도 서천군'),(169,'충청남도 청양군'),(170,'충청남도 홍성군'),(171,'충청남도 예산군'),(172,'충청남도 태안군'),(173,'전라북도 전주시'),(174,'전라북도 완산구'),(175,'전라북도 덕진구'),(176,'전라북도 군산시'),(177,'전라북도 익산시'),(178,'전라북도 정읍시'),(179,'전라북도 남원시'),(180,'전라북도 김제시'),(181,'전라북도 완주군'),(182,'전라북도 진안군'),(183,'전라북도 무주군'),(184,'전라북도 장수군'),(185,'전라북도 임실군'),(186,'전라북도 순창군'),(187,'전라북도 고창군'),(188,'전라북도 부안군'),(189,'전라남도 목포시'),(190,'전라남도 여수시'),(191,'전라남도 순천시'),(192,'전라남도 나주시'),(193,'전라남도 광양시'),(194,'전라남도 담양군'),(195,'전라남도 곡성군'),(196,'전라남도 구례군'),(197,'전라남도 고흥군'),(198,'전라남도 보성군'),(199,'전라남도 화순군'),(200,'전라남도 장흥군'),(201,'전라남도 강진군'),(202,'전라남도 해남군'),(203,'전라남도 영암군'),(204,'전라남도 무안군'),(205,'전라남도 함평군'),(206,'전라남도 영광군'),(207,'전라남도 장성군'),(208,'전라남도 완도군'),(209,'전라남도 진도군'),(210,'전라남도 신안군'),(211,'경상북도 포항시'),(212,'경상북도 남구'),(213,'경상북도 북구'),(214,'경상북도 경주시'),(215,'경상북도 김천시'),(216,'경상북도 안동시'),(217,'경상북도 구미시'),(218,'경상북도 영주시'),(219,'경상북도 영천시'),(220,'경상북도 상주시'),(221,'경상북도 문경시'),(222,'경상북도 경산시'),(223,'경상북도 군위군'),(224,'경상북도 의성군'),(225,'경상북도 청송군'),(226,'경상북도 영양군'),(227,'경상북도 영덕군'),(228,'경상북도 청도군'),(229,'경상북도 고령군'),(230,'경상북도 성주군'),(231,'경상북도 칠곡군'),(232,'경상북도 예천군'),(233,'경상북도 봉화군'),(234,'경상북도 울진군'),(235,'경상북도 울릉군'),(236,'경삼남도 진주시'),(237,'경상남도 통영시'),(238,'경상남도 사천시'),(239,'경상남도 김해시'),(240,'경상남도 밀양시'),(241,'경상남도 거제시'),(242,'경상남도 양산시'),(243,'경상남도 창원시'),(244,'경상남도 의창구'),(245,'경상남도 성산구'),(246,'경상남도 마산합포구'),(247,'경상남도 마산회원구'),(248,'경상남도 진해구'),(249,'경상남도 의령군'),(250,'경상남도 함안군'),(251,'경상남도 창녕군'),(252,'경상남도 고성군'),(253,'경상남도 하동군'),(254,'경상남도 산청군'),(255,'경상남도 함양군'),(256,'경상남도 거창군'),(257,'경상남도 합천군'),(258,'제주특별자치도 제주시'),(259,'제주특별자치도 서귀포시'),(260,'서울특별시 종로구');
/*!40000 ALTER TABLE `tb_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_badge`
--

DROP TABLE IF EXISTS `tb_badge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_badge` (
  `badge_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'AI',
  `badge_name` varchar(100) NOT NULL,
  `badge_image` varchar(1000) NOT NULL,
  `badge_condition` varchar(1000) DEFAULT NULL COMMENT '수정가능',
  PRIMARY KEY (`badge_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_comment`
--

DROP TABLE IF EXISTS `tb_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_comment` (
  `comment_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'AI',
  `host_id` int(10) unsigned NOT NULL COMMENT 'AI',
  `feed_id` int(10) unsigned NOT NULL,
  `comment_content` varchar(1000) NOT NULL,
  `comment_date` int(11) NOT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `FK_tb_user_TO_tb_comment_1` (`host_id`),
  KEY `FK_tb_feed_TO_tb_comment_1_idx` (`feed_id`),
  CONSTRAINT `FK_tb_feed_TO_tb_comment_1` FOREIGN KEY (`feed_id`) REFERENCES `tb_feed` (`feed_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_tb_user_TO_tb_comment_1` FOREIGN KEY (`host_id`) REFERENCES `tb_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_coordinate`
--

DROP TABLE IF EXISTS `tb_coordinate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_coordinate` (
  `coordinate_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'AI',
  `record_id` int(10) unsigned NOT NULL COMMENT 'AI',
  `coordinate_latitude` text NOT NULL,
  `coordinate_longitude` text NOT NULL,
  PRIMARY KEY (`coordinate_id`),
  KEY `FK_tb_record_TO_tb_coordinate_1` (`record_id`),
  CONSTRAINT `FK_tb_record_TO_tb_coordinate_1` FOREIGN KEY (`record_id`) REFERENCES `tb_record` (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_crew`
--

DROP TABLE IF EXISTS `tb_crew`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_crew` (
  `crew_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'AI',
  `host_id` int(10) unsigned NOT NULL COMMENT 'AI',
  `address_id` int(10) unsigned NOT NULL,
  `crew_name` varchar(20) NOT NULL,
  `crew_desc` varchar(300) DEFAULT NULL,
  `crew_notice` varchar(300) DEFAULT NULL,
  `crew_image` varchar(1000) DEFAULT NULL,
  `crew_join` char(1) NOT NULL DEFAULT 'F' COMMENT 'L승인필요 F바로가입',
  PRIMARY KEY (`crew_id`),
  KEY `FK_tb_user_TO_tb_crew_1` (`host_id`),
  KEY `FK_tb_address_TO_tb_crew_1_idx` (`address_id`),
  CONSTRAINT `FK_tb_address_TO_tb_crew_1` FOREIGN KEY (`address_id`) REFERENCES `tb_address` (`address_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_tb_user_TO_tb_crew_1` FOREIGN KEY (`host_id`) REFERENCES `tb_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tb_crew_tag`
--

DROP TABLE IF EXISTS `tb_crew_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_crew_tag` (
  `tag_id` int(10) unsigned NOT NULL COMMENT 'AI',
  `crew_id` int(10) unsigned NOT NULL COMMENT 'AI',
  PRIMARY KEY (`tag_id`,`crew_id`),
  KEY `FK_tb_crew_TO_tb_crew_tag_1` (`crew_id`),
  CONSTRAINT `FK_tb_crew_TO_tb_crew_tag_1` FOREIGN KEY (`crew_id`) REFERENCES `tb_crew` (`crew_id`),
  CONSTRAINT `FK_tb_tag_TO_tb_crew_tag_1` FOREIGN KEY (`tag_id`) REFERENCES `tb_tag` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_crew_waiting`
--

DROP TABLE IF EXISTS `tb_crew_waiting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_crew_waiting` (
  `crew_id` int(10) unsigned NOT NULL COMMENT 'AI',
  `user_id` int(10) unsigned NOT NULL COMMENT 'AI',
  PRIMARY KEY (`crew_id`,`user_id`),
  KEY `FK_tb_user_TO_tb_crew_waiting_1` (`user_id`),
  CONSTRAINT `FK_tb_crew_TO_tb_crew_waiting_1` FOREIGN KEY (`crew_id`) REFERENCES `tb_crew` (`crew_id`),
  CONSTRAINT `FK_tb_user_TO_tb_crew_waiting_1` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_day`
--

DROP TABLE IF EXISTS `tb_day`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_day` (
  `day_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL COMMENT 'AI',
  `day_date` int(11) NOT NULL,
  `day_distance` float(9,2) NOT NULL DEFAULT '0.00',
  `day_time` int(10) unsigned NOT NULL DEFAULT '0',
  `day_pace` int(10) unsigned NOT NULL DEFAULT '0',
  `day_num` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `day_heart` mediumint(8) unsigned DEFAULT '0',
  `day_heart_num` tinyint(3) unsigned DEFAULT '0',
  PRIMARY KEY (`day_id`),
  KEY `FK_tb_user_TO_tb_day_1` (`user_id`),
  CONSTRAINT `FK_tb_user_TO_tb_day_1` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_event`
--

DROP TABLE IF EXISTS `tb_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_event` (
  `event_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'AI',
  `user_id` int(10) unsigned NOT NULL COMMENT 'AI',
  `event_title` varchar(1000) NOT NULL,
  `event_content` varchar(2000) NOT NULL,
  `event_image` varchar(1000) DEFAULT NULL COMMENT '이벤트 포스터, 홍보물',
  `event_date` int(11) NOT NULL COMMENT 'unix timestamp',
  PRIMARY KEY (`event_id`),
  KEY `FK_tb_user_TO_tb_event_1` (`user_id`),
  CONSTRAINT `FK_tb_user_TO_tb_event_1` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_event`
--

-- LOCK TABLES `tb_event` WRITE;
-- /*!40000 ALTER TABLE `tb_event` DISABLE KEYS */;
-- INSERT INTO `tb_event` VALUES (1,1,'봄맞이 플러깅','봄맞이 벚꽃 플러깅! 달리고, 줍고, 분리하자!','https://darly-bucket.s3.ap-northeast-2.amazonaws.com/event/b44ebad5-6978-4204-8bc4-83cd6522a3b0.png',1652766802),(2,1,'가족의달 도시락 배달','가족의 달 5월, 달리와 함께 도시락을 배달하세요!','https://darly-bucket.s3.ap-northeast-2.amazonaws.com/event/%EC%9D%B4%EB%B2%A4%ED%8A%B82.png',1652766827),(3,1,'유기견 산책 챌린지','새로운 달리를 만나보세요! 유기견 산책 챌린지','https://darly-bucket.s3.ap-northeast-2.amazonaws.com/event/%EC%9D%B4%EB%B2%A4%ED%8A%B83.png',1652766848),(4,1,'벽화 그리기','달리만의 Color Run, 벽화 그리기','https://darly-bucket.s3.ap-northeast-2.amazonaws.com/event/%EC%9D%B4%EB%B2%A4%ED%8A%B84.png',1652766868);
-- /*!40000 ALTER TABLE `tb_event` ENABLE KEYS */;
-- UNLOCK TABLES;

--
-- Table structure for table `tb_feed`
--

DROP TABLE IF EXISTS `tb_feed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_feed` (
  `feed_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'AI',
  `host_id` int(10) unsigned NOT NULL COMMENT 'AI',
  `crew_id` int(10) unsigned NOT NULL,
  `feed_title` varchar(100) DEFAULT NULL,
  `feed_content` varchar(2000) NOT NULL,
  `feed_like` int(11) DEFAULT '0',
  `feed_date` int(11) NOT NULL COMMENT 'unix timestamp',
  PRIMARY KEY (`feed_id`),
  KEY `FK_tb_user_TO_tb_feed_1` (`host_id`),
  KEY `FK_tb_crew_TO_tb_feed_1_idx` (`crew_id`),
  CONSTRAINT `FK_tb_crew_TO_tb_feed_1` FOREIGN KEY (`crew_id`) REFERENCES `tb_crew` (`crew_id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_tb_user_TO_tb_feed_1` FOREIGN KEY (`host_id`) REFERENCES `tb_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_feed_image`
--

DROP TABLE IF EXISTS `tb_feed_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_feed_image` (
  `feed_image_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'AI',
  `feed_id` int(10) unsigned NOT NULL COMMENT 'AI',
  `feed_image` varchar(1000) NOT NULL,
  PRIMARY KEY (`feed_image_id`),
  KEY `FK_tb_feed_TO_tb_feed_image_1` (`feed_id`),
  CONSTRAINT `FK_tb_feed_TO_tb_feed_image_1` FOREIGN KEY (`feed_id`) REFERENCES `tb_feed` (`feed_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_feed_like`
--

DROP TABLE IF EXISTS `tb_feed_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_feed_like` (
  `user_id` int(10) unsigned NOT NULL COMMENT 'AI',
  `feed_id` int(10) unsigned NOT NULL COMMENT 'AI',
  PRIMARY KEY (`user_id`,`feed_id`),
  KEY `FK_tb_feed_TO_tb_feed_like_1` (`feed_id`),
  CONSTRAINT `FK_tb_feed_TO_tb_feed_like_1` FOREIGN KEY (`feed_id`) REFERENCES `tb_feed` (`feed_id`),
  CONSTRAINT `FK_tb_user_TO_tb_feed_like_1` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_friend`
--

DROP TABLE IF EXISTS `tb_friend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_friend` (
  `friend_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'AI',
  `friend_one` int(10) unsigned NOT NULL COMMENT 'AI',
  `friend_two` int(10) unsigned NOT NULL COMMENT 'AI',
  PRIMARY KEY (`friend_id`),
  KEY `FK_tb_user_TO_tb_friend_1` (`friend_one`),
  KEY `FK_tb_user_TO_tb_friend_2` (`friend_two`),
  CONSTRAINT `FK_tb_user_TO_tb_friend_1` FOREIGN KEY (`friend_one`) REFERENCES `tb_user` (`user_id`),
  CONSTRAINT `FK_tb_user_TO_tb_friend_2` FOREIGN KEY (`friend_two`) REFERENCES `tb_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_friend_waiting`
--

DROP TABLE IF EXISTS `tb_friend_waiting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_friend_waiting` (
  `friend_waiting_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'AI',
  `friend_one` int(10) unsigned NOT NULL COMMENT 'AI',
  `friend_two` int(10) unsigned NOT NULL COMMENT 'AI',
  PRIMARY KEY (`friend_waiting_id`),
  KEY `FK_tb_user_TO_tb_friend_waiting_1` (`friend_one`),
  KEY `FK_tb_user_TO_tb_friend_waiting_2` (`friend_two`),
  CONSTRAINT `FK_tb_user_TO_tb_friend_waiting_1` FOREIGN KEY (`friend_one`) REFERENCES `tb_user` (`user_id`),
  CONSTRAINT `FK_tb_user_TO_tb_friend_waiting_2` FOREIGN KEY (`friend_two`) REFERENCES `tb_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_match`
--

DROP TABLE IF EXISTS `tb_match`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_match` (
  `match_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'AI',
  `crew_id` int(10) unsigned DEFAULT NULL COMMENT 'AI',
  `host_id` int(10) unsigned NOT NULL COMMENT 'AI',
  `match_title` varchar(100) DEFAULT NULL,
  `match_max_person` tinyint(4) NOT NULL DEFAULT '6' COMMENT '2~6',
  `match_cur_person` tinyint(4) NOT NULL DEFAULT '1',
  `match_goal_distance` float(4,2) NOT NULL DEFAULT '5.00',
  `match_date` int(11) NOT NULL COMMENT 'unix timestamp',
  `match_start_time` int(11) DEFAULT NULL COMMENT 'unix timestamp',
  `match_status` char(1) NOT NULL DEFAULT 'W' COMMENT '대기W 시작S 종료E',
  PRIMARY KEY (`match_id`),
  KEY `FK_tb_crew_TO_tb_match_1` (`crew_id`),
  KEY `FK_tb_user_TO_tb_match_1` (`host_id`),
  CONSTRAINT `FK_tb_crew_TO_tb_match_1` FOREIGN KEY (`crew_id`) REFERENCES `tb_crew` (`crew_id`),
  CONSTRAINT `FK_tb_user_TO_tb_match_1` FOREIGN KEY (`host_id`) REFERENCES `tb_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_match_result`
--

DROP TABLE IF EXISTS `tb_match_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_match_result` (
  `match_result_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'AI',
  `match_id` int(10) unsigned NOT NULL COMMENT 'AI',
  `user_id` int(10) unsigned NOT NULL COMMENT 'AI',
  `match_result_rank` tinyint(4) NOT NULL,
  `match_result_time` int(10) unsigned DEFAULT NULL,
  `match_result_pace` float(5,2) DEFAULT '0.00',
  PRIMARY KEY (`match_result_id`),
  KEY `FK_tb_match_TO_tb_match_result_1` (`match_id`),
  KEY `FK_tb_user_TO_tb_match_result_1` (`user_id`),
  CONSTRAINT `FK_tb_match_TO_tb_match_result_1` FOREIGN KEY (`match_id`) REFERENCES `tb_match` (`match_id`),
  CONSTRAINT `FK_tb_user_TO_tb_match_result_1` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_record`
--

DROP TABLE IF EXISTS `tb_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_record` (
  `record_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'AI',
  `user_id` int(10) unsigned NOT NULL COMMENT 'AI',
  `day_id` int(10) unsigned NOT NULL,
  `match_id` int(10) unsigned DEFAULT NULL COMMENT 'AI',
  `record_title` varchar(100) NOT NULL,
  `record_date` int(11) NOT NULL COMMENT 'unix timestamp',
  `record_distance` float(6,2) NOT NULL DEFAULT '0.00' COMMENT 'km',
  `record_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT 'sec',
  `record_pace` int(11) NOT NULL DEFAULT '0',
  `record_speed` float(5,2) NOT NULL DEFAULT '0.00',
  `record_heart` tinyint(3) unsigned DEFAULT NULL,
  `record_calories` smallint(5) unsigned NOT NULL DEFAULT '0',
  `record_rank` tinyint(4) DEFAULT NULL,
  `record_image` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`record_id`),
  KEY `FK_tb_user_TO_tb_record_1` (`user_id`),
  KEY `FK_tb_day_TO_tb_record_1` (`day_id`),
  KEY `FK_tb_match_TO_tb_record_1` (`match_id`),
  CONSTRAINT `FK_tb_day_TO_tb_record_1` FOREIGN KEY (`day_id`) REFERENCES `tb_day` (`day_id`),
  CONSTRAINT `FK_tb_match_TO_tb_record_1` FOREIGN KEY (`match_id`) REFERENCES `tb_match` (`match_id`),
  CONSTRAINT `FK_tb_user_TO_tb_record_1` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_section`
--

DROP TABLE IF EXISTS `tb_section`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_section` (
  `section_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'AI',
  `record_id` int(10) unsigned NOT NULL COMMENT 'AI',
  `section_km` float(5,2) NOT NULL DEFAULT '1.00',
  `section_pace` int(10) unsigned NOT NULL DEFAULT '0',
  `section_calories` smallint(5) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`section_id`),
  KEY `FK_tb_record_TO_tb_section_1` (`record_id`),
  CONSTRAINT `FK_tb_record_TO_tb_section_1` FOREIGN KEY (`record_id`) REFERENCES `tb_record` (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_tag`
--

DROP TABLE IF EXISTS `tb_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_tag` (
  `tag_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'AI',
  `tag_name` varchar(100) NOT NULL,
  PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_user`
--

DROP TABLE IF EXISTS `tb_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_user` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'AI',
  `user_nickname` varchar(45) NOT NULL,
  `user_email` varchar(320) NOT NULL,
  `user_total_distance` float(9,2) NOT NULL DEFAULT '0.00',
  `user_total_time` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT 'sec',
  `user_total_heart` int(10) unsigned DEFAULT NULL,
  `user_total_calories` int(10) unsigned NOT NULL DEFAULT '0',
  `user_total_pace` float(9,2) NOT NULL DEFAULT '0.00',
  `user_heart_num` mediumint(8) unsigned DEFAULT NULL,
  `user_min_pace` float(5,2) DEFAULT NULL,
  `user_goal_distance` float(4,2) NOT NULL DEFAULT '5.00' COMMENT 'km',
  `user_goal_time` smallint(5) unsigned NOT NULL DEFAULT '25' COMMENT 'min',
  `user_point` int(11) NOT NULL DEFAULT '0',
  `user_image` varchar(1000) DEFAULT NULL,
  `user_message` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_user_address`
--

DROP TABLE IF EXISTS `tb_user_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_user_address` (
  `user_id` int(10) unsigned NOT NULL,
  `address_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`user_id`,`address_id`),
  KEY `FK_tb_address_TO_tb_user_address_1` (`address_id`),
  CONSTRAINT `FK_tb_address_TO_tb_user_address_1` FOREIGN KEY (`address_id`) REFERENCES `tb_address` (`address_id`),
  CONSTRAINT `FK_tb_user_TO_tb_user_address_1` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_user_badge`
--

DROP TABLE IF EXISTS `tb_user_badge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_user_badge` (
  `badge_id` int(10) unsigned NOT NULL COMMENT 'AI',
  `user_id` int(10) unsigned NOT NULL COMMENT 'AI',
  PRIMARY KEY (`badge_id`,`user_id`),
  KEY `FK_tb_user_TO_tb_user_badge_1` (`user_id`),
  CONSTRAINT `FK_tb_badge_TO_tb_user_badge_1` FOREIGN KEY (`badge_id`) REFERENCES `tb_badge` (`badge_id`),
  CONSTRAINT `FK_tb_user_TO_tb_user_badge_1` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_user_crew`
--

DROP TABLE IF EXISTS `tb_user_crew`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_user_crew` (
  `crew_id` int(10) unsigned NOT NULL COMMENT 'AI',
  `user_id` int(10) unsigned NOT NULL COMMENT 'AI',
  PRIMARY KEY (`crew_id`,`user_id`),
  KEY `FK_tb_user_TO_tb_user_crew_1` (`user_id`),
  CONSTRAINT `FK_tb_crew_TO_tb_user_crew_1` FOREIGN KEY (`crew_id`) REFERENCES `tb_crew` (`crew_id`),
  CONSTRAINT `FK_tb_user_TO_tb_user_crew_1` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_user_event`
--

DROP TABLE IF EXISTS `tb_user_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_user_event` (
  `user_id` int(10) unsigned NOT NULL,
  `event_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`user_id`,`event_id`),
  KEY `FK_tb_event_TO_tb_user_event_1` (`event_id`),
  CONSTRAINT `FK_tb_event_TO_tb_user_event_1` FOREIGN KEY (`event_id`) REFERENCES `tb_event` (`event_id`),
  CONSTRAINT `FK_tb_user_TO_tb_user_event_1` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_user_feed`
--

DROP TABLE IF EXISTS `tb_user_feed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_user_feed` (
  `user_feed_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL COMMENT 'AI',
  `user_feed_image` varchar(1000) NOT NULL,
  PRIMARY KEY (`user_feed_id`),
  KEY `FK_tb_user_TO_tb_user_feed_1` (`user_id`),
  CONSTRAINT `FK_tb_user_TO_tb_user_feed_1` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_user_match`
--

DROP TABLE IF EXISTS `tb_user_match`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_user_match` (
  `match_id` int(10) unsigned NOT NULL COMMENT 'AI',
  `user_id` int(10) unsigned NOT NULL COMMENT 'AI',
  `user_match_status` char(1) NOT NULL DEFAULT 'N' COMMENT '레디R 레디안함N',
  PRIMARY KEY (`match_id`,`user_id`),
  KEY `FK_tb_user_TO_tb_user_match_1` (`user_id`),
  CONSTRAINT `FK_tb_match_TO_tb_user_match_1` FOREIGN KEY (`match_id`) REFERENCES `tb_match` (`match_id`),
  CONSTRAINT `FK_tb_user_TO_tb_user_match_1` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-19 13:59:09
