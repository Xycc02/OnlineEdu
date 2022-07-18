package com.xuyuchao.eduService.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuyuchao.eduService.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xuyuchao.eduService.entity.dto.CourseAddDto;
import com.xuyuchao.eduService.entity.frontvo.course.CourseFrontVo;
import com.xuyuchao.eduService.entity.frontvo.course.CourseWebVo;
import com.xuyuchao.eduService.entity.vo.CourseQueryVo;
import com.xuyuchao.eduService.entity.vo.PublishCourseVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author xuyuchao
 * @since 2022-06-26
 */
public interface EduCourseService extends IService<EduCourse> {
    //添加课程并添加课程对应课程简介(两张表edu_course,edu_course_description)
    String saveCourseWithDescription(CourseAddDto courseAddDto);
    //修改课程
    void updateCourse(CourseAddDto courseAddDto);
    //根据id获取课程信息
    CourseAddDto getCourseInfo(String id);
    //发布课程
    void publishCourse(String id);
    //获取发布时的课程信息
    PublishCourseVo getPublishCourse(String id);
    //获取课程信息列表
    Map<String,Object> getListCourse(Integer page, Integer pageSize, CourseQueryVo courseQueryVo);
    //删除课程
    void deleteCourse(String id);
    //条件查询带分页查询课程前台
    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);
    //根据课程id，编写sql语句查询课程信息
    CourseWebVo getBaseCourseInfo(String courseId);
}
