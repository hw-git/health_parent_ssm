package cn.lanqiao.controller;

import cn.lanqiao.constant.MessageConstant;
import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.entity.Result;
import cn.lanqiao.pojo.DiseaseBase;
import cn.lanqiao.service.DiseaseBaseService;
import cn.lanqiao.utils.POIUtils;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Hou
 * @Date: 2021/5/16 17:55
 * @Description:疾病库
 */
@RestController
@RequestMapping("/diseaseBaseController")
public class DiseaseBaseController {
    //注入服务接口
    @Autowired
    private DiseaseBaseService diseaseBaseService;

    /**
     * 疾病库导入
     *
     * @param file
     * @return
     */
    @RequestMapping("/diseaseupload")
    public Result upload(@RequestParam("excelFile") MultipartFile file) {
        try {
            //获取上传数据并封装
            List<String[]> list = POIUtils.readExcel(file);
            List<DiseaseBase> diseaseBaseList = new ArrayList<>();
            for (String[] strings : list) {
                String number = strings[0];
                String diseasename = strings[1];
                String diseasetype = strings[2];
                String diseasestate = strings[3];
                DiseaseBase diseaseBase = new DiseaseBase(number, diseasename, diseasetype, diseasestate);
                diseaseBaseList.add(diseaseBase);
            }
            //调用服务接口
            diseaseBaseService.importData(diseaseBaseList);
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
            PageResult pageResult = diseaseBaseService.pageQuery(queryPageBean);
            return new Result(true, MessageConstant.QUERY_DISEASE_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_DISEASE_FAIL);
        }
    }

    /**
     * 回显数据
     *
     * @param diseaseid
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer diseaseid) {
        try {
            DiseaseBase foodBase = diseaseBaseService.findDiseaseById(diseaseid);
            return new Result(true, MessageConstant.QUERY_DISEASE_SUCCESS, foodBase);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_DISEASE_FAIL);
        }
    }

    /**
     * 编辑疾病
     *
     * @param diseaseBase
     * @return
     */
    @RequestMapping("/edit")
    public Result edit(@RequestBody DiseaseBase diseaseBase) {
        try {
            diseaseBaseService.edit(diseaseBase);
            return new Result(true, MessageConstant.EDIT_DISEASE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_DISEASE_FAIL);
        }
    }

    /**
     * 新增add
     *
     * @param diseaseBase
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody DiseaseBase diseaseBase) {
        try {
            diseaseBaseService.add(diseaseBase);
            return new Result(true, MessageConstant.ADD_DISEASE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_DISEASE_FAIL);
        }
    }

    /**
     * 删除数据
     *
     * @param diseaseid
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Integer diseaseid) {
        try {
            diseaseBaseService.deleteById(diseaseid);
            return new Result(true, MessageConstant.DELETE_DISEASE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_DISEASE_FAIL);
        }
    }

    @RequestMapping("/findAll")
    public Result findAll() {
        try {
            List<DiseaseBase> diseaseBases = diseaseBaseService.findAll();
            return new Result(true, MessageConstant.QUERY_DISEASE_SUCCESS, diseaseBases);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_DISEASE_FAIL);
        }
    }

    @RequestMapping("selectByCondition")
    public Result selectByCondition(@RequestBody Map map) {
        String condition = (String) map.get("condition");
        try {
            Page<DiseaseBase> page = diseaseBaseService.selectByCondition(condition);
            System.out.println(condition);
            return new Result(true, MessageConstant.QUERY_DISEASE_SUCCESS, page);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_DISEASE_FAIL);
        }
    }

}
