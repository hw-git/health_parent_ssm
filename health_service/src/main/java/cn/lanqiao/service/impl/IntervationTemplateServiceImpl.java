package cn.lanqiao.service.impl;

import cn.lanqiao.dao.IntervationTemplateDao;
import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.pojo.IntervationTemplate;
import cn.lanqiao.service.IntervationTemplateService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//干预模板
@Transactional
@Service
public class IntervationTemplateServiceImpl implements IntervationTemplateService {
    //注入Dao
    @Autowired
    private IntervationTemplateDao intervationTemplateDao;

    //新增干预模板
    @Override
    public void add(IntervationTemplate intervationTemplate, Integer[] foods, Integer[] sports) {
        intervationTemplateDao.add(intervationTemplate);
        //设置关联
        setTemplateAndFood(intervationTemplate.getTemplateid(), foods);
        setTemplateAndSport(intervationTemplate.getTemplateid(), sports);
    }

    //分页查询
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        //分页助手
        PageHelper.startPage(currentPage, pageSize);
        Page<IntervationTemplate> intervationTemplates = intervationTemplateDao.selectByCondition(queryString);
        Long total = intervationTemplates.getTotal();
        List<IntervationTemplate> templateList = intervationTemplates.getResult();
        return new PageResult(total, templateList);
    }

    //food回显选中
    @Override
    public List<Integer> findFoodIdByTid(Integer templateid) {
        return intervationTemplateDao.findFoodIdByTid(templateid);
    }

    //sport回显选中
    @Override
    public List<Integer> findSportIdByTid(Integer templateid) {
        return intervationTemplateDao.findSportIdByTid(templateid);
    }

    //编辑菜单信息回显
    @Override
    public IntervationTemplate findById(Integer templateid) {
        return intervationTemplateDao.findById(templateid);
    }

    //编辑模板信息提交
    @Override
    public void edit(IntervationTemplate intervationTemplate, Integer[] foodids, Integer[] sportids) {
        //判断是否关联
        if (foodids != null && foodids.length > 0) {
            intervationTemplateDao.deleteTemplateFoodByTID(intervationTemplate.getTemplateid());
        }
        if (sportids != null && sportids.length > 0) {
            intervationTemplateDao.deleteTemplateSportByTID(intervationTemplate.getTemplateid());
        }
        //修改基本数据
        intervationTemplateDao.edit(intervationTemplate);
        //重新设置关联
        setTemplateAndFood(intervationTemplate.getTemplateid(), foodids);
        setTemplateAndSport(intervationTemplate.getTemplateid(), sportids);
    }

    //修改模板状态
    @Override
    public void change(Integer templateid, Boolean templatestate) {
        IntervationTemplate intervationTemplate = new IntervationTemplate();
        intervationTemplate.setTemplateid(templateid);
        if (templatestate) {
            intervationTemplate.setTemplatestate(false);
            intervationTemplateDao.change(intervationTemplate);
        } else {
            intervationTemplate.setTemplatestate(true);
            intervationTemplateDao.change(intervationTemplate);
        }
    }

    //删除模板
    @Override
    public void delete(Integer templateid) {
        //删除关联
        intervationTemplateDao.deleteTemplateFoodByTID(templateid);
        intervationTemplateDao.deleteTemplateSportByTID(templateid);
        intervationTemplateDao.deleteTemplateHealthPlanByTID(templateid);
        //删除模板
        intervationTemplateDao.delete(templateid);
    }

    //查询所有
    @Override
    public List<IntervationTemplate> findAll() {
        return intervationTemplateDao.findAll();
    }

    //设置预约模板和食品的关联关系
    public void setTemplateAndFood(Integer TemplateId, Integer[] foodids) {
        if (foodids != null && foodids.length > 0) {
            for (Integer foodid : foodids) {
                Map<String, Integer> map = new HashMap<>();
                map.put("template_id", TemplateId);
                map.put("food_id", foodid);
                intervationTemplateDao.setTemplateAndFood(map);
            }
        }
    }

    //设置预约模板和食品的关联关系
    public void setTemplateAndSport(Integer TemplateId, Integer[] sportids) {
        if (sportids != null && sportids.length > 0) {
            for (Integer sportid : sportids) {
                Map<String, Integer> map = new HashMap<>();
                map.put("template_id", TemplateId);
                map.put("sport_id", sportid);
                intervationTemplateDao.setTemplateAndSport(map);
            }
        }
    }
}
