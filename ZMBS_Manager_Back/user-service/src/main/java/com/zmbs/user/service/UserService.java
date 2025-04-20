package com.zmbs.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zmbs.common.result.Result;
import com.zmbs.user.dto.LoginDTO;
import com.zmbs.user.dto.RegisterDTO;
import com.zmbs.user.dto.UpdatePasswordDTO;
import com.zmbs.user.dto.UpdateUserDTO;
import com.zmbs.user.entity.RoleMenu;
import com.zmbs.user.entity.User;
import com.zmbs.user.entity.UserRole;
import com.zmbs.user.vo.UserVO;

import javax.management.relation.Role;
import javax.management.relation.RoleInfo;
import java.util.Map;

/**
 * 用户Service接口
 */
public interface UserService extends IService<User> {

    /**
     * 用户登录
     *
     * @param loginDTO 登录信息
     * @return 登录结果
     */
    Result<Map> login(LoginDTO loginDTO);

    /**
     * 用户注册
     *
     * @param registerDTO 注册信息
     * @return 注册结果
     */
    Result<UserVO> register(RegisterDTO registerDTO);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    User getByUsername(String username);

    /**
     * 根据手机号查询用户
     *
     * @param phone 手机号
     * @return 用户信息
     */
    User getByPhone(String phone);

    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户信息
     */
    User getByEmail(String email);

    /**
     * 将用户对象转换为UserVO
     *
     * @param user 用户对象
     * @return UserVO
     */
    UserVO convertToUserVO(User user);
    
    /**
     * 获取当前登录用户信息
     *
     * @return 用户信息
     */
    Result<UserVO> getCurrentUserInfo();
    
    /**
     * 根据用户ID获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    Result<UserVO> getUserById(Long userId);
    
    /**
     * 更新用户信息
     *
     * @param updateUserDTO 用户信息
     * @return 更新后的用户信息
     */
    Result<UserVO> updateUserInfo(UpdateUserDTO updateUserDTO);
    
    /**
     * 修改密码
     *
     * @param updatePasswordDTO 密码信息
     * @return 修改结果
     */
    Result<Void> updatePassword(UpdatePasswordDTO updatePasswordDTO);
    
    /**
     * 退出登录
     *
     * @return 退出结果
     */
    Result<Void> logout();
} 