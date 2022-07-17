package cn.lanqiao.service.impl;

import cn.lanqiao.dao.*;
import cn.lanqiao.pojo.FoodBase;
import cn.lanqiao.pojo.IntervationTemplate;
import cn.lanqiao.pojo.SportBase;
import cn.lanqiao.service.HealthPlanInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class HealthPlanInfoServiceImpl implements HealthPlanInfoService {
    //注入Dao
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private HealthPlanDao healthPlanDao;
    @Autowired
    private FoodBaseDao foodBaseDao;
    @Autowired
    private SportBaseDao sportBaseDao;
    @Autowired
    private IntervationTemplateDao intervationTemplateDao;

    //生成页面干预内容
    @Override
    public List<IntervationTemplate> findInfoById(Integer id) {
        IntervationTemplate template = null;
        List<IntervationTemplate> intervationTemplates = new ArrayList<>();

        List<Integer> templateIds = healthPlanDao.findHealthPlan(id);
        for (Integer templateId : templateIds) {
            template = intervationTemplateDao.findById(templateId);

            //封装食品
            List<Integer> foodIdByTid = intervationTemplateDao.findFoodIdByTid(templateId);
            List<FoodBase> foodBases = new ArrayList<>();
            for (Integer foodid : foodIdByTid) {
                FoodBase foodBase = foodBaseDao.findFoodById(foodid);
                foodBases.add(foodBase);
            }
            //封装运动
            List<Integer> sportIdByTid = intervationTemplateDao.findSportIdByTid(templateId);
            List<SportBase> sportBases = new ArrayList<>();
            for (Integer sportid : sportIdByTid) {
                SportBase sportBase = sportBaseDao.findSportById(sportid);
                sportBases.add(sportBase);
            }
            template.setFoodBases(foodBases);
            template.setSportBases(sportBases);
            intervationTemplates.add(template);
        }
        return intervationTemplates;
    }
}
