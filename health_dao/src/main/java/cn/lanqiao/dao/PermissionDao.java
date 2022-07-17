package cn.lanqiao.dao;

import cn.lanqiao.pojo.Permission;

import java.util.Set;

/**
 * @Author: Hou
 * @Date: 2021/5/25 09:01
 * @Description:
 */
public interface PermissionDao {
    public Set<Permission> findByRoleId(int roleId);
}
