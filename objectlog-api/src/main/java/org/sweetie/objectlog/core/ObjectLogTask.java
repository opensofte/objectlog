package org.sweetie.objectlog.core;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sweetie.objectlog.core.enums.OperationEnum;
import org.sweetie.objectlog.core.model.ObjectOperationModel;
import org.sweetie.objectlog.core.strategy.AttributeParseStrategy;
import org.sweetie.objectlog.core.strategy.AttributeStrategyFactory;
import org.sweetie.objectlog.core.utils.HttpUtils;

import java.util.Map;


@Getter
public class ObjectLogTask implements Runnable {
    private final Logger LOG = LoggerFactory.getLogger(ObjectLogTask.class);
    @Setter
    private String host;
    @Setter
    private String path;
    @Setter
    private Map<String, String> headers;

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

    public ObjectLogTask(String objectId, String parentId, Object oldObject, Object modelObject, String moduleName, String comment, OperationEnum operationType) {
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
            AttributeParseStrategy strategy = AttributeStrategyFactory.getParseStrategy(operationType);
            ObjectOperationModel model = strategy.doParse(this);
            if (ObjectUtil.isNotEmpty(model)) {
                HttpUtils.doPost(host, path, headers, null, JSONUtil.toJsonStr(model));
            }
        } catch (Exception e) {
            LOG.error("{}{}", e.getMessage(), objectId, e);
        }
    }
}
