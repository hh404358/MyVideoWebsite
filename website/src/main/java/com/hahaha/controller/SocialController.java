package com.hahaha.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hahaha.entity.TFocus;
import com.hahaha.entity.User;
import com.hahaha.entity.vo.UserInfoVo;
import com.hahaha.enums.AppHttpCodeEnum;
import com.hahaha.exception.GlobalExceptionHandler;
import com.hahaha.exception.SystemException;
import com.hahaha.result.Result;
import com.hahaha.service.TFocusService;
import com.hahaha.service.UserService;
import com.hahaha.utils.SecurityUtils;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 社交模块管理
 */
@RestController
@RequestMapping("/user")
public class SocialController {
    @Autowired
    private UserService userService;
    @Autowired
    private TFocusService focusService;

    /**
     * 获取关注用户
     * @return
     */
    @GetMapping("/focus")
//    @PreAuthorize("hasAuthority('focus')")
    public Result focusList() {
        Long userId;
        try {
            userId = SecurityUtils.getUserId();
        } catch (Exception e) {
            return Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        List<UserInfoVo> users = focusService.getTFocus(userId);
        return Result.okResult(users);
    }

    /**
     * 获取粉丝列表
     * @return
     */
    @GetMapping("/fans")
    //@PreAuthorize("hasAuthority('fans')")
    public Result fanList( ) {
        Long userId;
        try {
            userId = SecurityUtils.getUserId();
        } catch (Exception e) {
            return Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        List<UserInfoVo> users = focusService.getFan(userId);
        return Result.okResult(users);
    }

    /**
     * 获取朋友列表
     * @return
     */
    @GetMapping("/friends")
    //@PreAuthorize("hasAuthority('friends')")
    public Result friendList( ) {
        Long userId;
        try {
            userId = SecurityUtils.getUserId();
        } catch (Exception e) {
            return Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        List<UserInfoVo> users = focusService.getFriends(userId);
        return Result.okResult(users);
    }

    /**
     * 新增关注
     * @param focusId
     * @return
     */
    @PostMapping("/addFocus")
    //@PreAuthorize("hasAuthority('addFocus')")
    public Result addTFocus(Long focusId){
        try{
            Long userId;
            try {
                userId = SecurityUtils.getUserId();
            } catch (Exception e) {
                return Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            }

            if(userId.equals(focusId)){
                return Result.errorResult(AppHttpCodeEnum.NO_SELF_FOCUS);
            }
            else if(isFocus(focusId)){
                return Result.errorResult(AppHttpCodeEnum.NO_REPEAT_FOCUS);
            }
            else{
                focusService.addTFocus(userId,focusId);
                return Result.okResult("新增关注成功：user"+userId+"关注了"+focusId);
            }
        }catch (SystemException e){
            return Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

    }

    /**
     * 取消关注
     * @param focusId
     * @return
     */
    @DeleteMapping("/cancelFocus")
    //@PreAuthorize("hasAuthority('cancelFocus')")
    public Result cancelTFocus(Long focusId) {
        Long userId;
        try {
            try {
                userId = SecurityUtils.getUserId();
            } catch (Exception e) {
                return Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            }

            if (isFocus(focusId)) {
                focusService.cancelTFocus(userId, focusId);
            }
        } catch (SystemException e) {
            return Result.errorResult(AppHttpCodeEnum.NO_FOCUS);
        }
        return Result.okResult("取消关注成功：user" + userId + "取消关注了user" + focusId);

    }

    public boolean isFocus(Long focusId) {
        Long userId;
        try {
            userId = SecurityUtils.getUserId();
        }catch (SystemException e){
            return false;
        }
        LambdaQueryWrapper<TFocus> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TFocus::getUserId, userId);
        queryWrapper.eq(TFocus::getFocusedId, focusId);
        if (focusService.getOne(queryWrapper) != null) {
            return true;
        } else {
            return false;
        }

    }




}
