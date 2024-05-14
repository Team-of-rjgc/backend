package com.gdut.lostfound.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 设置允许跨域的路径
                .allowedOrigins("http://10.21.32.86:8080","http://localhost:8080") // 设置允许跨域请求的域名
                .allowCredentials(true) // 是否允许证书 不再默认开启
                .allowedMethods("*") // 设置允许的方法
                .allowedHeaders("Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers") // 允许跨域请求包含的头信息
                .maxAge(3600); // 跨域允许时间
    }
}