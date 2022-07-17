package cn.lanqiao.service;

import cn.lanqiao.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @Author: Hou
 * @Date: 2021/4/28 22:32
 * @Description:
 */
public interface OrderSettingService {
    public void add(List<OrderSetting> data);
    public List<Map> getOrderSettingByMonth(String date);
    public void editNumberByDate(OrderSetting orderSetting);
}
