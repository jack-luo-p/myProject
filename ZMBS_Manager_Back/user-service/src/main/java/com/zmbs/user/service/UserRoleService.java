package com.zmbs.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zmbs.user.entity.UserRole;

public interface UserRoleService extends IService<UserRole> {
    /**
     * 通过用户ID获取角色信息
     * @param userId
     * @return
     */
    UserRole getUserRoleById(Long userId);
}
