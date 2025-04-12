package com.zmbs.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 菜单DTO
 */
@Data
@Schema(description = "菜单请求参数")
public class MenuDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID，新增时不需要传，修改时必传
     */
    @Schema(description = "菜单ID，新增时不需要传，修改时必传")
    private Long id;

    /**
     * 父菜单ID，一级菜单为0
     */
    @Schema(description = "父菜单ID，一级菜单为0")
    private Long parentId = 0L;

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    @Schema(description = "菜单名称", required = true)
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
    @NotNull(message = "菜单类型不能为空")
    @Schema(description = "类型：0-目录，1-菜单，2-按钮", required = true)
    private Integer type;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer orderNum = 0;

    /**
     * 是否隐藏：0-显示，1-隐藏
     */
    @Schema(description = "是否隐藏：0-显示，1-隐藏")
    private Integer hidden = 0;

    /**
     * 重定向地址
     */
    @Schema(description = "重定向地址")
    private String redirect;

    /**
     * 是否总是显示：0-否，1-是
     */
    @Schema(description = "是否总是显示：0-否，1-是")
    private Integer alwaysShow = 0;

    /**
     * 是否缓存：0-否，1-是
     */
    @Schema(description = "是否缓存：0-否，1-是")
    private Integer keepAlive = 0;

    /**
     * 打开方式：_self-当前窗口，_blank-新窗口
     */
    @Schema(description = "打开方式：_self-当前窗口，_blank-新窗口")
    private String target = "_self";

    /**
     * 状态：0-禁用，1-正常
     */
    @Schema(description = "状态：0-禁用，1-正常")
    private Integer status = 1;
} 