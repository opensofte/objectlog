package org.sweetie.objectlog.core.strategy;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import org.apache.ibatis.mapping.SqlCommandType;
import org.sweetie.objectlog.LogClient;
import org.sweetie.objectlog.core.ObjectLogTask;
import org.sweetie.objectlog.core.annotation.LogEntity;
import org.sweetie.objectlog.core.constant.Constant;
import org.sweetie.objectlog.core.enums.OperationEnum;
import org.sweetie.objectlog.core.model.ObjectAttributeModel;
import org.sweetie.objectlog.core.model.ObjectOperationModel;
import org.sweetie.objectlog.core.utils.ThreadLocalUtil;
import org.sweetie.objectlog.domain.BaseEntity;

import java.util.List;

/**
 * @Author: gouhao
 * @Date: 2024/08/23/13:42
 * @Description:
 */
public class BaseParseStrategy extends AbstractAttributeParseStrategy {
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
        // open but miss logEntity
        Object realParamterObject = getRealParamterObject(parameterObject);
        if (ObjectUtil.isEmpty(realParamterObject)) {
            //deleteById deleteByIdList
            if (methodName.startsWith(".delete")) {
                DeletedParseStrategy.parse(parameterObject, false, null);
            }
            return;
        }
        if (SqlCommandType.INSERT == sqlCommandType) {
            this.insertParse(realParamterObject);
            return;
        }
        if (SqlCommandType.UPDATE == sqlCommandType) {
            this.updateParse(realParamterObject);
            return;
        }
    }

    private void updateParse(Object parameterObject) {
        String objectId = ((BaseEntity) parameterObject).getId();
        Object oldObject = ThreadLocalUtil.getOldDataByKey(objectId);
        LogClient.getInstance().
                logObject(objectId, ThreadLocalUtil.getParentId(),
                        oldObject, parameterObject,
                        ThreadLocalUtil.getStringMapByKey(Constant.MODULE_NAME), ThreadLocalUtil.getStringMapByKey(Constant.REMARK),
                        OperationEnum.UPDATE);
    }

    private void insertParse(Object parameterObject) {
        LogClient.getInstance().
                logObject(((BaseEntity) parameterObject).getId(), ThreadLocalUtil.getParentId(),
                        null, parameterObject,
                        ThreadLocalUtil.getStringMapByKey(Constant.MODULE_NAME), ThreadLocalUtil.getStringMapByKey(Constant.REMARK),
                        OperationEnum.ADD);
    }
}
