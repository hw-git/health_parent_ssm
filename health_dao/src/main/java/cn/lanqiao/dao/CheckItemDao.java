package cn.lanqiao.dao;

import cn.lanqiao.pojo.CheckItem;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Hou
 * @Date: 2021/4/15 21:59
 * @Description:
 */
@Repository
public interface CheckItemDao {
    public void add(CheckItem checkItem);
    public Page<CheckItem> selectByCondition(String queryString);
    public long findCountByCheckItemId(Integer id);
    public void deleteById(Integer id);
    public void edit(CheckItem checkItem);
    public CheckItem findById(Integer id);
    public List<CheckItem> findAll();
}
