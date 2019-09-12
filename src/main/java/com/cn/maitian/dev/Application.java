package com.cn.maitian.dev;//package com.cn.zfb.redis.controller;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.cn.maitian.dev.util.ConfigUtil;

import feign.Logger;

/**
 * Created by stevenMore on 2018/4/28 0018.
 */
@Configuration
@ComponentScan(basePackages = "com.cn.maitian.dev")
@ImportResource("classpath:conf/spring.xml")
//@EnableDiscoveryClient
@EnableAutoConfiguration
@EnableTransactionManagement
@MapperScan("com.cn.maitian.dev.dao")
//@EnableFeignClients
@EnableCircuitBreaker
//@EnableScheduling
public class Application {
    @Bean
    Logger.Level feginLoggerLevel(){
        return Logger.Level.FULL;
    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
        System.out.println("系统版本 : 【" + ConfigUtil.VERSION + "】" );
    }
}