package cn.lanqiao.service;

import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.pojo.SportBase;

import java.util.List;

/**
 * 运动库服务接口
 */
public interface SportBaseService {
    /**
     * poi批量导入
     *
     * @param list
     */
    public void importData(List<SportBase> list);

    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    public PageResult pageQuery(QueryPageBean queryPageBean);

    /**
     * 编辑表格回显数据
     *
     * @param id
     * @return
     */
    public SportBase findSportById(Integer id);

    /**
     * 修改food数据
     *
     * @param sportBase
     */
    public void edit(SportBase sportBase);

    /**
     * 新增
     *
     * @param sportBase
     */
    public void add(SportBase sportBase);

    /**
     * 根据id删除
     *
     * @param id
     */
    public void deleteById(Integer id);

    /**
     * 查询所有
     *
     * @return
     */
    public List<SportBase> findAll();
}
