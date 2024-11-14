package org.sweetie.objectlog.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.sweetie.objectlog.core.model.ObjectOperationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class ObjectOperationDto extends ObjectOperationModel {
    @ApiModelProperty(name = "ids", value = "操作记录id集合(逗号隔开")
    private String ids;
    @ApiModelProperty(name = "objectIds", value = "对象io集合(逗号隔开)")
    private String objectIds;
    @ApiModelProperty(name = "parentIds", value = "父操作id集合(逗号隔开)")
    private String parentIds;
    @ApiModelProperty(name = "operateTypeName", value = "操作类型")
    private String operationTypeName;
    @ApiModelProperty(name = "operatorName", value = "操作人")
    private String operatorName;
    @ApiModelProperty(name = "pageNum", value = "页号")
    private Integer currentPage;
    @ApiModelProperty(name = "pageSize", value = "页大小")
    private Integer pageSize;
}
