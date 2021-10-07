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
-- Table structure for table `hours`
--

DROP TABLE IF EXISTS `hours`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hours` (
  `hours_id` bigint NOT NULL AUTO_INCREMENT,
  `end_time` time NOT NULL,
  `start_time` time NOT NULL,
  `type` int NOT NULL,
  `climbing_id` bigint DEFAULT NULL,
  PRIMARY KEY (`hours_id`),
  KEY `FK9ixgecq65t2rad6vvjlkd7ge5` (`climbing_id`),
  CONSTRAINT `FK9ixgecq65t2rad6vvjlkd7ge5` FOREIGN KEY (`climbing_id`) REFERENCES `climbing` (`climbing_id`)
) ENGINE=InnoDB AUTO_INCREMENT=345 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hours`
--

LOCK TABLES `hours` WRITE;
/*!40000 ALTER TABLE `hours` DISABLE KEYS */;
INSERT INTO `hours` VALUES (1,'22:00:00','12:00:00',1,1),(2,'22:00:00','12:00:00',2,1),(3,'22:00:00','12:00:00',3,1),(4,'22:00:00','12:00:00',4,1),(5,'22:00:00','12:00:00',5,1),(6,'19:00:00','10:00:00',6,1),(7,'19:00:00','10:00:00',7,1),(8,'19:00:00','10:00:00',8,1),(9,'22:00:00','10:00:00',1,2),(10,'22:00:00','10:00:00',2,2),(11,'22:00:00','10:00:00',3,2),(12,'22:00:00','10:00:00',4,2),(13,'22:00:00','10:00:00',5,2),(14,'22:00:00','10:00:00',6,2),(15,'20:00:00','10:00:00',7,2),(16,'20:00:00','10:00:00',8,2),(17,'22:00:00','10:00:00',1,3),(18,'22:00:00','10:00:00',2,3),(19,'22:00:00','10:00:00',3,3),(20,'22:00:00','10:00:00',4,3),(21,'22:00:00','10:00:00',5,3),(22,'20:00:00','10:00:00',6,3),(23,'20:00:00','10:00:00',7,3),(24,'20:00:00','10:00:00',8,3),(25,'22:00:00','10:00:00',1,4),(26,'22:00:00','10:00:00',2,4),(27,'22:00:00','10:00:00',3,4),(28,'22:00:00','10:00:00',4,4),(29,'22:00:00','10:00:00',5,4),(30,'22:00:00','10:00:00',6,4),(31,'20:00:00','10:00:00',7,4),(32,'20:00:00','10:00:00',8,4),(33,'22:00:00','10:00:00',1,5),(34,'22:00:00','10:00:00',2,5),(35,'22:00:00','10:00:00',3,5),(36,'22:00:00','10:00:00',4,5),(37,'22:00:00','10:00:00',5,5),(38,'20:00:00','10:00:00',6,5),(39,'20:00:00','10:00:00',7,5),(40,'20:00:00','10:00:00',8,5),(41,'22:00:00','10:00:00',1,6),(42,'22:00:00','10:00:00',2,6),(43,'22:00:00','10:00:00',3,6),(44,'22:00:00','10:00:00',4,6),(45,'22:00:00','10:00:00',5,6),(46,'20:00:00','10:00:00',6,6),(47,'20:00:00','10:00:00',7,6),(48,'20:00:00','10:00:00',8,6),(49,'23:00:00','15:00:00',1,7),(50,'23:00:00','14:00:00',2,7),(51,'23:00:00','14:00:00',3,7),(52,'23:00:00','14:00:00',4,7),(53,'23:00:00','14:00:00',5,7),(54,'19:00:00','12:00:00',6,7),(55,'19:00:00','12:00:00',7,7),(56,'19:00:00','12:00:00',8,7),(57,'23:00:00','15:00:00',1,8),(58,'23:00:00','14:00:00',2,8),(59,'23:00:00','14:00:00',3,8),(60,'23:00:00','14:00:00',4,8),(61,'23:00:00','14:00:00',5,8),(62,'19:00:00','12:00:00',6,8),(63,'19:00:00','12:00:00',7,8),(64,'19:00:00','12:00:00',8,8),(65,'22:30:00','10:00:00',1,9),(66,'22:30:00','10:00:00',2,9),(67,'22:30:00','10:00:00',3,9),(68,'22:30:00','10:00:00',4,9),(69,'22:30:00','10:00:00',5,9),(70,'18:30:00','10:00:00',6,9),(71,'18:30:00','10:00:00',7,9),(72,'18:30:00','10:00:00',8,9),(73,'22:00:00','12:00:00',1,10),(74,'22:00:00','12:00:00',2,10),(75,'22:00:00','12:00:00',3,10),(76,'22:00:00','12:00:00',4,10),(77,'22:00:00','12:00:00',5,10),(78,'20:00:00','12:00:00',6,10),(79,'20:00:00','12:00:00',7,10),(80,'20:00:00','12:00:00',8,10),(81,'22:00:00','10:00:00',1,11),(82,'22:00:00','10:00:00',2,11),(83,'22:00:00','10:00:00',3,11),(84,'22:00:00','10:00:00',4,11),(85,'22:00:00','10:00:00',5,11),(86,'20:00:00','10:00:00',6,11),(87,'20:00:00','10:00:00',7,11),(88,'20:00:00','10:00:00',8,11),(89,'23:00:00','11:00:00',1,12),(90,'23:00:00','11:00:00',2,12),(91,'23:00:00','11:00:00',3,12),(92,'23:00:00','11:00:00',4,12),(93,'23:00:00','11:00:00',5,12),(94,'18:00:00','11:00:00',6,12),(95,'18:00:00','11:00:00',7,12),(96,'18:00:00','11:00:00',8,12),(97,'23:00:00','11:00:00',1,13),(98,'23:00:00','11:00:00',2,13),(99,'23:00:00','11:00:00',3,13),(100,'23:00:00','11:00:00',4,13),(101,'23:00:00','11:00:00',5,13),(102,'20:00:00','12:00:00',6,13),(103,'20:00:00','12:00:00',7,13),(104,'20:00:00','12:00:00',8,13),(105,'23:00:00','14:00:00',1,14),(106,'23:00:00','14:00:00',2,14),(107,'23:00:00','14:00:00',3,14),(108,'23:00:00','14:00:00',4,14),(109,'23:00:00','17:00:00',5,14),(110,'19:00:00','11:00:00',6,14),(111,'19:00:00','11:00:00',7,14),(112,'00:00:00','00:00:00',8,14),(113,'23:00:00','11:00:00',1,15),(114,'23:00:00','11:00:00',2,15),(115,'23:00:00','11:00:00',3,15),(116,'23:00:00','11:00:00',4,15),(117,'23:00:00','11:00:00',5,15),(118,'20:00:00','12:00:00',6,15),(119,'20:00:00','12:00:00',7,15),(120,'20:00:00','12:00:00',8,15),(121,'22:30:00','11:00:00',1,16),(122,'22:30:00','11:00:00',2,16),(123,'22:30:00','11:00:00',3,16),(124,'22:30:00','11:00:00',4,16),(125,'22:30:00','11:00:00',5,16),(126,'19:00:00','11:00:00',6,16),(127,'19:00:00','11:00:00',7,16),(128,'19:00:00','11:00:00',8,16),(129,'23:00:00','10:00:00',1,17),(130,'23:00:00','10:00:00',2,17),(131,'23:00:00','10:00:00',3,17),(132,'23:00:00','10:00:00',4,17),(133,'23:00:00','10:00:00',5,17),(134,'19:00:00','10:00:00',6,17),(135,'19:00:00','10:00:00',7,17),(136,'19:00:00','10:00:00',8,17),(137,'22:00:00','10:00:00',1,18),(138,'22:00:00','10:00:00',2,18),(139,'22:00:00','10:00:00',3,18),(140,'22:00:00','10:00:00',4,18),(141,'22:00:00','10:00:00',5,18),(142,'18:00:00','10:00:00',6,18),(143,'18:00:00','10:00:00',7,18),(144,'18:00:00','10:00:00',8,18),(145,'22:00:00','11:00:00',1,19),(146,'22:00:00','11:00:00',2,19),(147,'22:00:00','11:00:00',3,19),(148,'22:00:00','11:00:00',4,19),(149,'22:00:00','11:00:00',5,19),(150,'19:00:00','11:00:00',6,19),(151,'19:00:00','11:00:00',7,19),(152,'19:00:00','11:00:00',8,19),(153,'22:00:00','10:00:00',1,20),(154,'22:00:00','10:00:00',2,20),(155,'22:00:00','10:00:00',3,20),(156,'22:00:00','10:00:00',4,20),(157,'22:00:00','10:00:00',5,20),(158,'20:00:00','11:00:00',6,20),(159,'20:00:00','11:00:00',7,20),(160,'20:00:00','11:00:00',8,20),(161,'23:00:00','11:00:00',1,21),(162,'23:00:00','11:00:00',2,21),(163,'23:00:00','11:00:00',3,21),(164,'23:00:00','11:00:00',4,21),(165,'23:00:00','12:00:00',5,21),(166,'20:00:00','12:00:00',6,21),(167,'20:00:00','12:00:00',7,21),(168,'20:00:00','12:00:00',8,21),(169,'23:00:00','11:00:00',1,22),(170,'23:00:00','11:00:00',2,22),(171,'23:00:00','11:00:00',3,22),(172,'23:00:00','11:00:00',4,22),(173,'23:00:00','11:00:00',5,22),(174,'20:00:00','10:00:00',6,22),(175,'20:00:00','10:00:00',7,22),(176,'20:00:00','10:00:00',8,22),(177,'23:30:00','11:00:00',1,23),(178,'23:30:00','11:00:00',2,23),(179,'23:30:00','11:00:00',3,23),(180,'23:30:00','11:00:00',4,23),(181,'21:00:00','11:00:00',5,23),(182,'20:00:00','11:00:00',6,23),(183,'20:00:00','11:00:00',7,23),(184,'20:00:00','11:00:00',8,23),(185,'23:00:00','11:00:00',1,24),(186,'23:00:00','11:00:00',2,24),(187,'23:00:00','11:00:00',3,24),(188,'23:00:00','11:00:00',4,24),(189,'23:00:00','11:00:00',5,24),(190,'19:00:00','11:00:00',6,24),(191,'19:00:00','11:00:00',7,24),(192,'00:00:00','00:00:00',8,24),(193,'23:00:00','12:00:00',1,25),(194,'23:00:00','12:00:00',2,25),(195,'23:00:00','12:00:00',3,25),(196,'23:00:00','12:00:00',4,25),(197,'23:00:00','16:00:00',5,25),(198,'20:00:00','12:00:00',6,25),(199,'20:00:00','12:00:00',7,25),(200,'20:00:00','12:00:00',8,25),(201,'23:00:00','10:00:00',1,26),(202,'23:00:00','10:00:00',2,26),(203,'23:00:00','10:00:00',3,26),(204,'23:00:00','10:00:00',4,26),(205,'23:00:00','10:00:00',5,26),(206,'18:00:00','10:00:00',6,26),(207,'00:00:00','00:00:00',7,26),(208,'00:00:00','00:00:00',8,26),(209,'22:00:00','10:00:00',1,27),(210,'22:00:00','10:00:00',2,27),(211,'22:00:00','10:00:00',3,27),(212,'22:00:00','10:00:00',4,27),(213,'22:00:00','10:00:00',5,27),(214,'20:00:00','10:00:00',6,27),(215,'20:00:00','10:00:00',7,27),(216,'20:00:00','10:00:00',8,27),(217,'23:00:00','13:00:00',1,28),(218,'23:00:00','13:00:00',2,28),(219,'23:00:00','13:00:00',3,28),(220,'23:00:00','13:00:00',4,28),(221,'23:00:00','13:00:00',5,28),(222,'19:00:00','11:00:00',6,28),(223,'19:00:00','11:00:00',7,28),(224,'19:00:00','11:00:00',8,28),(225,'23:00:00','11:00:00',1,29),(226,'23:00:00','11:00:00',2,29),(227,'23:00:00','11:00:00',3,29),(228,'23:00:00','11:00:00',4,29),(229,'23:00:00','11:00:00',5,29),(230,'20:00:00','14:00:00',6,29),(231,'20:00:00','14:00:00',7,29),(232,'20:00:00','14:00:00',8,29),(233,'22:00:00','10:00:00',1,30),(234,'22:00:00','10:00:00',2,30),(235,'22:00:00','10:00:00',3,30),(236,'22:00:00','10:00:00',4,30),(237,'22:00:00','10:00:00',5,30),(238,'22:00:00','10:00:00',6,30),(239,'22:00:00','10:00:00',7,30),(240,'22:00:00','10:00:00',8,30),(241,'23:00:00','10:00:00',1,31),(242,'23:00:00','10:00:00',2,31),(243,'23:00:00','10:00:00',3,31),(244,'23:00:00','10:00:00',4,31),(245,'23:00:00','10:00:00',5,31),(246,'20:00:00','10:00:00',6,31),(247,'20:00:00','10:00:00',7,31),(248,'20:00:00','10:00:00',8,31),(249,'23:00:00','07:00:00',1,32),(250,'23:00:00','07:00:00',2,32),(251,'23:00:00','07:00:00',3,32),(252,'23:00:00','07:00:00',4,32),(253,'23:00:00','07:00:00',5,32),(254,'20:00:00','10:00:00',6,32),(255,'20:00:00','10:00:00',7,32),(256,'20:00:00','10:00:00',8,32),(257,'23:00:00','12:00:00',1,33),(258,'23:00:00','12:00:00',2,33),(259,'23:00:00','12:00:00',3,33),(260,'23:00:00','12:00:00',4,33),(261,'23:00:00','12:00:00',5,33),(262,'20:00:00','11:00:00',6,33),(263,'20:00:00','11:00:00',7,33),(264,'20:00:00','11:00:00',8,33),(265,'23:00:00','11:30:00',1,34),(266,'23:00:00','11:30:00',2,34),(267,'23:00:00','11:30:00',3,34),(268,'23:00:00','11:30:00',4,34),(269,'22:00:00','15:00:00',5,34),(270,'19:00:00','12:00:00',6,34),(271,'00:00:00','00:00:00',7,34),(272,'19:00:00','12:00:00',8,34),(273,'22:00:00','12:00:00',1,36),(274,'22:00:00','12:00:00',2,36),(275,'22:00:00','12:00:00',3,36),(276,'22:00:00','12:00:00',4,36),(277,'22:00:00','12:00:00',5,36),(278,'22:00:00','10:00:00',6,36),(279,'22:00:00','10:00:00',7,36),(280,'22:00:00','10:00:00',8,36),(281,'23:00:00','10:00:00',1,37),(282,'23:00:00','10:00:00',2,37),(283,'23:00:00','10:00:00',3,37),(284,'23:00:00','10:00:00',4,37),(285,'23:00:00','10:00:00',5,37),(286,'20:00:00','13:00:00',6,37),(287,'20:00:00','13:00:00',7,37),(288,'20:00:00','13:00:00',8,37),(289,'22:00:00','12:00:00',1,35),(290,'22:00:00','12:00:00',2,35),(291,'22:00:00','12:00:00',3,35),(292,'22:00:00','12:00:00',4,35),(293,'22:00:00','12:00:00',5,35),(294,'19:00:00','10:00:00',6,35),(295,'19:00:00','10:00:00',7,35),(296,'19:00:00','10:00:00',8,35),(297,'22:00:00','10:00:00',1,38),(298,'22:00:00','10:00:00',2,38),(299,'22:00:00','10:00:00',3,38),(300,'22:00:00','10:00:00',4,38),(301,'21:45:00','15:00:00',5,38),(302,'18:45:00','10:00:00',6,38),(303,'18:45:00','10:00:00',7,38),(304,'18:45:00','10:00:00',8,38),(305,'22:30:00','11:00:00',1,39),(306,'22:30:00','11:00:00',2,39),(307,'22:30:00','11:00:00',3,39),(308,'22:30:00','11:00:00',4,39),(309,'22:35:00','11:00:00',5,39),(310,'19:00:00','10:30:00',6,39),(311,'19:00:00','10:30:00',7,39),(312,'19:00:00','10:30:00',8,39),(313,'23:00:00','14:00:00',1,40),(314,'23:00:00','14:00:00',2,40),(315,'23:00:00','14:00:00',3,40),(316,'23:00:00','14:00:00',4,40),(317,'23:00:00','14:00:00',5,40),(318,'18:00:00','11:00:00',6,40),(319,'00:00:00','00:00:00',7,40),(320,'00:00:00','00:00:00',8,40),(321,'23:00:00','10:00:00',1,41),(322,'23:00:00','10:00:00',2,41),(323,'23:00:00','10:00:00',3,41),(324,'23:00:00','10:00:00',4,41),(325,'23:00:00','10:00:00',5,41),(326,'20:00:00','10:00:00',6,41),(327,'20:00:00','10:00:00',7,41),(328,'20:00:00','10:00:00',8,41),(329,'23:00:00','11:00:00',1,42),(330,'23:00:00','11:00:00',2,42),(331,'23:00:00','11:00:00',3,42),(332,'23:00:00','11:00:00',4,42),(333,'23:00:00','11:00:00',5,42),(334,'20:00:00','11:00:00',6,42),(335,'20:00:00','11:00:00',7,42),(336,'20:00:00','11:00:00',8,42),(337,'23:00:00','11:00:00',1,43),(338,'23:00:00','11:00:00',2,43),(339,'23:00:00','11:00:00',3,43),(340,'23:00:00','11:00:00',4,43),(341,'23:00:00','11:00:00',5,43),(342,'20:00:00','12:00:00',6,43),(343,'20:00:00','12:00:00',7,43),(344,'20:00:00','12:00:00',8,43);
/*!40000 ALTER TABLE `hours` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-07  9:57:39
