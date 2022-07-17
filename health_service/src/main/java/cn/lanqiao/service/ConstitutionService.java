package cn.lanqiao.service;

import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.pojo.Constitution;


import java.util.List;

public interface ConstitutionService {
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
    public void add(Constitution constitution);
    /**
     * 查询所有
     */
    public List<Constitution> findAll(String number);
}
