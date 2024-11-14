package org.sweetie.objectlog.core.handler;
/*
 * FileName: DefaultAttributeValueHandler
 * Author gouhao
 */

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.sweetie.objectlog.core.ObjectFieldWrapper;
import org.sweetie.objectlog.core.enums.AttributeTypeEnum;
import org.sweetie.objectlog.core.utils.ClassUtil;
import org.sweetie.objectlog.core.utils.Html2TextUtil;
import org.sweetie.objectlog.core.utils.SpringBeanContextUtil;
import org.sweetie.objectlog.core.utils.StringDiffUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultAttributeValueHandler extends AbstractAttributeValueHandler {
    @Override
    public String handlerValue(ObjectFieldWrapper fieldWrapper) {
        return null;
    }

    public static String handlerValue(ObjectFieldWrapper fieldWrapper, AttributeTypeEnum attributeTypeEnum, boolean first) {
        String res = null;
        switch (attributeTypeEnum) {
            case NORMAL:
                res = dealNormal(fieldWrapper, first);
                break;
            case RICHTEXT:
                res = dealRichtext(fieldWrapper, first);
                break;
            case TEXT:
                res = dealText(fieldWrapper, first);
                break;
            default:
                res = dealNormal(fieldWrapper, first);
                break;
        }
        return res;
    }

    private static String dealRichtext(ObjectFieldWrapper fieldWrapper, boolean first) {
        if (first && fieldWrapper.isExtendedValue()) {
            return doAttributeValueHandler(fieldWrapper);
        }
        String simpleOldValue = Html2TextUtil.simpleHtml(fieldWrapper.getOldValueString());
        String simpleNewValue = Html2TextUtil.simpleHtml(fieldWrapper.getNewValueString());
        List<String> oldStringList = Arrays.asList(simpleOldValue.split("\n"));
        List<String> newStringList = Arrays.asList(simpleNewValue.split("\n"));
        return StringDiffUtil.diffText(oldStringList, newStringList);
    }

    private static String dealText(ObjectFieldWrapper fieldWrapper, boolean first) {
        if (first && fieldWrapper.isExtendedValue()) {
            return doAttributeValueHandler(fieldWrapper);
        }
        List<String> oldStringList = Arrays.asList(fieldWrapper.getOldValueString().split("\n"));
        List<String> newStringList = Arrays.asList(fieldWrapper.getNewValueString().split("\n"));
        return StringDiffUtil.diffText(oldStringList, newStringList);
    }

    private static String dealNormal(ObjectFieldWrapper fieldWrapper, boolean first) {
        if (first && fieldWrapper.isExtendedValue()) {
            return doAttributeValueHandler(fieldWrapper);
        }
        if (fieldWrapper.isAssociationValue()) {
            return handlerAssociationValue(fieldWrapper);
        }
        if (StrUtil.isBlank(fieldWrapper.getNewValueString())) {
            fieldWrapper.setNewValueString("");
        }
        return getStr(fieldWrapper);
    }

    private static String doAttributeValueHandler(ObjectFieldWrapper fieldWrapper) {
        AttributeValueHandler attributeValueHandler = ClassUtil.getInstance(fieldWrapper.getAttributeValueHandler());
        String value = null;
        if (null == attributeValueHandler) {
            DefaultAttributeValueHandler.handlerValue(fieldWrapper, fieldWrapper.getLogEntity().attributeTypeEnum(), false);
        } else {
            value = attributeValueHandler.handlerValue(fieldWrapper);
        }
        return value;
    }

    private static String handlerAssociationValue(ObjectFieldWrapper fieldWrapper) {
        if ((null != fieldWrapper.getServiceImplClass())
                && !ServiceImpl.class.equals(fieldWrapper.getServiceImplClass())) {
            try {
                Object instance = SpringBeanContextUtil.getContext().getBean(fieldWrapper.getServiceImplClass());
                return dealValue(instance, fieldWrapper);
            } catch (Exception e) {
                //不做处理
            }
        }
        if (StrUtil.isNotBlank(fieldWrapper.getServiceImplClassPath())) {
            try {
                Class<?> aClass = Class.forName(fieldWrapper.getServiceImplClassPath());
                Object instance = SpringBeanContextUtil.getContext().getBean(aClass);
                return dealValue(instance, fieldWrapper);
            } catch (Exception e) {
                //不做处理
            }
        }
        return getStr(fieldWrapper);
    }

    private static String dealValue(Object service, ObjectFieldWrapper fieldWrapper) throws Exception {
        if (null == service || StrUtil.isBlank(fieldWrapper.getNewValueString()) || StrUtil.isBlank(fieldWrapper.getEntityFieldName())) {
            throw new RuntimeException();
        }
        ServiceImpl instance = (ServiceImpl) service;
        List<Object> list = instance.selectBatchIds(Arrays.asList(fieldWrapper.getNewValueString().split(",")));
        Field declaredField;
        List<String> valueList = new ArrayList<>();
        String value = "";
        for (Object model : list) {
            declaredField = model.getClass().getDeclaredField(fieldWrapper.getEntityFieldName());
            declaredField.setAccessible(true);
            value = String.valueOf(declaredField.get(model));
            if (StrUtil.isNotBlank(value)) {
                valueList.add(value);
            }
        }
        if (CollUtil.isEmpty(valueList)) {
            throw new RuntimeException();
        }
        String res = valueList.stream().collect(Collectors.joining(","));
        StringBuilder sb = new StringBuilder();
        sb.append(fieldWrapper.getAttributeAlias());
        sb.append("从");
        sb.append("\"");
        sb.append(fieldWrapper.getOldValueString());
        sb.append("\"");
        sb.append(" 修改为 ");
        sb.append("\"");
        sb.append(res);
        sb.append("\"");
        return sb.toString();
    }
}
