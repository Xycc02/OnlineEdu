package com.xuyuchao.vodService.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.xuyuchao.baseService.exceptionhandler.GuliException;
import com.xuyuchao.commonUtils.R;
import com.xuyuchao.vodService.service.VodService;
import com.xuyuchao.vodService.utils.ConstantVodUtils;
import com.xuyuchao.vodService.utils.InitVodCilent;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: xuyuchao
 * @Date: 2022-06-28-14:43
 * @Description:
 */
@RestController
@RequestMapping("/eduvod/video")

public class VodController {

    @Resource
    private VodService vodService;

    /**
     * 上传视频到阿里云
     * @param file
     * @return
     */
    @PostMapping("/uploadAlyVideo")
    public R uploadAlyVideo(MultipartFile file) {
        String videoId = vodService.uploadAlyVideo(file);
        return R.ok().data("videoId",videoId);
    }

    /**
     * 根据视频id删除阿里云视频
     * @param id
     * @return
     */
    @DeleteMapping("removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable String id) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodCilent.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request设置视频id
            request.setVideoIds(id);
            //调用初始化对象的方法实现删除
            client.getAcsResponse(request);
            return R.ok();
        }catch(Exception e) {
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
    }

    /**
     * 删除多个阿里云视频的方法
     * 参数多个视频id  List videoIdList
     * @param videoIdList
     * @return
     */
    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList) {
        try {
            vodService.removeMoreAlyVideo(videoIdList);
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
    }

    /**
     * 根据视频id获取视频播放凭证,用于播放加密视频
     * @param videoId
     * @return
     */
    @GetMapping("getPlayAuth/{videoId}")
    public R getPlayAuth(@PathVariable String videoId) {
        String playAuth = vodService.getPlayAuth(videoId);
        return R.ok().data("playAuth",playAuth);
    }
}
