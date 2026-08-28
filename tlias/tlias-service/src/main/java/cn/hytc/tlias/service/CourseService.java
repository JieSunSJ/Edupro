package cn.hytc.tlias.service;

import cn.hytc.tlias.entity.Course;
import cn.hytc.tlias.entity.Exam;
import cn.hytc.tlias.entity.Schedule;
import cn.hytc.tlias.entity.StudentCourse;
import cn.hytc.tlias.vo.PageResult;

import java.util.List;
import java.util.Map;

public interface CourseService {
    PageResult<Course> pageAvailable(String name, Integer subject, Integer page, Integer pageSize);
    Course getById(Integer id);
    void enroll(Integer studentId, Integer courseId);
    Map<String, Object> unenroll(Integer studentId, Integer courseId);
    List<StudentCourse> getMyCourses(Integer studentId);
    Map<String, Object> getEnrollStatus(Integer studentId);
    Map<String, Object> getEnrollPeriod();
    List<Schedule> getSchedule(Integer studentId, String keyword);
    List<Exam> getExams(Integer studentId);
}