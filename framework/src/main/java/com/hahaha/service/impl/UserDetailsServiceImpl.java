package com.hahaha.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hahaha.constants.SystemConstants;
import com.hahaha.entity.LoginUser;
import com.hahaha.mapper.MenuMapper;
import com.hahaha.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import com.hahaha.entity.User;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(queryWrapper);

         if(Objects.isNull(user)){
            throw new RuntimeException("用户不存在");//后期会对异常进行统一处理
        }
        // 如果是后台用户，才需要查询权限，也就是只对后台用户做权限校验
        if(user.getType().equals(SystemConstants.IS_ADMAIN)){
            //根据用户id查询权限关键字，即list是权限信息的集合
            List<String> list = menuMapper.selectPermsByOtherUserId(user.getId());
            return new LoginUser(user,list);
        }

       return new LoginUser(user,null);
    }
}
