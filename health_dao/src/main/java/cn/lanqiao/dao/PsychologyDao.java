package cn.lanqiao.dao;


import cn.lanqiao.pojo.Psychology;
import com.github.pagehelper.Page;

import java.util.List;

public interface PsychologyDao {
    public void add(Psychology psychology);
    public List<Psychology> select(String id);
    public Page<Psychology> selectByCondition(String queryString);
}
