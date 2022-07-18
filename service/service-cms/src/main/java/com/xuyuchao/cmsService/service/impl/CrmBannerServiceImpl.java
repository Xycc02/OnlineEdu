package com.xuyuchao.cmsService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xuyuchao.cmsService.entity.CrmBanner;
import com.xuyuchao.cmsService.mapper.CrmBannerMapper;
import com.xuyuchao.cmsService.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author xuyuchao
 * @since 2022-06-29
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {
    //查询根据sort前五条banner
    @Cacheable(value = "banner",key = "'selectIndexList'")
    @Override
    public List<CrmBanner> selectAllBanner() {
        //根据id进行降序排列，显示排列之后前两条记录
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort");
        //last方法，拼接sql语句
        wrapper.last("limit 5");
        List<CrmBanner> list = baseMapper.selectList(wrapper);
        return list;
    }
}
