package org.sweetie.objectlog.core.handler;

import org.sweetie.objectlog.core.ObjectFieldWrapper;

public interface AttributeValueHandler {
    String handlerValue(ObjectFieldWrapper fieldWrapper);
}
