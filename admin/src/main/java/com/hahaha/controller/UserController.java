package com.hahaha.controller;

import com.hahaha.entity.Role;
import com.hahaha.entity.User;
import com.hahaha.entity.vo.UserInfoAndRoleIdsVo;
import com.hahaha.enums.AppHttpCodeEnum;
import com.hahaha.exception.SystemException;
import com.hahaha.result.Result;
import com.hahaha.service.RoleService;
import com.hahaha.service.UserService;
import com.hahaha.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户管理
 */
@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查询用户列表
     */
    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('system:user:list')")
    public Result list(User user, Integer pageNum, Integer pageSize) {
        return Result.okResult(userService.selectUserPage(user,pageNum,pageSize));
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @PostMapping
    @PreAuthorize("@ps.hasPermission('system:user:add')")
    public Result add(@RequestBody User user) {
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        if (!userService.checkUserNameUnique(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (!userService.checkPhoneUnique(user)){
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        if (!userService.checkEmailUnique(user)){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        userService.addUser(user);
        return Result.okResult();
    }

    /**
     * 删除用户
     * @param userIds
     * @return
     */

    @DeleteMapping("/{userIds}")
    @PreAuthorize("@ps.hasPermission('system:user:remove')")
    public Result remove(@PathVariable List<Long> userIds) {
        if(userIds.contains(SecurityUtils.getUserId())){
            return Result.errorResult(500,"不能删除当前你正在使用的用户");
        }
        userService.removeByIds(userIds);
        return Result.okResult();
    }

    /**
     * 根据id查询用户信息-
     */

    @Autowired
    private RoleService roleService;

    @GetMapping(value = { "/{userId}" })
    public Result getUserInfoAndRoleIds(@PathVariable(value = "userId") Long userId) {
        List<Role> roles = roleService.selectRoleAll();
        User user = userService.getById(userId);
        //当前用户所具有的角色id列表
        List<Long> roleIds = roleService.selectRoleIdByUserId(userId);

        UserInfoAndRoleIdsVo vo = new UserInfoAndRoleIdsVo(user,roles,roleIds);
        return Result.okResult(vo);
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */

    @PutMapping
    @PreAuthorize("@ps.hasPermission('system:user:edit')")

    public Result edit(@RequestBody User user) {
        userService.updateUser(user);
        return Result.okResult();
    }
}