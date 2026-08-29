package cn.hytc.edupro.controller;

import cn.hytc.edupro.entity.Clazz;
import cn.hytc.edupro.vo.PageResult;
import cn.hytc.edupro.vo.Result;
import cn.hytc.edupro.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 班级管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/clazzes")
public class ClazzController {
    @Autowired
    private ClazzService clazzService;

    /** 分页查询班级 */
    @GetMapping
    public Result getClazzList(@RequestParam(required = false) String name, @DateTimeFormat(pattern = "yyyy-M-d") LocalDate begin, @DateTimeFormat(pattern = "yyyy-M-d") LocalDate end, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer pageSize) {
        PageResult<Clazz> clazzList = clazzService.page(name, begin, end, page, pageSize);
        return Result.success(clazzList);
    }

    /** 删除班级 */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("删除班级: id={} ", id);
        clazzService.deleteById(id);
        return Result.success("删除成功");
    }

    /** 添加班级 */
    @PostMapping
    public Result add(@RequestBody Clazz clazz) {
        log.info("添加班级: {}", clazz);
        clazzService.add(clazz);
        return Result.success("添加成功");
    }

    /** 根据ID查询班级 */
    @GetMapping("/{id}")
    public Result get(@PathVariable Integer id) {
        log.info("根据ID查询: id={}", id);
        Clazz clazz = clazzService.getById(id);
        return Result.success(clazz);
    }

    /** 更新班级 */
    @PutMapping
    public Result update(@RequestBody Clazz clazz) {
        log.info("更新班级: {}", clazz);
        clazzService.update(clazz);
        return Result.success("更新成功");
    }

    /** 查询所有班级 */
    @GetMapping("/all")
    public Result getAll() {
        log.info("查询所有班级");
        List<Clazz> clazzList = clazzService.Alllist();
        return Result.success(clazzList);
    }

    /** 分页查询所有班级 */
    @RequestMapping("/all/page")
    public Result getAllPage(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer pageSize) {
        log.info("查询所有班级, 分页: page={}, pageSize={}", page, pageSize);
        PageResult<Clazz> pageResult = clazzService.allPage(page, pageSize);
        return Result.success(pageResult);
    }
}