package com.zmbs.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zmbs.common.result.Result;
import com.zmbs.user.entity.RoleMenu;
import com.zmbs.user.mapper.RoleMenuMapper;
import com.zmbs.user.service.RoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色菜单关联服务实现类
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> assignRoleMenus(Long roleId, List<Long> menuIds) {
        if (roleId == null) {
            return Result.failed("角色ID不能为空");
        }

        // 先删除原有的角色菜单关联
        deleteRoleMenus(roleId);

        // 如果菜单ID列表为空，则只进行删除操作
        if (CollectionUtils.isEmpty(menuIds)) {
            return Result.success("分配成功");
        }

        // 新增角色菜单关联
        if (baseMapper.batchInsert(roleId, menuIds) > 0) {
            return Result.success("分配成功");
        } else {
            return Result.failed("分配失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> deleteRoleMenus(Long roleId) {
        if (roleId == null) {
            return Result.failed("角色ID不能为空");
        }

        // 删除角色菜单关联
        baseMapper.deleteByRoleId(roleId);

        return Result.success("删除成功");
    }
} 