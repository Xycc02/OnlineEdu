package com.xuyuchao.canal;

import com.xuyuchao.canal.client.CanalClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

/**
 * @Author: xuyuchao
 * @Date: 2022-07-15-14:13
 * @Description:
 */
@SpringBootApplication
public class CanalApplication implements CommandLineRunner {
    @Resource
    private CanalClient canalClient;

    public static void main(String[] args) {
        SpringApplication.run(CanalApplication.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        //项目启动,执行canal客户端监听,实现数据同步
        canalClient.run();
    }
}
