package cn.lanqiao.service.impl;

import cn.lanqiao.dao.ResourcesPermissionDao;
import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.pojo.Permission;
import cn.lanqiao.service.ResourcesPermissionService;
import cn.lanqiao.utils.DateUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: Hou
 * @Date: 2021/5/24 18:55
 * @Description:资源权限业务操作
 */
@Transactional
@Service
public class ResourcesPermissionServiceImpl implements ResourcesPermissionService {
    //注入Dao
    @Autowired
    private ResourcesPermissionDao resourcesPermissionDao;

    /**
     * 资源权限分页
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) throws Exception {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        //分页助手
        PageHelper.startPage(currentPage, pageSize);
        Page<Permission> pageInfo = resourcesPermissionDao.selectByCondition(queryString);
        long total = pageInfo.getTotal();
        List<Permission> result = pageInfo.getResult();

        return new PageResult(total, result);
    }

    /**
     * 编辑表单回显
     *
     * @param id
     * @return
     */
    @Override
    public Permission findById(Integer id) {
        return resourcesPermissionDao.findById(id);
    }

    /**
     * 编辑表单提交
     *
     * @param permission
     */
    @Override
    public void edit(Permission permission) throws Exception {
        //设置时间
        permission.setCreationDate(setDate());
        resourcesPermissionDao.updateResources(permission);
    }

    /**
     * 新增资源权限
     *
     * @param permission
     */
    @Override
    public void add(Permission permission) throws Exception {
        //设置时间
        permission.setCreationDate(setDate());
        resourcesPermissionDao.add(permission);
    }

    /**
     * 删除资源权限
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        resourcesPermissionDao.delete(id);
    }

    /**
     * 切换权限状态
     *
     * @param id
     */
    @Override
    public void change(Integer id) {
        Permission permission = resourcesPermissionDao.findById(id);
        Boolean flag = permission.getState();
        Boolean state = !flag;
        permission.setState(state);
        resourcesPermissionDao.change(permission);
    }

    /**
     * 查询所有
     *
     * @return
     */
    @Override
    public List<Permission> findAll() {
        return resourcesPermissionDao.findAll();
    }

    /**
     * 生成当前时间日期
     *
     * @return
     */
    public Date setDate() throws Exception {
        //设置日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = simpleDateFormat.format(new Date());
        Date date = DateUtils.parseString2Date(formatDate);
        System.out.println(date);
        return date;
    }
}
