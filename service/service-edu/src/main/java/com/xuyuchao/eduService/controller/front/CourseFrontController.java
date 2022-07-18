package com.xuyuchao.eduService.controller.front;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuyuchao.commonUtils.R;
import com.xuyuchao.eduService.entity.EduCourse;
import com.xuyuchao.eduService.entity.chapter.ChapterVo;
import com.xuyuchao.eduService.entity.frontvo.course.CourseFrontVo;
import com.xuyuchao.eduService.entity.frontvo.course.CourseWebVo;
import com.xuyuchao.eduService.service.EduChapterService;
import com.xuyuchao.eduService.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")

public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    /**
     * 条件查询带分页查询课程(前台)
     * @param page
     * @param limit
     * @param courseFrontVo
     * @return
     */
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> pageCourse = new Page<>(page,limit);
        Map<String,Object> map = courseService.getCourseFrontList(pageCourse,courseFrontVo);
        //返回分页所有数据
        return R.ok().data(map);
    }

    /**
     * 根据课程id获得详情的方法
     * @param courseId
     * @return
     */
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId) {
        //根据课程id，编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        //根据课程id查询章节和小节
        List<ChapterVo> chapterVideoList = chapterService.getChapterVedioList(courseId);

        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList);
    }
}












