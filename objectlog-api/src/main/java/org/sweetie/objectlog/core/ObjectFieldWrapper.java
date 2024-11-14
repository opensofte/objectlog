package org.sweetie.objectlog.core;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.sweetie.objectlog.core.annotation.LogEntity;
import org.sweetie.objectlog.core.handler.AttributeTypeHandler;
import org.sweetie.objectlog.core.handler.AttributeValueHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Data
public class ObjectFieldWrapper {
    @ApiModelProperty(name = "oldObject", value = "旧对象,该属性是插入前数据")
    private Object oldObject;
    @ApiModelProperty(name = "newObject", value = "新对象,该属性是插入时数据")
    private Object newObject;
    @ApiModelProperty(name = "attributeName", value = "属性名称")
    private String attributeName;
    @ApiModelProperty(name = "attributeAlias", value = "主解的属性名称,如果不存在则使用attributeName")
    private String attributeAlias;
    @ApiModelProperty(name = "oldValue", value = "属性的旧值")
    private Object oldValue;
    @ApiModelProperty(name = "newValue", value = "属性的新值")
    private Object newValue;
    @ApiModelProperty(name = "oldValueString", value = "属性旧值字符串")
    private String oldValueString;
    @ApiModelProperty(name = "newValueString", value = "属性新值字符串")
    private String newValueString;
    @ApiModelProperty(name = "withLogEntity", value = "是否有注解")
    private boolean withLogEntity;
    @ApiModelProperty(name = "logEntity", value = "属性注解")
    private LogEntity logEntity;
    @ApiModelProperty(name = "withExtendedType", value = "是否是外部类型")
    private boolean withExtendedType;
    @ApiModelProperty(name = "extendedType", value = "外部类型具体值")
    private String extendedType;
    @ApiModelProperty(name = "extendedTypeHandler", value = "外部类型处理器")
    private Class<? extends AttributeTypeHandler> extendedTypeHandler;
    @ApiModelProperty(name = "extendedValue", value = "外部值处理器开启")
    private boolean extendedValue;
    @ApiModelProperty(name = "extendedTypeHandler", value = "外部值处理器")
    private Class<? extends AttributeValueHandler> attributeValueHandler;
    private boolean associationValue;
    private Class<? extends ServiceImpl> serviceImplClass;
    private String serviceImplClassPath;
    private String entityFieldName;

    public <E> ObjectFieldWrapper(Field field, Object oldValue, Object newValue, Object old0bject, Object newObject) {
        this.logEntity = field.getAnnotation(LogEntity.class);
        this.withLogEntity = logEntity != null;
        if (!this.withLogEntity) {
            return;
        }
        this.attributeName = field.getName();
        this.oldObject = old0bject;
        this.newObject = newObject;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.oldValueString = oldValue == null ? "" : oldValue.toString();
        this.newValueString = newValue == null ? "" : newValue.toString();
        this.attributeAlias = (withLogEntity && logEntity.alias().length() != 0) ? logEntity.alias() : field.getName();

        this.withExtendedType = withLogEntity && logEntity.extendedType().length() != 0;
        this.extendedType = withExtendedType ? logEntity.extendedType() : null;
        this.extendedTypeHandler = withExtendedType ? logEntity.attributeTypeHandler() : null;

        this.extendedValue = (withLogEntity && logEntity.extendedValue());
        this.attributeValueHandler = (extendedValue ? logEntity.attributeValueHandler() : null);

        this.associationValue = (withLogEntity && logEntity.associationValue());
        this.serviceImplClass = (associationValue ? (logEntity.serviceImplClass()) : null);
        this.serviceImplClassPath = (associationValue ? logEntity.serviceImplClassPath() : null);
        this.entityFieldName = (associationValue ? logEntity.entityFieldName() : null);

        if (withLogEntity && logEntity.enumValue()) {
            Class<?> aClass = logEntity.enumClass();
            try {
                Method valuesMethod = aClass.getMethod("values");
                Object valuesObj = valuesMethod.invoke(aClass);
                E[] values = (E[]) valuesObj;
                Field keyField;
                Field valueField;
                String key;
                String value;
                for (E e : values) {
                    keyField = e.getClass().getDeclaredField("key");
                    keyField.setAccessible(true);
                    key = String.valueOf(keyField.get(e));
                    if (key.equals(oldValueString)) {
                        valueField = e.getClass().getDeclaredField("value");
                        valueField.setAccessible(true);
                        value = String.valueOf(valueField.get(e));
                        this.oldValueString = String.valueOf(value);
                    }
                    if (key.equals(newValueString)) {
                        valueField = e.getClass().getDeclaredField("value");
                        valueField.setAccessible(true);
                        value = String.valueOf(valueField.get(e));
                        this.newValueString = String.valueOf(value);
                    }
                }
            } catch (Exception e) {
                //允许异常
            }
        }
    }
}