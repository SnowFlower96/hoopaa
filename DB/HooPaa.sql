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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_hashtag`
--

LOCK TABLES `room_hashtag` WRITE;
/*!40000 ALTER TABLE `room_hashtag` DISABLE KEYS */;
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
  `diagree` tinyint(3) unsigned DEFAULT NULL,
  `invalid` tinyint(3) unsigned DEFAULT NULL,
  `disagree` smallint(6) DEFAULT NULL,
  `king_id` mediumint(8) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `room_info_host_idx` (`host_id`),
  KEY `room_info_king_idx` (`king_id`),
  CONSTRAINT `room_info_host` FOREIGN KEY (`host_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `room_info_king` FOREIGN KEY (`king_id`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_info`
--

LOCK TABLES `room_info` WRITE;
/*!40000 ALTER TABLE `room_info` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'test','$2a$10$S8ueNyKUhTZP1m2/hYuSIe2BEOl7dNPZH9QsDAyM0Yic5SGGJu6Ce','test',1,0,'2022-08-12 16:05:13',NULL,NULL,NULL),(2,'admin','$2a$10$3IRyfHMkjV/rR/LUvojf7.Z8iMFseV.W1nB21KoytyCpnVRAGqSMW','admin',1,0,'2022-08-12 16:05:13',NULL,NULL,NULL);
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
INSERT INTO `user_stat` VALUES (1,0,0,0,0,0,0,0),(2,0,0,0,0,0,0,0);
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

-- Dump completed on 2022-08-12 16:10:42
