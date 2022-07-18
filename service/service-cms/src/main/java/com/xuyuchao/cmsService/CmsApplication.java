package com.xuyuchao.cmsService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: xuyuchao
 * @Date: 2022-06-29-20:07
 * @Description:
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.xuyuchao"})
@MapperScan("com/xuyuchao/cmsService/mapper")
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class,args);
    }
}
