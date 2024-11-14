package org.sweetie.objectlog.test.enums;
/*
 * FileName: StatusEnum
 * Author gouhao
 */

/**
 * 0 正常 1 停用
 */
public enum StatusEnum {
    NORMAL(0, "正常"),
    DEACTIVATE(1, "停用"),
    ;

    private StatusEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    private Integer key;
    private String value;
}
