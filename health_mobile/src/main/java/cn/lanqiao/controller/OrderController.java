package cn.lanqiao.controller;

import cn.lanqiao.constant.MessageConstant;
import cn.lanqiao.constant.RedisMessageConstant;
import cn.lanqiao.entity.Result;
import cn.lanqiao.pojo.Order;
import cn.lanqiao.pojo.Setmeal;
import cn.lanqiao.service.OrderService;
import cn.lanqiao.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;

/**
 * @Author: Hou
 * @Date: 2021/5/14 17:52
 * @Description:体检预约处理
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SetmealService setmealService;

    //在线体检预约
    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map) {
        String telephone = (String) map.get("telephone");
        //从Redis中获取保存的验证码
        String validateCodeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        String validateCode = (String) map.get("validateCode");

        //将用户输入的验证码和Redis中保存的验证码进行比对
        if (validateCodeInRedis != null && validateCode != null && validateCode.equals(validateCodeInRedis)) {
            //如果比对成功，调用服务完成预约业务处理
            map.put("orderType", Order.ORDERTYPE_WEIXIN);//设置预约类型，分为微信预约、电话预约
            Result result = null;
            try {
                result = orderService.order(map);//通过Dubbo远程调用服务实现在线预约业务处理
            } catch (Exception e) {
                e.printStackTrace();
                return result;
            }
            if (result.isFlag()) {
                //预约成功，可以为用户发送短信
                try {
//                    SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE,telephone, (String) map.get("orderDate"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return result;
        } else {
            //如果比对不成功，返回结果给页面
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
    }

    //体检预约
    @RequestMapping("/submitOrder")
    public Result submitOrder(@RequestBody Map map) {
        String telephone = (String) map.get("telephone");
        map.put("orderType", Order.ORDERTYPE_WEIXIN);
        Result result = null;
        try {
            result = orderService.order(map);
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
        return result;

    }

    //根据预约ID查询预约相关信息
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            Map map = orderService.findById(id);
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
        }
    }

    //根据预约ID查询体检报告相关信息
    @RequestMapping("/findTestById")
    public Result findTestById(Integer id) {
        try {
            List<Map> maps = orderService.findTestById(id);
            for (int i = 0; i < maps.size(); i++) {
                Map map = maps.get(i);
                Integer setmeal_id = (Integer) map.get("setmeal_id");
                Setmeal setmeal = setmealService.findById(setmeal_id);
                map.put("name", setmeal.getName());
                map.put("img", setmeal.getImg());
            }
            return new Result(true, MessageConstant.QUERY_TEST_SUCCESS, maps);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_TEST_FAIL);
        }
    }
}
