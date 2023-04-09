/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : sg_blog

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 20/11/2022 11:48:25
*/

SET NAMES utf8mb4;
SET
    FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sg_article
-- ----------------------------
DROP TABLE IF EXISTS `sg_article`;
CREATE TABLE `sg_article`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `title`       varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题',
    `content`     longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '文章内容',
    `summary`     varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文章摘要',
    `category_id` bigint NULL DEFAULT NULL COMMENT '所属分类id',
    `thumbnail`   varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '缩略图',
    `is_top`      char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '是否置顶（0否，1是）',
    `status`      char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '状态（0已发布，1草稿）',
    `view_count`  bigint NULL DEFAULT 0 COMMENT '访问量',
    `is_comment`  char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '是否允许评论 1是，0否',
    `create_by`   bigint NULL DEFAULT NULL,
    `create_time` datetime NULL DEFAULT NULL,
    `update_by`   bigint NULL DEFAULT NULL,
    `update_time` datetime NULL DEFAULT NULL,
    `del_flag`    int NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文章表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sg_article
-- ----------------------------
INSERT INTO `sg_article`
VALUES (1, 'SpringSecurity从入门到精通',
        '## 课程介绍\n![image20211219121555979.png](https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/31/e7131718e9e64faeaf3fe16404186eb4.png)\n\n## 0. 简介1\n\n	**Spring Security** 是 Spring 家族中的一个安全管理框架。相比与另外一个安全框架**Shiro**，它提供了更丰富的功能，社区资源也比Shiro丰富。\n\n	一般来说中大型的项目都是使用**SpringSecurity** 来做安全框架。小项目有Shiro的比较多，因为相比与SpringSecurity，Shiro的上手更加的简单。\n\n	 一般Web应用的需要进行**认证**和**授权**。\n\n		**认证：验证当前访问系统的是不是本系统的用户，并且要确认具体是哪个用户**\n\n		**授权：经过认证后判断当前用户是否有权限进行某个操作**\n\n	而认证和授权也是SpringSecurity作为安全框架的核心功能。\n\n\n\n## 1. 快速入门\n\n### 1.1 准备工作\n\n	我们先要搭建一个简单的SpringBoot工程\n\n① 设置父工程 添加依赖\n\n~~~~\n    <parent>\n        <groupId>org.springframework.boot</groupId>\n        <artifactId>spring-boot-starter-parent</artifactId>\n        <version>2.5.0</version>\n    </parent>\n    <dependencies>\n        <dependency>\n            <groupId>org.springframework.boot</groupId>\n            <artifactId>spring-boot-starter-web</artifactId>\n        </dependency>\n        <dependency>\n            <groupId>org.projectlombok</groupId>\n            <artifactId>lombok</artifactId>\n            <optional>true</optional>\n        </dependency>\n    </dependencies>\n~~~~\n\n② 创建启动类\n\n~~~~\n@SpringBootApplication\npublic class SecurityApplication {\n\n    public static void main(String[] args) {\n        SpringApplication.run(SecurityApplication.class,args);\n    }\n}\n\n~~~~\n\n③ 创建Controller\n\n~~~~java\n\nimport org.springframework.web.bind.annotation.RequestMapping;\nimport org.springframework.web.bind.annotation.RestController;\n\n@RestController\npublic class HelloController {\n\n    @RequestMapping(\"/hello\")\n    public String hello(){\n        return \"hello\";\n    }\n}\n\n~~~~\n\n\n\n### 1.2 引入SpringSecurity\n\n	在SpringBoot项目中使用SpringSecurity我们只需要引入依赖即可实现入门案例。\n\n~~~~xml\n        <dependency>\n            <groupId>org.springframework.boot</groupId>\n            <artifactId>spring-boot-starter-security</artifactId>\n        </dependency>\n~~~~\n\n	引入依赖后我们在尝试去访问之前的接口就会自动跳转到一个SpringSecurity的默认登陆页面，默认用户名是user,密码会输出在控制台。\n\n	必须登陆之后才能对接口进行访问。\n\n\n\n## 2. 认证\n\n### 2.1 登陆校验流程\n![image20211215094003288.png](https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/31/414a87eeed344828b5b00ffa80178958.png)',
        'SpringSecurity框架教程-Spring Security+JWT实现项目级前端分离认证授权', 1,
        'https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/31/948597e164614902ab1662ba8452e106.png', '1', '0',
        134, '0', NULL, '2022-01-22 23:20:11', NULL, '2022-11-15 16:33:11', 0);
INSERT INTO `sg_article`
VALUES (5, 'Docker详解',
        '# 1.docker为什么会出现\n\n一款产品从开发到上线，一般都会有`开发环境，测试环境，运行环境`。\n\n==如果有一个环境中某个软件或者依赖版本不同了，可能产品就会出现一些错误，甚至无法运行==。比如开发人员在windows系统，但是最终要把项目部署到linux。如果存在不支持跨平台的软件，那项目肯定也无法部署成功。\n\n这就产生了开发和运维人员之间的矛盾。开发人员在开发环境将代码跑通，但是到了上线的时候就崩了。还要重新检查操作系统，软件，依赖等版本，这大大降低了效率。造成了搭环境一两天，部署项目两分钟的事件。\n\ndocker的出现就能解决以上问题：\n\n开发人员把环境配置好，将需要运行的程序包运行成功，然后把==程序包和环境==一起打包给运维人员，让运维人员部署就可以了。这大大提高了项目上线的效率。\n\n\n\n# 2.docker简介\n\nDocker是基于Go语言实现的云开源项目。\n\nDocker的主要目标是`“Build，Ship and Run Any App,Anywhere”`，==也就是通过对应用组件的封装、分发、部署、运行等生命周期的管理，使用户的APP（可以是一个WEB应用或数据库应用等等）及其运行环境能够做到“<font color=\'red\'>一次镜像，处处运行</font>”==\n\n![image.png](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/b3ffe73fea1d51a52849159c521b3e84.png)\n\nLinux容器技术的出现就解决了这样一个问题，而 Docker 就是在它的基础上发展过来的。<font color=\'red\'>将应用打成镜像，通过镜像成为运行在Docker容器上面的实例，而 Docker容器在任何操作系统上都是一致的，这就实现了跨平台、跨服务器。只需要一次配置好环境，换到别的机器上就可以一键部署好，大大简化了操作</font>。\n\ndocker简介总结：\n\n==解决了运行环境和配置问题的软件容器，方便做持续集成并有助于整体发布的容器虚拟化技术。docker基于Linux内核，仅包含业务运行所需的runtime环境。==',
        'Docker详解', 17,
        'https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/sangengblog/63ead20b51854d2e898f27a0568ad5af.jpeg',
        '1', '0', 44, '0', NULL, '2022-01-17 14:58:37', NULL, '2022-11-15 16:32:52', 0);
INSERT INTO `sg_article`
VALUES (8, 'Redis从入门到高级',
        'Redis是一个NoSQL数据库，其数据都在内存中，支持持久化，主要用作备份恢复。<font color=\'cornflowerblue\'>除了支持简单的key-value模式，还支持多种数据结构的存储，比如 list、set、hash、zset等。</font>==一般是作为缓存数据库辅助持久化的数据库==\n![image-20220921122545305](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220921122545305.png)\nredis诞生小故事：\n\n说起我的诞生，跟关系数据库MySQL还挺有渊源的。\n\n在我还没来到这个世界上的时候，MySQL过的很辛苦，互联网发展的越来越快，<font color=\'red\'>它容纳的数据也越来越多，用户请求也随之暴涨</font>，而每一个用户请求都变成了对它的一个又一个读写操作，MySQL是苦不堪言。尤其是到“双11”、“618“这种全民购物狂欢的日子，都是MySQL受苦受难的日子。\n\n<font color=\'red\'>据后来MySQL告诉我说，其实有一大半的用户请求都是读操作，而且经常都是重复查询一个东西，浪费它很多时间去进行磁盘I/O。</font>\n\n后来有人就琢磨，是不是可以学学CPU，给数据库也加一个缓存呢？于是我就诞生了！\n\n出生不久，我就和MySQL成为了好朋友，我们俩常常携手出现在后端服务器中。\n\n<font color=\'cornflowerblue\'>应用程序们从MySQL查询到的数据，在我这里登记一下，后面再需要用到的时候，就先找我要，我这里没有再找MySQL要。</font>\n![image-20220921122636427](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220921122636427.png)',
        'Redis从入门到高级', 15,
        'https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/sangengblog/205af0ea438f4af6adca179086b7a7d1.png', '0',
        '0', 0, '0', 1, '2022-11-13 11:15:01', NULL, '2022-11-13 16:37:21', 0);

-- ----------------------------
-- Table structure for sg_article_tag
-- ----------------------------
DROP TABLE IF EXISTS `sg_article_tag`;
CREATE TABLE `sg_article_tag`
(
    `article_id` bigint NOT NULL AUTO_INCREMENT COMMENT '文章id',
    `tag_id`     bigint NOT NULL DEFAULT 0 COMMENT '标签id',
    PRIMARY KEY (`article_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文章标签关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sg_article_tag
-- ----------------------------
INSERT INTO `sg_article_tag`
VALUES (1, 4);
INSERT INTO `sg_article_tag`
VALUES (2, 1);
INSERT INTO `sg_article_tag`
VALUES (2, 4);
INSERT INTO `sg_article_tag`
VALUES (3, 4);
INSERT INTO `sg_article_tag`
VALUES (3, 5);
INSERT INTO `sg_article_tag`
VALUES (5, 13);
INSERT INTO `sg_article_tag`
VALUES (8, 1);
INSERT INTO `sg_article_tag`
VALUES (8, 4);
INSERT INTO `sg_article_tag`
VALUES (8, 11);
INSERT INTO `sg_article_tag`
VALUES (8, 12);
INSERT INTO `sg_article_tag`
VALUES (9, 1);
INSERT INTO `sg_article_tag`
VALUES (9, 4);
INSERT INTO `sg_article_tag`
VALUES (9, 11);

-- ----------------------------
-- Table structure for sg_category
-- ----------------------------
DROP TABLE IF EXISTS `sg_category`;
CREATE TABLE `sg_category`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `name`        varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类名',
    `pid`         bigint NULL DEFAULT -1 COMMENT '父分类id，如果没有父分类为-1',
    `description` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
    `status`      char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '状态0:正常,1禁用',
    `create_by`   bigint NULL DEFAULT NULL,
    `create_time` datetime NULL DEFAULT NULL,
    `update_by`   bigint NULL DEFAULT NULL,
    `update_time` datetime NULL DEFAULT NULL,
    `del_flag`    int NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sg_category
-- ----------------------------
INSERT INTO `sg_category`
VALUES (1, 'java', -1, '世界上最受欢迎的编程语言之一', '0', NULL, NULL, NULL, '2022-11-12 22:20:55', 0);
INSERT INTO `sg_category`
VALUES (2, 'PHP', -1, '世界上最好语言', '0', NULL, NULL, NULL, '2022-11-15 16:30:00', 1);
INSERT INTO `sg_category`
VALUES (15, 'redis', -1, 'redis中间件', '0', 1, '2022-11-12 21:52:19', 1, '2022-11-12 21:52:19', 0);
INSERT INTO `sg_category`
VALUES (17, 'docker', -1, '一次镜像，处处运行', '0', 1, '2022-11-15 16:29:54', 1, '2022-11-15 16:29:54', 0);
INSERT INTO `sg_category`
VALUES (18, 'Spring框架', -1, '轻量级的开源的JavaEE框架', '0', 1, '2022-11-15 16:31:02', 1, '2022-11-15 16:31:02', 0);

-- ----------------------------
-- Table structure for sg_comment
-- ----------------------------
DROP TABLE IF EXISTS `sg_comment`;
CREATE TABLE `sg_comment`
(
    `id`                 bigint NOT NULL AUTO_INCREMENT,
    `type`               char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '评论类型（0代表文章评论，1代表友链评论）',
    `article_id`         bigint NULL DEFAULT NULL COMMENT '文章id',
    `root_id`            bigint NULL DEFAULT -1 COMMENT '根评论id',
    `content`            varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评论内容',
    `to_comment_user_id` bigint NULL DEFAULT -1 COMMENT '所回复的目标评论的userid',
    `to_comment_id`      bigint NULL DEFAULT -1 COMMENT '回复目标评论id',
    `create_by`          bigint NULL DEFAULT NULL,
    `create_time`        datetime NULL DEFAULT NULL,
    `update_by`          bigint NULL DEFAULT NULL,
    `update_time`        datetime NULL DEFAULT NULL,
    `del_flag`           int NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sg_comment
-- ----------------------------
INSERT INTO `sg_comment`
VALUES (38, '0', 8, -1, 'Redis缓存出现的问题：缓存穿透、缓存击穿、缓存雪崩[吃惊]', -1, -1, 1, '2022-11-15 16:55:55', 1, '2022-11-15 16:55:55',
        0);
INSERT INTO `sg_comment`
VALUES (39, '0', 5, -1, 'docker:一次镜像，处处运行[哈哈]', -1, -1, 1, '2022-11-15 16:57:46', 1, '2022-11-15 16:57:46', 0);
INSERT INTO `sg_comment`
VALUES (40, '0', 1, -1, '三更老师讲的SpringSecurity真不错[赞][太开心]', -1, -1, 1, '2022-11-15 16:58:51', 1, '2022-11-15 16:58:51',
        0);

-- ----------------------------
-- Table structure for sg_link
-- ----------------------------
DROP TABLE IF EXISTS `sg_link`;
CREATE TABLE `sg_link`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `name`        varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `logo`        varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `description` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `address`     varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '网站地址',
    `status`      char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '2' COMMENT '审核状态 (0代表审核通过，1代表审核未通过，2代表未审核)',
    `create_by`   bigint NULL DEFAULT NULL,
    `create_time` datetime NULL DEFAULT NULL,
    `update_by`   bigint NULL DEFAULT NULL,
    `update_time` datetime NULL DEFAULT NULL,
    `del_flag`    int NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '友链' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sg_link
-- ----------------------------
INSERT INTO `sg_link`
VALUES (4, '哔哩哔哩', 'https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/sangengblog/bilibili.jpg',
        '哔哩哔哩（bilibili.com)是国内知名的视频弹幕网站', 'www.bilibili.com', '0', 1, '2022-11-12 19:22:41', NULL,
        '2022-11-12 22:21:25', 0);
INSERT INTO `sg_link`
VALUES (7, 'IMusic音乐', 'https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/sangengblog/wangyi.jpg',
        '基于SpringBoot+Vue的音乐网站', '43.143.117.57:8092', '0', 1, '2022-11-12 19:40:16', NULL, '2022-11-12 21:13:49', 0);

-- ----------------------------
-- Table structure for sg_tag
-- ----------------------------
DROP TABLE IF EXISTS `sg_tag`;
CREATE TABLE `sg_tag`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `name`        varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签名',
    `create_by`   bigint NULL DEFAULT NULL,
    `create_time` datetime NULL DEFAULT NULL,
    `update_by`   bigint NULL DEFAULT NULL,
    `update_time` datetime NULL DEFAULT NULL,
    `del_flag`    int NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
    `remark`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '标签' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sg_tag
-- ----------------------------
INSERT INTO `sg_tag`
VALUES (1, 'Mybatis', NULL, '2022-11-12 20:53:41', NULL, '2022-11-12 20:54:48', 0, 'Mybatis详解');
INSERT INTO `sg_tag`
VALUES (4, 'Java', NULL, '2022-01-13 15:22:43', NULL, '2022-01-13 15:22:43', 0, 'Java');
INSERT INTO `sg_tag`
VALUES (11, 'Spring', 1, '2022-11-12 16:22:09', 1, '2022-11-12 16:22:09', 0, 'Spring');
INSERT INTO `sg_tag`
VALUES (12, 'Redis', 1, '2022-11-12 20:55:27', 1, '2022-11-12 20:55:27', 0, 'Redis缓存');
INSERT INTO `sg_tag`
VALUES (13, 'docker', 1, '2022-11-15 16:32:03', 1, '2022-11-15 16:32:03', 0, 'docker');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
    `id`          bigint                                                 NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    `menu_name`   varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
    `parent_id`   bigint NULL DEFAULT 0 COMMENT '父菜单ID',
    `order_num`   int NULL DEFAULT 0 COMMENT '显示顺序',
    `path`        varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '路由地址',
    `component`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件路径',
    `is_frame`    int NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
    `menu_type`   char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
    `visible`     char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
    `status`      char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
    `perms`       varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
    `icon`        varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
    `create_by`   bigint NULL DEFAULT NULL COMMENT '创建者',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   bigint NULL DEFAULT NULL COMMENT '更新者',
    `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
    `del_flag`    char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2049 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu`
VALUES (1, '系统管理', 0, 1, 'system', NULL, 1, 'M', '0', '0', '', 'system', 0, '2021-11-12 10:46:19', 0, NULL, '系统管理目录',
        '0');
INSERT INTO `sys_menu`
VALUES (100, '用户管理', 1, 1, 'user', 'system/user/index', 1, 'C', '0', '0', 'system:user:list', 'user', 0,
        '2021-11-12 10:46:19', 1, '2022-07-31 15:47:58', '用户管理菜单', '0');
INSERT INTO `sys_menu`
VALUES (101, '角色管理', 1, 2, 'role', 'system/role/index', 1, 'C', '0', '0', 'system:role:list', 'peoples', 0,
        '2021-11-12 10:46:19', 0, NULL, '角色管理菜单', '0');
INSERT INTO `sys_menu`
VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', 1, 'C', '0', '0', 'system:menu:list', 'tree-table', 0,
        '2021-11-12 10:46:19', 0, NULL, '菜单管理菜单', '0');
INSERT INTO `sys_menu`
VALUES (1001, '用户查询', 100, 1, '', '', 1, 'F', '0', '0', 'system:user:query', '#', 0, '2021-11-12 10:46:19', 0, NULL, '',
        '0');
INSERT INTO `sys_menu`
VALUES (1002, '用户新增', 100, 2, '', '', 1, 'F', '0', '0', 'system:user:add', '#', 0, '2021-11-12 10:46:19', 0, NULL, '',
        '0');
INSERT INTO `sys_menu`
VALUES (1003, '用户修改', 100, 3, '', '', 1, 'F', '0', '0', 'system:user:edit', '#', 0, '2021-11-12 10:46:19', 0, NULL, '',
        '0');
INSERT INTO `sys_menu`
VALUES (1004, '用户删除', 100, 4, '', '', 1, 'F', '0', '0', 'system:user:remove', '#', 0, '2021-11-12 10:46:19', 0, NULL,
        '', '0');
INSERT INTO `sys_menu`
VALUES (1005, '用户导出', 100, 5, '', '', 1, 'F', '0', '0', 'system:user:export', '#', 0, '2021-11-12 10:46:19', 0, NULL,
        '', '0');
INSERT INTO `sys_menu`
VALUES (1006, '用户导入', 100, 6, '', '', 1, 'F', '0', '0', 'system:user:import', '#', 0, '2021-11-12 10:46:19', 0, NULL,
        '', '0');
INSERT INTO `sys_menu`
VALUES (1007, '重置密码', 100, 7, '', '', 1, 'F', '0', '0', 'system:user:resetPwd', '#', 0, '2021-11-12 10:46:19', 0, NULL,
        '', '0');
INSERT INTO `sys_menu`
VALUES (1008, '角色查询', 101, 1, '', '', 1, 'F', '0', '0', 'system:role:query', '#', 0, '2021-11-12 10:46:19', 0, NULL, '',
        '0');
INSERT INTO `sys_menu`
VALUES (1009, '角色新增', 101, 2, '', '', 1, 'F', '0', '0', 'system:role:add', '#', 0, '2021-11-12 10:46:19', 0, NULL, '',
        '0');
INSERT INTO `sys_menu`
VALUES (1010, '角色修改', 101, 3, '', '', 1, 'F', '0', '0', 'system:role:edit', '#', 0, '2021-11-12 10:46:19', 0, NULL, '',
        '0');
INSERT INTO `sys_menu`
VALUES (1011, '角色删除', 101, 4, '', '', 1, 'F', '0', '0', 'system:role:remove', '#', 0, '2021-11-12 10:46:19', 0, NULL,
        '', '0');
INSERT INTO `sys_menu`
VALUES (1012, '角色导出', 101, 5, '', '', 1, 'F', '0', '0', 'system:role:export', '#', 0, '2021-11-12 10:46:19', 0, NULL,
        '', '0');
INSERT INTO `sys_menu`
VALUES (1013, '菜单查询', 102, 1, '', '', 1, 'F', '0', '0', 'system:menu:query', '#', 0, '2021-11-12 10:46:19', 0, NULL, '',
        '0');
INSERT INTO `sys_menu`
VALUES (1014, '菜单新增', 102, 2, '', '', 1, 'F', '0', '0', 'system:menu:add', '#', 0, '2021-11-12 10:46:19', 0, NULL, '',
        '0');
INSERT INTO `sys_menu`
VALUES (1015, '菜单修改', 102, 3, '', '', 1, 'F', '0', '0', 'system:menu:edit', '#', 0, '2021-11-12 10:46:19', 0, NULL, '',
        '0');
INSERT INTO `sys_menu`
VALUES (1016, '菜单删除', 102, 4, '', '', 1, 'F', '0', '0', 'system:menu:remove', '#', 0, '2021-11-12 10:46:19', 0, NULL,
        '', '0');
INSERT INTO `sys_menu`
VALUES (2017, '内容管理', 0, 4, 'content', NULL, 1, 'M', '0', '0', NULL, 'table', NULL, '2022-01-08 02:44:38', 1,
        '2022-07-31 12:34:23', '', '0');
INSERT INTO `sys_menu`
VALUES (2018, '分类管理', 2017, 1, 'category', 'content/category/index', 1, 'C', '0', '0', 'content:category:list',
        'example', NULL, '2022-01-08 02:51:45', NULL, '2022-01-08 02:51:45', '', '0');
INSERT INTO `sys_menu`
VALUES (2019, '文章管理', 2017, 0, 'article', 'content/article/index', 1, 'C', '0', '0', 'content:article:list', 'build',
        NULL, '2022-01-08 02:53:10', NULL, '2022-01-08 02:53:10', '', '0');
INSERT INTO `sys_menu`
VALUES (2021, '标签管理', 2017, 6, 'tag', 'content/tag/index', 1, 'C', '0', '0', 'content:tag:index', 'button', NULL,
        '2022-01-08 02:55:37', NULL, '2022-01-08 02:55:50', '', '0');
INSERT INTO `sys_menu`
VALUES (2022, '友链管理', 2017, 4, 'link', 'content/link/index', 1, 'C', '0', '0', 'content:link:list', '404', NULL,
        '2022-01-08 02:56:50', NULL, '2022-01-08 02:56:50', '', '0');
INSERT INTO `sys_menu`
VALUES (2023, '写博文', 0, 0, 'write', 'content/article/write/index', 1, 'C', '0', '0', 'content:article:writer', 'build',
        NULL, '2022-01-08 03:39:58', 1, '2022-07-31 22:07:05', '', '0');
INSERT INTO `sys_menu`
VALUES (2024, '友链新增', 2022, 0, '', NULL, 1, 'F', '0', '0', 'content:link:add', '#', NULL, '2022-01-16 07:59:17', NULL,
        '2022-01-16 07:59:17', '', '0');
INSERT INTO `sys_menu`
VALUES (2025, '友链修改', 2022, 1, '', NULL, 1, 'F', '0', '0', 'content:link:edit', '#', NULL, '2022-01-16 07:59:44', NULL,
        '2022-01-16 07:59:44', '', '0');
INSERT INTO `sys_menu`
VALUES (2026, '友链删除', 2022, 1, '', NULL, 1, 'F', '0', '0', 'content:link:remove', '#', NULL, '2022-01-16 08:00:05',
        NULL, '2022-01-16 08:00:05', '', '0');
INSERT INTO `sys_menu`
VALUES (2027, '友链查询', 2022, 2, '', NULL, 1, 'F', '0', '0', 'content:link:query', '#', NULL, '2022-01-16 08:04:09', NULL,
        '2022-01-16 08:04:09', '', '0');
INSERT INTO `sys_menu`
VALUES (2048, 'test1', 0, 5, 'test1', NULL, 1, 'M', '0', '0', NULL, 'build', 1, '2022-11-14 12:38:09', NULL,
        '2022-11-14 12:49:58', '', '1');
INSERT INTO `sys_menu`
VALUES (2049, 'test22222', 2048, 1, 'test2222', NULL, 1, 'M', '0', '0', NULL, 'bug', 1, '2022-11-14 12:38:22', NULL,
        '2022-11-14 12:49:54', '', '1');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`          bigint                                                  NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `role_name`   varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '角色名称',
    `role_key`    varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色权限字符串',
    `role_sort`   int                                                     NOT NULL COMMENT '显示顺序',
    `status`      char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NOT NULL COMMENT '角色状态（0正常 1停用）',
    `del_flag`    char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    `create_by`   bigint NULL DEFAULT NULL COMMENT '创建者',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   bigint NULL DEFAULT NULL COMMENT '更新者',
    `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role`
VALUES (1, '超级管理员', 'admin', 1, '0', '0', 0, '2021-11-12 10:46:19', NULL, '2022-11-15 09:29:26', '超级管理员');
INSERT INTO `sys_role`
VALUES (2, '普通角色', 'common', 2, '0', '0', 0, '2021-11-12 10:46:19', NULL, '2022-11-15 09:29:12', '普通角色');
INSERT INTO `sys_role`
VALUES (11, '超级管理员', 'protect', 5, '0', '1', NULL, '2022-01-06 14:07:40', NULL, '2022-11-15 09:08:53', '系统维护人员');
INSERT INTO `sys_role`
VALUES (12, '友链审核员', 'link', 1, '0', '0', NULL, '2022-01-16 06:49:30', NULL, '2022-11-15 09:40:22', '友链审核员');
INSERT INTO `sys_role`
VALUES (13, '文章审核员', 'inspect', 2, '0', '0', NULL, '2022-11-14 23:01:30', NULL, '2022-11-15 09:30:09', '文章审核员');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`
(
    `role_id` bigint NOT NULL COMMENT '角色ID',
    `menu_id` bigint NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu`
VALUES (0, 0);
INSERT INTO `sys_role_menu`
VALUES (1, 1);
INSERT INTO `sys_role_menu`
VALUES (1, 100);
INSERT INTO `sys_role_menu`
VALUES (1, 101);
INSERT INTO `sys_role_menu`
VALUES (1, 102);
INSERT INTO `sys_role_menu`
VALUES (1, 1001);
INSERT INTO `sys_role_menu`
VALUES (1, 1002);
INSERT INTO `sys_role_menu`
VALUES (1, 1003);
INSERT INTO `sys_role_menu`
VALUES (1, 1004);
INSERT INTO `sys_role_menu`
VALUES (1, 1005);
INSERT INTO `sys_role_menu`
VALUES (1, 1006);
INSERT INTO `sys_role_menu`
VALUES (1, 1007);
INSERT INTO `sys_role_menu`
VALUES (1, 1008);
INSERT INTO `sys_role_menu`
VALUES (1, 1009);
INSERT INTO `sys_role_menu`
VALUES (1, 1010);
INSERT INTO `sys_role_menu`
VALUES (1, 1011);
INSERT INTO `sys_role_menu`
VALUES (1, 1012);
INSERT INTO `sys_role_menu`
VALUES (1, 1013);
INSERT INTO `sys_role_menu`
VALUES (1, 1014);
INSERT INTO `sys_role_menu`
VALUES (1, 1015);
INSERT INTO `sys_role_menu`
VALUES (1, 1016);
INSERT INTO `sys_role_menu`
VALUES (1, 2017);
INSERT INTO `sys_role_menu`
VALUES (1, 2018);
INSERT INTO `sys_role_menu`
VALUES (1, 2019);
INSERT INTO `sys_role_menu`
VALUES (1, 2021);
INSERT INTO `sys_role_menu`
VALUES (1, 2022);
INSERT INTO `sys_role_menu`
VALUES (1, 2023);
INSERT INTO `sys_role_menu`
VALUES (1, 2024);
INSERT INTO `sys_role_menu`
VALUES (1, 2025);
INSERT INTO `sys_role_menu`
VALUES (1, 2026);
INSERT INTO `sys_role_menu`
VALUES (1, 2027);
INSERT INTO `sys_role_menu`
VALUES (2, 1);
INSERT INTO `sys_role_menu`
VALUES (2, 102);
INSERT INTO `sys_role_menu`
VALUES (2, 1013);
INSERT INTO `sys_role_menu`
VALUES (2, 1014);
INSERT INTO `sys_role_menu`
VALUES (2, 1015);
INSERT INTO `sys_role_menu`
VALUES (2, 1016);
INSERT INTO `sys_role_menu`
VALUES (2, 2000);
INSERT INTO `sys_role_menu`
VALUES (2, 2023);
INSERT INTO `sys_role_menu`
VALUES (3, 2);
INSERT INTO `sys_role_menu`
VALUES (3, 3);
INSERT INTO `sys_role_menu`
VALUES (3, 4);
INSERT INTO `sys_role_menu`
VALUES (3, 100);
INSERT INTO `sys_role_menu`
VALUES (3, 101);
INSERT INTO `sys_role_menu`
VALUES (3, 103);
INSERT INTO `sys_role_menu`
VALUES (3, 104);
INSERT INTO `sys_role_menu`
VALUES (3, 105);
INSERT INTO `sys_role_menu`
VALUES (3, 106);
INSERT INTO `sys_role_menu`
VALUES (3, 107);
INSERT INTO `sys_role_menu`
VALUES (3, 108);
INSERT INTO `sys_role_menu`
VALUES (3, 109);
INSERT INTO `sys_role_menu`
VALUES (3, 110);
INSERT INTO `sys_role_menu`
VALUES (3, 111);
INSERT INTO `sys_role_menu`
VALUES (3, 112);
INSERT INTO `sys_role_menu`
VALUES (3, 113);
INSERT INTO `sys_role_menu`
VALUES (3, 114);
INSERT INTO `sys_role_menu`
VALUES (3, 115);
INSERT INTO `sys_role_menu`
VALUES (3, 116);
INSERT INTO `sys_role_menu`
VALUES (3, 500);
INSERT INTO `sys_role_menu`
VALUES (3, 501);
INSERT INTO `sys_role_menu`
VALUES (3, 1001);
INSERT INTO `sys_role_menu`
VALUES (3, 1002);
INSERT INTO `sys_role_menu`
VALUES (3, 1003);
INSERT INTO `sys_role_menu`
VALUES (3, 1004);
INSERT INTO `sys_role_menu`
VALUES (3, 1005);
INSERT INTO `sys_role_menu`
VALUES (3, 1006);
INSERT INTO `sys_role_menu`
VALUES (3, 1007);
INSERT INTO `sys_role_menu`
VALUES (3, 1008);
INSERT INTO `sys_role_menu`
VALUES (3, 1009);
INSERT INTO `sys_role_menu`
VALUES (3, 1010);
INSERT INTO `sys_role_menu`
VALUES (3, 1011);
INSERT INTO `sys_role_menu`
VALUES (3, 1012);
INSERT INTO `sys_role_menu`
VALUES (3, 1017);
INSERT INTO `sys_role_menu`
VALUES (3, 1018);
INSERT INTO `sys_role_menu`
VALUES (3, 1019);
INSERT INTO `sys_role_menu`
VALUES (3, 1020);
INSERT INTO `sys_role_menu`
VALUES (3, 1021);
INSERT INTO `sys_role_menu`
VALUES (3, 1022);
INSERT INTO `sys_role_menu`
VALUES (3, 1023);
INSERT INTO `sys_role_menu`
VALUES (3, 1024);
INSERT INTO `sys_role_menu`
VALUES (3, 1025);
INSERT INTO `sys_role_menu`
VALUES (3, 1026);
INSERT INTO `sys_role_menu`
VALUES (3, 1027);
INSERT INTO `sys_role_menu`
VALUES (3, 1028);
INSERT INTO `sys_role_menu`
VALUES (3, 1029);
INSERT INTO `sys_role_menu`
VALUES (3, 1030);
INSERT INTO `sys_role_menu`
VALUES (3, 1031);
INSERT INTO `sys_role_menu`
VALUES (3, 1032);
INSERT INTO `sys_role_menu`
VALUES (3, 1033);
INSERT INTO `sys_role_menu`
VALUES (3, 1034);
INSERT INTO `sys_role_menu`
VALUES (3, 1035);
INSERT INTO `sys_role_menu`
VALUES (3, 1036);
INSERT INTO `sys_role_menu`
VALUES (3, 1037);
INSERT INTO `sys_role_menu`
VALUES (3, 1038);
INSERT INTO `sys_role_menu`
VALUES (3, 1039);
INSERT INTO `sys_role_menu`
VALUES (3, 1040);
INSERT INTO `sys_role_menu`
VALUES (3, 1041);
INSERT INTO `sys_role_menu`
VALUES (3, 1042);
INSERT INTO `sys_role_menu`
VALUES (3, 1043);
INSERT INTO `sys_role_menu`
VALUES (3, 1044);
INSERT INTO `sys_role_menu`
VALUES (3, 1045);
INSERT INTO `sys_role_menu`
VALUES (3, 1046);
INSERT INTO `sys_role_menu`
VALUES (3, 1047);
INSERT INTO `sys_role_menu`
VALUES (3, 1048);
INSERT INTO `sys_role_menu`
VALUES (3, 1049);
INSERT INTO `sys_role_menu`
VALUES (3, 1050);
INSERT INTO `sys_role_menu`
VALUES (3, 1051);
INSERT INTO `sys_role_menu`
VALUES (3, 1052);
INSERT INTO `sys_role_menu`
VALUES (3, 1053);
INSERT INTO `sys_role_menu`
VALUES (3, 1054);
INSERT INTO `sys_role_menu`
VALUES (3, 1055);
INSERT INTO `sys_role_menu`
VALUES (3, 1056);
INSERT INTO `sys_role_menu`
VALUES (3, 1057);
INSERT INTO `sys_role_menu`
VALUES (3, 1058);
INSERT INTO `sys_role_menu`
VALUES (3, 1059);
INSERT INTO `sys_role_menu`
VALUES (3, 1060);
INSERT INTO `sys_role_menu`
VALUES (3, 2000);
INSERT INTO `sys_role_menu`
VALUES (12, 2023);
INSERT INTO `sys_role_menu`
VALUES (13, 2017);
INSERT INTO `sys_role_menu`
VALUES (13, 2019);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`          bigint                                                       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_name`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'NULL' COMMENT '用户名',
    `nick_name`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'NULL' COMMENT '昵称',
    `password`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'NULL' COMMENT '密码',
    `type`        char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '用户类型：0代表普通用户，1代表管理员',
    `status`      char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
    `email`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
    `phonenumber` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
    `sex`         char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户性别（0男，1女，2未知）',
    `avatar`      varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
    `create_by`   bigint NULL DEFAULT NULL COMMENT '创建人的用户id',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   bigint NULL DEFAULT NULL COMMENT '更新人',
    `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
    `del_flag`    int NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14787164048669 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user`
VALUES (1, 'zhangsan', '阳光', '$2a$10$GZONMojZmAm/ooGUWrIK/uTC2MCNHAX.Gax67pTkstgCG7wSiYIvu', '1', '0',
        '23412335@qq.com', '13783239981', '0',
        'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F3bf9c263bc0f2ac5c3a7feb9e218d07475573ec8.gi',
        NULL, '2022-01-05 09:01:56', 1, '2022-01-30 15:37:03', 0);
INSERT INTO `sys_user`
VALUES (2, 'sg3', 'weqe', '$2a$10$ydv3rLkteFnRx9xelQ7elOiVhFvXOooA98xCqk/omh7G94R.K/E3O', '1', '0', '23412352@qq.com',
        '13783239982', '0', '%2F3bf9c263bc0f2ac5c3a7feb9e218d07475573ec8.gi', NULL, '2022-01-05 13:28:43', NULL,
        '2022-01-05 13:28:43', 0);
INSERT INTO `sys_user`
VALUES (4, 'sg2', 'dsadd', '$2a$10$kY4T3SN7i4muBccZppd2OOkhxMN6yt8tND1sF89hXOaFylhY2T3he', '1', '0', '23412532@qq.com',
        '13783239983', '0', '%2F3bf9c263bc0f2ac5c3a7feb9e218d07475573ec8.gi', NULL, '2022-11-13 17:40:35', NULL, NULL,
        0);
INSERT INTO `sys_user`
VALUES (5, 'sg2233', 'tteqe', '', '1', '0', '23414332@qq.com', '13783239984', '1',
        '%2F3bf9c263bc0f2ac5c3a7feb9e218d07475573ec8.gi', NULL, '2022-01-06 03:51:13', NULL, '2022-01-06 07:00:50', 0);
INSERT INTO `sys_user`
VALUES (6, 'sangeng', 'sangeng', '$2a$10$Jnq31rRkNV3RNzXe0REsEOSKaYK8UgVZZqlNlNXqn.JeVcj2NdeZy', '1', '0',
        '23422332@qq.com', '13783239985', '0', '%2F3bf9c263bc0f2ac5c3a7feb9e218d07475573ec8.gi', NULL,
        '2022-01-16 06:54:26', NULL, '2022-01-16 07:06:34', 1);
INSERT INTO `sys_user`
VALUES (14787164048662, 'weixin', 'weixin', '$2a$10$y3k3fnMZsBNihsVLXWfI8uMNueVXBI08k.LzWYaKsW8CW7xXy18wC', '0', '0',
        'weixin@qq.com', '13783239986', '1', '%2F3bf9c263bc0f2ac5c3a7feb9e218d07475573ec8.gi', -1,
        '2022-01-30 17:18:44', -1, '2022-01-30 17:18:44', 1);
INSERT INTO `sys_user`
VALUES (14787164048667, 'xuhuaiang', 'Xu Huaiang', '$2a$10$PinuiEoUHAC2L/rYZy8ucuW/gJ3MubaiB82d1zHRxsVTMeuOUf8nq', '0',
        '0', 'xuhuaiang@qq.com', '13783239987', '0', '%2F3bf9c263bc0f2ac5c3a7feb9e218d07475573ec8.gi', NULL,
        '2022-11-13 17:40:38', NULL, NULL, 0);
INSERT INTO `sys_user`
VALUES (14787164048668, '张三', '张三', '123456', '0', '0', '13783239988@qq.com', '13783239988', '0', NULL, NULL, NULL,
        NULL, NULL, 1);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `user_id` bigint NOT NULL COMMENT '用户ID',
    `role_id` bigint NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role`
VALUES (1, 1);
INSERT INTO `sys_user_role`
VALUES (2, 2);
INSERT INTO `sys_user_role`
VALUES (4, 2);
INSERT INTO `sys_user_role`
VALUES (4, 12);
INSERT INTO `sys_user_role`
VALUES (5, 2);
INSERT INTO `sys_user_role`
VALUES (5, 12);
INSERT INTO `sys_user_role`
VALUES (6, 12);

SET
    FOREIGN_KEY_CHECKS = 1;