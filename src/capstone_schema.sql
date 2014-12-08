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
(1,NULL,1,'Please leave the building immediately and assemble in your designated evacuation area.'),
(2,NULL,2,'Turn off electrical devices to lessen the electrical load on circuits once the power is restored. Contact the Public Safety Office at 650-508-1860 for more information.'),
(3,NULL,3,'If you have any information about the missing person, please contact Public Safety at 650-504-0656, and dial 911 for emergencies.'),
(4,NULL,4,'Please leave the building immediately and assemble in your designated evacuation area.'),
(5,NULL,5,'Please leave the building immediately and assemble in your designated evacuation area.'),
(6,NULL,6,'Please remain safely indoors until the weather has passed.'),
(7,NULL,7,'Please leave the building immediately and assemble in your designated evacuation area.'),
(8,NULL,8,'If you are able to leave the area or the building, do so without placing yourself in
danger.'),
(9,NULL,9,'Contact the Public Safety Office at 650-508-3502 for more information.'),
(10,NULL,10,'If you know where the Shooter is and can get out safely, then do so as quickly as possible. Otherwise, hide out in the nearest room or office. Close, lock and barricade the door. Cover windows, turn off the lights, silence all noise producing sources, and remain silent as if no one is in the room.'),
(11,NULL,11,'Please notify Public Safety at 650-504-0656 if you notice someone threatening to hurt or kill themselves, or are not acting as their normal self. The Suicide Prevention hotline is 650-579-0350.'),
(12,NULL,12,'For counseling services, please call 650-508-3714.'),
(13,NULL,13,'For emergencies dial 911 or contact Public Safety at 650-504-0656.'),
(14,NULL,14,'For emergencies dial 911 or contact Public Safety at 650-504-0656.'),
(15,NULL,15,'For emergencies dial 911 or contact Public Safety at 650-504-0656.'),
(16,NULL,16,'For emergencies dial 911 or contact Public Safety at 650-504-0656. For counseling services, please call 650-508-3714.'),
(17,NULL,17,'Please leave the building immediately and assemble in your designated evacuation area. Seal off the leak to prevent contamination if possible.'),
(18,NULL,18,'Report individuals with mental health issues to Public Safety at 650-504-0656.');
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
(1,'Reported'),
(2,'In Progress'),
(3,'Responders on Scene'),
(4,'Complete');
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
(1,'St. Mary\'s Hall','1500 Ralston Avenue','Belmont','CA','94002',NULL,NULL,NULL),
(2,'Campus Center','1500 Ralston Avenue','Belmont','CA','94002',NULL,NULL,NULL),
(3,'Gellert Library','1500 Ralston Avenue','Belmont','CA','94002',NULL,NULL,NULL),
(4,'Gleason Gym','1500 Ralston Avenue','Belmont','CA','94002',NULL,NULL,NULL),
(5,'New Hall','1500 Ralston Avenue','Belmont','CA','94002',NULL,NULL,NULL),
(6,'Chapel','1500 Ralston Avenue','Belmont','CA','94002',NULL,NULL,NULL),
(7,'St. Joseph\'s Hall','1500 Ralston Avenue','Belmont','CA','94002',NULL,NULL,NULL),
(8,'Dining Hall','1500 Ralston Avenue','Belmont','CA','94002',NULL,NULL,NULL),
(9,'Campus Life','1500 Ralston Avenue','Belmont','CA','94002',NULL,NULL,NULL),
(10,'NDNU Theatre','1500 Ralston Avenue','Belmont','CA','94002',NULL,NULL,NULL);
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
  `id_number` int(11),
  `email` varchar(256) NOT NULL,
  `username` varchar(256),
  `password` varchar(4000),
  `phone` varchar(24) NOT NULL,
  `type_id` int(1) NOT NULL,
  `active` int(1) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `emergency_contact_name` varchar(256),
  `emergency_contact_phone` varchar(24),
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
(1,'Scott Mantegani',111111,'smantegani@student.ndnu.edu','smantegani','e08b22aa9529bc85b58a0542bdfd38d6d99309fca5e9164eceacc554963d8678','111-222-3333',2,1,'2013-02-28 14:59:32','John Doe 1','111-222-3333',''),
(2,'Bozena Camacho',222222,'bcamacho1@student.ndnu.edu','bcamacho1','e5afeeee6e8a86dec3d4aaa87d412ab3c39adb400c60535e784050a1ccc45812','111-222-3333',1,1,'2014-10-03 06:48:00','John Doe 2','111-222-3333',''),
(3,'Angelo Rivera',333333,'arivera@student.ndnu.edu','arivera','e28ca6b4a399dde7778603497fac13d32892a70654ac0b72ad65f49addf5e6d6','111-222-3333',2,1,'2014-11-27 04:06:25','Jane Doe 1','111-222-3333',''),
(4,'Michael Tempalski',444444,'mtempalski@student.ndnu.edu','mtempalski','373cbadc33e4a2cb7dcb7be0cc297d6c852011773ba69d8603613f22ea4ba9e2','111-222-3333',1,1,'2014-11-27 04:26:58','Jane Doe 2','111-222-3333','');
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
(2,'First Responder'),
(3,'Student'),
(4,'Faculty');
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
