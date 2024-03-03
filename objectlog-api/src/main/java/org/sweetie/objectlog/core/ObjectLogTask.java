package org.sweetie.objectlog.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sweetie.objectlog.core.enums.OperationEnum;
import org.sweetie.objectlog.core.strategy.AttributeParseStrategy;
import org.sweetie.objectlog.core.strategy.AttributeStrategyFactory;
import org.sweetie.objectlog.core.utils.ThreadLocalUtil;
import org.sweetie.objectlog.domain.ObjectOperationDto;
import org.sweetie.objectlog.feign.ObjectLogFeignClient;


public class ObjectLogTask implements Runnable {
    private final Logger LOG = LoggerFactory.getLogger(ObjectLogTask.class);
    private ObjectLogFeignClient objectLogFeignClient;
    private String token;
    private String objectId;
    private String moduleName;
    private String comment;
    private OperationEnum operationType;
    private Object modelObject;
    private Object oldObject;
    private String parentId;
    private String id;

    public ObjectLogTask(String id, String objectId, String parentId, Object oldObject, Object modelObject, String moduleName, String comment, OperationEnum operationType) {
        this.id = id;
        this.objectId = objectId;
        this.parentId = parentId;
        this.modelObject = modelObject;
        this.moduleName = moduleName;
        this.comment = comment;
        this.operationType = operationType;
        this.oldObject = oldObject;
    }

    public ObjectLogTask(String objectId, String parentId, Object oldObject, Object  modelObject, String moduleName, String comment, OperationEnum operationType) {
        this.objectId = objectId;
        this.parentId = parentId;
        this.modelObject = modelObject;
        this.moduleName = moduleName;
        this.comment = comment;
        this.operationType = operationType;
        this.oldObject = oldObject;
    }

    @Override
    public void run() {
        try {
            ThreadLocalUtil.setToken(token);
            //处理基础信息
            ObjectOperationDto model = new ObjectOperationDto();
            model.setId(id);
            model.setObjectId(objectId);
            model.setParentId(parentId);
            model.setOperationType(operationType.name());
            model.setModuleName(moduleName);
            model.setComment(comment);
            AttributeParseStrategy strategy = AttributeStrategyFactory.getParseStrategy(operationType);
            boolean done = strategy.doParse(model, modelObject, oldObject);
            if (done){
                objectLogFeignClient.addLog(model);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage() + objectId, e);
        } finally {
            ThreadLocalUtil.removeToken();
        }
    }

    public void setObjectLogFeignClient(ObjectLogFeignClient objectLogFeignClient) {
        this.objectLogFeignClient = objectLogFeignClient;
    }
    public void setToken(String token) {
        this.token = token;
    }

}
