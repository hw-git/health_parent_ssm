package cn.lanqiao.controller;

import cn.lanqiao.constant.MessageConstant;
import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.entity.Result;
import cn.lanqiao.pojo.SportBase;
import cn.lanqiao.service.SportBaseService;
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
 * @Description:运动知识库管理
 */
@RestController
@RequestMapping("/sportBaseController")
public class SportBaseController {

    //注入服务接口
    @Autowired
    private SportBaseService sportBaseService;

    /**
     * 食品知识库poi导入
     *
     * @param file
     * @return
     */
    @RequestMapping("/sportupload")
    public Result upload(@RequestParam("excelFile") MultipartFile file) {
        try {
            //获取上传数据并封装
            List<String[]> list = POIUtils.readExcel(file);
            List<SportBase> sportBaseList = new ArrayList<>();
            for (String[] strings : list) {
                String sportcode = strings[0];
                String sportname = strings[1];
                String sporttype = strings[2];
                String sportremark = strings[3];
                SportBase sportBase = new SportBase(sportcode, sportname, sporttype, sportremark);
                sportBaseList.add(sportBase);
            }
            //调用服务接口
            sportBaseService.importData(sportBaseList);
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
            PageResult pageResult = sportBaseService.pageQuery(queryPageBean);
            return new Result(true, MessageConstant.QUERY_SPORT_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SPORT_FAIL);
        }
    }


    /**
     * 编辑回显数据
     *
     * @param sportid
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer sportid) {
        try {
            SportBase sportBase = sportBaseService.findSportById(sportid);
            return new Result(true, MessageConstant.QUERY_SPORT_SUCCESS, sportBase);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SPORT_FAIL);
        }
    }

    /**
     * 编辑food
     *
     * @param sportBase
     * @return
     */
    @RequestMapping("/edit")
    public Result edit(@RequestBody SportBase sportBase) {
        try {
            sportBaseService.edit(sportBase);
            return new Result(true, MessageConstant.DELETE_SPORT_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_SPORT_FAIL);
        }
    }

    /**
     * 新增add
     *
     * @param sportBase
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody SportBase sportBase) {
        try {
            sportBaseService.add(sportBase);
            return new Result(true, MessageConstant.ADD_SPORT_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SPORT_FAIL);
        }
    }

    /**
     * 根据id删除
     *
     * @param sportid
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Integer sportid) {
        try {
            sportBaseService.deleteById(sportid);
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
            List<SportBase> sportBases = sportBaseService.findAll();
            return new Result(true, MessageConstant.QUERY_SPORT_SUCCESS, sportBases);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_SPORT_FAIL);
        }
    }
}
