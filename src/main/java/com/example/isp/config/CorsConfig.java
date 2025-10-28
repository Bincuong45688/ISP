package com.example.isp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // Use allowedOriginPatterns instead of allowedOrigins when allowCredentials is true
                .allowedOriginPatterns(
                        "https://isp-7jpp.onrender.com",
                        "http://localhost:3000",
                        "http://localhost:*"  // Allow any localhost port
                )
                // Cho phép tất cả method cần thiết
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                // Cho phép mọi header (Authorization, Content-Type, v.v.)
                .allowedHeaders("*")
                // Cho phép FE đọc header JWT/token trong response
                .exposedHeaders("Authorization", "Content-Type")
                // Cho phép FE gửi kèm cookie/token
                .allowCredentials(true)
                // Cache preflight request 1 tiếng
                .maxAge(3600);
    }
}
