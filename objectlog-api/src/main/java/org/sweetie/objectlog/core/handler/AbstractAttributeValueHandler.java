package org.sweetie.objectlog.core.handler;/*
 * Copyright (C), 2021-2024
 * FileName: AbstractAttributeValueHandler
 * Author gouhao
 * Date: 2024/3/2 10:10
 * Description:
 */

import org.sweetie.objectlog.core.ObjectFieldWrapper;

public class AbstractAttributeValueHandler implements AttributeValueHandler {
    @Override
    public String handlerValue(ObjectFieldWrapper fieldWrapper) {
        return null;
    }

    public static String getStr(ObjectFieldWrapper fieldWrapper){
        StringBuilder sb = new StringBuilder();
        sb.append(fieldWrapper.getAttributeAlias());
        sb.append("从");
        sb.append("\"");
        sb.append(fieldWrapper.getOldValueString());
        sb.append("\"");
        sb.append("修改为");
        sb.append("\"");
        sb.append(fieldWrapper.getNewValueString());
        sb.append("\"");
        return sb.toString();
    }
}
