package cn.lanqiao.service;

import cn.lanqiao.pojo.Role;

import java.util.List;

/**
 * @Author: Hou
 * @Date: 2021/5/25 09:03
 * @Description:角色服务
 */
public interface RoleService {
    /**
     * 查询所有角色信息
     *
     * @return
     */
    List<Role> list();

    /**
     * 根据userid查询角色信息
     *
     * @param userId
     * @return
     */
    List<Integer> getRoleIdsByUser(Integer userId);
}

