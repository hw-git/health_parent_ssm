package cn.lanqiao.service.impl;

import cn.lanqiao.dao.ManagerDao;
import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.pojo.Manager;
import cn.lanqiao.service.ManagerService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 崔云凯
 * @Date: 2021/05/26/11:23
 * @Description
 */
@Transactional
@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    private ManagerDao managerDao;

    @Override
    public List<Manager> findAll() {
        return managerDao.findAll();
    }

    @Override
    public void edit(Manager manager) {
        managerDao.edit(manager);
    }

    @Override
    public void delete(Integer id) {
        managerDao.delete(id);
    }

    @Override
    public void add(Manager manager) {
        managerDao.add(manager);
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        System.out.println("queryString = " + queryString);
        //分页
        PageHelper.startPage(currentPage, pageSize);
        Page<Manager> questionPage = managerDao.findManagerByCondition(queryString);
        long total = questionPage.getTotal();
        List<Manager> row = questionPage.getResult();
        return new PageResult(total, row);
    }


    @Override
    public Manager findById(Integer id) {
        return managerDao.findById(id);
    }

    @Override
    public List<Manager> findByType(Integer type) {
        return managerDao.findByType(type);
    }

}
