package com.hahaha.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hahaha.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 角色信息表(SysRole)表数据库访问层
 *
 * @author hahaha
 * @since 2024-03-16 11:37:44
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    //查询用户角色信息
    List<String> selectRoleKeyByOtherUserId(Long id);

    //根据id查询用户信息
    List<Long> selectRoleIdByUserId(Long userId);
}
