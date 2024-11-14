package org.sweetie.objectlog.objectlog.service.impl;
/*
 * FileName: ObjectOperationServiceImpl
 * Author gouhao
 */

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.sweetie.objectlog.core.model.ObjectAttributeModel;
import org.sweetie.objectlog.objectlog.mapper.ObjectAttributeMapper;
import org.sweetie.objectlog.objectlog.service.ObjectAttributeService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ObjectAttributeServiceImpl extends ServiceImpl<ObjectAttributeMapper, ObjectAttributeModel> implements ObjectAttributeService {
    @Resource
    private ObjectAttributeMapper attributeMapper;

    public List<ObjectAttributeModel> getModelsByOperationId(List<String> idList) {
        EntityWrapper<ObjectAttributeModel> ew = new EntityWrapper<>();
        ew.in("operation_id", idList);
        return selectList(ew);
    }
}
