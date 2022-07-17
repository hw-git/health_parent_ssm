package cn.lanqiao.dao;

import cn.lanqiao.pojo.FoodBase;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * 食品库Dao
 */
public interface FoodBaseDao {

    /**
     * 添加一条食品记录
     *
     * @param foodBase
     */
    public void add(FoodBase foodBase);

    /**
     * 根据食品名称修改记录
     *
     * @param foodBase
     */
    public void editFoodByName(FoodBase foodBase);

    /**
     * 根据名称查找
     *
     * @param foodname
     * @return
     */
    public Long findCountByName(String foodname);

    /**
     * 按条件查询
     *
     * @param queryString
     * @return
     */
    public Page<FoodBase> selectByCondition(String queryString);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    public FoodBase findFoodById(Integer id);

    /**
     * 根据id修改
     *
     * @param foodBase
     */
    public void edit(FoodBase foodBase);

    /**
     * 根据id删除
     *
     * @param id
     */
    public void deleteById(Integer id);

    /**
     * 查询全部
     *
     * @return
     */
    public List<FoodBase> findAll();

    /**
     * 根据会员id查询食品ids
     *
     * @param member_id
     * @return
     */
    public List<Integer> findFoodIdsByMemberIds(Integer member_id);
}
