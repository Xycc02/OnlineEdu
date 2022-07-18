package com.xuyuchao.eduService.client.Impl;

import com.xuyuchao.commonUtils.R;
import com.xuyuchao.eduService.client.UcenterClient;
import org.springframework.stereotype.Component;

/**
 * @Author: xuyuchao
 * @Date: 2022-07-06-22:05
 * @Description:
 */
@Component
public class UcenterDegradeFeignClient implements UcenterClient {
    @Override
    public R getMemberById(String id) {
        return R.error().message("获取登录用户信息失败!");
    }
}
