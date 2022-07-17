package cn.lanqiao.dao;

import cn.lanqiao.pojo.Constitution;
import com.github.pagehelper.Page;


import java.util.List;

public interface ConstitutionDao {
    public void add(Constitution constitution);
    public List<Constitution> select(String id);
    public Page<Constitution> selectByCondition(String queryString);

}
