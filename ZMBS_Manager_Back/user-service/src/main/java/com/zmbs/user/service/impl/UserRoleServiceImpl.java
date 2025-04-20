package com.zmbs.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zmbs.user.entity.User;
import com.zmbs.user.entity.UserRole;
import com.zmbs.user.mapper.UserMapper;
import com.zmbs.user.mapper.UserRoleMapper;
import com.zmbs.user.service.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Override
    public UserRole getUserRoleById(Long userId) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, userId);
        return getOne(queryWrapper);
    }
}
