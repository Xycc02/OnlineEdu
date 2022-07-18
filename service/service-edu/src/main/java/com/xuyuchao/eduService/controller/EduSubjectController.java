package com.xuyuchao.eduService.controller;


import com.xuyuchao.commonUtils.R;
import com.xuyuchao.eduService.entity.EduSubject;
import com.xuyuchao.eduService.service.EduSubjectService;
import com.xuyuchao.eduService.service.EduTeacherService;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author xuyuchao
 * @since 2022-06-25
 */
@RestController
@RequestMapping("/eduService/edu-subject")

public class EduSubjectController {
    @Resource
    private EduSubjectService eduSubjectService;

    /**
     * 添加课程分类
     * @param file
     * @return
     */
    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file) {
        eduSubjectService.saveSubject(file,eduSubjectService);
        return R.ok();
    }

    /**
     * 获取数据库中课程分类信息
     * @return
     */
    @GetMapping("/list")
    public R list() {
        List<Map<String, Object>> list = eduSubjectService.getList();
        return R.ok().data("subjectList",list);
    }

    /**
     * 获取所有一级分类
     * @return
     */
    @GetMapping("/getOneSubject")
    public R getOneSubject() {
        List<EduSubject> oneSubjectList = eduSubjectService.getOneSubject();
        return R.ok().data("oneSubjectList",oneSubjectList);
    }

    /**
     * 根据一级课程id获得二级课程集合
     * @param id
     * @return
     */
    @GetMapping("/getTwoSubject/{id}")
    public R getTwoSubject(@PathVariable String id) {
        List<EduSubject> twoSubjectList = eduSubjectService.getTwoSubject(id);
        return R.ok().data("twoSubjectList",twoSubjectList);
    }
}

