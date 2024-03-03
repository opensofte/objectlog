package org.sweetie.objectlog.test.enums;/*
 * Copyright (C), 2021-2024
 * FileName: StatusEnum
 * Author gouhao
 * Date: 2024/3/2 23:07
 * Description:
 */

/**
 * 0 正常 1 停用
 */
public enum StatusEnum {
    NORMAL(0,"正常"),
    DEACTIVATE(1,"停用"),;
    private StatusEnum(Integer key,String value){
        this.key = key;
        this.value = value;
    }
    private Integer key;
    private String value;
}
