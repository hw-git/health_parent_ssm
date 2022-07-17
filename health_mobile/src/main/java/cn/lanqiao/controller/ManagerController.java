package cn.lanqiao.controller;

import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.entity.Result;
import cn.lanqiao.pojo.Manager;
import cn.lanqiao.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 崔云凯
 * @Date: 2021/05/26/11:27
 * @Description
 */

@RestController
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    private ManagerService managerService;

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = managerService.pageQuery(queryPageBean);
        return pageResult;
    }

    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            Manager manager = managerService.findById(id);
            return new Result(true, "查询成功", manager);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, "查询失败");
        }
    }

    @RequestMapping("/findAll")
    public Result findAll() {
        try {
            List<Manager> list = managerService.findAll();
            return new Result(true, "查询成功", list);
        } catch (Exception e) {
            e.printStackTrace();
            //服务调用失败
            return new Result(false, "查询失败");
        }
    }

    @RequestMapping("/findByType")
    public Result findByType(Integer type) {
        try {
            List<Manager> list = managerService.findByType(type);
            return new Result(true, "查询成功", list);
        } catch (Exception e) {
            e.printStackTrace();
            //服务调用失败
            return new Result(false, "查询失败");
        }
    }

}
