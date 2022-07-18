package com.xuyuchao.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xuyuchao.baseService.exceptionhandler.GuliException;
import com.xuyuchao.eduService.entity.EduChapter;
import com.xuyuchao.eduService.entity.EduVideo;
import com.xuyuchao.eduService.entity.chapter.ChapterVo;
import com.xuyuchao.eduService.entity.chapter.VideoVo;
import com.xuyuchao.eduService.mapper.EduChapterMapper;
import com.xuyuchao.eduService.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuyuchao.eduService.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Resource
    private EduVideoService eduVideoService;

    /**
     * 获取课程大纲列表
     * @return
     */
    @Override
    public List<ChapterVo> getChapterVedioList(String courseId) {
        //最终返回的课程大纲
        List<ChapterVo> chapterAndVedioList = new ArrayList<>();
        //根据课程id获取所有章节
        LambdaQueryWrapper<EduChapter> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(EduChapter::getCourseId,courseId);
        List<EduChapter> chapters = this.list(queryWrapper1);

        //遍历所有章节并根据每个章节id获取小节集合
        chapterAndVedioList = chapters.stream().map(item -> {
            ChapterVo chapterVo = new ChapterVo();
            //将每个章节对应id和title复制给chapterVo
            BeanUtils.copyProperties(item,chapterVo);
            //获取该章节对应的所有小节视频
            LambdaQueryWrapper<EduVideo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(EduVideo::getChapterId,item.getId());
            List<EduVideo> videos = eduVideoService.list(queryWrapper);
            //遍历每个视频并将对应id和title复制给videoVo,得到对应章节的小节视频集合
            List<VideoVo> videoVoList = videos.stream().map(item1 -> {
                VideoVo videoVo = new VideoVo();
                BeanUtils.copyProperties(item1, videoVo);
                return videoVo;
            }).collect(Collectors.toList());
            //设置每个章节对应的小节视频集合
            chapterVo.setChildren(videoVoList);
            return chapterVo;
        }).collect(Collectors.toList());
        return chapterAndVedioList;
    }

    /**
     * 添加章节并返回章节id
     * @param chapter
     * @return
     */
    @Override
    public String addChapter(EduChapter chapter) {
        this.save(chapter);
        String id = chapter.getId();
        return id;
    }

    /**
     * 修改章节
     * @param chapter
     */
    @Override
    public void updateChapter(EduChapter chapter) {
        this.updateById(chapter);
    }

    /**
     * 删除章节
     * @param id
     */
    @Override
    @Transactional
    public void deleteChapter(String id) {
        //删除章节
        this.removeById(id);
        //删若该章节下有小节则不能删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",id);
        int count = eduVideoService.count(wrapper);
        if(count >0) {//查询出小节，不进行删除
            throw new GuliException(20001,"该章节中有小节,不能删除");
        }

    }

    /**
     * 根据id获得章节
     * @param id
     * @return
     */
    @Override
    public EduChapter getChapterById(String id) {
        EduChapter chapter = this.getById(id);
        return chapter;
    }
}
