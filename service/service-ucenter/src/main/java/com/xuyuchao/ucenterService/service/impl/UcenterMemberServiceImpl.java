package com.xuyuchao.ucenterService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xuyuchao.baseService.exceptionhandler.GuliException;
import com.xuyuchao.commonUtils.JwtUtils;
import com.xuyuchao.commonUtils.MD5;
import com.xuyuchao.ucenterService.entity.UcenterMember;
import com.xuyuchao.ucenterService.entity.vo.LoginVo;
import com.xuyuchao.ucenterService.entity.vo.RegisterVo;
import com.xuyuchao.ucenterService.mapper.UcenterMemberMapper;
import com.xuyuchao.ucenterService.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author xuyuchao
 * @since 2022-07-02
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Resource
    private RedisTemplate redisTemplate;
    @Override
    public String login(LoginVo loginVo) {
        //获取登录邮箱和密码
        String email = loginVo.getEmail();
        String password = loginVo.getPassword();

        //邮箱和密码非空判断
        if(StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001,"登录失败");
        }
        //判断邮箱是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        UcenterMember member = baseMapper.selectOne(wrapper);
        //判断查询对象是否为空
        if(member == null) {//没有这个邮箱
            throw new GuliException(20001,"邮箱不存在");
        }

        //判断密码
        //因为存储到数据库密码肯定加密的
        //把输入的密码进行加密，再和数据库密码进行比较
        //加密方式 MD5
        if(!MD5.encrypt(password).equals(member.getPassword())) {
            throw new GuliException(20001,"密码错误");
        }

        //判断用户是否禁用
        if(member.getIsDisabled()) {
            throw new GuliException(20001,"用户已被禁用");
        }

        //登录成功
        //生成token字符串，使用jwt工具类
        String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return jwtToken;
    }

    /**
     * 用户注册
     * @param registerVo
     */
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册的数据
        String code = registerVo.getCode(); //验证码
        String email = registerVo.getEmail(); //邮箱
        String nickname = registerVo.getNickname(); //昵称
        String password = registerVo.getPassword(); //密码

        //非空判断
        if(StringUtils.isEmpty(email) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)) {
            throw new GuliException(20001,"注册失败");
        }
        //判断验证码
        //获取redis验证码
        String redisCode = (String) redisTemplate.opsForValue().get(email);
        if(!code.equals(redisCode)) {
            throw new GuliException(20001,"注册失败");
        }

        //判断邮箱是否重复，表里面存在相同邮箱不进行添加
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0) {
            throw new GuliException(20001,"注册失败");
        }

        //数据添加数据库中
        UcenterMember member = new UcenterMember();
        member.setEmail(email);
        member.setNickname(nickname);

        member.setPassword(MD5.encrypt(password));//密码需要加密的
        member.setIsDisabled(false);//用户不禁用
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(member);
    }

    /**
     * 根据openid判断
     * @param openid
     * @return
     */
    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }

    /**
     * 查询某一天注册人数
     * @param day
     * @return
     */
    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
