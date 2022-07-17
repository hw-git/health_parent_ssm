package cn.lanqiao.service.impl;

import cn.lanqiao.constant.MessageConstant;
import cn.lanqiao.dao.MemberDao;
import cn.lanqiao.dao.OrderDao;
import cn.lanqiao.dao.OrderSettingDao;
import cn.lanqiao.entity.OrderQueryPageBean;
import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.Result;
import cn.lanqiao.pojo.Member;
import cn.lanqiao.pojo.Order;
import cn.lanqiao.pojo.OrderSetting;
import cn.lanqiao.service.OrderService;
import cn.lanqiao.utils.DateUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @Author: Hou
 * @Date: 2021/5/14 17:55
 * @Description:预约
 */
@Transactional
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;

    //体检预约
    @Override
    public Result order(Map map) throws Exception {
        //1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
        String orderDate = (String) map.get("orderDate");//预约日期
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(DateUtils.parseString2Date(orderDate));
        if (orderSetting == null) {
            //指定日期没有进行预约设置，无法完成体检预约
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }

        //2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
        int number = orderSetting.getNumber();//可预约人数
        int reservations = orderSetting.getReservations();//已预约人数
        if (reservations >= number) {
            //已经约满，无法预约
            return new Result(false, MessageConstant.ORDER_FULL);
        }

        //3、检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再次预约
        String telephone = (String) map.get("telephone");//获取用户页面输入的手机号
        Member member = memberDao.findByTelephone(telephone);
        if (member != null) {
            //判断是否在重复预约
            Integer memberId = member.getId();//会员ID
            Date order_Date = DateUtils.parseString2Date(orderDate);//预约日期
            String setmealId = (String) map.get("setmealId");//套餐ID
            Order order = new Order(memberId, order_Date, Integer.parseInt(setmealId));
            //根据条件进行查询
            List<Order> list = orderDao.findByCondition(order);
            if (list != null && list.size() > 0) {
                //说明用户在重复预约，无法完成再次预约
                return new Result(false, MessageConstant.HAS_ORDERED);
            }
        } else {
            //4、检查当前用户是否为会员，如果是会员则直接完成预约，如果不是会员则自动完成注册并进行预约
            member = new Member();
            member.setName((String) map.get("name"));
            member.setPhoneNumber(telephone);
            member.setIdCard((String) map.get("idCard"));
            member.setSex((String) map.get("sex"));
            member.setRegTime(new Date());
            memberDao.add(member);//自动完成会员注册
        }

        //5、预约成功，更新当日的已预约人数
        Order order = new Order();
        order.setMemberId(member.getId());//设置会员ID
        order.setOrderDate(DateUtils.parseString2Date(orderDate));//预约日期
        order.setOrderType((String) map.get("orderType"));//预约类型
        order.setOrderStatus(Order.ORDERSTATUS_NO);//到诊状态
        order.setSetmealId(Integer.parseInt((String) map.get("setmealId")));//套餐ID
        orderDao.add(order);

        orderSetting.setReservations(orderSetting.getReservations() + 1);//设置已预约人数+1
        orderSettingDao.editReservationsByOrderDate(orderSetting);

        return new Result(true, MessageConstant.ORDER_SUCCESS, order.getId());
    }

    //根据预约ID查询预约相关信息（体检人姓名、预约日期、套餐名称、预约类型）
    @Override
    public Map findById(Integer id) throws Exception {
        Map map = orderDao.findById4Detail(id);
        if (map != null) {
            //处理日期格式
            Date orderDate = (Date) map.get("orderDate");
            map.put("orderDate", DateUtils.parseDate2String(orderDate));
        }
        return map;
    }

    /**
     * 分页查询预约列表
     *
     * @param param
     * @return
     */
    @Override
    public PageResult findPage(OrderQueryPageBean param) {
        PageResult result = null;
        if (param != null && param.getCurrentPage() != null && param.getPageSize() != null) {
            PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
            Page<Map> orderSettingPage = orderDao.findPage(param);
            List<Map> list = orderSettingPage.getResult();
            result = new PageResult(orderSettingPage.getTotal(), list);
        }
        return result;
    }

    /**
     * 通过order_id修改预约状态
     *
     * @param param
     */
    @Override
    public void updateOrderStatus(Map param) {
        orderDao.updateOrderStatus(param);
    }

    /**
     * 电话预约
     *
     * @param map
     * @param setmealIds
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result phoneOrder(Map map, Integer[] setmealIds) throws Exception {
        if (setmealIds != null && setmealIds.length > 0) {
            //1.检查当前日期是否进行了预约设置
            String orderDate = (String) map.get("orderDate");
            Date date = DateUtils.parseString2Date(orderDate);
            OrderSetting orderSetting = orderSettingDao.findByOrderDate(date);
            if (orderSetting == null) {
                return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
            }

            //2.检查预约日期是否预约已满
            int number = orderSetting.getNumber();//可预约人数
            int reservations = orderSetting.getReservations();//已预约人数
            if (reservations >= number) {
                //预约已满，不能预约
                return new Result(false, MessageConstant.ORDER_FULL);
            }

            //检查当前用户是否为会员，根据手机号判断
            String telephone = (String) map.get("telephone");
            Member member = memberDao.findByTelephone(telephone);

            //3.防止重复预约

            if (member != null) {
                boolean flag = false;
                Integer memberId = member.getId();
                for (Integer setmealId : setmealIds) {
                    Order order = new Order(memberId, date, null, null, setmealId, null);
                    List<Order> list = orderDao.findByCondition(order);
                    if (list != null && list.size() > 0) {
                        //已经完成了预约，不能重复预约
                        flag = true;
                    } else {
                        //可以预约，设置预约人数加一
                        orderSetting.setReservations(orderSetting.getReservations() + 1);
                        orderSettingDao.editReservationsByOrderDate(orderSetting);
                        Order orders = new Order(member.getId(), date, (String) map.get("orderType"), Order.ORDERSTATUS_NO,
                                setmealId, (String) map.get("organizationPhone"));
                        orderDao.add(orders);
                    }
                }
                if (flag) {
                    return new Result(false, MessageConstant.HAS_ORDERED_CHECK);
                } else {
                    return new Result(true, MessageConstant.ORDER_SUCCESS);
                }
            }
            // 4.检查当前用户是否为会员
            if (member == null) {
                return new Result(false, MessageConstant.TELE_NOHAVE);
            }
            //5.保存预约信息到预约表
            for (Integer setmealId : setmealIds) {
                //可以预约，设置预约人数加一
                orderSetting.setReservations(orderSetting.getReservations() + 1);
                orderSettingDao.editReservationsByOrderDate(orderSetting);
                Order order = new Order(member.getId(), date, (String) map.get("orderType"), Order.ORDERSTATUS_NO,
                        setmealId, (String) map.get("organizationPhone"));
                orderDao.add(order);
            }
            return new Result(true, MessageConstant.ORDER_SUCCESS);
        } else {
            return new Result(false, "请选择需要预约的套餐");
        }


    }


    @Override
    public Result deleteOrder(Integer orderId) {
        Order order = orderDao.findByOrderId(orderId);
        if (order != null) {
            if (Order.ORDERSTATUS_YES.equals(order.getOrderStatus())) {
                orderDao.deleteOrder(orderId);
                return new Result(true, MessageConstant.DELETE_ORDER_OK);
            } else {
                return new Result(false, MessageConstant.DELETE_ORDER_FAIL);
            }
        } else {
            return new Result(false, "预约信息不存在");
        }
    }


    /**
     * 预约信息回显
     *
     * @param orderId
     * @return
     */
    @Override
    public Map orderInfoReView(Integer orderId) {
        return orderDao.findMapByOrderId(orderId);
    }

    //通过订单id修改预约信息
    @Override
    public Result orderUpdate(Map map, Integer[] setmealIds) throws Exception {
        String telephone = (String) map.get("telephone");
        String name = (String) map.get("name");
        Integer member_id = (Integer) map.get("member_id");
        Date orderDate = DateUtils.parseString2Date((String) map.get("orderDate"));
        Integer order_id = (Integer) map.get("order_id");
        String organizationPhone = (String) map.get("organizationPhone");
        if (setmealIds != null && setmealIds.length == 1) {
            Member member = memberDao.findByTelephone(telephone);
            Order order = orderDao.findByOrderId(order_id);
            if (!member.getId().equals(member_id)) {
                return new Result(false, "手机号已存在");
            }
            OrderSetting orderSetting = orderSettingDao.findByOrderDate(orderDate);
            //1.检查当前日期是否进行了预约设置
            if (orderSetting == null) {
                return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
            }
            if (!order.getSetmealId().equals(setmealIds[0])) {
                //2.检查预约日期是否预约已满
                int number = orderSetting.getNumber();//可预约人数
                int reservations = orderSetting.getReservations();//已预约人数
                if (reservations >= number) {
                    //预约已满，不能预约
                    return new Result(false, MessageConstant.ORDER_FULL);
                }
                Order orders = new Order(member_id, orderDate, null, null, setmealIds[0], null);
                List<Order> list = orderDao.findByCondition(orders);
                if (list != null && list.size() > 0) {
                    return new Result(false, MessageConstant.HAS_ORDERED);
                }
            }

            // 更新会员表
            member.setPhoneNumber(telephone);
            member.setName(name);
            memberDao.edit(member);
            // 更新预约表
            order.setOrderDate(orderDate);
            order.setOrganizationPhone(organizationPhone);
            order.setSetmealId(setmealIds[0]);
            order.setUrl((String) map.get("url"));
            orderDao.updateOrder(order);
            return new Result(true, MessageConstant.EDIT_ORDER_OK);
        } else if (setmealIds == null || setmealIds.length == 0) {
            return new Result(false, "套餐信息不能为空");
        } else {
            return new Result(false, "编辑时请勿选择多个套餐");
        }
    }

    @Override
    public Map findByDate4Detail(String startTime, String endTime) throws Exception {

        //DateUtils.parseString2Date(startDate), DateUtils.parseString2Date(endDate)
        List<Integer> memberIds = memberDao.findMemberId4Date(startTime, endTime);
        //根据会员memberId表查预约id
        for (Integer memberId : memberIds) {
            Integer orderId = orderDao.findIdByMemberId(memberId);
            //根据预约id查询预约信息，包括体检人信息、套餐信息
            Map map = orderDao.findById4Detail(orderId);
            return map;
        }
        return null;
    }

    @Override
    public List<Map> findTestById(Integer id) {
        List<Map> maps = orderDao.findTestById(id);
        return maps;
    }

}
