package com.xuyuchao.eduService.excel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xuyuchao
 * @Date: 2022-06-25-12:00
 * @Description:
 */
public class EasyExcelTest {

    private static List<DataDemo> getData() {
        List<DataDemo> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DataDemo data = new DataDemo();
            data.setSno(i);
            data.setSname("lucy"+i);
            list.add(data);
        }
        return list;
    }

    /**
     * excel文件写操作
     */
    @Test
    public void testWrite() {
        //实现excel写的操作
        // 1 设置写入文件夹地址和excel文件名称
        String filename = "C:\\Users\\徐宇超\\Desktop\\write.xlsx";
        // 2 调用easyexcel里面的方法实现写操作
        // write方法两个参数：第一个参数文件路径名称，第二个参数实体类class
        EasyExcel.write(filename,DataDemo.class).sheet("学生列表").doWrite(getData());
    }

    /**
     * 文件读操作
     */
    @Test
    public void testRead() {
        String filename = "C:\\Users\\徐宇超\\Desktop\\write.xlsx";
        EasyExcel.read(filename,DataDemo.class,new ExcelListener()).sheet().doRead();
    }
}
