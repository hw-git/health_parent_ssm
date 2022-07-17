package cn.lanqiao.service;

import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.pojo.IntervationTemplate;

import java.util.List;

public interface IntervationTemplateService {
    //新增干预模板
    void add(IntervationTemplate intervationTemplate, Integer[] foods, Integer[] sports);

    //分页查询
    PageResult pageQuery(QueryPageBean queryPageBean);

    //food回显选中
    List<Integer> findFoodIdByTid(Integer templateid);

    //sport回显选中
    List<Integer> findSportIdByTid(Integer templateid);

    //编辑菜单信息回显
    IntervationTemplate findById(Integer templateid);

    //编辑模板数据提交
    void edit(IntervationTemplate intervationTemplate, Integer[] foodids, Integer[] sportids);

    //修改模板状态
    void change(Integer templateid, Boolean templatestate);

    //删除模板
    void delete(Integer templateid);

    //查询所有模板
    List<IntervationTemplate> findAll();
}
