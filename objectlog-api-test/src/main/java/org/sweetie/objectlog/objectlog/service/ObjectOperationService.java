package org.sweetie.objectlog.objectlog.service;
/*
 * FileName: ObjectOperationService
 * Author gouhao
 */

import com.baomidou.mybatisplus.service.IService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.sweetie.objectlog.core.model.ObjectOperationModel;
import org.sweetie.objectlog.domain.ObjectOperationDto;

import java.util.List;
import java.util.Map;

public interface ObjectOperationService extends IService<ObjectOperationModel> {
    @PostMapping("/add")
    public void addLog(@RequestBody ObjectOperationDto model);

    @PostMapping("/query")
    public Map<String, List<ObjectOperationDto>> query(ObjectOperationDto queryDto0);
}
