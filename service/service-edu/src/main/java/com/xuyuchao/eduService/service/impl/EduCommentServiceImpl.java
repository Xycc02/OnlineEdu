package com.xuyuchao.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuyuchao.baseService.exceptionhandler.GuliException;
import com.xuyuchao.commonUtils.JwtUtils;
import com.xuyuchao.commonUtils.R;
import com.xuyuchao.eduService.client.UcenterClient;
import com.xuyuchao.eduService.client.VodClient;
import com.xuyuchao.eduService.entity.EduComment;
import com.xuyuchao.eduService.mapper.EduCommentMapper;
import com.xuyuchao.eduService.service.EduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author xuyuchao
 * @since 2022-07-06
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Resource
    private UcenterClient ucenterClient;

    /**
     * 获取评论列表
     * @return
     */
    @Override
    public Map<String,Object> getCommentList(Integer page,Integer limit,String courseId) {
        //分页构造器
        Page<EduComment> pageInfo = new Page<>(page, limit);
        //条件构造器
        LambdaQueryWrapper<EduComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduComment::getCourseId,courseId);
        queryWrapper.orderByDesc(EduComment::getGmtCreate);
        baseMapper.selectPage(pageInfo, queryWrapper);

        List<EduComment> records = pageInfo.getRecords();

        long current = pageInfo.getCurrent();
        long pages = pageInfo.getPages();
        long size = pageInfo.getSize();
        long total = pageInfo.getTotal();
        boolean hasNext = pageInfo.hasNext();//下一页
        boolean hasPrevious = pageInfo.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        Map<String,Object> map = new HashMap();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    /**
     * 根据请求头的token获取用户信息
     * @param request
     */
    @Override
    public R getLoginUserInfo(HttpServletRequest request) {
        //调用jwt工具类的方法。根据request对象获取头信息，获得用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        R res = ucenterClient.getMemberById(memberId);
        if(!res.getSuccess()) {
            throw new GuliException(20001,"获取用户信息,熔断器...");
        }
        return res;
    }

    /**
     * 发布评论
     * @param eduComment
     */
    @Override
    public boolean addComment(EduComment eduComment) {
        int res = baseMapper.insert(eduComment);
        if(res < 1) {
            return false;
        }
        return true;
    }
}
