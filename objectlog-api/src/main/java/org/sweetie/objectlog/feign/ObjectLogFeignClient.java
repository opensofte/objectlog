package org.sweetie.objectlog.feign;/*
 * Copyright (C), 2021-2023
 * FileName: ObjectLogFeignClient
 * Author gouhao
 * Date: 2023/12/2 19:02
 * Description:
 */

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.sweetie.objectlog.domain.ObjectOperationDto;
import org.sweetie.objectlog.feign.config.ObjectLogTokenInterceptor;
import org.sweetie.objectlog.feign.hystrix.ObjectLogFeignClientHystrix;

import java.util.List;
import java.util.Map;

@FeignClient(value = "objectlog-api-test", path ="/objectlog", fallbackFactory = ObjectLogFeignClientHystrix.class, configuration = ObjectLogTokenInterceptor.class)
public interface ObjectLogFeignClient {

    @PostMapping("/add")
    public void addLog(@RequestBody ObjectOperationDto model);

    @PostMapping("/query")
    public Map<String, List<ObjectOperationDto>> query(ObjectOperationDto queryDto0);

}
