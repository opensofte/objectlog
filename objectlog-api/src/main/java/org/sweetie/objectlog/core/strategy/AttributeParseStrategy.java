package org.sweetie.objectlog.core.strategy;/*
 * Copyright (C), 2021-2024
 * FileName: AttributeParseStrategy
 * Author gouhao
 * Date: 2024/2/25 17:23
 * Description:
 */

import org.sweetie.objectlog.domain.ObjectOperationDto;

public interface AttributeParseStrategy {
    public abstract boolean doParse(ObjectOperationDto model, Object modelObject, Object oldObject) throws IllegalAccessException;
}
