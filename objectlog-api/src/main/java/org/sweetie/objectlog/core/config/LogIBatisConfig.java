package org.sweetie.objectlog.core.config;/*
 * Copyright (C), 2021-2023
 * FileName: LogIBatisConfig
 * Author gouhao
 * Date: 2023/12/2 15:55
 * Description:
 */

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.springframework.stereotype.Component;
import org.sweetie.objectlog.LogClient;
import org.sweetie.objectlog.core.annotation.LogEntity;
import org.sweetie.objectlog.core.constant.Constant;
import org.sweetie.objectlog.core.enums.OperationEnum;
import org.sweetie.objectlog.core.utils.ThreadLocalUtil;
import org.sweetie.objectlog.domain.BaseEntity;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.sql.Statement;
import java.sql.Wrapper;
import java.util.*;

@Component
@Slf4j
@Intercepts({
        @Signature(type = StatementHandler.class, method = "update",args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "batch",args = {Statement.class})
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
            if (methodFullName.startsWith(".delete")) {
                parameterObject = this.findObjectFromParameter(parameterField.get(parameterHandler), List.class);
                if (null != parameterObject && parameterObject instanceof List) {
                    List<String> idList = (List<String>) parameterObject;
                    idList = Optional.ofNullable(idList).orElseGet(ArrayList::new);
                    for (String id : idList) {
                        LogClient.getInstance().
                                logObject(id, ThreadLocalUtil.getParentId(),
                                        null, parameterObject,
                                        ThreadLocalUtil.getStringMapByKey(Constant.MODULE_NAME),ThreadLocalUtil.getStringMapByKey(Constant.REMARK),
                                        OperationEnum.DEL);
                    }
                }
                parameterObject = this.findObjectFromParameter(parameterField.get(parameterHandler), String.class);
                if (null != parameterObject && parameterObject instanceof String) {
                    LogClient.getInstance().logObject((String) parameterObject, ThreadLocalUtil.getParentId(),
                                    null, parameterObject,
                                    ThreadLocalUtil.getStringMapByKey(Constant.MODULE_NAME),ThreadLocalUtil.getStringMapByKey(Constant.REMARK),
                                    OperationEnum.DEL);
                }
                parameterObject = this.findObjectFromParameter(parameterField.get(parameterHandler), Wrapper.class);
                if (null != parameterObject && parameterObject instanceof Wrapper) {
                    this.parseWrapper(parameterObject);
                }
            } else {
                parameterObject = this.findObjectFromParameter(parameterField.get(parameterHandler), BaseEntity.class);
                LogEntity annotation = Optional.ofNullable(parameterObject).orElseGet(Object::new).getClass().getAnnotation(LogEntity.class);
                if (null != annotation) {
                    if (SqlCommandType.INSERT.name().equals(ms.getSqlCommandType().name())) {
                        if (ThreadLocalUtil.getBooleanMapByKey(Constant.COMPLEX_OPERATION)) {
                            LogClient.getInstance().
                                    logObject(ThreadLocalUtil.getParentId(), ((BaseEntity) parameterObject).getId(),
                                            null, null,
                                            parameterObject, ThreadLocalUtil.getStringMapByKey(Constant.MODULE_NAME), ThreadLocalUtil.getStringMapByKey(Constant.REMARK),
                                            OperationEnum.COMPLEX);
                            ThreadLocalUtil.setBooleanMapByKey(Constant.COMPLEX_OPERATION, false);
                        } else {
                            if (ThreadLocalUtil.getBooleanMapByKey(Constant.COMMENT_OPERATION)) {
                                LogClient.getInstance().
                                        logObject(ThreadLocalUtil.getParentId(), null, null,
                                                null, null,
                                                ThreadLocalUtil.getStringMapByKey(Constant.MODULE_NAME),ThreadLocalUtil.getStringMapByKey(Constant.REMARK),
                                                OperationEnum.COMMON);
                                ThreadLocalUtil.setBooleanMapByKey(Constant.COMMENT_OPERATION, false);
                            }
                            LogClient.getInstance().
                                    logObject(((BaseEntity) parameterObject).getId(), ThreadLocalUtil.getParentId(),
                                            null, parameterObject,
                                            ThreadLocalUtil.getStringMapByKey(Constant.MODULE_NAME), ThreadLocalUtil.getStringMapByKey(Constant.REMARK),
                                            OperationEnum.ADD);
                        }
                    }
                    if (SqlCommandType.UPDATE.name().equals(ms.getSqlCommandType().name())) {
                        if (ThreadLocalUtil.getBooleanMapByKey(Constant.COMPLEX_OPERATION)) {
                            String objectId = ((BaseEntity) parameterObject).getId();
                            Object oldObject = ThreadLocalUtil.getOldDataByKey(objectId);
                            LogClient.getInstance().
                                    logObject(ThreadLocalUtil.getParentId(), objectId, null,
                                            oldObject, parameterObject,
                                            ThreadLocalUtil.getStringMapByKey(Constant.MODULE_NAME), ThreadLocalUtil.getStringMapByKey(Constant.REMARK),
                                            OperationEnum.COMPLEX);
                            ThreadLocalUtil.setBooleanMapByKey(Constant.COMPLEX_OPERATION, false);
                        } else {
                            if (ThreadLocalUtil.getBooleanMapByKey(Constant.COMMENT_OPERATION)) {
                                LogClient.getInstance().
                                        logObject(ThreadLocalUtil.getParentId(), null, null,
                                                null, null,
                                                ThreadLocalUtil.getStringMapByKey(Constant.MODULE_NAME), ThreadLocalUtil.getStringMapByKey(Constant.REMARK),
                                                OperationEnum.COMMON);
                                ThreadLocalUtil.setBooleanMapByKey(Constant.COMMENT_OPERATION, false);
                            }
                            String objectId = ((BaseEntity) parameterObject).getId();
                            Object oldObject = ThreadLocalUtil.getOldDataByKey(objectId);
                            LogClient.getInstance().
                                    logObject(objectId, ThreadLocalUtil.getParentId(),
                                            oldObject, parameterObject,
                                            ThreadLocalUtil.getStringMapByKey(Constant.MODULE_NAME),ThreadLocalUtil.getStringMapByKey(Constant.REMARK),
                                            OperationEnum.UPDATE);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("LogIBatisConfig--", e);
        }
        return invocation.proceed();
    }

    private void parseWrapper(Object parameterObject) throws Throwable {
        ////1、获取预处理sql语句
        //Field sqlPlusField = parameterObject.getClass().getSuperclass().getDeclaredField("sql");
        //sqlPlusField.setAccessible(true);
        //MybatisSqlPlus sqlPlus = (MybatisSqlPlus) sqlPlusField.get(parameterObject);
        //Method sqlMethod = sqlPlus.getClass().getSuperclass().getDeclaredMethod("sql");
        //sqlMethod.setAccessible(true);
        ////得到的是 MybatisAbstractSQL.SQLCondition 对象
        //Object sqlCondition = sqlMethod.invoke(sqlPlus);
        //Field whereField = sqlCondition.getClass().getDeclareddField("where");
        //whereField.setAccessible(true);
        //List<String> whereValue = (List<String>) whereField.get(sqlCondition);
        ////2、获取参数map
        //Field paramMap = parameterObject.getClass().getSuperclass().getDeclaredField("paramNameValuePairs");
        //paramMap.setAccessible(true);
        //Map<String, Object> paramNameValuePairs = (Map<String,Object>) paramMap.get(parameterObject);
        ////解析fid
        //boolean existId = Optional.ofNullable(whereValue.get(0)).orElseGet(String::new).startsWith("fid");
        //if (existId) {
        //    String[] split = Optional.ofNullable(whereValue.get(0)).orElseGet(String::new).split("#");
        //    int size = split.length - 1;
        //    for (int i = 1; i < size; i++) {
        //        LogClient.getInstance().
        //                logObject((String) paramNameValuePairs.get("MPGENVAL" + i), ThreadLocalUtil.getParentId(), null,
        //                        parameterObject, ThreadLocalUtil.getStringMapByKey(Constant.MODULE_NAME), OperationEnum.DEL);
        //    }
        //}
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
    public static <T> T realTarget(Object target){
        if(Proxy.isProxyClass(target.getClass())){
            MetaObject metaObject = SystemMetaObject.forObject(target);
            return realTarget(metaObject.getValue("h.target"));
        }
        return (T) target;
    }

    @SuppressWarnings("unchecked")
    private <T> T findObjectFromParameter(Object parameter, Class<T> target){
        if(parameter== null|target== null){
            return null;
        }
        if(target.isAssignableFrom(parameter.getClass())){
            return (T) parameter;
        }
        if(parameter instanceof String){
            return (T) parameter;
        }

        if (parameter instanceof MapperMethod.ParamMap){
            MapperMethod.ParamMap<Object> paramMap = (MapperMethod.ParamMap<Object>) parameter;
            for (Map.Entry<String,Object> entry : paramMap.entrySet()){
                Object paramValue = entry.getValue();
                if (paramValue != null && target.isAssignableFrom(paramValue.getClass())){
                    return (T) paramValue;
                }
            }
        }

        if (parameter instanceof DefaultSqlSession.StrictMap){
            DefaultSqlSession.StrictMap<Object> paramMap = (DefaultSqlSession.StrictMap<Object>) parameter;
            for (Map.Entry<String,Object> entry : paramMap.entrySet()){
                Object paramValue = entry.getValue();
                if (paramValue != null && target.isAssignableFrom(paramValue.getClass())){
                    return (T) paramValue;
                }
            }
        }
        return null;
    }

}

