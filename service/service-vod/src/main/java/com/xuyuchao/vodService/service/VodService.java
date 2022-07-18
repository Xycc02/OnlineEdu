package com.xuyuchao.vodService.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: xuyuchao
 * @Date: 2022-06-28-14:44
 * @Description:
 */
public interface VodService {
    //上传视频到阿里云
    String uploadAlyVideo(MultipartFile file);
    //删除多个阿里云视频的方法
    void removeMoreAlyVideo(List videoIdList);
    //根据视频id获取视频播放凭证,用于播放加密视频
    String getPlayAuth(String videoId);
}
