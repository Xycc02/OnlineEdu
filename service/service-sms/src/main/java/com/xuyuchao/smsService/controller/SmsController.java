package com.xuyuchao.smsService.controller;

import com.xuyuchao.commonUtils.R;
import com.xuyuchao.smsService.service.SmsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: xuyuchao
 * @Date: 2022-07-02-13:41
 * @Description:
 */
@RequestMapping("/smsService")
@RestController

public class SmsController {

    @Resource
    private SmsService smsService;

    @GetMapping("/sendMsg/{email}")
    public R sendMsg(@PathVariable String email) {
        //发送验证码
        boolean isSend = smsService.sendMsg(email);
        if(isSend) {
            return R.ok();
        }
        return R.error().message("验证码发送失败!");
    }
}
