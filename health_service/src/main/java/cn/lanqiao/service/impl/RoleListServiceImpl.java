package cn.lanqiao.service.impl;

import cn.lanqiao.dao.RoleListDao;
import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.pojo.Role;
import cn.lanqiao.service.RoleListService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Hou
 * @Date: 2021/5/25 09:52
 * @Description:
 */
@Transactional
@Service
public class RoleListServiceImpl implements RoleListService {
    //注入Dao
    @Autowired
    private RoleListDao roleListDao;

    /**
     * 角色信息分页
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        //分页
        PageHelper.startPage(currentPage, pageSize);
        Page<Role> rolePage = roleListDao.findByCondition(queryString);
        long total = rolePage.getTotal();
        List<Role> result = rolePage.getResult();
        return new PageResult(total, result);
    }

    /**
     * 添加角色
     *
     * @param role
     * @param permissionIds
     * @param menuIds
     */
    @Override
    public void add(Role role, Integer[] permissionIds, Integer[] menuIds) {
        //添加基本信息
        roleListDao.add(role);
        //设置关联关系
        setRoleMenuSystemResource(role.getId(), permissionIds, menuIds);
    }

    /**
     * 根据id查询角色
     *
     * @param id
     * @return
     */
    @Override
    public Role findById(Integer id) {
        return roleListDao.findById(id);
    }

    /**
     * 根据角色id查询关联的资源权限id
     *
     * @param id
     * @return
     */
    @Override
    public List<Integer> findPermissionIds(Integer id) {
        return roleListDao.findPermissionIds(id);
    }

    /**
     * 根据角色id查询关联的菜单id
     *
     * @param id
     * @return
     */
    @Override
    public List<Integer> findMenuIds(Integer id) {
        return roleListDao.findMenuIds(id);
    }

    /**
     * 修改角色信息
     *
     * @param role
     * @param permissionIds
     * @param menuIds
     */
    @Override
    public void edit(Role role, Integer[] permissionIds, Integer[] menuIds) {
        //删除角色与菜单、资源权限之间的关联
        deleteRoleMenuSystemResource(role.getId());
        //修改基本信息
        roleListDao.edit(role);
        //设置关联关系
        setRoleMenuSystemResource(role.getId(), permissionIds, menuIds);
    }

    /**
     * 删除角色
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        //删除关联关系
        deleteRoleMenuSystemResource(id);
        //删除角色
        roleListDao.delete(id);
    }

    /**
     * 设置角色，资源权限，菜单关联
     *
     * @param rId
     * @param permissionIds
     * @param menuIds
     */
    public void setRoleMenuSystemResource(Integer rId, Integer[] permissionIds, Integer[] menuIds) {
        if (permissionIds != null && permissionIds.length > 0) {
            //设置角色，资源权限关联
            for (Integer permissionId : permissionIds) {
                roleListDao.setRoleSystemResource(rId, permissionId);
            }
        }
        if (menuIds != null && menuIds.length > 0) {
            //设置角色，菜单关联
            for (Integer menuId : menuIds) {
                roleListDao.setRoleMenu(rId, menuId);
            }
        }
    }

    /**
     * 删除角色与菜单、资源权限之间的关联
     *
     * @param id
     */
    public void deleteRoleMenuSystemResource(Integer id) {
        roleListDao.deleteRoleSystemResource(id);
        roleListDao.deleteRloeMenu(id);
    }
}
