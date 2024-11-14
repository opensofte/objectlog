package org.sweetie.objectlog.core.enums;
/*
 * FileName: OperationEnum
 * Author gouhao
 */

public enum OperationEnum {
    /**
     * 操作类型枚举列表
     */
    ADD("ADD", "新增", false, false),
    DEL("DEL", "删除", false, false),
    UPDATE("UPDATE", "编辑", false, false),
    BASE("BASE", "基础", true, false),
    COMMON("COMMON", "复合", true, true),
    COMPLEX("COMPLEX", "复杂", true, true);
    private String key;
    private String value;
    private Boolean done;
    private Boolean multiple;

    OperationEnum(String key, String value, Boolean done, Boolean multiple) {
        this.key = key;
        this.value = value;
        this.done = done;
        this.multiple = multiple;
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

    public Boolean getMultiple() {
        return multiple;
    }
}
