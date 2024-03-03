package org.sweetie.objectlog.core.enums;/*
 * Copyright (C), 2021-2023
 * FileName: OperationEnum
 * Author gouhao
 * Date: 2023/12/2 17:00
 * Description:
 */

public enum OperationEnum {
    /**
     * 操作类型枚举列表
     */
    ADD("ADD", "新增",false),
    DEL("DEL", "删除",false),
    UPDATE("UPDATE", "编辑",false),
    BASE("BASE", "基础",true),
    COMMON("COMMON", "复合",true),
    COMPLEX("COMPLEX", "复杂",true);
    private String key;
    private String value;
    private Boolean done;
    OperationEnum(String key, String value,Boolean done) {
        this.key = key;
        this.value = value;
        this.done = done;
    }

    public static String getValueByKey(String key) {
        String value = "";
        OperationEnum[] values = OperationEnum.values();
        for (OperationEnum obj : values) {
            if (obj.getKey().equals(key)) {
                value = obj.getValue();
            }
        }
        return value;
    }

    public String getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }
    public Boolean getDone() {
        return done;
    }

}
