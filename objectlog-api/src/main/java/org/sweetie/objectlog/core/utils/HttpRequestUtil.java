package org.sweetie.objectlog.core.utils;/*
 * Copyright (C), 2021-2023
 * FileName: HttpRequestUtil
 * Author gouhao
 * Date: 2023/12/2 18:18
 * Description:
 */

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class HttpRequestUtil {
    public static String getToken() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        return request.getHeader("Authorization");
    }
}
