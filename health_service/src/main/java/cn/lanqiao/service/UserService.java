package cn.lanqiao.service;

import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.entity.Result;
import cn.lanqiao.pojo.User;

import java.io.UnsupportedEncodingException;

/**
 * @Author: Hou
 * @Date: 2021/5/13 10:00
 * @Description:用户服务接口
 */
public interface UserService {
    public User findByUsername(String username) throws UnsupportedEncodingException;

    /**
     * 用户信息分页查询
     *
     * @param queryPageBean
     * @return
     */
    PageResult findPageUser(QueryPageBean queryPageBean);

    /**
     * 新增用户
     *
     * @param user 用户信息
     * @return
     */
    Result addUser(User user, Integer[] roleIds);

    /**
     * 编辑用户
     *
     * @param user    用户信息
     * @param roleIds 角色id集合
     */
    Result edit(User user, Integer[] roleIds);

    /**
     * 删除用户
     *
     * @param userId 用户id
     */
    void delete(Integer userId);

    Result login(String username, String password);
}
