package com.xuyuchao.baseService.exceptionhandler;

import com.xuyuchao.commonUtils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: xuyuchao
 * @Date: 2022-06-21-15:28
 * @Description:
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public R exceptionHandler(Exception e) {
        log.error("出现了"+ e +"异常!");
        return R.error().message("全局异常处理器捕获到了异常!");
    }

    @ExceptionHandler(NullPointerException.class)
    public R exceptionHandler(NullPointerException e) {
        log.error("出现了"+ e +"异常!");
        return R.error().message("空指针异常!");
    }

    @ExceptionHandler(GuliException.class)
    public R exceptionHandler(GuliException e) {
        log.error("出现了"+ e +"异常!");
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
