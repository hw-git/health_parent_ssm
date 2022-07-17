package cn.lanqiao.service;

import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @Author: Hou
 * @Date: 2021/4/29 13:56
 * @Description:
 */
public interface SetmealService {
    public void add(Setmeal setmeal, Integer[] checkgroupIds);

    public PageResult pageQuery(QueryPageBean queryPageBean);

    public Setmeal findById(Integer id);

    public List<Integer> findCheckGroupById(Integer id);

    public void deleteById(Integer id);

    public void update(Setmeal setmeal, Integer[] checkgroupIds);

    public List<Setmeal> findAll();

    List<Map> getSetmealReport(String startTime, String endTime);

    public List<Map> findSetmealCount();
}
