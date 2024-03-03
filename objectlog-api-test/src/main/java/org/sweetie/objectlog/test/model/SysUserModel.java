package org.sweetie.objectlog.test.model;/*
 * Copyright (C), 2021-2024
 * FileName: SysUserModel
 * Author gouhao
 * Date: 2024/3/2 18:44
 * Description:
 */

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.sweetie.objectlog.core.annotation.LogEntity;
import org.sweetie.objectlog.core.enums.AttributeTypeEnum;
import org.sweetie.objectlog.domain.BaseEntity;
import org.sweetie.objectlog.test.enums.StatusEnum;
import org.sweetie.objectlog.test.service.impl.SysRoleServiceImpl;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@ApiModel(value = "SysUserModel", description = "用户信息表")
@TableName("sys_user")
@LogEntity
public class SysUserModel extends BaseEntity {

    @LogEntity(alias = "角色信息", associationValue = true, serviceImplClass = SysRoleServiceImpl.class, entityFieldName = "roleName")
    @ApiModelProperty(name = "roleId",value = "角色表，角色id,逗号隔开")
    private String roleId;
    @LogEntity(alias = "用户昵称")
    @ApiModelProperty(name = "userName",value = "用户昵称")
    private String userName;
    @LogEntity(alias = "备注" ,attributeTypeEnum = AttributeTypeEnum.TEXT)
    @ApiModelProperty(name = "remark",value = "备注")
    private String remark;
    @LogEntity(alias = "富文本内容" , attributeTypeEnum = AttributeTypeEnum.RICHTEXT)
    @ApiModelProperty(name = "richText",value = "富文本内容")
    private String richText;
    @LogEntity(alias = "帐号状态",enumValue = true,enumClass = StatusEnum.class)
    @ApiModelProperty(name = "status",value = "帐号状态（0正常 1停用）")
    private Integer status;



    @TableField(exist = false)
    @ApiModelProperty(name = "roleModel",value = "角色信息")
    private SysRoleModel roleModel;
}
