package cn.lanqiao.dao;

import cn.lanqiao.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @Author: Hou
 * @Date: 2021/5/25 08:59
 * @Description:
 */
public interface RoleDao {
    public Set<Role> findByUserId(int id);

    /**
     * 查询所有角色信息
     *
     * @return
     */
    List<Role> list();

    /**
     * 新增用户和角色关系
     *
     * @param userId 用户id
     * @param roleId 角色id
     */
    void setUserAndRoleRelation(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    /**
     * 删除用户角色关系
     *
     * @param id 用户id
     */
    void deleteRoleUserRelation(Integer id);

    /**
     * 查询用户角色信息
     *
     * @param userId
     * @return
     */
    List<Integer> getRoleIdsByUser(Integer userId);
}
