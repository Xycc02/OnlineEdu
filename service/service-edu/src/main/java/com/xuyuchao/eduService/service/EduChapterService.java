package com.xuyuchao.eduService.service;

import com.xuyuchao.eduService.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xuyuchao.eduService.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author xuyuchao
 * @since 2022-06-26
 */
public interface EduChapterService extends IService<EduChapter> {
    //获得课程大纲列表
    List<ChapterVo> getChapterVedioList(String courseId);
    //添加章节
    String addChapter(EduChapter chapter);
    //修改章节
    void updateChapter(EduChapter chapter);
    //删除章节
    void deleteChapter(String id);
    //根据id获得章节
    EduChapter getChapterById(String id);
}
