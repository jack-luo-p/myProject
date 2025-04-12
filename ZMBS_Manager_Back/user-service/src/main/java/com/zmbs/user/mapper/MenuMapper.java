package com.zmbs.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zmbs.user.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单Mapper接口
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 获取用户菜单列表
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<Menu> selectMenusByUserId(@Param("userId") Long userId);

    /**
     * 获取角色菜单列表
     *
     * @param roleId 角色ID
     * @return 菜单列表
     */
    List<Menu> selectMenusByRoleId(@Param("roleId") Long roleId);
} 