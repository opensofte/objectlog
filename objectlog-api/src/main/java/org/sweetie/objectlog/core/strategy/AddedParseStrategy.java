package org.sweetie.objectlog.core.strategy;/*
 * Copyright (C), 2021-2024
 * FileName: AddedParseStrategy
 * Author gouhao
 * Date: 2024/2/25 17:39
 * Description:
 */

import cn.hutool.json.JSONUtil;
import org.sweetie.objectlog.core.annotation.LogEntity;
import org.sweetie.objectlog.core.model.ObjectAttributeModel;
import org.sweetie.objectlog.domain.ObjectOperationDto;

import java.util.List;

public class AddedParseStrategy extends AbstractAttributeParseStrategy {
    @Override
    public boolean doParse(ObjectOperationDto model, Object modelObject, Object oldObject) throws IllegalAccessException {
        List<ObjectAttributeModel> attributeModels = super.dealAttributeModelList(modelObject, oldObject);
        LogEntity LogEntity = modelObject.getClass().getAnnotation(LogEntity.class);
        if (null != LogEntity) {
            model.setObject(JSONUtil.toJsonStr(modelObject));
        }
        model.setObjectName(modelObject.getClass().getSimpleName());
        model.setAttributes(attributeModels);
        return true;
    }
}
