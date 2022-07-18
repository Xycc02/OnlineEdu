package com.xuyuchao.ossService.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.xuyuchao.ossService.service.OssService;
import com.xuyuchao.ossService.utils.ConstantPropertiesUtils;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * @Author: xuyuchao
 * @Date: 2022-06-24-20:42
 * @Description:
 */
@Service
@Slf4j
public class OssServiceImpl implements OssService {
    /**
     * 文件上传
     * @param file
     */
    @Override
    public String uploadOssAvatar(MultipartFile file) {
        //获取配置类阿里云oss信息
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        OSS ossClient = null;
        try {
            //创建OSSClient实例
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            //得到文件输入流
            InputStream inputStream = file.getInputStream();
            //得到上传过来的文件名,利用uuid或者时间戳,避免覆盖
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            String fileName = uuid + file.getOriginalFilename();
            //利用joda-time工具类转换时间格式并在oss中建立文件夹便于分类
            String datePath = new DateTime().toString("yyyy/MM/dd");
            //将日期格式化后的路径拼接到文件名
            fileName = datePath + fileName;
            //上传文件到oss
            ossClient.putObject(bucketName,fileName,inputStream);
            // https://xuyuchao-guliedu.oss-cn-hangzhou.aliyuncs.com/%E5%A4%B4%E5%83%8F.jpg
            //拼接文件url
            String avatarUrl = "https://" + bucketName + "." + endpoint + "/" + fileName;
            return avatarUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return "获取文件路径失败!";
        }finally {
            if(ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}
