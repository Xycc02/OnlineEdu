package com.xuyuchao.eduService.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author: xuyuchao
 * @Date: 2022-06-25-11:56
 * @Description:
 */
@Data
public class DataDemo {
    //设置excel表头
    @ExcelProperty(value = "学生编号",index = 0)
    private Integer sno;
    @ExcelProperty(value = "学生姓名",index = 1)
    private String sname;
}
