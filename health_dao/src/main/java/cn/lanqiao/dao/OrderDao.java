package cn.lanqiao.dao;

import cn.lanqiao.entity.OrderQueryPageBean;
import cn.lanqiao.pojo.Order;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author: Hou
 * @Date: 2021/5/14 17:56
 * @Description:
 */
public interface OrderDao {
    public void add(Order order);

    public List<Order> findByCondition(Order order);

    public Map findById4Detail(Integer id);

    public List<Map> findTestById(Integer id);

    public Integer findOrderCountByDate(String date);

    public Integer findOrderCountAfterDate(String date);

    public Integer findVisitsCountByDate(String date);

    public Integer findVisitsCountAfterDate(String date);

    public List<Map> findHotSetmeal();

    /**
     * 分页查询预约信息
     *
     * @param param
     * @return
     */
    Page<Map> findPage(OrderQueryPageBean param);

    /**
     * 通过order_id修改预约状态
     *
     * @param param
     */
    void updateOrderStatus(Map param);

    /**
     * 通过orderId查询预约信息
     *
     * @param orderId
     * @return
     */
    Order findByOrderId(Integer orderId);

    /**
     * 删除预约信息
     *
     * @param orderId
     */
    void deleteOrder(Integer orderId);

    /**
     * 编辑预约回显
     *
     * @param orderId
     * @return
     */
    Map findMapByOrderId(Integer orderId);

    /**
     * 更新预约信息
     *
     * @param order
     */
    void updateOrder(Order order);

    Integer findIdByMemberId(Integer memberId);
}
