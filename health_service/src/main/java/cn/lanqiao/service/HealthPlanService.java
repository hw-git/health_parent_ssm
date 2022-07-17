package cn.lanqiao.service;

import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;

import java.util.List;
import java.util.Map;

//制定干预方案
public interface HealthPlanService {
    //分页查询
    PageResult pageQuery(QueryPageBean queryPageBean);
    //id查询用户信息
    Map<String,Object> findMemberById(Integer id);
    //干预模板数据回显
    List<Integer> findTemplateIds(Integer id);
    //新增，修改
    void edit(Integer[] templateIds , Integer id);
}
