package com.xuyuchao.eduService.entity.chapter;

import lombok.Data;

/**
 * @Author: xuyuchao
 * @Date: 2022-06-27-0:41
 * @Description:
 */
@Data
public class VideoVo {
    private String id;
    private String title;
    private Integer isFree;
    private String videoSourceId;
}
