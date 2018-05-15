
CREATE DATABASE topbaby_rest;
USE topbaby_rest;

/*  access_key 表  */
CREATE TABLE `access_key` (
  `id` int NOT NULL AUTO_INCREMENT ,
  `access_key_id` varchar(128)   NOT NULL COMMENT '第三方用户唯一凭证',
  `access_secret` varchar(128)   NOT NULL COMMENT '第三方用户唯一凭证密钥',
  `type` varchar(36) NOT NULL  COMMENT '认证类型',
  `status` char(1)  COMMENT '备用状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO access_key(id,access_key_id,access_secret,type,status) VALUES (1,'topbabykeyid', 'topbabysecret', 'default', 'T');

/* qrcode_config */
CREATE TABLE `qrcode_config` (
  `id` int NOT NULL AUTO_INCREMENT ,
  `name` varchar(36) COMMENT '配置名称',
  `type` char(1) NOT NULL COMMENT 'U-导购员；B-门店；A-特殊活动；P-合作伙伴；O-其他',
  `user_id` bigint(20) COMMENT '角色(导购员、门店、活动)ID，若为0，则代表该配置为此类型的默认值',
  `target_url` varchar(255) NOT NULL COMMENT '需要跳转的链接地址',
  `target_url_name` varchar(36) COMMENT '链接简称',
  `qrcode_type` varchar(36) NOT NULL COMMENT '二维码保存类型：QR_SCENE-临时二维码；QR_LIMIT_SCENE-永久qrcode；QR_LIMIT_STR_SCENE=永久字符串参数类型qrcode',
  `content` varchar(255) NOT NULL COMMENT '推荐消息内容',
  `description` varchar(255) COMMENT '该配置的简单功能介绍',
  `status` char(1) NOT NULL COMMENT 'T-正常；F-注销；O-过期',
  `author` varchar(20) COMMENT '操作人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `end_time` timestamp DEFAULT '00-00-00 00:00:00' COMMENT '截止时间（若为空，或者为00-00-00 00:00:00，则为永久）',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO qrcode_config(name,type,user_id,target_url,target_url_name,qrcode_type,content,description,status,author) VALUES ('导购员默认配置','U',0,'https://www.61topbaby.com/evercos/member/register/index.html?brandshopUserSeq=','点击注册淘璞会员','QR_LIMIT_STR_SCENE','默认推送信息','默认专用','T','sxy');

/* qrcode */
CREATE TABLE `qrcode` (
  `id` int NOT NULL AUTO_INCREMENT ,
  `user_id` bigint(20) COMMENT 'type=U:导购员ID;type=P:合作伙伴ID;type=B:门店ID;type=A:活动ID',
  `brandshop_id` bigint(20) COMMENT '门店ID',
  `ticket` varchar(255) NOT NULL COMMENT '获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码',
  `expire_seconds` int COMMENT '该二维码有效时间，以秒为单位。 最大不超过2592000（即30天）',
  `url` varchar(255) NOT NULL COMMENT '二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片',
  `image_id` int COMMENT '二维码图片ID',
  `type` char(1) NOT NULL COMMENT 'U-导购员;B-门店;A-特殊活动;P-合作伙伴;O-其他',
  `status` char(1) NOT NULL DEFAULT 'T' COMMENT 'T-正常（默认）;F-注销;O-过期',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;