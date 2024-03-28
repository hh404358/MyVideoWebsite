package com.hahaha.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hahaha.annotation.mySystemlog;
import com.hahaha.entity.User;
import com.hahaha.enums.AppHttpCodeEnum;
import com.hahaha.result.Result;
import com.hahaha.service.UserService;
import com.hahaha.utils.AliOSSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 用户管理
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    @mySystemlog(xxbusinessName = "查询个人信息")//接口描述，用于'日志记录'功能
    public Result userInfo(){
        return Result.okResult(userService.userInfo());
    }

    @PutMapping("/userInfo")
    @mySystemlog(xxbusinessName = "更新个人信息")//接口描述，用于'日志记录'功能
    public Result updateUserINfo(@RequestBody User user){
        userService.updateUserInfo(user);
        return Result.okResult();
    }

    /**
     * 获取用户头像
     */
    @GetMapping("/getAvatar")
    public Result getAvatar( Long userId){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId,userId);
        User user = userService.getOne(queryWrapper);
        System.out.println("getAvartar userId:"+userId);
        if(user !=null){
            return Result.okResult(userService.getById(userId).getAvatar());

        }
        else{
            return Result.errorResult(AppHttpCodeEnum.AVATAR_GET_ERROR);
        }
    }

    @Autowired
    private AliOSSUtils aliOSSUtils;
    @PostMapping("/uploadAvatar")
    public Result uploadVideo(MultipartFile file) throws IOException {
        String url = aliOSSUtils.uploadImgae(file);
        //log.info(aliOSSUtils.getAliOSSProperties().getAccessKeyId());
        return Result.okResult(url);
    }


}
