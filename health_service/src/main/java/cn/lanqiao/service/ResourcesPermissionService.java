package cn.lanqiao.service;

import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.pojo.Permission;

import java.util.List;

/**
 * @Author: Hou
 * @Date: 2021/5/24 18:55
 * @Description:资源权限管理服务接口
 */
public interface ResourcesPermissionService {

    /**
     * 资源权限分页
     *
     * @param queryPageBean
     * @return
     */
    public PageResult pageQuery(QueryPageBean queryPageBean) throws Exception;

    /**
     * 编辑表单回显
     *
     * @param integer
     * @return
     */
    public Permission findById(Integer integer);

    /**
     * 编辑提交表单
     *
     * @param permission
     * @return
     */
    public void edit(Permission permission) throws Exception;

    /**
     * 新增资源权限
     *
     * @param permission
     */
    public void add(Permission permission) throws Exception;

    /**
     * 删除资源权限
     *
     * @param id
     */
    public void delete(Integer id);

    /**
     * 切换权限状态
     *
     * @param id
     */
    public void change(Integer id);

    /**
     * 角色管理资源权限信息回显
     *
     * @return
     */
    public List<Permission> findAll();
}
