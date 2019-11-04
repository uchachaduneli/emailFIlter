-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.20-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             10.1.0.5464
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for emailfilter
CREATE DATABASE IF NOT EXISTS `emailfilter` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `emailfilter`;

-- Dumping structure for table emailfilter.email
CREATE TABLE IF NOT EXISTS `email` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `from` varchar(50) DEFAULT NULL,
  `to` varchar(50) DEFAULT NULL,
  `subject` text,
  `send_date` timestamp NULL DEFAULT NULL,
  `receive_date` timestamp NULL DEFAULT NULL,
  `content` text,
  `attachments` text,
  `insert_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `uid` varchar(50) DEFAULT NULL,
  `folder_id` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `Index 2` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8;

-- Dumping data for table emailfilter.email: ~0 rows (approximately)
/*!40000 ALTER TABLE `email` DISABLE KEYS */;
/*!40000 ALTER TABLE `email` ENABLE KEYS */;

-- Dumping structure for table emailfilter.email_folders
CREATE TABLE IF NOT EXISTS `email_folders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table emailfilter.email_folders: ~2 rows (approximately)
/*!40000 ALTER TABLE `email_folders` DISABLE KEYS */;
INSERT INTO `email_folders` (`id`, `name`) VALUES
	(1, 'Inbox'),
	(2, 'Sent');
/*!40000 ALTER TABLE `email_folders` ENABLE KEYS */;

-- Dumping structure for table emailfilter.filter
CREATE TABLE IF NOT EXISTS `filter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `filter` text NOT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table emailfilter.filter: ~0 rows (approximately)
/*!40000 ALTER TABLE `filter` DISABLE KEYS */;
/*!40000 ALTER TABLE `filter` ENABLE KEYS */;

-- Dumping structure for table emailfilter.filter_type
CREATE TABLE IF NOT EXISTS `filter_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table emailfilter.filter_type: ~0 rows (approximately)
/*!40000 ALTER TABLE `filter_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `filter_type` ENABLE KEYS */;

-- Dumping structure for table emailfilter.users
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_desc` varchar(50) NOT NULL,
  `user_name` varchar(45) NOT NULL,
  `user_password` varchar(200) NOT NULL,
  `type_id` int(11) NOT NULL,
  `deleted` int(11) NOT NULL DEFAULT '0',
  `email` varchar(50) NOT NULL,
  `email_password` varchar(50) NOT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name` (`user_name`),
  KEY `FK_users_config` (`type_id`),
  CONSTRAINT `FK_users_config` FOREIGN KEY (`type_id`) REFERENCES `user_types` (`user_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- Dumping data for table emailfilter.users: ~2 rows (approximately)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`user_id`, `user_desc`, `user_name`, `user_password`, `type_id`, `deleted`, `email`, `email_password`, `create_date`) VALUES
	(1, 'უჩა ჩადუნელი', 'a', 'c2f0789e6ad28c3f6f85da1fb9828d79', 1, 0, 'emailfiltertest@yandex.ru', '1qaz@WSX1qaz', '2019-11-03 00:58:32'),
	(2, 'o', 'o', '10e21da237a4a1491e769df6f4c3b419', 1, 0, 'o', 'o', '2018-08-26 13:00:13');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

-- Dumping structure for table emailfilter.user_types
CREATE TABLE IF NOT EXISTS `user_types` (
  `user_type_id` int(5) NOT NULL AUTO_INCREMENT,
  `user_type_name` varchar(200) NOT NULL,
  PRIMARY KEY (`user_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- Dumping data for table emailfilter.user_types: ~2 rows (approximately)
/*!40000 ALTER TABLE `user_types` DISABLE KEYS */;
INSERT INTO `user_types` (`user_type_id`, `user_type_name`) VALUES
	(1, 'Administrator'),
	(2, 'operator');
/*!40000 ALTER TABLE `user_types` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
