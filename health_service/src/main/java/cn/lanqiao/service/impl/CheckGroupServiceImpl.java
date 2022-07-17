package cn.lanqiao.service.impl;

import cn.lanqiao.dao.CheckGroupDao;
import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.pojo.CheckGroup;
import cn.lanqiao.service.CheckGroupService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Hou
 * @Date: 2021/4/20 11:25
 * @Description:
 */
@Service
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;
    //新增检查组，同时需要让检查组关联检查项
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //新增检查组，操作t_checkgroup表
        checkGroupDao.add(checkGroup);
        //设置检查组和检查项的多对多的关联关系，操作t_checkgroup_checkitem表
        Integer checkGroupId = checkGroup.getId();
        this.setCheckGroupAndCheckItem(checkGroupId,checkitemIds);
    }

    //分页查询
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckGroup> page = checkGroupDao.findByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    //根据ID查询检查组
    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }
    //根据检查组ID查询关联的检查项ID
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }
    //根据ID删除检查组
    @Override
    public void deleteById(Integer id) {
        //判断当前检查a组是否已经关联到套餐
        long count = checkGroupDao.findCountByCheckGroupId(id);
        if(count > 0){
            //当前检查项已经被关联到检查组，不允许删除
            new RuntimeException();
        }
        checkGroupDao.deleteAssocication(id);
        checkGroupDao.deleteById(id);
    }

    //编辑检查组信息，同时需要关联检查项
    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        //修改检查组基本信息，操作检查组t_checkgroup表
        checkGroupDao.edit(checkGroup);
        //清理当前检查组关联的检查项，操作中间关系表t_checkgroup_checkitem表
        checkGroupDao.deleteAssocication(checkGroup.getId());
        //重新建立当前检查组和检查项的关联关系
        Integer checkGroupId = checkGroup.getId();
        setCheckGroupAndCheckItem(checkGroupId,checkitemIds);
    }

    //建立检查组和检查项多对多关系
    public void setCheckGroupAndCheckItem(Integer checkGroupId,Integer[] checkitemIds){
        if(checkitemIds != null && checkitemIds.length > 0){
            for (Integer checkitemId : checkitemIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("checkgroupId",checkGroupId);
                map.put("checkitemId",checkitemId);
                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }
    }
}
