package cn.lanqiao.service;

import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.pojo.CheckGroup;
import cn.lanqiao.pojo.CheckItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Hou
 * @Date: 2021/4/20 11:24
 * @Description:
 */
   @Transactional
public interface CheckGroupService {
    public void add(CheckGroup checkGroup, Integer[] checkitemIds);
    public PageResult pageQuery(QueryPageBean queryPageBean);
    public CheckGroup findById(Integer id);
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id);
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds);
    public void deleteById(Integer id);
    public List<CheckGroup> findAll();
}