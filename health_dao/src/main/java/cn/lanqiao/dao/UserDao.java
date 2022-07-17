package cn.lanqiao.dao;

import cn.lanqiao.pojo.User;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: Hou
 * @Date: 2021/5/13 09:52
 * @Description:
 */
public interface UserDao {
    public User findByUsername(@Param("username") String username);

    /**
     * 分页查询用户
     *
     * @param queryString
     * @return
     */
    Page<User> findPage(@Param("queryString") String queryString);

    void addUser(User user);

    /**
     * 编辑用户信息
     *
     * @param user
     */
    void editUser(User user);

    /**
     * 删除用户
     *
     * @param userId
     */
    void deleteUser(Integer userId);
}
