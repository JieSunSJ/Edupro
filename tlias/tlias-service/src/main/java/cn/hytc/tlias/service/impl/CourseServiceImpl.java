package cn.hytc.tlias.service.impl;

import cn.hytc.tlias.entity.Course;
import cn.hytc.tlias.entity.Exam;
import cn.hytc.tlias.entity.Schedule;
import cn.hytc.tlias.entity.Student;
import cn.hytc.tlias.entity.StudentCourse;
import cn.hytc.tlias.entity.SystemConfig;
import cn.hytc.tlias.mapper.CourseMapper;
import cn.hytc.tlias.mapper.ExamMapper;
import cn.hytc.tlias.mapper.ScheduleMapper;
import cn.hytc.tlias.mapper.StudentCourseMapper;
import cn.hytc.tlias.mapper.StudentMapper;
import cn.hytc.tlias.mapper.SystemConfigMapper;
import cn.hytc.tlias.service.CourseService;
import cn.hytc.tlias.vo.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseServiceImpl implements CourseService {

    private static final String SEMESTER = "2024-2025-1";
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private StudentCourseMapper studentCourseMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private SystemConfigMapper systemConfigMapper;

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Autowired
    private ExamMapper examMapper;

    @Override
    public PageResult<Course> pageAvailable(String name, Integer subject, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Course> list = courseMapper.listAvailable(name, subject);
        PageInfo<Course> pageInfo = new PageInfo<>(list);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public Course getById(Integer id) {
        return courseMapper.getById(id);
    }

    @Override
    @Transactional
    public void enroll(Integer studentId, Integer courseId) {
        LocalDateTime now = LocalDateTime.now();

        SystemConfig startCfg = systemConfigMapper.getByKey("enroll_start_time");
        SystemConfig endCfg = systemConfigMapper.getByKey("enroll_end_time");
        if (startCfg == null || endCfg == null) {
            throw new RuntimeException("选课时间未配置");
        }
        LocalDateTime startTime = LocalDateTime.parse(startCfg.getConfigValue(), DTF);
        LocalDateTime endTime = LocalDateTime.parse(endCfg.getConfigValue(), DTF);
        if (now.isBefore(startTime) || now.isAfter(endTime)) {
            throw new RuntimeException("当前不在选课时间段内");
        }

        Course course = courseMapper.getById(courseId);
        if (course == null) {
            throw new RuntimeException("课程不存在");
        }

        int enrolled = courseMapper.countEnrolled(courseId);
        if (enrolled >= course.getCapacity()) {
            throw new RuntimeException("该课程已满员，请选择其他课程");
        }

        StudentCourse existing = studentCourseMapper.findByStudentAndCourse(studentId, courseId, SEMESTER);
        if (existing != null) {
            throw new RuntimeException("你已经选过该课程");
        }

        List<StudentCourse> myCourses = studentCourseMapper.findByStudentId(studentId);
        if (myCourses != null && !myCourses.isEmpty()) {
            for (StudentCourse old : myCourses) {
                studentCourseMapper.deleteByStudentAndCourse(studentId, old.getCourseId(), SEMESTER);
            }
        }

        StudentCourse sc = new StudentCourse();
        sc.setStudentId(studentId);
        sc.setCourseId(courseId);
        sc.setSemester(SEMESTER);
        studentCourseMapper.insert(sc);
    }

    @Override
    @Transactional
    public Map<String, Object> unenroll(Integer studentId, Integer courseId) {
        LocalDateTime now = LocalDateTime.now();

        SystemConfig startCfg = systemConfigMapper.getByKey("enroll_start_time");
        SystemConfig endCfg = systemConfigMapper.getByKey("enroll_end_time");
        if (startCfg == null || endCfg == null) {
            throw new RuntimeException("选课时间未配置");
        }
        LocalDateTime startTime = LocalDateTime.parse(startCfg.getConfigValue(), DTF);
        LocalDateTime endTime = LocalDateTime.parse(endCfg.getConfigValue(), DTF);
        if (now.isBefore(startTime) || now.isAfter(endTime)) {
            throw new RuntimeException("当前不在选课时间段内");
        }

        Student student = studentMapper.getById(studentId);
        int usedCount = student.getUnenrollCount() != null ? student.getUnenrollCount() : 0;
        int maxCount = 3;
        if (usedCount >= maxCount) {
            Map<String, Object> result = new HashMap<>();
            result.put("usedCount", maxCount);
            result.put("remainingCount", 0);
            result.put("maxCount", maxCount);
            throw new RuntimeException("本学期退课次数已用完（" + maxCount + "/" + maxCount + "），无法继续退课");
        }

        StudentCourse existing = studentCourseMapper.findByStudentAndCourse(studentId, courseId, SEMESTER);
        if (existing == null) {
            throw new RuntimeException("你未选该课程，无法退课");
        }

        studentCourseMapper.deleteByStudentAndCourse(studentId, courseId, SEMESTER);

        studentMapper.updateUnenrollCount(studentId, usedCount + 1);

        Map<String, Object> result = new HashMap<>();
        result.put("usedCount", usedCount + 1);
        result.put("remainingCount", maxCount - (usedCount + 1));
        result.put("maxCount", maxCount);
        return result;
    }

    @Override
    public List<StudentCourse> getMyCourses(Integer studentId) {
        return studentCourseMapper.findByStudentId(studentId);
    }

    @Override
    public Map<String, Object> getEnrollStatus(Integer studentId) {
        Student student = studentMapper.getById(studentId);
        int courseCount = studentCourseMapper.countByStudentAndSemester(studentId, SEMESTER);
        int unenrollUsed = student.getUnenrollCount() != null ? student.getUnenrollCount() : 0;
        int unenrollMax = 3;

        Map<String, Object> result = new HashMap<>();
        result.put("hasCourse", courseCount > 0);
        result.put("courseCount", courseCount);
        result.put("unenrollUsed", unenrollUsed);
        result.put("unenrollRemaining", unenrollMax - unenrollUsed);
        result.put("unenrollMax", unenrollMax);
        return result;
    }

    @Override
    public Map<String, Object> getEnrollPeriod() {
        SystemConfig startCfg = systemConfigMapper.getByKey("enroll_start_time");
        SystemConfig endCfg = systemConfigMapper.getByKey("enroll_end_time");

        Map<String, Object> result = new HashMap<>();
        if (startCfg == null || endCfg == null) {
            result.put("isOpen", false);
            result.put("statusText", "选课时间未配置");
            return result;
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime = LocalDateTime.parse(startCfg.getConfigValue(), DTF);
        LocalDateTime endTime = LocalDateTime.parse(endCfg.getConfigValue(), DTF);

        result.put("startTime", startCfg.getConfigValue());
        result.put("endTime", endCfg.getConfigValue());

        if (now.isBefore(startTime)) {
            long days = ChronoUnit.DAYS.between(now, startTime);
            result.put("isOpen", false);
            result.put("statusText", "选课将于 " + startTime.getMonthValue() + "月" + startTime.getDayOfMonth() + "日 开始");
            result.put("countdown", ChronoUnit.SECONDS.between(now, startTime));
        } else if (now.isAfter(endTime)) {
            result.put("isOpen", false);
            result.put("statusText", "选课已截止，未选课学生将被自动分配");
            result.put("countdown", 0L);
        } else {
            long remainingSeconds = ChronoUnit.SECONDS.between(now, endTime);
            long days = remainingSeconds / 86400;
            long hours = (remainingSeconds % 86400) / 3600;
            result.put("isOpen", true);
            result.put("statusText", "选课进行中，距截止还有 " + days + "天" + hours + "时");
            result.put("countdown", remainingSeconds);
        }
        return result;
    }

    @Override
    public List<Schedule> getSchedule(Integer studentId, String keyword) {
        return scheduleMapper.findByStudentId(studentId, keyword);
    }

    @Override
    public List<Exam> getExams(Integer studentId) {
        return examMapper.findByStudentId(studentId);
    }
}