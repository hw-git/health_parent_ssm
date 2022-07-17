package cn.lanqiao.controller;

import cn.lanqiao.entity.Result;
import cn.lanqiao.pojo.Menu;
import cn.lanqiao.service.UserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Hou
 * @Date: 2021/5/25 10:12
 * @Description:菜单动态显示
 */
@RestController
@RequestMapping("/userListController")
public class UserListController {

    //注入服务接口
    @Autowired
    private UserListService userListService;

    /**
     * 根据用户名，回显动态菜单
     *
     * @param username
     * @return
     */
    @RequestMapping("/userMenu")
    public Result userMenu(String username) {
        try {
            List<Menu> menus = userListService.findMenuByUsername(username);
            return new Result(true, "菜单查询成功", menus);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, "菜单查询失败");
        }

    }
}
