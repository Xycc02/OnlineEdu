package com.xuyuchao.eduService.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuyuchao.eduService.entity.EduSubject;
import com.xuyuchao.eduService.entity.excel.SubjectData;
import com.xuyuchao.eduService.entity.frontvo.subject.OneSubjectVo;
import com.xuyuchao.eduService.entity.frontvo.subject.TwoSubjectVo;
import com.xuyuchao.eduService.listener.SubjectExcelListener;
import com.xuyuchao.eduService.mapper.EduSubjectMapper;
import com.xuyuchao.eduService.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author xuyuchao
 * @since 2022-06-25
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    /**
     * 添加课程分类
     * @param file
     */
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService eduSubjectService) {
        try {
            //以文件流的方式读取文件
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取所有课程分类数据     List<Map<EduSubject,List<EduSubject>>>  前端返回树形结构数据
     * @return
     */
    @Override
    public List<Map<String,Object>> getList() {
        List<Map<String,Object>> lists = new ArrayList<>();
        //获取所有一级课程分类
        LambdaQueryWrapper<EduSubject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduSubject::getParentId,"0")
                .orderByAsc(EduSubject::getSort);
        List<EduSubject> oneSubjectList = this.list(queryWrapper);

        oneSubjectList.forEach(item -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("label",item.getTitle());
            //根据一级分类课程id得到对应二级分类课程
            LambdaQueryWrapper<EduSubject> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(EduSubject::getParentId,item.getId());
            List<EduSubject> twoSubjectList = this.list(queryWrapper1);
            ArrayList<Map> list = new ArrayList<>();
            twoSubjectList.forEach(item1 -> {
                HashMap<String, Object> map1 = new HashMap<>();
                map1.put("label",item1.getTitle());
                list.add(map1);
            });
            map.put("children",list);

            lists.add(map);
        });
        return lists;
    }

    /**
     * 获取所有一级分类课程
     * @return
     */
    @Override
    public List<EduSubject> getOneSubject() {
        //获取所有一级课程分类
        LambdaQueryWrapper<EduSubject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduSubject::getParentId,"0")
                .orderByAsc(EduSubject::getSort);
        List<EduSubject> oneSubjectList = this.list(queryWrapper);
        return oneSubjectList;
    }

    /**
     * 根据一级课程id获取二级课程集合
     * @return
     */
    @Override
    public List<EduSubject> getTwoSubject(String id) {
        //根据一级课程id获取二级课程集合
        LambdaQueryWrapper<EduSubject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduSubject::getParentId,id)
                .orderByAsc(EduSubject::getSort);
        List<EduSubject> twoSubjectList = this.list(queryWrapper);
        return twoSubjectList;
    }

    /**
     * 获取所有课程分类并编排数据格式(前台,易维护)
     * @return
     */
    @Override
    public List<OneSubjectVo> getAllSubject() {
        //返回结果
        List<OneSubjectVo> list = new ArrayList<>();
        //获取所有一级分类集合
        LambdaQueryWrapper<EduSubject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduSubject::getParentId,0);
        List<EduSubject> oneSubjectList = baseMapper.selectList(queryWrapper);

        //根据一级分类id获得所有一级分类对应的二级分类
        list = oneSubjectList.stream().map(item -> {
            OneSubjectVo oneSubjectVo = new OneSubjectVo();
            BeanUtils.copyProperties(item,oneSubjectVo);

            LambdaQueryWrapper<EduSubject> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(EduSubject::getParentId,item.getId());
            List<EduSubject> twoSubjectList = baseMapper.selectList(queryWrapper1);
            List<TwoSubjectVo> twoSubjectVos = twoSubjectList.stream().map(item1 -> {
                TwoSubjectVo twoSubjectVo = new TwoSubjectVo();
                BeanUtils.copyProperties(item1, twoSubjectVo);
                return twoSubjectVo;
            }).collect(Collectors.toList());

            oneSubjectVo.setChildren(twoSubjectVos);
            return oneSubjectVo;
        }).collect(Collectors.toList());
        return list;
    }
}
