package com.xuyuchao.eduService.entity.frontvo.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xuyuchao
 * @Date: 2022-07-04-20:38
 * @Description:
 */
@Data
public class OneSubjectVo {
    private String id;
    private String title;

    List<TwoSubjectVo> children = new ArrayList<>();
}
