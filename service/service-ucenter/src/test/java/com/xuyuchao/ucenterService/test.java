package com.xuyuchao.ucenterService;

import com.xuyuchao.ucenterService.mapper.UcenterMemberMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: xuyuchao
 * @Date: 2022-07-13-20:01
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class test {
    @Resource
    private UcenterMemberMapper ucenterMemberMapper;
    @Test
    public void test() {
        Integer count = ucenterMemberMapper.countRegisterDay("2022-07-03");
        System.out.println(count);
    }
}
