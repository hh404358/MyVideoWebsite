package com.hahaha.filter;


import com.alibaba.fastjson.JSON;
import com.hahaha.entity.LoginUser;
import com.hahaha.enums.AppHttpCodeEnum;
import com.hahaha.result.Result;
import com.hahaha.utils.JwtUtils;
import com.hahaha.utils.RedisCache;
import com.hahaha.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 登录认证过滤器
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        //不需要登录
        if(!StringUtils.hasText(token)){
            filterChain.doFilter(request,response);
            return;
        }

        Claims claims = null;
        try {
            claims = JwtUtils.parseJWT(token);
        } catch (Exception e) {
            //当token过期或token被篡改就会进入下面那行的异常处理
            e.printStackTrace();
            //报异常之后，把异常响应给前端，需要重新登录。
            Result result = Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        String userid = claims.getSubject();

        //在redis中，通过key来获取value
        LoginUser loginUser = redisCache.getCacheObject("websitelogin:" + userid);
        //如果在redis获取不到值，说明登录是过期了，需要重新登录
        if(Objects.isNull(loginUser)){
            Result result = Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }

        //把从redis获取到的value，存入到SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request,response);
    }
}
