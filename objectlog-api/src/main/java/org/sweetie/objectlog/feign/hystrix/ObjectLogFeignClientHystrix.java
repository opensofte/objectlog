package org.sweetie.objectlog.feign.hystrix;/*
 * Copyright (C), 2021-2023
 * FileName: ObjectLogFeignClientHystrix
 * Author gouhao
 * Date: 2023/12/9 10:31
 * Description:
 */

import feign.hystrix.FallbackFactory;
import org.sweetie.objectlog.domain.ObjectOperationDto;
import org.sweetie.objectlog.feign.ObjectLogFeignClient;

import java.util.List;
import java.util.Map;

public class ObjectLogFeignClientHystrix implements FallbackFactory<ObjectLogFeignClient> {
    @Override
    public ObjectLogFeignClient create(Throwable throwable) {
        return new ObjectLogFeignClient(){
            @Override
            public void addLog(ObjectOperationDto model) {

            }
            @Override
            public Map<String, List<ObjectOperationDto>> query(ObjectOperationDto queryDto0) {
                return null;
            }
        };
    }
}
