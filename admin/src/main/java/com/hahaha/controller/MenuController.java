package com.hahaha.controller;

import com.hahaha.entity.Menu;
import com.hahaha.entity.vo.MenuVo;
import com.hahaha.result.Result;
import com.hahaha.service.MenuService;
import com.hahaha.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜單管理
 */
@RequestMapping("/system/menu")
@RestController
public class MenuController {
    @Autowired
    private MenuService menuService;

    /**
     * 查询菜单列表
     * @param menu
     * @return
     */

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('system:menu:query')")
    public Result list(@RequestBody Menu menu) {
        List<Menu> menus = menuService.selectMenuList(menu);
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menus, MenuVo.class);
        return Result.okResult(menuVos);
    }

    /**
     * 新增菜单
     * @param menu
     * @return
     */
    @PostMapping
    @PreAuthorize("@ps.hasPermission('system:menu:add')")
    public Result add(@RequestBody Menu menu) {
        menuService.save(menu);
        return Result.okResult();
    }

    /**
     * 根據id獲取菜單信息
     * @param menuId
     * @return
     */

    @GetMapping("/{menuId}")
    @PreAuthorize("@ps.hasPermission('system:menu:query')")
    public Result getInfo(@PathVariable Long menuId) {
        return Result.okResult(menuService.getById(menuId));
    }

    /**
     * 修改菜单
     * @param menu
     * @return
     */
    @PutMapping
    @PreAuthorize("@ps.hasPermission('system:menu:edit')")
    public Result edit(@RequestBody Menu menu) {
        if (menu.getId().equals(menu.getParentId())) {
            return Result.errorResult(500,"修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        menuService.updateById(menu);
        return Result.okResult();
    }

    /**
     * 删除菜单
     * @param menuId
     * @return
     */

    @DeleteMapping("/{menuId}")
    @PreAuthorize("@ps.hasPermission('system:menu:remove')")
    public Result remove(@PathVariable("menuId") Long menuId) {
        if (menuService.hasChild(menuId)) {
            return Result.errorResult(500,"存在子菜单不允许删除");
        }
        menuService.removeById(menuId);
        return Result.okResult();
    }
}
