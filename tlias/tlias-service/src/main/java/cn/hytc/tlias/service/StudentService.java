package cn.hytc.tlias.service;

import cn.hytc.tlias.entity.Student;
import cn.hytc.tlias.vo.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学生服务接口
 */
public interface StudentService {
    PageResult<Student> page(String name, String degree, Integer clazzId, Integer page, Integer pageSize);
    List<Student> list(String name, String degree, Integer clazzId);
    void addStudent(Student student);
    void deleteById(Integer id);
    void updateStudent(Student student);
    Student getById(Integer id);
}