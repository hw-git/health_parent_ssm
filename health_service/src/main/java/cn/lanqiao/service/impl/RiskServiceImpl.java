package cn.lanqiao.service.impl;

import cn.lanqiao.dao.RiskDao;
import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.pojo.Risk;
import cn.lanqiao.service.RiskService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Service
public class RiskServiceImpl implements RiskService {
    @Autowired
    private RiskDao riskDao;
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        //使用分页助手
        PageHelper.startPage(currentPage,pageSize);
        Page<Risk> risks = riskDao.selectByCondition(queryString);

        //获取查询数据
        long total = risks.getTotal();
        List<Risk> result = risks.getResult();

        return new PageResult(total,result);
    }

    @Override
    public void add(Risk risk) {
        riskDao.add(risk);
    }

    @Override
    public List<Risk> findAll(String number) {
        return riskDao.select(number);
    }
}
