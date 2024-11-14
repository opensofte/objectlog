package org.sweetie.objectlog.core.handler;
/*
 * FileName: AbstractAttributeValueHandler
 * Author gouhao
 */

import org.sweetie.objectlog.core.ObjectFieldWrapper;

public abstract class AbstractAttributeValueHandler implements AttributeValueHandler {
    @Override
    public String handlerValue(ObjectFieldWrapper fieldWrapper) {
        return null;
    }

    public static String getStr(ObjectFieldWrapper fieldWrapper) {
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
