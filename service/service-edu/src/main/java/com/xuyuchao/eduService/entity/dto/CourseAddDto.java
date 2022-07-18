package com.xuyuchao.eduService.entity.dto;

import com.xuyuchao.eduService.entity.EduCourse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: xuyuchao
 * @Date: 2022-06-26-11:57
 * @Description: 前端传过来的添加课程信息的数据,封装dto对象并添加一个属性
 */
@Data
public class CourseAddDto extends EduCourse {
    @ApiModelProperty(value = "课程简介")
    private String description;
}
