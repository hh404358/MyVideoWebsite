package com.hahaha.controller;

import com.hahaha.entity.LoginUser;
import com.hahaha.entity.Menu;
import com.hahaha.entity.vo.AdminUserInfoVo;
import com.hahaha.entity.vo.RoutersVo;
import com.hahaha.entity.vo.UserInfoVo;
import com.hahaha.enums.AppHttpCodeEnum;
import com.hahaha.exception.SystemException;
import com.hahaha.result.Result;
import com.hahaha.service.MenuService;
import com.hahaha.service.RoleService;
import com.hahaha.service.SystemLoginService;
import com.hahaha.utils.BeanCopyUtils;
import com.hahaha.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.hahaha.entity.User;

import java.util.List;

/**
 * 登录管理
 */
@RestController
public class LoginController {
    @Autowired
    private SystemLoginService systemLoginService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;

    /**
     * 登录
     * @param user
     * @return
     */
    @PostMapping("/admin/login")
    public Result login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return Result.okResult(systemLoginService.login(user));
    }

    /**
     * 查询路由信息(权限菜单)
     * @return
     */

    @GetMapping("/getRouters")
 
    public Result<RoutersVo> getRouters(){
        //获取用户id
        Long userId = SecurityUtils.getUserId();

        //根据用户id来查询menu(权限菜单)。要求查询结果是tree的形式，也就是子父菜单树
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        //封装响应返回
        return Result.okResult(new RoutersVo(menus));
    }

    /**
     * 查询(超级管理员|非超级管理员)的权限和角色信息
     * @return
     */

    @GetMapping("/getInfo")
    public Result<AdminUserInfoVo> getInfo(){

        LoginUser loginUser = SecurityUtils.getLoginUser();

        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());

        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());


        User user = loginUser.getUser();

        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        //封装响应返回
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms,roleKeyList,userInfoVo);
        return Result.okResult(adminUserInfoVo);
    }

    /**
     * 退出
     * @return
     */


    @PostMapping("/admin/logout")
    public Result logout(){
        systemLoginService.logout();
        return Result.okResult();
    }
}
