package cn.lanqiao.service.impl;

import cn.lanqiao.constant.RedisConstant;
import cn.lanqiao.dao.SetmealDao;
import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.pojo.Setmeal;
import cn.lanqiao.service.SetmealService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Transactional
@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private JedisPool jedisPool;

    //新增套餐信息，同时需要关联检查组
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        Integer setmealId = setmeal.getId();
        this.setSetmealAndCheckgroup(setmealId, checkgroupIds);
        //将图片名称保存到Redis集合中
        String fileName = setmeal.getImg();
        if (fileName != null) {
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, fileName);
        }
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage, pageSize);
        Page<Setmeal> page = setmealDao.findByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    @Override
    public List<Map> getSetmealReport(String startTime, String endTime) {
        return setmealDao.getSetmealReport(startTime, endTime);
    }

    @Override
    public List<Map> findSetmealCount() {
        return setmealDao.findSetmealCount();
    }

    //根据ID查询套餐
    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    //根据套餐ID查询关联的检查组ID
    @Override
    public List<Integer> findCheckGroupById(Integer id) {
        return setmealDao.findCheckGroupById(id);
    }

    //根据ID删除检查组
    @Override
    public void deleteById(Integer id) {
        setmealDao.deleteAssocication(id);
        setmealDao.deleteById(id);
    }

    //更新套餐
    @Override
    public void update(Setmeal setmeal, Integer[] checkgroupIds) {
        //修改套餐基本信息
        setmealDao.update(setmeal);
        System.out.println("--------------");
        System.out.println(setmealDao.findById(setmeal.getId()));
        //清理当前套餐关联的检查组
        setmealDao.deleteAssocication(setmeal.getId());
        //重新建立当前套餐和检查组的关联关系
        Integer setmealId = setmeal.getId();
        this.setSetmealAndCheckgroup(setmealId, checkgroupIds);
        //将图片名称保存到Redis集合中
        String fileName = setmeal.getImg();
        if (fileName != null) {
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, fileName);
        }
    }

    //设置套餐和检查组多对多关系，操作t_setmeal_checkgroup
    public void setSetmealAndCheckgroup(Integer setmealId, Integer[] checkgroupIds) {
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            for (Integer checkgroupId : checkgroupIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("setmealId", setmealId);
                map.put("checkgroupId", checkgroupId);
                setmealDao.setSetmealAndCheckGroup(map);
            }
        }
    }
}
