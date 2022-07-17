package cn.lanqiao.service.impl;

import cn.lanqiao.dao.SportBaseDao;
import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.pojo.SportBase;
import cn.lanqiao.service.SportBaseService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 食品知识库业务
 */
@Transactional
@Service
public class SportBaseServiceImpl implements SportBaseService {
    //注入sportbaseDao
    @Autowired
    private SportBaseDao sportBaseDao;

    /**
     * poi批量导入
     *
     * @param list
     */
    @Override
    public void importData(List<SportBase> list) {
        //判断是否为空
        if (list != null) {
            for (SportBase sportBase : list) {
                //查询数据库判断该数据是否存在(防止数据重复)
                Long count = sportBaseDao.findCountByName(sportBase.getSportname());
                if (count > 0) {
                    //该记录存在，执行修改操作
                    sportBaseDao.editSportByName(sportBase);
                } else {
                    //该剧路不存在，执行新增操作
                    sportBaseDao.add(sportBase);
                }
            }
        }
    }

    /**
     * 分页
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
        Page<SportBase> sportBases = sportBaseDao.selectByCondition(queryString);

        //获取查询数据
        long total = sportBases.getTotal();
        List<SportBase> result = sportBases.getResult();

        return new PageResult(total, result);
    }

    /**
     * 通过id查找
     *
     * @param id
     * @return
     */
    @Override
    public SportBase findSportById(Integer id) {
        return sportBaseDao.findSportById(id);
    }

    /**
     * 修改数据
     *
     * @param sportBase
     */
    @Override
    public void edit(SportBase sportBase) {
        sportBaseDao.edit(sportBase);
    }

    /**
     * 添加数据
     *
     * @param sportBase
     */
    @Override
    public void add(SportBase sportBase) {
        sportBaseDao.add(sportBase);
    }

    /**
     * 通过id删除数据
     *
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
        sportBaseDao.deleteById(id);
    }

    @Override
    public List<SportBase> findAll() {
        return sportBaseDao.findAll();
    }
}
