package com.xuyuchao.baseService.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: xuyuchao
 * @Date: 2022-06-21-19:32
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuliException extends RuntimeException{
    private Integer code;//状态码
    private String msg;//异常信息
}
