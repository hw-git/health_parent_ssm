package cn.lanqiao.service.impl;

import cn.lanqiao.dao.PsychologyDao;
import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.pojo.Psychology;
import cn.lanqiao.service.PsychologyService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Service
public class PsychologyServiceImpl implements PsychologyService {
    @Autowired
    private PsychologyDao psychologyDao;
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        //使用分页助手
        PageHelper.startPage(currentPage,pageSize);
        Page<Psychology> psychologies = psychologyDao.selectByCondition(queryString);

        //获取查询数据
        long total = psychologies.getTotal();
        List<Psychology> result = psychologies.getResult();

        return new PageResult(total,result);
    }

    @Override
    public void add(Psychology psychology) {
        psychologyDao.add(psychology);
    }

    @Override
    public List<Psychology> findAll(String number) {
        return psychologyDao.select(number);
    }
}
