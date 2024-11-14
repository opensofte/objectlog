package org.sweetie.objectlog.test.service;
/*
 * FileName: ObjectOperationService
 * Author gouhao
 */

import com.baomidou.mybatisplus.service.IService;
import org.sweetie.objectlog.test.model.SysUserModel;

import java.util.List;

public interface SysUserService extends IService<SysUserModel> {
    void add(SysUserModel insertModel);

    void update(SysUserModel insertModel);

    void delete(String id);

    void addList(List<SysUserModel> insertModelList);

    void updateList(List<SysUserModel> insertModelList);

    void deleteList(List<String> idList);

    void addAssociate(SysUserModel userModel);

    void updateAssociate(SysUserModel userModel);

    void deleteAssociate(String userId, String roleId);
}
