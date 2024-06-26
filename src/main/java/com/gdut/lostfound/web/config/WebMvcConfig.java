package com.gdut.lostfound.web.config;

import com.gdut.lostfound.web.config.interceptor.DefaultInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public DefaultInterceptor defaultInterceptor() {
        return new DefaultInterceptor();
    }

    /**
     * 添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截所有
        registry.addInterceptor(defaultInterceptor()).addPathPatterns("/**");
    }

    /**
     * 允许跨域访问
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//允许跨域访问的路径
                .allowCredentials(true)
                .allowedHeaders("*")
                //.allowedOrigins("http://192.168.1.100:8080")
                .allowedMethods("*")
                .exposedHeaders(HttpHeaders.SET_COOKIE)
                .maxAge(-1L);//单位：秒，-1表示无限制，到关闭浏览器前均有效
    }

}