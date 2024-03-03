package org.sweetie.objectlog.core.handler;

import org.sweetie.objectlog.core.ObjectFieldWrapper;
import org.sweetie.objectlog.core.model.ObjectAttributeModel;

public abstract class AbstractAttributeTypeHandler implements AttributeTypeHandler {
    @Override
    public abstract ObjectAttributeModel handlerAttributeChange(ObjectFieldWrapper fieldWrapper);

    public static void dealBaseInfo(ObjectFieldWrapper fieldWrapper, ObjectAttributeModel attributeModel, String attributeType) {
        //固定值
        attributeModel.setOldValue(fieldWrapper.getOldValueString());
        attributeModel.setNewValue(fieldWrapper.getNewValueString());
        attributeModel.setAttributeName (fieldWrapper.getAttributeName());
        attributeModel.setAttributeAlias(fieldWrapper.getAttributeAlias());
        attributeModel.setAttributeType(attributeType);
    }
}
