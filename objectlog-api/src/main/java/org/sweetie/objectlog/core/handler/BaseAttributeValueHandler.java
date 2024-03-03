package org.sweetie.objectlog.core.handler;

import org.sweetie.objectlog.core.ObjectFieldWrapper;

public class BaseAttributeValueHandler extends AbstractAttributeValueHandler {
    @Override
    public String handlerValue(ObjectFieldWrapper fieldWrapper) {
        return getStr(fieldWrapper);
    }
}
