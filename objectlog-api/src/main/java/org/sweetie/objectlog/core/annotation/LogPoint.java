package org.sweetie.objectlog.core.annotation;
/*
 * FileName: LogPoint
 * Author gouhao
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
}
