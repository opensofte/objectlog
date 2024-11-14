package org.sweetie.objectlog;
/*
 * FileName: LogClient
 * Author gouhao
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.sweetie.objectlog.core.ObjectLogTask;
import org.sweetie.objectlog.core.config.LogConfigProperty;
import org.sweetie.objectlog.core.enums.OperationEnum;
import org.sweetie.objectlog.core.utils.HttpRequestUtil;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class LogClient {

    private static LogClient instance;
    @Resource
    private LogConfigProperty logConfigProperty;

    //指定线程池
    @Autowired
    @Qualifier(value = "objectlog")
    private ThreadPoolTaskExecutor taskExecutor;

    //利用生命周期初始化
    @PostConstruct
    public void init() {
        instance = this;
    }

    public static LogClient getInstance() {
        return instance;
    }


    public void logObject(String objectId, String parentId, Object oldObject, Object modelObject, String moduleName, String comment, OperationEnum operationType) {
        ObjectLogTask logObjectTask = new ObjectLogTask(objectId, parentId, oldObject, modelObject, moduleName, comment, operationType);
        init(logObjectTask);
        taskExecutor.execute(logObjectTask);
    }

    public void logObject(String id, String objectId, String parentId, Object oldObject, Object modelObject, String moduleName, String comment, OperationEnum operationType) {
        ObjectLogTask logObjectTask = new ObjectLogTask(id, objectId, parentId, oldObject, modelObject, moduleName, comment, operationType);
        init(logObjectTask);
        taskExecutor.execute(logObjectTask);
    }

    private void init(ObjectLogTask task) {
        task.setHost(logConfigProperty.getHost());
        task.setPath(logConfigProperty.getPath());
        task.setHeaders(HttpRequestUtil.getHeaders(logConfigProperty));
    }
}
