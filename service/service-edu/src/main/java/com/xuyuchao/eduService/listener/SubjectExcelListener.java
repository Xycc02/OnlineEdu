package com.xuyuchao.eduService.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuyuchao.baseService.exceptionhandler.GuliException;
import com.xuyuchao.eduService.entity.EduSubject;
import com.xuyuchao.eduService.entity.excel.SubjectData;
import com.xuyuchao.eduService.service.EduSubjectService;

import java.util.Map;

/**
 * @Author: xuyuchao
 * @Date: 2022-06-25-13:03
 * @Description:
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    //SubjectExcelListener不能作为spring的组件,所以不能注入其他组件
    //因此使用有参构造将service注入进来
    private EduSubjectService eduSubjectService;
    public SubjectExcelListener() {}
    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    /**
     * 读excel文件表头
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap(Map headMap, AnalysisContext context) {

    }

    /**
     * 逐行读取表中内容
     * @param subjectData
     * @param analysisContext
     */
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        //若表格为空则抛出异常
        if(subjectData == null) {
            throw new GuliException(20001,"表格异常!");
        }
        //一行一行读取并存入数据库
        //判断是否有相同一级分类
        EduSubject oneSubject = exitOneSubject(eduSubjectService, subjectData.getOneSubjectName());
        if(oneSubject == null) {
            //表明表中没有相同一级分类,将课程添加到数据库
            oneSubject = new EduSubject();
            oneSubject.setParentId("0");
            oneSubject.setTitle(subjectData.getOneSubjectName());
            eduSubjectService.save(oneSubject);
        }
        //获得二级分类对应的一级分类的课程id
        String oneSubjectId = oneSubject.getId();
        EduSubject twoSubject = exitTwoSubject(eduSubjectService, subjectData.getTwoSubjectName(), oneSubjectId);
        //判断是否有相同的二级分类
        if(twoSubject == null) {
            //表明数据库中没有相同的二级分类,将其添加到数据库中
            twoSubject = new EduSubject();
            twoSubject.setTitle(subjectData.getTwoSubjectName());
            twoSubject.setParentId(oneSubjectId);
            eduSubjectService.save(twoSubject);
        }
    }

    /**
     * 判断一级分类不能重复添加,在数据库中查询title以及ParentId对应的课程
     * @param eduSubjectService
     * @param name
     * @return
     */
    private EduSubject exitOneSubject(EduSubjectService eduSubjectService,String name) {
        LambdaQueryWrapper<EduSubject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduSubject::getTitle,name)
                .eq(EduSubject::getParentId,"0");
        EduSubject oneSubject = eduSubjectService.getOne(queryWrapper);
        return oneSubject;
    }

    /**
     * 判断二级分类不能重复添加,在数据库中查询title以及ParentId对应的课程
     * @param eduSubjectService
     * @param pid
     * @return
     */
    private EduSubject exitTwoSubject(EduSubjectService eduSubjectService,String name,String pid) {
        LambdaQueryWrapper<EduSubject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduSubject::getTitle,name)
                .eq(EduSubject::getParentId,pid);
        EduSubject oneSubject = eduSubjectService.getOne(queryWrapper);
        return oneSubject;
    }
    /**
     * 读取完成之后
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
