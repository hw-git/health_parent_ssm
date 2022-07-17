package cn.lanqiao.dao;

import cn.lanqiao.pojo.Member;
import cn.lanqiao.pojo.Order;
import cn.lanqiao.pojo.Setmeal;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//健康干预
public interface HealthPlanDao {
    //f分页
    Page<Member> selectByCondition(String queryString);

    //id查询会员
    Member findMemberById(Integer id);

    //id查询检查
    Order findOrderById(Integer id);

    //id查询套餐
    Setmeal findSetmealById(Integer id);


    //id 查询关联干预模板id
    List<Integer> findHealthPlan(Integer id);

    //删除干预记录
    void deletePlan(Integer member_id);

    //新增干预记录
    void addPlan(@Param("member_id") Integer member_id, @Param("template_id") Integer template_id);
}
