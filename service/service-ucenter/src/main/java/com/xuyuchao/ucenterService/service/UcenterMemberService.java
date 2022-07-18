package com.xuyuchao.ucenterService.service;

import com.xuyuchao.ucenterService.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xuyuchao.ucenterService.entity.vo.LoginVo;
import com.xuyuchao.ucenterService.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author xuyuchao
 * @since 2022-07-02
 */
public interface UcenterMemberService extends IService<UcenterMember> {
    //用户单点登录
    String login(LoginVo loginVo);
    //用户注册
    void register(RegisterVo registerVo);
    //根据openid判断
    UcenterMember getOpenIdMember(String openid);
    //查询某一天注册人数
    Integer countRegisterDay(String day);
}
