package org.sweetie.objectlog.core.strategy;
/*
 * FileName: ComplexParseStrategy
 * Author gouhao
 */

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

public class ComplexParseStrategy extends AbstractAttributeParseStrategy {
    @Override
    public ObjectOperationModel doParse(ObjectLogTask task) throws IllegalAccessException {
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

    /**
     * UPDATE INSERT DELETE
     *
     * @param sqlCommandType
     * @param parameterObject
     * @param methodName
     * @throws Exception
     */
    @Override
    public void parse(SqlCommandType sqlCommandType, Object parameterObject, String methodName) throws Exception {
        // open but miss logEntity
        Object realParamterObject = super.getRealParamterObject(parameterObject);
        if (ObjectUtil.isEmpty(realParamterObject)) {
            //deleteById deleteByIdList
            if (methodName.startsWith(".delete")) {
                DeletedParseStrategy.parse(parameterObject, ThreadLocalUtil.getBooleanMapByKey(Constant.COMPLEX_OPERATION), (t) -> {
                    ThreadLocalUtil.setBooleanMapByKey(Constant.COMPLEX_OPERATION, false);
                });
            }
            return;
        }
        if (SqlCommandType.INSERT == sqlCommandType) {
            this.insertParse(realParamterObject);
            return;
        }

        if (SqlCommandType.UPDATE == sqlCommandType) {
            this.updateParse(realParamterObject);
        }
    }

    private void updateParse(Object parameterObject) {
        if (ThreadLocalUtil.getBooleanMapByKey(Constant.COMPLEX_OPERATION)) {
            String objectId = ((BaseEntity) parameterObject).getId();
            Object oldObject = ThreadLocalUtil.getOldDataByKey(objectId);
            LogClient.getInstance().logObject(
                    ThreadLocalUtil.getParentId(),
                    objectId,
                    null,
                    oldObject,
                    parameterObject,
                    ThreadLocalUtil.getStringMapByKey(Constant.MODULE_NAME),
                    ThreadLocalUtil.getStringMapByKey(Constant.REMARK),
                    OperationEnum.COMPLEX);
            ThreadLocalUtil.setBooleanMapByKey(Constant.COMPLEX_OPERATION, false);
        } else {
            String objectId = ((BaseEntity) parameterObject).getId();
            Object oldObject = ThreadLocalUtil.getOldDataByKey(objectId);
            LogClient.getInstance().logObject(
                    objectId,
                    ThreadLocalUtil.getParentId(),
                    oldObject,
                    parameterObject,
                    ThreadLocalUtil.getStringMapByKey(Constant.MODULE_NAME),
                    ThreadLocalUtil.getStringMapByKey(Constant.REMARK),
                    OperationEnum.UPDATE);
        }
    }

    private void insertParse(Object parameterObject) {
        if (ThreadLocalUtil.getBooleanMapByKey(Constant.COMPLEX_OPERATION)) {
            LogClient.getInstance().logObject(
                    ThreadLocalUtil.getParentId(),
                    ((BaseEntity) parameterObject).getId(),
                    null,
                    null,
                    parameterObject,
                    ThreadLocalUtil.getStringMapByKey(Constant.MODULE_NAME),
                    ThreadLocalUtil.getStringMapByKey(Constant.REMARK),
                    OperationEnum.COMPLEX);
            ThreadLocalUtil.setBooleanMapByKey(Constant.COMPLEX_OPERATION, false);
        } else {
            LogClient.getInstance().logObject(
                    ((BaseEntity) parameterObject).getId(),
                    ThreadLocalUtil.getParentId(),
                    null,
                    parameterObject,
                    ThreadLocalUtil.getStringMapByKey(Constant.MODULE_NAME),
                    ThreadLocalUtil.getStringMapByKey(Constant.REMARK),
                    OperationEnum.ADD);
        }
    }
}
