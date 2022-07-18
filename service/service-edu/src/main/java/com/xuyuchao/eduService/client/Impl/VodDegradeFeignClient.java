package com.xuyuchao.eduService.client.Impl;

import com.xuyuchao.commonUtils.R;
import com.xuyuchao.eduService.client.VodClient;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: xuyuchao
 * @Date: 2022-06-29-11:13
 * @Description:    vod-service服务降级
 */
@Component
public class VodDegradeFeignClient implements VodClient {
    @Override
    public R removeAlyVideo(String id) {
        return R.error().message("删除单个视频出错了!");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除多个视频出错了!");
    }

}
