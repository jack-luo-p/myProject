package com.zmbs.user.controller;

import com.zmbs.common.result.Result;
import com.zmbs.user.service.RoleMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色菜单关联控制器
 */
@RestController
@RequestMapping("/api/role-menu")
@Tag(name = "角色菜单管理", description = "角色菜单关联接口")
public class RoleMenuController {

    @Autowired
    private RoleMenuService roleMenuService;

    /**
     * 分配角色菜单
     */
    @PostMapping("/assign/{roleId}")
    @Operation(summary = "分配角色菜单", description = "给指定角色分配菜单权限")
    public Result<Void> assignRoleMenus(@PathVariable("roleId") Long roleId, @RequestBody List<Long> menuIds) {
        return roleMenuService.assignRoleMenus(roleId, menuIds);
    }

    /**
     * 删除角色菜单
     */
    @DeleteMapping("/{roleId}")
    @Operation(summary = "删除角色菜单", description = "删除指定角色的所有菜单权限")
    public Result<Void> deleteRoleMenus(@PathVariable("roleId") Long roleId) {
        return roleMenuService.deleteRoleMenus(roleId);
    }
} 