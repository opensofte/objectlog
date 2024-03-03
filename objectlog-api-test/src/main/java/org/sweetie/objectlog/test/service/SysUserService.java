package org.sweetie.objectlog.test.service;/*
 * Copyright (C), 2021-2024
 * FileName: ObjectOperationService
 * Author gouhao
 * Date: 2024/3/2 16:25
 * Description:
 */

import com.baomidou.mybatisplus.service.IService;
import org.sweetie.objectlog.test.model.SysUserModel;

import java.util.List;

public interface SysUserService  extends IService<SysUserModel> {
    void add(SysUserModel insertModel);

    void addList(List<SysUserModel> insertModelList);

    void addassociate(SysUserModel userModel);
}
