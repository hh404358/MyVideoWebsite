package com.hahaha.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hahaha.entity.Role;
import com.hahaha.entity.vo.PageVo;
import com.hahaha.result.Result;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 角色信息表(SysRole)表服务接口
 *
 * @author hahaha
 * @since 2024-03-16 11:37:44
 */
public interface RoleService extends IService<Role> {

    //查询用户的角色信息
    List<String> selectRoleKeyByUserId(Long id);

    //查询角色列表
    PageVo selectRolePage(Role role, Integer pageNum, Integer pageSize);

    @Transactional
    void insertRole(Role role);


    void updateRole(Role role);

    List<Role> selectRoleAll();

    //根据id查询用户信息
    List<Long> selectRoleIdByUserId(Long userId);
}
