package cn.lanqiao.service;

import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.pojo.CheckItem;

import java.util.List;

/**
 * @Author: Hou
 * @Date: 2021/4/18 15:47
 * @Description:
 */
public interface CheckItemService {
    public void add(CheckItem checkItem);
    public PageResult pageQuery(QueryPageBean queryPageBean);
    public void deleteById(Integer id);
    public void edit(CheckItem checkItem);
    public CheckItem findById(Integer id);
    public List<CheckItem> findAll();
}
