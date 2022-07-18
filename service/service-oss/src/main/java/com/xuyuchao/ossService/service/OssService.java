package com.xuyuchao.ossService.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: xuyuchao
 * @Date: 2022-06-24-20:41
 * @Description:
 */
public interface OssService {
    //上传文件
    String uploadOssAvatar(MultipartFile file);
}
