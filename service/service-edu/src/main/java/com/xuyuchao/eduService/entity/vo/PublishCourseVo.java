package com.xuyuchao.eduService.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: xuyuchao
 * @Date: 2022-06-27-23:02
 * @Description:
 */
@Data
public class PublishCourseVo implements Serializable {
    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;
    @ApiModelProperty(value = "课程标题")
    private String title;
    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;
    @ApiModelProperty(value = "一级分类名")
    private String subjectLevelOne;
    @ApiModelProperty(value = "二级分类名")
    private String subjectLevelTwo;
    @ApiModelProperty(value = "讲师名称")
    private String teacherName;
    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    private BigDecimal price;
}
