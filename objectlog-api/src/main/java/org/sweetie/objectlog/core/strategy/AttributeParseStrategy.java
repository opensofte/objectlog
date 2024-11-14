package org.sweetie.objectlog.core.strategy;
/*
 * FileName: AttributeParseStrategy
 * Author gouhao
 */

import org.apache.ibatis.mapping.SqlCommandType;
import org.sweetie.objectlog.core.ObjectLogTask;
import org.sweetie.objectlog.core.model.ObjectOperationModel;

public interface AttributeParseStrategy {
    public abstract ObjectOperationModel doParse(ObjectLogTask task) throws Exception;

    public abstract void parse(SqlCommandType sqlCommandType, Object parameterObject, String methodName) throws Exception;

}
