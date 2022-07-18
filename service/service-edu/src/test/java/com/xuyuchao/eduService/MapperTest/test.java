package com.xuyuchao.eduService.MapperTest;

import com.xuyuchao.eduService.entity.frontvo.course.CourseWebVo;
import com.xuyuchao.eduService.mapper.EduCourseMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: xuyuchao
 * @Date: 2022-07-05-15:01
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class test {
    @Resource
    private EduCourseMapper eduCourseMapper;
    @Test
    public void test1() {
        CourseWebVo baseCourseInfo = eduCourseMapper.getBaseCourseInfo("1542753632550924289");
        System.out.println(baseCourseInfo);
    }
}
