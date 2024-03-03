package org.sweetie.objectlog.core.annotation;/*
 * Copyright (C), 2021-2023
 * FileName: LogEntity
 * Author gouhao
 * Date: 2023/12/2 15:33
 * Description:
 */

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.sweetie.objectlog.core.enums.AttributeTypeEnum;
import org.sweetie.objectlog.core.handler.AttributeTypeHandler;
import org.sweetie.objectlog.core.handler.AttributeValueHandler;
import org.sweetie.objectlog.core.handler.BaseAttributeTypeHandler;
import org.sweetie.objectlog.core.handler.BaseAttributeValueHandler;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogEntity {
    String alias() default "";
    AttributeTypeEnum attributeTypeEnum() default AttributeTypeEnum.NORMAL;

    String extendedType() default "";
    Class<? extends AttributeTypeHandler> attributeTypeHandler() default BaseAttributeTypeHandler.class;

    boolean extendedValue() default false;
    Class<? extends AttributeValueHandler> attributeValueHandler() default BaseAttributeValueHandler.class;

    boolean enumValue() default false;
    Class<?> enumClass() default Enum.class;

    boolean associationValue() default false;
    Class<? extends ServiceImpl> serviceImplClass() default ServiceImpl.class;
    String serviceImplClassPath() default "";
    String entityFieldName() default "";


}
