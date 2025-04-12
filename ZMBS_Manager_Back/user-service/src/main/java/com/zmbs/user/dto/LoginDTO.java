package com.zmbs.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 登录DTO
 */
@Data
@Schema(description = "登录请求参数")
public class LoginDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户名/手机号/邮箱
     */
    @NotBlank(message = "用户名不能为空")
    @Schema(description = "用户名/手机号/邮箱", required = true)
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码", required = true)
    private String password;
} 