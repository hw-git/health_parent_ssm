package cn.lanqiao.controller;

import cn.lanqiao.constant.ResourcesPermissionCon;
import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.entity.Result;
import cn.lanqiao.pojo.Role;
import cn.lanqiao.service.RoleListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Hou
 * @Date: 2021/5/25 09:51
 * @Description:角色信息管理
 */
@RestController
@RequestMapping("/roleListController")
public class RoleListController {

    //注入服务i接口
    @Autowired
    private RoleListService roleListService;

    /**
     * 角色信息分页显示
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = roleListService.pageQuery(queryPageBean);
            return new Result(true, ResourcesPermissionCon.QUERY_ROLE_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, ResourcesPermissionCon.QUERY_ROLE_FAIL);
        }
    }

    /**
     * 添加角色
     *
     * @param role
     * @param permissionIds
     * @param menuIds
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Role role, Integer[] permissionIds, Integer[] menuIds) {
        try {
            roleListService.add(role, permissionIds, menuIds);
            return new Result(true, ResourcesPermissionCon.ADD_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, ResourcesPermissionCon.ADD_ROLE_FAIL);
        }
    }

    /**
     * 根据id修改角色数据回显
     *
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            Role role = roleListService.findById(id);
            return new Result(true, ResourcesPermissionCon.QUERY_ROLE_SUCCESS, role);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, ResourcesPermissionCon.QUERY_ROLE_FAIL);
        }
    }

    /**
     * 根据角色id查询资源权限信息已关联id
     *
     * @param id
     * @return
     */
    @RequestMapping("/findPermissionIds")
    public Result findPermissionIds(Integer id) {
        try {
            List<Integer> permissionIds = roleListService.findPermissionIds(id);
            return new Result(true, ResourcesPermissionCon.QUERY_RES_SUCCESS, permissionIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, ResourcesPermissionCon.QUERY_RES_FAIL);
        }
    }

    /**
     * 根据角色id查询关联的菜单id
     *
     * @param id
     * @return
     */
    @RequestMapping("/findMenuIds")
    public Result findMenuIds(Integer id) {
        try {
            List<Integer> menuIds = roleListService.findMenuIds(id);
            return new Result(true, ResourcesPermissionCon.QUERY_LIST_SUCCESS, menuIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, ResourcesPermissionCon.QUERY_LIST_FAIL);
        }
    }

    /**
     * 修改角色信息
     *
     * @param role
     * @param permissionIds
     * @param menuIds
     * @return
     */
    @RequestMapping("/edit")
    public Result edit(@RequestBody Role role, Integer[] permissionIds, Integer[] menuIds) {
        try {
            roleListService.edit(role, permissionIds, menuIds);
            return new Result(true, ResourcesPermissionCon.EDIT_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, ResourcesPermissionCon.EDIT_ROLE_FAIL);
        }
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            roleListService.delete(id);
            return new Result(true, ResourcesPermissionCon.DELETE_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, ResourcesPermissionCon.DELETE_ROLE_FAIL);
        }
    }
}
