package com.xuyuchao.eduService.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xuyuchao
 * @Date: 2022-06-27-0:40
 * @Description:
 */
@Data
public class ChapterVo {
    private String id;
    private String title;
    //每个章节中有多个小节
    private List<VideoVo> children = new ArrayList<>();
}
