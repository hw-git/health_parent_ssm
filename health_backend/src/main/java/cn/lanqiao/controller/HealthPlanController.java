package cn.lanqiao.controller;

import cn.lanqiao.constant.MessageConstant;
import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.entity.Result;
import cn.lanqiao.service.HealthPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author: Hou
 * @Date: 2021/5/16 18:21
 * @Description:健康干预
 */
@RestController
@RequestMapping("/healthPlanController")
public class HealthPlanController {
    //注入接口
    @Autowired
    private HealthPlanService healthPlanService;

    //分页
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = healthPlanService.pageQuery(queryPageBean);
            return new Result(true, MessageConstant.QUERY_PERSON_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_PERSON_FAIL);
        }
    }

    //查询基本信息
    @RequestMapping("/findMemberById")
    public Result findMemberById(Integer id) {
        try {
            Map<String, Object> map = healthPlanService.findMemberById(id);
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }

    //干预模板数据回显
    @RequestMapping("/findTemplateIds")
    public Result findTemplateIds(Integer id) {
        try {
            List<Integer> templateIds = healthPlanService.findTemplateIds(id);
            return new Result(true, "查询模板成功", templateIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "查询模板失败");
        }
    }

    //新增、编辑干预
    @RequestMapping("/edit")
    public Result edit(Integer[] templateIds, Integer id) {
        try {
            healthPlanService.edit(templateIds, id);
            return new Result(true, MessageConstant.EDIT_PLAN_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_PLAN_FAIL);
        }
    }
}
