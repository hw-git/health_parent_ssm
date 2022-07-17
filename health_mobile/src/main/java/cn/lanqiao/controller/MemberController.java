package cn.lanqiao.controller;

import cn.lanqiao.constant.MobileConstant;
import cn.lanqiao.constant.RedisMessageConstant;
import cn.lanqiao.entity.Result;
import cn.lanqiao.pojo.Member;
import cn.lanqiao.service.MemberService;
import cn.lanqiao.utils.DateUtils;
import cn.lanqiao.utils.MD5Utils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @Author: Hou
 * @Date: 2021/5/14 15:38
 * @Description:
 */
@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private MemberService memberService;

    //手机号快速登录
    @RequestMapping("/telephoneLogin")
    public Result telephoneLogin(HttpServletResponse response, @RequestBody Map map) {
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");
        //从Redis中获取保存的验证码
        String validateCodeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        if (validateCodeInRedis != null && validateCode != null && validateCode.equals(validateCodeInRedis)) {
            //验证码输入正确
            //判断当前用户是否为会员（查询会员表来确定）
            Member member = memberService.findByTelephone(telephone);
            if (member == null) {
                member = new Member();
                //不是会员，自动完成注册（自动将当前用户信息保存到会员表）
                member.setRegTime(new Date());
                member.setPhoneNumber(telephone);
                memberService.add(member);
            }
            //向客户端浏览器写入Cookie，内容为手机号
            Cookie cookie = new Cookie("login_member_telephone", telephone);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24 * 30);
            response.addCookie(cookie);
            //将会员信息保存到Redis
            String json = JSON.toJSON(member).toString();
            jedisPool.getResource().setex(telephone, 60 * 30, json);
            return new Result(true, MobileConstant.LOGIN_SUCCESS);
        } else {
            //验证码输入错误
            return new Result(false, MobileConstant.VALIDATECODE_ERROR);
        }
    }

    //登录
    @RequestMapping("/login")
    public Result login(String telephone, String password) {
//        String telephone = (String) map.get("telephone");
//        String password = (String) map.get("password");
        Member member = memberService.findByTelephone(telephone);
        System.out.println("------------"+member);
        if (member == null) {
            return new Result(false, MobileConstant.TELEPHONE_UNREGISTERED);
        }
        password = MD5Utils.md5(password);
        if (password.equals(member.getPassword())) {
            return new Result(true, MobileConstant.LOGIN_SUCCESS, member);
        } else {
            return new Result(false, MobileConstant.LOGIN_FAIL);
        }
    }

    //注册
    @RequestMapping("/registered")
    public Result registered(HttpServletResponse response, @RequestBody Member m) {
        Member member = memberService.findByTelephone(m.getPhoneNumber());
        if (member != null) {
            return new Result(false, MobileConstant.TELEPHONE_REGISTERED);
        }
        m.setRegTime(new Date());
        String idCard = m.getIdCard();
        Map map = DateUtils.getBirAgeSex(idCard);
        m.setAge(Integer.parseInt((String) map.get("age")));
        m.setSex((String) map.get("sexCode"));
        Date bir = null;
        try {
            bir = DateUtils.parseString2Date((String) map.get("birthday"), "yyyy-MM-dd");
        } catch (Exception e) {
            e.printStackTrace();
        }
        m.setBirthday(bir);
        try {
            memberService.add(m);
            return new Result(true, MobileConstant.REGISTERED_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MobileConstant.REGISTERED_FAIL);
        }

    }
}
