package org.sweetie.objectlog.objectlog.service.impl;
/*
 * FileName: ObjectOperationServiceImpl
 * Author gouhao
 */

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.sweetie.objectlog.core.Context;
import org.sweetie.objectlog.core.model.ObjectAttributeModel;
import org.sweetie.objectlog.core.model.ObjectOperationModel;
import org.sweetie.objectlog.domain.ObjectOperationDto;
import org.sweetie.objectlog.objectlog.mapper.ObjectOperationMapper;
import org.sweetie.objectlog.objectlog.service.ObjectAttributeService;
import org.sweetie.objectlog.objectlog.service.ObjectOperationService;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ObjectOperationServiceImpl extends ServiceImpl<ObjectOperationMapper, ObjectOperationModel> implements ObjectOperationService {
    private final Logger logger = LoggerFactory.getLogger(ObjectOperationServiceImpl.class);
    @Resource
    private ObjectAttributeService attributeService;

    public void addLog(ObjectOperationDto dto) {
        if (StrUtil.isBlank(Context.getToken())) {
            logger.info("未登录");
            return;
        }
        ObjectOperationModel insertModel = new ObjectOperationModel();
        BeanUtils.copyProperties(dto, insertModel);
        EntityWrapper<ObjectOperationModel> ew = new EntityWrapper<>();
        ew.eq("object_id", insertModel.getObjectId());
        ew.orderBy("version", false);
        ObjectOperationModel tableModel = this.selectOne(ew);
        int version = 1;
        if (null != tableModel) {
            version = tableModel.getVersion() + 1;
        }
        insertModel.setVersion(version);
        this.insert(insertModel);
        if (CollUtil.isNotEmpty(dto.getAttributes())) {
            dto.getAttributes().forEach(item -> item.setOperationId(insertModel.getId()));
            attributeService.insertBatch(dto.getAttributes());
        }
    }

    public Map<String, List<ObjectOperationDto>> query(ObjectOperationDto queryDto) {
        EntityWrapper<ObjectOperationModel> ew = new EntityWrapper<>();
        this.dealWrapper(ew, queryDto);
        ew.orderBy("version", true);
        List<ObjectOperationModel> models = this.selectList(ew);
        if (CollUtil.isEmpty(models)) {
            return Collections.emptyMap();
        }
        List<ObjectOperationDto> result = this.modelToDto(models);
        return result.stream().collect(
                Collectors.groupingBy(ObjectOperationDto::getObjectId, HashMap::new, Collectors.toList()));
    }

    private List<ObjectOperationDto> modelToDto(List<ObjectOperationModel> models) {
        if (CollUtil.isEmpty(models)) {
            return new ArrayList<>();
        }
        List<String> operationIdList = models.stream().map(ObjectOperationModel::getId).collect(Collectors.toList());
        List<ObjectAttributeModel> attributeList = attributeService.getModelsByOperationId(operationIdList);
        HashMap<String, List<ObjectAttributeModel>> attributeWithOperationIdMap = attributeList.stream().collect(Collectors.groupingBy(ObjectAttributeModel::getOperationId, HashMap::new, Collectors.toList()));
        ObjectOperationDto dto;
        List<ObjectOperationDto> result = new ArrayList<>(models.size());
        for (ObjectOperationModel model : models) {
            dto = new ObjectOperationDto();
            BeanUtils.copyProperties(model, dto);
            dto.setAttributes(attributeWithOperationIdMap.get(model.getId()));
            result.add(dto);
        }
        return result;
    }

    private void dealWrapper(EntityWrapper<ObjectOperationModel> entityWrapper, ObjectOperationDto queryDto) {
        if (StrUtil.isNotBlank(queryDto.getObjectId())) {
            entityWrapper.eq("object_id", queryDto.getObjectId());
        }
        if (StrUtil.isNotBlank(queryDto.getObjectIds())) {
            entityWrapper.in("object_id", Arrays.asList(queryDto.getObjectIds().split(",")));
        }
        if (StrUtil.isNotBlank(queryDto.getParentId())) {
            entityWrapper.eq("parent_id", queryDto.getParentId());
        }
        if (StrUtil.isNotBlank(queryDto.getParentIds())) {
            entityWrapper.in("parent_id", Arrays.asList(queryDto.getParentIds().split(",")));
        }
        if (StrUtil.isNotBlank(queryDto.getId())) {
            entityWrapper.eq("id", queryDto.getId());
        }
        if (StrUtil.isNotBlank(queryDto.getIds())) {
            entityWrapper.in("id", Arrays.asList(queryDto.getIds().split(",")));
        }
        if (StrUtil.isNotBlank(queryDto.getModuleName())) {
            entityWrapper.like("module_name", queryDto.getModuleName());
        }
    }
}
