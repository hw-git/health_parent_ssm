package cn.lanqiao.service;

import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.pojo.Risk;


import java.util.List;


public interface RiskService {
    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */

    public PageResult pageQuery(QueryPageBean queryPageBean);
    /**
     * 新增
     * @param
     */
    public void add(Risk risk);
    /**
     * 查询所有
     */
    public List<Risk> findAll(String number);
}
