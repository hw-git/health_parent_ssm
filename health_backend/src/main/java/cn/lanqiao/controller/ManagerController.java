package cn.lanqiao.controller;

import cn.lanqiao.constant.MessageConstant;
import cn.lanqiao.constant.RedisConstant;
import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.entity.Result;
import cn.lanqiao.pojo.Manager;
import cn.lanqiao.service.ManagerService;
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
 * /**
 *
 * @Author: Hou
 * @Date: 2021/5/20 12:55
 * @Description:资讯管理
 */
@RestController
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    private ManagerService managerService;
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

    @RequestMapping("/edit")
    public Result edit(@RequestBody Manager manager) {
        try {
            managerService.edit(manager);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, "修改失败");
        }
    }

    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            managerService.delete(id);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, "删除失败");
        }
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Manager manager) {
        try {
            managerService.add(manager);
            return new Result(true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, "添加失败");
        }
    }
}
