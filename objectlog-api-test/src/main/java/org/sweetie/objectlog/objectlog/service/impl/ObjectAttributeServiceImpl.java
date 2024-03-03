package org.sweetie.objectlog.objectlog.service.impl;/*
 * Copyright (C), 2021-2024
 * FileName: ObjectOperationServiceImpl
 * Author gouhao
 * Date: 2024/3/2 16:25
 * Description:
 */

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.sweetie.objectlog.core.model.ObjectAttributeModel;
import org.sweetie.objectlog.core.model.ObjectOperationModel;
import org.sweetie.objectlog.domain.ObjectOperationDto;
import org.sweetie.objectlog.objectlog.mapper.ObjectAttributeMapper;
import org.sweetie.objectlog.objectlog.mapper.ObjectOperationMapper;
import org.sweetie.objectlog.objectlog.service.ObjectAttributeService;
import org.sweetie.objectlog.objectlog.service.ObjectOperationService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ObjectAttributeServiceImpl extends ServiceImpl<ObjectAttributeMapper, ObjectAttributeModel> implements ObjectAttributeService {
    @Resource
    private ObjectAttributeMapper attributeMapper;

    public List<ObjectAttributeModel> getModelsByOperationId(List<String> idList){
        EntityWrapper<ObjectAttributeModel> ew = new EntityWrapper<>();
        ew.in("operation_id",idList);
        return selectList(ew);
    }
}
