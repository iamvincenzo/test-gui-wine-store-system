CREATE DATABASE  IF NOT EXISTS `winestore_dipalma_299636_fraello_299647` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `winestore_dipalma_299636_fraello_299647`;
-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: winestore_dipalma_299636_fraello_299647
-- ------------------------------------------------------
-- Server version	8.0.22

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
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `administrator` (
  `ID_A` int NOT NULL AUTO_INCREMENT,
  `Name_A` varchar(20) NOT NULL,
  `Surname_A` varchar(20) NOT NULL,
  `Email_A` varchar(30) NOT NULL,
  `Password_A` varchar(8) NOT NULL,
  PRIMARY KEY (`ID_A`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (1,'Giorgio','Verdi','giorgio.verdi@gmail.com','12345678'),(2,'a','b','a@b','12345678');
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `ID_C` int NOT NULL AUTO_INCREMENT,
  `Name_C` varchar(20) NOT NULL,
  `Surname_C` varchar(20) NOT NULL,
  `Email_C` varchar(30) NOT NULL,
  `Password_C` varchar(15) NOT NULL,
  `Address_C` varchar(30) NOT NULL,
  `Activated_C` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID_C`),
  UNIQUE KEY `Email_C` (`Email_C`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'Vincenzo','Fraello','vincenzo.fraello@gmail.com','29964712','Via P.G. C 28',1),(2,'Lorenzo','Di Palma','lorenzo.dipalma@gmail.com','29963612','Via F. R 2',1),(3,'Mario','Rossi','mario.rossi@gmail.com','12345678','Via D. P. L 5',1),(4,'Alicie','Bob','a@b','12345678','Via F. C. A 5',1);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `ID_E` int NOT NULL AUTO_INCREMENT,
  `Name_E` varchar(20) NOT NULL,
  `Surname_E` varchar(20) NOT NULL,
  `Email_E` varchar(30) NOT NULL,
  `Password_E` varchar(8) NOT NULL,
  PRIMARY KEY (`ID_E`),
  UNIQUE KEY `email_unique` (`Email_E`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'Paolo','Bianchi','paolo.bianchi@outlook.it','87654321'),(2,'a','b','a@b','12345678');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
  `ID_N` int NOT NULL AUTO_INCREMENT,
  `CodCustomer_N` int NOT NULL,
  `CodWine_N` int NOT NULL,
  `NumBottles_N` int NOT NULL,
  `FlagSent_N` tinyint(1) NOT NULL DEFAULT '0',
  `Date_N` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID_N`),
  KEY `CodCustomer_N` (`CodCustomer_N`),
  KEY `CodWine_N` (`CodWine_N`),
  CONSTRAINT `notification_ibfk_1` FOREIGN KEY (`CodCustomer_N`) REFERENCES `customer` (`ID_C`),
  CONSTRAINT `notification_ibfk_2` FOREIGN KEY (`CodWine_N`) REFERENCES `wine` (`ID_W`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` VALUES (2,4,5,123,0,NULL),(3,4,5,1233,0,NULL),(4,4,5,456,0,NULL),(5,4,3,145,0,NULL),(6,4,1,5,0,NULL);
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderedwineemployee`
--

DROP TABLE IF EXISTS `orderedwineemployee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderedwineemployee` (
  `ID_W_E` int NOT NULL AUTO_INCREMENT,
  `Name_W_E` varchar(20) NOT NULL,
  `Producer_W_E` varchar(20) NOT NULL,
  `Year_W_E` varchar(4) NOT NULL,
  `TechnicalNotes_W_E` varchar(30) DEFAULT NULL,
  `Vines_W_E` varchar(30) DEFAULT NULL,
  `NumBottles_W_E` int NOT NULL,
  `Price_W_E` float NOT NULL,
  `Uploaded_W_E` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID_W_E`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderedwineemployee`
--

LOCK TABLES `orderedwineemployee` WRITE;
/*!40000 ALTER TABLE `orderedwineemployee` DISABLE KEYS */;
INSERT INTO `orderedwineemployee` VALUES (1,'Merlot','Verdi','1970','c','c',12,11.6,1),(2,'Merlot','L','1970','c','c',10,224,1),(3,'WineTest','a','2017','wow ','polinesia',23,14.5,1),(4,'a','a','2020','c','c',2,0.8,1),(5,'Merlot','Bianchi','1970','c','c',1,8.4,1);
/*!40000 ALTER TABLE `orderedwineemployee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `ID_O` int NOT NULL AUTO_INCREMENT,
  `CodCustomer_O` int NOT NULL,
  `CodWine_O` int NOT NULL,
  `NumBOttles_O` int NOT NULL,
  `Completed_O` tinyint(1) NOT NULL DEFAULT '0',
  `Data_O` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Tot_O` float NOT NULL,
  PRIMARY KEY (`ID_O`),
  KEY `CodCustomer_O` (`CodCustomer_O`),
  KEY `CodWine_O` (`CodWine_O`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`CodCustomer_O`) REFERENCES `customer` (`ID_C`),
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`CodWine_O`) REFERENCES `wine` (`ID_W`)
) ENGINE=InnoDB AUTO_INCREMENT=149 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,4,3,1,1,'2020-12-17 20:37:28',10.5),(2,4,5,2,1,'2020-12-17 20:42:01',2),(3,4,5,1,1,'2020-12-17 20:43:41',1),(4,4,5,3,1,'2020-12-17 20:44:05',3),(5,4,5,2,1,'2020-12-17 20:44:47',2),(6,4,1,2,1,'2020-12-17 20:45:04',24),(7,4,2,2,1,'2020-12-17 20:47:08',29),(8,4,5,3,0,'2020-12-17 21:08:18',3),(9,4,5,1,0,'2020-12-22 20:13:05',1),(10,4,5,1,0,'2020-12-22 20:15:29',1),(11,4,1,2,0,'2020-12-22 21:11:10',24),(24,1,1,1,0,'2020-12-26 14:05:05',8),(25,1,2,1,0,'2020-12-26 14:05:06',12),(26,1,3,1,0,'2020-12-26 14:05:06',14),(27,1,4,1,0,'2020-12-26 14:05:06',11),(28,1,1,1,0,'2020-12-26 14:09:50',8),(29,1,2,1,0,'2020-12-26 14:09:51',12),(30,1,3,1,0,'2020-12-26 14:09:51',14),(31,1,4,1,0,'2020-12-26 14:09:51',11),(32,1,1,1,0,'2020-12-26 14:11:57',8),(33,1,2,1,0,'2020-12-26 14:11:57',12),(34,1,3,1,0,'2020-12-26 14:11:57',14),(35,1,4,1,0,'2020-12-26 14:11:58',11),(36,1,1,1,0,'2020-12-26 14:14:26',8),(37,1,2,1,0,'2020-12-26 14:14:26',12),(38,1,3,1,0,'2020-12-26 14:14:27',14),(39,1,4,1,0,'2020-12-26 14:14:27',11),(40,1,1,1,0,'2020-12-26 14:17:00',8),(41,1,2,1,0,'2020-12-26 14:17:01',12),(42,1,3,1,0,'2020-12-26 14:17:01',14),(43,1,4,1,0,'2020-12-26 14:17:01',11),(44,1,1,1,0,'2020-12-26 14:18:11',8),(45,1,2,1,0,'2020-12-26 14:18:11',12),(46,1,3,1,0,'2020-12-26 14:18:11',14),(47,1,4,1,0,'2020-12-26 14:18:11',11),(48,1,1,1,0,'2020-12-26 14:20:18',8),(49,1,2,1,0,'2020-12-26 14:20:18',12),(50,1,3,1,0,'2020-12-26 14:20:18',14),(51,1,4,1,0,'2020-12-26 14:20:19',11),(52,1,1,1,0,'2020-12-26 14:27:04',8),(53,1,2,1,0,'2020-12-26 14:27:04',12),(54,1,3,1,0,'2020-12-26 14:27:05',14),(55,1,4,1,0,'2020-12-26 14:27:05',11),(56,1,1,1,0,'2020-12-26 14:27:42',8),(57,1,2,1,0,'2020-12-26 14:27:42',12),(58,1,3,1,0,'2020-12-26 14:27:42',14),(59,1,4,1,0,'2020-12-26 14:27:43',11),(60,1,1,1,0,'2020-12-26 14:29:07',8),(61,1,2,1,0,'2020-12-26 14:29:07',12),(62,1,3,1,0,'2020-12-26 14:29:08',14),(63,1,4,1,0,'2020-12-26 14:29:08',11),(64,4,1,20,0,'2020-12-26 14:40:29',240),(65,1,1,1,0,'2020-12-26 14:45:29',8),(66,1,3,1,0,'2020-12-26 15:10:01',5),(67,1,3,1,0,'2020-12-26 15:15:19',4),(68,1,1,1,0,'2020-12-26 15:18:22',20),(69,1,3,1,0,'2020-12-26 15:18:23',3),(70,1,1,1,0,'2020-12-26 15:22:48',19),(71,1,3,1,0,'2020-12-26 15:22:49',2),(72,1,1,1,0,'2020-12-26 15:35:46',18),(73,1,3,1,0,'2020-12-26 15:35:47',1),(74,1,1,1,0,'2020-12-26 16:40:50',17),(75,1,1,1,0,'2020-12-26 16:41:16',16),(76,1,1,1,0,'2020-12-26 16:44:11',15),(77,1,1,1,0,'2020-12-26 17:20:02',14),(78,1,1,1,0,'2020-12-26 17:36:37',12),(79,1,1,-1,0,'2020-12-26 17:36:38',12),(80,1,1,50,0,'2020-12-26 17:36:38',12),(81,1,1,1,0,'2020-12-26 17:40:07',12),(82,1,1,-1,0,'2020-12-26 17:40:07',12),(83,1,1,50,0,'2020-12-26 17:40:07',12),(84,1,1,1,0,'2020-12-28 12:49:17',12),(85,1,1,-1,0,'2020-12-28 12:49:18',12),(86,1,1,50,0,'2020-12-28 12:49:18',12),(87,1,1,1,0,'2020-12-28 12:49:31',12),(88,1,1,-1,0,'2020-12-28 12:49:32',12),(89,1,1,50,0,'2020-12-28 12:49:32',12),(90,1,1,1,0,'2020-12-28 13:36:14',12),(91,1,1,-1,0,'2020-12-28 13:36:14',12),(92,1,1,50,0,'2020-12-28 13:36:14',12),(93,1,1,1,0,'2020-12-28 13:39:29',12),(94,1,1,-1,0,'2020-12-28 13:39:29',12),(95,1,1,50,0,'2020-12-28 13:39:29',12),(96,1,1,1,0,'2020-12-28 13:41:37',12),(97,1,1,-1,0,'2020-12-28 13:41:37',12),(98,1,1,50,0,'2020-12-28 13:41:37',12),(99,1,1,1,0,'2020-12-28 13:46:16',12),(100,1,1,-1,0,'2020-12-28 13:46:16',12),(101,1,1,50,0,'2020-12-28 13:46:16',12),(102,1,1,1,0,'2020-12-28 14:37:47',12),(103,1,1,-1,0,'2020-12-28 14:37:47',12),(104,1,1,50,0,'2020-12-28 14:37:48',12),(105,1,1,1,0,'2020-12-28 14:39:47',12),(106,1,1,-1,0,'2020-12-28 14:39:47',12),(107,1,1,50,0,'2020-12-28 14:39:47',12),(108,1,1,1,0,'2020-12-28 18:49:52',12),(109,1,1,-1,0,'2020-12-28 18:49:52',12),(110,1,1,50,0,'2020-12-28 18:49:52',12),(111,1,1,1,0,'2020-12-28 18:57:20',1000),(112,1,1,1,0,'2020-12-28 18:57:38',12),(113,1,1,-1,0,'2020-12-28 18:57:38',12),(114,1,1,50,0,'2020-12-28 18:57:38',12),(115,1,1,1,0,'2020-12-28 18:57:41',949),(116,1,1,1,0,'2020-12-29 15:25:03',12),(117,1,1,-1,0,'2020-12-29 15:25:04',12),(118,1,1,50,0,'2020-12-29 15:25:04',12),(119,1,1,1,0,'2020-12-29 15:25:30',12),(120,1,1,-1,0,'2020-12-29 15:25:31',12),(121,1,1,50,0,'2020-12-29 15:25:31',12),(122,1,1,1,0,'2020-12-29 15:25:35',848),(123,1,1,1,0,'2020-12-29 15:26:43',847),(124,1,1,1,0,'2020-12-29 15:28:18',846),(125,1,1,1,0,'2020-12-29 15:35:27',845),(126,1,1,1,0,'2021-01-04 15:26:44',12),(127,1,1,-1,0,'2021-01-04 15:26:44',12),(128,1,1,50,0,'2021-01-04 15:26:44',12),(129,1,1,1,0,'2021-01-04 15:26:48',794),(130,1,1,1,0,'2021-01-04 15:30:05',12),(131,1,1,-1,0,'2021-01-04 15:30:05',12),(132,1,1,50,0,'2021-01-04 15:30:05',12),(133,1,1,1,0,'2021-01-04 15:30:08',743),(134,1,1,1,0,'2021-01-04 15:31:50',742),(135,1,1,1,0,'2021-01-04 15:32:42',741),(136,1,1,1,0,'2021-01-04 16:23:20',740),(137,1,1,1,0,'2021-01-04 16:26:47',739),(138,1,1,1,0,'2021-01-04 16:27:58',12),(139,1,1,-1,0,'2021-01-04 16:27:58',12),(140,1,1,50,0,'2021-01-04 16:27:58',12),(141,1,1,1,0,'2021-01-04 16:30:04',12),(142,1,1,-1,0,'2021-01-04 16:30:04',12),(143,1,1,50,0,'2021-01-04 16:30:04',12),(144,1,1,1,0,'2021-01-04 16:32:26',638),(145,1,1,1,0,'2021-01-04 16:32:55',12),(146,1,1,-1,0,'2021-01-04 16:32:55',12),(147,1,1,50,0,'2021-01-04 16:32:55',12),(148,1,1,1,0,'2021-01-04 16:32:57',587);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wine`
--

DROP TABLE IF EXISTS `wine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wine` (
  `ID_W` int NOT NULL AUTO_INCREMENT,
  `Name_W` varchar(20) NOT NULL,
  `Producer_W` varchar(20) NOT NULL,
  `Year_W` varchar(4) NOT NULL,
  `TechnicalNotes_W` varchar(30) DEFAULT NULL,
  `Vines_W` varchar(30) DEFAULT NULL,
  `NumBottles_W` int NOT NULL,
  `Price_W` float NOT NULL,
  `Visualized_W` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID_W`),
  UNIQUE KEY `Name_W` (`Name_W`,`Producer_W`,`Year_W`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wine`
--

LOCK TABLES `wine` WRITE;
/*!40000 ALTER TABLE `wine` DISABLE KEYS */;
INSERT INTO `wine` VALUES (1,'Tavernello','Bianchi','2020','c','c',586,12,0),(2,'Merlot','Verdi','1970','c','c',23,14.5,0),(3,'Merlot','Bianchi','1970','c','c',0,10.5,0),(4,'Merlot','L','1970','c','c',20,280,0),(5,'a','a','2020','c','c',0,1,0);
/*!40000 ALTER TABLE `wine` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-04 17:37:24
