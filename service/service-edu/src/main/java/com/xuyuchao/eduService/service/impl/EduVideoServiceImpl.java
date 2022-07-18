package com.xuyuchao.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuyuchao.baseService.exceptionhandler.GuliException;
import com.xuyuchao.commonUtils.R;
import com.xuyuchao.eduService.client.VodClient;
import com.xuyuchao.eduService.entity.EduVideo;
import com.xuyuchao.eduService.mapper.EduVideoMapper;
import com.xuyuchao.eduService.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author xuyuchao
 * @since 2022-06-26
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    @Resource
    private VodClient vodClient;

    /**
     * 添加小节
     * @param eduVideo
     */
    @Override
    public void addVideo(EduVideo eduVideo) {
        this.save(eduVideo);
    }

    /**
     * 删除小节，删除对应阿里云视频
     * @param id
     */
    @Override
    @Transactional
    public void deleteVideo(String id) {
        //根据小节id获取视频id
        EduVideo video = this.getById(id);
        String videoSourceId = video.getVideoSourceId();
        //删除小节
        this.removeById(id);
        //删除小节视频
        if(videoSourceId != null) {
            R res = vodClient.removeAlyVideo(videoSourceId);
            if(!res.getSuccess()) {
                throw new GuliException(20001,"删除视频失败,熔断器...");
            }
        }
    }

    /**
     * 根据小节id获得小节信息
     * @param id
     * @return
     */
    @Override
    public EduVideo getVideoById(String id) {
        EduVideo video = this.getById(id);
        return video;
    }

    /**
     * 修改小节信息
     */
    @Override
    public void updateVideo(EduVideo video) {
        this.updateById(video);
    }
}
