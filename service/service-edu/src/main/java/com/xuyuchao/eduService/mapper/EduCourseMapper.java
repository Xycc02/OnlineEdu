package com.xuyuchao.eduService.mapper;

import com.xuyuchao.eduService.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuyuchao.eduService.entity.frontvo.course.CourseWebVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author xuyuchao
 * @since 2022-06-26
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    //根据课程id获取课程详细信息(前台课程详情页)
    CourseWebVo getBaseCourseInfo(@Param("courseId") String courseId);
}
