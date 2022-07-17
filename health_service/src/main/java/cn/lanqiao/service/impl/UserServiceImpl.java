package cn.lanqiao.service.impl;

import cn.lanqiao.constant.MessageConstant;
import cn.lanqiao.dao.PermissionDao;
import cn.lanqiao.dao.RoleDao;
import cn.lanqiao.dao.UserDao;
import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.entity.Result;
import cn.lanqiao.pojo.Permission;
import cn.lanqiao.pojo.Role;
import cn.lanqiao.pojo.User;
import cn.lanqiao.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;


/**
 * @Author: Hou
 * @Date: 2021/5/25 08:57
 * @Description:
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findByUsername(String username) {
        username = new String(username.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        // 获取User
        User user = userDao.findByUsername(username);
        if (user == null) {
            return null;
        }

        // 获取Role
        Integer userId = user.getId();
        Set<Role> roles = roleDao.findByUserId(userId);

        // 获取Permission
        if (roles != null && roles.size() > 0) {
            for (Role role : roles) {
                Integer roleId = role.getId();
                Set<Permission> permissions = permissionDao.findByRoleId(roleId);
                if (permissions != null && permissions.size() > 0) {
                    role.setPermissions(permissions);
                }
            }
            user.setRoles(roles);
        }

        return user;
    }

    /**
     * 用户信息分页查询
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findPageUser(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<User> users = userDao.findPage(queryPageBean.getQueryString());
        long total = users.getTotal();
        List<User> list = users.getResult();
        PageResult pageResult = new PageResult(total, list);
        return pageResult;
    }

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    @Override
    public Result addUser(User user, Integer[] roleIds) {
        if (user != null && !"".equals(user.getPassword())) {
            User users = userDao.findByUsername(user.getUsername());
            if (users != null) {
                return new Result(false, MessageConstant.USER_NAME_FAIL);
            }
            String password = user.getPassword();
            String hashPass = bCryptPasswordEncoder.encode(password);
            user.setPassword(hashPass);
            userDao.addUser(user);
            setUserAndRoleRelation(user.getId(), roleIds);
        }
        return new Result(true, MessageConstant.ADD_MEMBER_SUCCESS);
    }

    /**
     * 编辑用户
     *
     * @param user
     * @param roleIds
     */
    @Override
    public Result edit(User user, Integer[] roleIds) {
        if (user != null) {
            User user1 = userDao.findByUsername(user.getUsername());
            if (user1 != null && !user1.getId().equals(user.getId())) {
                return new Result(false, MessageConstant.USER_NAME_FAIL);
            }
            String password = user.getPassword();
            // 加密
            String hashPass = bCryptPasswordEncoder.encode(password);
            user.setPassword(hashPass);
            userDao.editUser(user);
            // 删除角色关系
            roleDao.deleteRoleUserRelation(user.getId());
            // 设置角色关系
            this.setUserAndRoleRelation(user.getId(), roleIds);
            return new Result(true, MessageConstant.EDIT_USER_OK);
        } else {
            throw new NullPointerException("用户为空");
        }
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashPass = bCryptPasswordEncoder.encode("123456");
        System.out.println(hashPass);
    }
    /**
     * 删除用户
     *
     * @param userId 用户id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer userId) {
        // 删除用户角色关系
        roleDao.deleteRoleUserRelation(userId);
        // 删除用户
        userDao.deleteUser(userId);
    }

    @Override
    public Result login(String username, String password) {
        User user = userDao.findByUsername(username);
        if (user == null) {
            return new Result(false, MessageConstant.LOGIN_FAIL_ERROR);
        }
        String pass = user.getPassword();
        System.out.println("pass = " + pass);
        if (bCryptPasswordEncoder.matches(password, pass)) {
            return new Result(true, MessageConstant.LOGIN_SUCCESS);
        }
        return new Result(false, MessageConstant.LOGIN_FAIL_ERROR);

    }

    /**
     * 设置用户和角色关系
     *
     * @param userId  用户id
     * @param roleIds 用户角色id集合
     */
    public void setUserAndRoleRelation(Integer userId, Integer[] roleIds) {
        if (roleIds != null && roleIds.length > 0) {
            for (Integer roleId : roleIds) {
                roleDao.setUserAndRoleRelation(userId, roleId);
            }
        }
    }

}