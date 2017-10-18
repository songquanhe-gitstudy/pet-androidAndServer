/*
Navicat MySQL Data Transfer

Source Server         : songcould
Source Server Version : 50709
Source Host           : 115.159.113.18:3305
Source Database       : petleaguedb

Target Server Type    : MYSQL
Target Server Version : 50709
File Encoding         : 65001

Date: 2017-10-18 09:16:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_circle
-- ----------------------------
DROP TABLE IF EXISTS `t_circle`;
CREATE TABLE `t_circle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `u_id` int(11) NOT NULL,
  `c_content` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `c_date` varchar(255) CHARACTER SET utf8 NOT NULL,
  `c_type` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '2',
  `c_link_img` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `c_link_title` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `c_video_url` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `c_video_img_url` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `发帖用户id` (`u_id`),
  CONSTRAINT `发帖用户id` FOREIGN KEY (`u_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of t_circle
-- ----------------------------
INSERT INTO `t_circle` VALUES ('8', '1', '进步', '2017-06-04 03-00-41', '2', null, null, null, null);
INSERT INTO `t_circle` VALUES ('9', '1', '发个4G，', '2017-06-04 03-01-54', '2', null, null, null, null);
INSERT INTO `t_circle` VALUES ('10', '1', '我家的小宝贝真的很可爱，你们觉得呢！', '2017-06-04 04-04-17', '2', null, null, null, null);
INSERT INTO `t_circle` VALUES ('11', '1', '哈哈  小宝贝，吃食物！', '2017-06-04 04-09-30', '2', null, null, null, null);
INSERT INTO `t_circle` VALUES ('12', '2', '小喵，出来玩耍，哈哈，可爱哦！', '2017-06-04 06-04-33', '2', null, null, null, null);

-- ----------------------------
-- Table structure for t_circle_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_circle_comment`;
CREATE TABLE `t_circle_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `c_id` int(11) NOT NULL,
  `u_id` int(11) NOT NULL,
  `c_toRep_id` int(11) DEFAULT NULL,
  `c_content` varchar(255) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`),
  KEY `评论帖子的id` (`c_id`),
  KEY `评论用户id` (`u_id`),
  KEY `回复的id` (`c_toRep_id`),
  CONSTRAINT `回复的id` FOREIGN KEY (`c_toRep_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `评论帖子的id` FOREIGN KEY (`c_id`) REFERENCES `t_circle` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `评论用户id` FOREIGN KEY (`u_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of t_circle_comment
-- ----------------------------
INSERT INTO `t_circle_comment` VALUES ('2', '11', '2', null, '哈哈，可爱哦！');
INSERT INTO `t_circle_comment` VALUES ('4', '11', '1', '2', '谢谢哦！');
INSERT INTO `t_circle_comment` VALUES ('8', '12', '1', null, '不在家');

-- ----------------------------
-- Table structure for t_circle_pictures
-- ----------------------------
DROP TABLE IF EXISTS `t_circle_pictures`;
CREATE TABLE `t_circle_pictures` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `circle_id` int(11) NOT NULL,
  `p_pic_url` varchar(255) CHARACTER SET utf8 NOT NULL,
  `p_wight` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '120',
  `p_hight` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '220',
  PRIMARY KEY (`id`),
  KEY `图片所在帖子的id` (`circle_id`),
  CONSTRAINT `图片所在帖子的id` FOREIGN KEY (`circle_id`) REFERENCES `t_circle` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of t_circle_pictures
-- ----------------------------
INSERT INTO `t_circle_pictures` VALUES ('7', '8', 'http://115.159.113.18:8080/petServer/upload/cirlce_photos/e03317a5-4330-4cc7-91e8-fd6fcbc356431496559564352.jpg', '120', '120');
INSERT INTO `t_circle_pictures` VALUES ('8', '9', 'http://115.159.113.18:8080/petServer/upload/cirlce_photos/b1a8586e-c708-43d0-9548-1e8256368c541496559634531.jpg', '120', '120');
INSERT INTO `t_circle_pictures` VALUES ('9', '9', 'http://115.159.113.18:8080/petServer/upload/cirlce_photos/281d4fdb-3b79-47df-bd6a-c1e3301fcd371496559636614.jpg', '120', '120');
INSERT INTO `t_circle_pictures` VALUES ('10', '10', 'http://115.159.113.18:8080/petServer/upload/cirlce_photos/9239c877-734f-4908-96c0-c6db74a7a7091496563380598.jpg', '120', '200');
INSERT INTO `t_circle_pictures` VALUES ('11', '11', 'http://115.159.113.18:8080/petServer/upload/cirlce_photos/457ca844-fd2d-4906-8f64-165fdb06b4a11496563692648.jpg', '120', '220');
INSERT INTO `t_circle_pictures` VALUES ('12', '11', 'http://115.159.113.18:8080/petServer/upload/cirlce_photos/91ce4276-2ac7-4d3f-8747-e7436fbf87b11496563694624.jpg', '120', '220');
INSERT INTO `t_circle_pictures` VALUES ('13', '12', 'http://115.159.113.18:8080/petServer/upload/cirlce_photos/b20b1a76-a119-40f4-b418-0b27c25e98eb1496570593398.jpg', '120', '220');
INSERT INTO `t_circle_pictures` VALUES ('14', '12', 'http://115.159.113.18:8080/petServer/upload/cirlce_photos/9e23613e-da09-4772-94e9-45b5da9fbeae1496570595426.jpg', '120', '220');
INSERT INTO `t_circle_pictures` VALUES ('15', '12', 'http://115.159.113.18:8080/petServer/upload/cirlce_photos/d5ea9643-f909-4735-9dd6-a297707f8ad61496570596816.jpg', '120', '220');

-- ----------------------------
-- Table structure for t_circle_praise
-- ----------------------------
DROP TABLE IF EXISTS `t_circle_praise`;
CREATE TABLE `t_circle_praise` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `c_id` int(11) NOT NULL,
  `u_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `点赞所在帖子的id` (`c_id`),
  KEY `点赞所在用户id` (`u_id`),
  CONSTRAINT `点赞所在帖子的id` FOREIGN KEY (`c_id`) REFERENCES `t_circle` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `点赞所在用户id` FOREIGN KEY (`u_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of t_circle_praise
-- ----------------------------
INSERT INTO `t_circle_praise` VALUES ('3', '11', '1');
INSERT INTO `t_circle_praise` VALUES ('5', '10', '2');
INSERT INTO `t_circle_praise` VALUES ('9', '11', '2');
INSERT INTO `t_circle_praise` VALUES ('18', '10', '1');
INSERT INTO `t_circle_praise` VALUES ('19', '12', '1');

-- ----------------------------
-- Table structure for t_mb_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_mb_comment`;
CREATE TABLE `t_mb_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `u_id` int(11) NOT NULL,
  `m_id` int(11) NOT NULL,
  `mb_content` varchar(255) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`),
  KEY `评论用户的id` (`u_id`),
  KEY `评论留言板的id` (`m_id`),
  CONSTRAINT `评论用户的id` FOREIGN KEY (`u_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `评论留言板的id` FOREIGN KEY (`m_id`) REFERENCES `t_message_board` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of t_mb_comment
-- ----------------------------
INSERT INTO `t_mb_comment` VALUES ('1', '1', '1', '叫妞妞呢 ^-^');

-- ----------------------------
-- Table structure for t_message_board
-- ----------------------------
DROP TABLE IF EXISTS `t_message_board`;
CREATE TABLE `t_message_board` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ub_id` int(11) NOT NULL,
  `um_id` int(11) NOT NULL,
  `m_content` varchar(255) CHARACTER SET utf8 NOT NULL,
  `m_data` varchar(255) CHARACTER SET utf8 NOT NULL,
  `m_type` varchar(255) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`),
  KEY `用户自己的id` (`ub_id`),
  KEY `评论板子的用户id` (`um_id`),
  CONSTRAINT `用户自己的id` FOREIGN KEY (`ub_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `评论板子的用户id` FOREIGN KEY (`um_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of t_message_board
-- ----------------------------
INSERT INTO `t_message_board` VALUES ('1', '1', '1', '你的宠物叫什么名字呢，我们可以共同交流一下～', '2017-06-04 04-17_12', '2');
INSERT INTO `t_message_board` VALUES ('2', '1', '1', '很多很多事情事情还想跟我家妞妞一起做，希望它能永远开心下去！', '2017-06-04 04-18_57', '1');
INSERT INTO `t_message_board` VALUES ('3', '2', '1', '哈哈！你的妞妞很可爱哦，跟我家小喵一样。', '2017-06-04 05-35_00', '1');

-- ----------------------------
-- Table structure for t_news
-- ----------------------------
DROP TABLE IF EXISTS `t_news`;
CREATE TABLE `t_news` (
  `id` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `n_title` varchar(255) CHARACTER SET utf8 NOT NULL,
  `n_author` varchar(255) CHARACTER SET utf8 NOT NULL,
  `n_content` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '1',
  `n_pic_url` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `n_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `n_praise_num` int(11) DEFAULT NULL,
  `n_comment_num` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of t_news
-- ----------------------------
INSERT INTO `t_news` VALUES ('1', '1', '哪些人适合饲养喜乐蒂牧羊犬,纯喜乐蒂牧羊犬多少钱', '网络小文', '1', 'http://115.159.113.18:8080/petServer/upload/news_photo/1.jpg', '2017-06-04 19:15:58', '880', '50');
INSERT INTO `t_news` VALUES ('2', '2', '猫咪不听话 猫咪非常调皮淘气应怎么办', '天天新闻', '1', 'http://115.159.113.18:8080/petServer/upload/news_photo/2.jpg', '2017-06-04 19:15:55', '800', '65');
INSERT INTO `t_news` VALUES ('3', '2', '猫咪老叫唤怎么办 猫咪老叫唤什么原因', '小红花', '1', 'http://115.159.113.18:8080/petServer/upload/news_photo/3.jpg', '2017-06-04 19:15:50', '523', '54');
INSERT INTO `t_news` VALUES ('4', '2', '猫咪挑食怎么办 有什么办法不让猫挑食', '小红花', '1', 'http://115.159.113.18:8080/petServer/upload/news_photo/4.jpg', '2017-06-04 19:15:46', '231', '34');
INSERT INTO `t_news` VALUES ('5', '2', '猫随地大小便 如何纠正猫咪随地大小便', '小红花', '1', 'http://115.159.113.18:8080/petServer/upload/news_photo/5.jpg', '2017-06-04 19:15:43', '324', '44');
INSERT INTO `t_news` VALUES ('6', '2', '猫乱捡东西吃 如何阻止猫咪乱捡东西吃', '小红花', '1', 'http://115.159.113.18:8080/petServer/upload/news_photo/6.jpg', '2017-06-04 19:15:40', '545', '45');
INSERT INTO `t_news` VALUES ('7', '2', '猫咪吃饭上桌 如何制止纠正猫吃饭上桌', '小红花', '1', 'http://115.159.113.18:8080/petServer/upload/news_photo/7.jpg', '2017-06-04 19:15:35', '345', '45');
INSERT INTO `t_news` VALUES ('8', '2', '猫洗澡乱抓人 怎样给猫洗澡让它不抓人', '小红花', '1', 'http://115.159.113.18:8080/petServer/upload/news_photo/8.jpg', '2017-06-04 19:14:44', '454', '43');
INSERT INTO `t_news` VALUES ('9', '2', '猫咪老抓人什么原因 猫咪老抓人怎么办', '小红娘', '1', 'http://115.159.113.18:8080/petServer/upload/news_photo/9.jpg', '2017-06-04 19:14:50', '545', '34');
INSERT INTO `t_news` VALUES ('10', '2', '猫咪为什么怕生人 猫咪怕生人该怎么办', '小水', '1', 'http://115.159.113.18:8080/petServer/upload/news_photo/10.jpg', '2017-06-04 19:14:59', '564', '45');
INSERT INTO `t_news` VALUES ('11', '2', '猫咪上床尿尿 如何纠正猫咪在床上尿尿', '小红花', '1', 'http://115.159.113.18:8080/petServer/upload/news_photo/11.jpg', '2017-06-04 19:15:04', '5464', '456');
INSERT INTO `t_news` VALUES ('12', '1', '猫咪多大吃猫粮合适,猫咪的生理系统及生理功能', '娱文', '1', 'http://115.159.113.18:8080/petServer/upload/news_photo/12.jpg', '2017-06-04 19:23:54', '654', '45');
INSERT INTO `t_news` VALUES ('13', '1', '猫咪吃成猫粮和幼猫粮都可以吗,猫粮', '天天快讯', '1', 'http://115.159.113.18:8080/petServer/upload/news_photo/13.jpg', '2017-06-04 19:23:57', '232', '32');
INSERT INTO `t_news` VALUES ('14', '1', '《猫咪后院》现实版你也可以拥有,猫咪做错事如何', '天台女', '1', 'http://115.159.113.18:8080/petServer/upload/news_photo/14.jpg', '2017-06-04 19:24:00', '342', '23');
INSERT INTO `t_news` VALUES ('15', '1', '猫咪晚上不睡觉原因及解决办法,猫咪不睡觉怎么办', '史蒂夫', '1', 'http://115.159.113.18:8080/petServer/upload/news_photo/15.jpg', '2017-06-04 19:24:03', '5645', '232');
INSERT INTO `t_news` VALUES ('16', '1', '注意猫咪绝对不能吃哪些食物,猫不能做素食主义者', '天天资讯', '1', 'http://115.159.113.18:8080/petServer/upload/news_photo/16.jpg', '2017-06-04 19:24:07', '455', '34');
INSERT INTO `t_news` VALUES ('17', '1', '教你选购纯种的金吉拉猫猫,选购正牌金吉拉猫的小', '小红花', '1', 'http://115.159.113.18:8080/petServer/upload/news_photo/17.jpg', '2017-06-04 19:24:11', '867', '56');
INSERT INTO `t_news` VALUES ('18', '1', '预防猫猫便秘应该从小抓起,营养不良容易导致猫猫', '小红花', '1', 'http://115.159.113.18:8080/petServer/upload/news_photo/18.jpg', '2017-06-04 19:24:15', '5654', '455');
INSERT INTO `t_news` VALUES ('19', '1', '选购暹罗猫的十大常识,暹罗猫习惯用尾巴表达心情', '小红花', '1', 'http://115.159.113.18:8080/petServer/upload/news_photo/19.jpg', '2017-06-04 19:24:18', '234', '54');
INSERT INTO `t_news` VALUES ('20', '3', '狗狗身上60多个洞，以为被虫咬，没想到却是因为有恶邻 2017', '小红花', '1', 'http://115.159.113.18:8080/petServer/upload/news_photo/20.jpg', '2017-06-04 19:43:38', '456', '45');
INSERT INTO `t_news` VALUES ('21', '3', '欢迎自带宠物来就餐 20', '小红花', '1', 'http://115.159.113.18:8080/petServer/upload/news_photo/21.jpg', '2017-06-04 19:43:46', '4564', '545');
INSERT INTO `t_news` VALUES ('22', '3', '13岁小男孩徒步6公里只为救爱犬，他的举动感动了所有人！ ', '小红花', '1', 'http://115.159.113.18:8080/petServer/upload/news_photo/22.jpg', '2017-06-04 19:43:51', '454', '43');
INSERT INTO `t_news` VALUES ('23', '3', '这只狗叫“老虎”，今年8岁已经在海边做了5年环卫工 ', '小红花', '1', 'http://115.159.113.18:8080/petServer/upload/news_photo/23.jpg', '2017-06-04 19:43:54', '546', '45');
INSERT INTO `t_news` VALUES ('24', '3', '夏天到了！你还敢给狗剃毛吗？ ', '熊小华', '1', 'http://115.159.113.18:8080/petServer/upload/news_photo/24.jpg', '2017-06-04 19:43:57', '544', '43');
INSERT INTO `t_news` VALUES ('25', '3', '没有了鼻子，没有了耳朵和尾巴，却有了一个不离不弃的家 ', '熊小华', '1', 'http://115.159.113.18:8080/petServer/upload/news_photo/25.jpg', '2017-06-04 19:44:00', '435', '34');
INSERT INTO `t_news` VALUES ('26', '3', '恐怖来袭！我与它的灵异事件 ', '小红花', '1', 'http://115.159.113.18:8080/petServer/upload/news_photo/26.jpg', '2017-06-04 19:44:04', '435', '34');
INSERT INTO `t_news` VALUES ('27', '3', '有爱：主人突发脑溢血，医院通融让狗狗见主人最后一面 ', '小红花', '1', 'http://115.159.113.18:8080/petServer/upload/news_photo/27.jpg', '2017-06-04 19:44:07', '435', '54');
INSERT INTO `t_news` VALUES ('28', '3', '不能再萌：受了伤还一本正经卖萌的小动物们', '熊爱红', '1', 'http://115.159.113.18:8080/petServer/upload/news_photo/28.jpg', '2017-06-04 19:44:13', '345', '34');
INSERT INTO `t_news` VALUES ('29', '3', '愚昧无知害死狗，必须科学喂养！比瑞吉狗粮开团！', '小红花', '1', 'http://115.159.113.18:8080/petServer/upload/news_photo/29.jpg', '2017-06-04 19:44:18', '456', '45');
INSERT INTO `t_news` VALUES ('30', '3', '喜大普奔！握爪宝宝80天内遍布全中国！单笔成交最高8万！ ', '小红花', '1', 'http://115.159.113.18:8080/petServer/upload/news_photo/30.jpg', '2017-06-04 19:44:23', '234', '32');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `u_name` varchar(255) CHARACTER SET utf8 NOT NULL,
  `u_pwd` varchar(255) CHARACTER SET utf8 NOT NULL,
  `u_head_url` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `u_head_bg_url` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `u_phone_number` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `u_sex` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `u_age` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `u_college` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `u_major` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `u_class` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `u_student_number` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `u_city` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `u_birthday` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `u_signature` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `u_constellation` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `u_emotion` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `u_usually_city` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `u_habbies` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `u_like_something` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', '宋泉河', '123456', 'http://115.159.113.18:8080/petServer/upload/user_photos/1f2fc127-f4d4-4b11-9474-a93afdf44a441496564993067.jpg', 'http://115.159.113.18:8080/petServer/upload/userBg_photos/5b67fec5-d781-42ac-acaa-5b4fd61f48ac1496571089103.jpg', 'sdfsd', 'GG', '12', '宜春学院计算机科学与技术学院', 'df', 'dsf', 'sdf', 'dsf', '123', 'dsf', 'sdf', 'sdf', 'sdf', 'sdf', 'd');
INSERT INTO `t_user` VALUES ('2', '邓建飞', '123456', 'http://115.159.113.18:8080/petServer/upload/user_photos/b3bae28f-85ee-4744-a70b-4590c68cf9c41496568737502.jpg', 'http://115.159.113.18:8080/petServer/upload/userBg_photos/15f0f43e-141e-4eba-9476-3d1ef536e1db1496568347318.jpg', 'sdfsdf', 'MM', '1', '宜春学院体育学院', 'sd', 'sd', 'd', 'sd', 'd', 'd', 'd', 'd', 'd', 'd', 'd');

-- ----------------------------
-- Table structure for t_user_frient
-- ----------------------------
DROP TABLE IF EXISTS `t_user_frient`;
CREATE TABLE `t_user_frient` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `u_id` int(11) NOT NULL,
  `fri_uId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `朋友id` (`fri_uId`),
  KEY `朋友的id` (`u_id`),
  CONSTRAINT `朋友id` FOREIGN KEY (`fri_uId`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `朋友的id` FOREIGN KEY (`u_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of t_user_frient
-- ----------------------------
INSERT INTO `t_user_frient` VALUES ('2', '2', '1');
INSERT INTO `t_user_frient` VALUES ('7', '1', '2');
