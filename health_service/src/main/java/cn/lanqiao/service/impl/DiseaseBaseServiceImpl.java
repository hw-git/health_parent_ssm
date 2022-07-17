package cn.lanqiao.service.impl;


import cn.lanqiao.dao.DiseaseBaseDao;
import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.pojo.DiseaseBase;
import cn.lanqiao.service.DiseaseBaseService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 疾病库业务
 */
@Transactional
@Service
public class DiseaseBaseServiceImpl implements DiseaseBaseService {
    //注入dao接口
    @Autowired
    private DiseaseBaseDao diseaseBaseDao;

    /**
     * 批量导入
     *
     * @param list
     */
    @Override
    public void importData(List<DiseaseBase> list) {
        //判断是否为空
        if (list != null) {
            for (DiseaseBase diseaseBase : list) {
                Long count = diseaseBaseDao.findContByName(diseaseBase.getDiseasename());
                if (count > 0) {
                    //记录存在，执行修改操作
                    diseaseBaseDao.editDiseaseByName(diseaseBase);
                } else {
                    //该路径不存在，执行添加操作
                    diseaseBaseDao.add(diseaseBase);
                }
            }
        }
    }

    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        //使用分页助手
        PageHelper.startPage(currentPage, pageSize);
        Page<DiseaseBase> diseaseBases = diseaseBaseDao.selectByCondition(queryString);

        //获取查询数据
        long total = diseaseBases.getTotal();
        List<DiseaseBase> result = diseaseBases.getResult();

        return new PageResult(total, result);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Override
    public DiseaseBase findDiseaseById(Integer id) {
        return diseaseBaseDao.findDiseaseById(id);
    }

    /**
     * 根据id修改
     *
     * @param diseaseBase
     */
    @Override
    public void edit(DiseaseBase diseaseBase) {
        diseaseBaseDao.edit(diseaseBase);
    }

    /**
     * 新增疾病库
     *
     * @param diseaseBase
     */
    @Override
    public void add(DiseaseBase diseaseBase) {
        diseaseBaseDao.add(diseaseBase);
    }

    /**
     * 根据id删除
     *
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
        diseaseBaseDao.deleteById(id);
    }

    /**
     * 查询全部
     *
     * @return
     */
    @Override
    public List<DiseaseBase> findAll() {
        return diseaseBaseDao.findAll();
    }

    @Override
    public Page<DiseaseBase> selectByCondition(String diseaseName) {
        return diseaseBaseDao.selectByCondition(diseaseName);
    }


}
