package com.hh.pms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import com.ruoyi.common.swagger.annotation.EnableCustomSwagger2;

import java.util.Arrays;

/**
 * 系统模块
 *
 * @author ruoyi
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
public class JqyHhSupplierApplication {
    public static void main(String[] args) {
        SpringApplication.run(JqyHhSupplierApplication.class, args);
        System.out.println("这是系统模块的启动类，你会发现已经启动了");
    }

}
