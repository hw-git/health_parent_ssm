package cn.lanqiao.dao;

import cn.lanqiao.pojo.Permission;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @Author: Hou
 * @Date: 2021/5/24 18:56
 * @Description:资源权限Dao
 */
public interface ResourcesPermissionDao {

    /**
     * 条件查询
     *
     * @param queryString
     * @return
     */
    public Page<Permission> selectByCondition(String queryString);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    public Permission findById(Integer id);

    /**
     * 修改表单数据
     *
     * @param permission
     */
    public void updateResources(Permission permission);

    /**
     * 新增表单数据
     *
     * @param permission
     */
    public void add(Permission permission);

    /**
     * 删除资源权限
     *
     * @param id
     */
    public void delete(Integer id);

    /**
     * 切换权限状态
     *
     * @param permission
     */
    public void change(Permission permission);

    /**
     * 查询所有未禁用资源权限信息
     *
     * @return
     */
    public List<Permission> findAll();
}
