CREATE DATABASE  IF NOT EXISTS `debate` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin */;
USE `debate`;
-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: debatee
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
-- Table structure for table `hashtag`
--

DROP TABLE IF EXISTS `hashtag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hashtag` (
  `id` mediumint(8) unsigned NOT NULL,
  `nm` varchar(5) COLLATE utf8mb4_bin DEFAULT NULL,
  `cnt` mediumint(8) unsigned DEFAULT '0',
  `recent` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hashtag`
--

LOCK TABLES `hashtag` WRITE;
/*!40000 ALTER TABLE `hashtag` DISABLE KEYS */;
/*!40000 ALTER TABLE `hashtag` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_category`
--

LOCK TABLES `room_category` WRITE;
/*!40000 ALTER TABLE `room_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `room_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_history`
--

DROP TABLE IF EXISTS `room_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_history` (
  `id` int(10) unsigned NOT NULL,
  `log` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `winner` tinyint(3) unsigned DEFAULT NULL,
  `agree` tinyint(3) unsigned DEFAULT NULL,
  `disagree` tinyint(3) unsigned DEFAULT NULL,
  `invalid` tinyint(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `room_history_id` FOREIGN KEY (`id`) REFERENCES `room_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_history`
--

LOCK TABLES `room_history` WRITE;
/*!40000 ALTER TABLE `room_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `room_history` ENABLE KEYS */;
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
  `is_sys` tinyint(4) DEFAULT '0',
  `thumb_url` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `phase` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `max_num` tinyint(3) unsigned NOT NULL DEFAULT '3',
  `cur_num` tinyint(3) unsigned NOT NULL DEFAULT '1',
  `start_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `cate` tinyint(3) unsigned DEFAULT NULL,
  `hash_1` mediumint(8) unsigned DEFAULT NULL,
  `hash_2` mediumint(8) unsigned DEFAULT NULL,
  `hash_3` mediumint(8) unsigned DEFAULT NULL,
  `title` varchar(20) COLLATE utf8mb4_bin NOT NULL,
  `subtitle` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `room_info_host_idx` (`host_id`),
  KEY `room_info_category_idx` (`cate`),
  KEY `room_info_hash_1_idx` (`hash_1`),
  KEY `room_info_hash_2_idx` (`hash_2`),
  KEY `room_info_hash_3_idx` (`hash_3`),
  CONSTRAINT `room_info_category` FOREIGN KEY (`cate`) REFERENCES `room_category` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `room_info_hash_1` FOREIGN KEY (`hash_1`) REFERENCES `hashtag` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `room_info_hash_2` FOREIGN KEY (`hash_2`) REFERENCES `hashtag` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `room_info_hash_3` FOREIGN KEY (`hash_3`) REFERENCES `hashtag` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `room_info_host` FOREIGN KEY (`host_id`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_history`
--

DROP TABLE IF EXISTS `user_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_history` (
  `id` int(10) unsigned NOT NULL,
  `user_id` mediumint(8) unsigned NOT NULL,
  `room_id` int(10) unsigned NOT NULL,
  `is_host` tinyint(4) NOT NULL DEFAULT '0',
  `is_king` tinyint(4) NOT NULL DEFAULT '0',
  `user_pos` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_history_user_idx` (`user_id`),
  KEY `user_history_room_idx` (`room_id`),
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
  `panelty` smallint(5) unsigned NOT NULL DEFAULT '0',
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

-- Dump completed on 2022-07-21 15:05:14
