package cn.lanqiao.service;

import cn.lanqiao.pojo.IntervationTemplate;

import java.util.List;

public interface HealthPlanInfoService {
    //生成页面干预内容
    List<IntervationTemplate> findInfoById(Integer id);
}
