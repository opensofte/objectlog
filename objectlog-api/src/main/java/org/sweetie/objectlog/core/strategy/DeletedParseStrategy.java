package org.sweetie.objectlog.core.strategy;/*
 * Copyright (C), 2021-2024
 * FileName: DeletedParseStrategy
 * Author gouhao
 * Date: 2024/2/25 17:39
 * Description:
 */

import org.sweetie.objectlog.domain.ObjectOperationDto;

public class DeletedParseStrategy extends AbstractAttributeParseStrategy {
    @Override
    public boolean doParse(ObjectOperationDto model, Object modelObject, Object oldObject) {
        return true;
    }
}
