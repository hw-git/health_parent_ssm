package cn.lanqiao.service;

import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.pojo.Member;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 崔云凯
 * @Date: 2021/05/22/11:10
 * @Description
 */
/**
 * 会员管理服务接口
 */
public interface MemberMarService {
    /**
     * 分页
     * @param queryPageBean
     * @return
     */
    public PageResult pageQuery(QueryPageBean queryPageBean);

    /**
     * 编辑表单数据回显
     *
     * @param id
     * @return
     */
    public Member findById(Integer id);


    /**
     * 修改
     * @param member
     */
    public void edit(Member member);

    /**
     * 新增
     * @param member
     */
    public void add(Member member);

    /**
     * 删除
     * @param id
     */
    public void delete(Integer id);

    /**
     * 删除选中
     * @param list
     */
    public void deleteSelect(List<Integer> list);

    /**
     * 通过id查找
     * @param ids
     * @return
     */
    public List<Member> findByIds(Integer[] ids);

    /**
     * 查询所有
     * @return
     */
    public List<Member> findAll();
}
