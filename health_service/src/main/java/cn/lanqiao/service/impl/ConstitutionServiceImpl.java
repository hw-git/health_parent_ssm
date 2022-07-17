package cn.lanqiao.service.impl;

import cn.lanqiao.dao.ConstitutionDao;
import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.pojo.Constitution;
import cn.lanqiao.service.ConstitutionService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ConstitutionServiceImpl implements ConstitutionService{
    @Autowired
    private ConstitutionDao constitutionDao;
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        //使用分页助手
        PageHelper.startPage(currentPage,pageSize);
        Page<Constitution> sportBases = constitutionDao.selectByCondition(queryString);

        //获取查询数据
        long total = sportBases.getTotal();
        List<Constitution> result = sportBases.getResult();

        return new PageResult(total,result);
    }

    @Override
    public void add(Constitution constitution) {
        constitutionDao.add(constitution);
    }

    @Override
    public List<Constitution> findAll(String number) {
        return constitutionDao.select(number);
    }
}
