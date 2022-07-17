package cn.lanqiao.dao;

import cn.lanqiao.pojo.Role;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Hou
 * @Date: 2021/5/25 09:53
 * @Description:角色信息Dao
 */
public interface RoleListDao {

    /**
     * 角色信息分页
     *
     * @param queryString
     * @return
     */
    public Page<Role> findByCondition(String queryString);

    /**
     * 添加角色
     *
     * @param role
     */
    public void add(Role role);

    /**
     * 设置角色，资源权限关联
     *
     * @param role_id
     * @param permission_id
     */
    public void setRoleSystemResource(@Param("role_id") Integer role_id, @Param("permission_id") Integer permission_id);

    /**
     * 设置角色，菜单关联
     *
     * @param role_id
     * @param menu_id
     */
    public void setRoleMenu(@Param("role_id") Integer role_id, @Param("menu_id") Integer menu_id);

    /**
     * 根据id拆线呢角色
     *
     * @param id
     * @return
     */
    public Role findById(Integer id);

    /**
     * 根据角色id查询关联的资源权限id
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
     * 删除角色与资源权限之间的关联
     *
     * @param role_id
     */
    public void deleteRoleSystemResource(@Param("role_id") Integer role_id);

    /**
     * 删除角色与菜单的关联
     *
     * @param role_id
     */
    public void deleteRloeMenu(@Param("role_id") Integer role_id);

    /**
     * 修改角色基本信息
     *
     * @param role
     */
    public void edit(Role role);

    public void delete(@Param("id") Integer id);
}
