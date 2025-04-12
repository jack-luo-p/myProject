package com.zmbs.order.controller;

import com.zmbs.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单控制器
 */
@RestController
@RequestMapping("/api/order")
@Tag(name = "订单管理", description = "订单相关接口")
public class OrderController {

    /**
     * 测试接口
     */
    @GetMapping("/test")
    @Operation(summary = "测试接口", description = "用于测试订单服务是否正常")
    public Result<String> test() {
        return Result.success("订单服务测试成功");
    }
} 