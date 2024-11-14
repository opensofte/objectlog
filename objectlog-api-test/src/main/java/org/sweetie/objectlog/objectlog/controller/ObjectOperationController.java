package org.sweetie.objectlog.objectlog.controller;
/*
 * FileName: ObjectOperationController
 * Author gouhao
 */

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.sweetie.objectlog.domain.ObjectOperationDto;
import org.sweetie.objectlog.objectlog.service.ObjectOperationService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/objectlog")
public class ObjectOperationController {
    @Resource
    private ObjectOperationService operationService;

    @PostMapping("/add")
    public void addLog(@RequestBody ObjectOperationDto model) {
        operationService.addLog(model);
    }

    @PostMapping("/query")
    public Map<String, List<ObjectOperationDto>> query(ObjectOperationDto queryDto) {
        return operationService.query(queryDto);
    }
}
