CREATE TABLE `mod_module` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `module_name` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '组件名称',
  `module_code` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '组件code',
  `module_desc` varchar(256) COLLATE utf8_bin DEFAULT '' COMMENT '组件描述',
  `min_version` int(11) NOT NULL COMMENT '组件最低支持版本',
  `max_version` int(11) NOT NULL COMMENT '组件最高支持版本',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除0 未删除 1 已删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `index_mod_module_create_time` (`create_time`) USING BTREE COMMENT '创建时间索引',
  KEY `index_mod_module_update_time` (`update_time`) USING BTREE COMMENT '更新时间索引'
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;