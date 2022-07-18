package com.xuyuchao.eduService.client;

import com.xuyuchao.commonUtils.R;
import com.xuyuchao.eduService.client.Impl.VodDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: xuyuchao
 * @Date: 2022-06-28-21:11
 * @Description:
 */
@FeignClient(name = "service-vod",fallback = VodDegradeFeignClient.class)
@Component
public interface VodClient {
    //根据小节id删除阿里云视频
    @DeleteMapping("/eduvod/video/removeAlyVideo/{id}")
    R removeAlyVideo(@PathVariable("id") String id);
    //删除课程时,删除所有对应小节阿里云视频
    @DeleteMapping("/eduvod/video/delete-batch")
    R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
