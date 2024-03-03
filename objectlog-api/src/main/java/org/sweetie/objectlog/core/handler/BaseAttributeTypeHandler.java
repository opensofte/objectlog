package org.sweetie.objectlog.core.handler;

import org.sweetie.objectlog.core.ObjectFieldWrapper;
import org.sweetie.objectlog.core.model.ObjectAttributeModel;

public class BaseAttributeTypeHandler extends AbstractAttributeTypeHandler{
    @Override
    public ObjectAttributeModel handlerAttributeChange(ObjectFieldWrapper fieldWrapper) {
        return this.dealAttributeModel(fieldWrapper);
    }
    public final ObjectAttributeModel dealAttributeModel(ObjectFieldWrapper fieldWrapper) {
        ObjectAttributeModel attributeModel = this.getAttribute(fieldWrapper);
        if (null != attributeModel) {
            dealBaseInfo(fieldWrapper, attributeModel, fieldWrapper.getExtendedType());
            attributeModel.setDiffValue(this.dealDiffValue(fieldWrapper.getOldValueString(), fieldWrapper.getNewValueString()));
        }
        return attributeModel;
    }

    public String dealDiffValue(String oldValue, String newValue) {
        return oldValue + "->" + newValue;
    }

    public ObjectAttributeModel getAttribute(ObjectFieldWrapper fieldwrapper) {
        return new ObjectAttributeModel();
    }
}
