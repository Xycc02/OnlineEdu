package com.xuyuchao.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuyuchao.baseService.exceptionhandler.GuliException;
import com.xuyuchao.commonUtils.R;
import com.xuyuchao.eduService.client.VodClient;
import com.xuyuchao.eduService.entity.*;
import com.xuyuchao.eduService.entity.dto.CourseAddDto;
import com.xuyuchao.eduService.entity.frontvo.course.CourseFrontVo;
import com.xuyuchao.eduService.entity.frontvo.course.CourseWebVo;
import com.xuyuchao.eduService.entity.vo.CourseQueryVo;
import com.xuyuchao.eduService.entity.vo.PublishCourseVo;
import com.xuyuchao.eduService.mapper.EduCourseDescriptionMapper;
import com.xuyuchao.eduService.mapper.EduCourseMapper;
import com.xuyuchao.eduService.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author xuyuchao
 * @since 2022-06-26
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Resource
    private EduCourseDescriptionMapper eduCourseDescriptionMapper;
    @Resource
    private EduTeacherService eduTeacherService;
    @Resource
    private EduSubjectService eduSubjectService;
    @Resource
    private EduVideoService eduVideoService;
    @Resource
    private EduChapterService eduChapterService;
    @Resource
    private VodClient vodClient;
    /**
     *添加课程并添加课程对应课程简介(两张表edu_course,edu_course_description)
     * @param courseAddDto
     */
    @Override
    @Transactional
    public String saveCourseWithDescription(CourseAddDto courseAddDto) {
        //添加课程
        boolean insert = this.save(courseAddDto);
        if(!insert) {
            throw new GuliException(20001,"添加课程信息失败!");
        }
        //添加课程简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseAddDto.getId());
        eduCourseDescription.setDescription(courseAddDto.getDescription());
        eduCourseDescriptionMapper.insert(eduCourseDescription);

        return courseAddDto.getId();
    }

    /**
     * 修改课程以及简介
     * @param courseAddDto
     */
    @Override
    @Transactional
    public void updateCourse(CourseAddDto courseAddDto) {
        //修改课程
        this.updateById(courseAddDto);
        //修改课程简介
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setId(courseAddDto.getId());
        courseDescription.setDescription(courseAddDto.getDescription());
        eduCourseDescriptionMapper.updateById(courseDescription);
    }

    /**
     * 根据id获取课程信息
     * @param id
     * @return
     */
    @Override
    public CourseAddDto getCourseInfo(String id) {
        CourseAddDto courseAddDto = new CourseAddDto();
        EduCourse eduCourse = this.getById(id);
        BeanUtils.copyProperties(eduCourse, courseAddDto);
        //获取课程id对应的课程简介
        EduCourseDescription courseDescription = eduCourseDescriptionMapper.selectById(id);
        courseAddDto.setDescription(courseDescription.getDescription());
        return courseAddDto;
    }

    /**
     * 删除课程以及课程对应阿里云小节视频
     * @param id
     */
    @Override
    @Transactional
    public void deleteCourse(String id) {
        //删除所有章节
        LambdaQueryWrapper<EduChapter> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(EduChapter::getCourseId,id);
        eduChapterService.remove(queryWrapper1);
        //根据课程id获取所有小节信息
        LambdaQueryWrapper<EduVideo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduVideo::getCourseId,id);
        List<EduVideo> videos = eduVideoService.list(queryWrapper);
        //删除所有小节
        eduVideoService.remove(queryWrapper);
        //将小节信息视频id封装成list集合
        List<String> list = new ArrayList<>();
        list = videos.stream().map(item -> {
            return item.getVideoSourceId();
        }).collect(Collectors.toList());
        //远程调用
        R res = vodClient.deleteBatch(list);
        if(!res.getSuccess()) {
            throw new GuliException(20001,"删除视频失败,熔断器...");
        }
        //删除课程
        this.removeById(id);
    }

    /**
     * 获取发布时的课程信息
     * @param id
     * @return
     */
    @Override
    public PublishCourseVo getPublishCourse(String id) {
        PublishCourseVo publishCourseVo = new PublishCourseVo();
        //根据id获取课程信息
        EduCourse eduCourse = this.getById(id);
        BeanUtils.copyProperties(eduCourse,publishCourseVo);
        //根据讲师id获取讲师名称
        LambdaQueryWrapper<EduTeacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduTeacher::getId,eduCourse.getTeacherId());
        EduTeacher teacher = eduTeacherService.getOne(queryWrapper);
        publishCourseVo.setTeacherName(teacher.getName());
        //根据二级课程id获取分类名称
        LambdaQueryWrapper<EduSubject> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(EduSubject::getId,eduCourse.getSubjectId());
        EduSubject twoSubject = eduSubjectService.getOne(queryWrapper1);
        publishCourseVo.setSubjectLevelTwo(twoSubject.getTitle());
        //根据一级课程id获取分类名称
        LambdaQueryWrapper<EduSubject> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(EduSubject::getId,eduCourse.getSubjectParentId());
        EduSubject oneSubject = eduSubjectService.getOne(queryWrapper2);
        publishCourseVo.setSubjectLevelOne(oneSubject.getTitle());
        return publishCourseVo;
    }

    /**
     * 发布课程
     * @param id
     */
    @Override
    public void publishCourse(String id) {
        //根据id查询课程信息
        EduCourse course = this.getById(id);
        if("Normal".equals(course.getStatus())) {
            throw new GuliException(20001,"课程已是发布状态!");
        }
        course.setStatus("Normal");
        this.updateById(course);
    }

    /**
     * 分页   条件查询    课程列表
     * @param page
     * @param pageSize
     * @param courseQueryVo
     * @return
     */
    @Override
    public Map<String,Object> getListCourse(Integer page, Integer pageSize, CourseQueryVo courseQueryVo) {
        //分页构造器
        Page<EduCourse> pageInfo = new Page<>(page,pageSize);
        //封装条件(如果存在)
        LambdaQueryWrapper<EduCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(!StringUtils.isEmpty(courseQueryVo.getTitle()),EduCourse::getTitle,courseQueryVo.getTitle())
                .like(!StringUtils.isEmpty(courseQueryVo.getStatus()),EduCourse::getStatus,courseQueryVo.getStatus())
                .orderByDesc(EduCourse::getGmtModified);
        //分页查询
        this.page(pageInfo,queryWrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("total",pageInfo.getTotal());
        map.put("courseList",pageInfo.getRecords());
        return map;
    }

    /**
     * 条件查询带分页查询课程(前台)
     * @param pageCourse
     * @param courseFrontVo
     * @return
     */
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo) {
        LambdaQueryWrapper<EduCourse> queryWrapper = new LambdaQueryWrapper<>();
        //封装查询信息
        queryWrapper.eq(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId()),EduCourse::getSubjectParentId,courseFrontVo.getSubjectParentId())
                .eq(!StringUtils.isEmpty(courseFrontVo.getSubjectId()),EduCourse::getSubjectId,courseFrontVo.getSubjectId())
                .orderByDesc(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort()),EduCourse::getBuyCount)
                .orderByDesc(!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort()),EduCourse::getGmtCreate)
                .orderByDesc(!StringUtils.isEmpty(courseFrontVo.getPriceSort()),EduCourse::getPrice);

        baseMapper.selectPage(pageCourse,queryWrapper);

        List<EduCourse> records = pageCourse.getRecords();
        long current = pageCourse.getCurrent();
        long pages = pageCourse.getPages();
        long size = pageCourse.getSize();
        long total = pageCourse.getTotal();
        boolean hasNext = pageCourse.hasNext();//下一页
        boolean hasPrevious = pageCourse.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        //map返回
        return map;
    }

    /**
     * 根据课程id获得详情的方法
     * @param courseId
     * @return
     */
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        CourseWebVo courseWebVo = baseMapper.getBaseCourseInfo(courseId);
        return courseWebVo;
    }
}
