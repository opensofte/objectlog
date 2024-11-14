package org.sweetie.objectlog.core.config;

/*
 * FileName: LogFilter
 * Author gouhao
 */

import org.sweetie.objectlog.core.utils.ThreadLocalUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/*")
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {

        } finally {
            //清空缓存信息
            ThreadLocalUtil.removeParentId();
            ThreadLocalUtil.resetCurrentThreadInfo();
        }
    }

    @Override
    public void destroy() {
    }
}
