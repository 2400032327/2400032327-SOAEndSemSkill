package com.example.tourist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class TouristRecordApplication implements WebMvcConfigurer {
    private final TokenValidationInterceptor tokenValidationInterceptor;

    public TouristRecordApplication(TokenValidationInterceptor tokenValidationInterceptor) {
        this.tokenValidationInterceptor = tokenValidationInterceptor;
    }

    public static void main(String[] args) {
        SpringApplication.run(TouristRecordApplication.class, args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenValidationInterceptor)
                .addPathPatterns("/api/tourists/**")
                .excludePathPatterns("/api/tourists", "/swagger-ui/**", "/v3/api-docs/**");
    }
}
