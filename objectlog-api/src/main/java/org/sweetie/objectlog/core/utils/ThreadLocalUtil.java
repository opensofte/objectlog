package org.sweetie.objectlog.core.utils;
/*
 * Copyright (C), 2021-2023
 * FileName: ThreadLocalUtil
 * Author gouhao
 * Date: 2023/12/2 18:26
 * Description:
 */
import org.sweetie.objectlog.domain.BaseEntity;

import java.util.HashMap;

public class ThreadLocalUtil {
    private static final ThreadLocal<String> TOKEN = new ThreadLocal<>();
    private static final ThreadLocal<HashMap<String, ? extends BaseEntity>> OLD_DATA_MAP = new ThreadLocal<>();
    private static final ThreadLocal<HashMap<String, String>> STRING_MAP = new ThreadLocal<>();
    private static final ThreadLocal<HashMap<String, Boolean>> BOOLEAN_MAP = new ThreadLocal<>();
    private static final ThreadLocal<String> PARENT_ID= new ThreadLocal<>();

    public static String getToken() { return TOKEN.get(); }
    public static void setToken(String value) { TOKEN.set(value); }
    public static void removeToken() { TOKEN.remove(); }

    public static String getParentId() { return PARENT_ID.get(); }
    public static void setParentId(String parentId){ PARENT_ID.set(parentId);}
    public static void removeParentId(){ PARENT_ID.remove(); }

    public static void setOldDataMap(HashMap<String, ? extends BaseEntity> oldData) { OLD_DATA_MAP.set(oldData);}
    public static void removeOldDataMap() { OLD_DATA_MAP.remove();}
    public static BaseEntity getOldDataByKey(String key){
        if (null == OLD_DATA_MAP.get()){
            return null;
        }
        return OLD_DATA_MAP.get().get(key);
    }

    public static void setStringMap(HashMap<String,String> stringMap){STRING_MAP.set(stringMap);}
    public static void removeStringMap() { STRING_MAP.remove(); }
    public static String getStringMapByKey(String key){
        if (null == STRING_MAP.get()){
            return "";
        }
        return STRING_MAP.get().get(key);
    }
    public static void setStringMapByKey(String key,String value){
        if (null == STRING_MAP.get()){
            STRING_MAP.set(new HashMap<>(2));
        }
        STRING_MAP.get().put(key,value);
    }

    public static void setBooleanMap(HashMap<String,Boolean> booleanMap){BOOLEAN_MAP.set(booleanMap);}
    public static void removeBooleanMap() { BOOLEAN_MAP.remove(); }
    public static Boolean getBooleanMapByKey(String key){
        if (null == BOOLEAN_MAP.get()){
            return Boolean.FALSE;
        }
        return BOOLEAN_MAP.get().get(key);
    }
    public static void setBooleanMapByKey(String key,Boolean value){
        if (null == BOOLEAN_MAP.get()){
            BOOLEAN_MAP.set(new HashMap<>(2));
        }
        BOOLEAN_MAP.get().put(key,value);}

    public static void resetCurrentThreadInfo(){
        removeOldDataMap();
        removeStringMap();
        removeBooleanMap();
    }

}
