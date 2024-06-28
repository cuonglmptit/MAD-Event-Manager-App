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
-- Table structure for table `tblinvite`
--

DROP TABLE IF EXISTS `tblinvite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblinvite` (
  `id` int NOT NULL AUTO_INCREMENT,
  `context` text,
  `createdDate` datetime(6) DEFAULT NULL,
  `confirmed` tinyint DEFAULT NULL,
  `isRead` bit(1) DEFAULT NULL,
  `eventId` int DEFAULT NULL,
  `userId` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK99tyltonia6r5d09dfclovaco` (`eventId`),
  KEY `FKmg2p9mlkn671j95j0t6009mi8` (`userId`),
  CONSTRAINT `FK99tyltonia6r5d09dfclovaco` FOREIGN KEY (`eventId`) REFERENCES `tblevent` (`id`),
  CONSTRAINT `FKmg2p9mlkn671j95j0t6009mi8` FOREIGN KEY (`userId`) REFERENCES `tbluser` (`id`),
  CONSTRAINT `tblinvite_chk_1` CHECK ((`confirmed` between 0 and 2))
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblinvite`
--

LOCK TABLES `tblinvite` WRITE;
/*!40000 ALTER TABLE `tblinvite` DISABLE KEYS */;
INSERT INTO `tblinvite` VALUES (1,'Xin loi cac nban\n////////\nĐã có thay đổi về event: PTIT thuc tap, den tuong lai EventID: 1 sẽ dự kiến bị hủy','2024-05-09 14:53:27.412000',0,_binary '\0',1,2),(2,'Đã có thay đổi về event: Summoner\'s Rift EventID: 4 sẽ bắt đầu vào ngày Tuesday, 30 04 2024 lúc 06:00 am tại Nguyen Trai km12','2024-05-09 00:31:52.639000',0,_binary '',4,1),(3,'Người dùng Cuong gửi lời mời cho bạn đến Summoner\'s Rift EventID: 4 sẽ bắt đầu vào ngày Tuesday, 30 04 2024 lúc 06:00 am tại Nguyen Trai km12','2024-05-09 00:31:00.239000',2,_binary '',4,3),(4,'Người dùng Duy gửi lời mời cho bạn đến Tham vuon huong duong EventID: 5 sẽ bắt đầu vào ngày Sunday, 31 03 2024 lúc 13:00 am tại Nha trang','2024-05-09 09:06:26.765000',0,_binary '',5,2),(5,'Người dùng Duy gửi lời mời cho bạn đến Tham vuon huong duong EventID: 5 sẽ bắt đầu vào ngày Sunday, 31 03 2024 lúc 13:00 am tại Nha trang','2024-05-09 09:07:03.866000',2,_binary '',5,3),(6,'Xin loi cac nban\n////////\nĐã có thay đổi về event: PTIT thuc tap, den tuong lai EventID: 1 sẽ dự kiến bị hủy','2024-05-09 14:53:27.412000',0,_binary '\0',1,4),(7,'Xin loi cac nban\n////////\nĐã có thay đổi về event: PTIT thuc tap, den tuong lai EventID: 1 sẽ dự kiến bị hủy','2024-05-09 14:53:27.412000',0,_binary '',1,5),(8,'Xin loi cac nban\n////////\nĐã có thay đổi về event: PTIT thuc tap, den tuong lai EventID: 1 sẽ dự kiến bị hủy','2024-05-09 14:53:27.412000',1,_binary '\0',1,6),(9,'Xin loi cac nban\n////////\nĐã có thay đổi về event: PTIT thuc tap, den tuong lai EventID: 1 sẽ dự kiến bị hủy','2024-05-09 14:53:27.412000',1,_binary '\0',1,7),(10,'Xin loi cac nban\n////////\nĐã có thay đổi về event: PTIT thuc tap, den tuong lai EventID: 1 sẽ dự kiến bị hủy','2024-05-09 14:53:27.412000',2,_binary '\0',1,8);
/*!40000 ALTER TABLE `tblinvite` ENABLE KEYS */;
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
