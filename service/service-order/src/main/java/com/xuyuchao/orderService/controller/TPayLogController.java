package com.xuyuchao.orderService.controller;


import com.xuyuchao.commonUtils.R;
import com.xuyuchao.orderService.service.TPayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author xuyuchao
 * @since 2022-07-10
 */
@RestController
@RequestMapping("/orderService/t-pay-log")

public class TPayLogController {
    @Autowired
    private TPayLogService tPayLogService;

    /**
     * 根据订单号生成支付二维码
     * @param orderNo
     * @return
     */
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo) {
        //返回信息，包含二维码地址，还有其他需要的信息
        Map map = tPayLogService.createNatvie(orderNo);
        System.out.println("****返回二维码map集合:"+map);
        return R.ok().data(map);
    }

    /**
     * 查询订单支付状态
     * 参数：订单号，根据订单号查询 支付状态
     * @param orderNo
     * @return
     */
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo) {
        //查询订单支付状态
        Map<String,String> map = tPayLogService.queryPayStatus(orderNo);
        System.out.println("*****查询订单状态map集合:"+map);
        if(map == null) {
            return R.error().message("支付出错了");
        }
        //如果返回map里面不为空，通过map获取订单状态
        if(map.get("trade_state").equals("SUCCESS")) {//支付成功
            //添加记录到支付表，更新订单表订单状态
            tPayLogService.updateOrdersStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中");

    }
}

