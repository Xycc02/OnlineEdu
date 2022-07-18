package com.xuyuchao.eduService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: xuyuchao
 * @Date: 2022-06-20-19:40
 * @Description:
 */
@SpringBootApplication
@EnableFeignClients //开启远程调用
@ComponentScan(basePackages = {"com.xuyuchao"})
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class,args);
    }
}
