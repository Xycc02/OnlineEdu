package com.xuyuchao.orderService.client;

import com.xuyuchao.commonUtils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    //根据用户id获取用户信息
    @GetMapping("/ucenterService/member/getMemberById/{id}")
    public R getMemberById(@PathVariable("id") String id);
}
