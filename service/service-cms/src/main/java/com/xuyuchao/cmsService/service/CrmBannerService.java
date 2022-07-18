package com.xuyuchao.cmsService.service;

import com.xuyuchao.cmsService.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author xuyuchao
 * @since 2022-06-29
 */
public interface CrmBannerService extends IService<CrmBanner> {

    //查询根据sort前五条banner
    List<CrmBanner> selectAllBanner();
}
