CREATE TABLE `sys_role`
(
    `id`          varchar(48) NOT NULL COMMENT '角色ID',
    `role_name`   varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '角色名称',
    `role_key`    varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '角色权限字符串',
    `role_sort`   int(4) NULL COMMENT '显示顺序',
    `status`      int(1) NULL COMMENT '角色状态（0正常 1停用）',
    `del_flag`    int(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
    `create_time` datetime NULL COMMENT '创建时间',
    `update_time` datetime NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色信息表';