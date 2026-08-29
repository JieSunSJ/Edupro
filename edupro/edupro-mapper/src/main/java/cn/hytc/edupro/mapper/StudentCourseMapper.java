package cn.hytc.edupro.mapper;

import cn.hytc.edupro.entity.StudentCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentCourseMapper {
    int insert(StudentCourse studentCourse);
    int deleteByStudentAndCourse(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId, @Param("semester") String semester);
    List<StudentCourse> findByStudentId(@Param("studentId") Integer studentId);
    int countByStudentAndSemester(@Param("studentId") Integer studentId, @Param("semester") String semester);
    StudentCourse findByStudentAndCourse(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId, @Param("semester") String semester);
    List<StudentCourse> findUnassignedStudents(@Param("semester") String semester);
}