package cn.lanqiao.dao;

import cn.lanqiao.pojo.Member;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Hou
 * @Date: 2021/5/14 16:19
 * @Description:
 */
public interface MemberDao {
    public List<Member> findAll();

    public Page<Member> selectByCondition(String queryString);

    public void add(Member member);

    public void deleteById(Integer id);

    public Member findById(Integer id);

    public Member findByTelephone(String telephone);

    public void edit(Member member);

    public Integer findMemberCountBeforeDate(String date);

    public Integer findMemberCountByDate(String date);

    public Integer findMemberCountAfterDate(String date);

    public Integer findMemberTotalCount();

    Member getMemberByIdcard(String idCard);

    //根据日期范围查询会员Id
    //Date startDate, Date endDate
    List<Integer> findMemberId4Date(@Param("startTime") String startTime, @Param("endTime") String endTime);

}
