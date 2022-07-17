package cn.lanqiao.service.impl;

import cn.lanqiao.dao.MemberDao;
import cn.lanqiao.pojo.Member;
import cn.lanqiao.service.MemberService;
import cn.lanqiao.utils.MD5Utils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Hou
 * @Date: 2021/5/14 16:18
 * @Description:会员服务
 */
@Transactional
@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private JedisPool jedisPool;

    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    //保存会员信息
    @Override
    public void add(Member member) {
        String password = member.getPassword();
        if (password != null) {
            //使用md5将明文密码进行加密
            password = MD5Utils.md5(password);
            member.setPassword(password);
        } else {
            //使用md5将明文密码进行加密
            password = MD5Utils.md5("123456");
            member.setPassword(password);
        }
        memberDao.add(member);
    }

    //根据月份查询会员数量
    @Override
    public List<Integer> findMemberCountByMonth(List<String> months) {//2018.05
        List<Integer> memberCount = new ArrayList<>();
        for (String month : months) {
            String date = month + ".31";//2018.05.31
            Integer count = memberDao.findMemberCountBeforeDate(date);
            memberCount.add(count);
        }
        return memberCount;
    }


    @Override
    public List<Member> findMember() {
        return memberDao.findAll();
    }

    @Override
    public void edit(Member member) {
        memberDao.edit(member);
        //更新redis
        String telephone = member.getPhoneNumber();
        String json = JSON.toJSON(member).toString();
        jedisPool.getResource().setex(telephone, 60 * 30, json);
    }
}
