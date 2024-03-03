package org.sweetie.objectlog;/*
 * Copyright (C), 2021-2023
 * FileName: LogClient
 * Author gouhao
 * Date: 2023/12/2 15:10
 * Description:
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.sweetie.objectlog.core.enums.OperationEnum;
import org.sweetie.objectlog.core.ObjectLogTask;
import org.sweetie.objectlog.core.utils.HttpRequestUtil;
import org.sweetie.objectlog.feign.ObjectLogFeignClient;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class  LogClient {

    private final Logger logger = LoggerFactory.getLogger(LogClient.class);
    private static LogClient instance;
    @Resource
    private ObjectLogFeignClient objectLogFeignClient;

    //指定线程池
    @Autowired
    @Qualifier(value = "objectlog")
    private ThreadPoolTaskExecutor taskExecutor;
    //利用生命周期初始化
    @PostConstruct
    public void init() {
        instance = this;
    }

    public static LogClient getInstance(){
        return instance;
    }


    public void logObject(String objectId, String parentId, Object oldObject, Object modelObject, String moduleName, String comment,OperationEnum operationType){
        ObjectLogTask logObjectTask = new ObjectLogTask(objectId,parentId, oldObject, modelObject, moduleName, comment, operationType);
        init(logObjectTask);
        taskExecutor.execute(logObjectTask);
    }

    public void logObject(String id, String objectId, String parentId, Object oldObject, Object modelObject,String moduleName, String comment, OperationEnum operationType){
        ObjectLogTask logObjectTask = new ObjectLogTask(id, objectId, parentId, oldObject, modelObject, moduleName, comment, operationType);
        init(logObjectTask);
        taskExecutor.execute(logObjectTask);
    }

    private void init(ObjectLogTask task) {
        task.setToken(HttpRequestUtil.getToken());
        task.setObjectLogFeignClient(objectLogFeignClient);
    }
}
