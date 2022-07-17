package cn.lanqiao.service;

import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.pojo.Menu;

import java.util.List;

/**
 * @Author: Hou
 * @Date: 2021/5/24 18:38
 * @Description:权限管理菜单管理
 */
public interface SystemListService {
    /**
     * 分页
     *
     * @param queryPageBean
     * @return
     */
    public PageResult pageQuery(QueryPageBean queryPageBean);

    /**
     * 查找下拉菜单内容
     *
     * @return
     */
    public List<Menu> findAllLable();

    /**
     * 添加新菜单
     *
     * @param pId
     * @param menu
     */
    public void add(Integer pId, Menu menu);

    /**
     * 根据id查询Menu基本信息
     *
     * @param id
     * @return
     */
    public Menu findMenuById(Integer id);

    /**
     * 编辑菜单
     *
     * @param pId
     * @param menu
     */
    public void edit(Integer pId, Menu menu);

    /**
     * 删除菜单
     *
     * @param id
     */
    public void delete(Integer id);

    /**
     * 角色管理菜单信息回显
     *
     * @return
     */
    public List<Menu> findAll();

}