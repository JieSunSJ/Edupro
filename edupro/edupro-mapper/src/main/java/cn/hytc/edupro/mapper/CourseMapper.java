package cn.hytc.edupro.mapper;

import cn.hytc.edupro.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseMapper {
    List<Course> listAvailable(@Param("name") String name, @Param("subject") Integer subject);
    Course getById(@Param("id") Integer id);
    int countEnrolled(@Param("courseId") Integer courseId);
}