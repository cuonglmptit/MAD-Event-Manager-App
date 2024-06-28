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
-- Table structure for table `tblfeedback`
--

DROP TABLE IF EXISTS `tblfeedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblfeedback` (
  `id` int NOT NULL AUTO_INCREMENT,
  `createdTime` datetime(6) DEFAULT NULL,
  `feedbackType` tinyint DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `eventId` int DEFAULT NULL,
  `userId` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5wy9nncy1pvnow9aq9t8l4l1q` (`eventId`),
  KEY `FKf1k3mtl6xxiw4doqk655y40dc` (`userId`),
  CONSTRAINT `FK5wy9nncy1pvnow9aq9t8l4l1q` FOREIGN KEY (`eventId`) REFERENCES `tblevent` (`id`),
  CONSTRAINT `FKf1k3mtl6xxiw4doqk655y40dc` FOREIGN KEY (`userId`) REFERENCES `tbluser` (`id`),
  CONSTRAINT `tblfeedback_chk_1` CHECK ((`feedbackType` between 0 and 2))
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblfeedback`
--

LOCK TABLES `tblfeedback` WRITE;
/*!40000 ALTER TABLE `tblfeedback` DISABLE KEYS */;
INSERT INTO `tblfeedback` VALUES (1,'2024-05-08 16:40:07.136000',0,'chan',1,1),(2,'2024-05-08 16:40:24.933000',2,'hay ma',1,3),(3,'2024-05-08 16:40:32.715000',0,'qua hay',1,4),(4,'2024-05-08 19:20:18.032000',0,'chan',1,5),(5,'2024-05-09 09:09:55.404000',0,'tu nhien bi huy, tuc gian',1,4),(6,'2024-05-09 09:09:55.404000',1,'Hay',1,5),(7,'2024-05-09 09:09:55.404000',1,'hay lam',1,6),(8,'2024-05-09 09:09:55.404000',1,'tam nen cho cao',1,7),(9,'2024-05-09 09:09:55.404000',2,'binh thuong',1,8),(10,'2024-05-09 13:37:28.138000',1,'test feedback',1,1),(11,'2024-05-09 14:56:01.515000',2,'Se den xem',1,5),(12,'2024-05-09 14:57:37.539000',1,'test',1,10),(13,'2024-05-09 14:57:47.930000',2,'test2',1,10),(14,'2024-05-09 16:09:16.147000',0,'22',1,1),(15,'2024-05-09 16:09:21.923000',1,'44',1,1);
/*!40000 ALTER TABLE `tblfeedback` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-09 16:30:38
