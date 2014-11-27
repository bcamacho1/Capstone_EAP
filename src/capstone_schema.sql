-- MySQL dump 10.13  Distrib 5.6.10, for osx10.6 (i386)
--
-- Host: localhost    Database: capstone
-- ------------------------------------------------------
-- Server version	5.6.10

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `capstone`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `capstone` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `capstone`;

--
-- Table structure for table `emergency`
--

DROP TABLE IF EXISTS `emergency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `emergency` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `location_id` int(11) NOT NULL,
  `type_id` int(11) NOT NULL,
  `status_id` int(11) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `location_id` (`location_id`),
  KEY `type_id` (`type_id`),
  KEY `status_id` (`status_id`),
  CONSTRAINT `emergency_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `emergency_ibfk_2` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`),
  CONSTRAINT `emergency_ibfk_3` FOREIGN KEY (`type_id`) REFERENCES `emergency_type` (`id`),
  CONSTRAINT `emergency_ibfk_4` FOREIGN KEY (`status_id`) REFERENCES `emergency_status` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emergency`
--

LOCK TABLES `emergency` WRITE;
/*!40000 ALTER TABLE `emergency` DISABLE KEYS */;
INSERT INTO `emergency` VALUES 
(1,1,1,1,2,'2013-02-28 15:09:01',NULL),
(2,3,7,7,3,'2013-02-28 15:09:01',NULL),
(3,3,8,1,1,'2014-09-03 06:04:35',NULL);
/*!40000 ALTER TABLE `emergency` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emergency_alert_log`
--

DROP TABLE IF EXISTS `emergency_alert_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `emergency_alert_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `emergency_id` int(11) NOT NULL,
  `emergency_message_id` int(11) DEFAULT NULL,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `sent` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `emergency_id` (`emergency_id`),
  KEY `emergency_message_id` (`emergency_message_id`),
  CONSTRAINT `emergency_alert_log_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `emergency_alert_log_ibfk_2` FOREIGN KEY (`emergency_id`) REFERENCES `emergency` (`id`),
  CONSTRAINT `emergency_alert_log_ibfk_3` FOREIGN KEY (`emergency_message_id`) REFERENCES `emergency_message` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emergency_alert_log`
--

LOCK TABLES `emergency_alert_log` WRITE;
/*!40000 ALTER TABLE `emergency_alert_log` DISABLE KEYS */;
INSERT INTO `emergency_alert_log` VALUES 
(1,1,1,NULL,'2014-11-20 08:01:32',1);
/*!40000 ALTER TABLE `emergency_alert_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emergency_message`
--

DROP TABLE IF EXISTS `emergency_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `emergency_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `emergency_type_id` int(11) DEFAULT NULL,
  `message` varchar(4000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `emergency_type_id` (`emergency_type_id`),
  CONSTRAINT `emergency_message_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `emergency_message_ibfk_2` FOREIGN KEY (`emergency_type_id`) REFERENCES `emergency_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emergency_message`
--

LOCK TABLES `emergency_message` WRITE;
/*!40000 ALTER TABLE `emergency_message` DISABLE KEYS */;
INSERT INTO `emergency_message` VALUES 
(1,NULL,1,'Please stay calm and evacuate immediately.'),
(2,1,2,'Emergency message 2'),
(3,2,2,'Emergency message 3'),
(4,3,3,'Sending message as a first responder');
/*!40000 ALTER TABLE `emergency_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emergency_status`
--

DROP TABLE IF EXISTS `emergency_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `emergency_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emergency_status`
--

LOCK TABLES `emergency_status` WRITE;
/*!40000 ALTER TABLE `emergency_status` DISABLE KEYS */;
INSERT INTO `emergency_status` VALUES 
(1,'New'),
(2,'Reported'),
(3,'In Progress'),
(4,'Responders on Scene'),
(5,'Complete');
/*!40000 ALTER TABLE `emergency_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emergency_type`
--

DROP TABLE IF EXISTS `emergency_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `emergency_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  `category` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emergency_type`
--

LOCK TABLES `emergency_type` WRITE;
/*!40000 ALTER TABLE `emergency_type` DISABLE KEYS */;
INSERT INTO `emergency_type` VALUES 
(1,'Fire',2),
(2,'Power Outage',2),
(3,'Missing Persons',2),
(4,'Structural Disaster',2),
(5,'Gas Leak',2),
(6,'Inclement Weather',2),
(7,'Bomb Threat',2),
(8,'Hostage Crisis',2),
(9,'Campus Wide Safety Alert',2),
(10,'Active Shooter',2),
(11,'Attempted Suicide',1),
(12,'Death of a Student, Faculty, or Staff Member',1),
(13,'Accident or Serious Illness',1),
(14,'Major Crime',1),
(15,'Arrest on a Major Charge',1),
(16,'Family Emergency',1),
(17,'Hazardous Chemical Spills',1),
(18,'Mental Health Crisis',1);
/*!40000 ALTER TABLE `emergency_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evacuation`
--

DROP TABLE IF EXISTS `evacuation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evacuation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `area` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evacuation`
--

LOCK TABLES `evacuation` WRITE;
/*!40000 ALTER TABLE `evacuation` DISABLE KEYS */;
/*!40000 ALTER TABLE `evacuation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  `address` varchar(1024) NOT NULL,
  `city` varchar(1024) NOT NULL,
  `state` varchar(64) NOT NULL,
  `zipcode` varchar(64) NOT NULL,
  `evacuation_area` int(11) DEFAULT NULL,
  `latitude` varchar(64) DEFAULT NULL,
  `longitude` varchar(64) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES 
(1,'St. Mary\'s Hall','1500 Ralston Avenue','Belmont','CA','94002',NULL,NULL,NULL,NULL),
(2,'Campus Center','1500 Ralston Avenue','Belmont','CA','94002',NULL,NULL,NULL,NULL),
(3,'Gellert Library','1500 Ralston Avenue','Belmont','CA','94002',NULL,NULL,NULL,NULL),
(4,'Gleason Gym','1500 Ralston Avenue','Belmont','CA','94002',NULL,NULL,NULL,NULL),
(5,'New Hall','1500 Ralston Avenue','Belmont','CA','94002',NULL,NULL,NULL,NULL),
(6,'Chapel','1500 Ralston Avenue','Belmont','CA','94002',NULL,NULL,NULL,NULL),
(7,'St. Joseph\'s Hall','1500 Ralston Avenue','Belmont','CA','94002',NULL,NULL,NULL,NULL),
(8,'Dining Hall','1500 Ralston Avenue','Belmont','CA','94002',NULL,NULL,NULL,NULL),
(9,'Campus Life','1500 Ralston Avenue','Belmont','CA','94002',NULL,NULL,NULL,NULL),
(10,'NDNU Theatre','1500 Ralston Avenue','Belmont','CA','94002',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  `email` varchar(256) NOT NULL,
  `username` varchar(256) NOT NULL,
  `password` varchar(4000) NOT NULL,
  `phone` varchar(24) NOT NULL,
  `type_id` int(1) NOT NULL,
  `active` int(1) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `username` (`username`),
  KEY `type_id` (`type_id`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `user_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=latin1 DEFAULT COLLATE latin1_general_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES 
(1,'Scott Mantegani','smantegani@student.ndnu.edu','smantegani','54921001a1b93a0e1197f95ee0adb3795162d6340e3de6d988bc11b364ea67bc','123-4567',2,1,'2013-02-28 14:59:32',''),
(2,'Scott Mantegani','admin2@student','admin','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918','111-1111',1,1,'2014-10-03 06:48:00',''),
(3,'Admin','Administrator@student.ndnu.edu','Administrator','e7d3e769f3f593dadcb8634cc5b09fc90dd3a61c4a06a79cb0923662fe6fae6b','111-222-3333',2,1,'2014-11-27 04:06:25',''),
(4,'Scott','scott@student.ndnu.edu','scott','12a303c224c250d07c81691de6e0fd74699ce6bd78c234057de70413a58457cf','111-222-3333',1,1,'2014-11-27 04:26:58',''),
(5,'Admin','Admin@student.ndnu.edu','Admin','c1c224b03cd9bc7b6a86d77f5dace40191766c485cd55dc48caf9ac873335d6f','111-222-3333',1,1,'2014-11-27 05:35:59','');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_type`
--

DROP TABLE IF EXISTS `user_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_type`
--

LOCK TABLES `user_type` WRITE;
/*!40000 ALTER TABLE `user_type` DISABLE KEYS */;
INSERT INTO `user_type` VALUES 
(1,'Admin'),
(2,'First Responder');
/*!40000 ALTER TABLE `user_type` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-11-26 21:47:33
