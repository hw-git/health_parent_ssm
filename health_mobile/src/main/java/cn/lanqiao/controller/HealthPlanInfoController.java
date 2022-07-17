package cn.lanqiao.controller;

import cn.lanqiao.entity.Result;
import cn.lanqiao.pojo.FoodBase;
import cn.lanqiao.pojo.IntervationTemplate;
import cn.lanqiao.pojo.SportBase;
import cn.lanqiao.service.HealthPlanInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @Author: Hou
 * @Date: 2021/5/25 15:29
 * @Description:
 */
@RestController
@RequestMapping("/healthPlanInfo")
public class HealthPlanInfoController {
    //注入服务接口
    @Autowired
    private HealthPlanInfoService healthPlanInfoService;

    /**
     * 生成页面干预记录
     *
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findInfoById(Integer id) {
        try {
            List<IntervationTemplate> intervationTemplate = healthPlanInfoService.findInfoById(id);
            Set<SportBase> sportBases = new HashSet<>();
            Set<FoodBase> foodBases = new HashSet<>();
            for (IntervationTemplate intervationTemplate1 : intervationTemplate) {
                for (SportBase sportBase : intervationTemplate1.getSportBases()) {
                    sportBases.add(sportBase);
                }
                for (FoodBase foodBase : intervationTemplate1.getFoodBases()) {
                    foodBases.add(foodBase);
                }
            }
            Map map = new HashMap();
            map.put("sportBase", sportBases);
            map.put("foodBase", foodBases);
            return new Result(true, "健康干预查询成功", map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "健康干预查询失败");
        }

    }
}
