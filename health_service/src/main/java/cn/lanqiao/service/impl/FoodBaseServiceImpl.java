package cn.lanqiao.service.impl;

import cn.lanqiao.dao.FoodBaseDao;
import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.pojo.FoodBase;
import cn.lanqiao.service.FoodBaseService;
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
public class FoodBaseServiceImpl implements FoodBaseService {
    //注入Dao接口
    @Autowired
    private FoodBaseDao foodBaseDao;

    /**
     * poi批量导入
     *
     * @param list
     */
    @Override
    public void importData(List<FoodBase> list) {
        //判断是否为空
        if (list != null) {
            for (FoodBase foodBase : list) {
                //查询数据库判断该数据是否存在(防止数据重复)
                Long count = foodBaseDao.findCountByName(foodBase.getFoodname());
                if (count > 0) {
                    //该记录存在，执行修改操作
                    foodBaseDao.editFoodByName(foodBase);
                } else {
                    //该剧路不存在，执行新增操作
                    foodBaseDao.add(foodBase);
                }
            }
        }
    }

    /**
     * 分页查询
     *
     * @param queryPageBean
     */
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        //使用分页助手
        PageHelper.startPage(currentPage, pageSize);
        Page<FoodBase> foodBases = foodBaseDao.selectByCondition(queryString);

        //获取查询数据
        long total = foodBases.getTotal();
        List<FoodBase> result = foodBases.getResult();

        return new PageResult(total, result);
    }

    /**
     * 根据id查询
     */
    @Override
    public FoodBase findFoodById(Integer foodid) {
        return foodBaseDao.findFoodById(foodid);
    }

    /**
     * 根据id修改food数据
     */
    @Override
    public void edit(FoodBase foodBase) {
        foodBaseDao.edit(foodBase);
    }

    /**
     * 新增食品
     */
    @Override
    public void add(FoodBase foodBase) {
        foodBaseDao.add(foodBase);
    }

    /**
     * 根据id删除
     */
    @Override
    public void deleteById(Integer id) {
        foodBaseDao.deleteById(id);
    }

    /**
     * 查询全部
     */
    @Override
    public List<FoodBase> findAll() {
        return foodBaseDao.findAll();
    }


}
