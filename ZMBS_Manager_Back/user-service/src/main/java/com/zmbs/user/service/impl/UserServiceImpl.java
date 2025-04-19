package com.zmbs.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zmbs.common.result.Result;
import com.zmbs.common.result.ResultCode;
import com.zmbs.common.util.JwtUtils;
import com.zmbs.common.util.SecurityUtils;
import com.zmbs.user.dto.LoginDTO;
import com.zmbs.user.dto.RegisterDTO;
import com.zmbs.user.dto.UpdatePasswordDTO;
import com.zmbs.user.dto.UpdateUserDTO;
import com.zmbs.user.entity.User;
import com.zmbs.user.mapper.UserMapper;
import com.zmbs.user.service.UserService;
import com.zmbs.user.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 用户Service实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public Result<UserVO> login(LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        // 加密密码
        String encryptedPassword = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));

        // 根据用户名、手机号或邮箱查询用户
        User user = getByUsername(username);
        if (user == null) {
            user = getByPhone(username);
        }
        if (user == null) {
            user = getByEmail(username);
        }

        // 用户不存在，提示注册
        if (user == null) {
            return Result.failed("用户不存在，请先注册");
        }

        // 根据用户查找对应的角色


        // 校验密码
        if (!encryptedPassword.equals(user.getPassword())) {
            return Result.failed("密码错误");
        }

        // 校验用户状态
        if (user.getStatus() != 1) {
            return Result.failed("账号已被禁用");
        }

        // 登录成功，返回用户信息
        UserVO userVO = convertToUserVO(user);
        return Result.success("登录成功", userVO);
    }

    @Override
    public Result<UserVO> register(RegisterDTO registerDTO) {
        // 校验两次密码是否一致
        if (!Objects.equals(registerDTO.getPassword(), registerDTO.getConfirmPassword())) {
            return Result.failed("两次密码不一致");
        }

        // 校验用户名是否已存在
        if (getByUsername(registerDTO.getUsername()) != null) {
            return Result.failed("用户名已存在");
        }

        // 校验手机号是否已存在
        if (registerDTO.getPhone() != null && getByPhone(registerDTO.getPhone()) != null) {
            return Result.failed("手机号已存在");
        }

        // 校验邮箱是否已存在
        if (registerDTO.getEmail() != null && getByEmail(registerDTO.getEmail()) != null) {
            return Result.failed("邮箱已存在");
        }

        // 创建用户
        User user = new User();
        BeanUtils.copyProperties(registerDTO, user);
        
        // 设置默认值
        user.setNickname(registerDTO.getNickname() != null ? registerDTO.getNickname() : registerDTO.getUsername());
        user.setStatus(1); // 默认启用
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setIsDeleted(0); // 默认未删除
        
        // 密码加密
        String encryptedPassword = DigestUtils.md5DigestAsHex(registerDTO.getPassword().getBytes(StandardCharsets.UTF_8));
        user.setPassword(encryptedPassword);
        
        // 保存用户
        save(user);
        
        // 返回用户信息
        UserVO userVO = convertToUserVO(user);
        return Result.success("注册成功", userVO);
    }

    @Override
    public User getByUsername(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        return getOne(queryWrapper);
    }

    @Override
    public User getByPhone(String phone) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone, phone);
        return getOne(queryWrapper);
    }

    @Override
    public User getByEmail(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        return getOne(queryWrapper);
    }

    @Override
    public UserVO convertToUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        userVO.setToken(JwtUtils.generateToken(user.getUsername()));
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }
    
    @Override
    public Result<UserVO> getCurrentUserInfo() {
        // 获取当前登录用户ID
        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            return Result.failed(ResultCode.UNAUTHORIZED, "用户未登录");
        }
        
        // 查询用户信息
        User user = getById(userId);
        if (user == null) {
            return Result.failed("用户不存在");
        }
        
        // 返回用户信息
        UserVO userVO = convertToUserVO(user);
        return Result.success(userVO);
    }
    
    @Override
    public Result<UserVO> getUserById(Long userId) {
        if (userId == null) {
            return Result.failed("用户ID不能为空");
        }
        
        // 查询用户信息
        User user = getById(userId);
        if (user == null) {
            return Result.failed("用户不存在");
        }
        
        // 返回用户信息
        UserVO userVO = convertToUserVO(user);
        return Result.success(userVO);
    }
    
    @Override
    public Result<UserVO> updateUserInfo(UpdateUserDTO updateUserDTO) {
        // 获取当前登录用户ID
        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            return Result.failed(ResultCode.UNAUTHORIZED, "用户未登录");
        }
        
        // 查询用户信息
        User user = getById(userId);
        if (user == null) {
            return Result.failed("用户不存在");
        }
        
        // 检查电话和邮箱是否已被其他用户使用
        if (updateUserDTO.getPhone() != null && !updateUserDTO.getPhone().equals(user.getPhone())) {
            User existUser = getByPhone(updateUserDTO.getPhone());
            if (existUser != null && !existUser.getId().equals(userId)) {
                return Result.failed("手机号已被其他用户使用");
            }
        }
        
        if (updateUserDTO.getEmail() != null && !updateUserDTO.getEmail().equals(user.getEmail())) {
            User existUser = getByEmail(updateUserDTO.getEmail());
            if (existUser != null && !existUser.getId().equals(userId)) {
                return Result.failed("邮箱已被其他用户使用");
            }
        }
        
        // 更新用户信息
        if (updateUserDTO.getNickname() != null) {
            user.setNickname(updateUserDTO.getNickname());
        }
        if (updateUserDTO.getPhone() != null) {
            user.setPhone(updateUserDTO.getPhone());
        }
        if (updateUserDTO.getEmail() != null) {
            user.setEmail(updateUserDTO.getEmail());
        }
        if (updateUserDTO.getAvatar() != null) {
            user.setAvatar(updateUserDTO.getAvatar());
        }
        
        user.setUpdateTime(LocalDateTime.now());
        
        // 保存更新
        updateById(user);
        
        // 返回更新后的用户信息
        UserVO userVO = convertToUserVO(user);
        return Result.success("更新成功", userVO);
    }
    
    @Override
    public Result<Void> updatePassword(UpdatePasswordDTO updatePasswordDTO) {
        // 获取当前登录用户ID
        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            return Result.failed(ResultCode.UNAUTHORIZED, "用户未登录");
        }
        
        // 查询用户信息
        User user = getById(userId);
        if (user == null) {
            return Result.failed("用户不存在");
        }
        
        // 校验旧密码
        String oldEncryptedPassword = DigestUtils.md5DigestAsHex(
                updatePasswordDTO.getOldPassword().getBytes(StandardCharsets.UTF_8));
        if (!oldEncryptedPassword.equals(user.getPassword())) {
            return Result.failed("旧密码错误");
        }
        
        // 校验两次密码是否一致
        if (!Objects.equals(updatePasswordDTO.getNewPassword(), updatePasswordDTO.getConfirmNewPassword())) {
            return Result.failed("两次新密码不一致");
        }
        
        // 更新密码
        String newEncryptedPassword = DigestUtils.md5DigestAsHex(
                updatePasswordDTO.getNewPassword().getBytes(StandardCharsets.UTF_8));
        user.setPassword(newEncryptedPassword);
        user.setUpdateTime(LocalDateTime.now());
        
        // 保存更新
        updateById(user);
        
        return Result.success("密码修改成功");
    }
    
    @Override
    public Result<Void> logout() {
        // 实际项目中，这里需要清除token或会话
        // 由于我们使用了Spring Security，可以调用相关方法进行登出操作
        // SecurityContextHolder.clearContext();
        
        return Result.success("退出成功");
    }
} 