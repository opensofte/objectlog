package org.sweetie.objectlog.core.utils;
/*
 * FileName: UuidUtil
 * Author gouhao
 */

import java.util.UUID;

public class UuidUtil {
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
