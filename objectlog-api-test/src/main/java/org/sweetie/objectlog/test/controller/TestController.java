package org.sweetie.objectlog.test.controller;
/*
 * FileName: ObjectOperationController
 * Author gouhao
 */

import org.springframework.web.bind.annotation.*;
import org.sweetie.objectlog.domain.ObjectOperationDto;
import org.sweetie.objectlog.objectlog.service.ObjectOperationService;
import org.sweetie.objectlog.test.model.SysRoleModel;
import org.sweetie.objectlog.test.model.SysUserModel;
import org.sweetie.objectlog.test.service.SysUserService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/test")
public class TestController {
    @Resource
    private SysUserService userService;

    @Resource
    private ObjectOperationService service;
    private final String singleId = "1111111111111111111";
    private final String listOne = "222222222222222222222";
    private final String listTwo = "333333333333333333333";
    private final String userId = "4444444444444444444444444";
    private final String roleId = "55555555555555555555555";

    /**
     * 代码持续更新，后续补充其他特性，例如【分库分表】技术中的分表。如有需要请关注仓库，感谢支持。
     */
    @GetMapping(value = "/add")
    @ResponseBody
    public void add() {
        SysUserModel userModel = new SysUserModel();
        userModel.setId(singleId);
        userModel.setRoleId("9830274072323");
        userModel.setUserName("哈哈");
        userModel.setRemark("这是第一行\n" + "这是第二行");
        userModel.setStatus(0);
        userModel.setRichText("<p1>富文本<p1>");
        userService.add(userModel);
        //查看数据库
    }

    @GetMapping(value = "/update")
    @ResponseBody
    public void update() {
        SysUserModel userModel = new SysUserModel();
        userModel.setId(singleId);
        userModel.setRoleId("9830274072323");
        userModel.setUserName("喜喜");
        userModel.setRemark("这是第三行\n" + "这是第四行");
        userModel.setStatus(1);
        userModel.setRichText("<p1>多文本<p1>");
        userService.update(userModel);
        //查看数据库
    }

    @GetMapping(value = "/delete")
    @ResponseBody
    public void delete() {
        userService.delete(singleId);
    }

    @GetMapping(value = "/addlist")
    @ResponseBody
    public void addList() {
        List<SysUserModel> insertList = new ArrayList<>();
        SysUserModel userModel = new SysUserModel();
        userModel.setId(listOne);
        userModel.setRoleId("9830274072323");
        userModel.setUserName("哈哈");
        userModel.setRemark("这是第一行\n" + "这是第二行");
        userModel.setStatus(0);
        userModel.setRichText("<p1>富文本<p1>");

        SysUserModel userMode2 = new SysUserModel();
        userMode2.setId(listTwo);
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

    @GetMapping(value = "/updatelist")
    @ResponseBody
    public void updateList() {
        List<SysUserModel> insertList = new ArrayList<>();
        SysUserModel userModel = new SysUserModel();
        userModel.setId(listOne);
        userModel.setRoleId("9830274072323");
        userModel.setUserName("喜喜");
        userModel.setRemark("这是第三行\n" + "这是第四行");
        userModel.setStatus(1);
        userModel.setRichText("<p1>多文本<p1>");

        SysUserModel userMode2 = new SysUserModel();
        userMode2.setId(listTwo);
        userModel.setRoleId("9830274072323");
        userModel.setUserName("喜喜");
        userModel.setRemark("这是第五行\n" + "这是第六行");
        userModel.setStatus(1);
        userModel.setRichText("<p1>多文本<p1>");
        insertList.add(userModel);
        insertList.add(userMode2);
        userService.updateList(insertList);
        //查看数据库
    }

    @GetMapping(value = "/deletelist")
    @ResponseBody
    public void deleteList() {
        userService.deleteList(Arrays.asList(listOne, listTwo));
    }

    @GetMapping(value = "/addassociate")
    @ResponseBody
    public void addAssociate() {
        //用户
        SysUserModel userModel = new SysUserModel();
        userModel.setId(userId);
        userModel.setRoleId("9830274072323");
        userModel.setUserName("哈哈");
        userModel.setRemark("这是第一行\n" + "这是第二行");
        userModel.setStatus(0);
        userModel.setRichText("<p1>富文本<p1>");

        //角色
        SysRoleModel roleModel = new SysRoleModel();
        roleModel.setId(roleId);
        roleModel.setRoleName("测试啊啊啊啊");
        roleModel.setRoleKey("testaaa");
        roleModel.setStatus(0);

        //关联
        userModel.setRoleModel(roleModel);

        userService.addAssociate(userModel);
        //查看数据库
    }

    @GetMapping(value = "/updateassociate")
    @ResponseBody
    public void updateAssociate() {
        //用户
        SysUserModel userModel = new SysUserModel();
        userModel.setId(userId);
        userModel.setRoleId("9830274072323");
        userModel.setUserName("喜喜");
        userModel.setRemark("这是第三行\n" + "这是第四行");
        userModel.setStatus(1);
        userModel.setRichText("<p1>多文本<p1>");

        //角色
        SysRoleModel roleModel = new SysRoleModel();
        roleModel.setId(roleId);
        roleModel.setRoleName("咯咯咯咯");
        roleModel.setRoleKey("update");
        roleModel.setStatus(0);

        //关联
        userModel.setRoleModel(roleModel);

        userService.updateAssociate(userModel);
        //查看数据库
    }


    @GetMapping(value = "/deleteassociate")
    @ResponseBody
    public void deleteAssociate() {
        userService.deleteAssociate(userId, roleId);
    }

    @PostMapping(value = "/getlog")
    @ResponseBody
    public Object get(@RequestBody ObjectOperationDto dto) {
        return service.query(dto);
    }
}


