package cn.lanqiao.controller;

import cn.lanqiao.constant.MessageConstant;
import cn.lanqiao.constant.RedisConstant;
import cn.lanqiao.entity.OrderQueryPageBean;
import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.Result;
import cn.lanqiao.pojo.Order;
import cn.lanqiao.service.OrderService;
import cn.lanqiao.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: Hou
 * @Date: 2021/5/21 12:20
 * @Description:预约
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
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

    /**
     * 预约列表分页查询
     *
     * @param param 查询条件
     * @return 预约数据
     * @author renzheng
     */
    @PostMapping("/findPage")
    public PageResult findPage(@RequestBody OrderQueryPageBean param) {
        PageResult result = orderService.findPage(param);
        return result;
    }

    /**
     * 更新到诊状态
     *
     * @param param 预约id,到诊状态
     * @return
     */
    @PostMapping("/updateOrderStatus")
    public Result updateOrderStatus(@RequestBody Map param) {
        try {
            orderService.updateOrderStatus(param);
            return new Result(true, MessageConstant.UPDATE_ORDER_STATUS_OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.UPDATE_ORDER_STATUS_FAIL);
    }

    /**
     * 电话预约
     *
     * @param param
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Map param, Integer[] setmealIds) {
        Result result = null;
        try {
            param.put("orderType", Order.ORDERTYPE_TELEPHONE);
            result = orderService.phoneOrder(param, setmealIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 删除预约订单信息
     *
     * @param orderId
     * @return
     */
    @RequestMapping("/deleteOrder")
    public Result deleteOrder(Integer orderId) {
        return orderService.deleteOrder(orderId);
    }

    /**
     * 编辑预约信息回显
     *
     * @param orderId
     * @return
     */
    @RequestMapping("/orderInfoReView")
    public Result orderInfoReView(Integer orderId) {
        Map order = null;
        try {
            order = orderService.orderInfoReView(orderId);
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, order);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
    }

    @RequestMapping("/editOrder")
    public Result editOrder(@RequestBody Map map, Integer[] setmealIds) {
        Result result = null;
        try {
            return orderService.orderUpdate(map, setmealIds);
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }


}
