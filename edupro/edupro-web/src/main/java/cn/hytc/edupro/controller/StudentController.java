package cn.hytc.edupro.controller;

import cn.hytc.edupro.entity.Student;
import cn.hytc.edupro.service.StudentService;
import cn.hytc.edupro.vo.PageResult;
import cn.hytc.edupro.vo.Result;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 学生管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    /** 分页查询学生信息 */
    @RequestMapping("/list")
    public Result getPage(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "degree", required = false) String degree, @RequestParam(value = "clazzId", required = false) Integer clazzId, @RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize) {
        PageResult<Student> pageResult = studentService.page(name, degree, clazzId, page, pageSize);
        return Result.success(pageResult);
    }

    /** 删除学生信息 */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        studentService.deleteById(id);
        return Result.success("删除成功");
    }

    /** 添加学生信息 */
    @PostMapping("/add")
    public Result add(@RequestBody Student student) {
        studentService.addStudent(student);
        return Result.success("添加成功");
    }

    /** 修改学生信息 */
    @PutMapping("/update")
    public Result update(@RequestBody Student student) {
        studentService.updateStudent(student);
        return Result.success("修改成功");
    }

    /** 根据ID查询学生信息 */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        log.info("根据ID查询学生: id={}", id);
        return Result.success(studentService.getById(id));
    }

}