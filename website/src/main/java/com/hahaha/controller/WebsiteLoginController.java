package com.hahaha.controller;

import com.hahaha.annotation.mySystemlog;
import com.hahaha.entity.User;
import com.hahaha.enums.AppHttpCodeEnum;
import com.hahaha.exception.SystemException;
import com.hahaha.result.Result;
import com.hahaha.service.WebsiteLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 网站用户登录管理
 */
@RestController
public class WebsiteLoginController {

    @Autowired
    private WebsiteLoginService websiteLoginService;

    /**
     * 登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    @mySystemlog(xxbusinessName = "登录")
    public Result login(@RequestBody User user){

        //如果用户在进行登录时，没有传入'用户名'
        if(!StringUtils.hasText(user.getUserName())){
            // 提示'必须要传用户名'。
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }

        return Result.okResult(websiteLoginService.login(user));
    }

    /**
     * 退出
     * @return
     */
    @PostMapping("/logout")
    @mySystemlog(xxbusinessName = "退出")
    public Result logout(){
        websiteLoginService.logout();
        return Result.okResult();
    }

    /**
     * 注册
     * @param user
     * @return
     */

    @PostMapping("/register")
    @mySystemlog(xxbusinessName = "注册")
    public Result register(@RequestBody User user){
        websiteLoginService.register(user);
        return Result.okResult();
    }


}