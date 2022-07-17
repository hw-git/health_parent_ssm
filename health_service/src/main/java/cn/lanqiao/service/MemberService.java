package cn.lanqiao.service;

import cn.lanqiao.pojo.Member;

import java.util.List;


public interface MemberService {
    //根据手机号查询会员
    public Member findByTelephone(String telephone);
    //增加会员
    public void add(Member member);
    //根据月份查询
    public List<Integer> findMemberCountByMonth(List<String> months);
    //列表
    List<Member> findMember();
    //修改
    void edit(Member member);


}
