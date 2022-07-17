package cn.lanqiao.service;

import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.pojo.Psychology;

import java.util.List;

public interface PsychologyService {
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
    public void add(Psychology psychology);
    /**
     * 查询所有
     */
    public List<Psychology> findAll(String number);
}
