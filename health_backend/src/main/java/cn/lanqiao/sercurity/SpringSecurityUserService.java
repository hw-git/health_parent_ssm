package cn.lanqiao.sercurity;

import cn.lanqiao.pojo.Permission;
import cn.lanqiao.pojo.Role;
import cn.lanqiao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author: Hou
 * @Date: 2021/5/25 09:22
 * @Description:
 */
@Component
public class SpringSecurityUserService implements UserDetailsService {

    @Autowired //注意：此处要通过dubbo远程调用用户服务
    private UserService userService;

    //根据用户名查询用户信息
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //远程调用用户服务，根据用户名查询用户信息
        cn.lanqiao.pojo.User user = null;
        try {
            user = userService.findByUsername(username);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (user == null) {
            //用户名不存在
            throw new UsernameNotFoundException(username + "不存在");
        }

        List<GrantedAuthority> list = new ArrayList<>();
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            //授予角色
            list.add(new SimpleGrantedAuthority(role.getKeyword()));
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                //授权
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }
        UserDetails userDetails = null;
        if ("1".equals(user.getStation())) {
            userDetails = new User(username, user.getPassword(), true, true, true, true, list);
        } else {
            userDetails = new User(username, user.getPassword(), false, true, true, true, list);
        }
        return userDetails;
    }
}