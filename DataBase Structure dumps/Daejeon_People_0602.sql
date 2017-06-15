-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: daejeon_people
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Table structure for table `accommodation_detail_info`
--

DROP TABLE IF EXISTS `accommodation_detail_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `accommodation_detail_info` (
  `content_id` int(11) NOT NULL,
  `info_center` varchar(256) DEFAULT NULL,
  `checkin_time` varchar(256) DEFAULT NULL,
  `checkout_time` varchar(256) DEFAULT NULL,
  `benikia` tinyint(4) DEFAULT NULL,
  `goodstay` tinyint(4) DEFAULT NULL,
  `accomcount` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `id` varchar(256) NOT NULL,
  `password` varchar(256) NOT NULL,
  `email` varchar(256) NOT NULL,
  `phone_number` varchar(256) DEFAULT NULL,
  `name` varchar(256) NOT NULL,
  `register_date` varchar(256) DEFAULT NULL,
  `registration_id` varchar(256) NOT NULL,
  `session_id` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `attractions_basic`
--

DROP TABLE IF EXISTS `attractions_basic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attractions_basic` (
  `content_id` int(11) NOT NULL,
  `content_type_id` int(11) NOT NULL,
  `wish_count` int(11) NOT NULL,
  `title` varchar(256) NOT NULL,
  `cat1` varchar(4) NOT NULL,
  `cat2` varchar(6) NOT NULL,
  `cat3` varchar(10) DEFAULT NULL,
  `address` varchar(128) DEFAULT NULL,
  `mapx` double DEFAULT NULL,
  `mapy` double DEFAULT NULL,
  `views_count` int(11) NOT NULL,
  `image_mini_url` varchar(512) DEFAULT NULL,
  `image_big_url` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `attractions_detail_common`
--

DROP TABLE IF EXISTS `attractions_detail_common`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attractions_detail_common` (
  `content_id` int(11) NOT NULL,
  `content_type_id` int(11) NOT NULL,
  `homepage` varchar(512) DEFAULT NULL,
  `overview` varchar(2048) DEFAULT NULL,
  `tel_name` varchar(256) DEFAULT NULL,
  `tel` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `attractions_images`
--

DROP TABLE IF EXISTS `attractions_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attractions_images` (
  `content_id` int(11) NOT NULL,
  `image` varchar(512) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cultural_facility_detail_info`
--

DROP TABLE IF EXISTS `cultural_facility_detail_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cultural_facility_detail_info` (
  `content_id` int(11) NOT NULL,
  `credit_card` varchar(4) DEFAULT NULL,
  `baby_carriage` varchar(4) DEFAULT NULL,
  `pet` varchar(4) DEFAULT NULL,
  `info_center` varchar(256) DEFAULT NULL,
  `use_time` varchar(256) DEFAULT NULL,
  `rest_date` varchar(256) DEFAULT NULL,
  `use_fee` varchar(2048) DEFAULT NULL,
  `spend_time` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `email_verify_codes`
--

DROP TABLE IF EXISTS `email_verify_codes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `email_verify_codes` (
  `email` varchar(256) NOT NULL,
  `code` varchar(6) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `festival_detail_info`
--

DROP TABLE IF EXISTS `festival_detail_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `festival_detail_info` (
  `content_id` int(11) NOT NULL,
  `start_date` varchar(256) DEFAULT NULL,
  `end_date` varchar(256) DEFAULT NULL,
  `use_fee` varchar(2048) DEFAULT NULL,
  `spend_time` varchar(256) DEFAULT NULL,
  `place` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `friend_list`
--

DROP TABLE IF EXISTS `friend_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `friend_list` (
  `client_id1` varchar(256) NOT NULL,
  `client_id2` varchar(256) NOT NULL,
  PRIMARY KEY (`client_id1`,`client_id2`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `friend_requests`
--

DROP TABLE IF EXISTS `friend_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `friend_requests` (
  `src_id` varchar(256) NOT NULL,
  `dst_id` varchar(256) NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`src_id`,`dst_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `leports_detail_info`
--

DROP TABLE IF EXISTS `leports_detail_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `leports_detail_info` (
  `content_id` int(11) NOT NULL,
  `credit_card` varchar(4) DEFAULT NULL,
  `baby_carriage` varchar(4) DEFAULT NULL,
  `pet` varchar(4) DEFAULT NULL,
  `info_center` varchar(256) DEFAULT NULL,
  `use_time` varchar(256) DEFAULT NULL,
  `rest_date` varchar(256) DEFAULT NULL,
  `use_fee` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `phone_verify_codes`
--

DROP TABLE IF EXISTS `phone_verify_codes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `phone_verify_codes` (
  `phone_number` varchar(256) NOT NULL,
  `code` varchar(6) NOT NULL,
  PRIMARY KEY (`phone_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `restaurant_detail_info`
--

DROP TABLE IF EXISTS `restaurant_detail_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `restaurant_detail_info` (
  `content_id` int(11) NOT NULL,
  `credit_card` varchar(256) DEFAULT NULL,
  `info_center` varchar(256) DEFAULT NULL,
  `use_time` varchar(256) DEFAULT NULL,
  `rest_date` varchar(256) DEFAULT NULL,
  `rep_menu` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `shopping_detail_info`
--

DROP TABLE IF EXISTS `shopping_detail_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shopping_detail_info` (
  `content_id` int(11) NOT NULL,
  `credit_card` varchar(256) DEFAULT NULL,
  `baby_carriage` varchar(256) DEFAULT NULL,
  `pet` varchar(256) DEFAULT NULL,
  `info_center` varchar(256) DEFAULT NULL,
  `use_time` varchar(256) DEFAULT NULL,
  `rest_date` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tour_course_detail_info`
--

DROP TABLE IF EXISTS `tour_course_detail_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tour_course_detail_info` (
  `content_id` int(11) NOT NULL,
  `spend_time` varchar(256) DEFAULT NULL,
  `distance` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tourrism_detail_info`
--

DROP TABLE IF EXISTS `tourrism_detail_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tourrism_detail_info` (
  `content_id` int(11) NOT NULL,
  `credit_card` varchar(4) DEFAULT NULL,
  `baby_carriage` varchar(4) DEFAULT NULL,
  `pet` varchar(4) DEFAULT NULL,
  `info_center` varchar(256) DEFAULT NULL,
  `use_time` varchar(256) DEFAULT NULL,
  `rest_date` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `travel_invites`
--

DROP TABLE IF EXISTS `travel_invites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `travel_invites` (
  `src_id` varchar(256) NOT NULL,
  `dst_id` varchar(256) NOT NULL,
  `topic` varchar(256) NOT NULL,
  `msg` varchar(1024) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`dst_id`,`topic`),
  KEY `fk_topic_idx` (`topic`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `travel_pins`
--

DROP TABLE IF EXISTS `travel_pins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `travel_pins` (
  `topic` varchar(256) NOT NULL,
  `content_id` int(11) NOT NULL,
  `title` varchar(128) DEFAULT NULL,
  `owner` varchar(256) DEFAULT NULL,
  `mapx` double DEFAULT NULL,
  `mapy` double DEFAULT NULL,
  PRIMARY KEY (`topic`,`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `travels`
--

DROP TABLE IF EXISTS `travels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `travels` (
  `topic` varchar(256) NOT NULL,
  `title` varchar(256) NOT NULL,
  `client_id` varchar(256) NOT NULL,
  PRIMARY KEY (`client_id`,`topic`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wish_list`
--

DROP TABLE IF EXISTS `wish_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wish_list` (
  `client_id` varchar(256) NOT NULL,
  `content_id` int(11) NOT NULL,
  PRIMARY KEY (`client_id`,`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-02 14:27:03
