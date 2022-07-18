package com.xuyuchao.orderService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xuyuchao.commonUtils.JwtUtils;
import com.xuyuchao.commonUtils.R;
import com.xuyuchao.orderService.entity.TOrder;
import com.xuyuchao.orderService.service.TOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author xuyuchao
 * @since 2022-07-10
 */
@RestController
@RequestMapping("/orderService/t-order")

public class TOrderController {
    @Autowired
    private TOrderService tOrderService;

    //1 生成订单的方法
    @PostMapping("createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request) {
        //创建订单，返回订单号
        String orderNo =
                tOrderService.createOrders(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId",orderNo);
    }

    //2 根据订单id查询订单信息
    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId) {
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        TOrder order = tOrderService.getOne(wrapper);
        return R.ok().data("item",order);
    }

    /**
     * 根据课程id和用户id获取收费课程是否购买
     * @param request
     * @param courseId
     * @return
     */
    @GetMapping("getCourseStatus/{courseId}")
    public R getCourseStatus(HttpServletRequest request,@PathVariable String courseId) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        boolean isBuy = tOrderService.getCourseStatus(memberId,courseId);
        if(isBuy) {
            return R.ok().message("该收费课程已购买,请尽情观看!");
        }
        return R.error().message("该收费课程未购买,请购买后再观看!");
    }
}

