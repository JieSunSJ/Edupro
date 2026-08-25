package cn.hytc.tlias.service.impl;

import cn.hytc.tlias.annotation.LogOperation;
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
    @LogOperation //自定义注解（表示：当前方法属于目标方法）
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
}