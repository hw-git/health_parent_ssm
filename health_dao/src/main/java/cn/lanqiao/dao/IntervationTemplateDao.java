package cn.lanqiao.dao;

import cn.lanqiao.pojo.IntervationTemplate;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

//干预模板
public interface IntervationTemplateDao {
    //新增模板
    void add(IntervationTemplate intervationTemplate);

    //模板与食品关联
    void setTemplateAndFood(Map<String, Integer> map);

    //模板与运动关联
    void setTemplateAndSport(Map<String, Integer> map);

    //条件查询
    Page<IntervationTemplate> selectByCondition(String queryString);

    //id查询食品
    List<Integer> findFoodIdByTid(Integer templateid);

    //id查询运动
    List<Integer> findSportIdByTid(Integer templateid);

    //id查询模板数据
    IntervationTemplate findById(Integer templateid);

    //删除模板与食品的关联
    void deleteTemplateFood(Integer foodid);

    void deleteTemplateFoodByTID(Integer templateid);

    //删除模板与运动的关联
    void deleteTemplateSport(Integer sportid);

    void deleteTemplateSportByTID(Integer templateid);

    //删除模板与会员id关联
    void deleteTemplateHealthPlanByTID(Integer templateid);

    //修改基本数据
    void edit(IntervationTemplate intervationTemplate);

    //修改模板状态
    void change(IntervationTemplate interventionTemplate);

    //删除模板
    void delete(Integer templateid);

    //查询所有模板
    List<IntervationTemplate> findAll();
}
