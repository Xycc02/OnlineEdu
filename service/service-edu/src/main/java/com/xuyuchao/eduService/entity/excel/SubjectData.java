package com.xuyuchao.eduService.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author: xuyuchao
 * @Date: 2022-06-25-12:57
 * @Description:
 */
@Data
public class SubjectData {
    //一级学科
    @ExcelProperty(index = 0)
    private String oneSubjectName;
    //二级学科
    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
