package com.hahaha.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hahaha.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(SysMenu)表服务接口
 *
 * @author hahaha
 * @since 2024-03-16 12:24:52
 */
public interface MenuService extends IService<Menu> {

    //查询用户的权限信息
    List<String> selectPermsByUserId(Long id);
    //查询用户的路由信息，也就是查询权限菜单
    List<Menu> selectRouterMenuTreeByUserId(Long userId);
    //查询菜单列表
    List<Menu> selectMenuList(Menu menu);
    //删除菜单-判断是否存在子菜单
    boolean hasChild(Long menuId);

    List<Long> selectMenuListByRoleId(Long roleId);
}
