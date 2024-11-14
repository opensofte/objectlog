package org.sweetie.objectlog.objectlog.service;
/*
 * FileName: ObjectOperationService
 * Author gouhao
 */

import com.baomidou.mybatisplus.service.IService;
import org.sweetie.objectlog.core.model.ObjectAttributeModel;

import java.util.List;

public interface ObjectAttributeService extends IService<ObjectAttributeModel> {
    List<ObjectAttributeModel> getModelsByOperationId(List<String> idList);
}
