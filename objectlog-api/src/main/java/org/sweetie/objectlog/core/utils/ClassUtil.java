package org.sweetie.objectlog.core.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassUtil {

    public static List<Field> getFields(Class clazz) {
        List<Field> fieldList = new ArrayList<>();
        return getFields(fieldList, clazz);
    }

    private static List<Field> getFields(List<Field> fieldList, Class clazz) {
        fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
        Class superClazz = clazz.getSuperclass();
        if (superClazz != null) {
            getFields(fieldList, superClazz);
        }
        return fieldList;
    }

    public static <T> T getInstance(Class<T> clazz) {
        T instance = null;
        try {
            instance = clazz.newInstance();
        } catch (Exception e) {

        }
        return instance;
    }
}