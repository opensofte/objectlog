package org.sweetie.objectlog.core.strategy;
/*
 * FileName: AbstractAttributeParseStrategy
 * Author gouhao
 */

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.sweetie.objectlog.core.ObjectFieldWrapper;
import org.sweetie.objectlog.core.ObjectLogTask;
import org.sweetie.objectlog.core.annotation.LogEntity;
import org.sweetie.objectlog.core.enums.AttributeTypeEnum;
import org.sweetie.objectlog.core.handler.AttributeTypeHandler;
import org.sweetie.objectlog.core.handler.BaseAttributeTypeHandler;
import org.sweetie.objectlog.core.handler.DefaultAttributeTypeHandler;
import org.sweetie.objectlog.core.model.ObjectAttributeModel;
import org.sweetie.objectlog.core.model.ObjectOperationModel;
import org.sweetie.objectlog.core.utils.ClassUtil;
import org.sweetie.objectlog.domain.BaseEntity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractAttributeParseStrategy implements AttributeParseStrategy {

    protected Object getRealParamterObject(Object parameterObject) {
        parameterObject = this.findObjectFromParameter(parameterObject, BaseEntity.class);
        LogEntity annotation = Optional.ofNullable(parameterObject).orElseGet(Object::new).getClass().getAnnotation(LogEntity.class);
        return annotation != null ? parameterObject : null;
    }

    protected ObjectOperationModel getObject(ObjectLogTask task) {
        //处理基础信息
        ObjectOperationModel model = new ObjectOperationModel();
        model.setId(task.getId());
        model.setObjectId(task.getObjectId());
        model.setParentId(task.getParentId());
        model.setOperationType(task.getOperationType().name());
        model.setModuleName(task.getModuleName());
        model.setComment(task.getComment());
        return model;
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
                    }
                    if (baseAttributeModel != null) {
                        attributeModelList.add(baseAttributeModel);
                    }
                }
            }
        }
        return attributeModelList;
    }

    private ObjectAttributeModel handleBaseAttributeType(ObjectFieldWrapper fieldWrapper) {
        AttributeTypeEnum baseAttributeType = AttributeTypeEnum.NORMAL;
        if (fieldWrapper.getLogEntity() != null) {
            baseAttributeType = fieldWrapper.getLogEntity().attributeTypeEnum();
        }
        return DefaultAttributeTypeHandler.handlerAttributeChange(fieldWrapper, baseAttributeType);
    }

    private ObjectAttributeModel handleExtendedAttributeType(ObjectFieldWrapper fieldWrapper) {
        AttributeTypeHandler attributeTypeHandler = ClassUtil.getInstance(fieldWrapper.getExtendedTypeHandler());
        if (null == attributeTypeHandler) {
            attributeTypeHandler = new BaseAttributeTypeHandler();
        }
        return attributeTypeHandler.handlerAttributeChange(fieldWrapper);
    }


    private boolean nullableEquals(Object a, Object b) {
        return (a == null && b == null) || (a != null && a.equals(b));
    }

    @SuppressWarnings("unchecked")
    protected static <T> T findObjectFromParameter(Object parameter, Class<T> target) {
        if (parameter == null | target == null) {
            return null;
        }
        if (target.isAssignableFrom(parameter.getClass())) {
            return (T) parameter;
        }
        if (parameter instanceof String) {
            return (T) parameter;
        }

        if (parameter instanceof MapperMethod.ParamMap) {
            MapperMethod.ParamMap<Object> paramMap = (MapperMethod.ParamMap<Object>) parameter;
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                Object paramValue = entry.getValue();
                if (paramValue != null && target.isAssignableFrom(paramValue.getClass())) {
                    return (T) paramValue;
                }
            }
        }

        if (parameter instanceof DefaultSqlSession.StrictMap) {
            DefaultSqlSession.StrictMap<Object> paramMap = (DefaultSqlSession.StrictMap<Object>) parameter;
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                Object paramValue = entry.getValue();
                if (paramValue != null && target.isAssignableFrom(paramValue.getClass())) {
                    return (T) paramValue;
                }
            }
        }
        return null;
    }
}
