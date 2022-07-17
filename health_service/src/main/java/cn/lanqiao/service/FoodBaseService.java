package cn.lanqiao.service;

import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.pojo.FoodBase;

import java.util.List;

/**
 * 食品库服务接口
 */
public interface FoodBaseService {
    /**
     * poi批量导入
     *
     * @param list
     */
    public void importData(List<FoodBase> list);

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
    public FoodBase findFoodById(Integer id);

    /**
     * 修改food数据
     *
     * @param foodBase
     */
    public void edit(FoodBase foodBase);

    /**
     * 新增
     *
     * @param foodBase
     */
    public void add(FoodBase foodBase);

    /**
     * 根据id删除
     *
     * @param id
     */
    public void deleteById(Integer id);

    /**
     * 查询所有
     */
    public List<FoodBase> findAll();
}
