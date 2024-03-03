package org.sweetie.objectlog.core.handler;/*
 * Copyright (C), 2021-2023
 * FileName: DefaultAttributeTypeHandler
 * Author gouhao
 * Date: 2023/12/2 17:12
 * Description:
 */

import cn.hutool.core.util.StrUtil;
import org.sweetie.objectlog.core.enums.AttributeTypeEnum;
import org.sweetie.objectlog.core.model.ObjectAttributeModel;
import org.sweetie.objectlog.core.utils.Html2TextUtil;
import org.sweetie.objectlog.core.ObjectFieldWrapper;

public class DefaultAttributeTypeHandler extends AbstractAttributeTypeHandler {
    @Override
    public ObjectAttributeModel handlerAttributeChange(ObjectFieldWrapper fieldWrapper) {
        return null;
    }
    public static ObjectAttributeModel handlerAttributeChange(ObjectFieldWrapper fieldWrapper, AttributeTypeEnum attributeTypeEnum) {
        ObjectAttributeModel attributeModel = null;
        switch (attributeTypeEnum) {
            case NORMAL:
                attributeModel = dealNormal(fieldWrapper);
                break;
            case RICHTEXT:
                attributeModel = dealRichtext(fieldWrapper);
                break;
            case TEXT:
                attributeModel = dealText(fieldWrapper);
                break;
            default:
                break;
        }
        return attributeModel;
    }
    public static ObjectAttributeModel dealNormal(ObjectFieldWrapper fieldWrapper){
        ObjectAttributeModel attributeModel = new ObjectAttributeModel();
        attributeModel.setDiffValue(DefaultAttributeValueHandler.handlerValue(fieldWrapper, AttributeTypeEnum.NORMAL, true));
        dealBaseInfo(fieldWrapper, attributeModel, AttributeTypeEnum.NORMAL.name());
        return attributeModel;
    }

    public static ObjectAttributeModel dealRichtext(ObjectFieldWrapper fieldWrapper){
        String simpleOldValue = Html2TextUtil.simpleHtml(fieldWrapper.getOldValueString());
        String simpleNewValue = Html2TextUtil.simpleHtml(fieldWrapper.getNewValueString());
        if ((StrUtil.isBlank(simpleOldValue) && StrUtil.isBlank(simpleNewValue)) ||
                (StrUtil.isNotBlank(simpleOldValue) && simpleOldValue.equals(simpleNewValue))) {
            return null;
        } else {
            ObjectAttributeModel attributeModel = new ObjectAttributeModel();
            attributeModel.setDiffValue(DefaultAttributeValueHandler.handlerValue(fieldWrapper, AttributeTypeEnum.RICHTEXT,true));
            dealBaseInfo(fieldWrapper, attributeModel, AttributeTypeEnum.RICHTEXT.name());
            return attributeModel;
        }
    }

    public static ObjectAttributeModel dealText(ObjectFieldWrapper fieldWrapper){
        String oldValue = fieldWrapper.getOldValueString();
        String newValue = fieldWrapper.getNewValueString();
        if ((StrUtil.isBlank(oldValue) & StrUtil.isBlank(newValue))|| (oldValue != null & oldValue.equals(newValue))) {
            return null;
        }else {
            ObjectAttributeModel attributeModel = new ObjectAttributeModel();
            attributeModel.setDiffValue(DefaultAttributeValueHandler.handlerValue(fieldWrapper, AttributeTypeEnum.TEXT,true));
            dealBaseInfo(fieldWrapper, attributeModel, AttributeTypeEnum.TEXT.name());
            return attributeModel;
        }
    }
}
