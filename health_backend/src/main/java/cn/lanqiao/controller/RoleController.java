package cn.lanqiao.controller;

import cn.lanqiao.constant.MessageConstant;
import cn.lanqiao.entity.Result;
import cn.lanqiao.pojo.Role;
import cn.lanqiao.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Hou
 * @Date: 2021/5/25 09:59
 * @Description:角色管理
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 查询所有角色信息
     *
     * @return
     */
    @RequestMapping("/list")
    public Result findAll() {
        try {
            List<Role> list = roleService.list();
            return new Result(true, MessageConstant.QUERY_ROLE_OK, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ROLE_FAIL);
        }
    }
}
