package org.sweetie.objectlog.core.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Author: gouhao
 * @Date: 2024/08/30/10:12
 * @Description:
 */
@Component
@ConfigurationProperties(prefix = "object-log")
@Data
@Slf4j
public class LogConfigProperty {
    private String host;
    private String path;
    private List<String> header;

    @PostConstruct
    public void init() {
        log.info("当前日志记录配置信息：host:{}, url:{}, header:{}", host, path, header);
    }
}
