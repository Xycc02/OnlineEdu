package com.xuyuchao.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuyuchao.eduService.entity.EduTeacher;
import com.xuyuchao.eduService.entity.vo.TeacherQueryVo;
import com.xuyuchao.eduService.mapper.EduTeacherMapper;
import com.xuyuchao.eduService.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author xuyuchao
 * @since 2022-06-20
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    /**
     * 查询所有讲师
     * @return
     */
    @Override
    public List<EduTeacher> getAll() {
        List<EduTeacher> teachers = this.list(null);
        return teachers;
    }

    /**
     * 根据id删除教师
     * @param id
     * @return
     */
    @Override
    public boolean deleteTeacher(String id) {
        boolean flag = this.removeById(id);
        return flag;
    }

    /**
     * 分页查询教师
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public Map<String, Object> pageListTeacher(Integer page, Integer pageSize) {
        Page<EduTeacher> pageInfo = new Page<>(page,pageSize);
        LambdaQueryWrapper<EduTeacher> queryWrapper = new LambdaQueryWrapper<>();
        this.page(pageInfo,queryWrapper);
        Long total = pageInfo.getTotal();
        List<EduTeacher> records = pageInfo.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows",records);
        return map;
    }

    /**
     * 分页并模糊查询教师
     * @param page
     * @param pageSize
     * @param teacherQueryVo
     * @return
     */
    @Override
    public Map<String, Object> pageTeacherCondition(Integer page, Integer pageSize, TeacherQueryVo teacherQueryVo) {
        Page<EduTeacher> pageInfo = new Page<>(page,pageSize);
        LambdaQueryWrapper<EduTeacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(!StringUtils.isEmpty(teacherQueryVo.getName()), EduTeacher::getName, teacherQueryVo.getName())
                .eq(!StringUtils.isEmpty(teacherQueryVo.getLevel()),EduTeacher::getLevel, teacherQueryVo.getLevel())
                .ge(!StringUtils.isEmpty(teacherQueryVo.getBegin()),EduTeacher::getGmtCreate, teacherQueryVo.getBegin())
                .le(!StringUtils.isEmpty(teacherQueryVo.getEnd()),EduTeacher::getGmtCreate, teacherQueryVo.getEnd())
                .orderByDesc(EduTeacher::getGmtModified);
        this.page(pageInfo,queryWrapper);
        Long total = pageInfo.getTotal();
        List<EduTeacher> records = pageInfo.getRecords();
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows",records);
        return map;
    }

    /**
     * 添加讲师
     * @param eduTeacher
     * @return
     */
    @Override
    public boolean addTeacher(EduTeacher eduTeacher) {
        boolean flag = this.save(eduTeacher);
        return flag;
    }

    /**
     * 根据讲师id查询讲师
     * @param id
     * @return
     */
    @Override
    public EduTeacher getTeacher(Long id) {
        EduTeacher teacher = this.getById(id);
        return teacher;
    }

    /**
     * 根据讲师id修改讲师
     * @param teacher
     * @return
     */
    @Override
    public boolean updateTeacher(EduTeacher teacher) {
        boolean flag = this.updateById(teacher);
        return flag;
    }

    /**
     * 前台分页查询讲师
     * @param pageInfo
     * @return
     */
    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageInfo) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //把分页数据封装到pageTeacher对象
        baseMapper.selectPage(pageInfo,wrapper);

        List<EduTeacher> records = pageInfo.getRecords();
        long current = pageInfo.getCurrent();
        long pages = pageInfo.getPages();
        long size = pageInfo.getSize();
        long total = pageInfo.getTotal();
        boolean hasNext = pageInfo.hasNext();//下一页
        boolean hasPrevious = pageInfo.hasPrevious();//上一页

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
}
