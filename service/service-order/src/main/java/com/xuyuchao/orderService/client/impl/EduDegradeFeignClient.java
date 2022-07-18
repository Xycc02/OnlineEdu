package com.xuyuchao.orderService.client.impl;

import com.xuyuchao.commonUtils.R;
import com.xuyuchao.orderService.client.EduClient;
import org.springframework.stereotype.Component;

/**
 * @Author: xuyuchao
 * @Date: 2022-07-11-21:40
 * @Description:
 */
@Component
public class EduDegradeFeignClient implements EduClient {
    @Override
    public R getFrontCourseInfo(String courseId) {
        return R.error().message("获取订单用户信息失败");
    }
}
