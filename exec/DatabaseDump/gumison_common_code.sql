-- MySQL dump 10.13  Distrib 8.0.22, for macos10.15 (x86_64)
--
-- Host: j5a202.p.ssafy.io    Database: gumison
-- ------------------------------------------------------
-- Server version	8.0.26-0ubuntu0.20.04.2

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
-- Table structure for table `common_code`
--

DROP TABLE IF EXISTS `common_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `common_code` (
  `code` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `solution_exp` bigint DEFAULT NULL,
  `tier_exp` bigint DEFAULT NULL,
  `type` varchar(255) NOT NULL,
  `type_name` varchar(255) NOT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `common_code`
--

LOCK TABLES `common_code` WRITE;
/*!40000 ALTER TABLE `common_code` DISABLE KEYS */;
INSERT INTO `common_code` VALUES (101,'빨강',NULL,NULL,'100','래밸'),(102,'주황',NULL,NULL,'100','래밸'),(103,'노랑',NULL,NULL,'100','래밸'),(104,'초록',NULL,NULL,'100','래밸'),(105,'파랑',NULL,NULL,'100','래밸'),(106,'남색',NULL,NULL,'100','래밸'),(107,'보라',NULL,NULL,'100','래밸'),(108,'회색',NULL,NULL,'100','래밸'),(109,'갈색',NULL,NULL,'100','래밸'),(110,'검정',NULL,NULL,'100','래밸'),(111,'연두',NULL,NULL,'100','래밸'),(112,'핑크',NULL,NULL,'100','래밸'),(113,'흰색',NULL,NULL,'100','래밸'),(114,'P',NULL,NULL,'100','래밸'),(115,'A',NULL,NULL,'100','래밸'),(116,'B',NULL,NULL,'100','래밸'),(117,'C',NULL,NULL,'100','래밸'),(118,'D',NULL,NULL,'100','래밸'),(119,'E',NULL,NULL,'100','래밸'),(120,'F',NULL,NULL,'100','래밸'),(121,'G',NULL,NULL,'100','래밸'),(201,'Bronze4',10,50,'200','티어'),(202,'Bronze3',15,140,'200','티어'),(203,'Bronze2',23,302,'200','티어'),(204,'Bronze1',34,594,'200','티어'),(205,'Silver4',51,1118,'200','티어'),(206,'Silver3',76,2063,'200','티어'),(207,'Silver2',114,3764,'200','티어'),(208,'Silver1',171,6825,'200','티어'),(209,'Gold4',256,12335,'200','티어'),(210,'Gold3',384,22253,'200','티어'),(211,'Gold2',577,40105,'200','티어'),(212,'Gold1',865,72239,'200','티어'),(213,'Platinum4',1297,130081,'200','티어'),(214,'Platinum3',1946,234196,'200','티어'),(215,'Platinum2',2919,421603,'200','티어'),(216,'Platinum1',4379,758935,'200','티어'),(217,'Diamond4',6568,1340832,'200','티어'),(218,'Diamond3',9853,2344606,'200','티어'),(219,'Diamond2',14779,4076115,'200','티어'),(220,'Diamond1',22168,7062969,'200','티어'),(221,'Master4',33253,12215292,'200','티어'),(222,'Master3',49879,21103049,'200','티어'),(223,'Master2',74818,36434430,'200','티어'),(224,'Master1',112227,62881063,'200','티어');
/*!40000 ALTER TABLE `common_code` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-07  9:57:44
