package cn.lanqiao.dao;

import cn.lanqiao.pojo.Manager;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 崔云凯
 * @Date: 2021/05/26/11:13
 * @Description
 */
public interface ManagerDao {
    public List<Manager> findAll();

    public void edit(Manager manager);

    public void delete(Integer id);

    public void add(Manager manager);

    public Page<Manager> findManagerByCondition(String queryString);

    public Manager findById(Integer id);

    public List<Manager> findByType(Integer type);

}
