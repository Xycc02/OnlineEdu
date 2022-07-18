package com.xuyuchao.ucenterService.controller;


import com.xuyuchao.commonUtils.JwtUtils;
import com.xuyuchao.commonUtils.R;
import com.xuyuchao.ucenterService.entity.UcenterMember;
import com.xuyuchao.ucenterService.entity.vo.LoginVo;
import com.xuyuchao.ucenterService.entity.vo.RegisterVo;
import com.xuyuchao.ucenterService.service.UcenterMemberService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author xuyuchao
 * @since 2022-07-02
 */
@RestController
@RequestMapping("/ucenterService/member")

public class UcenterMemberController {
    @Resource
    private UcenterMemberService ucenterMemberService;

    /**
     * 用户登录
     * @param loginVo
     * @return
     */
    @PostMapping("login")
    public R loginUser(@RequestBody LoginVo loginVo) {
        //member对象封装邮箱和密码
        //调用service方法实现登录
        //返回token值，使用jwt生成
        String token = ucenterMemberService.login(loginVo);
        return R.ok().data("token",token);
    }

    /**
     * 用户注册
     * @param registerVo
     * @return
     */
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo) {
        ucenterMemberService.register(registerVo);
        return R.ok();
    }

    /**
     * 根据token获取用户信息
     * @param request
     * @return
     */
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        //调用jwt工具类的方法。根据request对象获取头信息，获得用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库根据用户id获取用户信息
        UcenterMember member = ucenterMemberService.getById(memberId);
        return R.ok().data("userInfo",member);
    }

    /**
     * 根据id获取用户信息
     * @param id
     * @return
     */
    @GetMapping("getMemberById/{id}")
    public R getMemberById(@PathVariable String id) {
        UcenterMember userInfo = ucenterMemberService.getById(id);
        return R.ok().data("userInfo",userInfo);
    }

    /**
     * 查询某一天注册人数
     * @param day
     * @return
     */
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable String day) {
        Integer count = ucenterMemberService.countRegisterDay(day);
        return R.ok().data("countRegister",count);
    }
}

