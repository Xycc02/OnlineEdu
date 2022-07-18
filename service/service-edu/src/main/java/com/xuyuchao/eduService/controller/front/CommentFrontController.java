package com.xuyuchao.eduService.controller.front;

import com.xuyuchao.commonUtils.R;
import com.xuyuchao.eduService.entity.EduComment;
import com.xuyuchao.eduService.service.EduCommentService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author: xuyuchao
 * @Date: 2022-07-06-15:53
 * @Description:
 */
@RestController
@RequestMapping("/eduservice/commentfront")

public class CommentFrontController {
    @Resource
    private EduCommentService eduCommentService;

    /**
     * 分页查询评论
     * @param page
     * @param limit
     * @param courseId
     * @return
     */
    @GetMapping("/getCommentList/{page}/{limit}")
    public R getCommontList(@PathVariable Integer page,@PathVariable Integer limit,String courseId) {
        Map<String,Object> map = eduCommentService.getCommentList(page,limit,courseId);
        return R.ok().data(map);
    }

    /**
     * 根据请求头的token获取用户信息
     * @param request
     * @return
     */
    @GetMapping("/getLoginUserInfo")
    public R getLoginUserInfo(HttpServletRequest request) {
        R res = eduCommentService.getLoginUserInfo(request);
        return res;
    }

    /**
     * 发布评论
     * @param eduComment
     * @return
     */
    @PostMapping("/addComment")
    public R addComment(@RequestBody EduComment eduComment) {
        boolean flag = eduCommentService.addComment(eduComment);
        if(flag == true) {
            return R.ok();
        }else {
            return R.error().message("对不起,发布评论失败!");
        }
    }
}
