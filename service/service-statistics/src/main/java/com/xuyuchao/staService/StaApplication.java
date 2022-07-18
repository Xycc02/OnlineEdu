package com.xuyuchao.staService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author: xuyuchao
 * @Date: 2022-07-13-19:26
 * @Description:
 */
@SpringBootApplication
@MapperScan("com.xuyuchao.staService.mapper")
@ComponentScan(basePackages = {"com.xuyuchao"})
@EnableFeignClients
@EnableScheduling   //开启定时任务
public class StaApplication {
    public static void main(String[] args) {
        SpringApplication.run(StaApplication.class,args);
    }
}
