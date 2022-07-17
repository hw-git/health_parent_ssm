package cn.lanqiao.controller;

import cn.lanqiao.constant.MessageConstant;
import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.entity.Result;
import cn.lanqiao.pojo.FoodBase;
import cn.lanqiao.service.FoodBaseService;
import cn.lanqiao.utils.POIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Hou
 * @Date: 2021/5/14 17:55
 * @Description:食品库
 */
@RestController
@RequestMapping("/foodBaseController")
public class FoodBaseController {

    //注入服务接口
    @Autowired
    private FoodBaseService foodBaseService;

    /**
     * 食品知识库poi导入
     *
     * @param file
     * @return
     */
    @RequestMapping("/foodupload")
    public Result upload(@RequestParam("excelFile") MultipartFile file) {
        try {
            //获取上传数据并封装
            List<String[]> list = POIUtils.readExcel(file);
            List<FoodBase> foodBaseList = new ArrayList<>();
            for (String[] strings : list) {
                System.out.println(strings[0]);
                System.out.println(strings[1]);
                System.out.println(strings[2]);
                System.out.println(strings[3]);
                String foodcode = strings[0];
                String foodname = strings[1];
                String foodtype = strings[2];
                String foodremark = strings[3];
                FoodBase foodBase = new FoodBase(foodcode, foodname, foodtype, foodremark);
                foodBaseList.add(foodBase);
            }
            //调用服务接口
            foodBaseService.importData(foodBaseList);
            return new Result(true, MessageConstant.UPLOAD_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.UPLOAD_FAIL);
        }
    }

    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = foodBaseService.pageQuery(queryPageBean);
            return new Result(true, MessageConstant.QUERY_FOOD_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_FOOD_FAIL);
        }
    }


    /**
     * 编辑回显数据
     *
     * @param foodid
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer foodid) {
        try {
            FoodBase foodBase = foodBaseService.findFoodById(foodid);
            return new Result(true, MessageConstant.QUERY_FOOD_SUCCESS, foodBase);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_FOOD_FAIL);
        }
    }

    /**
     * 编辑food
     *
     * @param foodBase
     * @return
     */
    @RequestMapping("/edit")
    public Result edit(@RequestBody FoodBase foodBase) {
        try {
            foodBaseService.edit(foodBase);
            return new Result(true, MessageConstant.EDIT_FOOD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_FOOD_FAIL);
        }
    }

    /**
     * 新增add
     *
     * @param foodBase
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody FoodBase foodBase) {
        try {
            foodBaseService.add(foodBase);
            return new Result(true, MessageConstant.ADD_FOOD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_FOOD_FAIL);
        }
    }

    /**
     * 根据id删除
     *
     * @param foodid
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Integer foodid) {
        try {
            foodBaseService.deleteById(foodid);
            return new Result(true, MessageConstant.DELETE_FOOD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_FOOD_FAIL);
        }
    }

    /**
     * 查询所有
     *
     * @return
     */
    @RequestMapping("/findAll")
    public Result findAll() {
        try {
            List<FoodBase> foodBases = foodBaseService.findAll();
            return new Result(true, MessageConstant.QUERY_FOOD_SUCCESS, foodBases);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_FOOD_FAIL);
        }
    }
}
