/*
 Navicat Premium Data Transfer

 Source Server         : mydatabase
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : mybatis

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 26/06/2022 00:42:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bk_user
-- ----------------------------
DROP TABLE IF EXISTS `bk_user`;
CREATE TABLE `bk_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `money` double(9, 2) NULL DEFAULT 0.00,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bk_user
-- ----------------------------
INSERT INTO `bk_user` VALUES (1, 'A', 100.00);
INSERT INTO `bk_user` VALUES (2, 'B', 0.00);
INSERT INTO `bk_user` VALUES (3, 'C', 0.00);
INSERT INTO `bk_user` VALUES (4, 'D', 0.00);
INSERT INTO `bk_user` VALUES (5, '张部国内账号', 0.00);
INSERT INTO `bk_user` VALUES (6, '张部海外账号', 100.00);

-- ----------------------------
-- Table structure for tb_brand
-- ----------------------------
DROP TABLE IF EXISTS `tb_brand`;
CREATE TABLE `tb_brand`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `brand_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `company_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `ordered` int(0) NULL DEFAULT NULL,
  `description` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 140 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_brand
-- ----------------------------
INSERT INTO `tb_brand` VALUES (1, 'Dickies', 'Dickies背带裤公司', 1000, 'Dickies成立于1922年，成立之初是一家小型背带裤公司，对功能的注重使Dickies成为了品牌中的另类，如今是美式休闲工装鞋服制造商，潮流鞋服公司。', 1);
INSERT INTO `tb_brand` VALUES (2, '华为', '华为技术有限公司', 100, '华为致力于把数字世界带入每个人、每个家庭、每个组织，构建万物互联的智能世界', 1);
INSERT INTO `tb_brand` VALUES (3, '小米', '小米科技有限公司', 50, '小米是一家以手机、智能硬件和IoT平台为核心的互联网公司，以智能手机、智能电视、笔记本等丰富的产品与服务。致力于让全球每个人都能享受科技带来的美好生活', 1);
INSERT INTO `tb_brand` VALUES (4, '苹果', '苹果公司（Apple Inc. ）', 1000, '是美国一家高科技公司。由史蒂夫·乔布斯、斯蒂夫·盖瑞·沃兹尼亚克和罗纳德·杰拉尔德·韦恩（Ron Wayne）等人于1976年4月1日创立，并命名为美国苹果电脑公司（Apple Computer Inc.），2007年1月9日更名为苹果公司，总部位于加利福尼亚州的库比蒂诺。', 1);
INSERT INTO `tb_brand` VALUES (5, 'adidas', '德国阿迪达斯AG成员公司', 1000, 'adidas（阿迪达斯）创办于1949年，是德国运动用品制造商阿迪达斯AG成员公司。以其创办人阿道夫·阿迪·达斯勒（Adolf Adi Dassler）命名，1920年在黑措根奥拉赫开始生产鞋类产品。', 1);
INSERT INTO `tb_brand` VALUES (6, 'Champion', '冠军品牌', 1000, '1919年，Simon Feinbloom和他的父亲一起，在美国纽约的罗切斯特正式成立了Champion KnitWear Co.。如同所痴迷于竞技体育运动的人们对于胜利的渴望，所有人的心中都存在着一个关于冠军的梦，因此Champion从问世就吸引了所有运动爱好者的眼球。', 1);
INSERT INTO `tb_brand` VALUES (59, '孙悟空', '大蛇丸公司', NULL, '五台山山脚', 1);
INSERT INTO `tb_brand` VALUES (60, '华为', '华为技术有限公司', 100, '万物互联', 1);
INSERT INTO `tb_brand` VALUES (61, '小米', '小米科技有限公司', 50, 'are you ok', 1);
INSERT INTO `tb_brand` VALUES (62, '格力', '格力电器股份有限公司', 30, '让世界爱上中国造', 1);
INSERT INTO `tb_brand` VALUES (63, '阿里巴巴', '阿里巴巴集团控股有限公司', 10, '买买买', 1);
INSERT INTO `tb_brand` VALUES (64, '腾讯', '腾讯计算机系统有限公司', 50, '玩玩玩', 0);
INSERT INTO `tb_brand` VALUES (65, '百度', '百度在线网络技术公司', 5, '搜搜搜', 0);
INSERT INTO `tb_brand` VALUES (66, '京东', '北京京东世纪贸易有限公司', 40, '就是快', 1);
INSERT INTO `tb_brand` VALUES (67, '小米', '小米科技有限公司', 50, 'are you ok', 1);
INSERT INTO `tb_brand` VALUES (68, '三只松鼠', '三只松鼠股份有限公司', 5, '好吃不上火', 0);
INSERT INTO `tb_brand` VALUES (69, '华为', '华为技术有限公司', 100, '万物互联', 1);
INSERT INTO `tb_brand` VALUES (70, '小米', '小米科技有限公司', 50, 'are you ok', 1);
INSERT INTO `tb_brand` VALUES (71, '格力', '格力电器股份有限公司', 30, '让世界爱上中国造', 1);
INSERT INTO `tb_brand` VALUES (72, '阿里巴巴', '阿里巴巴集团控股有限公司', 10, '买买买', 1);
INSERT INTO `tb_brand` VALUES (73, '腾讯', '腾讯计算机系统有限公司', 50, '玩玩玩', 0);
INSERT INTO `tb_brand` VALUES (74, '百度', '百度在线网络技术公司', 5, '搜搜搜', 0);
INSERT INTO `tb_brand` VALUES (75, '京东', '北京京东世纪贸易有限公司', 40, '就是快', 1);
INSERT INTO `tb_brand` VALUES (76, '华为', '华为技术有限公司', 100, '万物互联', 1);
INSERT INTO `tb_brand` VALUES (77, '小米', '小米科技有限公司', 50, 'are you ok', 1);
INSERT INTO `tb_brand` VALUES (78, '格力', '格力电器股份有限公司', 30, '让世界爱上中国造', 1);
INSERT INTO `tb_brand` VALUES (79, '阿里巴巴', '阿里巴巴集团控股有限公司', 10, '买买买', 1);
INSERT INTO `tb_brand` VALUES (80, '腾讯', '腾讯计算机系统有限公司', 50, '玩玩玩', 0);
INSERT INTO `tb_brand` VALUES (81, '百度', '百度在线网络技术公司', 5, '搜搜搜', 0);
INSERT INTO `tb_brand` VALUES (83, '小米', '小米科技有限公司', 50, 'are you ok', 1);
INSERT INTO `tb_brand` VALUES (85, '华为', '华为技术有限公司', 100, '万物互联', 1);
INSERT INTO `tb_brand` VALUES (86, '小米', '小米科技有限公司', 50, 'are you ok', 1);
INSERT INTO `tb_brand` VALUES (87, '格力', '格力电器股份有限公司', 30, '让世界爱上中国造', 1);
INSERT INTO `tb_brand` VALUES (88, '阿里巴巴', '阿里巴巴集团控股有限公司', 10, '买买买', 1);
INSERT INTO `tb_brand` VALUES (89, '腾讯', '腾讯计算机系统有限公司', 50, '玩玩玩', 0);
INSERT INTO `tb_brand` VALUES (90, '百度', '百度在线网络技术公司', 5, '搜搜搜', 0);
INSERT INTO `tb_brand` VALUES (91, '京东', '北京京东世纪贸易有限公司', 40, '就是快', 1);
INSERT INTO `tb_brand` VALUES (92, '华为', '华为技术有限公司', 100, '万物互联', 1);
INSERT INTO `tb_brand` VALUES (93, '小米', '小米科技有限公司', 50, 'are you ok', 1);
INSERT INTO `tb_brand` VALUES (94, '格力', '格力电器股份有限公司', 30, '让世界爱上中国造', 1);
INSERT INTO `tb_brand` VALUES (95, '阿里巴巴', '阿里巴巴集团控股有限公司', 10, '买买买', 1);
INSERT INTO `tb_brand` VALUES (96, '腾讯', '腾讯计算机系统有限公司', 50, '玩玩玩', 0);
INSERT INTO `tb_brand` VALUES (97, '百度', '百度在线网络技术公司', 5, '搜搜搜', 0);
INSERT INTO `tb_brand` VALUES (98, '京东', '北京京东世纪贸易有限公司', 40, '就是快', 1);
INSERT INTO `tb_brand` VALUES (99, '小米', '小米科技有限公司', 50, 'are you ok', 1);
INSERT INTO `tb_brand` VALUES (100, '三只松鼠', '三只松鼠股份有限公司', 5, '好吃不上火', 0);
INSERT INTO `tb_brand` VALUES (101, '华为', '华为技术有限公司', 100, '万物互联', 1);
INSERT INTO `tb_brand` VALUES (102, '小米', '小米科技有限公司', 50, 'are you ok', 1);
INSERT INTO `tb_brand` VALUES (103, '格力', '格力电器股份有限公司', 30, '让世界爱上中国造', 1);
INSERT INTO `tb_brand` VALUES (104, '阿里巴巴', '阿里巴巴集团控股有限公司', 10, '买买买', 1);
INSERT INTO `tb_brand` VALUES (105, '腾讯', '腾讯计算机系统有限公司', 50, '玩玩玩', 0);
INSERT INTO `tb_brand` VALUES (106, '百度', '百度在线网络技术公司', 5, '搜搜搜', 0);

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `gender` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `addr` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `cid` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_cid_bid`(`cid`) USING BTREE,
  CONSTRAINT `fk_cid_bid` FOREIGN KEY (`cid`) REFERENCES `tb_brand` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (1, 'zhangsan', '123', '男', '北京', 1);
INSERT INTO `tb_user` VALUES (2, '李四', '234', '女', '天津', 2);
INSERT INTO `tb_user` VALUES (3, '王五', '11', '男', '西安', 2);
INSERT INTO `tb_user` VALUES (4, '赵六', '123456', NULL, NULL, NULL);
INSERT INTO `tb_user` VALUES (9, '孙悟空', '123456', NULL, NULL, NULL);
INSERT INTO `tb_user` VALUES (11, '罗翔', '123456', NULL, NULL, NULL);
INSERT INTO `tb_user` VALUES (12, '唐僧', '123456', NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
