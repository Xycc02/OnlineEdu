package com.xuyuchao.eduService.service;

import com.xuyuchao.commonUtils.R;
import com.xuyuchao.eduService.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author xuyuchao
 * @since 2022-07-06
 */
public interface EduCommentService extends IService<EduComment> {
    //获取评论列表
    Map<String,Object> getCommentList(Integer page, Integer limit, String courseId);
    //根据请求头的token获取用户信息
    R getLoginUserInfo(HttpServletRequest request);
    //发布评论
    boolean addComment(EduComment eduComment);
}
