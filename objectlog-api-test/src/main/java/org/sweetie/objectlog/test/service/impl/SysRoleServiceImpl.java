package org.sweetie.objectlog.test.service.impl;
/*
 * FileName: ObjectOperationServiceImpl
 * Author gouhao
 */

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.sweetie.objectlog.test.mapper.SysRoleMapper;
import org.sweetie.objectlog.test.model.SysRoleModel;
import org.sweetie.objectlog.test.service.SysRoleService;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleModel> implements SysRoleService {

}
