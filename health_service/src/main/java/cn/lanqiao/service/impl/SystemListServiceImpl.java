package cn.lanqiao.service.impl;

import cn.lanqiao.dao.SystemListDao;
import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.pojo.Menu;
import cn.lanqiao.service.SystemListService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Hou
 * @Date: 2021/5/24 18:39
 * @Description:
 */
@Transactional
@Service
public class SystemListServiceImpl implements SystemListService {

    //注入dao
    @Autowired
    private SystemListDao systemListDao;

    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Page<Menu> menus = null;

        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        //如果没有查询条件
        if (queryString == null || queryString.equals("")) {
            PageHelper.startPage(currentPage, pageSize);
            menus = systemListDao.findByLevel();
            long total = menus.getTotal();
            List<Menu> result = menus.getResult();
            //遍历得到id
            List<Menu> row = new ArrayList<>();
            for (Menu menu : result) {
                //查询子集
                List<Menu> child = systemListDao.findById(menu.getId());
                menu.setChildren(child);
                row.add(menu);
            }
            return new PageResult(total, row);
        } else {
            //如果有查询条件
            PageHelper.startPage(currentPage, pageSize);
            List<Menu> menus1 = systemListDao.findByName(queryString);
            if (menus1.size() != 0) {
                return new PageResult(1L, menus1);
            } else {
                return null;
            }
        }
    }

    /**
     * 查询所有菜单名称（新建回显）
     *
     * @return
     */
    @Override
    public List<Menu> findAllLable() {
        return systemListDao.findAllLable();
    }

    /**
     * 添加新菜单
     *
     * @param pId
     * @param menu
     */
    @Override
    public void add(Integer pId, Menu menu) {
        Menu setMenu = setMenu(pId, menu);
        systemListDao.add(setMenu);
    }

    /**
     * 根据id查询菜单
     *
     * @param id
     * @return
     */
    @Override
    public Menu findMenuById(Integer id) {
        return systemListDao.findMenuById(id);
    }

    /**
     * 编辑菜单
     *
     * @param pId
     * @param menu
     */
    @Override
    public void edit(Integer pId, Menu menu) {
        Menu setMenu = setMenu(pId, menu);
        systemListDao.edit(setMenu);
    }

    /**
     * 删除菜单
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        systemListDao.delete(id);
    }

    /**
     * 查询全部
     *
     * @return
     */
    @Override
    public List<Menu> findAll() {
        return systemListDao.findAll();
    }

    /**
     * 设置Menu相关数据方法
     *
     * @param pId
     * @param menu
     * @return
     */
    public Menu setMenu(Integer pId, Menu menu) {
        //先判断pid,如果pid == 0,说明没有父级菜单
        if (pId == 0) {
            //没有父级菜单，查询path的最大值，设定新菜单的path值为最大值+1,level = 1 ,自关联id ==null
            String pathMax = systemListDao.findPathMax();
            int i = Integer.parseInt(pathMax) + 1;
            String path = String.valueOf(i);
            //menu.setPath(path);
            menu.setLevel(1);
            menu.setParentMenuId(null);
            return menu;
        } else {
            //有父级菜单,根据pid查询父级菜单level，先判断父及菜单的level，设定新菜单level为父级菜单 + 1 ，设定自关联id为pid,查询父级菜单的path和
            //父及菜单包含的子菜单个数
            Menu parent = systemListDao.findParentById(pId);
            Integer parentLevel = parent.getLevel();
            Integer childrenLevel = parentLevel + 1;
            //查询子菜单个数
            int childCount = Math.toIntExact(systemListDao.childCount(pId));
            String childrenPath = "/" + parent.getPath() + "-" + String.valueOf(childCount + 1);
            //menu.setPath(childrenPath);
            menu.setLevel(childrenLevel);
            menu.setParentMenuId(pId);
            return menu;
        }
    }
}
