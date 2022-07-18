package com.xuyuchao.cmsService.controller;

import com.xuyuchao.cmsService.entity.CrmBanner;
import com.xuyuchao.cmsService.service.CrmBannerService;
import com.xuyuchao.commonUtils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: xuyuchao
 * @Date: 2022-06-29-20:30
 * @Description:
 */
@RestController
@RequestMapping("/cmsService/crm-bannerFront")

public class CrmBannerFrontController {
    @Autowired
    private CrmBannerService bannerService;

    //查询根据sort前五条banner
    @GetMapping("getAllBanner")
    public R getAllBanner() {
        List<CrmBanner> list = bannerService.selectAllBanner();
        return R.ok().data("list",list);
    }
}
