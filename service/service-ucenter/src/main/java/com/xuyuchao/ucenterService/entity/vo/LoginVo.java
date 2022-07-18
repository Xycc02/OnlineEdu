package com.xuyuchao.ucenterService.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: xuyuchao
 * @Date: 2022-07-02-16:49
 * @Description:
 */
@Data
public class LoginVo {
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "密码")
    private String password;
}
