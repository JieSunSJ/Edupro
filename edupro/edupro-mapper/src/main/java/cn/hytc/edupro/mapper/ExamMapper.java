package cn.hytc.edupro.mapper;

import cn.hytc.edupro.entity.Exam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExamMapper {
    List<Exam> findByStudentId(Integer studentId);
}