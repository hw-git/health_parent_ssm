package cn.lanqiao.service.impl;

import cn.lanqiao.dao.RoleDao;
import cn.lanqiao.pojo.Role;
import cn.lanqiao.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Hou
 * @Date: 2021/5/25 09:04
 * @Description:
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    /**
     * 查询所有角色信息
     *
     * @return
     */
    @Override
    public List<Role> list() {
        return roleDao.list();
    }

    /**
     * 根据userid查询角色信息
     *
     * @param userId
     * @return
     */
    @Override
    public List<Integer> getRoleIdsByUser(Integer userId) {
        return roleDao.getRoleIdsByUser(userId);
    }
}
