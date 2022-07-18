package com.xuyuchao.eduService.controller;

import com.xuyuchao.commonUtils.R;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: xuyuchao
 * @Date: 2022-06-22-21:39
 * @Description:
 */
@RequestMapping("/eduService/user")
@RestController
public class EduLoginController {

    @PostMapping("/login")
    public R login() {
        return R.ok().data("token","admin");
    }

    @GetMapping("/info")
    public R info() {
        return R.ok().data("roles","admin").data("name","admin").data("avatar", "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fup.enterdesk.com%2Fedpic%2Fc9%2F63%2F77%2Fc963777ebd9c8ebf5583b39565cfa1d7.jpg&refer=http%3A%2F%2Fup.enterdesk.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1658547393&t=091ef0eb8b4f725efb1813a6b1cee1bb");
    }
}
