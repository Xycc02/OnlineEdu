package com.xuyuchao.ucenterService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: xuyuchao
 * @Date: 2022-07-02-14:47
 * @Description:
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.xuyuchao"})
@MapperScan("com.xuyuchao.ucenterService.mapper")
public class UcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class,args);
    }
}
