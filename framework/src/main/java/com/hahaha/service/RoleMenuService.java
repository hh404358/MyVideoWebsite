package com.hahaha.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hahaha.entity.RoleMenu;


/**
 * (SysRoleMenu)表服务接口
 *
 * @author makejava
 * @since 2024-03-24 15:47:05
 */
public interface RoleMenuService extends IService<RoleMenu> {

    void deleteRoleMenuByRoleId(Long id);
}
