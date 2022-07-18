package com.xuyuchao.orderService.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuyuchao.baseService.exceptionhandler.GuliException;
import com.xuyuchao.commonUtils.R;
import com.xuyuchao.orderService.client.EduClient;
import com.xuyuchao.orderService.client.UcenterClient;
import com.xuyuchao.orderService.entity.TOrder;
import com.xuyuchao.orderService.entity.ordervo.CourseWebVoOrder;
import com.xuyuchao.orderService.entity.ordervo.UcenterMemberOrder;
import com.xuyuchao.orderService.mapper.TOrderMapper;
import com.xuyuchao.orderService.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuyuchao.orderService.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author xuyuchao
 * @since 2022-07-10
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;

    /**
     * 生成订单的方法
     * @param courseId
     * @param memberId
     * @return
     */
    @Override
    @Transactional
    public String createOrders(String courseId, String memberId) {
        //通过远程调用根据用户id获取用户信息
        R memberResult = ucenterClient.getMemberById(memberId);
        if(!memberResult.getSuccess()) {
            throw new GuliException(20001,"获取订单用户信息失败!");
        }
        //将获取到的R中的用户信息转换为UcenterMemberOrder对象
        UcenterMemberOrder userInfoOrder = JSON.parseObject(JSON.toJSONString(memberResult.getData().get("userInfo")), UcenterMemberOrder.class);

        //通过远程调用根据课程id获取课程信息
        R courseResult = eduClient.getFrontCourseInfo(courseId);
        if(!courseResult.getSuccess()) {
            throw new GuliException(20001,"获取订单课程信息失败!");
        }
        CourseWebVoOrder courseInfoOrder = JSON.parseObject(JSON.toJSONString(courseResult.getData().get("courseWebVo")), CourseWebVoOrder.class);

        //创建Order对象，向order对象里面设置需要数据
        TOrder order = new TOrder();
        order.setOrderNo(OrderNoUtil.getOrderNo());//订单号
        order.setCourseId(courseId); //课程id
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        order.setStatus(0);  //订单状态（0：未支付 1：已支付）
        order.setPayType(1);  //支付类型 ，微信1
        baseMapper.insert(order);
        //返回订单号
        return order.getOrderNo();
    }

    /**
     * 根据课程id和用户id获取收费课程是否购买
     * @param memberId
     * @param courseId
     * @return
     */
    @Override
    public boolean getCourseStatus(String memberId, String courseId) {
        LambdaQueryWrapper<TOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TOrder::getMemberId,memberId)
                .eq(TOrder::getCourseId,courseId)
                .eq(TOrder::getStatus,1);
        int res = baseMapper.selectCount(queryWrapper);
        if(res >= 1) {
            return true;
        }
        return false;
    }
}
