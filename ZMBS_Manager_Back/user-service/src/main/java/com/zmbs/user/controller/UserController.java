package com.zmbs.user.controller;

import com.zmbs.common.result.Result;
import com.zmbs.user.dto.LoginDTO;
import com.zmbs.user.dto.RegisterDTO;
import com.zmbs.user.dto.UpdatePasswordDTO;
import com.zmbs.user.dto.UpdateUserDTO;
import com.zmbs.user.service.UserService;
import com.zmbs.user.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/api/user")
@Tag(name = "用户管理", description = "用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 测试接口
     */
    @GetMapping("/test")
    @Operation(summary = "测试接口", description = "用于测试用户服务是否正常")
    public Result<String> test() {
        return Result.success("用户服务测试成功");
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录接口，如果用户不存在会提示注册")
    public Result<UserVO> login(@Validated @RequestBody LoginDTO loginDTO) {
        return userService.login(loginDTO);
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "用户注册接口")
    public Result<UserVO> register(@Validated @RequestBody RegisterDTO registerDTO) {
        return userService.register(registerDTO);
    }

    /**
     * 获取当前登录用户信息
     */
    @GetMapping("/info")
    @Operation(summary = "获取用户信息", description = "获取当前登录用户的详细信息")
    public Result<UserVO> getUserInfo() {
        return userService.getCurrentUserInfo();
    }

    /**
     * 根据用户ID获取用户信息
     */
    @GetMapping("/{userId}")
    @Operation(summary = "根据ID获取用户", description = "根据用户ID获取用户信息")
    public Result<UserVO> getUserById(@PathVariable("userId") Long userId) {
        return userService.getUserById(userId);
    }
    
    /**
     * 更新用户信息
     */
    @PutMapping("/update")
    @Operation(summary = "更新用户信息", description = "更新当前登录用户的基本信息")
    public Result<UserVO> updateUser(@Validated @RequestBody UpdateUserDTO updateUserDTO) {
        return userService.updateUserInfo(updateUserDTO);
    }
    
    /**
     * 修改密码
     */
    @PutMapping("/password")
    @Operation(summary = "修改密码", description = "修改当前登录用户的密码")
    public Result<Void> updatePassword(@Validated @RequestBody UpdatePasswordDTO updatePasswordDTO) {
        return userService.updatePassword(updatePasswordDTO);
    }
    
    /**
     * 退出登录
     */
    @PostMapping("/logout")
    @Operation(summary = "退出登录", description = "用户退出登录接口")
    public Result<Void> logout() {
        return userService.logout();
    }
} 