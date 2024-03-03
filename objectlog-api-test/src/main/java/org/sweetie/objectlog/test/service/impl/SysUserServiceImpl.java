package org.sweetie.objectlog.test.service.impl;/*
 * Copyright (C), 2021-2024
 * FileName: ObjectOperationServiceImpl
 * Author gouhao
 * Date: 2024/3/2 16:25
 * Description:
 */

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sweetie.objectlog.core.annotation.LogPoint;
import org.sweetie.objectlog.core.enums.OperationEnum;
import org.sweetie.objectlog.test.mapper.SysUserMapper;
import org.sweetie.objectlog.test.model.SysUserModel;
import org.sweetie.objectlog.test.service.SysRoleService;
import org.sweetie.objectlog.test.service.SysUserService;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserModel> implements SysUserService {

    @Resource
    private SysRoleService roleService;

    @Override
    @LogPoint( serviceHandler = SysUserServiceImpl.class, entityHandler = SysUserModel.class,
            moduleName = "sysUser",  remark = "测试用户模块")
    public void add(SysUserModel insertModel) {
        this.insert(insertModel);
    }

    @Override
    @LogPoint( serviceHandler = SysUserServiceImpl.class, entityHandler = SysUserModel.class,
            operation = OperationEnum.COMMON, moduleName = "sysUser",  remark = "测试用户模块",multiple = true)
    public void addList(List<SysUserModel> insertModelList) {
        this.insertBatch(insertModelList);
    }

    @Override
    @LogPoint( serviceHandler = SysUserServiceImpl.class, entityHandler = SysUserModel.class,
            operation = OperationEnum.COMPLEX, moduleName = "sysUser",  remark = "测试用户模块",multiple = true)
    public void addassociate(SysUserModel userModel) {
        this.insert(userModel);
        roleService.insert(userModel.getRoleModel());
    }
}
