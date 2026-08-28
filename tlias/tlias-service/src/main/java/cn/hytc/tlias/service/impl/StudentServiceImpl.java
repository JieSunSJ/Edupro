package cn.hytc.tlias.service.impl;

import cn.hytc.tlias.entity.Student;
import cn.hytc.tlias.mapper.StudentMapper;
import cn.hytc.tlias.service.StudentService;
import cn.hytc.tlias.vo.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 学生服务实现
 */

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;
    @Override
    public List<Student> list(String name, String degree, Integer clazzId) {
        return studentMapper.list(name, degree, clazzId);
    }
    @Override
    public PageResult<Student> page(String name, String degree, Integer clazzId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Student> list = studentMapper.list(name, degree, clazzId);
        PageInfo<Student> pageInfo = new PageInfo<>(list);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }
    @Override
    public void addStudent(Student student) {
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.add(student);
    }
    @Override
    public void deleteById(Integer id) {
        studentMapper.deleteById(id);
    }
    @Override
    public void updateStudent(Student student) {
        studentMapper.updateById(student);
    }

    @Override
    public Student getById(Integer id) {
        return studentMapper.getById(id);
    }

    @Override
    public Student login(String no, String password) {
        Student student = studentMapper.findByNo(no);
        if (student == null) {
            throw new RuntimeException("学号不存在");
        }
        if (student.getPassword() == null || !student.getPassword().equals(password)) {
            throw new RuntimeException("密码错误");
        }
        return student;
    }

    @Override
    public void updateProfile(Student student) {
        studentMapper.updateProfile(student);
    }

    @Override
    public void updatePassword(Integer id, String oldPassword, String newPassword) {
        Student student = studentMapper.getById(id);
        if (student == null) {
            throw new RuntimeException("学生不存在");
        }
        if (student.getPassword() == null || !student.getPassword().equals(oldPassword)) {
            throw new RuntimeException("原密码错误");
        }
        studentMapper.updatePassword(id, newPassword);
    }

    @Override
    public void updateImage(Integer id, String imageUrl) {
        studentMapper.updateImage(id, imageUrl);
    }
}