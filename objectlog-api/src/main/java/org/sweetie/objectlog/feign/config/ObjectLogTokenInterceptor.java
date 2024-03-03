package org.sweetie.objectlog.feign.config;/*
 * Copyright (C), 2021-2023
 * FileName: ObjectLogTokenInterceptor
 * Author gouhao
 * Date: 2023/12/9 10:33
 * Description:
 */

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.sweetie.objectlog.core.utils.ThreadLocalUtil;

public class ObjectLogTokenInterceptor implements RequestInterceptor {
    public void apply(RequestTemplate requestTemplate) {
        String authorization = ThreadLocalUtil.getToken();
        requestTemplate.header("Authorization", new String[]{authorization});
    }
}
