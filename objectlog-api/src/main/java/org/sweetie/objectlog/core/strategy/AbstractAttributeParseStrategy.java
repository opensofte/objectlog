package org.sweetie.objectlog.core.strategy;/*
 * Copyright (C), 2021-2024
 * FileName: AbstractAttributeParseStrategy
 * Author gouhao
 * Date: 2024/2/25 17:44
 * Description:
 */

import org.sweetie.objectlog.core.ObjectFieldWrapper;
import org.sweetie.objectlog.core.enums.AttributeTypeEnum;
import org.sweetie.objectlog.core.handler.AttributeTypeHandler;
import org.sweetie.objectlog.core.handler.BaseAttributeTypeHandler;
import org.sweetie.objectlog.core.handler.DefaultAttributeTypeHandler;
import org.sweetie.objectlog.core.utils.ClassUtil;
import org.sweetie.objectlog.core.model.ObjectAttributeModel;
import org.sweetie.objectlog.domain.ObjectOperationDto;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAttributeParseStrategy implements AttributeParseStrategy {
    @Override
    public boolean doParse(ObjectOperationDto model, Object modelObject, Object oldObject) throws IllegalAccessException {
        return true;
    }


    public List<ObjectAttributeModel> dealAttributeModelList(Object modelObject, Object oldObject) throws IllegalAccessException {
        List<ObjectAttributeModel> attributeModelList = new ArrayList<>(10);
        ObjectAttributeModel baseAttributeModel;
        Class modelClazz = modelObject.getClass();
        List<Field> fieldList = ClassUtil.getFields(modelClazz);
        for (Field field : fieldList) {
            field.setAccessible(true);
            //获取字段上的@LogEntity注解 同时对比本次model的新旧值
            ObjectFieldWrapper fieldWrapper = new ObjectFieldWrapper(field, ((null == oldObject) ? null : field.get(oldObject)), field.get(modelObject), oldObject, modelObject);
            //字段被@LogEntity注解标记
            if (fieldWrapper.isWithLogEntity()) {
                if (!nullableEquals(fieldWrapper.getOldValue(), fieldWrapper.getNewValue())) {
                    //外部类型 则使用
                    if (fieldWrapper.isWithExtendedType()) {
                        baseAttributeModel = handleExtendedAttributeType(fieldWrapper);
                    } else {
                        baseAttributeModel = handleBaseAttributeType(fieldWrapper);
                        if (baseAttributeModel != null) {
                            attributeModelList.add(baseAttributeModel);
                        }
                    }
                }
            }
        }
        return attributeModelList;
    }

    private ObjectAttributeModel handleBaseAttributeType(ObjectFieldWrapper fieldWrapper){
        AttributeTypeEnum baseAttributeType = AttributeTypeEnum.NORMAL;
        if(fieldWrapper.getLogEntity()!= null){
            baseAttributeType = fieldWrapper.getLogEntity().attributeTypeEnum();
        }
        return DefaultAttributeTypeHandler.handlerAttributeChange(fieldWrapper, baseAttributeType);
    }

    private ObjectAttributeModel handleExtendedAttributeType(ObjectFieldWrapper fieldWrapper){
        AttributeTypeHandler attributeTypeHandler = ClassUtil.getInstance(fieldWrapper.getExtendedTypeHandler());
        if(null == attributeTypeHandler) {
            attributeTypeHandler = new BaseAttributeTypeHandler();
        }
        return attributeTypeHandler.handlerAttributeChange(fieldWrapper);
    }


    private boolean nullableEquals(Object a, Object b) {
        return (a == null && b == null) || (a != null && a.equals(b));
    }
}
