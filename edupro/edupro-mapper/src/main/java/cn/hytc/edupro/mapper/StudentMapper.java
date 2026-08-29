package cn.hytc.edupro.mapper;

import cn.hytc.edupro.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 学生Mapper
 */
@Mapper
public interface StudentMapper {
    List<Student> list(@Param("name") String name, @Param("degree") String degree, @Param("clazzId") Integer clazzId);
    @Delete("delete from student where id = #{id}")
    int deleteById(@Param("id") Integer id);
    int add(Student student);
    int updateById(Student student);
    Student getById(@Param("id") Integer id);
    Student findByNo(@Param("no") String no);
    int updatePassword(@Param("id") Integer id, @Param("password") String password);
    int updateProfile(Student student);
    int updateImage(@Param("id") Integer id, @Param("image") String image);
    int updateUnenrollCount(@Param("id") Integer id, @Param("count") Integer count);
    List<Map> countStudentGenderData();
    List<Map> countStudentCollegeData();
    List<Map<String, Object>> countStudentCountData();
}