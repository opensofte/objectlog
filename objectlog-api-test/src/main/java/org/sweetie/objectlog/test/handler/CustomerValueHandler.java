package org.sweetie.objectlog.test.handler;
/*
 * FileName: CustomerValueHandler
 * Author gouhao
 */

import org.sweetie.objectlog.core.ObjectFieldWrapper;
import org.sweetie.objectlog.core.handler.AttributeValueHandler;

public class CustomerValueHandler implements AttributeValueHandler {
    //新的对比方式
    @Override
    public String handlerValue(ObjectFieldWrapper objectFieldWrapper) {
        return objectFieldWrapper.getOldValueString() + "->" + objectFieldWrapper.getNewValueString();
    }
}
