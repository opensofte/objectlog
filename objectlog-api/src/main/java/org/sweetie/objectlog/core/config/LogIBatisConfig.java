package org.sweetie.objectlog.core.config;
/*
 * FileName: LogIBatisConfig
 * Author gouhao
 */

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;
import org.sweetie.objectlog.core.constant.Constant;
import org.sweetie.objectlog.core.strategy.AttributeParseStrategy;
import org.sweetie.objectlog.core.strategy.AttributeStrategyFactory;
import org.sweetie.objectlog.core.utils.ThreadLocalUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.sql.Statement;
import java.util.Properties;

@Component
@Slf4j
@Intercepts({
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class})
})
public class LogIBatisConfig implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        try {
            if (!ThreadLocalUtil.getBooleanMapByKey(Constant.OPEN)) {
                return invocation.proceed();
            }
            Object realTarget = realTarget(invocation.getTarget());
            MetaObject metaobject = SystemMetaObject.forObject(realTarget);
            MappedStatement ms = (MappedStatement) metaobject.getValue("delegate.mappedStatement");
            ParameterHandler parameterHandler = (ParameterHandler) metaobject.getValue("delegate.parameterHandler");
            Field parameterField = parameterHandler.getClass().getDeclaredField("parameterObject");
            parameterField.setAccessible(true);
            String methodFullName = ms.getId().substring(ms.getId().lastIndexOf("."));
            Object parameterObject = parameterField.get(parameterHandler);
            AttributeParseStrategy strategy = AttributeStrategyFactory.getParseStrategy(ThreadLocalUtil.getLogPoint().operation());
            if (strategy != null) {
                strategy.parse(ms.getSqlCommandType(), parameterObject, methodFullName);
            }
        } catch (Exception e) {
            log.error("LogIBatisConfig--", e);
        }
        return invocation.proceed();
    }


    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties prop) {

    }

    @SuppressWarnings("unchecked")
    public static <T> T realTarget(Object target) {
        if (Proxy.isProxyClass(target.getClass())) {
            MetaObject metaObject = SystemMetaObject.forObject(target);
            return realTarget(metaObject.getValue("h.target"));
        }
        return (T) target;
    }

}

