package cn.lanqiao.dao;

import cn.lanqiao.pojo.CheckGroup;
import cn.lanqiao.pojo.CheckItem;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: Hou
 * @Date: 2021/4/20 11:26
 * @Description:
 */
@Repository
public interface CheckGroupDao {
    public void add(CheckGroup checkGroup);
    public void setCheckGroupAndCheckItem(Map map);
    public Page<CheckGroup> findByCondition(String queryString);
    public CheckGroup findById(Integer id);
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id);
    public void edit(CheckGroup checkGroup);
    public void deleteAssocication(Integer id);
    public void deleteById(Integer id);
    public long findCountByCheckGroupId(Integer id);
    public List<CheckGroup> findAll();
}

