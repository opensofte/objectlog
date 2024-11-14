package org.sweetie.objectlog.test.service.impl;
/*
 * FileName: ObjectOperationServiceImpl
 * Author gouhao
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
    @LogPoint(serviceHandler = SysUserServiceImpl.class, entityHandler = SysUserModel.class,
            moduleName = "sysUser", remark = "add")
    public void add(SysUserModel insertModel) {
        this.insert(insertModel);
    }

    @Override
    @LogPoint(serviceHandler = SysUserServiceImpl.class, entityHandler = SysUserModel.class,
            moduleName = "sysUser", remark = "update")
    public void update(SysUserModel insertModel) {
        this.updateById(insertModel);
    }

    @Override
    @LogPoint(serviceHandler = SysUserServiceImpl.class, entityHandler = SysUserModel.class,
            moduleName = "sysUser", remark = "delete")
    public void delete(String id) {
        this.deleteById(id);
    }


    @Override
    @LogPoint(serviceHandler = SysUserServiceImpl.class, entityHandler = SysUserModel.class,
            operation = OperationEnum.COMMON, moduleName = "sysUser", remark = "addList")
    public void addList(List<SysUserModel> insertModelList) {
        this.insertBatch(insertModelList);
    }

    @Override
    @LogPoint(serviceHandler = SysUserServiceImpl.class, entityHandler = SysUserModel.class,
            operation = OperationEnum.COMMON, moduleName = "sysUser", remark = "updateList")
    public void updateList(List<SysUserModel> insertModelList) {
        this.updateBatchById(insertModelList);
    }

    @Override
    @LogPoint(serviceHandler = SysUserServiceImpl.class, entityHandler = SysUserModel.class,
            operation = OperationEnum.COMMON, moduleName = "sysUser", remark = "deleteList")
    public void deleteList(List<String> idList) {
        this.deleteBatchIds(idList);
    }

    @Override
    @LogPoint(serviceHandler = SysUserServiceImpl.class, entityHandler = SysUserModel.class,
            operation = OperationEnum.COMPLEX, moduleName = "sysUser", remark = "addAssociate")
    public void addAssociate(SysUserModel userModel) {
        this.insert(userModel);
        roleService.insert(userModel.getRoleModel());
    }

    @Override
    @LogPoint(serviceHandler = SysUserServiceImpl.class, entityHandler = SysUserModel.class,
            operation = OperationEnum.COMPLEX, moduleName = "sysUser", remark = "updateAssociate")
    public void updateAssociate(SysUserModel userModel) {
        this.updateById(userModel);
        roleService.updateById(userModel.getRoleModel());
    }

    @Override
    @LogPoint(serviceHandler = SysUserServiceImpl.class, entityHandler = SysUserModel.class,
            operation = OperationEnum.COMPLEX, moduleName = "sysUser", remark = "deleteAssociate")
    public void deleteAssociate(String userId, String roleId) {
        this.deleteById(userId);
        roleService.deleteById(roleId);
    }
}
