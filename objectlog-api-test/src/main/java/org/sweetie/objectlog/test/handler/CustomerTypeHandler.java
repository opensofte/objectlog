package org.sweetie.objectlog.test.handler;
/*
 * FileName: CustomerTypeHandler
 * Author gouhao
 */

import org.sweetie.objectlog.core.ObjectFieldWrapper;
import org.sweetie.objectlog.core.handler.AttributeTypeHandler;
import org.sweetie.objectlog.core.model.ObjectAttributeModel;

public class CustomerTypeHandler implements AttributeTypeHandler {
    @Override
    public ObjectAttributeModel handlerAttributeChange(ObjectFieldWrapper fieldWrapper) {
        return this.dealAttributeModel(fieldWrapper);
    }

    public ObjectAttributeModel dealAttributeModel(ObjectFieldWrapper fieldWrapper) {
        ObjectAttributeModel attributeModel = this.getAttribute(fieldWrapper);
        if (null != attributeModel) {
            dealBaseInfo(fieldWrapper, attributeModel, fieldWrapper.getExtendedType());
            attributeModel.setDiffValue(this.dealDiffValue(fieldWrapper.getOldValueString(), fieldWrapper.getNewValueString()));
        }
        return attributeModel;
    }

    public String dealDiffValue(String oldValue, String newValue) {
        return oldValue + "@@@@" + newValue;
    }

    public ObjectAttributeModel getAttribute(ObjectFieldWrapper fieldWrapper) {
        return new ObjectAttributeModel();
    }

    public static void dealBaseInfo(ObjectFieldWrapper fieldWrapper, ObjectAttributeModel attributeModel, String attributeType) {
        //固定值
        attributeModel.setOldValue(fieldWrapper.getOldValueString());
        attributeModel.setNewValue(fieldWrapper.getNewValueString());
        attributeModel.setAttributeName(fieldWrapper.getAttributeName());
        attributeModel.setAttributeAlias(fieldWrapper.getAttributeAlias());
        attributeModel.setAttributeType(attributeType);
    }
}
