package com.hahaha.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hahaha.entity.LoginUser;
import com.hahaha.entity.User;
import com.hahaha.entity.vo.WebsiteUserLoginVo;
import com.hahaha.entity.vo.UserInfoVo;
import com.hahaha.enums.AppHttpCodeEnum;
import com.hahaha.exception.SystemException;
import com.hahaha.mapper.UserMapper;
import com.hahaha.service.WebsiteLoginService;
import com.hahaha.utils.BeanCopyUtils;
import com.hahaha.utils.JwtUtils;
import com.hahaha.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;


@Service
public class WebsiteLoginServiceImpl implements WebsiteLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserMapper userMapper;
    @Override
    public WebsiteUserLoginVo login(User user) {
        //封装登录的用户名和密码
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        //认证
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断用户认证是否通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException(AppHttpCodeEnum.LOGIN_ERROR.getMsg());
        }
        //获取userid
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        //把这个userid转成密文(token值)
        String jwt = JwtUtils.createJWT(userId);

        //下token值保存到Redis。
        redisCache.setCacheObject("websitelogin:"+userId,loginUser);


        //把User转化为UserInfoVo，再放入vo对象的第二个参数
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        return  new WebsiteUserLoginVo(jwt,userInfoVo);

    }

    @Override
    public void logout() {

        //获取token，然后解析token值获取其中的userid
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //LoginUser是我们写的类
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        //获取userid
        Long userid = loginUser.getUser().getId();

        //在redis根据key来删除用户的value值
        redisCache.deleteObject("websitelogin:"+userid);

    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 用户注册功能
     * @param user
     */
    @Override
    public void register(User user) {
        //非空判断：null、""，就抛出异常
        //对前端传过来的用户名
        if(!StringUtils.hasText(user.getUserName())){
              
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        //密码
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        //邮箱
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        //昵称
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //手机号码
        if(!StringUtils.hasText(user.getPhonenumber())){
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_NOT_NULL);
        }

        //存在性检验
        //判断用户传给我们的用户名
        if(userNameExist(user.getUserName())){
              
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        //判断用户传给我们的昵称是否在数据库已经存在。 
        if(NickNameExist(user.getNickName())){
              
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        //判断用户传给我们的邮箱是否在数据库已经存在。 
        if(EmailExist(user.getEmail())){
              
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        //判断用户传给我们的手机号码是否在数据库已经存在。
        if(PhonenumberExist(user.getPhonenumber())){
              
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        //加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);

        userMapper.insert(user);

    }

    /**
     * 判断用户传给我们的用户名是否在数据库已经存在' 的方法
     * @param userName
     * @return
     */
    private boolean userNameExist(String userName) {
        try {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getUserName, userName);
            return userMapper.selectCount(queryWrapper) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * '判断用户传给我们的昵称是否在数据库已经存在' 的方法
     * @param nickName
     * @return
     */
    private boolean NickNameExist(String nickName) {
        try {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getNickName, nickName);
            return userMapper.selectCount(queryWrapper) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * '判断用户传给我们的邮箱是否在数据库已经存在' 的方法
     * @param email
     * @return
     */
    private boolean EmailExist(String email) {
        try {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getEmail, email);
            return userMapper.selectCount(queryWrapper) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * '判断用户传给我们的手机号码是否在数据库已经存在' 的方法
     * @param phonenumber
     * @return
     */
    private boolean PhonenumberExist(String phonenumber) {
        try {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhonenumber, phonenumber);
            return userMapper.selectCount(queryWrapper) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}