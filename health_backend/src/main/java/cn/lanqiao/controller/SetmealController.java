package cn.lanqiao.controller;

import cn.lanqiao.constant.MessageConstant;
import cn.lanqiao.constant.RedisConstant;
import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.entity.Result;
import cn.lanqiao.pojo.Setmeal;
import cn.lanqiao.service.SetmealService;
import cn.lanqiao.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @Author: Hou
 * @Date: 2021/4/29 13:55
 * @Description:套餐
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    //使用JedisPool操作Redis服务
    @Autowired
    private JedisPool jedisPool;

    //文件上传
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile) {
        System.out.println(imgFile);
        String originalFilename = imgFile.getOriginalFilename();//原始文件名 3bd90d2c-4e82-42a1-a401-882c88b06a1a2.jpg
        System.out.println("originalFilename = " + originalFilename);
        int index = originalFilename.lastIndexOf(".");
        System.out.println("index = " + index);
        String extention = originalFilename.substring(index - 1);//.jpg
        System.out.println("extention = " + extention);
        String fileName = UUID.randomUUID().toString() + extention;//	FuM1Sa5TtL_ekLsdkYWcf5pyjKGu.jpg
        System.out.println("fileName = " + fileName);
        try {
            //将文件上传到七牛云服务器
            QiniuUtils.upload2Qiniu(imgFile.getBytes(), fileName);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
        return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, fileName);
    }

    @Autowired
    private SetmealService setmealService;

    //新增套餐
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        try {
            setmealService.add(setmeal, checkgroupIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    //修改套餐
    @RequestMapping("/update")
    public Result update(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        System.out.println("setmeal = " + setmeal);
        System.out.println("setmeal.getAge() = " + setmeal.getAge());
        try {
            setmealService.update(setmeal, checkgroupIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.UPDATE_SETMEAL_FAIL);
        }
        return new Result(true, MessageConstant.UPDATE_SETMEAL_SUCCESS);
    }

    //根据ID查询套餐
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            Setmeal setmeal = setmealService.findById(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);//查询成功
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);//查询失败
        }
    }

    //查询当前检查组包含的所有检查项id
    @RequestMapping("/findCheckGroupById")
    public Result findCheckGroupById(Integer id) {
        try {
            List<Integer> setmealIds = setmealService.findCheckGroupById(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmealIds);//查询成功
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);//查询失败
        }
    }

    //删除套餐
    @RequestMapping("/delete.do")
    public Result delete(Integer id) {
        try {
            setmealService.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.DELETE_SETMEAL_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS);
    }

    //分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return setmealService.pageQuery(queryPageBean);
    }

    /**
     * 查询所有套餐信息
     *
     * @return
     */
    @RequestMapping("/findAll")
    public Result findAll() {
        try {
            List<Setmeal> all = setmealService.findAll();
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, all);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
    }
}
