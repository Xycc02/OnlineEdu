package com.xuyuchao.ossService;

import com.xuyuchao.ossService.utils.ConstantPropertiesUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * @Author: xuyuchao
 * @Date: 2022-06-24-22:02
 * @Description:
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class test {
    @Test
    public void test1() {
        System.out.println(ConstantPropertiesUtils.BUCKET_NAME);
    }
    @Test
    public void test2() {
        String uuid = UUID.randomUUID().toString().replaceAll("-","").substring(0,6);
        System.out.println(uuid);
        long currentTimeMillis = System.currentTimeMillis();
        System.out.println(currentTimeMillis);
    }
}
