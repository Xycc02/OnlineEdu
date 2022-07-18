package com.xuyuchao.eduService.service;

import com.xuyuchao.eduService.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xuyuchao.eduService.entity.frontvo.subject.OneSubjectVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author xuyuchao
 * @since 2022-06-25
 */
public interface EduSubjectService extends IService<EduSubject> {
    //添加课程分类,参数EduSubjectService是为了将service传入读取excel的监听器中,方便监听器中调用业务
    void saveSubject(MultipartFile file,EduSubjectService eduSubjectService);
    //获取所有课程分类并编排数据格式(后台显示,不宜维护)
    List<Map<String,Object>> getList();
    //获取一级课程分类集合
    List<EduSubject> getOneSubject();
    //根据一级课程id获取二级课程集合
    List<EduSubject> getTwoSubject(String id);
    //获取所有课程分类并编排数据格式(前台,易维护)
    List<OneSubjectVo> getAllSubject();
}
