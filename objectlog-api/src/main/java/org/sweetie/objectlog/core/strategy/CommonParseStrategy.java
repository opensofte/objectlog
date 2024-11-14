package org.sweetie.objectlog.core.strategy;
/*
 * FileName: CommonParseStrategy
 * Author gouhao
 */

import cn.hutool.core.util.ObjectUtil;
import org.apache.ibatis.mapping.SqlCommandType;
import org.sweetie.objectlog.LogClient;
import org.sweetie.objectlog.core.ObjectLogTask;
import org.sweetie.objectlog.core.constant.Constant;
import org.sweetie.objectlog.core.enums.OperationEnum;
import org.sweetie.objectlog.core.model.ObjectOperationModel;
import org.sweetie.objectlog.core.utils.ThreadLocalUtil;
import org.sweetie.objectlog.domain.BaseEntity;

public class CommonParseStrategy extends AbstractAttributeParseStrategy {
    @Override
    public ObjectOperationModel doParse(ObjectLogTask task) {
        return super.getObject(task);
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
                this.generatorParentInfo(parameterObject);
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
        this.generatorParentInfo(parameterObject);
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

    private void insertParse(Object parameterObject) {
        this.generatorParentInfo(parameterObject);
        LogClient.getInstance().logObject(
                ((BaseEntity) parameterObject).getId(),
                ThreadLocalUtil.getParentId(),
                null,
                parameterObject,
                ThreadLocalUtil.getStringMapByKey(Constant.MODULE_NAME),
                ThreadLocalUtil.getStringMapByKey(Constant.REMARK),
                OperationEnum.ADD);
    }

    private void generatorParentInfo(Object parameterObject) {
        if (ThreadLocalUtil.getBooleanMapByKey(Constant.COMMENT_OPERATION)) {
            LogClient.getInstance().logObject(
                    ThreadLocalUtil.getParentId(),
                    null,
                    null,
                    null,
                    null,
                    ThreadLocalUtil.getStringMapByKey(Constant.MODULE_NAME),
                    ThreadLocalUtil.getStringMapByKey(Constant.REMARK),
                    OperationEnum.COMMON);
            ThreadLocalUtil.setBooleanMapByKey(Constant.COMMENT_OPERATION, false);
        }
    }
}
