package cn.lanqiao.service;

import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.pojo.Role;

import java.util.List;


/**
 * @Author: Hou
 * @Date: 2021/5/25 09:52
 * @Description:角色信息服务接口
 */
public interface RoleListService {

    /**
     * 角色信息分页
     *
     * @param queryPageBean
     * @return
     */
    public PageResult pageQuery(QueryPageBean queryPageBean);

    /**
     * 添加角色
     *
     * @param role
     * @param permissionIds
     * @param menuIds
     */
    public void add(Role role, Integer[] permissionIds, Integer[] menuIds);

    /**
     * 根据id修改角色数据回显
     *
     * @param id
     * @return
     */
    public Role findById(Integer id);

    /**
     * 根据角色id，查询资源权限已关联id
     *
     * @param id
     * @return
     */
    public List<Integer> findPermissionIds(Integer id);

    /**
     * 根据角色id查询关联的菜单id
     *
     * @param id
     * @return
     */
    public List<Integer> findMenuIds(Integer id);

    /**
     * 修改角色信息
     *
     * @param role
     * @param permissionIds
     * @param menuIds
     */
    public void edit(Role role, Integer[] permissionIds, Integer[] menuIds);

    /**
     * 删除角色信息
     *
     * @param id
     */
    public void delete(Integer id);
}
