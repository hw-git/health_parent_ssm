package cn.lanqiao.service;

import cn.lanqiao.pojo.Menu;

import java.util.List;

/**
 * @Author: Hou
 * @Date: 2021/5/25 10:12
 * @Description:菜单动态显示服务接口
 */
public interface UserListService {
    /**
     * 根据用户名回显对应菜单
     *
     * @param username
     * @return
     */
    public List<Menu> findMenuByUsername(String username);
}

