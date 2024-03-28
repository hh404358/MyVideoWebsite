package com.hahaha.service.impl;

import com.hahaha.entity.LoginUser;
import com.hahaha.entity.User;
import com.hahaha.service.SystemLoginService;
import com.hahaha.utils.JwtUtils;
import com.hahaha.utils.RedisCache;
import com.hahaha.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class SystemLoginServiceImpl implements SystemLoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public Map<String,String> login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userid
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtils.createJWT(userId);
        redisCache.setCacheObject("adminlogin:"+userId,loginUser);


        //把token封装 返回
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return  map;
    }
    @Override
    public void logout() {

        Long userId = SecurityUtils.getUserId();

        //删除redis中对应的值
        redisCache.deleteObject("adminlogin:" + userId);

    }
}
