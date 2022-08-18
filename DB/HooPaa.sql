DROP DATABASE IF EXISTS `debate`;
CREATE DATABASE  IF NOT EXISTS `debate` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin */;
USE `debate`;
-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: debate
-- ------------------------------------------------------
-- Server version	5.7.35-log

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

--
-- Table structure for table `room_category`
--

DROP TABLE IF EXISTS `room_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_category` (
  `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `nm` varchar(10) COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_category`
--

LOCK TABLES `room_category` WRITE;
/*!40000 ALTER TABLE `room_category` DISABLE KEYS */;
INSERT INTO `room_category` VALUES (1,'유머'),(2,'정치'),(3,'경제'),(4,'사회'),(5,'문화'),(6,'교육'),(7,'과학'),(8,'스포츠');
/*!40000 ALTER TABLE `room_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_description`
--

DROP TABLE IF EXISTS `room_description`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_description` (
  `id` int(10) unsigned NOT NULL,
  `title` varchar(20) COLLATE utf8mb4_bin NOT NULL,
  `subtitle` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `cate` tinyint(3) unsigned DEFAULT NULL,
  `hash1` mediumint(8) unsigned DEFAULT NULL,
  `hash2` mediumint(8) unsigned DEFAULT NULL,
  `hash3` mediumint(8) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `room_description.cate_idx` (`cate`),
  KEY `room_description.hash1_idx` (`hash1`),
  KEY `room_description.hash2_idx` (`hash2`),
  KEY `room_description.hash3_idx` (`hash3`),
  CONSTRAINT `room_description.cate` FOREIGN KEY (`cate`) REFERENCES `room_category` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `room_description.hash1` FOREIGN KEY (`hash1`) REFERENCES `room_hashtag` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `room_description.hash2` FOREIGN KEY (`hash2`) REFERENCES `room_hashtag` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `room_description.hash3` FOREIGN KEY (`hash3`) REFERENCES `room_hashtag` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `room_description_id` FOREIGN KEY (`id`) REFERENCES `room_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_description`
--

LOCK TABLES `room_description` WRITE;
/*!40000 ALTER TABLE `room_description` DISABLE KEYS */;
INSERT INTO `room_description` VALUES (1,'깻잎논쟁','요즘 핫한 깻잎논쟁',4,1,NULL,NULL),(2,'박카스vs핫식스','당신의 부스터는?',5,2,3,NULL),(3,'짜장 대 짬뽕','먹고싶다',3,4,5,6),(4,'바다 vs 산','여름 휴가 장소',5,7,2,8),(5,'Java vs Python','프로그래밍 입문 언어는?',1,9,10,11),(6,'자취 vs 기숙사','뭐가 좋을까',1,12,13,14),(7,'카페인 과다섭취 이대로 괜찮은가','현대인의 필수아이템',3,15,16,NULL),(8,'악법도 법이다','테스형',4,17,NULL,NULL),(9,'담배값 인상','얼마로?',5,NULL,NULL,NULL),(10,'심청이는 효녀인가','전래동화',4,18,19,20),(11,'버스 vs 지하철','뭐가 편한가',3,19,21,NULL),(12,'빈공간','널널',3,NULL,NULL,NULL);
/*!40000 ALTER TABLE `room_description` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_hashtag`
--

DROP TABLE IF EXISTS `room_hashtag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_hashtag` (
  `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `nm` varchar(5) COLLATE utf8mb4_bin DEFAULT NULL,
  `cnt` mediumint(8) unsigned DEFAULT '0',
  `recent` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_hashtag`
--

LOCK TABLES `room_hashtag` WRITE;
/*!40000 ALTER TABLE `room_hashtag` DISABLE KEYS */;
INSERT INTO `room_hashtag` VALUES (1,'깻잎논쟁',1,'2022-08-17 17:37:55'),(2,'핫이슈',2,'2022-08-17 17:37:55'),(3,'찬반',1,'2022-08-17 17:37:55'),(4,'후파',1,'2022-08-17 17:37:56'),(5,'나락',1,'2022-08-17 17:37:56'),(6,'해시',1,'2022-08-17 17:37:56'),(7,'스프링',1,'2022-08-17 17:37:56'),(8,'Vue',1,'2022-08-17 17:37:56'),(9,'9시',1,'2022-08-17 17:37:56'),(10,'뉴스',1,'2022-08-17 17:37:56'),(11,'우와',1,'2022-08-17 17:37:56'),(12,'뭘',1,'2022-08-17 17:37:56'),(13,'써야',1,'2022-08-17 17:37:56'),(14,'될까',1,'2022-08-17 17:37:56'),(15,'아이디어',1,'2022-08-17 17:37:56'),(16,'고갈',1,'2022-08-17 17:37:56'),(17,'살려줘',1,'2022-08-17 17:37:56'),(18,'핫식스',1,'2022-08-17 17:37:56'),(19,'카페인',2,'2022-08-17 17:37:56'),(20,'ㅁ나ㅣㅇ',1,'2022-08-17 17:37:56'),(21,'타우린',1,'2022-08-17 17:37:56');
/*!40000 ALTER TABLE `room_hashtag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_info`
--

DROP TABLE IF EXISTS `room_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `pwd` varchar(60) COLLATE utf8mb4_bin DEFAULT NULL,
  `host_id` mediumint(8) unsigned DEFAULT NULL,
  `thumb_url` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `max_num` tinyint(3) unsigned DEFAULT NULL,
  `cur_num` tinyint(3) unsigned DEFAULT NULL,
  `phase` tinyint(4) DEFAULT '0',
  `start_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `end_time` datetime DEFAULT NULL,
  `log` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `winner` tinyint(3) unsigned DEFAULT NULL,
  `agree` tinyint(3) unsigned DEFAULT NULL,
  `disagree` tinyint(3) unsigned DEFAULT NULL,
  `invalid` tinyint(3) unsigned DEFAULT NULL,
  `king_id` mediumint(8) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `room_info_host_idx` (`host_id`),
  KEY `room_info_king_idx` (`king_id`),
  CONSTRAINT `room_info_host` FOREIGN KEY (`host_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `room_info_king` FOREIGN KEY (`king_id`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_info`
--

LOCK TABLES `room_info` WRITE;
/*!40000 ALTER TABLE `room_info` DISABLE KEYS */;
INSERT INTO `room_info` VALUES (1,'',3,'1660725475903_3.jpg',5,43,2,'2022-08-17 17:37:55',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,'',4,'1660725475977_4.jpg',2,46,0,'2022-08-17 17:37:55',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(3,'1234',5,'1660725476062_5.jpg',5,39,0,'2022-08-17 17:37:56',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(4,'',6,'1660725476139_6.jpg',3,34,0,'2022-08-17 17:37:56',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(5,'',7,'1660725476195_7.jpg',3,8,1,'2022-08-17 17:37:56',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(6,'',8,'1660725476253_8.jpg',5,26,2,'2022-08-17 17:37:56',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(7,'',9,'1660725476306_9.jpg',1,44,0,'2022-08-17 17:37:56',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(8,'',10,'1660725476348_10.jpg',3,19,1,'2022-08-17 17:37:56',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(9,'1234',11,'1660725476384_11.jpg',1,47,2,'2022-08-17 17:37:56',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(10,'',12,'1660725476436_12.jpg',2,24,0,'2022-08-17 17:37:56',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(11,'',13,'1660725476487_13.jpg',3,11,2,'2022-08-17 17:37:56',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(12,'',14,'1660725476537_14.jpg',4,46,1,'2022-08-17 17:37:56',NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `room_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_panel`
--

DROP TABLE IF EXISTS `room_panel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_panel` (
  `id` int(11) unsigned NOT NULL,
  `agree1` mediumint(9) unsigned DEFAULT NULL,
  `agree2` mediumint(9) unsigned DEFAULT NULL,
  `agree3` mediumint(9) unsigned DEFAULT NULL,
  `agree4` mediumint(9) unsigned DEFAULT NULL,
  `agree5` mediumint(9) unsigned DEFAULT NULL,
  `disagree1` mediumint(9) unsigned DEFAULT NULL,
  `disagree2` mediumint(9) unsigned DEFAULT NULL,
  `disagree3` mediumint(9) unsigned DEFAULT NULL,
  `disagree4` mediumint(9) unsigned DEFAULT NULL,
  `disagree5` mediumint(9) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `room_status_agree1_idx` (`agree1`),
  KEY `room_status_agree2_idx` (`agree2`),
  KEY `room_status_agree3_idx` (`agree3`),
  KEY `room_status_agree4_idx` (`agree4`),
  KEY `room_status_agree5_idx` (`agree5`),
  KEY `room_status_disagree1_idx` (`disagree1`),
  KEY `room_status_disagree2_idx` (`disagree2`),
  KEY `room_status_disagree3_idx` (`disagree3`),
  KEY `room_status_disagree4_idx` (`disagree4`),
  KEY `room_status_disagree5_idx` (`disagree5`),
  CONSTRAINT `room_status_agree1` FOREIGN KEY (`agree1`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `room_status_agree2` FOREIGN KEY (`agree2`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `room_status_agree3` FOREIGN KEY (`agree3`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `room_status_agree4` FOREIGN KEY (`agree4`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `room_status_agree5` FOREIGN KEY (`agree5`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `room_status_disagree1` FOREIGN KEY (`disagree1`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `room_status_disagree2` FOREIGN KEY (`disagree2`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `room_status_disagree3` FOREIGN KEY (`disagree3`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `room_status_disagree4` FOREIGN KEY (`disagree4`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `room_status_disagree5` FOREIGN KEY (`disagree5`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `room_status_id` FOREIGN KEY (`id`) REFERENCES `room_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_panel`
--

LOCK TABLES `room_panel` WRITE;
/*!40000 ALTER TABLE `room_panel` DISABLE KEYS */;
INSERT INTO `room_panel` VALUES (1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(4,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(5,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(8,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(9,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(11,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(12,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `room_panel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `em` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `pwd` varchar(60) COLLATE utf8mb4_bin NOT NULL,
  `nnm` varchar(10) COLLATE utf8mb4_bin NOT NULL,
  `em_auth` tinyint(4) NOT NULL DEFAULT '0',
  `level` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `join_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_dt` datetime DEFAULT NULL,
  `leave_dt` datetime DEFAULT NULL,
  `refresh_tk` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `em_UNIQUE` (`em`),
  UNIQUE KEY `nnm_UNIQUE` (`nnm`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'test','$2a$10$H7wPn/iBvsYh2.NhB9bio.cbnazkGh/F2N3ptNC9Es9Wqfi8zkkvG','test',1,0,'2022-08-17 17:37:54',NULL,NULL,NULL),(2,'admin','$2a$10$lpVwODfWl8nOxNzOaemmb.dPgDJ6O47bMhJtFdNj4BFH4zJ327Ufe','admin',1,0,'2022-08-17 17:37:54',NULL,NULL,NULL),(3,'efasf12@gmail.com','$2a$10$1tLA9h0Ulmr5DJTDoU7F3.GFzpIPiuK2I4ZD9YKmrlh26GzrloANC','대전',1,0,'2022-08-17 17:37:54',NULL,NULL,NULL),(4,'zxawdd32@gmail.com','$2a$10$kc/6U4gRqplee82yYUcYKO.2LRqNv23KR0DdYqTRy9R8Pv5MzGcAS','3반',1,0,'2022-08-17 17:37:54',NULL,NULL,NULL),(5,'asdf@gmail.com','$2a$10$8YhzGYT0iTNyrwBD3z2GaOF0WQTGRKtq/1vIl4e.onDTMu4yxHKUy','공통',1,0,'2022-08-17 17:37:54',NULL,NULL,NULL),(6,'njkadn@gmail.com','$2a$10$oFfaMFq.qiv0xi6MEAn9IeVwSTypl05zhhRTmtuIDV3Ni3ZAx2.2O','2팀',1,0,'2022-08-17 17:37:54',NULL,NULL,NULL),(7,'qwdnj2@gmail.com','$2a$10$29EqN3V7nv6BbJAnaV.AWOB8dkVvK6kib.FpuO3Rd7WGvq4dla.pu','닉네임',1,0,'2022-08-17 17:37:54',NULL,NULL,NULL),(8,'njnk@gmail.com','$2a$10$2RsIRzGVI816Ka0KN581weSnIgk5PNWDdDIDGUD.V/BZkqAHrvD4q','비그만와',1,0,'2022-08-17 17:37:54',NULL,NULL,NULL),(9,'sdfa@naver.com','$2a$10$PD/cbZ16T07eEbGgTWzCYeNeq3cHe/hS./NQoZ1Oc1HTqwusyt5Oe','후파',1,0,'2022-08-17 17:37:55',NULL,NULL,NULL),(10,'anjkzxc@naver.com','$2a$10$8brn4yGLII3ftMGEr7E3J.Y2Bp/lMrbUTQdUgLzYkthCdEXwMVXdO','배고파',1,0,'2022-08-17 17:37:55',NULL,NULL,NULL),(11,'ajnwd123@naver.com','$2a$10$vwbkzckVVKEHkHNZIfKywuwuTc1aQh23OHHKvVmk9u9jlF04nrXye','커피',1,0,'2022-08-17 17:37:55',NULL,NULL,NULL),(12,'asdfm@kakao.com','$2a$10$g/guwgReRDbNyiSkYQ55GeW.KXw4khlu2T/Ig/Ek/hNtwiuJUvVR.','스프링',1,0,'2022-08-17 17:37:55',NULL,NULL,NULL),(13,'asnjz124@kakao.com','$2a$10$JMuFKgnRTBXmrpiabVC7Gez26DVl3ZuIaH5i24tpLTFAsZcD7uIS2','젠킨스',1,0,'2022-08-17 17:37:55',NULL,NULL,NULL),(14,'nkjxczv55@kakao.com','$2a$10$ZzxWyJLJIR35K9xLbtl9WeHs/I/wTY6GAUOaOBxr8gF/J9J1JLyX6','인간집사',1,0,'2022-08-17 17:37:55',NULL,NULL,NULL),(15,'8x15yz@gmail.com','$2a$10$pr.N2jjFgNzPkkQNQntdeO06FVB80cZZ42xNr4Kj7lrWcXTZCZM3G','Jay',1,0,'2022-08-17 17:37:55',NULL,NULL,NULL),(16,'jhno96@gmail.com','$2a$10$wN/0sd0dgpTa81mGT9EVWezruUjIrhkFceo.kyg.TXbMFRe2PJg1y','Back',1,0,'2022-08-17 17:37:55',NULL,NULL,NULL),(17,'wjdgus9596@naver.com','$2a$10$HOREeM8caSwRPnp6zaz16OtUs9CQLOO.jR0My2iWjU9mhpltvMY2u','정현',1,0,'2022-08-17 17:37:55',NULL,NULL,NULL),(18,'jhr991108@naver.com','$2a$10$K1AkXW6ELe/4yTjLcoGxuubNeAt70sIt3VgBvUgkoO3yx4qT9mxUG','혜령',1,0,'2022-08-17 17:37:55',NULL,NULL,NULL),(19,'wj5295@naver.com','$2a$10$Esxq.hEsJKftn72C2sYcs.nRgu7JMY/kLDtBOjqiq1jUw.SDaA0oy','원재',1,0,'2022-08-17 17:37:55',NULL,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_history`
--

DROP TABLE IF EXISTS `user_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_history` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` mediumint(8) unsigned NOT NULL,
  `room_id` int(10) unsigned NOT NULL,
  `is_host` tinyint(4) NOT NULL DEFAULT '0',
  `is_king` tinyint(4) NOT NULL DEFAULT '0',
  `user_pos` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_history_user_idx` (`user_id`),
  KEY `user_history_room_idx` (`room_id`),
  CONSTRAINT `FK3bud5uwyote3w1qkgstwsnqsd` FOREIGN KEY (`user_id`) REFERENCES `user_stat` (`id`),
  CONSTRAINT `user_history_room` FOREIGN KEY (`room_id`) REFERENCES `room_info` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `user_history_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_history`
--

LOCK TABLES `user_history` WRITE;
/*!40000 ALTER TABLE `user_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_stat`
--

DROP TABLE IF EXISTS `user_stat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_stat` (
  `id` mediumint(8) unsigned NOT NULL,
  `exp` int(10) unsigned NOT NULL DEFAULT '0',
  `total` smallint(5) unsigned NOT NULL DEFAULT '0',
  `win` smallint(5) unsigned NOT NULL DEFAULT '0',
  `lose` smallint(5) unsigned NOT NULL DEFAULT '0',
  `draw` smallint(5) unsigned NOT NULL DEFAULT '0',
  `penalty` smallint(5) unsigned NOT NULL DEFAULT '0',
  `king` smallint(5) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  CONSTRAINT `user_stat_id` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_stat`
--

LOCK TABLES `user_stat` WRITE;
/*!40000 ALTER TABLE `user_stat` DISABLE KEYS */;
INSERT INTO `user_stat` VALUES (1,0,0,0,0,0,0,0),(2,0,0,0,0,0,0,0),(3,0,0,0,0,0,0,0),(4,0,0,0,0,0,0,0),(5,0,0,0,0,0,0,0),(6,0,0,0,0,0,0,0),(7,0,0,0,0,0,0,0),(8,0,0,0,0,0,0,0),(9,0,0,0,0,0,0,0),(10,0,0,0,0,0,0,0),(11,0,0,0,0,0,0,0),(12,0,0,0,0,0,0,0),(13,0,0,0,0,0,0,0),(14,0,0,0,0,0,0,0),(15,0,0,0,0,0,0,0),(16,0,0,0,0,0,0,0),(17,0,0,0,0,0,0,0),(18,0,0,0,0,0,0,0),(19,0,0,0,0,0,0,0);
/*!40000 ALTER TABLE `user_stat` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-08-18 10:59:46
