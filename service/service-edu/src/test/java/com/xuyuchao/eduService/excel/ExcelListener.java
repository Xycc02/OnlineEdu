package com.xuyuchao.eduService.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * @Author: xuyuchao
 * @Date: 2022-06-25-12:29
 * @Description: 创建一个Excel监听器读取
 */
public class ExcelListener extends AnalysisEventListener<DataDemo> {
    //一行一行读取excel内容
    @Override
    public void invoke(DataDemo dataDemo, AnalysisContext analysisContext) {
        System.out.println("正在读取=>"+dataDemo);
    }
    //读取表头内容
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头："+headMap);
    }
    //读取完成之后
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("读取完成!!");
    }
}
