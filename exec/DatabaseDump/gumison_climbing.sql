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
-- Table structure for table `climbing`
--

DROP TABLE IF EXISTS `climbing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `climbing` (
  `climbing_id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `climbing_name` varchar(255) NOT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `img_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`climbing_id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `climbing`
--

LOCK TABLES `climbing` WRITE;
/*!40000 ALTER TABLE `climbing` DISABLE KEYS */;
INSERT INTO `climbing` VALUES (1,'서울 성동구 성수일로 19 지하2층','서울숲 클라이밍','010-4372-1116','seoul_forest.png'),(2,'서울 마포구 양화로 125 경남관광빌딩','더클라임 홍대점','02-332-5014','the_climb.png'),(3,'서울 강서구 마곡동로 62 마곡사이언스타워 7층','더클라임 마곡점','02-2668-5014','the_climb.png'),(4,'경기 고양시 일산동구 중앙로 1160 5층 더클라임','더클라임 일산점','031-905-5014','the_climb.png'),(5,'서울 관악구 남부순환로 1801 지하1층','더클라임 서울대점','02-888-8821','the_climb.png'),(6,'서울 강남구 남부순환로 2615 지하1층	','더클라임 양재점','02-576-8821','the_climb.png'),(7,'서울 영등포구 영등포로33길 14','알레 클라이밍','070-8862-2018',NULL),(8,'서울 종로구 창경궁로34길 18-5 토가빌딩 B2','알레 클라이밍 혜화','070-8886-2018',NULL),(9,'서울 종로구 삼일대로 386','더코아 클라이밍 센터','02-2269-5659','the_core.png'),(10,'서울 성동구 성수일로12길 34 3층','훅클라이밍 성수점','02-6082-4739','hook.png'),(11,'서울 성동구 고산자로6길 40 2층','훅클라이밍 왕십리점','02-2282-4739','hook.png'),(12,'서울 광진구 천호대로 611','버티고 클라이밍짐','02-446-6111','vertigo.png'),(13,'서울 동대문구 장한로2길 63 호정빌딩 2F','V10 클라이밍 장한평점','0507-1313-4171','v10.png'),(14,'서울 강동구 올림픽로 588 귀뚜라미빌딩 B1','V10 클라이밍 천호본점','02-482-2015','v10.png'),(15,'경기 하남시 미사강변동로 81 13층','V10 클라이밍 하남미사점','031-794-5045','v10.png'),(16,'서울 동대문구 난계로30길 23','산타클라이밍','0507-1425-5157','santa.png'),(17,'서울 강서구 공항대로 284 동서빌딩 4층','강서클라이밍 센터','02-3662-0138','gangseo.png'),(18,'서울 마포구 월드컵북로 396 누리꿈스퀘어 비즈니스타워 B1001호','코알라클라이밍','070-7733-4768','koala.png'),(19,'서울 금천구 디지털로 121 20층 2009호','어거스트 클라이밍','02-3016-7964',NULL),(20,'서울특별시 관악구 봉천로 462 은일빌딩 7층','정지현클라이밍짐','02-883-5014','jungjihyeon.png'),(21,'서울특별시 서초구 서초대로26길 3','게이트원 클라이밍','070-8883-8850','gate1.png'),(22,'서울특별시 강남구 논현로 563 언주타워 지하 1층','비블럭 클라이밍 강남점','02-547-5939','b_bloc.png'),(23,'인천 연수구 아트센터대로 149','비블럭 클라이밍 송도점','032-723-6430','b_bloc.png'),(24,'서울특별시 강남구 압구정로28길 11 B1','타잔101 클라이밍 압구정점','02-511-1013','tarzan101.png'),(25,'서울특별시 강남구 강남대로118길 12 B2','락트리클라이밍 강남','070-7802-8889','rocktree.png'),(26,'서울 노원구 동일로241길 53 202~209호','볼더 클라이밍 짐','070-4696-3270','boulder.png'),(27,'서울 노원구 상계로5길 32 7층','드림캐처클라이밍짐','02-6080-1229','dream_catcher.png'),(28,'서울 노원구 동일로 1393 지하1층','비숍 클라이밍','02-936-0515','bshop.png'),(29,'서울 은평구 통일로 1020 은평뉴타운 신한헤센스마트 M203호','맑음클라임','0507-1418-8377',NULL),(30,'서울 마포구 홍익로 25','볼더프렌즈 클라이밍','02-332-7738','boulder_friends.png'),(31,'서울특별시 강남구 선릉로 324 SH타워 지하3층','클라이밍파크 한티점','050-71340-4662','climbing_park.png'),(32,'서울특별시 강남구 강남대로 468 지하3층','클라이밍 파크','02-555-4662','climbing_park.png'),(33,'서울 송파구 법원로8길 9 청림타워 2층','닷클라이밍짐','070-7700-0120','dot.png'),(34,'서울특별시 강남구 테헤란로30길 49 지하1층','역삼 클라이밍 랩','010-9115-6911','yeoksam.png'),(35,'서울특별시 영등포구 양평로28마길 7 3층','서울 볼더스 클라이밍 컴퍼니','02-2632-4478',NULL),(36,'서울 영등포구 도림로 423','더 플라스틱 클라이밍','02-6406-8890','the_plastik.png'),(37,'서울 영등포구 선유로53길 6','서종국 클라이밍','02-2676-1932',NULL),(38,'서울특별시 송파구 백제고분로 224 창대빌딩 지하1층','손상원 클라이밍짐 잠실점','02-423-5015',NULL),(39,'서울 송파구 위례광장로 188 아이온스퀘어 12층','에어즈락 클라이밍','02-400-3845','eyers_rock.png'),(40,'서울 강동구 천호대로 1178 알레소 빌딩 지하','강동클라이밍짐','0507-1438-8849','gangdong.png'),(41,'서울특별시 구로구 가마산로27길 14','에이스클라이밍센터','02-836-8848','ace.png'),(42,'서울특별시 송파구 백제고분로 435 예스빌딩 B1','락스타클라이밍','02-418-8848','rockstar.png'),(43,'서울특별시 서초구 등산로 84','비스트클라이밍짐','02-2058-1423','beast.png');
/*!40000 ALTER TABLE `climbing` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-07  9:57:42
