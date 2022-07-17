package cn.lanqiao.dao;

import cn.lanqiao.pojo.Menu;

import java.util.List;

/**
 * @Author: Hou
 * @Date: 2021/5/25 10:13
 * @Description:
 */
public interface UserListDao {
    /**
     * 根据用户名查询角色id
     *
     * @param username
     * @return
     */
    public Integer findRoleIdByUsername(String username);

    public List<Menu> findMenuByRoleId(Integer role_id);
}
