package org.sweetie.objectlog.core.strategy;
/*
 * FileName: DeletedParseStrategy
 * Author gouhao
 */

import org.apache.ibatis.mapping.SqlCommandType;
import org.sweetie.objectlog.LogClient;
import org.sweetie.objectlog.core.ObjectLogTask;
import org.sweetie.objectlog.core.constant.Constant;
import org.sweetie.objectlog.core.enums.OperationEnum;
import org.sweetie.objectlog.core.model.ObjectOperationModel;
import org.sweetie.objectlog.core.utils.ThreadLocalUtil;

import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class DeletedParseStrategy extends AbstractAttributeParseStrategy {

    @Override
    public ObjectOperationModel doParse(ObjectLogTask task) throws Exception {
        return super.getObject(task);
    }

    public static void parse(Object parameterObject, boolean isParent, Consumer callBack) throws Exception {
        parameterObject = findObjectFromParameter(parameterObject, List.class);
        if (parameterObject instanceof List) {
            List<String> idList = (List<String>) parameterObject;
            idList = Optional.ofNullable(idList).orElseGet(ArrayList::new);
            for (String id : idList) {
                LogClient.getInstance().logObject(
                        id,
                        ThreadLocalUtil.getParentId(),
                        null,
                        parameterObject,
                        ThreadLocalUtil.getStringMapByKey(Constant.MODULE_NAME),
                        ThreadLocalUtil.getStringMapByKey(Constant.REMARK),
                        OperationEnum.DEL);
            }
            return;
        }
        parameterObject = findObjectFromParameter(parameterObject, String.class);
        if (parameterObject instanceof String) {
            if (isParent) {
                LogClient.getInstance().logObject(
                        ThreadLocalUtil.getParentId(),
                        (String) parameterObject,
                        null,
                        null,
                        parameterObject,
                        ThreadLocalUtil.getStringMapByKey(Constant.MODULE_NAME),
                        ThreadLocalUtil.getStringMapByKey(Constant.REMARK),
                        OperationEnum.DEL);
                callBack.accept(null);
            } else {
                LogClient.getInstance().logObject(
                        (String) parameterObject,
                        ThreadLocalUtil.getParentId(),
                        null,
                        parameterObject,
                        ThreadLocalUtil.getStringMapByKey(Constant.MODULE_NAME),
                        ThreadLocalUtil.getStringMapByKey(Constant.REMARK),
                        OperationEnum.DEL);
            }
            return;
        }
        parameterObject = findObjectFromParameter(parameterObject, Wrapper.class);
        if (parameterObject instanceof Wrapper) {
            parseWrapper(parameterObject);
            return;
        }
        //TODO 参数是对象
    }


    @Override
    public void parse(SqlCommandType sqlCommandType, Object parameterObject, String methodName) throws Exception {
        parse(parameterObject, false, null);
    }

    private static void parseWrapper(Object parameterObject) throws Exception {
        ////1、获取预处理sql语句
        //Field sqlPlusField = parameterObject.getClass().getSuperclass().getDeclaredField("sql");
        //sqlPlusField.setAccessible(true);
        //MybatisSqlPlus sqlPlus = (MybatisSqlPlus) sqlPlusField.get(parameterObject);
        //Method sqlMethod = sqlPlus.getClass().getSuperclass().getDeclaredMethod("sql");
        //sqlMethod.setAccessible(true);
        ////得到的是 MybatisAbstractSQL.SQLCondition 对象
        //Object sqlCondition = sqlMethod.invoke(sqlPlus);
        //Field whereField = sqlCondition.getClass().getDeclareddField("where");
        //whereField.setAccessible(true);
        //List<String> whereValue = (List<String>) whereField.get(sqlCondition);
        ////2、获取参数map
        //Field paramMap = parameterObject.getClass().getSuperclass().getDeclaredField("paramNameValuePairs");
        //paramMap.setAccessible(true);
        //Map<String, Object> paramNameValuePairs = (Map<String,Object>) paramMap.get(parameterObject);
        ////解析fid
        //boolean existId = Optional.ofNullable(whereValue.get(0)).orElseGet(String::new).startsWith("fid");
        //if (existId) {
        //    String[] split = Optional.ofNullable(whereValue.get(0)).orElseGet(String::new).split("#");
        //    int size = split.length - 1;
        //    for (int i = 1; i < size; i++) {
        //        LogClient.getInstance().
        //                logObject((String) paramNameValuePairs.get("MPGENVAL" + i), ThreadLocalUtil.getParentId(), null,
        //                        parameterObject, ThreadLocalUtil.getStringMapByKey(Constant.MODULE_NAME), OperationEnum.DEL);
        //    }
        //}
    }
}
