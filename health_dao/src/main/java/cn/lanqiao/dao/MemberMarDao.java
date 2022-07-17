package cn.lanqiao.dao;

import cn.lanqiao.pojo.Member;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 崔云凯
 * @Date: 2021/05/22/10:36
 * @Description
 */
public interface MemberMarDao {
    /**
     * 条件查询
     * @param queryString
     * @return
     */
    public Page<Member> findMemberByCondition(String queryString);

    /**
     * 根据id查找会员
     * @param id
     * @return
     */
    public Member findById(Integer id);

    /**
     * 修改会员
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
     * 查询所有
     * @return
     */
    public List<Member> findAll();

}
