package cn.hytc.tlias.controller;

import cn.hytc.tlias.vo.JobOption;
import cn.hytc.tlias.vo.Result;
import cn.hytc.tlias.service.ReportService;
import cn.hytc.tlias.vo.StudentClazzOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 数据统计报表控制器
 */
@Slf4j
@RequestMapping("/report")
@RestController
public class ReportController {

    @Autowired
    private ReportService reportService;

    /** 统计各职位员工人数 */
    @GetMapping("/empJobData")
    public Result getEmpJobData() {
        log.info("统计各个职位的员工人数");
        JobOption jobOption = reportService.getEmpJobData();
        return Result.success(jobOption);
    }

    /** 统计员工性别数量 */
    @GetMapping("/empGenderData")
    public Result getEmpGenderData() {
        log.info("统计员工性别数量");
        List<Map> list = reportService.getEmpGenderData();
        return Result.success(list);
    }

    /** 统计学生性别数量 */
    @GetMapping("/studentGenderData")
    public Result getStudentGenderData() {
        log.info("统计学生性别数量");
        List<Map> list = reportService.getStudentGenderData();
        return Result.success(list);
    }

    /** 统计学生学历数量 */
    @GetMapping("/studentCollegeData")
    public Result getStudentCollegeData() {
        log.info("统计学生学历数量");
        List<Map> list = reportService.getStudentCollegeData();
        return Result.success(list);
    }

    /** 统计各班级学生人数 */
    @GetMapping("/studentCountData")
    public Result getStudentCountData() {
        log.info("统计各班级学生人数");
        StudentClazzOption studentClazzOption = reportService.getStudentCountData();
        return Result.success(studentClazzOption);
    }
}