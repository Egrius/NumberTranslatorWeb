-- MySQL dump 10.13  Distrib 9.3.0, for Linux (x86_64)
--
-- Host: localhost    Database: numbers_representation
-- ------------------------------------------------------
-- Server version	9.3.0

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
-- Table structure for table `declinations`
--

DROP TABLE IF EXISTS `declinations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `declinations` (
  `id` int NOT NULL AUTO_INCREMENT,
  `category` enum('INTEGER','FRACTIONAL') NOT NULL,
  `number` int NOT NULL,
  `singular` varchar(30) NOT NULL,
  `few` varchar(30) DEFAULT NULL,
  `many` varchar(30) DEFAULT NULL,
  `fraction_base_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fraction_base_id` (`fraction_base_id`),
  CONSTRAINT `declinations_ibfk_1` FOREIGN KEY (`fraction_base_id`) REFERENCES `fraction_bases` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `declinations`
--

LOCK TABLES `declinations` WRITE;
/*!40000 ALTER TABLE `declinations` DISABLE KEYS */;
INSERT INTO `declinations` VALUES (1,'INTEGER',4,'тысяча','тысячи','тысяч',NULL),(2,'INTEGER',7,'миллион','миллиона','миллионов',NULL),(3,'INTEGER',10,'миллиард','миллиарда','миллиардов',NULL),(4,'INTEGER',13,'триллион','триллиона','триллионов',NULL),(5,'FRACTIONAL',1,'ая',NULL,'ых',1),(6,'FRACTIONAL',2,'ая',NULL,'ых',2),(7,'FRACTIONAL',3,'ая',NULL,'ых',3),(8,'FRACTIONAL',4,'ая',NULL,'ых',4),(9,'FRACTIONAL',5,'ая',NULL,'ых',5),(10,'FRACTIONAL',6,'ая',NULL,'ых',6),(11,'FRACTIONAL',7,'ая',NULL,'ых',7),(12,'FRACTIONAL',8,'ая',NULL,'ых',8),(13,'FRACTIONAL',9,'ая',NULL,'ых',9),(14,'FRACTIONAL',10,'ая',NULL,'ых',10),(15,'FRACTIONAL',11,'ая',NULL,'ых',11),(16,'FRACTIONAL',12,'ая',NULL,'ых',12),(17,'FRACTIONAL',13,'ая',NULL,'ых',13),(18,'FRACTIONAL',14,'ая',NULL,'ых',14);
/*!40000 ALTER TABLE `declinations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `digits`
--

DROP TABLE IF EXISTS `digits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `digits` (
  `id` int NOT NULL AUTO_INCREMENT,
  `number` int unsigned NOT NULL,
  `number_str` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `digits`
--

LOCK TABLES `digits` WRITE;
/*!40000 ALTER TABLE `digits` DISABLE KEYS */;
INSERT INTO `digits` VALUES (1,1,'один'),(2,2,'два'),(3,3,'три'),(4,4,'четыре'),(5,5,'пять'),(6,6,'шесть'),(7,7,'семь'),(8,8,'восемь'),(9,9,'девять');
/*!40000 ALTER TABLE `digits` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fraction_bases`
--

DROP TABLE IF EXISTS `fraction_bases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fraction_bases` (
  `id` int NOT NULL AUTO_INCREMENT,
  `base` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fraction_bases`
--

LOCK TABLES `fraction_bases` WRITE;
/*!40000 ALTER TABLE `fraction_bases` DISABLE KEYS */;
INSERT INTO `fraction_bases` VALUES (1,'десят'),(2,'сот'),(3,'тысячн'),(4,'десятитысячн'),(5,'стотысячн'),(6,'миллионн'),(7,'десятимиллионн'),(8,'стомиллионн'),(9,'триллион'),(10,'десятитриллионн'),(11,'стотриллионн'),(12,'триллионн'),(13,'десятитриллионн'),(14,'стотриллионн');
/*!40000 ALTER TABLE `fraction_bases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hundreds`
--

DROP TABLE IF EXISTS `hundreds`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hundreds` (
  `id` int NOT NULL AUTO_INCREMENT,
  `number` int unsigned NOT NULL,
  `number_str` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hundreds`
--

LOCK TABLES `hundreds` WRITE;
/*!40000 ALTER TABLE `hundreds` DISABLE KEYS */;
INSERT INTO `hundreds` VALUES (1,1,'сто'),(2,2,'двести'),(3,3,'триста'),(4,4,'четыреста'),(5,5,'пятьсот'),(6,6,'шестьсот'),(7,7,'семьсот'),(8,8,'восемьсот'),(9,9,'девятьсот');
/*!40000 ALTER TABLE `hundreds` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `numbers`
--

DROP TABLE IF EXISTS `numbers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `numbers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `number` int unsigned NOT NULL,
  `number_str` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `numbers`
--

LOCK TABLES `numbers` WRITE;
/*!40000 ALTER TABLE `numbers` DISABLE KEYS */;
INSERT INTO `numbers` VALUES (1,10,'десять'),(2,11,'одиннадцать'),(3,12,'двенадцать'),(4,13,'тринадцать'),(5,14,'четырнадцать'),(6,15,'пятнадцать'),(7,16,'шестнадцать'),(8,17,'семнадцать'),(9,18,'восемнадцать'),(10,19,'девятнадцать');
/*!40000 ALTER TABLE `numbers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tens`
--

DROP TABLE IF EXISTS `tens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tens` (
  `id` int NOT NULL AUTO_INCREMENT,
  `number` int unsigned NOT NULL,
  `number_str` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tens`
--

LOCK TABLES `tens` WRITE;
/*!40000 ALTER TABLE `tens` DISABLE KEYS */;
INSERT INTO `tens` VALUES (1,1,'десять'),(2,2,'двадцать'),(3,3,'тридцать'),(4,4,'сорок'),(5,5,'пятьдесят'),(6,6,'шестьдесят'),(7,7,'семьдесят'),(8,8,'восемьдесят'),(9,9,'девяносто');
/*!40000 ALTER TABLE `tens` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-29 15:34:11
