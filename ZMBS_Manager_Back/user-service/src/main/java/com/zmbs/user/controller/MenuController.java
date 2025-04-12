package com.zmbs.user.controller;

import com.zmbs.common.result.Result;
import com.zmbs.user.dto.MenuDTO;
import com.zmbs.user.service.MenuService;
import com.zmbs.user.vo.MenuVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单控制器
 */
@RestController
@RequestMapping("/api/menu")
@Tag(name = "菜单管理", description = "菜单相关接口")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 获取菜单树形结构
     */
    @GetMapping("/tree")
    @Operation(summary = "获取菜单树", description = "获取所有菜单的树形结构")
    public Result<List<MenuVO>> getMenuTree() {
        return menuService.getMenuTree();
    }

    /**
     * 获取当前用户的菜单
     */
    @GetMapping("/current")
    @Operation(summary = "获取当前用户菜单", description = "获取当前登录用户的菜单列表")
    public Result<List<MenuVO>> getCurrentUserMenus() {
        return menuService.getCurrentUserMenus();
    }

    /**
     * 添加菜单
     */
    @PostMapping
    @Operation(summary = "添加菜单", description = "添加新的菜单")
    public Result<Void> addMenu(@Validated @RequestBody MenuDTO menuDTO) {
        return menuService.addMenu(menuDTO);
    }

    /**
     * 修改菜单
     */
    @PutMapping
    @Operation(summary = "修改菜单", description = "修改已有的菜单")
    public Result<Void> updateMenu(@Validated @RequestBody MenuDTO menuDTO) {
        return menuService.updateMenu(menuDTO);
    }

    /**
     * 删除菜单
     */
    @DeleteMapping("/{menuId}")
    @Operation(summary = "删除菜单", description = "删除指定ID的菜单")
    public Result<Void> deleteMenu(@PathVariable("menuId") Long menuId) {
        return menuService.deleteMenu(menuId);
    }

    /**
     * 获取菜单详情
     */
    @GetMapping("/{menuId}")
    @Operation(summary = "获取菜单详情", description = "获取指定ID的菜单详情")
    public Result<MenuVO> getMenuById(@PathVariable("menuId") Long menuId) {
        return menuService.getMenuById(menuId);
    }

    /**
     * 获取角色菜单ID列表
     */
    @GetMapping("/role/{roleId}")
    @Operation(summary = "获取角色菜单ID列表", description = "获取指定角色拥有的所有菜单ID")
    public Result<List<Long>> getRoleMenuIds(@PathVariable("roleId") Long roleId) {
        return menuService.getRoleMenuIds(roleId);
    }

    /**
     * 获取角色菜单树
     */
    @GetMapping("/role/tree/{roleId}")
    @Operation(summary = "获取角色菜单树", description = "获取指定角色拥有的菜单树形结构")
    public Result<List<MenuVO>> getRoleMenuTree(@PathVariable("roleId") Long roleId) {
        return menuService.getRoleMenuTree(roleId);
    }
} 