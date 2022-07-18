package com.xuyuchao.eduService.client;

import com.xuyuchao.commonUtils.R;
import com.xuyuchao.eduService.client.Impl.UcenterDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * @Author: xuyuchao
 * @Date: 2022-07-06-22:02
 * @Description:
 */
@FeignClient(name = "service-ucenter",fallback = UcenterDegradeFeignClient.class)
@Component
public interface UcenterClient {
    //根据id获取用户信息
    @GetMapping("/ucenterService/member/getMemberById/{id}")
    R getMemberById(@PathVariable String id);
}
