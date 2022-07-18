package com.xuyuchao.eduService.controller;

import com.xuyuchao.commonUtils.R;
import com.xuyuchao.eduService.entity.EduTeacher;
import com.xuyuchao.eduService.entity.vo.TeacherQueryVo;
import com.xuyuchao.eduService.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author xuyuchao
 * @since 2022-06-20
 */
@RestController
@RequestMapping("/eduService/edu-teacher")

public class EduTeacherController {

    @Resource
    private EduTeacherService eduTeacherService;

    /**
     * 查询所有讲师接口
     * @return R
     */
    @ApiOperation("查询所有讲师接口")
    @GetMapping("/getAll")
    public R getAllTeacher() {
        List<EduTeacher> teachers = eduTeacherService.getAll();
        // try {
        //     int i = 10/0;
        // }catch (Exception e) {
        //     throw new GuliException(400,"出现了GuliException自定义异常!");
        // }
        return R.ok().data("teachers", teachers);
    }

    /**
     * 根据id删除教师接口
     * @param id
     * @return R
     */
    @ApiOperation(value = "根据id删除教师接口")
    @DeleteMapping("/{id}")
    public R deleteTeacher(@PathVariable String id) {
        boolean flag = eduTeacherService.deleteTeacher(id);
        if(flag) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 分页查询教师接口
     * @param page
     * @param pageSize
     * @return R
     */
    @ApiOperation(value = "分页查询教师接口")
    @GetMapping("pageTeacher/{page}/{pageSize}")
    public R pageListTeacher(@PathVariable Integer page,
                             @PathVariable Integer pageSize) {
        Map<String, Object> map = eduTeacherService.pageListTeacher(page, pageSize);
        return R.ok().data("page",map);
    }

    /**
     * 分页并模糊查询教师接口
     * @param page
     * @param pageSize
     * @param teacherQueryVo
     * @return R
     */
    @ApiOperation(value = "分页并条件模糊查询教师接口")
    @PostMapping("/pageTeacherCondition/{page}/{pageSize}")
    public R pageTeacherCondition(@PathVariable Integer page,
                                  @PathVariable Integer pageSize,
                                  @RequestBody(required = false) TeacherQueryVo teacherQueryVo) {
        Map<String, Object> map = eduTeacherService.pageTeacherCondition(page, pageSize, teacherQueryVo);
        return R.ok().data("page",map);
    }

    /**
     * 添加讲师接口
     * @param eduTeacher
     * @return R
     */
    @ApiOperation("添加讲师接口")
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = eduTeacherService.addTeacher(eduTeacher);
        if(flag) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 根据讲师id查询讲师接口
     * @param id
     * @return R
     */
    @ApiOperation("根据讲师id查询讲师接口")
    @GetMapping("/getTeacher/{id}")
    public R getTeacher(@PathVariable Long id) {
        EduTeacher teacher = eduTeacherService.getTeacher(id);
        return R.ok().data("teacher",teacher);
    }

    /**
     * 根据讲师id修改讲师接口
     * @param teacher
     * @return R
     */
    @ApiOperation("根据讲师id修改讲师接口")
    @PutMapping("/updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher teacher) {
        boolean flag = eduTeacherService.updateTeacher(teacher);
        if(flag) {
            return R.ok();
        }
        return R.error();
    }
}

