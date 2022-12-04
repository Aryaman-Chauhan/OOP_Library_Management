CREATE DATABASE  IF NOT EXISTS `bits_library` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `bits_library`;
-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: bits_library
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `bname` varchar(100) DEFAULT NULL,
  `isbn` varchar(13) NOT NULL,
  `bauth` varchar(100) DEFAULT NULL,
  `bgenre` varchar(100) DEFAULT NULL,
  `bookissue` int DEFAULT '1',
  `publish` varchar(40) DEFAULT NULL,
  `duedate` date DEFAULT NULL,
  `issueno` int DEFAULT (0),
  KEY `bookissue` (`bookissue`),
  CONSTRAINT `book_ibfk_1` FOREIGN KEY (`bookissue`) REFERENCES `student` (`idno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES ('Digital Design','9788131794746','M. Morris Mano','Educational, Electronics',1,'Pearson',NULL,0),('Discrete Mathematics for Computer Scientists and Mathematicians','9789332550490','Joe L. Mott, Abraham Kandel','Educational, Computer Science',1,'Pearson',NULL,0),('Introduction to Quantum Mechanics','9788177582307','David J. Griffiths','Educational, Physics, Science',1,'Pearson',NULL,0),('Introduction to Solid State Physics','9788126535187','Charles Kittel','Educational, Physics, Science',1,'Wiley',NULL,0),('Oliver Twist','9780192833396','Charles Dickens','Novel, Fiction, Social',1,'Oxford World Classics',NULL,0),('Mein Kampf','9788172241643','Adolf Hitler','Non-Fiction, Autobiography, History',1,'Jaico Book House',NULL,0),('DISCRETE MATHEMATICS AND ITS APPLICATIONS','9780073383095','Kenneth H. Rosen','Educational, Computer Science',1,'McGraw Hill',NULL,0),('Object Oriented Design & Patterns','9780471744870','Cay Horstmann','Educational, Computer Science',1,'John Wiley & Sons, Inc.',NULL,0),('Java-The Complete Reference','9781259589348','Herbert Schildt','Educational, Computer Science',1,'McGraw-Hill',NULL,0),('Java Design Patterns: A Tutorial','0201485397','James W. Cooper','Educational, Computer Science',1,'Addison Wesley',NULL,0),('A Christmas Carol','9780706247831','Charles Dickens','Novel, Children, Fiction',1,'Chapman & Hall',NULL,0);
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `idno` int NOT NULL AUTO_INCREMENT,
  `sid` varchar(20) DEFAULT NULL,
  `sname` varchar(40) DEFAULT NULL,
  `pwd` varchar(40) DEFAULT NULL,
  `regdate` date DEFAULT NULL,
  `dues` double DEFAULT (0.0),
  PRIMARY KEY (`idno`),
  UNIQUE KEY `sid` (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'ADMIN','Librarian','admin','2022-12-01',0),(2,'F2021A7PS2539P','Shardul Shingare','12345','2022-12-02',0),(3,'F2020B5A72006P','Aryaman Chauhan','12345','2022-12-03',0),(4,'F2021A7PS0468P','Shubham Gupta','12345','2022-12-03',0),(5,'F2021A7PS2534P','Radhey Kanade','12345','2022-12-03',0),(6,'F2021A7PS0661P','YASH PANDEY','12345','2022-12-03',0),(7,'F2021A7PS2695P','VINAMRA MAHAJAN','12345','2022-12-03',0),(8,'F2021A7PS2216P','ATHARVA RADAYE','12345','2022-12-03',0),(9,'F2021A7PS2432P','SHREYAS KUMAR','12345','2022-12-03',0),(10,'F2021A7PS0011P','ARUSH DAYAL','12345','2022-12-03',0);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'bits_library'
--

--
-- Dumping routines for database 'bits_library'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-04 17:56:26
