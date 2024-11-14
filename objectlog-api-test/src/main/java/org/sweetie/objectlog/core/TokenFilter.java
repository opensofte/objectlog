package org.sweetie.objectlog.core;
/*
 * FileName: TokenFilter
 * Author gouhao
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@WebFilter("/*")
public class TokenFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
            String token = httpRequest.getHeader("Authorization");
            Context.setToken(token);
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            logger.error("异常：" + e.getMessage(), e);
        } finally {
            Context.removeToken();
        }

    }
}
