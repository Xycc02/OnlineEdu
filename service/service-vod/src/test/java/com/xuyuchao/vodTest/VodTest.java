package com.xuyuchao.vodTest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;
import org.junit.Test;

import java.util.List;

/**
 * @Author: xuyuchao
 * @Date: 2022-06-28-11:00
 * @Description:
 */
public class VodTest {

    @Test
    public void uploadVideo() {
        String accessKeyId = "LTAI5tN7m8QobbR4HuMB9QbL";
        String accessKeySecret = "w1NrDySS5q6iJ8Qc3JY2u9H3p67eSf";

        String title = "6 - What If I Want to Move Faster - upload by sdk";   //上传之后文件名称
        String fileName = "E:\\Java微服务\\谷粒学苑项目资料\\项目资料\\1-阿里云上传测试视频\\6 - What If I Want to Move Faster.mp4";  //本地文件路径和名称
        //上传视频的方法
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);

        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    //1 根据视频iD获取视频播放凭证(视频加密,通过凭证和专用播放器播放)
    @Test
    public void getPlayAuth() throws Exception{

        DefaultAcsClient client = InitVod.initVodClient("LTAI5tN7m8QobbR4HuMB9QbL", "w1NrDySS5q6iJ8Qc3JY2u9H3p67eSf");

        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        request.setVideoId("772a082bbb814396af7a8a615eec0b5a");

        response = client.getAcsResponse(request);
        System.out.println("playAuth:"+response.getPlayAuth());
    }
    //1 根据视频iD获取视频播放地址(可根据地址直接播放)
    @Test
    public void getPlayUrl() throws Exception{
        //创建初始化对象
        DefaultAcsClient client = InitVod.initVodClient("LTAI5tN7m8QobbR4HuMB9QbL", "w1NrDySS5q6iJ8Qc3JY2u9H3p67eSf");

        //创建获取视频地址request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        //向request对象里面设置视频id
        request.setVideoId("474be24d43ad4f76af344b9f4daaabd1");

        //调用初始化对象里面的方法，传递request，获取数据
        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }
}
