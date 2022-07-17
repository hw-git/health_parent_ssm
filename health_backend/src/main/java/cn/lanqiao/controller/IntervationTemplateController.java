package cn.lanqiao.controller;

import cn.lanqiao.constant.MessageConstant;
import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.entity.Result;
import cn.lanqiao.pojo.IntervationTemplate;
import cn.lanqiao.service.IntervationTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Hou
 * @Date: 2021/5/14 17:55
 * @Description:干预模板
 */
@RestController
@RequestMapping("/interventionTemplateController")
//干预模板控制
public class IntervationTemplateController {
    //注入模板服务接口
    @Autowired
    private IntervationTemplateService intervationTemplateService;

    //新增干预模板
    @RequestMapping("/add")
    public Result add(@RequestBody IntervationTemplate intervationTemplate, Integer[] foods, Integer[] sports) {
        try {
            intervationTemplateService.add(intervationTemplate, foods, sports);
            return new Result(true, "添加模板成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加模板失败");
        }
    }

    //分页查询
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = intervationTemplateService.pageQuery(queryPageBean);
            return new Result(true, "查询模板成功", pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "查询模板失败");
        }
    }

    //food回显选中
    @RequestMapping("/findFoodIdByTid")
    public Result findFoodIdByTid(Integer templateid) {
        try {
            List<Integer> foodIds = intervationTemplateService.findFoodIdByTid(templateid);
            System.out.println("foodids" + foodIds);
            return new Result(true, MessageConstant.QUERY_FOOD_SUCCESS, foodIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_FOOD_FAIL);
        }
    }

    //sport回显选中
    @RequestMapping("/findSportIdByTid")
    public Result findSportIdByTid(Integer templateid) {
        try {
            List<Integer> sportIds = intervationTemplateService.findSportIdByTid(templateid);
            System.out.println("sportids" + sportIds);
            return new Result(true, MessageConstant.QUERY_SPORT_SUCCESS, sportIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SPORT_FAIL);
        }
    }

    //基本数据回显
    @RequestMapping("/findById")
    public Result findById(Integer templateid) {
        try {
            IntervationTemplate intervationTemplate = intervationTemplateService.findById(templateid);
            System.out.println(intervationTemplate);
            return new Result(true, "查询模板成功", intervationTemplate);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "查询模板失败");
        }
    }

    //模板数据提交
    @RequestMapping("/edit")
    public Result edit(@RequestBody IntervationTemplate intervationTemplate, Integer[] foods, Integer[] sports) {
        try {
            intervationTemplateService.edit(intervationTemplate, foods, sports);
            return new Result(true, "修改模板成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, "修改模板失败");
        }
    }

    //修改模板状态
    @RequestMapping("/change")
    public Result change(Integer templateid, Boolean templatestate) {
        try {
            intervationTemplateService.change(templateid, templatestate);
            return new Result(true, "修改模板成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改模板失败");
        }
    }

    //删除模板
    @RequestMapping("/delete")
    public Result delete(Integer templateid) {
        try {
            intervationTemplateService.delete(templateid);
            return new Result(true, "删除模板成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除模板失败");
        }
    }

    //查询所有模板
    @RequestMapping("/findAll")
    public Result findAll() {
        try {
            List<IntervationTemplate> list = intervationTemplateService.findAll();
            return new Result(true, "查询模板成功", list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "查询模板失败");
        }
    }
}
