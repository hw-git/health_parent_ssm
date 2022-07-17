package cn.lanqiao.controller;

import cn.lanqiao.constant.MessageConstant;
import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.entity.Result;
import cn.lanqiao.pojo.Psychology;
import cn.lanqiao.service.PsychologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/PsychologyController")
public class PsychologyController {
    @Autowired
    private PsychologyService psychologyService;
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        try {
            PageResult pageResult = psychologyService.pageQuery(queryPageBean);
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
    public Result add(@RequestBody Psychology psychology){
        try {
            psychologyService.add(psychology);
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
    @RequestMapping("/find")
    public Result findAll(String number){
        if(number!=null){
            try {
                List<Psychology> psychologies = psychologyService.findAll(number);
                System.out.println(number+"1----1");
                System.out.println(psychologies+"------");
                return new Result(true,MessageConstant.QUERY_CONSTITUTION_SUCCESS,psychologies);
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(false,MessageConstant.QUERY_CONSTITUTION_FAIL);
            }
        }else {
            return new Result(false,MessageConstant.QUERY_CONSTITUTION_FAIL);
        }
    }
}
