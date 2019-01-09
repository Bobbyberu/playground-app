-- MySQL dump 10.13  Distrib 5.7.24, for Linux (x86_64)
--
-- Host: localhost    Database: playground
-- ------------------------------------------------------
-- Server version	5.7.24-0ubuntu0.18.04.1

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
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL,
  `archived` bit(1) NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `mark` double NOT NULL,
  `author_id` int(11) DEFAULT NULL,
  `playground_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh1gtv412u19wcbx22177xbkjp` (`author_id`),
  KEY `FKfyn9hq6uqtsrt3u0ax52ljse` (`playground_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (6,_binary '\0','starfoulah',0,NULL,NULL);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (15),(15),(15),(15),(15),(15),(15),(15),(15),(15);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hours`
--

DROP TABLE IF EXISTS `hours`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hours` (
  `id` int(11) NOT NULL,
  `archived` bit(1) NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `mark` double NOT NULL,
  `opening` datetime DEFAULT NULL,
  `close` datetime DEFAULT NULL,
  `day` varchar(255) DEFAULT NULL,
  `author_id` int(11) DEFAULT NULL,
  `playground_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3s7vild0cdobl005c5x4d6kk` (`author_id`),
  KEY `FKg2wu3555yl800h6vs87md9yco` (`playground_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hours`
--

LOCK TABLES `hours` WRITE;
/*!40000 ALTER TABLE `hours` DISABLE KEYS */;
/*!40000 ALTER TABLE `hours` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `image` (
  `id` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `playground`
--

DROP TABLE IF EXISTS `playground`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `playground` (
  `id` int(11) NOT NULL,
  `average_mark` double NOT NULL,
  `covered` bit(1) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_private` bit(1) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `surface` varchar(255) DEFAULT NULL,
  `image_id` int(11) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK87y49acfg1lslp8fa90ajhgen` (`image_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `playground`
--

LOCK TABLES `playground` WRITE;
/*!40000 ALTER TABLE `playground` DISABLE KEYS */;
INSERT INTO `playground` VALUES (7,0,_binary '\0','Un city stade qu\'il est mashallah',_binary '\0',45.795632,4.2281828,'City Stade de Pouilly-Lès-Feurs',NULL,NULL,'Pré Coton','Pouilly-Lès-Feurs'),(8,0,_binary '\0','Les terrains des charlots de l\'INSA',_binary '\0',45.785478,4.8824848,'Terrains de l\'INSA',NULL,NULL,'Boulevard Niels Bohr','Villeurbanne');
/*!40000 ALTER TABLE `playground` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `playground_players`
--

DROP TABLE IF EXISTS `playground_players`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `playground_players` (
  `playground_id` int(11) NOT NULL,
  `players_id` int(11) NOT NULL,
  PRIMARY KEY (`playground_id`,`players_id`),
  UNIQUE KEY `UK_q0tr41p1g7rbtwacwcyfcax1i` (`players_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `playground_players`
--

LOCK TABLES `playground_players` WRITE;
/*!40000 ALTER TABLE `playground_players` DISABLE KEYS */;
/*!40000 ALTER TABLE `playground_players` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `playground_sports`
--

DROP TABLE IF EXISTS `playground_sports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `playground_sports` (
  `playground_id` int(11) NOT NULL,
  `sports_id` int(11) NOT NULL,
  PRIMARY KEY (`playground_id`,`sports_id`),
  KEY `FKtbch6ao0hu7aivh75xwxro2lv` (`sports_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `playground_sports`
--

LOCK TABLES `playground_sports` WRITE;
/*!40000 ALTER TABLE `playground_sports` DISABLE KEYS */;
INSERT INTO `playground_sports` VALUES (7,1),(7,2),(8,1),(8,3),(13,1),(13,3);
/*!40000 ALTER TABLE `playground_sports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (5,'USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `session`
--

DROP TABLE IF EXISTS `session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `session` (
  `id` int(11) NOT NULL,
  `date` datetime DEFAULT NULL,
  `is_private` bit(1) NOT NULL,
  `maxplayers` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `creator_id` int(11) DEFAULT NULL,
  `playground_id` int(11) DEFAULT NULL,
  `sport_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5dym81rtytk893v25xpjy50d2` (`creator_id`),
  KEY `FKo7kp2qsym8kgwmmnu1q437ity` (`playground_id`),
  KEY `FKgw746x4gkmcj0qqyjnny8ygrr` (`sport_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `session`
--

LOCK TABLES `session` WRITE;
/*!40000 ALTER TABLE `session` DISABLE KEYS */;
/*!40000 ALTER TABLE `session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `session_participants`
--

DROP TABLE IF EXISTS `session_participants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `session_participants` (
  `session_id` int(11) NOT NULL,
  `participants_id` int(11) NOT NULL,
  PRIMARY KEY (`session_id`,`participants_id`),
  UNIQUE KEY `UK_dqj5vk2mhu0i4nlcbt4chu9fx` (`participants_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `session_participants`
--

LOCK TABLES `session_participants` WRITE;
/*!40000 ALTER TABLE `session_participants` DISABLE KEYS */;
/*!40000 ALTER TABLE `session_participants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `signal_comment`
--

DROP TABLE IF EXISTS `signal_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `signal_comment` (
  `id` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `author_id` int(11) DEFAULT NULL,
  `comment_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbiprs4wpllavjovgspng191ny` (`author_id`),
  KEY `FKqxoq1qrnfv7grlg7f56x6vxq3` (`comment_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `signal_comment`
--

LOCK TABLES `signal_comment` WRITE;
/*!40000 ALTER TABLE `signal_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `signal_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `signal_playground`
--

DROP TABLE IF EXISTS `signal_playground`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `signal_playground` (
  `id` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `author_id` int(11) DEFAULT NULL,
  `playground_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9ldwmi2al7akoryiidttild1t` (`author_id`),
  KEY `FKhsltt9cr9w9uijy49ujwn08kf` (`playground_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `signal_playground`
--

LOCK TABLES `signal_playground` WRITE;
/*!40000 ALTER TABLE `signal_playground` DISABLE KEYS */;
/*!40000 ALTER TABLE `signal_playground` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sport`
--

DROP TABLE IF EXISTS `sport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sport` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `symbol` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sport`
--

LOCK TABLES `sport` WRITE;
/*!40000 ALTER TABLE `sport` DISABLE KEYS */;
INSERT INTO `sport` VALUES (1,'Basketball',NULL),(2,'Football',NULL),(3,'Beach Volley',NULL);
/*!40000 ALTER TABLE `sport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `archived` bit(1) NOT NULL,
  `banned` bit(1) NOT NULL,
  `birth_date` datetime DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `avatar_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK463dlh5j2p2mnn5jxcmtjre0e` (`avatar_id`),
  KEY `FKn82ha3ccdebhokx3a8fgdqeyy` (`role_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (4,_binary '\0',_binary '\0',NULL,'Châtilly-les-Besouls',_binary '\0','jean.michel@testeur.fr','$2a$10$G5MNS.xAhkn/7nYtnRmTCuXNQsLl909yXbupOz1/6c9hH8EWOkcdS','JeanMichelTesteur',NULL,5),(14,_binary '\0',_binary '\0',NULL,NULL,_binary '\0',NULL,'$2a$10$nIiJklnlBqN/JOnoixJ87eLgFMXavI.ocvkl11lH9Vo8M2.FRCxsC','admin',NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_favourite_playgrounds`
--

DROP TABLE IF EXISTS `user_favourite_playgrounds`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_favourite_playgrounds` (
  `user_id` int(11) NOT NULL,
  `favourite_playgrounds_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`favourite_playgrounds_id`),
  UNIQUE KEY `UK_rwygxij5mv4l2tyypmt95irsw` (`favourite_playgrounds_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_favourite_playgrounds`
--

LOCK TABLES `user_favourite_playgrounds` WRITE;
/*!40000 ALTER TABLE `user_favourite_playgrounds` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_favourite_playgrounds` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_favourite_sports`
--

DROP TABLE IF EXISTS `user_favourite_sports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_favourite_sports` (
  `user_id` int(11) NOT NULL,
  `favourite_sports_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`favourite_sports_id`),
  UNIQUE KEY `UK_6v15a8g0dm070eilo3o81gx1v` (`favourite_sports_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_favourite_sports`
--

LOCK TABLES `user_favourite_sports` WRITE;
/*!40000 ALTER TABLE `user_favourite_sports` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_favourite_sports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_friends`
--

DROP TABLE IF EXISTS `user_friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_friends` (
  `user_id` int(11) NOT NULL,
  `friends_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`friends_id`),
  UNIQUE KEY `UK_bbrnh12js1l8culgfpipyat29` (`friends_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_friends`
--

LOCK TABLES `user_friends` WRITE;
/*!40000 ALTER TABLE `user_friends` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_friends` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-09 15:39:38
