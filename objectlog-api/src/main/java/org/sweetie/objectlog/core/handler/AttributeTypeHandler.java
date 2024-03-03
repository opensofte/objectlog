package org.sweetie.objectlog.core.handler;

import org.sweetie.objectlog.core.ObjectFieldWrapper;
import org.sweetie.objectlog.core.model.ObjectAttributeModel;

public interface AttributeTypeHandler {
    ObjectAttributeModel handlerAttributeChange(ObjectFieldWrapper fieldWrapper);
}
