package com.xuyuchao.smsService.service.impl;

import com.xuyuchao.commonUtils.EmailUtil;
import com.xuyuchao.commonUtils.R;
import com.xuyuchao.commonUtils.RandomUtil;
import com.xuyuchao.smsService.service.SmsService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xuyuchao
 * @Date: 2022-07-02-13:40
 * @Description:
 */
@Service
public class SmsServiceImpl implements SmsService {
    @Resource
    private RedisTemplate redisTemplate;
    /**
     * 向email邮箱发送验证码
     * @param email
     * @return
     */
    @Override
    public boolean sendMsg(String email) {
        //1 从redis获取验证码，如果获取到直接返回
        String code = (String) redisTemplate.opsForValue().get(email);
        if(!StringUtils.isEmpty(code)) {
            return true;
        }
        //2 如果redis获取 不到，进行阿里云发送
        //生成随机值，传递阿里云进行发送
        code = RandomUtil.getFourBitRandom();
        //调用service发送短信的方法
        try {
            EmailUtil.sendMessage(email,code);
        }catch (Exception e) {
            return false;
        }
        //发送成功，把发送成功验证码放到redis里面
        //设置有效时间
        redisTemplate.opsForValue().set(email,code,5, TimeUnit.MINUTES);
        return true;
        }
}
