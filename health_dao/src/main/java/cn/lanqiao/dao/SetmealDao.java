package cn.lanqiao.dao;

import cn.lanqiao.pojo.Setmeal;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author: Hou
 * @Date: 2021/4/29 13:57
 * @Description:
 */
public interface SetmealDao {
    public void add(Setmeal setmeal);

    public void setSetmealAndCheckGroup(Map map);

    public Page<Setmeal> findByCondition(String queryString);

    public void deleteAssocication(Integer id);

    public void deleteById(Integer id);

    public Setmeal findById(Integer id);

    public List<Integer> findCheckGroupById(Integer id);

    public void update(Setmeal setmeal);

    public List<Setmeal> findAll();

    public List<Map> findSetmealCount();

    List<Map> getSetmealReport(String startTime, String endTime);
}

