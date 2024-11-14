package org.sweetie.objectlog.core.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.sweetie.objectlog.domain.BaseEntity;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@ApiModel(value = "ObjectOperationModel", description = "对象操作记录表")
@TableName("object_operation")
public class ObjectOperationModel extends BaseEntity {
    @ApiModelProperty(name = "parentId", value = "父操作fid")
    private String parentId;
    @ApiModelProperty(name = "moduleName", value = "模块名称")
    private String moduleName;
    @ApiModelProperty(name = "objectName", value = "对于象名称(类名称)")
    private String objectName;
    @ApiModelProperty(name = "objectId", value = "对象id")
    private String objectId;
    @ApiModelProperty(name = "object", value = "对象信息")
    private String object;
    @ApiModelProperty(name = "operationType", value = "操作类型")
    private String operationType;
    @ApiModelProperty(name = "version", value = "版本号")
    private Integer version;
    @ApiModelProperty(name = "comment", value = "备注")
    private String comment;
    @ApiModelProperty(name = "attributes", value = "对象属性变更记录表")
    @TableField(exist = false)
    private List<ObjectAttributeModel> attributes;
}
