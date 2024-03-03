package org.sweetie.objectlog.core.utils;/*
 * Copyright (C), 2021-2023
 * FileName: UuidUtil
 * Author gouhao
 * Date: 2023/12/2 18:11
 * Description:
 */

import java.util.UUID;

public class UuidUtil {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
