package cn.lanqiao.service;

import cn.lanqiao.entity.OrderQueryPageBean;
import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.Result;

import java.util.List;
import java.util.Map;

/**
 * @Author: Hou
 * @Date: 2021/5/14 14:44
 * @Description:
 */
public interface OrderService {
    //体检预约
    public Result order(Map map) throws Exception;

    //根据id查询预约信息，包括体检人信息、套餐信息
    public Map findById(Integer id) throws Exception;

    /**
     * 分页查询预约信息
     *
     * @param param
     * @return
     */
    PageResult findPage(OrderQueryPageBean param);

    /**
     * 通过order_id修改预约状态
     *
     * @param param
     */
    void updateOrderStatus(Map param);

    /**
     * 电话预约
     *
     * @param param
     * @param setmealIds
     * @return
     */
    Result phoneOrder(Map param, Integer[] setmealIds) throws Exception;

    /**
     * 删除预约信息
     *
     * @param orderId
     */
    Result deleteOrder(Integer orderId);

    /**
     * 预约信息回显
     *
     * @param orderId
     * @return
     */
    Map orderInfoReView(Integer orderId);

    /**
     * 通过订单id修改预约信息
     *
     * @param map
     * @param setmealIds
     * @return
     */
    Result orderUpdate(Map map, Integer[] setmealIds) throws Exception;

    /*根据日期，查询当前用户预约信息，包括体检人信息、套餐信息*/
    Map findByDate4Detail(String startTime, String endTime) throws Exception;

    List<Map> findTestById(Integer id);

}
