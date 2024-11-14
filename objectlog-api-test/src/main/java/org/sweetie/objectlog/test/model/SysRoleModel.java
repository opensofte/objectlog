package org.sweetie.objectlog.test.model;
/*
 * FileName: SysUserModel
 * Author gouhao
 */

import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.sweetie.objectlog.core.annotation.LogEntity;
import org.sweetie.objectlog.domain.BaseEntity;
import org.sweetie.objectlog.test.enums.StatusEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@ApiModel(value = "SysRoleModel", description = "角色信息表")
@TableName("sys_role")
@LogEntity
public class SysRoleModel extends BaseEntity {
    @LogEntity(alias = "角色名称")
    @ApiModelProperty(name = "roleName", value = "角色名称")
    private String roleName;
    @ApiModelProperty(name = "roleKey", value = "角色权限字符串")
    private String roleKey;
    @ApiModelProperty(name = "roleSort", value = "显示顺序")
    private Integer roleSort;
    @ApiModelProperty(name = "status", value = "角色状态（0正常 1停用）")
    @LogEntity(alias = "角色状态", enumValue = true, enumClass = StatusEnum.class)
    private Integer status;
}
