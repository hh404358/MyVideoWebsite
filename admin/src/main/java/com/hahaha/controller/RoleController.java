package com.hahaha.controller;

import com.hahaha.entity.Role;
import com.hahaha.entity.dto.ChangeRoleStatusDto;
import com.hahaha.result.Result;
import com.hahaha.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 角色管理
 */
@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;


    /**
     * 查询角色列表
     * @param role
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('system:role:query')")//权限控制
    public Result list(@RequestBody Role role, Integer pageNum, Integer pageSize) {
        return Result.okResult(roleService.selectRolePage(role,pageNum,pageSize));
    }

    /**
     * 修改角色的状态
     * @param roleStatusDto
     * @return
     */

    @PutMapping("/changeStatus")
    public Result changeStatus(@RequestBody ChangeRoleStatusDto roleStatusDto){
        Role role = new Role();
        role.setId(roleStatusDto.getRoleId());
        role.setStatus(roleStatusDto.getStatus());
        return Result.okResult(roleService.updateById(role));
    }

    /**
     * 新增角色
     * @param role
     * @return
     */

    @PostMapping
    @PreAuthorize("@ps.hasPermission('system:role:add')")//权限控制
    public Result add( @RequestBody Role role) {
        roleService.insertRole(role);
        return Result.okResult();
    }

    /**
     * 根据角色id查询对应的角色
     * @param roleId
     * @return
     */

    @GetMapping( "/{roleId}")
    @PreAuthorize("@ps.hasPermission('system:role:query')")//权限控制
    public Result getInfo(@PathVariable Long roleId) {
        Role role = roleService.getById(roleId);
        return Result.okResult(role);
    }

    /**
     * 保存修改好的角色信息
     * @param role
     * @return
     */

    @PutMapping
    @PreAuthorize("@ps.hasPermission('system:role:edit')")//权限控制
    public Result edit(@RequestBody Role role) {
        roleService.updateRole(role);
        return Result.okResult();
    }

    /**
     * 删除角色
     * @param id
     * @return
     */

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('system:role:remove')")//权限控制
    public Result remove(@PathVariable(name = "id") Long id) {
        roleService.removeById(id);
        return Result.okResult();
    }

    

}