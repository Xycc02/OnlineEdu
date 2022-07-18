package com.xuyuchao.eduService.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuyuchao.eduService.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xuyuchao.eduService.entity.vo.TeacherQueryVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author xuyuchao
 * @since 2022-06-20
 */
public interface EduTeacherService extends IService<EduTeacher> {
    //查询所有讲师业务
    List<EduTeacher> getAll();
    //根据id删除教师业务
    boolean deleteTeacher(String id);
    //分页查询教师业务
    Map<String,Object> pageListTeacher(Integer page,Integer pageSize);
    //分页并条件模糊查询教师业务
    Map<String,Object> pageTeacherCondition(Integer page, Integer pageSize, TeacherQueryVo teacherQueryVo);
    //添加讲师业务
    boolean addTeacher(EduTeacher eduTeacher);
    //根据讲师id查询讲师业务
    EduTeacher getTeacher(Long id);
    //根据讲师id修改讲师业务
    boolean updateTeacher(EduTeacher teacher);
    //前台分页查询讲师
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageInfo);
}
