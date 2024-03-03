package org.sweetie.objectlog.test.controller;/*
 * Copyright (C), 2021-2024
 * FileName: ObjectOperationController
 * Author gouhao
 * Date: 2024/3/2 16:23
 * Description:
 */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.sweetie.objectlog.domain.ObjectOperationDto;
import org.sweetie.objectlog.objectlog.service.ObjectOperationService;
import org.sweetie.objectlog.test.model.SysRoleModel;
import org.sweetie.objectlog.test.model.SysUserModel;
import org.sweetie.objectlog.test.service.SysRoleService;
import org.sweetie.objectlog.test.service.SysUserService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/test")
public class TestController {
    @Resource
    private SysUserService userService;

    @GetMapping(value = "/add")
    @ResponseBody
    public void testAdd(){
        SysUserModel userModel = new SysUserModel();
        userModel.setRoleId("9830274072323");
        userModel.setUserName("哈哈");
        userModel.setRemark("这是第一行\n" + "这是第二行");
        userModel.setStatus(0);
        userModel.setRichText("<p1>富文本<p1>");
        userService.add(userModel);
        //查看数据库
    }

    @GetMapping(value = "/addlist")
    @ResponseBody
    public void testAddList(){
        List<SysUserModel> insertList = new ArrayList<>();
        SysUserModel userModel = new SysUserModel();
        userModel.setRoleId("9830274072323");
        userModel.setUserName("哈哈");
        userModel.setRemark("这是第一行\n" + "这是第二行");
        userModel.setStatus(0);
        userModel.setRichText("<p1>富文本<p1>");

        SysUserModel userMode2 = new SysUserModel();
        userMode2.setRoleId("9830274072323");
        userMode2.setUserName("哈哈1");
        userMode2.setRemark("这是第一行1\n" + "这是第二行1");
        userMode2.setStatus(1);
        userMode2.setRichText("<p1>富文本1<p1>");
        insertList.add(userModel);
        insertList.add(userMode2);
        userService.addList(insertList);
        //查看数据库
    }


    @GetMapping(value = "/addassociate")
    @ResponseBody
    public void addassociate(){
        //用户
        SysUserModel userModel = new SysUserModel();
        userModel.setRoleId("9830274072323");
        userModel.setUserName("哈哈");
        userModel.setRemark("这是第一行\n" + "这是第二行");
        userModel.setStatus(0);
        userModel.setRichText("<p1>富文本<p1>");

        //角色
        SysRoleModel roleModel = new SysRoleModel();
        roleModel.setRoleName("测试啊啊啊啊");
        roleModel.setRoleKey("testaaa");
        roleModel.setStatus(0);

        //关联
        userModel.setRoleModel(roleModel);

        userService.addassociate(userModel);
        //查看数据库
    }
}


