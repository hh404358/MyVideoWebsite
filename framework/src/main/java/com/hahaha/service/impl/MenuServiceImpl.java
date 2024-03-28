package com.hahaha.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hahaha.constants.SystemConstants;
import com.hahaha.mapper.MenuMapper;
import org.springframework.stereotype.Service;
import com.hahaha.entity.Menu;
import com.hahaha.service.MenuService;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(SysMenu)表服务实现类
 *
 * @author hahaha
 * @since 2024-03-16 12:24:52
 */
@Service("MenuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    /**
     * 查询用户的权限信息
     * @param id
     * @return
     */
    @Override
    public List<String> selectPermsByUserId(Long id) {
        //根据用户id查询用户的权限信息。用户id为id代表管理员，如果是管理员就返回所有的权限
        if(id == 1L){
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            //查询条件是permissions中需要有所有菜单类型为C或者F的权限。
            wrapper.in(Menu::getMenuType, SystemConstants.TYPE_MENU, SystemConstants.TYPE_BUTTON);
            //查询条件是permissions中需要有状态为正常的权限。
            wrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            //查询条件是permissions中需要未被删除的权限的权限
            List<Menu> menus = list(wrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }

        //如果不是管理员就返回对应用户所具有的权限
        List<String> otherPerms = getBaseMapper().selectPermsByOtherUserId(id);
        return otherPerms;
    }

    //查询用户的权限菜单

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {

        MenuMapper menuMapper = getBaseMapper();

        List<Menu> menus = null;

        //判断是否是超级管理员，用户id为id代表超级管理员，如果是就返回所有符合要求的权限菜单
        if(userId.equals(1L)){
            menus = menuMapper.selectAllRouterMenu();
        }else{
            menus = menuMapper.selectOtherRouterMenuTreeByUserId(userId);
        }
         List<Menu> menuTree = buildMenuTree(menus,0L);

        return menuTree;
    }

    private List<Menu> buildMenuTree(List<Menu> menus, long parentId){
        List<Menu> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuTree;
    }
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream()
                //通过过滤得到子菜单
                .filter(m -> m.getParentId().equals(menu.getId()))
                //如果有三层菜单的话，也就是子菜单的子菜单，我们就用下面那行递归(自己调用自己)来处理
                .map(m -> m.setChildren(getChildren(m,menus)))
                .collect(Collectors.toList());
        return childrenList;
    }

    //查询菜单列表

    @Override
    public List<Menu> selectMenuList(Menu menu) {

        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        //menuName模糊查询
        queryWrapper.like(StringUtils.hasText(menu.getMenuName()),Menu::getMenuName,menu.getMenuName());
        queryWrapper.eq(StringUtils.hasText(menu.getStatus()),Menu::getStatus,menu.getStatus());
        //排序 parent_id和order_num
        queryWrapper.orderByAsc(Menu::getParentId,Menu::getOrderNum);
        List<Menu> menus = list(queryWrapper);
        return menus;
    }

    //删除菜单-是否存在子菜单
    @Override
    public boolean hasChild(Long menuId) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId,menuId);
        return count(queryWrapper) != 0;
    }

    @Override
    public List<Long> selectMenuListByRoleId(Long roleId) {
        return getBaseMapper().selectMenuListByRoleId(roleId);
    }

}
