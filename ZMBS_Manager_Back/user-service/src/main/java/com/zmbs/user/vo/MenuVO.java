package com.zmbs.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单视图对象
 */
@Data
@Schema(description = "菜单信息")
public class MenuVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    @Schema(description = "菜单ID")
    private Long id;

    /**
     * 父菜单ID
     */
    @Schema(description = "父菜单ID")
    private Long parentId;

    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称")
    private String name;

    /**
     * 路由路径
     */
    @Schema(description = "路由路径")
    private String path;

    /**
     * 组件路径
     */
    @Schema(description = "组件路径")
    private String component;

    /**
     * 权限标识
     */
    @Schema(description = "权限标识")
    private String perms;

    /**
     * 菜单图标
     */
    @Schema(description = "菜单图标")
    private String icon;

    /**
     * 类型：0-目录，1-菜单，2-按钮
     */
    @Schema(description = "类型：0-目录，1-菜单，2-按钮")
    private Integer type;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer orderNum;

    /**
     * 是否隐藏：0-显示，1-隐藏
     */
    @Schema(description = "是否隐藏：0-显示，1-隐藏")
    private Integer hidden;

    /**
     * 重定向地址
     */
    @Schema(description = "重定向地址")
    private String redirect;

    /**
     * 是否总是显示：0-否，1-是
     */
    @Schema(description = "是否总是显示：0-否，1-是")
    private Integer alwaysShow;

    /**
     * 是否缓存：0-否，1-是
     */
    @Schema(description = "是否缓存：0-否，1-是")
    private Integer keepAlive;

    /**
     * 打开方式：_self-当前窗口，_blank-新窗口
     */
    @Schema(description = "打开方式：_self-当前窗口，_blank-新窗口")
    private String target;

    /**
     * 状态：0-禁用，1-正常
     */
    @Schema(description = "状态：0-禁用，1-正常")
    private Integer status;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 子菜单
     */
    @Schema(description = "子菜单")
    private List<MenuVO> children = new ArrayList<>();
} 