package cn.lanqiao.service.impl;

import cn.lanqiao.dao.SystemListDao;
import cn.lanqiao.dao.UserListDao;
import cn.lanqiao.pojo.Menu;
import cn.lanqiao.service.UserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Hou
 * @Date: 2021/5/25 10:14
 * @Description:
 */
@Transactional
@Service
public class UserListServiceImpl implements UserListService {

    //注入Dao
    @Autowired
    private UserListDao userListDao;
    @Autowired
    private SystemListDao systemListDao;


    /**
     * 根据用户名查询对应菜单
     *
     * @param username
     * @return
     */
    @Override
    public List<Menu> findMenuByUsername(String username) {
        //根据用户名查询角色id
        Integer roleId = userListDao.findRoleIdByUsername(username);
        //根据角色id查询定影的父类菜单id
        List<Menu> menus = userListDao.findMenuByRoleId(roleId);
        //获取结果中所有父菜单id
        List<Menu> returnMenu = new ArrayList<>();
        if (menus != null && menus.size() > 0) {
            for (Menu menu : menus) {
                if (menu.getLevel() == 1) {
                    //父级菜单
                    List<Menu> menuList = systemListDao.findById(menu.getId());
                    menu.setChildren(menuList);
                    returnMenu.add(menu);
                }
            }
        }
        //根据父类菜单id查询子类菜单并封装返回
        return returnMenu;
    }
}
