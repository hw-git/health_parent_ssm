package cn.lanqiao.service.impl;

import cn.lanqiao.dao.HealthPlanDao;
import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.pojo.Member;
import cn.lanqiao.pojo.Order;
import cn.lanqiao.pojo.Setmeal;
import cn.lanqiao.service.HealthPlanService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class HealthPlanServiceImpl implements HealthPlanService {
    //注入Dao
    @Autowired
    private HealthPlanDao healthPlanDao;

    //分页
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        //使用分页助手
        PageHelper.startPage(currentPage, pageSize);
        Page<Member> members = healthPlanDao.selectByCondition(queryString);
        long total = members.getTotal();
        List<Member> membersList = members.getResult();
        return new PageResult(total, membersList);
    }

    //id查询会员
    @Override
    public Map<String, Object> findMemberById(Integer id) {
        //查询会员基本信息
        Member member = healthPlanDao.findMemberById(id);
        //查询预约信息
        Order order = healthPlanDao.findOrderById(id);
        //查询套餐
        Setmeal setmeal = healthPlanDao.findSetmealById(order.getSetmealId());

        //重新封装返回数据(注意空指针异常)
        Map<String, Object> map = new HashMap<>();
        if (member == null || member.getFileNumber() == null) {
            map.put("fileNumber", "无");
        } else {
            map.put("fileNumber", member.getFileNumber());
        }
        if (member == null || member.getName() == null) {
            map.put("name", "无");
        } else {
            map.put("name", member.getName());
        }
        if (member == null || member.getSex() == null) {
            map.put("sex", "0");
        } else {
            map.put("sex", member.getSex());
        }
        if (order == null || order.getOrderDate() == null) {
            map.put("checkdate", "无");
        } else {
            map.put("checkdate", order.getOrderDate());
        }
        if (setmeal == null || setmeal.getName() == null) {
            map.put("checkitem", "无");
        } else {
            map.put("checkitem", setmeal.getName());
        }
        map.put("id", id);
        return map;
    }

    //干预模板数据回显
    @Override
    public List<Integer> findTemplateIds(Integer id) {
        return healthPlanDao.findHealthPlan(id);
    }

    //新增编辑
    @Override
    public void edit(Integer[] templateIds, Integer id) {
        if (templateIds == null || templateIds.length <= 0) {
            healthPlanDao.deletePlan(id);
            return;
        }
        healthPlanDao.deletePlan(id);
        for (Integer templateId : templateIds) {
            healthPlanDao.addPlan(id, templateId);
        }
    }
}
