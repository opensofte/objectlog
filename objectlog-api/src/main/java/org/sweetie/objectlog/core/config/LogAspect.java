package org.sweetie.objectlog.core.config;/*
 * Copyright (C), 2021-2023
 * FileName: LogTagAspect
 * Author gouhao
 * Date: 2023/12/2 15:46
 * Description:
 */

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.sweetie.objectlog.LogClient;
import org.sweetie.objectlog.core.annotation.LogPoint;
import org.sweetie.objectlog.core.constant.Constant;
import org.sweetie.objectlog.core.enums.OperationEnum;
import org.sweetie.objectlog.core.utils.SpringBeanContextUtil;
import org.sweetie.objectlog.core.utils.ThreadLocalUtil;
import org.sweetie.objectlog.core.utils.UuidUtil;
import org.sweetie.objectlog.domain.BaseEntity;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(LogClient.class);
    @Pointcut("@annotation(org.sweetie.objectlog.core.annotation.LogPoint)")
          public void objectLogPointCut() {
      }

    @Before(value = "objectLogPointCut()")
    public <T extends BaseEntity> void saveObject(JoinPoint joinPoint) throws Throwable {
        //清空数据集合当前线程缓存变量
        ThreadLocalUtil.resetCurrentThreadInfo();
        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();
        //参数集合
        Object [] args = joinPoint.getArgs();
        LogPoint annotation = method.getAnnotation(LogPoint.class);
        try{
            if(null != annotation) {
                if (annotation.operation().getDone()){
                    this.dealOldData(annotation, args);
                }
                if (annotation.multiple() && StrUtil.isBlank(ThreadLocalUtil.getParentId())){
                    ThreadLocalUtil.setParentId(UuidUtil.getUUID());
                }
                this.parse(annotation);
            }
        } catch (Throwable e){
            logger.error(method.getName() + "控制器执行异常" + JSONUtil.toJsonStr(args));
            throw e;
        }
    }

    private void parse(LogPoint annotation) {
        HashMap<String,String > stringMap = new HashMap<>(2,1f);
        stringMap.put(Constant.MODULE_NAME, annotation.moduleName());
        stringMap.put(Constant.REMARK, annotation.remark());
        ThreadLocalUtil.setStringMap(stringMap);

        HashMap<String,Boolean> booleanMap = new HashMap<>(4,1f);
        booleanMap.put(Constant.OPEN, true);
        booleanMap.put(Constant.COMMENT_OPERATION, OperationEnum.COMMON == annotation.operation());
        booleanMap.put(Constant.COMPLEX_OPERATION, OperationEnum.COMPLEX == annotation.operation());
        ThreadLocalUtil.setBooleanMap(booleanMap);
    }

    private <T extends BaseEntity > void dealOldData (LogPoint annotation, Object[]args){ 
        if (null == annotation){
            return;
        }
        List<T> updateModelList = this.getUpdateModelData(args, annotation);
        //获取历史数据
        if (CollUtil.isNotEmpty(updateModelList)) {
            Class<? extends ServiceImpl> serviceClass = annotation.serviceHandler();
            ServiceImpl service = SpringBeanContextUtil.getContext().getBean(serviceClass);
            List<String> idList = updateModelList.stream().map(BaseEntity::getId).collect(Collectors.toList());
            List<? extends BaseEntity> oldList = service.selectBatchIds(idList);
            HashMap<String, ? extends BaseEntity> oldInfoMap = Optional.ofNullable(oldList).orElseGet(ArrayList::new)
                    .stream().collect(Collectors.toMap(BaseEntity::getId, item -> item, (k1, k2) -> k2, HashMap::new));
            //存储修改数据的旧值
            ThreadLocalUtil.setOldDataMap(oldInfoMap);
        }
    }

    private <T extends BaseEntity> List<T> getUpdateModelData(Object[] args, LogPoint annotation){
        if(args.length <1) {
            return Collections.emptyList();
        }
        Class<T> entityClass = (Class<T>) annotation.entityHandler();
        List<Object> dataList = new ArrayList<>(4);
        for (Object item : args) {
            this.getTypeResult(item, dataList);
        }
        List<T> updateModelList = new ArrayList<>(10);
        T model;
        for (Object obj: dataList) {
            model = entityClass.cast(obj);
            if (null != model.getId()) {
                updateModelList.add(model);
            }
        }
        return updateModelList;
    }

    private void getTypeResult(Object item, List<Object> expectData) {
        if(null == item){
            return;
        }
        if(BaseEntity.class.isAssignableFrom(item.getClass())){
            expectData.add(item);
        }
         if ( item instanceof List){
            List<Object> data = (List) item;
            if(CollUtil.isNotEmpty(data) && BaseEntity.class.isAssignableFrom(data.get(0).getClass())){
                expectData.addAll(data);
            }
        }
         if(item instanceof Map) {
            for (Object entry : ((Map) item).values()) {
                this.getTypeResult(entry, expectData);
            }
         }
    }
}



