package com.xuyuchao.commonUtils;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 * @Author: xuyuchao
 * @Date: 2022-07-02-13:57
 * @Description:
 */
public class EmailUtil {
    public static void sendMessage(String recipient,String code) {
        HtmlEmail email=new HtmlEmail();//创建一个HtmlEmail实例对象
        email.setHostName("smtp.163.com");//邮箱的SMTP服务器，一般123邮箱的是smtp.123.com,qq邮箱为smtp.qq.com
        email.setCharset("utf-8");//设置发送的字符类型

        try {
            email.addTo(recipient);//设置收件人
            email.setFrom("xyc2672424338@163.com","徐宇超");//发送人的邮箱为自己的，用户名可以随便填
        } catch (EmailException e) {
            e.printStackTrace();
        }
        email.setAuthentication("xyc2672424338@163.com","GHZMKUVHNWQLYHTJ");//设置发送人到的邮箱和用户名和授权码(授权码是自己设置的)

        email.setSubject("请查收验证码");//设置发送主题
        try {
            email.setMsg("您的验证码为: "+code+" 请勿泄露!");//设置发送内容
            email.send();//进行发送
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}
