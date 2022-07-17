package cn.lanqiao.controller;


import cn.lanqiao.constant.MessageConstant;
import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.entity.Result;
import cn.lanqiao.pojo.Constitution;

import cn.lanqiao.service.ConstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@RestController
@RequestMapping("/constitutionController")
public class ConstitutionController {
    @Autowired
    private ConstitutionService constitutionService;
    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        try {
            PageResult pageResult = constitutionService.pageQuery(queryPageBean);
            return new Result(true, MessageConstant.QUERY_CONSTITUTION_SUCCESS,pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CONSTITUTION_FAIL);
        }
    }
    /**
     * 新增add
     * @param
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Constitution constitutione){
        try {
            constitutionService.add(constitutione);
            return new Result(true,MessageConstant.ADD_CONSTITUTION_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_CONSTITUTION_FAIL);
        }
    }
    /**
     * 查询所有
     * @return
     */
    @RequestMapping("/findAll")
    public Result findAll(String number){
        try {
            List<Constitution> constitutions = constitutionService.findAll(number);
            return new Result(true,MessageConstant.QUERY_CONSTITUTION_SUCCESS,constitutions);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CONSTITUTION_FAIL);
        }
    }
}
