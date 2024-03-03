
CREATE TABLE `sys_user`  (
  `id` varchar(48) NOT NULL COMMENT '用户ID',	
  `role_id` varchar(48) NULL COMMENT '角色ID',
  `user_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '用户昵称',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL  COMMENT '备注',
  `rich_text` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '富文本内容',
  `status` int(1) DEFAULT 0 COMMENT '帐号状态（0正常 1停用）',
  `del_flag` int(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表';

