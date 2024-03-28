package com.hahaha.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hahaha.entity.User;
import com.hahaha.entity.UserRole;
import com.hahaha.entity.vo.PageVo;
import com.hahaha.entity.vo.UserInfoVo;
import com.hahaha.entity.vo.UserVo;
import com.hahaha.enums.AppHttpCodeEnum;
import com.hahaha.exception.SystemException;
import com.hahaha.mapper.UserMapper;
import com.hahaha.service.UserRoleService;
import com.hahaha.service.UserService;
import com.hahaha.utils.BeanCopyUtils;
import com.hahaha.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表(SysUser)表服务实现类
 *
 * @author hahaha
 * @since 2024-03-15 15:28:42
 */
@Service("UserService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public UserInfoVo userInfo() {
        Long userId = SecurityUtils.getUserId();
        User user = getById(userId);
        UserInfoVo vo = BeanCopyUtils.copyBean(user,UserInfoVo.class);
        return vo;
    }

    @Override
    public void updateUserInfo(User user) {
        updateById(user);

    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void register(User user) {

        //对前端传过来的用户名进行非空判断
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

        //判断用户传给我们的用户名是否在数据库已经存在。userNameExist方法是下面定义的
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        //判断用户传给我们的昵称是否在数据库已经存在
        if(NickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        //判断用户传给我们的邮箱是否在数据库已经存在
        if(EmailExist(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        String encodePassword = passwordEncoder.encode(user.getPassword());//加密
        user.setPassword(encodePassword);

        save(user);

    }

    //'判断用户传给我们的用户名是否在数据库已经存在' 的方法
    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        boolean result = count(queryWrapper) > 0;
        return result;
    }

    //'判断用户传给我们的昵称是否在数据库已经存在' 的方法
    private boolean NickNameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName,nickName);
        boolean result = count(queryWrapper) > 0;
        return result;
    }

    //'判断用户传给我们的邮箱是否在数据库已经存在' 的方法
    private boolean EmailExist(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail,email);
        boolean result = count(queryWrapper) > 0;
        return result;
    }


    //查询用户列表

    @Override
    public PageVo selectUserPage(User user, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();

        queryWrapper.like(StringUtils.hasText(user.getUserName()),User::getUserName,user.getUserName());
        queryWrapper.eq(StringUtils.hasText(user.getStatus()),User::getStatus,user.getStatus());
        queryWrapper.eq(StringUtils.hasText(user.getPhonenumber()),User::getPhonenumber,user.getPhonenumber());

        Page<User> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);

        //转换成VO
        List<User> users = page.getRecords();
        List<UserVo> userVoList = users.stream()
                .map(u -> BeanCopyUtils.copyBean(u, UserVo.class))
                .collect(Collectors.toList());
        PageVo pageVo = new PageVo();
        pageVo.setTotal(page.getTotal());
        pageVo.setRows(userVoList);
        return pageVo;
    }

    //新增用户

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public boolean checkUserNameUnique(String userName) {
        return count(Wrappers.<User>lambdaQuery().eq(User::getUserName,userName))==0;
    }

    @Override
    public boolean checkPhoneUnique(User user) {
        return count(Wrappers.<User>lambdaQuery().eq(User::getPhonenumber,user.getPhonenumber()))==0;
    }

    @Override
    public boolean checkEmailUnique(User user) {
        return count(Wrappers.<User>lambdaQuery().eq(User::getEmail,user.getEmail()))==0;
    }

    @Override
    @Transactional
    public void addUser(User user) {
        //密码加密处理
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);

        if(user.getRoleIds()!=null&&user.getRoleIds().length>0){
            insertUserRole(user);
        }

    }

    private void insertUserRole(User user) {
        List<UserRole> sysUserRoles = Arrays.stream(user.getRoleIds())
                .map(roleId -> new UserRole(user.getId(), roleId)).collect(Collectors.toList());
        userRoleService.saveBatch(sysUserRoles);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        // 删除用户与角色关联
        LambdaQueryWrapper<UserRole> userRoleUpdateWrapper = new LambdaQueryWrapper<>();
        userRoleUpdateWrapper.eq(UserRole::getUserId,user.getId());
        userRoleService.remove(userRoleUpdateWrapper);

        // 新增用户与角色管理
        insertUserRole(user);
        // 更新用户信息
        updateById(user);
    }
}
