package cn.hytc.tlias.controller;

import cn.hytc.tlias.entity.Course;
import cn.hytc.tlias.entity.StudentCourse;
import cn.hytc.tlias.service.CourseService;
import cn.hytc.tlias.utils.JwtUtils;
import cn.hytc.tlias.vo.PageResult;
import cn.hytc.tlias.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/student")
public class StudentCourseController {

    @Autowired
    private CourseService courseService;

    private Integer getStudentId(String token) {
        return (Integer) JwtUtils.parseJWT(token).get("id");
    }

    @GetMapping("/enroll-period")
    public Result getEnrollPeriod() {
        return Result.success(courseService.getEnrollPeriod());
    }

    @GetMapping("/course/available")
    public Result listAvailable(@RequestParam(required = false) String name,
                                @RequestParam(required = false) Integer subject,
                                @RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "10") Integer pageSize) {
        PageResult<Course> result = courseService.pageAvailable(name, subject, page, pageSize);
        return Result.success(result);
    }

    @GetMapping("/course/available/{id}")
    public Result getCourseDetail(@PathVariable Integer id) {
        Course course = courseService.getById(id);
        return Result.success(course);
    }

    @PostMapping("/course/enroll/{courseId}")
    public Result enroll(@RequestHeader("token") String token, @PathVariable Integer courseId) {
        try {
            Integer studentId = getStudentId(token);
            courseService.enroll(studentId, courseId);
            return Result.success("选课成功");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/course/unenroll/{courseId}")
    public Result unenroll(@RequestHeader("token") String token, @PathVariable Integer courseId) {
        try {
            Integer studentId = getStudentId(token);
            Map<String, Object> result = courseService.unenroll(studentId, courseId);
            return Result.success(result);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/course/status")
    public Result getEnrollStatus(@RequestHeader("token") String token) {
        Integer studentId = getStudentId(token);
        return Result.success(courseService.getEnrollStatus(studentId));
    }

    @GetMapping("/course/my")
    public Result getMyCourses(@RequestHeader("token") String token) {
        Integer studentId = getStudentId(token);
        List<StudentCourse> list = courseService.getMyCourses(studentId);
        return Result.success(list);
    }

    @GetMapping("/schedule")
    public Result getSchedule(@RequestHeader("token") String token,
                               @RequestParam(required = false) String keyword) {
        Integer studentId = getStudentId(token);
        return Result.success(courseService.getSchedule(studentId, keyword));
    }

    @GetMapping("/exam")
    public Result getExams(@RequestHeader("token") String token) {
        Integer studentId = getStudentId(token);
        return Result.success(courseService.getExams(studentId));
    }
}