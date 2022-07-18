package com.xuyuchao.eduService.controller;


import com.xuyuchao.commonUtils.R;
import com.xuyuchao.eduService.entity.EduVideo;
import com.xuyuchao.eduService.service.EduVideoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author xuyuchao
 * @since 2022-06-26
 */
@RestController
@RequestMapping("/eduService/edu-video")

public class EduVideoController {
    @Resource
    private EduVideoService eduVideoService;

    /**
     * 添加小节
     * @param eduVideo
     * @return
     */
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.addVideo(eduVideo);
        return R.ok();
    }

    /**
     * 删除小节
     * @param id
     * @return
     */
    @DeleteMapping("/deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id) {
        eduVideoService.deleteVideo(id);
        return R.ok();
    }

    /**
     * 根据小节id获得小节信息
     * @param id
     * @return
     */
    @GetMapping("/getVideoById/{id}")
    public R getVideoById(@PathVariable String id) {
        EduVideo video = eduVideoService.getVideoById(id);
        return R.ok().data("video",video);
    }

    /**
     * 修改小节信息
     * @param video
     * @return
     */
        @PutMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo video) {
        eduVideoService.updateVideo(video);
        return R.ok();
    }
}

