package com.xuyuchao.staService.controller;


import com.xuyuchao.commonUtils.R;
import com.xuyuchao.staService.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author xuyuchao
 * @since 2022-07-13
 */
@RestController
@RequestMapping("/staService/statistics-daily")

public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService staService;

    /**
     * 统计某一天注册人数,生成统计数据
     * @param day
     * @return
     */
    @PostMapping("registerCount/{day}")
    public R registerCount(@PathVariable String day) {
        staService.registerCount(day);
        return R.ok();
    }

    /**
     * 图表显示，返回两部分数据，日期json数组，数量json数组
     * @param type
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type,@PathVariable String begin,
                      @PathVariable String end) {
        Map<String,Object> map = staService.getShowData(type,begin,end);
        return R.ok().data(map);
    }
}

