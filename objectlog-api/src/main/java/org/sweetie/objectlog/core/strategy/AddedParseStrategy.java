package org.sweetie.objectlog.core.strategy;
/*
 * FileName: AddedParseStrategy
 * Author gouhao
 */

import cn.hutool.json.JSONUtil;
import org.apache.ibatis.mapping.SqlCommandType;
import org.sweetie.objectlog.core.ObjectLogTask;
import org.sweetie.objectlog.core.annotation.LogEntity;
import org.sweetie.objectlog.core.model.ObjectAttributeModel;
import org.sweetie.objectlog.core.model.ObjectOperationModel;

import java.util.List;

public class AddedParseStrategy extends AbstractAttributeParseStrategy {
    @Override
    public ObjectOperationModel doParse(ObjectLogTask task) throws Exception {
        ObjectOperationModel model = super.getObject(task);

        List<ObjectAttributeModel> attributeModels = super.dealAttributeModelList(task.getModelObject(), task.getOldObject());
        LogEntity LogEntity = task.getModelObject().getClass().getAnnotation(LogEntity.class);
        if (null != LogEntity) {
            model.setObject(JSONUtil.toJsonStr(task.getModelObject()));
        }
        model.setObjectName(task.getModelObject().getClass().getSimpleName());
        model.setAttributes(attributeModels);
        return model;
    }

    @Override
    public void parse(SqlCommandType sqlCommandType, Object parameterObject, String methodName) throws Exception {
        //TODO-G
    }
}
