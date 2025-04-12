package com.zmbs.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zmbs.common.result.Result;
import com.zmbs.user.dto.MenuDTO;
import com.zmbs.user.entity.Menu;
import com.zmbs.user.vo.MenuVO;

import java.util.List;

/**
 * 菜单服务接口
 */
public interface MenuService extends IService<Menu> {

    /**
     * 获取菜单树形结构
     *
     * @return 菜单树
     */
    Result<List<MenuVO>> getMenuTree();

    /**
     * 获取当前用户的菜单
     *
     * @return 菜单列表
     */
    Result<List<MenuVO>> getCurrentUserMenus();

    /**
     * 添加菜单
     *
     * @param menuDTO 菜单数据
     * @return 添加结果
     */
    Result<Void> addMenu(MenuDTO menuDTO);

    /**
     * 修改菜单
     *
     * @param menuDTO 菜单数据
     * @return 修改结果
     */
    Result<Void> updateMenu(MenuDTO menuDTO);

    /**
     * 删除菜单
     *
     * @param menuId 菜单ID
     * @return 删除结果
     */
    Result<Void> deleteMenu(Long menuId);

    /**
     * 获取菜单详情
     *
     * @param menuId 菜单ID
     * @return 菜单详情
     */
    Result<MenuVO> getMenuById(Long menuId);

    /**
     * 获取角色菜单ID列表
     *
     * @param roleId 角色ID
     * @return 菜单ID列表
     */
    Result<List<Long>> getRoleMenuIds(Long roleId);

    /**
     * 获取角色菜单树
     *
     * @param roleId 角色ID
     * @return 菜单树
     */
    Result<List<MenuVO>> getRoleMenuTree(Long roleId);
} 