package com.zmbs.user.config;

import com.zmbs.common.config.BaseSwaggerConfig;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger配置
 */
@Configuration
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public Info apiInfo() {
        return createApiInfo(
                "用户服务API",
                "ZMBS用户服务相关接口文档",
                "1.0",
                "ZMBS团队",
                "https://zmbs.com",
                "contact@zmbs.com",
                "Apache 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0.html");
    }
} 