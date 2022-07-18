package com.xuyuchao.eduService.service;

import com.xuyuchao.eduService.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author xuyuchao
 * @since 2022-06-26
 */
public interface EduVideoService extends IService<EduVideo> {
    //添加小节
    void addVideo(EduVideo eduVideo);
    //删除小节
    void deleteVideo(String id);
    //根据小节id获得小节信息
    EduVideo getVideoById(String id);
    //修改小节信息
    void updateVideo(EduVideo video);
}
