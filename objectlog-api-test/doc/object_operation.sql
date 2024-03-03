
CREATE TABLE `object_operation`  (
  `id` varchar(48) NOT NULL COMMENT '操作ID',
  `parent_id` varchar(48) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '父操作fid',
  `object_id` varchar(48) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '对象id',
  `object` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '对象信息',
  `object_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '对于象名称(类名称)',
  `module_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '模块名称',
  `operation_type` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '操作类型',
  `version` int(1) default 1 COMMENT '版本号',
  `comment` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '备注',
  `del_flag` int(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
  `create_time` datetime NULL COMMENT '创建时间',
  `update_time` datetime NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '对象操作记录表';
