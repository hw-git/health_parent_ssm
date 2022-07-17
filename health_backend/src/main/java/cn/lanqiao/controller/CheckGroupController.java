package cn.lanqiao.controller;

import cn.lanqiao.constant.MessageConstant;
import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.entity.Result;
import cn.lanqiao.pojo.CheckGroup;
import cn.lanqiao.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Hou
 * @Date: 2021/4/20 11:23
 * @Description:检查组
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @Autowired
    private CheckGroupService checkGroupService;

    //新增检查组
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        try {
            checkGroupService.add(checkGroup, checkitemIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);//新增失败
        }
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);//新增成功
    }

    //分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        System.out.println("调用查询");
        return checkGroupService.pageQuery(queryPageBean);
    }

    //根据ID查询检查组
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            CheckGroup checkGroup = checkGroupService.findById(id);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroup);//查询成功
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);//查询失败
        }
    }

    //根据检查组ID查询检查组包含的多个检查项ID
    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer id) {
        try {
            List<Integer> checkitemIds = checkGroupService.findCheckItemIdsByCheckGroupId(id);
            System.out.println("根据检查组ID查询检查组包含的多个检查项ID");
            for (Integer i : checkitemIds) {
                System.out.println(i);
            }
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkitemIds);//查询成功
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);//查询失败
        }
    }

    //删除检查项
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            checkGroupService.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }

    //编辑检查组
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        try {
            checkGroupService.edit(checkGroup, checkitemIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);//新增失败
        }
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);//新增成功
    }

    @RequestMapping("/findAll")
    public Result findAll() {
        try {
            List<CheckGroup> list = checkGroupService.findAll();
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
}
