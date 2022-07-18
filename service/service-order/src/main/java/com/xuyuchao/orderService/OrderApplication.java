package com.xuyuchao.orderService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: xuyuchao
 * @Date: 2022-07-10-9:34
 * @Description:
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.xuyuchao"})
@MapperScan("com.xuyuchao.orderService.mapper")
@EnableFeignClients //开启远程调用
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }
}
