package cn.lanqiao.service.impl;


import cn.lanqiao.dao.MemberMarDao;
import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.pojo.Member;
import cn.lanqiao.service.MemberMarService;
import cn.lanqiao.utils.MD5Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 崔云凯
 * @Date: 2021/05/22/11:11
 * @Description
 */


@Transactional
@Service
public class MemberMarServiceImpl implements MemberMarService {
    //注入Dao
    @Autowired
    private MemberMarDao memberMarDao;

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
        System.out.println("queryString = " + queryString);
        //分页
        PageHelper.startPage(currentPage, pageSize);
        Page<Member> memberPage = memberMarDao.findMemberByCondition(queryString);
        long total = memberPage.getTotal();
        List<Member> row = memberPage.getResult();
        return new PageResult(total, row);
    }

    /**
     * 根据id查找会员
     *
     * @param id
     * @return
     */
    @Override
    public Member findById(Integer id) {
        return memberMarDao.findById(id);
    }

    /**
     * 修改会员资料
     *
     * @param member
     */
    @Override
    public void edit(Member member) {
        memberMarDao.edit(member);
    }

    /**
     * 新增
     *
     * @param member
     */
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
        memberMarDao.add(member);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        memberMarDao.delete(id);
    }

    /**
     * 删除选中
     *
     * @param list
     */
    @Override
    public void deleteSelect(List<Integer> list) {
        for (Integer id : list) {
            memberMarDao.delete(id);
        }
    }

    /**
     * 根据id查找
     *
     * @param ids
     * @return
     */
    @Override
    public List<Member> findByIds(Integer[] ids) {
        List<Member> list = new ArrayList<>();
        for (Integer id : ids) {
            Member member = memberMarDao.findById(id);
            list.add(member);
        }
        return list;
    }

    /**
     * 查询所有
     *
     * @return
     */
    @Override
    public List<Member> findAll() {
        return memberMarDao.findAll();
    }
}