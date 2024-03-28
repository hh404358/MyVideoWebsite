package com.hahaha.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hahaha.entity.RoleMenu;
import com.hahaha.mapper.RoleMenuMapper;
import com.hahaha.service.RoleMenuService;
import org.springframework.stereotype.Service;

/**
 * (SysRoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2024-03-24 15:47:05
 */
@Service("sysRoleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {
    @Override
    public void deleteRoleMenuByRoleId(Long id) {
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId,id);
        remove(queryWrapper);
    }
}
