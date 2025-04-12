package com.zmbs.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 安全工具类
 */
public class SecurityUtils {

    /**
     * 获取当前登录用户ID
     *
     * @return 用户ID
     */
    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return null;
        }
        
        // 根据实际存储用户信息的方式获取用户ID
        // 这里假设用户ID存储在Principal对象中
        try {
            Object principal = authentication.getPrincipal();
            
            // 根据项目实际情况获取用户ID
            // 如果使用自定义UserDetails实现，可以直接转换
            if (principal instanceof CustomUserDetails) {
                return ((CustomUserDetails) principal).getUserId();
            }
            
            // 或者从JWT Token中获取
            // 这里只是一个示例，实际需要根据认证方式调整
            return Long.valueOf(authentication.getName());
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 获取当前登录用户名
     *
     * @return 用户名
     */
    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return null;
        }
        
        return authentication.getName();
    }
    
    /**
     * 判断当前用户是否已登录
     *
     * @return 是否已登录
     */
    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal());
    }
}

/**
 * 自定义UserDetails接口
 * 实际项目中应该放在单独的文件中
 */
interface CustomUserDetails {
    Long getUserId();
} 