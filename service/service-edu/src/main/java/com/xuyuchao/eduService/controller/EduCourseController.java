package com.xuyuchao.eduService.controller;


import com.xuyuchao.commonUtils.R;
import com.xuyuchao.eduService.entity.EduCourse;
import com.xuyuchao.eduService.entity.dto.CourseAddDto;
import com.xuyuchao.eduService.entity.vo.CourseQueryVo;
import com.xuyuchao.eduService.entity.vo.PublishCourseVo;
import com.xuyuchao.eduService.service.EduCourseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author xuyuchao
 * @since 2022-06-26
 */
@RestController
@RequestMapping("/eduService/edu-course")

public class EduCourseController {
    @Resource
    private EduCourseService eduCourseService;

    /**
     * 添加课程
     * @param courseAddDto
     * @return
     */
    @PostMapping("/addCourse")
    public R addCourse(@RequestBody CourseAddDto courseAddDto) {
        //返回课程id,给前端添加课程大纲使用
        String courseId = eduCourseService.saveCourseWithDescription(courseAddDto);
        return R.ok().data("courseId",courseId);
    }

    /**
     * 修改课程
     * @param courseAddDto
     * @return
     */
    @PutMapping("/updateCourse")
    public R updateCourse(@RequestBody CourseAddDto courseAddDto) {
        eduCourseService.updateCourse(courseAddDto);
        return R.ok();
    }

    /**
     * 根据id获取课程信息
     * @param id
     * @return
     */
    @GetMapping("/getCourse/{id}")
    public R getCourseInfo(@PathVariable String id) {
        CourseAddDto courseInfo = eduCourseService.getCourseInfo(id);
        return R.ok().data("courseInfo",courseInfo);
    }

    /**
     * 课程删除
     * @param id
     * @return
     */
    @DeleteMapping("/deleteCourse/{id}")
    public R deleteCourse(@PathVariable String id) {
        eduCourseService.deleteCourse(id);
        return R.ok();
    }
    /**
     * 获取发布时的课程信息
     * @param id
     * @return
     */
    @GetMapping("/publishCourse/{id}")
    public R getPublishCourse(@PathVariable String id) {
        PublishCourseVo publishCourse = eduCourseService.getPublishCourse(id);
        return R.ok().data("publishCourse",publishCourse);
    }

    /**
     * 发布课程
     * @param id
     * @return
     */
    @PostMapping("/publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        eduCourseService.publishCourse(id);
        return R.ok();
    }

    /**
     * 获取课程列表
     * @return
     */
    @PostMapping("/getListCourse/{page}/{pageSize}")
    public R getListCourse(@PathVariable Integer page,
                           @PathVariable Integer pageSize,
                           @RequestBody(required = false) CourseQueryVo courseQueryVo) {
        Map<String,Object> map = eduCourseService.getListCourse(page,pageSize,courseQueryVo);
        return R.ok().data("page",map);
    }
}

