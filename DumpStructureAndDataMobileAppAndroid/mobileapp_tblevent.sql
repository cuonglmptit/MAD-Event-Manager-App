-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: mobileapp
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Table structure for table `tblevent`
--

DROP TABLE IF EXISTS `tblevent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblevent` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userId` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `starttime` varchar(255) DEFAULT NULL,
  `endtime` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `des` varchar(255) DEFAULT NULL,
  `eventvideo` varchar(255) DEFAULT NULL,
  `registrationtype` varchar(255) DEFAULT NULL,
  `websitelink` varchar(255) DEFAULT NULL,
  `imgurl` varchar(255) DEFAULT NULL,
  `startdate` date DEFAULT NULL,
  `enddate` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  CONSTRAINT `tblevent_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `tbluser` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblevent`
--

LOCK TABLES `tblevent` WRITE;
/*!40000 ALTER TABLE `tblevent` DISABLE KEYS */;
INSERT INTO `tblevent` VALUES (1,1,'PTIT thuc tap, den tuong lai','06:00 am','08:00 pm','PTIT','Nguyen Trai km12','Ha Noi','Hướng dấn thực tập tốt nghiệp và ptit career','link','venue','link','http://10.0.2.2:8080/image/fileSystem/92021.jpg','2024-05-30','2024-05-30'),(4,2,'Summoner\'s Rift','09:00 am','08:00 pm','SRift','Client Liên Minh Huyền Thoại','Lào Cai','Lên đại cao thủ','link','venue','link','http://10.0.2.2:8080/image/fileSystem/3G6fna9H.jpg','2024-04-30','2024-04-30'),(5,1,'Tham vuon huong duong','13:00 am','08:00 pm','PTIT','Nha trang','Ha Noi','Thăm vừa hướng dương cùng người đẹp anime','link','venue','link','http://10.0.2.2:8080/image/fileSystem/4GELPJCw-wallha.com.jpg','2024-03-31','2024-03-31');
/*!40000 ALTER TABLE `tblevent` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-09 16:30:39
