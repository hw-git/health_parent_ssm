package cn.lanqiao.service;


import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.pojo.DiseaseBase;
import com.github.pagehelper.Page;

import java.util.List;

public interface DiseaseBaseService {
    /**
     * poi批量导入
     *
     * @param list
     */
    public void importData(List<DiseaseBase> list);

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
    public DiseaseBase findDiseaseById(Integer id);

    /**
     * 修改food数据
     *
     * @param diseaseBase
     */
    public void edit(DiseaseBase diseaseBase);

    /**
     * 新增
     *
     * @param diseaseBase
     */
    public void add(DiseaseBase diseaseBase);

    /**
     * 根据id删除
     *
     * @param id
     */
    public void deleteById(Integer id);

    /**
     * 查询所有
     */
    public List<DiseaseBase> findAll();

    public Page<DiseaseBase> selectByCondition(String queryString);


}
