package com.zmbs.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zmbs.common.result.Result;
import com.zmbs.user.entity.RoleMenu;

import java.util.List;

/**
 * 角色菜单关联服务接口
 */
public interface RoleMenuService extends IService<RoleMenu> {

    /**
     * 分配角色菜单
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID列表
     * @return 操作结果
     */
    Result<Void> assignRoleMenus(Long roleId, List<Long> menuIds);

    /**
     * 删除角色的所有菜单
     *
     * @param roleId 角色ID
     * @return 操作结果
     */
    Result<Void> deleteRoleMenus(Long roleId);
} 