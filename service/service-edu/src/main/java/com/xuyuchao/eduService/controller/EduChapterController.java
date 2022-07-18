package com.xuyuchao.eduService.controller;


import com.xuyuchao.commonUtils.R;
import com.xuyuchao.eduService.entity.EduChapter;
import com.xuyuchao.eduService.entity.chapter.ChapterVo;
import com.xuyuchao.eduService.service.EduChapterService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author xuyuchao
 * @since 2022-06-26
 */
@RestController
@RequestMapping("/eduService/edu-chapter")

public class EduChapterController {
    @Resource
    private EduChapterService eduChapterService;

    /**
     * 根据课程id返回课程大纲列表,章节和每章节对应小节
     * @return
     */
    @GetMapping("/getChapterVedioList/{courseId}")
    public R getChapterVedioList(@PathVariable String courseId) {
        List<ChapterVo> chapterVedioList = eduChapterService.getChapterVedioList(courseId);
        return R.ok().data("chapterVedioList",chapterVedioList);
    }

    /**
     * 添加章节
     * @param chapter
     * @return
     */
    @PostMapping("/addChapter")
    public R addChapter(@RequestBody EduChapter chapter) {
        String chapterId = eduChapterService.addChapter(chapter);
        return R.ok().data("chapterId",chapterId);
    }

    /**
     * 根据章节id得到章节
     * @param id
     * @return
     */
    @GetMapping("/getChapterById/{id}")
    public R getChapterById(@PathVariable String id) {
        EduChapter chapter = eduChapterService.getChapterById(id);
        return R.ok().data("chapter",chapter);
    }
    /**
     * 修改章节
     * @param chapter
     * @return
     */
    @PutMapping("/updateChapter")
    public R updateChapter(@RequestBody EduChapter chapter) {
        eduChapterService.updateChapter(chapter);
        return R.ok();
    }

    /**
     * 删除章节
     * @param id
     * @return
     */
    @DeleteMapping("/deleteChapter/{id}")
    public R deleteChapter(@PathVariable String id) {
        eduChapterService.deleteChapter(id);
        return R.ok();
    }
}

