package cn.lanqiao.dao;

import cn.lanqiao.pojo.SportBase;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * 食品库Dao
 */
public interface SportBaseDao {

    /**
     * 添加一条食品记录
     *
     * @param sportBase
     */
    public void add(SportBase sportBase);

    /**
     * 根据食品名称修改记录
     *
     * @param sportBase
     */
    public void editSportByName(SportBase sportBase);

    /**
     * 根据名称查找
     *
     * @param sportBase
     * @return
     */
    public Long findCountByName(String sportBase);

    /**
     * 按条件查询
     *
     * @param queryString
     * @return
     */
    public Page<SportBase> selectByCondition(String queryString);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    public SportBase findSportById(Integer id);

    /**
     * 根据id修改
     *
     * @param sportBase
     */
    public void edit(SportBase sportBase);

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

    /**
     * 根据会员id查询运动ids
     *
     * @param member_id
     * @return
     */
    public List<Integer> findSportIdsByMemberIds(Integer member_id);
}
