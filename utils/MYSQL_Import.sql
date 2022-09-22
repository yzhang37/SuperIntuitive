-- MySQL dump 10.13  Distrib 8.0.30, for macos12 (x86_64)
--
-- Host: 127.0.0.1    Database: GradingSystem
-- ------------------------------------------------------
-- Server version	8.0.30

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
-- Table structure for table `assignments`
--

DROP TABLE IF EXISTS `assignments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `assignments` (
  `aid` int NOT NULL AUTO_INCREMENT,
  `name` varchar(150) DEFAULT NULL COMMENT 'Assignment''s Name',
  `score` int DEFAULT NULL,
  `weight` int DEFAULT NULL,
  `course_id` int DEFAULT NULL,
  PRIMARY KEY (`aid`),
  KEY `assignment_courses_cid_fk` (`course_id`),
  CONSTRAINT `assignment_courses_cid_fk` FOREIGN KEY (`course_id`) REFERENCES `courses` (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='List all the assignments in the DB';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assignments`
--

LOCK TABLES `assignments` WRITE;
/*!40000 ALTER TABLE `assignments` DISABLE KEYS */;
INSERT INTO `assignments` VALUES (1,'Snake game',100,20,1),(2,'Minesweeper game',100,25,1),(3,'DOTA2-like game',100,35,1);
/*!40000 ALTER TABLE `assignments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses` (
  `cid` int NOT NULL AUTO_INCREMENT COMMENT 'Course''s Id.',
  `name` varchar(200) DEFAULT NULL COMMENT 'Course''s Name',
  `instructor` varchar(10) DEFAULT NULL COMMENT 'Staff id of the instructor of this course.',
  `semester` varchar(20) DEFAULT NULL,
  `alias` varchar(10) DEFAULT NULL COMMENT 'The short alias name for the course.',
  PRIMARY KEY (`cid`),
  KEY `courses_instructors__fk` (`instructor`),
  CONSTRAINT `courses_instructors__fk` FOREIGN KEY (`instructor`) REFERENCES `staffs` (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='List all the courses in the Academic DB.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES (1,'Principles of OOP in Java','U00000000','Spring 2022','GRS CS 611'),(2,'Intro to CS II','U00000000','Spring 2022','CAS CS 112');
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staffs`
--

DROP TABLE IF EXISTS `staffs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `staffs` (
  `sid` varchar(10) NOT NULL COMMENT 'Staff''s ID (BU 10 digits format).',
  `name` varchar(80) DEFAULT NULL COMMENT 'Staff/Student''s Name',
  `email` varchar(30) DEFAULT NULL COMMENT 'Staff/Student''s Email',
  `isInstructor` tinyint(1) DEFAULT NULL COMMENT 'If a staff is a student or a Instructor.',
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Store all teachers/student members in GradingSystem';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staffs`
--

LOCK TABLES `staffs` WRITE;
/*!40000 ALTER TABLE `staffs` DISABLE KEYS */;
INSERT INTO `staffs` VALUES ('U00000000','Christine Papadakis-Kanaris','cpk@bu.edu',1),('U19657074','Temi Jordan','jordantemi.test@bu.edu',0),('U23042622','Zhenghang Yin','zhyin.test@bu.edu',0),('U38312673','Hanyu Chen','chanyu.test@bu.edu',0),('U46838276','Carly Redon','redonc.test@bu.edu',0),('U58851675','Finn Janus','fjanus.test@bu.edu',0),('U68557294','Chenyu Cao','cyc.test@bu.edu',0),('U84494801','Wiener Joy','jwe.test@bu.edu',0),('U96784462','Francis Chin','fchin.test@bu.edu',0);
/*!40000 ALTER TABLE `staffs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_reg`
--

DROP TABLE IF EXISTS `student_reg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_reg` (
  `sid` varchar(10) DEFAULT NULL,
  `cid` int DEFAULT NULL,
  UNIQUE KEY `student_reg_pk` (`sid`,`cid`),
  KEY `student_reg_courses_cid_fk` (`cid`),
  CONSTRAINT `student_reg_courses_cid_fk` FOREIGN KEY (`cid`) REFERENCES `courses` (`cid`),
  CONSTRAINT `student_reg_staffs_sid_fk` FOREIGN KEY (`sid`) REFERENCES `staffs` (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Store student''s registration in each course';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_reg`
--

LOCK TABLES `student_reg` WRITE;
/*!40000 ALTER TABLE `student_reg` DISABLE KEYS */;
INSERT INTO `student_reg` VALUES ('U38312673',1),('U46838276',1),('U84494801',1);
/*!40000 ALTER TABLE `student_reg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_score`
--

DROP TABLE IF EXISTS `student_score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_score` (
  `sid` varchar(10) NOT NULL,
  `aid` int NOT NULL,
  `score` int DEFAULT NULL COMMENT 'The score of a student''s score on the assignment.',
  PRIMARY KEY (`sid`,`aid`),
  KEY `student_score_assignments_aid_fk` (`aid`),
  CONSTRAINT `student_score_assignments_aid_fk` FOREIGN KEY (`aid`) REFERENCES `assignments` (`aid`),
  CONSTRAINT `student_score_staffs_sid_fk` FOREIGN KEY (`sid`) REFERENCES `staffs` (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Store the student''s score for each assignment';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_score`
--

LOCK TABLES `student_score` WRITE;
/*!40000 ALTER TABLE `student_score` DISABLE KEYS */;
/*!40000 ALTER TABLE `student_score` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-09-22  4:49:55
