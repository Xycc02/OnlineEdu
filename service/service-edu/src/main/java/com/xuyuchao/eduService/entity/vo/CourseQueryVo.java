package com.xuyuchao.eduService.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: xuyuchao
 * @Date: 2022-06-27-23:40
 * @Description:
 */
@Data
public class CourseQueryVo {
    @ApiModelProperty(value = "课程标题")
    private String title;
    @ApiModelProperty(value = "课程状态 Draft未发布  Normal已发布")
    private String status;
}
