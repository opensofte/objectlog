package org.sweetie.objectlog.core.model;

import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.sweetie.objectlog.domain.BaseEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@ApiModel(value = "ObjectAttributeModel", description="对象属性变更记录表")
@TableName("object_attribute")
public class ObjectAttributeModel extends BaseEntity {
    @ApiModelProperty(name = "operationId",value = "操作记录id")
    private String operationId;
    @ApiModelProperty(name = "attributeType",value = "字段类型(NORMAL,RICHTEXT,TEXT)")
    private String attributeType;
    @ApiModelProperty(name = "attributeName",value = "字段名称")
    private String attributeName;
    @ApiModelProperty(name = "attributeAlias", value = "字段名称(中文)")
    private String attributeAlias;
    @ApiModelProperty(name = "oldValue" , value = "旧值")
    private String oldValue;
    @ApiModelProperty(name = "newValue", value = "新值")
    private String newValue;
    @ApiModelProperty(name = "diffValue", value = "不同点详细说明")
    private String diffValue;
}

