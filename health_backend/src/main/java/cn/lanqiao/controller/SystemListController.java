package cn.lanqiao.controller;

import cn.lanqiao.constant.ResourcesPermissionCon;
import cn.lanqiao.entity.MenuResult;
import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.entity.Result;
import cn.lanqiao.pojo.Menu;
import cn.lanqiao.service.SystemListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Hou
 * @Date: 2021/5/24 18:35
 * @Description:权限管理菜单管理
 */
@RestController
@RequestMapping("/systemListController")
public class SystemListController {
    //注入Service
    @Autowired
    private SystemListService systemListService;

    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = systemListService.pageQuery(queryPageBean);
            return new Result(true, ResourcesPermissionCon.QUERY_LIST_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, ResourcesPermissionCon.QUERY_LIST_FAIL);
        }

    }

    /**
     * 新建菜单时自关联项目名称回显
     *
     * @return
     */
    @RequestMapping("/findAllLable")
    public Result findAllLable() {
        try {
            List<Menu> menuList = systemListService.findAllLable();

            MenuResult menuResult = null;

            List<MenuResult> returnList = new ArrayList<>();
            menuResult = new MenuResult();
            menuResult.setValue(0);
            menuResult.setLabel("无自关联");
            returnList.add(menuResult);
            for (Menu menu : menuList) {
                Menu lableInfo = menu;
                menuResult = new MenuResult();
                menuResult.setLabel(lableInfo.getName());
                menuResult.setValue(lableInfo.getId());
                returnList.add(menuResult);
            }

            return new Result(true, ResourcesPermissionCon.QUERY_LIST_SUCCESS, returnList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, ResourcesPermissionCon.QUERY_LIST_FAIL);
        }
    }

    /**
     * 新建菜单
     *
     * @param value
     * @param menu
     * @return
     */
    @RequestMapping("/add")
    public Result add(Integer value, @RequestBody Menu menu) {
        try {
            systemListService.add(value, menu);
            return new Result(true, ResourcesPermissionCon.ADD_LIST_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, ResourcesPermissionCon.ADD_LIST_FAIL);
        }
    }

    /**
     * 根据id查询menu基本信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/findMenuById")
    public Result findMenuById(Integer id) {
        try {
            Menu menu = systemListService.findMenuById(id);
            return new Result(true, ResourcesPermissionCon.QUERY_LIST_SUCCESS, menu);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, ResourcesPermissionCon.QUERY_LIST_FAIL);
        }
    }

    /**
     * 菜单编辑
     *
     * @param value
     * @param menu
     * @return
     */
    @RequestMapping("/edit")
    public Result edit(Integer value, @RequestBody Menu menu) {
        try {
            systemListService.edit(value, menu);
            return new Result(true, ResourcesPermissionCon.EDIT_LIST_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, ResourcesPermissionCon.EDIT_LIST_FAIL);
        }
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            systemListService.delete(id);
            return new Result(true, ResourcesPermissionCon.DELETE_LIST_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, ResourcesPermissionCon.DELETE_LIST_FAIL);
        }
    }

    /**
     * 角色管理菜单信息回显
     *
     * @return
     */
    @RequestMapping("/findAll")
    public Result findAll() {
        try {
            List<Menu> menuList = systemListService.findAll();
            return new Result(true, ResourcesPermissionCon.QUERY_LIST_SUCCESS, menuList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, ResourcesPermissionCon.QUERY_LIST_FAIL);
        }
    }

}
