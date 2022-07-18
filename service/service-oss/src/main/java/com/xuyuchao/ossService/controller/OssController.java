package com.xuyuchao.ossService.controller;

import com.xuyuchao.commonUtils.R;
import com.xuyuchao.ossService.service.OssService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Author: xuyuchao
 * @Date: 2022-06-24-20:41
 * @Description:
 */
@RequestMapping("/eduoss/fileoss")
@RestController

public class OssController {

    @Resource
    private OssService ossService;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R uploadOssAvatar(MultipartFile file) {
        String avatarUrl = ossService.uploadOssAvatar(file);
        return R.ok().data("avatarUrl",avatarUrl);
    }
}
