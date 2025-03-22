SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_name` varchar(50) DEFAULT NULL,
  `admin_pwd` varchar(50) DEFAULT NULL,
  `admin_email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1', 'admin', '123456', '501455447@gmail.com');

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `book_id` int(11) NOT NULL AUTO_INCREMENT,
  `book_name` varchar(255) NOT NULL,
  `book_author` varchar(255) DEFAULT NULL,
  `book_publish` varchar(255) DEFAULT NULL,
  `book_category` int(11) DEFAULT NULL,
  `book_price` double DEFAULT NULL,
  `book_introduction` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`book_id`),
  KEY `book_category` (`book_category`) USING BTREE,
  CONSTRAINT `book_ibfk_1` FOREIGN KEY (`book_category`) REFERENCES `book_category` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('1', 'The Fall of Giants', 'Ken Follett', 'Jiangsu Phoenix Literature and Art Publishing House', '1', '129', 'A story set in World War I');
INSERT INTO `book` VALUES ('2', 'The Three-Body Problem', 'Liu Cixin', 'Nanjing University Press', '1', '68', 'Science fiction novel');
INSERT INTO `book` VALUES ('3', 'Resurrection', 'Leo Tolstoy', 'Shanghai Translation Publishing House', '1', '19', 'Russian novel');
INSERT INTO `book` VALUES ('6', 'Ordinary World', 'Lu Yao', 'Shanghai Literature and Art Publishing House', '1', '88', 'A novel about two brothers, Sun Shaoping and Sun Shaan...');
INSERT INTO `book` VALUES ('15', 'White Deer Plain', 'Chen Zhongshi', 'Nanjing Press', '1', '36', 'Contemporary novel');
INSERT INTO `book` VALUES ('16', 'Computer Networks', 'Xie Xiren', 'Electronic Industry Press', '3', '49', 'Computer science book');
INSERT INTO `book` VALUES ('17', 'Love in the Time of Cholera', 'Gabriel Garcia Marquez', 'Yilin Press', '9', '39', 'Foreign novel');
INSERT INTO `book` VALUES ('18', 'Left Genius, Right Madman', 'Gao Ming', 'Beijing United Publishing Company', '1', '39.8', 'Psychology book');
INSERT INTO `book` VALUES ('19', 'Abandoned City', 'Jia Pingwa', 'Commercial Press', '8', '29', 'Modern novel');
INSERT INTO `book` VALUES ('20', 'jQuery', 'Ryan', 'China Electric Power Press', '3', '78', 'JavaScript library');
INSERT INTO `book` VALUES ('21', 'Python Web Scraping', 'Zhang Bowen', 'Tsinghua University Press', '3', '52', 'Introduction to web scraping with Python');
INSERT INTO `book` VALUES ('22', 'Python Visualization for Beginners', 'Variation', 'Electronic University Press', '8', '61', 'Exploring the hidden secrets of data');
INSERT INTO `book` VALUES ('71', 'Spring Boot from Basics to Practice', 'Xiaowei', 'Peking University Press', '3', '78', 'Introduction to Spring Boot');

-- ----------------------------
-- Table structure for book_category
-- ----------------------------
DROP TABLE IF EXISTS `book_category`;
CREATE TABLE `book_category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of book_category
-- ----------------------------
INSERT INTO `book_category` VALUES ('1', 'Novel');
INSERT INTO `book_category` VALUES ('2', 'History');
INSERT INTO `book_category` VALUES ('3', 'Computer Science');
INSERT INTO `book_category` VALUES ('4', 'Philosophy');
INSERT INTO `book_category` VALUES ('5', 'Social Science');

-- ----------------------------
-- Table structure for borrowingbooks
-- ----------------------------
DROP TABLE IF EXISTS `borrowingbooks`;
CREATE TABLE `borrowingbooks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `book_id` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `book_id` (`book_id`) USING BTREE,
  CONSTRAINT `borrowingbooks_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`),
  CONSTRAINT `borrowingbooks_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `user_pwd` varchar(50) DEFAULT NULL,
  `user_email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'user1', '123456', '501455447@qq.com');
INSERT INTO `user` VALUES ('2', 'zbw', '123456', '501455447@qq.com');
INSERT INTO `user` VALUES ('5', 'user2', '123456', '501455447@qq.com');
INSERT INTO `user` VALUES ('6', 'LeBron James', '123456', '501455447@qq.com');
INSERT INTO `user` VALUES ('7', 'Kobe Bryant', '123456', '501455447@qq.com');
INSERT INTO `user` VALUES ('8', 'Plato', '123456', '501455447@qq.com');
INSERT INTO `user` VALUES ('9', 'Napoleon', '123456', '501455447@qq.com');
INSERT INTO `user` VALUES ('10', 'Irving', '123456', '501455447@qq.com');

