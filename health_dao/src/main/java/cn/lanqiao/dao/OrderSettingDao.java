package cn.lanqiao.dao;

import cn.lanqiao.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Hou
 * @Date: 2021/4/28 22:37
 * @Description:
 */
public interface OrderSettingDao {
    public void add(OrderSetting orderSetting);

    public void editNumberByOrderDate(OrderSetting orderSetting);

    public long findCountByOrderDate(Date orderDate);

    public List<OrderSetting> getOrderSettingByMonth(Map map);

    public OrderSetting findByOrderDate(Date orderDate);

    public void editReservationsByOrderDate(OrderSetting orderSetting);
}
