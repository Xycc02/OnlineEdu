package com.xuyuchao.orderService.service;

import com.xuyuchao.orderService.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author xuyuchao
 * @since 2022-07-10
 */
public interface TOrderService extends IService<TOrder> {
    //生成订单的方法
    String createOrders(String courseId, String memberIdByJwtToken);
    //根据课程id和用户id获取收费课程是否购买
    boolean getCourseStatus(String memberId, String courseId);
}
