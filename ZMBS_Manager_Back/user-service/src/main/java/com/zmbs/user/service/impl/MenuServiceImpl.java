package com.zmbs.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zmbs.common.result.Result;
import com.zmbs.common.result.ResultCode;
import com.zmbs.common.util.SecurityUtils;
import com.zmbs.user.dto.MenuDTO;
import com.zmbs.user.entity.Menu;
import com.zmbs.user.entity.RoleMenu;
import com.zmbs.user.mapper.MenuMapper;
import com.zmbs.user.mapper.RoleMenuMapper;
import com.zmbs.user.service.MenuService;
import com.zmbs.user.vo.MenuVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单服务实现类
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public Result<List<MenuVO>> getMenuTree() {
        // 查询所有菜单
        List<Menu> menus = this.list(new LambdaQueryWrapper<Menu>()
                .eq(Menu::getStatus, 1)
                .orderByAsc(Menu::getOrderNum));

        // 构建树形结构
        List<MenuVO> menuTree = buildMenuTree(menus);

        return Result.success(menuTree);
    }

    @Override
    public Result<List<MenuVO>> getCurrentUserMenus() {
        // 获取当前用户ID
        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            return Result.failed(ResultCode.UNAUTHORIZED, "用户未登录");
        }

        // 查询用户菜单
        List<Menu> menus = baseMapper.selectMenusByUserId(userId);

        // 构建树形结构
        List<MenuVO> menuTree = buildMenuTree(menus);

        return Result.success(menuTree);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> addMenu(MenuDTO menuDTO) {
        // 检查名称是否重复
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getName, menuDTO.getName());
        queryWrapper.eq(Menu::getParentId, menuDTO.getParentId());
        queryWrapper.eq(Menu::getStatus, 1);
        if (this.count(queryWrapper) > 0) {
            return Result.failed("同级菜单下已存在相同名称的菜单");
        }

        // 创建菜单
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuDTO, menu);
        menu.setCreateTime(LocalDateTime.now());
        menu.setUpdateTime(LocalDateTime.now());
        menu.setIsDeleted(0);

        // 保存菜单
        this.save(menu);

        return Result.success("添加成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> updateMenu(MenuDTO menuDTO) {
        // 检查菜单是否存在
        Menu menu = this.getById(menuDTO.getId());
        if (menu == null) {
            return Result.failed("菜单不存在");
        }

        // 检查名称是否重复
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getName, menuDTO.getName());
        queryWrapper.eq(Menu::getParentId, menuDTO.getParentId());
        queryWrapper.eq(Menu::getStatus, 1);
        queryWrapper.ne(Menu::getId, menuDTO.getId());
        if (this.count(queryWrapper) > 0) {
            return Result.failed("同级菜单下已存在相同名称的菜单");
        }

        // 更新菜单
        BeanUtils.copyProperties(menuDTO, menu);
        menu.setUpdateTime(LocalDateTime.now());

        // 保存更新
        this.updateById(menu);

        return Result.success("更新成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> deleteMenu(Long menuId) {
        // 检查菜单是否存在
        Menu menu = this.getById(menuId);
        if (menu == null) {
            return Result.failed("菜单不存在");
        }

        // 检查是否有子菜单
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId, menuId);
        if (this.count(queryWrapper) > 0) {
            return Result.failed("该菜单下存在子菜单，不能删除");
        }

        // 删除菜单
        this.removeById(menuId);

        // 删除角色菜单关联
        roleMenuMapper.deleteByMenuId(menuId);

        return Result.success("删除成功");
    }

    @Override
    public Result<MenuVO> getMenuById(Long menuId) {
        // 查询菜单
        Menu menu = this.getById(menuId);
        if (menu == null) {
            return Result.failed("菜单不存在");
        }

        // 转换为VO
        MenuVO menuVO = convertToMenuVO(menu);

        return Result.success(menuVO);
    }

    @Override
    public Result<List<Long>> getRoleMenuIds(Long roleId) {
        // 查询角色菜单
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId, roleId);
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(queryWrapper);

        // 提取菜单ID
        List<Long> menuIds = roleMenus.stream()
                .map(RoleMenu::getMenuId)
                .collect(Collectors.toList());

        return Result.success(menuIds);
    }

    @Override
    public Result<List<MenuVO>> getRoleMenuTree(Long roleId) {
        // 查询角色菜单
        List<Menu> menus = baseMapper.selectMenusByRoleId(roleId);

        // 构建树形结构
        List<MenuVO> menuTree = buildMenuTree(menus);

        return Result.success(menuTree);
    }

    /**
     * 构建菜单树
     *
     * @param menus 菜单列表
     * @return 菜单树
     */
    private List<MenuVO> buildMenuTree(List<Menu> menus) {
        if (CollectionUtils.isEmpty(menus)) {
            return new ArrayList<>();
        }

        // 转换为VO
        List<MenuVO> menuVOList = menus.stream()
                .map(this::convertToMenuVO)
                .collect(Collectors.toList());

        // 构建菜单ID到菜单的映射
        Map<Long, MenuVO> menuMap = menuVOList.stream()
                .collect(Collectors.toMap(MenuVO::getId, menu -> menu));

        // 构建树形结构
        List<MenuVO> menuTree = new ArrayList<>();
        for (MenuVO menuVO : menuVOList) {
            Long parentId = menuVO.getParentId();
            if (parentId == null || parentId == 0) {
                // 顶级菜单
                menuTree.add(menuVO);
            } else {
                // 子菜单
                MenuVO parentMenu = menuMap.get(parentId);
                if (parentMenu != null) {
                    if (parentMenu.getChildren() == null) {
                        parentMenu.setChildren(new ArrayList<>());
                    }
                    parentMenu.getChildren().add(menuVO);
                }
            }
        }

        // 对菜单进行排序
        sortMenuTree(menuTree);

        return menuTree;
    }

    /**
     * 对菜单树进行排序
     *
     * @param menuTree 菜单树
     */
    private void sortMenuTree(List<MenuVO> menuTree) {
        menuTree.sort(Comparator.comparing(MenuVO::getOrderNum));
        for (MenuVO menuVO : menuTree) {
            if (menuVO.getChildren() != null && !menuVO.getChildren().isEmpty()) {
                sortMenuTree(menuVO.getChildren());
            }
        }
    }

    /**
     * 将菜单对象转换为MenuVO
     *
     * @param menu 菜单对象
     * @return MenuVO
     */
    private MenuVO convertToMenuVO(Menu menu) {
        if (menu == null) {
            return null;
        }
        MenuVO menuVO = new MenuVO();
        BeanUtils.copyProperties(menu, menuVO);
        return menuVO;
    }
} 