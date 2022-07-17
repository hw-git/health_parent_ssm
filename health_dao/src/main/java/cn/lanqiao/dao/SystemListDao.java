package cn.lanqiao.dao;

import cn.lanqiao.pojo.Menu;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @Author: Hou
 * @Date: 2021/5/24 18:41
 * @Description:
 */
public interface SystemListDao {
    /**
     * 条件查询(只查询level = 1的数据)
     *
     * @return
     */
    public Page<Menu> findByLevel();

    /**
     * 根据自关联id查询子菜单
     *
     * @param uname
     * @return
     */
    public Page<Menu> findByName(String uname);

    /**
     * 自关联查询
     *
     * @param pid
     * @return
     */
    public List<Menu> findById(Integer pid);

    /**
     * 查询所有菜单名称
     *
     * @return
     */
    public List<Menu> findAllLable();

    /**
     * 查询path最大值
     *
     * @return
     */
    public String findPathMax();

    /**
     * 添加菜单
     *
     * @param menu
     */
    public void add(Menu menu);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    public Menu findParentById(Integer id);

    /**
     * 根据自关联id查询总数
     *
     * @param parentMenuId
     * @return
     */
    public Long childCount(Integer parentMenuId);

    /**
     * 根据id查询菜单
     *
     * @param id
     * @return
     */
    public Menu findMenuById(Integer id);

    /**
     * 修改菜单数据
     *
     * @param menu
     */
    public void edit(Menu menu);

    /**
     * 删除菜单
     *
     * @param id
     */
    public void delete(Integer id);

    /**
     * 查询全部
     *
     * @return
     */
    public List<Menu> findAll();
}
