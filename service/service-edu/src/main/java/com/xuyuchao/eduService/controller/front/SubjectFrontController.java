package com.xuyuchao.eduService.controller.front;

import com.xuyuchao.commonUtils.R;
import com.xuyuchao.eduService.entity.frontvo.subject.OneSubjectVo;
import com.xuyuchao.eduService.service.EduSubjectService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: xuyuchao
 * @Date: 2022-07-04-20:41
 * @Description:
 */
@RestController
@RequestMapping("/eduservice/subjectfront")

public class SubjectFrontController {
    @Resource
    private EduSubjectService eduSubjectService;

    /**
     * 获取所有学科分类(一级,二级)前台
     * @return
     */
    @GetMapping("/getAllSubject")
    public R getAllSubject() {
        List<OneSubjectVo> list = eduSubjectService.getAllSubject();
        return R.ok().data("list",list);
    }
}
