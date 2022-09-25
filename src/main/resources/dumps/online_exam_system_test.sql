-- MySQL dump 10.13  Distrib 8.0.30, for Linux (x86_64)
--
-- Host: localhost    Database: online_exam_system_test
-- ------------------------------------------------------
-- Server version	8.0.30-0ubuntu0.22.04.1

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
-- Table structure for table `ANSWER`
--

DROP TABLE IF EXISTS `ANSWER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ANSWER` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `CONTENT` longtext,
  `IS_CORRECT` bit(1) DEFAULT NULL,
  `QUESTION_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKl49sk30wrfoteigkl5w76dqhy` (`QUESTION_ID`),
  CONSTRAINT `FKl49sk30wrfoteigkl5w76dqhy` FOREIGN KEY (`QUESTION_ID`) REFERENCES `QUESTION` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ANSWER`
--

LOCK TABLES `ANSWER` WRITE;
/*!40000 ALTER TABLE `ANSWER` DISABLE KEYS */;
INSERT INTO `ANSWER` VALUES (1,' System.out.println(\"Hello World\");',_binary '',1),(2,' Console.WriteLine(\"Hello World\");',_binary '\0',1),(3,' echo(\"Hello World\");',_binary '\0',1),(4,' print (\"Hello World\");',_binary '\0',1),(5,'True',_binary '\0',2),(6,'False',_binary '',2),(7,' /* This is a comment',_binary '\0',3),(8,' // This is a comment',_binary '',3),(9,'# This is a comment',_binary '\0',3),(10,'Txt',_binary '\0',4),(11,'string',_binary '\0',4),(12,'String',_binary '',4),(13,'Text',_binary '\0',4),(14,'x=5',_binary '\0',5),(15,'int x=5',_binary '',5),(16,'float x=5',_binary '\0',5),(17,'num x=5',_binary '\0',5),(18,'int x=2.8f',_binary '\0',6),(19,'float x=2.8f',_binary '',6),(20,'x=2.8f',_binary '\0',6),(21,'byte x=2.8f',_binary '\0',6),(22,'length()',_binary '',7),(23,'len()',_binary '\0',7),(24,'size()',_binary '\0',7),(25,'getSize()',_binary '\0',7),(26,'the + size',_binary '',8),(27,'the * size',_binary '\0',8),(28,'the & size',_binary '\0',8),(29,'true',_binary '\0',9),(30,'false',_binary '',9),(31,'upperCase()',_binary '\0',10),(32,'toUpperCase()',_binary '',10),(33,'tuc()',_binary '\0',10),(34,'uppercase()',_binary '\0',10),(42,'x',_binary '',14),(43,'y',_binary '\0',14),(44,'2',_binary '',15),(45,'3',_binary '\0',15),(46,'4',_binary '\0',15),(47,'5',_binary '\0',15);
/*!40000 ALTER TABLE `ANSWER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CATALOG`
--

DROP TABLE IF EXISTS `CATALOG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CATALOG` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) DEFAULT NULL,
  `CATALOG_PARENT_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `CATALOG_ID_FK` (`CATALOG_PARENT_ID`),
  CONSTRAINT `CATALOG_ID_FK` FOREIGN KEY (`CATALOG_PARENT_ID`) REFERENCES `CATALOG` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CATALOG`
--

LOCK TABLES `CATALOG` WRITE;
/*!40000 ALTER TABLE `CATALOG` DISABLE KEYS */;
INSERT INTO `CATALOG` VALUES (1,'Software programming',NULL),(2,'Java',1),(3,'Networking',4),(4,'System administration',NULL);
/*!40000 ALTER TABLE `CATALOG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EXAM`
--

DROP TABLE IF EXISTS `EXAM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `EXAM` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `BANNER_IMAGE` varchar(255) DEFAULT NULL,
  `DESCRIPTION` longtext,
  `DURATION` int DEFAULT NULL,
  `EXAM_CODE` varchar(255) DEFAULT NULL,
  `PASSING_SCORE` int DEFAULT NULL,
  `TITLE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EXAM`
--

LOCK TABLES `EXAM` WRITE;
/*!40000 ALTER TABLE `EXAM` DISABLE KEYS */;
INSERT INTO `EXAM` VALUES (1,NULL,'Oracle Certificate Associate JavaSE 8 ',75,'1Z0-808',65,'Oracle Certificate Associate JavaSE 8'),(2,NULL,'Starting point to become certified network administrator',50,'200-125',20,'CCNA Cisco Certified Network Associate'),(3,NULL,'Become a leader at virtualization technology',60,'1V0-41.20',65,'Associate VMware Network Virtualization');
/*!40000 ALTER TABLE `EXAM` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PROFILE`
--

DROP TABLE IF EXISTS `PROFILE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PROFILE` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `BIRTHDAY` date DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `FULL_NAME` varchar(255) DEFAULT NULL,
  `GENDER` varchar(255) DEFAULT NULL,
  `IMAGE` varchar(255) DEFAULT NULL,
  `PHONE_NO` varchar(255) DEFAULT NULL,
  `USER_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKeljpboq7i9xlblsmcpra03l2f` (`USER_ID`),
  CONSTRAINT `FKeljpboq7i9xlblsmcpra03l2f` FOREIGN KEY (`USER_ID`) REFERENCES `USER` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PROFILE`
--

LOCK TABLES `PROFILE` WRITE;
/*!40000 ALTER TABLE `PROFILE` DISABLE KEYS */;
INSERT INTO `PROFILE` VALUES (1,'2022-01-03','admin@gmail.co','Luong Xuan Tran','male',NULL,'0353943399',1),(2,'2022-01-17','tranxuanluong1998@gmail.com','Tran Xuan Luong','male',NULL,'0353943399',2);
/*!40000 ALTER TABLE `PROFILE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QUESTION`
--

DROP TABLE IF EXISTS `QUESTION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `QUESTION` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `CONTENT` longtext,
  `CATALOG_ID` bigint DEFAULT NULL,
  `EXAM_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKjojeid0rnvm2oxbdfnvo0n61r` (`CATALOG_ID`),
  KEY `FKaaga3f1pxwdq5rbos402sfqxs` (`EXAM_ID`),
  CONSTRAINT `FKaaga3f1pxwdq5rbos402sfqxs` FOREIGN KEY (`EXAM_ID`) REFERENCES `EXAM` (`ID`),
  CONSTRAINT `FKjojeid0rnvm2oxbdfnvo0n61r` FOREIGN KEY (`CATALOG_ID`) REFERENCES `CATALOG` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QUESTION`
--

LOCK TABLES `QUESTION` WRITE;
/*!40000 ALTER TABLE `QUESTION` DISABLE KEYS */;
INSERT INTO `QUESTION` VALUES (1,'What is a correct syntax to output \"Hello World\" in Java?',2,1),(2,'Java is short for \"JavaScript\".',2,1),(3,'How do you insert COMMENTS in Java code?',2,1),(4,'Which data type is used to create a variable that should store text?',1,1),(5,'How do you create a variable with the numeric value 5?',1,1),(6,'How do you create a variable with the floating number 2.8?',2,1),(7,'Which method can be used to find the length of a string?',2,1),(8,'Which operator is used to add together two values?',2,1),(9,'The value of a string variable can be surrounded by single quotes.',2,1),(10,'Which method can be used to return a string in upper case letters?',2,1),(14,'XX',NULL,NULL),(15,'1+1=?',NULL,NULL);
/*!40000 ALTER TABLE `QUESTION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ROLE`
--

DROP TABLE IF EXISTS `ROLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ROLE` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ROLE`
--

LOCK TABLES `ROLE` WRITE;
/*!40000 ALTER TABLE `ROLE` DISABLE KEYS */;
INSERT INTO `ROLE` VALUES (1,'ROLE_USER'),(2,'ROLE_ADMIN');
/*!40000 ALTER TABLE `ROLE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER`
--

DROP TABLE IF EXISTS `USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `USER` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `ENABLED` bit(1) NOT NULL DEFAULT b'1',
  `PASSWORD` varchar(255) NOT NULL,
  `USERNAME` varchar(255) NOT NULL,
  `PROFILE_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKn1r0yrxq1kslkg5suy0tmpsy8` (`PROFILE_ID`),
  CONSTRAINT `FKn1r0yrxq1kslkg5suy0tmpsy8` FOREIGN KEY (`PROFILE_ID`) REFERENCES `PROFILE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER`
--

LOCK TABLES `USER` WRITE;
/*!40000 ALTER TABLE `USER` DISABLE KEYS */;
INSERT INTO `USER` VALUES (1,_binary '\0','$2a$10$S0Lq3EEbzbwI2RU3b59eCOTeWZO5qxbmlkkcXmsDunl.cjgwDQ6.q','admin',NULL),(2,_binary '\0','$2a$10$wcyLaOMnMqrkzWhNOHxske5Tu3.nUIv5X3tD4xTdtlRKsk7yk25r2','luong',NULL);
/*!40000 ALTER TABLE `USER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_EXAM`
--

DROP TABLE IF EXISTS `USER_EXAM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `USER_EXAM` (
  `EXAM_ID` bigint NOT NULL,
  `FINISHED_DATE` datetime(6) DEFAULT NULL,
  `EXAM_SCORE` int DEFAULT NULL,
  `EXAM_STATUS` bit(1) DEFAULT NULL,
  `USER_ID` bigint NOT NULL,
  `ID` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`,`USER_ID`,`EXAM_ID`),
  KEY `FK_USER` (`USER_ID`),
  KEY `FK_EXAM` (`EXAM_ID`),
  CONSTRAINT `FK_EXAM` FOREIGN KEY (`EXAM_ID`) REFERENCES `EXAM` (`ID`),
  CONSTRAINT `FK_USER` FOREIGN KEY (`USER_ID`) REFERENCES `USER` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_EXAM`
--

LOCK TABLES `USER_EXAM` WRITE;
/*!40000 ALTER TABLE `USER_EXAM` DISABLE KEYS */;
INSERT INTO `USER_EXAM` VALUES (1,'2022-01-02 23:34:57.912890',30,_binary '\0',2,1),(1,'2022-01-02 23:36:28.842561',100,_binary '',2,2);
/*!40000 ALTER TABLE `USER_EXAM` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_ROLE`
--

DROP TABLE IF EXISTS `USER_ROLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `USER_ROLE` (
  `USER_ID` bigint NOT NULL,
  `ROLE_ID` bigint NOT NULL,
  PRIMARY KEY (`USER_ID`,`ROLE_ID`),
  KEY `FKc2x25s5otkg7w4txxdhhfjs4d` (`ROLE_ID`),
  CONSTRAINT `FKc2x25s5otkg7w4txxdhhfjs4d` FOREIGN KEY (`ROLE_ID`) REFERENCES `ROLE` (`ID`),
  CONSTRAINT `FKru1oq1y3m2v2kv2sc68nixhxy` FOREIGN KEY (`USER_ID`) REFERENCES `USER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_ROLE`
--

LOCK TABLES `USER_ROLE` WRITE;
/*!40000 ALTER TABLE `USER_ROLE` DISABLE KEYS */;
INSERT INTO `USER_ROLE` VALUES (2,1),(1,2);
/*!40000 ALTER TABLE `USER_ROLE` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-09-26  0:45:25
