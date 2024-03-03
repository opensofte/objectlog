CREATE TABLE `object_attribute`  (
  `id` varchar(48) NOT NULL COMMENT '角色ID',	
  `operation_id` varchar(48) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '操作记录id',
  `attribute_type` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '字段类型(NORMAL,RICHTEXT,TEXT)',
  `attribute_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '字段名称',
  `attribute_alias` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '字段名称(中文)',
  `old_value` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '旧值',
  `new_value` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '新值',
  `diff_value` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '不同点详细说明',
  `del_flag` int(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
  `create_time` datetime NULL COMMENT '创建时间',
  `update_time` datetime NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '对象属性变更记录表';


