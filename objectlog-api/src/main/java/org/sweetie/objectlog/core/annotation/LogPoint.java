package org.sweetie.objectlog.core.annotation;/*
 * Copyright (C), 2021-2023
 * FileName: LogPoint
 * Author gouhao
 * Date: 2023/12/2 15:16
 * Description:
 */

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.sweetie.objectlog.core.enums.OperationEnum;
import org.sweetie.objectlog.domain.BaseEntity;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogPoint {
    Class<? extends ServiceImpl> serviceHandler();
    Class<? extends BaseEntity> entityHandler();
    OperationEnum operation() default OperationEnum.BASE;
    String moduleName() default "";
    String remark() default "";
    boolean multiple() default false;
}
