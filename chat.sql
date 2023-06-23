/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : chat

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 13/11/2022 20:13:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for agree
-- ----------------------------
DROP TABLE IF EXISTS `agree`;
CREATE TABLE `agree` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dynamicId` int NOT NULL,
  `userId` int NOT NULL,
  `state` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `agreeUserId` (`userId`),
  KEY `agreeDynamicId` (`dynamicId`),
  CONSTRAINT `agreeDynamicId` FOREIGN KEY (`dynamicId`) REFERENCES `dynamic` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `agreeUserId` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for collection
-- ----------------------------
DROP TABLE IF EXISTS `collection`;
CREATE TABLE `collection` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dynamicId` int NOT NULL,
  `userId` int NOT NULL,
  `state` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `collectiondynamicId` (`dynamicId`),
  KEY `collectionUserId` (`userId`),
  CONSTRAINT `collection_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`),
  CONSTRAINT `collectionDynamicId` FOREIGN KEY (`dynamicId`) REFERENCES `dynamic` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for dynamic
-- ----------------------------
DROP TABLE IF EXISTS `dynamic`;
CREATE TABLE `dynamic` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userId` int NOT NULL,
  `content` text,
  `time` datetime NOT NULL,
  `agreeCount` int DEFAULT '0',
  `collectionCount` int DEFAULT '0',
  `state` int DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  CONSTRAINT `userId` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for friend
-- ----------------------------
DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userId` int NOT NULL,
  `friendId` int NOT NULL,
  `state` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `friendUserId` (`userId`),
  KEY `friendId` (`friendId`),
  CONSTRAINT `friendId` FOREIGN KEY (`friendId`) REFERENCES `user` (`id`),
  CONSTRAINT `friendUserId` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for friendRequest
-- ----------------------------
DROP TABLE IF EXISTS `friendRequest`;
CREATE TABLE `friendRequest` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sendId` int NOT NULL,
  `receivedId` int NOT NULL,
  `state` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL,
  `type` int DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `sendId` int NOT NULL,
  `receivedId` int NOT NULL,
  `state` int DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `sendId` (`sendId`),
  KEY `receivedId` (`receivedId`),
  CONSTRAINT `receivedId` FOREIGN KEY (`receivedId`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `sendId` FOREIGN KEY (`sendId`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for picture
-- ----------------------------
DROP TABLE IF EXISTS `picture`;
CREATE TABLE `picture` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dynamicId` int NOT NULL,
  `picture` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `state` int NOT NULL DEFAULT '1',
  `width` double DEFAULT NULL,
  `height` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `pictureDynamicId` (`dynamicId`),
  CONSTRAINT `pictureDynamicId` FOREIGN KEY (`dynamicId`) REFERENCES `dynamic` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nickname` varchar(255) NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `gender` int DEFAULT NULL,
  `state` int DEFAULT '1',
  `headPortrait` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `age` int DEFAULT NULL,
  `background` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

SET FOREIGN_KEY_CHECKS = 1;
