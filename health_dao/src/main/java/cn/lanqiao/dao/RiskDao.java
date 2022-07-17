package cn.lanqiao.dao;

import cn.lanqiao.pojo.Constitution;
import cn.lanqiao.pojo.Risk;
import com.github.pagehelper.Page;

import java.util.List;

public interface RiskDao {
    public void add(Risk risk);
    public List<Risk> select(String id);
    public Page<Risk> selectByCondition(String queryString);

}
