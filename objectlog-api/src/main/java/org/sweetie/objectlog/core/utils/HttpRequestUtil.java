package org.sweetie.objectlog.core.utils;
/*
 * FileName: HttpRequestUtil
 * Author gouhao
 */

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.sweetie.objectlog.core.config.LogConfigProperty;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestUtil {

    public static Map<String, String> getHeaders(LogConfigProperty logConfigProperty) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        List<String> headers = logConfigProperty.getHeader();
        Map<String, String> res = new HashMap<>(headers.size(), 1f);
        for (String item : headers) {
            res.put(item, request.getHeader(item));
        }
        return res;
    }
}
