package com.hahaha.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hahaha.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 菜单权限表(SysMenu)表数据库访问层
 *
 * @author hahaha
 * @since 2024-03-16 12:24:51
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByOtherUserId(Long id);

    List<Menu> selectAllRouterMenu();

    List<Menu> selectOtherRouterMenuTreeByUserId(Long userId);

    //修改角色-根据角色id查询对应角色菜单列表树
    List<Long> selectMenuListByRoleId(Long roleId);
}
