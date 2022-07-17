package cn.lanqiao.dao;


import cn.lanqiao.pojo.DiseaseBase;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * 疾病库
 */
public interface DiseaseBaseDao {
    /**
     * 添加一条疾病记录
     *
     * @param diseaseBase
     */
    public void add(DiseaseBase diseaseBase);

    /**
     * 根据疾病名称修改记录
     *
     * @param diseaseBase
     */
    public void editDiseaseByName(DiseaseBase diseaseBase);

    /**
     * 按名称查找
     *
     * @param diseasename
     * @return
     */
    public Long findContByName(String diseasename);

    /**
     * 根据条件查询
     *
     * @param queryString
     * @return
     */
    public Page<DiseaseBase> selectByCondition(String queryString);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    public DiseaseBase findDiseaseById(Integer id);

    /**
     * 根据id修改
     *
     * @param diseaseBase
     */
    public void edit(DiseaseBase diseaseBase);

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
    public List<DiseaseBase> findAll();

}
