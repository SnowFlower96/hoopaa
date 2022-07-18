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
-- Table structure for table `hashtag`
--

DROP TABLE IF EXISTS `hashtag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hashtag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nm` varchar(5) COLLATE utf8mb4_bin NOT NULL,
  `cnt` int(11) NOT NULL DEFAULT '1',
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
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nm` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL,
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
-- Table structure for table `room_description`
--

DROP TABLE IF EXISTS `room_description`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_description` (
  `id` int(11) NOT NULL,
  `cate` int(11) DEFAULT NULL,
  `hash_1` int(11) DEFAULT NULL,
  `hash_2` int(11) DEFAULT NULL,
  `hash_3` int(11) DEFAULT NULL,
  `title` varchar(20) COLLATE utf8mb4_bin NOT NULL,
  `subtitle` varchar(20) COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cate` (`cate`),
  KEY `hash_1` (`hash_1`),
  KEY `hash_2` (`hash_2`),
  KEY `hash_3` (`hash_3`),
  CONSTRAINT `room_description_ibfk_1` FOREIGN KEY (`id`) REFERENCES `room_info` (`id`) ON DELETE CASCADE,
  CONSTRAINT `room_description_ibfk_2` FOREIGN KEY (`cate`) REFERENCES `room_category` (`id`) ON DELETE SET NULL,
  CONSTRAINT `room_description_ibfk_3` FOREIGN KEY (`hash_1`) REFERENCES `hashtag` (`id`) ON DELETE SET NULL,
  CONSTRAINT `room_description_ibfk_4` FOREIGN KEY (`hash_2`) REFERENCES `hashtag` (`id`) ON DELETE SET NULL,
  CONSTRAINT `room_description_ibfk_5` FOREIGN KEY (`hash_3`) REFERENCES `hashtag` (`id`) ON DELETE SET NULL
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
-- Table structure for table `room_history`
--

DROP TABLE IF EXISTS `room_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_history` (
  `id` int(11) NOT NULL,
  `log` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `end` datetime DEFAULT NULL,
  `winner` int(11) DEFAULT NULL,
  `agree` int(11) DEFAULT '0',
  `disagree` int(11) DEFAULT '0',
  `invalid` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  CONSTRAINT `room_history_ibfk_1` FOREIGN KEY (`id`) REFERENCES `room_info` (`id`) ON DELETE CASCADE
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
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pwd` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `host_id` int(11) DEFAULT NULL,
  `is_sys` tinyint(1) DEFAULT '0',
  `thumb_url` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `phase` int(11) DEFAULT '0',
  `max` int(11) DEFAULT '1',
  `cur` int(11) DEFAULT '1',
  `start` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `host_id` (`host_id`),
  CONSTRAINT `room_info_ibfk_1` FOREIGN KEY (`host_id`) REFERENCES `user` (`id`) ON DELETE SET NULL
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
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `em` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `pwd` varchar(200) COLLATE utf8mb4_bin NOT NULL,
  `nnm` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `level` int(11) NOT NULL DEFAULT '0',
  `em_auth` tinyint(1) NOT NULL DEFAULT '0',
  `join_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `refresh_tk` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `access_tk` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `modify_dt` datetime DEFAULT NULL,
  `leave_dt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `em` (`em`,`nnm`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'ssafy@ssafy.com','$2a$10$8RqbYW7J.cK.vn7K1jzW3uMS.6WlVoDDU0ZN/50aIHDUOmdB3q6TC','your_nickname',0,0,'2022-07-18 17:19:15',NULL,NULL,NULL,NULL),(3,'ssafy@ssafy1.com','$2a$10$XSFCx3/FBagsiJhnJcGy5eIaQ1woxwGeO87IrHGo3P7NlRJr496pa','your_nickname1',0,0,'2022-07-18 17:21:30',NULL,NULL,NULL,NULL),(5,'ssafy@ssafy2.com','$2a$10$Fi/vIjQl0FGO/zgspJREleMQWLaP0mSWGXX60wCUpL4ZqtO19zod6','your_nickname2',0,0,'2022-07-18 17:28:42',NULL,NULL,NULL,NULL),(7,'ssafy@ssafy3.com','$2a$10$3OwxGptOZBOynflFGDrhBea/hs4Y8WE9kypKA7gQdMwtAQeiZn8WW','your_nickname3',0,0,'2022-07-18 17:30:47',NULL,NULL,NULL,NULL),(8,'ssafy@ssafy4.com','$2a$10$58bq24P1bULsBUo52yJPued4cM9PjJccyiertlDiDxMapmbe//3n6','your_nickname4',0,0,'2022-07-18 17:30:55',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_history`
--

DROP TABLE IF EXISTS `user_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `room_id` int(11) DEFAULT NULL,
  `role` int(11) NOT NULL,
  `is_king` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `room_id` (`room_id`),
  CONSTRAINT `user_history_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `user_history_ibfk_2` FOREIGN KEY (`room_id`) REFERENCES `room_info` (`id`) ON DELETE CASCADE
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
  `id` int(11) NOT NULL,
  `exp` int(11) DEFAULT '0',
  `total` int(11) DEFAULT '0',
  `win` int(11) DEFAULT '0',
  `lose` int(11) DEFAULT '0',
  `draw` int(11) DEFAULT '0',
  `penalty` int(11) DEFAULT '0',
  `king` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  CONSTRAINT `user_stat_ibfk_1` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
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

-- Dump completed on 2022-07-18 17:55:46
