package cn.lanqiao.controller;

import cn.lanqiao.constant.MessageConstant;
import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.entity.Result;
import cn.lanqiao.service.RoleService;
import cn.lanqiao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Hou
 * @Date: 2021/5/25 08:52
 * @Description:用户
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    //获取当前登录用户的用户名
    @RequestMapping("/getUsername")
    public Result getUsername() throws Exception {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, user.getUsername());
        } catch (Exception e) {
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }

    /**
     * 用户信息分页查询
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPageUser(@RequestBody QueryPageBean queryPageBean) {
        return userService.findPageUser(queryPageBean);
    }

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    @RequestMapping("/addUser")
    public Result addUser(@RequestBody cn.lanqiao.pojo.User user, Integer[] roleIds) {
        try {
            return userService.addUser(user, roleIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_MEMBER_FAIL);
        }
    }

    /**
     * 编辑用户
     *
     * @param user    用户信息
     * @param roleIds 角色id集合
     * @return
     */
    @RequestMapping("/editUser")
    public Result editUser(@RequestBody cn.lanqiao.pojo.User user, Integer[] roleIds) {
        try {
            return userService.edit(user, roleIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_USER_FAIL);
        }
    }

    /**
     * 删除用户
     *
     * @param userId 用户id
     * @return
     */
    @RequestMapping("/delete")
    public Result deleteUser(Integer userId) {
        try {
            userService.delete(userId);
            return new Result(true, MessageConstant.DELETE_USER_OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_USER_FAIL);
        }
    }

    /**
     * 编辑用户回显
     *
     * @param userName
     * @return
     */
    @RequestMapping("/userEcho")
    public Result userEcho(String userName) {
        try {
            cn.lanqiao.pojo.User user = userService.findByUsername(userName);
            return new Result(true, MessageConstant.QUERY_USER_OK, user);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_USER_FAIL);
        }
    }

    /**
     * 查询用户角色集合
     *
     * @param userId 用户id
     * @return
     */
    @RequestMapping("/getRoleIdsByUser")
    public Result getRoleIdsByUser(Integer userId) {
        try {
            List<Integer> roleIds = roleService.getRoleIdsByUser(userId);
            return new Result(true, null, roleIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.QUERY_ROLE_FAIL);
        }
    }

    @RequestMapping("/login")
    public Result login(String username, String password) {
        System.out.println("username = " + username);
        System.out.println("password = " + password);
        //远程调用用户服务，根据用户名查询用户信息
        cn.lanqiao.pojo.User user = null;
        try {
            return userService.login(username, password);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.LOGIN_FAIL);
        }
    }
}
