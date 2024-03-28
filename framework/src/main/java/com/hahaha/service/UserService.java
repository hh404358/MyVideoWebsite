package com.hahaha.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hahaha.entity.User;
import com.hahaha.entity.vo.PageVo;
import com.hahaha.entity.vo.UserInfoVo;
import com.hahaha.result.Result;
import org.springframework.transaction.annotation.Transactional;


/**
 * 用户表(SysUser)表服务接口
 *
 * @author hahaha
 * @since 2024-03-15 15:28:42
 */
public interface UserService extends IService<User> {


    UserInfoVo userInfo();

    void updateUserInfo(User user);

    void register(User user);

    PageVo selectUserPage(User user, Integer pageNum, Integer pageSize);

    boolean checkUserNameUnique(String userName);

    boolean checkPhoneUnique(User user);

    boolean checkEmailUnique(User user);

    @Transactional
    void addUser(User user);

    @Transactional
    void updateUser(User user);
}
