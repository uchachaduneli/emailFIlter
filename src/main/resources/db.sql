-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.20-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for emailfilter
CREATE DATABASE IF NOT EXISTS `emailfilter` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `emailfilter`;

-- Dumping structure for table emailfilter.email
CREATE TABLE IF NOT EXISTS `email`
(
    `id`           int(11)   NOT NULL AUTO_INCREMENT,
    `user_id`      int(11)   NOT NULL,
    `from`         varchar(50)        DEFAULT NULL,
    `to`           varchar(50)        DEFAULT NULL,
    `subject`      text,
    `content`      text,
    `insert_date`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `uid`          varchar(50)        DEFAULT NULL,
    `folder_id`    int(11)   NOT NULL DEFAULT '1',
    `sender_ip`    varchar(20)        DEFAULT NULL,
    `send_date`    timestamp NULL     DEFAULT NULL,
    `receive_date` timestamp NULL     DEFAULT NULL,
    `attachments`  text,
    PRIMARY KEY (`id`),
    KEY `Index 2` (`user_id`) USING BTREE,
    KEY `FK_email_email_folders` (`folder_id`),
    CONSTRAINT `FK_email_email_folders` FOREIGN KEY (`folder_id`) REFERENCES `email_folders` (`id`),
    CONSTRAINT `FK_email_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 16
  DEFAULT CHARSET = utf8;

-- Dumping data for table emailfilter.email: ~3 rows (approximately)
/*!40000 ALTER TABLE `email`
    DISABLE KEYS */;
INSERT INTO `email` (`id`, `user_id`, `from`, `to`, `subject`, `content`, `insert_date`, `uid`, `folder_id`, `sender_ip`, `send_date`, `receive_date`,
                     `attachments`)
VALUES (11, 2, 'super', 'emailfilter19@gmail.com', 'attachs', '\n\r\n', '2019-11-16 00:42:33', '', 1, '', '2019-11-15 23:15:39',
        '2019-11-15 23:15:39', 'pdf-test.pdf Test_image123.jpg'),
       (12, 1, 'admin', 'emailfilter19@gmail.com', 'asd', '\n\r\n', '2019-11-16 00:42:34', '', 1, '', '2019-11-15 23:18:31', '2019-11-15 23:18:31',
        '_pdf-test.pdf'),
       (13, 3, 'operator1', 'emailfilter19@gmail.com', 'asd', '\n\r\n', '2019-11-16 00:42:34', '', 1, '', '2019-11-15 23:18:31',
        '2019-11-15 23:18:31', '_pdf-test.pdf'),
       (14, 32, 'operator2', 'emailfilter19@gmail.com', 'asd', '\n\r\n', '2019-11-16 00:42:34', '', 1, '', '2019-11-15 23:18:31',
        '2019-11-15 23:18:31', '_pdf-test.pdf'),
       (15, 3, 'operator1', 'emailfilter19@gmail.com', 'asd2', '\n\r\n', '2019-11-16 00:42:34', '', 1, '', '2019-11-15 23:18:31',
        '2019-11-15 23:18:31', '_pdf-test.pdf');
/*!40000 ALTER TABLE `email`
    ENABLE KEYS */;

-- Dumping structure for table emailfilter.email_folders
CREATE TABLE IF NOT EXISTS `email_folders`
(
    `id`   int(11)     NOT NULL AUTO_INCREMENT,
    `name` varchar(50) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8;

-- Dumping data for table emailfilter.email_folders: ~2 rows (approximately)
/*!40000 ALTER TABLE `email_folders`
    DISABLE KEYS */;
INSERT INTO `email_folders` (`id`, `name`)
VALUES (1, 'Inbox'),
       (2, 'My Spam');
/*!40000 ALTER TABLE `email_folders`
    ENABLE KEYS */;

-- Dumping structure for table emailfilter.filter
CREATE TABLE IF NOT EXISTS `filter`
(
    `id`          int(11)   NOT NULL AUTO_INCREMENT,
    `desc`        text      NOT NULL,
    `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `type_id`     int(11)   NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_filter_filter_type` (`type_id`),
    CONSTRAINT `FK_filter_filter_type` FOREIGN KEY (`type_id`) REFERENCES `filter_type` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = utf8;

-- Dumping data for table emailfilter.filter: ~2 rows (approximately)
/*!40000 ALTER TABLE `filter`
    DISABLE KEYS */;
INSERT INTO `filter` (`id`, `desc`, `create_date`, `update_date`, `type_id`)
VALUES (4, 'you won prize', '2019-11-12 00:45:12', '2019-11-12 00:45:12', 1),
       (5, 'you have been set', '2019-11-12 00:45:26', '2019-11-12 00:45:26', 1);
/*!40000 ALTER TABLE `filter`
    ENABLE KEYS */;

-- Dumping structure for table emailfilter.filter_type
CREATE TABLE IF NOT EXISTS `filter_type`
(
    `id`   int(11)     NOT NULL AUTO_INCREMENT,
    `name` varchar(50) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8;

-- Dumping data for table emailfilter.filter_type: ~2 rows (approximately)
/*!40000 ALTER TABLE `filter_type`
    DISABLE KEYS */;
INSERT INTO `filter_type` (`id`, `name`)
VALUES (1, 'By Content'),
       (2, 'By Sender IP');
/*!40000 ALTER TABLE `filter_type`
    ENABLE KEYS */;

-- Dumping structure for table emailfilter.users
CREATE TABLE IF NOT EXISTS `users`
(
    `user_id`        int(11)      NOT NULL AUTO_INCREMENT,
    `user_desc`      varchar(50)  NOT NULL,
    `user_name`      varchar(45)  NOT NULL,
    `user_password`  varchar(200) NOT NULL,
    `temp_password`  varchar(200)          DEFAULT NULL,
    `type_id`        int(11)      NOT NULL,
    `deleted`        int(11)      NOT NULL DEFAULT '0',
    `email`          varchar(50)  NOT NULL,
    `email_password` varchar(50)  NOT NULL,
    `create_date`    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `user_name` (`user_name`),
    KEY `FK_users_config` (`type_id`),
    CONSTRAINT `FK_users_config` FOREIGN KEY (`type_id`) REFERENCES `user_types` (`user_type_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 34
  DEFAULT CHARSET = utf8;

-- Dumping data for table emailfilter.users: ~0 rows (approximately)
/*!40000 ALTER TABLE `users`
    DISABLE KEYS */;
INSERT INTO `users` (`user_id`, `user_desc`, `user_name`, `user_password`, `temp_password`, `type_id`, `deleted`, `email`, `email_password`,
                     `create_date`)
VALUES (1, 'Admin', 'a', 'c2f0789e6ad28c3f6f85da1fb9828d79', NULL, 1, 0, '', '', '2019-11-18 12:28:59'),
       (2, 'SuperAdmin', 's', 'c2f0789e6ad28c3f6f85da1fb9828d79', NULL, 3, 0, 'emailfilter19@gmail.com', '123!@#asdASD', '2019-11-17 00:12:19'),
       (3, 'operator', 'o', 'c2f0789e6ad28c3f6f85da1fb9828d79', NULL, 2, 0, '', '', '2019-11-17 00:11:46'),
       (32, 'operator2', 'o2', 'c2f0789e6ad28c3f6f85da1fb9828d79', NULL, 2, 0, '', '', '2019-11-17 00:11:47'),
       (33, 'Admin2', 'a2', 'c2f0789e6ad28c3f6f85da1fb9828d79', NULL, 1, 0, '', '', '2019-11-18 12:29:04');
/*!40000 ALTER TABLE `users`
    ENABLE KEYS */;

-- Dumping structure for table emailfilter.user_types
CREATE TABLE IF NOT EXISTS `user_types`
(
    `user_type_id`   int(5)       NOT NULL AUTO_INCREMENT,
    `user_type_name` varchar(200) NOT NULL,
    PRIMARY KEY (`user_type_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8;

-- Dumping data for table emailfilter.user_types: ~0 rows (approximately)
/*!40000 ALTER TABLE `user_types`
    DISABLE KEYS */;
INSERT INTO `user_types` (`user_type_id`, `user_type_name`)
VALUES (1, 'Admin'),
       (2, 'user'),
       (3, 'SuperAdmin');
/*!40000 ALTER TABLE `user_types`
    ENABLE KEYS */;

/*!40101 SET SQL_MODE = IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS = IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
