package com.zmbs.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜单实体类
 */
@Data
@Accessors(chain = true)
@TableName("menu")
public class Menu implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 路由路径
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 类型：0-目录，1-菜单，2-按钮
     */
    private Integer type;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 是否隐藏：0-显示，1-隐藏
     */
    private Integer hidden;

    /**
     * 重定向地址
     */
    private String redirect;

    /**
     * 是否总是显示：0-否，1-是
     */
    private Integer alwaysShow;

    /**
     * 是否缓存：0-否，1-是
     */
    private Integer keepAlive;

    /**
     * 打开方式：_self-当前窗口，_blank-新窗口
     */
    private String target;

    /**
     * 状态：0-禁用，1-正常
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除：0-未删除，1-已删除
     */
    @TableLogic
    private Integer isDeleted;
} 