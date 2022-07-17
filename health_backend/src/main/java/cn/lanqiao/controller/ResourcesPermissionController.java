package cn.lanqiao.controller;

import cn.lanqiao.constant.ResourcesPermissionCon;
import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.entity.Result;
import cn.lanqiao.pojo.Permission;
import cn.lanqiao.service.ResourcesPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Hou
 * @Date: 2021/5/24 18:53
 * @Description:资源权限管理
 */
@RestController
@RequestMapping("/resourcesPermissionController")
public class ResourcesPermissionController {
    //注入服务接口
    @Autowired
    private ResourcesPermissionService resourcesPermissionService;

    /**
     * 资源权限分页
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = resourcesPermissionService.pageQuery(queryPageBean);
            return new Result(true, ResourcesPermissionCon.QUERY_RES_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, ResourcesPermissionCon.QUERY_RES_FAIL);
        }
    }

    /**
     * 编辑表单回显
     *
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            Permission permission = resourcesPermissionService.findById(id);
            return new Result(true, ResourcesPermissionCon.QUERY_RES_SUCCESS, permission);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, ResourcesPermissionCon.QUERY_RES_FAIL);
        }
    }

    /**
     * 编辑提交表单
     *
     * @param permission
     * @return
     */
    @RequestMapping("/edit")
    public Result edit(@RequestBody Permission permission) {
        try {
            resourcesPermissionService.edit(permission);
            return new Result(true, ResourcesPermissionCon.EDIT_RES_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, ResourcesPermissionCon.EDIT_RES_FAIL);
        }
    }

    /**
     * 新增资源权限
     *
     * @param permission
     * @return
     */
    @RequestMapping("add")
    public Result add(@RequestBody Permission permission) {
        try {
            resourcesPermissionService.add(permission);
            return new Result(true, ResourcesPermissionCon.ADD_RES_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, ResourcesPermissionCon.ADD_RES_FAIL);
        }
    }

    /**
     * 删除资源权限
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            resourcesPermissionService.delete(id);
            return new Result(true, ResourcesPermissionCon.DELETE_RES_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, ResourcesPermissionCon.DELETE_RES_FAIL);
        }
    }

    /**
     * 切换权限状态
     *
     * @param id
     * @return
     */
    @RequestMapping("/change")
    public Result change(Integer id) {
        try {
            resourcesPermissionService.change(id);
            return new Result(true, ResourcesPermissionCon.CHANGE_RES_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, ResourcesPermissionCon.CHANGE_RES_FAIL);
        }
    }

    /**
     * 角色管理资源权限信息回显
     *
     * @return
     */
    @RequestMapping("/findAll")
    public Result findAll() {
        try {
            List<Permission> permissions = resourcesPermissionService.findAll();
            return new Result(true, ResourcesPermissionCon.QUERY_RES_SUCCESS, permissions);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, ResourcesPermissionCon.QUERY_RES_FAIL);
        }

    }
}
