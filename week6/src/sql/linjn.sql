-- 创建数据库
DROP DATABASE IF EXISTS `linjn`;
CREATE DATABASE linjn CHARACTER SET utf8mb4;

USE linjn;
-- 用户表
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
	`user_name` VARCHAR(255) NOT NULL COMMENT '用户名',
	`pwd` VARCHAR(50) NOT NULL COMMENT '密码',
	`email` VARCHAR(20) COMMENT '邮箱',
	`phone` VARCHAR(20) COMMENT '手机号码',
	`img` VARCHAR(255) COMMENT '用户头像',
	`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`delete_time` datetime DEFAULT NULL COMMENT '删除时间',
	PRIMARY KEY(`id`)
) ENGINE = INNODB DEFAULT CHARSET= utf8mb4 COMMENT = '用户表';

-- 地址表
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`(
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
	`name` VARCHAR(50) NOT NULL COMMENT '收货姓名',
	`phone` VARCHAR(20) NOT NULL COMMENT '手机号码',
	`address` VARCHAR(500) NOT NULL COMMENT '详细地址',
	`isDefault` TINYINT(4) NOT NULL DEFAULT 1 COMMENT '是否是默认地址。1：不是。2：是',
	`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`delete_time` datetime DEFAULT NULL COMMENT '删除时间',
	PRIMARY KEY(`id`)
) ENGINE = INNODB DEFAULT CHARSET= utf8mb4 COMMENT = '地址表';

-- 商品分类表
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
	`category_name` VARCHAR(50) NOT NULL COMMENT '分类名称',
	`description` VARCHAR(255) DEFAULT '' COMMENT '描述',
	`img` VARCHAR(255) DEFAULT NULL COMMENT '分类图片',
	`status` TINYINT(4) NOT NULL DEFAULT 1 COMMENT '状态。1 可使用；2 被禁用',
	`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`delete_time` datetime DEFAULT NULL COMMENT '删除时间',
	PRIMARY KEY(`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COMMENT = '商品分类表';

-- 商品标签表
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
	`tag_name` VARCHAR(50) NOT NULL COMMENT '标签名称',
	`description` VARCHAR(255) DEFAULT '' COMMENT '描述',
	`status` TINYINT(4) NOT NULL DEFAULT 1 COMMENT '状态。1 可使用；2 被禁用',
	`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`delete_time` datetime DEFAULT NULL COMMENT '删除时间',
	PRIMARY KEY(`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COMMENT '商品标签表';

-- 商品规格表（例如一件商品有颜色、大小等规格）
DROP TABLE IF EXISTS `spec`;
CREATE TABLE `spec`(
	`id` INT(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
	`spec_name` VARCHAR(50) NOT NULL COMMENT '规格名称',
	`unit` VARCHAR(50) NOT NULL COMMENT '规格的单位，例如数量的单位是个，尺寸的单位是米',
	`description` VARCHAR(255) COMMENT '描述',
	`status` TINYINT(4) NOT NULL DEFAULT 1 COMMENT '状态。1 可使用；2 被禁用',
	`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`delete_time` datetime DEFAULT NULL COMMENT '删除时间',
	PRIMARY KEY(`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COMMENT '商品规格表';

-- 商品规格详细表，例如商品的规格有颜色，那么颜色就有具体的颜色值：红色、蓝色和白色等
DROP TABLE IF EXISTS `spec_detail`;
CREATE TABLE `spec_detail`(
	`id` INT(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
	`spec_id` INT(10) NOT NULL COMMENT '关联 spec 表的 id 字段，商品规格的id',
	`value` VARCHAR(50) NOT NULL COMMENT '具体的规格名称',
	`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`delete_time` datetime DEFAULT NULL COMMENT '删除时间',
	PRIMARY KEY(`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COMMENT '商品规格详细表';

-- spu 表
DROP TABLE IF EXISTS `spu`;
CREATE TABLE `spu`(
	`id` INT(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
	`title` VARCHAR(50) NOT NULL COMMENT 'spu名称，例如苹果手机，华为手机',
	`sub_title` VARCHAR(50) NOT NULL COMMENT '副标题',
	`category_id` INT(11) NOT NULL COMMENT '关联 category 表的 id 字段，spu 分类',
	`tags` VARCHAR(50) COMMENT '关联标签表的id字段，由于标签允许有多个，所有标签之间使用逗号隔开',
	`oneline` TINYINT(4) NOT NULL DEFAULT 1 COMMENT '商品是否上线。1 为上线；2 为未上线',
	`price` VARCHAR(20) COMMENT '显示价格，在用户还没有选中具体的 sku 时显示的价格',
	`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`delete_time` datetime DEFAULT NULL COMMENT '删除时间',
	PRIMARY KEY(`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COMMENT 'spu表';

-- spu 的图片，一般现在都用来做轮播图。实际现在不止轮播图，还有视频播放，这里就不考虑视频等情况了。
DROP TABLE IF EXISTS `spu_img`;
CREATE TABLE `spu_img`(
	`id` INT(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
	`spu_id` INT(10) NOT NULL COMMENT '关联 spu 表的主键id',
	`img` VARCHAR(255) NOT NULL COMMENT '图片地址',
	`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`delete_time` datetime DEFAULT NULL COMMENT '删除时间',
	PRIMARY KEY(`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COMMENT 'spu图片表';

-- spu详细信息图片表，主要是用来显示商品的详情信息，以手机端举例，详细信息一般都放在商品的下面作为商品的介绍
DROP TABLE IF EXISTS `spu_detail_img`;
CREATE TABLE `spu_detail_img`(
	`id` INT(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
	`spu_id` INT(10) NOT NULL COMMENT '关联 spu 表的主键id',
	`img` VARCHAR(255) NOT NULL COMMENT '图片地址',
	`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`delete_time` datetime DEFAULT NULL COMMENT '删除时间',
	PRIMARY KEY(`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COMMENT 'spu详细信息图片表';

-- sku 表
DROP TABLE IF EXISTS `sku`;
CREATE TABLE `sku`(
	`id` INT(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
	`sku_name` VARCHAR(50) COMMENT 'sku名称',
	`spu_id` INT(10) NOT NULL COMMENT '关联 spu 表的主键id',
	`specs` json DEFAULT NULL COMMENT '关联 spec 与 spec_detail 两个表。sku 的详情信息，例如这款手机的详细规格为：玫瑰色、内存 16G、容量 256G',
	`price` VARCHAR(20) NOT NULL COMMENT '价格',
	`stock` INT(10) NOT NULL COMMENT '库存量',
	`img` VARCHAR(255) COMMENT '图片',
	`description` VARCHAR(255) COMMENT '描述',
	`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`delete_time` datetime DEFAULT NULL COMMENT '删除时间',
	PRIMARY KEY(`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COMMENT 'sku';

-- 订单表
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`(
	`id` INT(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
	`order_no` VARCHAR(50) NOT NULL UNIQUE COMMENT '订单编号',
	`user_id` int(10) NOT NULL COMMENT '关联 user 表的 id',
	`status` TINYINT(4) NOT NULL COMMENT '订单状态。1：待支付；2：已支付；3：已过期；4：已取消',
	`price` VARCHAR(20) NOT NULL COMMENT '订单总价格',
	`discount_price` VARCHAR(20) NOT NULL COMMENT '折扣后价格',
	`count` int(11) NOT NULL COMMENT '订单数量',
	`address_snapshoot` json COMMENT '地址镜像',
	`place_time` datetime NOT NULL COMMENT '下单时间',
	`expire_time` datetime NOT NULL COMMENT '订单过期时间',
	`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`delete_time` datetime DEFAULT NULL COMMENT '删除时间',
	PRIMARY KEY(`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COMMENT '订单表';

-- 订单详情表
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail` (
	`id` INT(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
	`order_id` INT(10) NOT NULL COMMENT '关联订单表的id',
	`spu_id` INT(10) NOT NULL COMMENT '关联 spu 表的 Id',
	`sku_id` INT(10) NOT NULL COMMENT '关联 sku 表的 id',
	`spu_snapshoot` json COMMENT 'spu 的快照',
	`sku_snapshoot` json COMMENT 'sku 的快照',
	`price` VARCHAR(20) NOT NULL COMMENT '总价格',
	`discount_price` VARCHAR(20) NOT NULL COMMENT '折扣后价格',
	`count` int(11) NOT NULL COMMENT '数量',
	`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`delete_time` datetime DEFAULT NULL COMMENT '删除时间',
	PRIMARY KEY(`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COMMENT '订单详情表';





