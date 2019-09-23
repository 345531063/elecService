package com.cn.maitian.dev.config;

import com.cn.maitian.dev.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

@Configuration
public class SecurityConfigure extends WebMvcConfigurerAdapter {
    @Bean
    public LoginCheckInterceptor userLoginCheckInterceptor() {
        return new LoginCheckInterceptor();
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        System.out.println(">>>> 调用成功>>>>addInterceptors..");
        registry.addInterceptor(userLoginCheckInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login/**", "/logout/**", "/loginPage/**", "/error/**");
        super.addInterceptors(registry);
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .maxAge(168000)
                .allowCredentials(true);
    }
}