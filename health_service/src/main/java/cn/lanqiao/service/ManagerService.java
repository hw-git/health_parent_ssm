package cn.lanqiao.service;

import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.pojo.Manager;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 崔云凯
 * @Date: 2021/05/26/11:21
 * @Description
 */
public interface ManagerService {
    public List<Manager> findAll();

    public void edit(Manager manager);

    public void delete(Integer id);

    public void add(Manager manager);

    public PageResult pageQuery(QueryPageBean queryPageBean);

    public Manager findById(Integer id);

    public List<Manager> findByType(Integer type);

}
