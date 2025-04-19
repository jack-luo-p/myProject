package com.zmbs.common.util;

/**
 * @ClassDescription:
 * @Author:LuoPeng
 * @Create:2025/4/19 01:06
 **/


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtils {

    // 生成密钥（建议放在配置文件中）
    private static final String SECRET = "my-secret-key";

    // 过期时间（毫秒） — 1小时
    private static final long EXPIRATION = 3600000;

    // 生成 token
    public static String generateToken(String userId,String userName,String userRole) {
        return Jwts.builder()
                .setSubject(userId) // userId
                .claim("username", userName)
                .claim("role", userRole)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION)) // 1小时有效
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();

    }

    // 解析 token
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    // 判断 token 是否过期
    public static boolean isExpired(String token) {
        return parseToken(token).getExpiration().before(new Date());
    }
}

