package com.xuyuchao.eduService.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuyuchao.commonUtils.R;
import com.xuyuchao.eduService.entity.EduCourse;
import com.xuyuchao.eduService.entity.EduTeacher;
import com.xuyuchao.eduService.service.EduCourseService;
import com.xuyuchao.eduService.service.EduTeacherService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author: xuyuchao
 * @Date: 2022-07-04-15:09
 * @Description:
 */
@RestController
@RequestMapping("/eduservice/teacherfront")

public class TeacherFrontController {
    @Resource
    private EduTeacherService eduTeacherService;
    @Resource
    private EduCourseService eduCourseService;

    /**
     * 前台分页查询讲师
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/getTeacherFrontList/{page}/{pageSize}")
    public R getTeacherFrontList(@PathVariable Integer page,@PathVariable Integer pageSize) {
        Page<EduTeacher> pageInfo = new Page<>(page,pageSize);
        Map<String,Object> map = eduTeacherService.getTeacherFrontList(pageInfo);
        return R.ok().data(map);
    }

    /**
     * 根据id获得讲师详情以及所讲课程
     * @param teacherId
     * @return
     */
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId) {
        //1 根据讲师id查询讲师基本信息
        EduTeacher eduTeacher = eduTeacherService.getById(teacherId);
        //2 根据讲师id查询所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> courseList = eduCourseService.list(wrapper);
        return R.ok().data("teacher",eduTeacher).data("courseList",courseList);
    }
}
